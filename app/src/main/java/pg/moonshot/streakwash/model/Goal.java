package pg.moonshot.streakwash.model;

/**
 * Created by NGO on 23/03/2018.
 */

public class Goal {

    private String id;
    private String title;
    private int total;

    public Goal() {}

    public Goal(String id, String title, int total) {
        this.id = id;
        this.title = title;
        this.total = total;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }



}
