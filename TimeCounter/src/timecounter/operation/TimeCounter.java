package timecounter.operation;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import timecounter.Timer;
import timecounter.TimerStop;

public class TimeCounter {

    private TimerStop stop = null;

    public void TimeCounterThread(Date myDate, Timer timer) {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Calendar derminedTime = Calendar.getInstance();
        derminedTime.setTime(myDate);
        long oneHour = 1000 * 60 * 60;
        Runnable r = () -> {
            try {
                while (true) {
                    Calendar currentTime = Calendar.getInstance();
                    Calendar timeBetween = Calendar.getInstance();
                    long time = derminedTime.getTimeInMillis() - currentTime.getTimeInMillis() - oneHour;
                    Date d = new Date(time);
                    timeBetween.setTime(d);
                    timer.runned.jLabel2.setText(dateFormat.format(timeBetween.getTime()));
                    //show window to close application before shutdown a system
                    if (timeBetween.get(Calendar.HOUR) == 0 && timeBetween.get(Calendar.MINUTE) == 0
                            && timeBetween.get(Calendar.SECOND) <= 15) {
                        if (this.stop == null) {
                            this.stop = new TimerStop();
                        }
                        this.stop.jLabel1.setText("" + timeBetween.get(Calendar.SECOND));
                    }
                    if (!currentTime.before(derminedTime)) {
                        CloseSystem close = new CloseSystem();
                        close.shutdownSystem();
                        break;
                    }
                    Thread.sleep(1000);
                }
            } catch (Exception e) {
                System.out.println("TimeCounterThread-error: " + e);
            }
        };
        Thread tr = new Thread(r);
        tr.start();
    }
}
