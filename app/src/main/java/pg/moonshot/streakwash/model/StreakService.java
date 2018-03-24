package pg.moonshot.streakwash.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by NGO on 24/03/2018.
 */

public class StreakService {
    public static int countDaily (List <JSONObject> streaks, Calendar start, Calendar end) {
        int count = 0;
        Calendar startDay = (Calendar) start.clone();
        do {

            for (JSONObject streak: streaks) {
                try {

                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    String date = streak.get("date").toString();


                    Log.d("Date", date);
                    Log.d("start", format.format(startDay.getTime()));
                    if (date.equals(format.format(startDay.getTime()))) {
                        count += Integer.parseInt(streak.get("count").toString());
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            startDay.add(Calendar.DAY_OF_YEAR, 1);
        } while (startDay.getTimeInMillis() < end.getTimeInMillis());

        return count;
    }

    public static String getMonth (int month) {
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

        return monthStr;
    }

    public static String getMonthPlusOne(int i) {String monthStr = "";
        switch (i) {
            case 1: monthStr = "January"; break;
            case 2: monthStr = "February"; break;
            case 3: monthStr = "March"; break;
            case 4: monthStr = "April"; break;
            case 5: monthStr = "May"; break;
            case 6: monthStr = "June"; break;
            case 7: monthStr = "July"; break;
            case 8: monthStr = "August"; break;
            case 9: monthStr = "September"; break;
            case 10: monthStr = "October"; break;
            case 11: monthStr = "November"; break;
            case 12: monthStr = "December"; break;
        }

        return monthStr;
    }
}
