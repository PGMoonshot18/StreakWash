package pg.moonshot.streakwash;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Acer on 3/24/2018.
 */

public class StreakDialog extends android.support.v4.app.DialogFragment {

    private Button btn_yes;
    private Button btn_no;
    private int numStreak = 0;

    DatabaseReference databaseReference;

    private FirebaseAuth auth;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_streak, null);
        btn_yes = (Button)v.findViewById(R.id.btn_yes);
        btn_no = (Button)v.findViewById(R.id.btn_no);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setView(v);

        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        auth = FirebaseAuth.getInstance();
        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StreakDialogSuccess sds = new StreakDialogSuccess();
                sds.show(getFragmentManager(), "");

                final DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                final String userId = auth.getCurrentUser().getUid();     // User Id is dependent on logged user
                FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("streaks")
                        .limitToFirst(1).orderByChild("date").equalTo(format.format(new Date())).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String key = dataSnapshot.getKey().toString();

                                if(dataSnapshot.getValue() == null) {
                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("streaks").push();
                                    ref.child("count").getRef().setValue(Integer.parseInt(String.valueOf(1)));
                                    ref.child("date").getRef().setValue(format.format(new Date()));

                                } else {
                                    for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                                        numStreak = Integer.parseInt(dataSnapshot1.child("count").getValue().toString()) + 1;
                                        FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("streaks").child(dataSnapshot1.getKey()).child("count")
                                                .limitToFirst(1).orderByChild("date").getRef()
                                                .setValue(numStreak);
                                        FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("streaks").child(dataSnapshot1.getKey()).child("date")
                                                .setValue(dataSnapshot1.child("date").getValue());

                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("points")
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.d("snap",dataSnapshot.toString());
                        if(dataSnapshot.getValue() == null) {
                            FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("points").setValue(5);

                        } else {
                            if(numStreak <= 3) {
                                int value = Integer.parseInt(dataSnapshot.getValue().toString()) + 5;
                                FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("points").setValue(value);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

//                databaseReference.setValue()

                dismiss();
            }
        });

        return builder.create();
    }
}
