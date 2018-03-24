package pg.moonshot.streakwash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

/**
 * Created by Acer on 3/23/2018.
 */

public class ProfileActivity extends AppCompatActivity{

    ImageView iv_streak;
    ImageView iv_goals;
    ImageView iv_leaderboard;
    ImageView iv_rewards;
    ImageView iv_profile;

    private FirebaseAuth auth;
    Button btn_logout;
    TextView tv_email;
    TextView tv_username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        btn_logout = (Button)findViewById(R.id.btn_logout);
        tv_email = (TextView)findViewById(R.id.tv_email);
        tv_username = (TextView)findViewById(R.id.tv_username);

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

        auth = FirebaseAuth.getInstance();

        tv_email.setText(auth.getCurrentUser().getEmail());
        tv_username.setText(auth.getCurrentUser().getDisplayName());

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
//                    @Override
//                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//
//                    }
//                };

                auth.signOut();

                FirebaseUser user = auth.getCurrentUser();

//                Log.i("tag",user.toString());
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(getBaseContext(), LoginActivity.class));
                    finish();
                }
            }
        });

    }

}
