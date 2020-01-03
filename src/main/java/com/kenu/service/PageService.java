package com.kenu.service;

import com.kenu.entities.Page;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Pageable;

public interface PageService {
    Page index(String uri, int maxLevel) throws IOException;

    List<Page> find(String query, Pageable pageable);
}
