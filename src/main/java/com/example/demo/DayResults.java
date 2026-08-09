package com.example.demo;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DayResults {

    private final Map<String, ExerciseResult> map = new LinkedHashMap<>();

    public void add(String exercise, int quantity, String comment) {
        map.computeIfAbsent(exercise, ExerciseResult::new)
                .add(quantity, comment);
    }

    public Collection<ExerciseResult> getExerciseResults(List<String> allExercises) {
        // добавляем пустые упражнения, если их нет
        for (String ex : allExercises) {
            map.computeIfAbsent(ex, ExerciseResult::new);
        }
        return map.values();
    }

    // Формат для страницы:
    // Отжимания: 2+5+8=15 | Приседания: 10+10=20
    public String formatQuantities() {
        return map.values().stream()
                .map(er -> er.getExerciseName() + ": " + er.formatQuantities())
                .collect(Collectors.joining(" <br> "));
    }

    public String formatComments() {
        return map.values().stream()
                .map(ExerciseResult::formatComments)
                .filter(s -> !s.endsWith(": ")) // если нет комментариев
                .collect(Collectors.joining(" ; "));
    }

    public int totalSum() {
        return map.values().stream()
                .mapToInt(ExerciseResult::getSum)
                .sum();
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    public String getEmptyText() {
        return "Нет результатов";
    }

}

