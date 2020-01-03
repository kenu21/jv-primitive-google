package com.kenu.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
public class Page {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String uri;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String body;

    public Page(String uri, String title, String body) {
        this.uri = uri;
        this.title = title;
        this.body = body;
    }
}
