package com.example.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class SportResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer quantity;
    private LocalDateTime createdAt;
    private String comment;

    public SportResult(String name, Integer quantity) {
        this.name = name;
        this.quantity = quantity;
        this.createdAt = LocalDateTime.now();
    }

    public SportResult(String name, Integer quantity, String comment) {
        this.name = name;
        this.quantity = quantity;
        this.createdAt = LocalDateTime.now();
        this.comment = comment;
    }

    public LocalDateTime test() {
        return getCreatedAt();
    }
}
