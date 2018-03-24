package pg.moonshot.streakwash;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Random;

/**
 * Created by Acer on 3/24/2018.
 */

public class StreakDialogSuccess extends android.support.v4.app.DialogFragment {

    Button btn_ok;
    TextView tv_trivia;

    String[] trivias = {"It was observed that 64% of females DID NOT wash their hands AT ALL after using the mall toilet",
        "7 out of 10 Filipinos claim to wash their hands with soap after using the toilet but in reality, only 2 out of 10 really do.",
        "It was observed that 60% of females washed their hands with water ONLY before cooking.",
        "Before feeding the kids, 40% washed their hands with water ONLY.",
        "80% of Filipinos don’t wash their hands after shaking hands with another person",
        "40% of Filipinos don’t wash their hands before touching young children",
        "49% don’t wash their hands after using their mobile phones",
        "50% don’t wash their hands after having traveled on public transportation",
        "66% of Filipino have noticed or witnessed children not washing their hands after using the toilet",
        "In the office, 31% have noticed that their co-workers don’t wash their hands after using the toilet",
        "70% don’t wash their hands at all after sneezing or coughing}"
    };

    public Dialog onCreateDialog(Bundle savedInstanceState){
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_streak_success, null);
        tv_trivia = (TextView)v.findViewById(R.id.tv_trivia_wash);
        btn_ok = (Button)v.findViewById(R.id.btn_ok);
        Random rand = new Random();

        int  n = rand.nextInt(11) + 0;

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setView(v);
        tv_trivia.setText(trivias[n]);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        return builder.create();
    }

}
