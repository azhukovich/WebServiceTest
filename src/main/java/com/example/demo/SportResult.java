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
    private String name;
    private Integer count;
    private LocalDateTime createdAt;

    public SportResult() {}

    public SportResult(String name, Integer count) {
        this.name = name;
        this.count = count;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public Integer getQuantity() { return count; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
