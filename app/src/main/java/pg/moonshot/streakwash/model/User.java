package pg.moonshot.streakwash.model;

import java.util.ArrayList;

/**
 * Created by NGO on 23/03/2018.
 */

public class User {
    private String id;
    private String username;
    private String email;
    private String password;
    private ArrayList<Streak> streaks;
    private ArrayList<UserGoal> goals;
    private float germCount;
    private int points;

    public User() {}

    public User(String id, String username, String email, String password, ArrayList<Streak> streaks, ArrayList<UserGoal> goals, float germCount, int points) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.streaks = streaks;
        this.goals = goals;
        this.germCount = germCount;
    }

    public User (String id, String username, ArrayList<Streak> streaks, ArrayList<UserGoal> goals, float germCount, int points) {
        this.id = id;
        this.username = username;
        this.streaks = streaks;
        this.goals = goals;
        this.germCount = germCount;
        this.points = points;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Streak> getStreaks() {
        return streaks;
    }

    public void setStreaks(ArrayList<Streak> streaks) {
        this.streaks = streaks;
    }

    public ArrayList<UserGoal> getGoals() {
        return goals;
    }

    public void setGoals(ArrayList<UserGoal> goals) {
        this.goals = goals;
    }

    public float getGermCount() {
        return germCount;
    }

    public void setGermCount(float germCount) {
        this.germCount = germCount;
    }
}
