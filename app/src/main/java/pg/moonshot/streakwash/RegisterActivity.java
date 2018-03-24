package pg.moonshot.streakwash;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import pg.moonshot.streakwash.model.Streak;
import pg.moonshot.streakwash.model.User;
import pg.moonshot.streakwash.model.UserGoal;

public class RegisterActivity extends AppCompatActivity {

    EditText et_email;
    EditText et_username;
    EditText et_password;
    Button btn_register;
    TextView tv_back_login;

    private FirebaseAuth auth;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        auth = FirebaseAuth.getInstance();

        et_email = (EditText) findViewById(R.id.et_email);
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_register = (Button) findViewById(R.id.btn_register);
        tv_back_login = (TextView) findViewById(R.id.tv_back_login);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = et_email.getText().toString().trim();
                final String username = et_username.getText().toString().trim();
                String password = et_password.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(getApplicationContext(), "Enter username!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(RegisterActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        } else {

                            FirebaseUser user = auth.getCurrentUser();
                            if(user!=null){
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(username).build();
                                user.updateProfile(profileUpdates);
                            }

                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            Toast.makeText(RegisterActivity.this, "Registered user.",
                                    Toast.LENGTH_SHORT).show();

                            ArrayList<UserGoal> goals = new ArrayList<UserGoal>();
                            for(int i=1;i<=3;i++) {
                                goals.add(new UserGoal("Goal" + i, 0));
                            }

                            ArrayList<Streak> streaks = new ArrayList<Streak>();
                            User u = new User(auth.getCurrentUser().getUid(), et_username.getText().toString(), streaks, goals, 0, 0);
                            databaseReference.child("users").child(auth.getCurrentUser().getUid()).setValue(u);
                            finish();

                        }
                    }
                });

            }
        });
    }
}
