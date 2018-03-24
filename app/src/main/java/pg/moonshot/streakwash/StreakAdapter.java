package pg.moonshot.streakwash;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

import pg.moonshot.streakwash.model.StreakRecordViewHolder;
import pg.moonshot.streakwash.model.StreakService;

/**
 * Created by NGO on 05/10/2017.
 */

public class StreakAdapter extends RecyclerView.Adapter<StreakRecordViewHolder> {

    ArrayList<JSONObject> data = new ArrayList<>();

    public void setData(ArrayList<JSONObject> data)
    {
        this.data = data;
        this.notifyDataSetChanged();
    }

    @Override
    public StreakRecordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate the layout item_restaurant
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_records, parent, false);

        return new StreakRecordViewHolder(v);
    }

    @Override
    public void onBindViewHolder(StreakRecordViewHolder holder, int position) {
        // we put in the data at this position
        JSONObject currentRecord = data.get(position);

        try {
            holder.tvStreakDifference.setText("");
            holder.tvStreak.setText(currentRecord.get("streak").toString());
            String [] date = currentRecord.get("date").toString().split("-");
            String str = StreakService.getMonthPlusOne(Integer.parseInt(date[1])) + " " + Integer.parseInt(date[2]) + " " + Integer.parseInt(date[0]);
            holder.tvOccurence.setText(str);
            holder.tvDate.setText("");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
