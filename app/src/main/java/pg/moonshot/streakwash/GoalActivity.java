package pg.moonshot.streakwash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import pg.moonshot.streakwash.model.StreakService;
import pg.moonshot.streakwash.model.User;
import pg.moonshot.streakwash.model.UserGoal;
import pg.moonshot.streakwash.model.Goal;

public class GoalActivity extends AppCompatActivity {
    RecyclerView rvGoals, rvCompleted;
    String userId;
    DatabaseReference databaseReference, goalReference;

    private FirebaseAuth auth;

    ImageView iv_streak;
    ImageView iv_goals;
    ImageView iv_leaderboard;
    ImageView iv_rewards;
    ImageView iv_profile;

    int numWashed = 0;


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goal);

        auth = FirebaseAuth.getInstance();
        iv_streak = (ImageView) findViewById(R.id.iv_streak);
        iv_goals= (ImageView) findViewById(R.id.iv_goals);
        iv_leaderboard= (ImageView) findViewById(R.id.iv_leaderboard);
        iv_rewards = (ImageView) findViewById(R.id.iv_rewards);
        iv_profile = (ImageView) findViewById(R.id.iv_profile);

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

        rvGoals = (RecyclerView) findViewById(R.id.rv_goals);
        userId = auth.getCurrentUser().getUid();     // User Id is dependent on logged user
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("goals");

        database.getReference("users").child(auth.getCurrentUser().getUid()).child("streaks")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<JSONObject> streakList = new ArrayList<>();
                        for (DataSnapshot streak: dataSnapshot.getChildren()) {
                            JSONObject object = new JSONObject();
                            try {
                                object.put("count", streak.child("count").getValue());
                                object.put("date", streak.child("date").getValue());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            streakList.add(object);
                            Log.d("StreakList", object.toString());
                        }

                        Calendar calendar = Calendar.getInstance();
                        DateFormat format = new SimpleDateFormat("yyyy-dd-mm");

                        numWashed = StreakService.countDaily(streakList, calendar, calendar);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        FirebaseRecyclerAdapter<UserGoal, GoalViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<UserGoal, GoalViewHolder>(
                UserGoal.class, R.layout.item_goal, GoalViewHolder.class, databaseReference
        ) {
            @Override
            protected void populateViewHolder(final GoalViewHolder viewHolder, UserGoal model, int position) {
                goalReference = FirebaseDatabase.getInstance().getReference().child("goal").child(model.getGoalId());

                ValueEventListener goalListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Goal goal = dataSnapshot.getValue(Goal.class);
                        viewHolder.tvGoalTitle.setText(goal.getTitle());
                        viewHolder.tvGoalDesc.setText("You have to wash your hands " +numWashed + "/" + goal.getTotal() + " times today.");
                        viewHolder.pbGoalProgress.setMax(goal.getTotal());

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                };
                goalReference.addValueEventListener(goalListener);
                viewHolder.pbGoalProgress.setProgress(numWashed);
            }
        };

        rvGoals.setAdapter(firebaseRecyclerAdapter);
        rvGoals.setLayoutManager(new LinearLayoutManager(getBaseContext()));
    }
}