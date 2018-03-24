package pg.moonshot.streakwash.model;

import java.time.LocalDate;

/**
 * Created by NGO on 23/03/2018.
 */

public class Streak {
    @Override
    public String toString() {
        return "Streak{" +
                "date=" + date +
                ", count=" + count +
                '}';
    }

    private LocalDate date;
    private int count;

    public Streak() {}

    public Streak(LocalDate date, int count) {
        this.date = date;
        this.count = count;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
