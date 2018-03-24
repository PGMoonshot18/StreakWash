package pg.moonshot.streakwash;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import pg.moonshot.streakwash.model.Streak;
import pg.moonshot.streakwash.model.StreakRecordViewHolder;
import pg.moonshot.streakwash.model.StreakService;

public class StreakActivity extends AppCompatActivity {
    public static final int PENDINGINTENT_WASH = 0;
    public static final int NOTIF_WASH = 0;

    ImageView iv_streak;
    ImageView iv_goals;
    ImageView iv_leaderboard;
    ImageView iv_rewards;
    ImageView iv_profile;

    TextView tv_daily_date;
    TextView tv_daily_count;

    TextView tv_weekly_date;
    TextView tv_weekly_count;

    TextView tv_monthly_count;
    TextView tv_monthly_date;

    RecyclerView rv_streaks;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();

    StreakAdapter firebaseStreakRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.streak);
        iv_streak = (ImageView) findViewById(R.id.iv_streak);
        iv_goals= (ImageView) findViewById(R.id.iv_goals);
        iv_leaderboard= (ImageView) findViewById(R.id.iv_leaderboard);
        iv_rewards = (ImageView) findViewById(R.id.iv_rewards);
        iv_profile = (ImageView) findViewById(R.id.iv_profile);


        tv_daily_count = (TextView) findViewById(R.id.tv_daily_count);
        tv_daily_date = (TextView) findViewById(R.id.tv_daily_date);
        tv_monthly_count = (TextView) findViewById(R.id.tv_monthly_count);
        tv_monthly_date = (TextView) findViewById(R.id.tv_monthly_date);
        tv_weekly_count = (TextView) findViewById(R.id.tv_weekly_count);
        tv_weekly_date = (TextView) findViewById(R.id.tv_weekly_date);

        rv_streaks = (RecyclerView) findViewById(R.id.rv_streaks);

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

                    /*Calendar yesterday = (Calendar) calendar.clone();
                    yesterday.add(Calendar.DATE,-1);
                    Log.d("yesterday", yesterday.toString());
                    if(StreakService.countDaily(streakList, yesterday, yesterday) == 0 &&
                            StreakService.countDaily(streakList, calendar, calendar) == 0) {
                        streakList = new ArrayList<>();
                        database.getReference("users").child(auth.getCurrentUser().getUid()).child("streaks").setValue(null);
                    }*/

                    int month = calendar.get(Calendar.MONTH);
                    String monthStr = "";
                    switch (month) {
                        case 0: monthStr = "January"; break;
                        case 1: monthStr = "February"; break;
                        case 2: monthStr = "March"; break;
                        case 3: monthStr = "April"; break;
                        case 4: monthStr = "May"; break;
                        case 5: monthStr = "June"; break;
                        case 6: monthStr = "July"; break;
                        case 7: monthStr = "August"; break;
                        case 8: monthStr = "September"; break;
                        case 9: monthStr = "October"; break;
                        case 10: monthStr = "November"; break;
                        case 11: monthStr = "December"; break;
                    }

                    tv_monthly_date.setText(monthStr.toUpperCase());

                    tv_daily_date.setText(monthStr.toUpperCase() + " " + calendar.get(Calendar.DAY_OF_MONTH));

                    Calendar start = (Calendar) calendar.clone();
                    start.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                    Calendar end = (Calendar) start.clone();
                    end.add(Calendar.DAY_OF_YEAR, 6);

                    tv_weekly_date.setText(monthStr.toUpperCase() + " " + start.get(Calendar.DAY_OF_MONTH) + " - " + StreakService.getMonth(start.get(Calendar.MONTH)) + " " + end.get(Calendar.DAY_OF_MONTH));



                    tv_daily_count.setText(StreakService.countDaily(streakList, calendar, calendar) + "");

                    tv_weekly_count.setText(StreakService.countDaily(streakList, start, end) + "");


                    start.set(Calendar.DAY_OF_MONTH, 1);

                    end.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

                    tv_monthly_count.setText(StreakService.countDaily(streakList, start , end) + "");

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        firebaseStreakRecord = new StreakAdapter();

        database.getReference("users").child(auth.getCurrentUser().getUid()).child("streaks").orderByChild("date")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ArrayList <JSONObject> records = new ArrayList<>();
                        for (DataSnapshot streak: dataSnapshot.getChildren()) {
                            Long count = (Long) streak.child("count").getValue();
                            String date = (String) streak.child("date").getValue();

                            JSONObject object = new JSONObject();
                            try {
                                object.put("streak", count);
                                object.put("date", date);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            records.add(object);
                            Log.d("StreakList", object.toString());
                        }

                        Collections.reverse(records);
                        firebaseStreakRecord.setData(records);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        rv_streaks.setAdapter(firebaseStreakRecord);
        rv_streaks.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false));

        iv_streak.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                finish();
                overridePendingTransition(0,0);
                Intent i = new Intent();
                i.setClass(getBaseContext(), StreakActivity.class);
                startActivity(i);
            }
        });

        iv_goals.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0,0);
                Intent i = new Intent();
                i.setClass(getBaseContext(), GoalActivity.class);
                startActivity(i);
            }
        });

        iv_leaderboard.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0,0);
                Intent i = new Intent();
                i.setClass(getBaseContext(), LeaderboardActivity.class);
                startActivity(i);
            }
        });

        iv_rewards.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0,0);
                Intent i = new Intent();
                i.setClass(getBaseContext(), RewardsActivity.class);
                startActivity(i);
            }
        });

        iv_profile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0,0);
                Intent i = new Intent();
                i.setClass(getBaseContext(), ProfileActivity.class);
                startActivity(i);
            }
        });

        createAlarm();

        iv_streak.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                StreakDialog sd = new StreakDialog();
                sd.show(getSupportFragmentManager(), "");

                return true;
            }
        });

    }

    public void cancelAlarm() {
        AlarmManager alarmManager
                = (AlarmManager) getSystemService(Service.ALARM_SERVICE);
        Intent broadcastIntent = new Intent(getBaseContext(), AlarmWashReceiver.class);
        PendingIntent bcPI
                = PendingIntent.getBroadcast(getBaseContext(),
                PENDINGINTENT_WASH, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.cancel(bcPI);
        bcPI.cancel();
    }

    public void createAlarm() {
        AlarmManager alarmManager
                = (AlarmManager) getBaseContext().getSystemService(Service.ALARM_SERVICE);
        Intent broadcastIntent = new Intent(getBaseContext(), AlarmWashReceiver.class);
        PendingIntent bcPI
                = PendingIntent.getBroadcast(getBaseContext(),
                StreakActivity.PENDINGINTENT_WASH, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis() + 2*1000*60,
                bcPI);
    }
}
