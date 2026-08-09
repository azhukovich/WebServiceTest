package com.example.demo;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ExerciseResult {

    @Getter
    private final String exerciseName;
    private final List<Integer> quantities = new ArrayList<>();
    private final List<String> comments = new ArrayList<>();
    @Getter
    private String colorClass;

    public ExerciseResult(String exerciseName) {
        this.exerciseName = exerciseName;
        updateColor();
    }

    public void add(int quantity, String comment) {
        quantities.add(quantity);
        if (comment != null && !comment.isBlank()) {
            comments.add(comment);
        }
        updateColor();
    }

    public boolean isEmpty() {
        return quantities.isEmpty();
    }

    // Формат: 2+5+8=15
    public String formatQuantities() {
        if (quantities.isEmpty()) return "Нет результатов";

        String left = quantities.stream()
                .map(Object::toString)
                .collect(Collectors.joining("+"));

        int sum = quantities.stream().mapToInt(Integer::intValue).sum();

        return left + "=" + sum;
    }

    // Комментарии:
    public String formatComments() {
        return String.join("; ", comments);
    }

    public int getSum() {
        return quantities.stream().mapToInt(Integer::intValue).sum();
    }

    private void updateColor() {
        if (isEmpty()) {
            colorClass = "red";
            return;
        }
        int sum = quantities.stream().mapToInt(Integer::intValue).sum();
        if (sum > 95) colorClass = "green";
        else if (sum >= 50) colorClass = "yellow";
        else if (sum >= 10) colorClass = "blue";
        else if (sum > 0) colorClass = "red";
        else colorClass = "gray";
    }

}

