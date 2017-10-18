package sliitassisme.assistmemain.FirstTime;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import sliitassisme.assistmemain.Alarm.Alarm_Receiver;
import sliitassisme.assistmemain.Alarm.weathercomp.model.BadWeather;
import sliitassisme.assistmemain.Alarm.weathercomp.model.Weather;
import sliitassisme.assistmemain.MainActivity;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by DELL on 10/17/2017.
 */

public class CallRecivers extends BroadcastReceiver {

    int alarm_tracks=2;


    private boolean weatherCondition;
    private String myAppId = "dcb6553bfccc040683d9917eedd6cfbe";
    Weather weather = new Weather();
    BadWeather badWeather = new BadWeather();
    public List<String> myList = new ArrayList<>();


    @Override
    public void onReceive(Context context, Intent intent) {

        //renderWeatherData("Colombo,LK");
        String time=MainActivity.DATABASEHANDLER.databasetostringTimeSedule(currntDay());
        String[] Time = time.split("#");
        myList = Arrays.asList(Time);
        int hour=Integer.parseInt(myList.get(0));
        String aa="("+myList.get(0)+"#"+myList.get(1)+")";
        int minute = Integer.parseInt(myList.get(1));
        Toast.makeText(context,aa,Toast.LENGTH_LONG).show();
        //int hour=01;
        //int minute=00;
        int minute1=1+minute;


        Log.v("Isha","Call Reciver Start");
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Intent notificationIntent = new Intent("android.media.action.DISPLAY_NOTIFICATION");
        notificationIntent.addCategory("android.intent.category.DEFAULT");
        PendingIntent broadcast = PendingIntent.getBroadcast(context, 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar cal = Calendar.getInstance();
        //cal.add(Calendar.SECOND, 20);
        /*cal.set(Calendar.HOUR_OF_DAY,22);
        cal.set(Calendar.MINUTE,00);
        cal.set(Calendar.SECOND,00);*/
        cal.set(Calendar.MINUTE,minute1);
        cal.set(Calendar.HOUR_OF_DAY,hour);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast);




        //Alrm




       /* if(weatherCondition){
            minute=minute-15;
            if(minute<0){
                minute=60+minute;
                hour=hour-1;
            }

        }*/



        AlarmManager alarmManager1 = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Intent AlrmRsiver = new Intent(context, Alarm_Receiver.class );
        AlrmRsiver.putExtra("extra", "alarm on");//tells the clock that the alarm on button is pressed, putting extra string to my_intent
        AlrmRsiver.putExtra("alarm tone", alarm_tracks);
        notificationIntent.addCategory("android.intent.category.DEFAULT");
        PendingIntent broadcast1 = PendingIntent.getBroadcast(context, 100, AlrmRsiver, PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar cal1 = Calendar.getInstance();
        //cal1.add(Calendar.SECOND, 10);
        cal1.set(Calendar.MINUTE,minute);
        cal1.set(Calendar.HOUR_OF_DAY,hour);
        //cal.set(Calendar.MINUTE,00);
        //cal.set(Calendar.SECOND,00);
        alarmManager1.setExact(AlarmManager.RTC_WAKEUP, cal1.getTimeInMillis(), broadcast1);



    }

    public String currntDay(){
        String DAY="";
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        switch (day) {
            case Calendar.SUNDAY:
                DAY="SUN";
                break;
            case Calendar.MONDAY:
                DAY="MON";
                break;
            case Calendar.TUESDAY:
                DAY="TUE";
                break;
            case Calendar.WEDNESDAY:
                DAY="WED";
                break;
            case Calendar.THURSDAY:
                DAY="THU";
                break;
            case Calendar.FRIDAY:
                DAY="FRI";
                break;
            case Calendar.SATURDAY:
                DAY="SAT";
                break;
        }
        return DAY;
    }

}
