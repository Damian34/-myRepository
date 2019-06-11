package timecounter.operation;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeFormat {

    public boolean formatCheck(String str) {
        try {
            String[] time = str.split(":");
            int hour = Integer.valueOf(time[0]);
            int min = Integer.valueOf(time[1]);
            if (hour >= 0 && hour <= 23 && min >= 0 && min <= 59) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    public Date getDateByString(String str) {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Calendar toDay = Calendar.getInstance();
        try {
            Calendar derminedTime = Calendar.getInstance();
            Calendar myHour = Calendar.getInstance();
            myHour.setTime(dateFormat.parse(str));

            derminedTime.set(Calendar.HOUR_OF_DAY, myHour.get(Calendar.HOUR_OF_DAY));
            derminedTime.set(Calendar.MINUTE, myHour.get(Calendar.MINUTE));
            derminedTime.set(Calendar.SECOND, 0);
            derminedTime.set(Calendar.MILLISECOND, 0);
            
            if (derminedTime.before(toDay)) {
                derminedTime.set(Calendar.DAY_OF_MONTH, derminedTime.get(Calendar.DAY_OF_MONTH) + 1);
            }
            return derminedTime.getTime();
        } catch (ParseException e) {
        }
        return null;
    }
}
