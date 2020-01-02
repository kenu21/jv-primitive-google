package com.kenu.service;

import com.kenu.entities.Page;
import com.kenu.repositories.PageRepository;
import com.kenu.threads.IndexNext;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PageServiceImpl implements PageService {
    private static final ExecutorService EXECUTOR_SERVICE =
            Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    @Autowired
    private PageRepository pageRepository;

    @Override
    public Page index(String uri, int level, Set<String> set) throws IOException {
        Document doc = Jsoup.connect(uri).get();
        Page save = pageRepository.save(new Page(uri, doc.title(), doc.body().text()));
        Elements links = doc.select("a[href]");

        if (level > 0) {
            level--;
            EXECUTOR_SERVICE.execute(new IndexNext(set, links, level));
        }
        return save;
    }

    @Override
    public List<Page> find(String query, Pageable pageable) {
        List<Page> allByBody = pageRepository.findAllByBody(query, pageable);
        return allByBody;
    }
}
