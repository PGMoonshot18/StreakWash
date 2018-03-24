package pg.moonshot.streakwash.model;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import pg.moonshot.streakwash.R;

/**
 * Created by NGO on 11/21/2017.
 */

public class StreakRecordViewHolder extends RecyclerView.ViewHolder {
    public TextView tvOccurence;
    public TextView tvDate;
    public TextView tvStreak;
    public TextView tvStreakDifference;

    public StreakRecordViewHolder(View itemView) {
        super(itemView);
        tvDate = (TextView) itemView.findViewById(R.id.tv_occurrence);
        tvOccurence = (TextView) itemView.findViewById(R.id.tv_date);

        tvStreakDifference = (TextView) itemView.findViewById(R.id.tv_streak_difference);

        tvStreak = (TextView) itemView.findViewById(R.id.tv_streak);


    }
}
