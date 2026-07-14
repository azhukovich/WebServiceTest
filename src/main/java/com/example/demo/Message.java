package com.example.demo;

import jakarta.persistence.*;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    public Message() {}

    public Message(String text) {
        this.text = text;
    }

    public Long getId() { return id; }
    public String getText() { return text; }
}
