package com.example.demo;

public class SportDto {
    private final String name;
    private final Integer quantity;
    private final String createdAt;

    public SportDto(String name, Integer quantity, String createdAt) {
        this.name = name;
        this.quantity = quantity;
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
