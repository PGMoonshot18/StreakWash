package pg.moonshot.streakwash.model;

/**
 * Created by Jonal on 3/23/2018.
 */

public class UserGoal {
    private String goalId;
    private int progress;

    public UserGoal () { }

    public UserGoal(String goalId, int progress) {
        this.goalId = goalId;
        this.progress = progress;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getGoalId() {
        return goalId;
    }

    public void setGoalId(String goalId) {
        this.goalId = goalId;
    }
}
