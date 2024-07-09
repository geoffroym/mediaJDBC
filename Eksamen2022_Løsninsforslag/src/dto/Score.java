package dto;

import java.sql.Timestamp;

public record Score(int id, User user, int score, Topic topic, Category category, Timestamp time) {

    @Override
    public String toString() {
        return String.format("%d - %s - Topic:%s - Category:%s - Time:%5$tY-%5$tm-%5$td %5$tH:%5$tM:%5$tS", score(), user.name(), topic.topic(), category.category(), time);
    }
}
