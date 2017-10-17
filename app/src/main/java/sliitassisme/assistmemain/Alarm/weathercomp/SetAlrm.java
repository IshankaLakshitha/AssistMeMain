package sliitassisme.assistmemain.Alarm.weathercomp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import sliitassisme.assistmemain.Alarm.Alarm_Receiver;
import sliitassisme.assistmemain.Alarm.weathercomp.data.JSONWeatherParser;
import sliitassisme.assistmemain.Alarm.weathercomp.data.WeatherHttpClient;
import sliitassisme.assistmemain.Alarm.weathercomp.model.BadWeather;
import sliitassisme.assistmemain.Alarm.weathercomp.model.Weather;

/**
 * Created by DELL on 10/16/2017.
 */

public class SetAlrm extends AppCompatActivity{


    AlarmManager alarm_manager;
    TimePicker alarm_time_picker;
    TextView update_text;
    Context context;
    PendingIntent pending_intent;
    int alarm_tracks;
    DateFormat df;
    Date time1, time2;
    long delay = 900000;
    String timeNow;
    EditText city,country;
    String parsedata="";

    //Weather >>>>>>>>>>>>>>>>>>>>>>>
    private boolean weatherCondition;
    private String myAppId = "dcb6553bfccc040683d9917eedd6cfbe";
    Weather weather = new Weather();
    BadWeather badWeather = new BadWeather();

    public void Alrm(int min,int hr){
        alarm_manager = (AlarmManager)getSystemService(ALARM_SERVICE);
        //alarm_time_picker = (TimePicker)findViewById(R.id.timePicker);
        //update_text = (TextView)findViewById(R.id.update_alarm);
        final Calendar calendar = Calendar.getInstance(); //Create an instance of the calendar
        final Intent my_intent = new Intent(this.context, Alarm_Receiver.class);


        renderWeatherData("Colombo,LK");

        int minute=30;
        int hour=12;
        /*
        if(weatherCondition){
            minute=minute-15;
            if(minute<0){
                minute=60+minute;
                hour=hour-1;
            }

        }
        */


        calendar.set(Calendar.HOUR_OF_DAY,hour );//set calendar instance with hours and minutes on the time picker
        calendar.set(Calendar.MINUTE, minute);



        String hour_string = String.valueOf(hour);
        String minute_string = String.valueOf(minute);

        if (hour > 12){

            hour_string = String.valueOf(hour - 12);
        }

        if (minute < 10){

            minute_string = "0" +String.valueOf(minute);

        }

        //set_alarm_text("Alarm Set to " +hour_string+":" +minute_string);//changes the text in the update text box

        my_intent.putExtra("extra", "alarm on");//tells the clock that the alarm on button is pressed, putting extra string to my_intent

        my_intent.putExtra("alarm tone", alarm_tracks);//tell the app that you want a certain value from the spinner

        Log.e("The alarm id is", String.valueOf(alarm_tracks));

        pending_intent = PendingIntent.getBroadcast(SetAlrm.this, 0,
                my_intent, PendingIntent.FLAG_UPDATE_CURRENT);//Create a pending intent

        alarm_manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending_intent );
    }

    public void renderWeatherData(String city){

        WeatherTask weatherTask = new WeatherTask();
        weatherTask.execute(new String[]{city+"&appid="+myAppId}); //FIX if needed
        //weatherTask.execute(new String[]{city+"&appid=dcb6553bfccc040683d9917eedd6cfbe"});

    }

    private class WeatherTask extends AsyncTask<String, Void, Weather> {

        @Override
        protected Weather doInBackground(String... params) {
            //data hold the whole StringBuffer that we returned from WeatherHttpClient class
            String data = ((new WeatherHttpClient()).getWeatherData(params[0]));
            weather = JSONWeatherParser.getWeather(data);

            //Log.v("Data : ",weather.place.getCity());
            //Log.v("Data : ",weather.currentCondition.getDescription());
            String weatherSample = weather.currentCondition.getDescription();

            if ((badWeather.isCloudy(weatherSample)) || (badWeather.isRaining(weatherSample))){
                weatherCondition = true;
                Log.v("Good or Bad : true ", String.valueOf(weatherCondition));
            }
            else{
                weatherCondition = false;
                Log.v("Good or Bad : false ", String.valueOf(weatherCondition));
            }

            Log.v("Good or Bad : ", weather.currentCondition.getDescription());

            return weather;
        }

        @Override
        protected void onPostExecute(Weather weather) {
            super.onPostExecute(weather);
        }
    }
}
