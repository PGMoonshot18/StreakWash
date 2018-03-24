package pg.moonshot.streakwash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Acer on 3/23/2018.
 */

public class RewardsActivity extends AppCompatActivity {

    ImageView iv_streak;
    ImageView iv_goals;
    ImageView iv_leaderboard;
    ImageView iv_rewards;
    ImageView iv_profile;
    TextView tv_points;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rewards);
        iv_streak = (ImageView) findViewById(R.id.iv_streak);
        iv_goals= (ImageView) findViewById(R.id.iv_goals);
        iv_leaderboard= (ImageView) findViewById(R.id.iv_leaderboard);
        iv_rewards = (ImageView) findViewById(R.id.iv_rewards);
        iv_profile = (ImageView) findViewById(R.id.iv_profile);
        tv_points = (TextView) findViewById(R.id.tv_points);

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

        auth = FirebaseAuth.getInstance();
        final String userId = auth.getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("points")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.d("snap",dataSnapshot.toString());
                        if(dataSnapshot.getValue() == null) {
                            tv_points.setText("0");

                        } else {
                            int value = Integer.parseInt(dataSnapshot.getValue().toString());
                            Log.d("points",value+"");
                            tv_points.setText(value+"");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
}
