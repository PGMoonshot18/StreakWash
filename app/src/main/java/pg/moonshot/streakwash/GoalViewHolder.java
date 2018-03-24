package pg.moonshot.streakwash;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class GoalViewHolder extends RecyclerView.ViewHolder {
    TextView tvGoalTitle;
    TextView tvGoalDesc;
    ProgressBar pbGoalProgress;

    public GoalViewHolder(View itemView) {
        super(itemView);
        tvGoalTitle = (TextView) itemView.findViewById(R.id.tv_goal_title);
        tvGoalDesc = (TextView) itemView.findViewById(R.id.tv_goal_desc);
        pbGoalProgress = (ProgressBar) itemView.findViewById(R.id.pb_goal_progress);
    }
}
