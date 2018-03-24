package pg.moonshot.streakwash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class LeaderboardActivity extends AppCompatActivity {

    ImageView iv_streak;
    ImageView iv_goals;
    ImageView iv_leaderboard;
    ImageView iv_rewards;
    ImageView iv_profile;

    LinearLayout ll_daily;
    LinearLayout ll_weekly;
    LinearLayout ll_monthly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard);

        ll_daily = (LinearLayout) findViewById(R.id.ll_daily);
        ll_weekly = (LinearLayout) findViewById(R.id.ll_weekly);
        ll_monthly = (LinearLayout) findViewById(R.id.ll_monthly);

        iv_streak = (ImageView) findViewById(R.id.iv_streak);
        iv_goals= (ImageView) findViewById(R.id.iv_goals);
        iv_leaderboard= (ImageView) findViewById(R.id.iv_leaderboard);
        iv_rewards = (ImageView) findViewById(R.id.iv_rewards);
        iv_profile = (ImageView) findViewById(R.id.iv_profile);

//        ll_daily.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent();
//                i.setClass(getBaseContext(), StreakActivity.class);
//                startActivity(i);
//            }
//        });

        iv_streak.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
                Intent i = new Intent();
                i.setClass(getBaseContext(), StreakActivity.class);
                startActivity(i);
            }
        });

        iv_goals.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
                Intent i = new Intent();
                i.setClass(getBaseContext(), GoalActivity.class);
                startActivity(i);
            }
        });

        iv_leaderboard.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
                Intent i = new Intent();
                i.setClass(getBaseContext(), LeaderboardActivity.class);
                startActivity(i);
            }
        });

        iv_rewards.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
                Intent i = new Intent();
                i.setClass(getBaseContext(), RewardsActivity.class);
                startActivity(i);
            }
        });

        iv_profile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
                Intent i = new Intent();
                i.setClass(getBaseContext(), ProfileActivity.class);
                startActivity(i);
            }
        });

        iv_streak.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                StreakDialog sd = new StreakDialog();
                sd.show(getSupportFragmentManager(), "");

                return true;
            }
        });
    }
}
