package myProject.workout;

public class SportDto {
    private final String name;
    private final String comment;
    private final Integer quantity;
    private final String createdAt;

    public SportDto(String name, String comment, Integer quantity, String createdAt) {
        this.name = name;
        this.comment = comment;
        this.quantity = quantity;
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
