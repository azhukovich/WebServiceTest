package com.example.demo;

import java.util.ArrayList;
import java.util.List;

public class MessageStorage {
    private static final List<String> messages = new ArrayList<>();

    public static void add(String msg) {
        messages.add(msg);
    }

    public static List<String> getAll() {
        return messages;
    }
}
