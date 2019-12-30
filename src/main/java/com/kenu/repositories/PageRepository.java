package com.kenu.repositories;

import com.kenu.entities.Page;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PageRepository extends JpaRepository<Page, Long> {
    List<Page> findAllByBody(String body, Pageable pageable);
}
