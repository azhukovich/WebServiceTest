package myProject.workout;

public class MessageDto {
    private final String text;
    private final String createdAt;

    public MessageDto(String text, String createdAt) {
        this.text = text;
        this.createdAt = createdAt;
    }

    public String getText() {
        return text;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
