package com.kenu.service;

import com.kenu.entities.Page;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Pageable;

public interface PageService {
    Page index(String uri, int maxLevel, Set<String> set) throws IOException;

    List<Page> find(String query, Pageable pageable);
}
