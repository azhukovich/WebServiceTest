package com.example.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class SportResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    private Integer count;
    private LocalDateTime createdAt;

    public SportResult() {}

    public SportResult(String text, Integer count) {
        this.text = text;
        this.count = count;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public String getText() { return text; }
    public Integer getCount() { return count; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
