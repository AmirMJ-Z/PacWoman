package model;

public class User {
    private int score;
    private String username;
    public User(String username) {
        this.username = username;
        this.score = 0;
    }

    User(String username, int score) {
        this.username = username;
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public String getUsername() {
        return username;
    }
}
