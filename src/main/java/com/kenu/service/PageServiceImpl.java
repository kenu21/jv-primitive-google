package com.kenu.service;

import com.kenu.entities.Page;
import com.kenu.repositories.PageRepository;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

import lombok.SneakyThrows;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PageServiceImpl implements PageService {
    private static final ForkJoinPool FORK_JOIN_POOL =
            new ForkJoinPool(Runtime.getRuntime().availableProcessors());
    private static final Set<String> SET = new HashSet<>();

    @Autowired
    private PageRepository pageRepository;

    @Override
    public Page index(String uri, int level) throws IOException {
        if (!SET.add(uri)) {
            return null;
        }

        Document doc = null;
        try {
            doc = Jsoup.connect(uri).get();
        } catch (MalformedURLException | HttpStatusException e) {
            return null;
        }

        Elements links = doc.select("a[href]");

        if (level > 0) {
            level--;
            for (Element link : links) {
                String attr = link.attr("abs:href");
                FORK_JOIN_POOL.invoke(new Threads(attr, level));
            }
        }

        Page save = pageRepository.save(new Page(uri, doc.title(), doc.body().text()));
        return save;
    }

    @Override
    public List<Page> find(String query, Pageable pageable) {
        List<Page> allByBody = pageRepository.findAllByBody(query, pageable);
        return allByBody;
    }

    private class Threads extends RecursiveAction {
        private String uri;
        private int level;

        public Threads(String uri, int level) {
            this.uri = uri;
            this.level = level;
        }

        @SneakyThrows
        @Override
        protected void compute() {
            index(uri, level);
        }
    }
}
