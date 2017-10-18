package sliitassisme.assistmemain;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.Calendar;

import sliitassisme.assistmemain.FirstTime.CallRecivers;
import sliitassisme.assistmemain.FirstTime.FirstTimeDevicesActivity;
import sliitassisme.assistmemain.Health.HealthMain;
import sliitassisme.assistmemain.database.DBhandler;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static DBhandler DATABASEHANDLER;
    public static int firsttime=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        DATABASEHANDLER=new DBhandler(this,null,null,1);
        //DATABASEHANDLER.adduserDetails("11","22","152","53","121");
        //Intent intent1 = new Intent(this, FirstTimeDevicesActivity.class);
        //startActivity(intent1);
        if(isFirstTime()) {
            Intent intent = new Intent(this, FirstTimeDevicesActivity.class);
            startActivity(intent);
            firsttime=1;
        }else{
            firsttime=0;

            //btndone.setVisibility(View.INVISIBLE);
        }
        setAlrm();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_alarms) {

        } else if (id == R.id.nav_items) {
            Intent intent = new Intent(this, FirstTimeDevicesActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_health) {
            Intent intent = new Intent(this, HealthMain.class);
            startActivity(intent);
        } else if (id == R.id.nav_navigation) {

        } else if (id == R.id.nav_settings) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean isFirstTime()
    {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        boolean ranBefore = preferences.getBoolean("RanBefore", false);
        if (!ranBefore) {
            // first time
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("RanBefore", true);
            editor.commit();
        }
        return !ranBefore;
    }

    public void ItemComponant(View view) {
        Intent intent = new Intent(this, FirstTimeDevicesActivity.class);
        startActivity(intent);
    }

    public void healthComponant(View view) {
        Intent intent = new Intent(this, HealthMain.class);
        startActivity(intent);
    }

    public void setAlrm(){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent notificationIntent = new Intent(this, CallRecivers.class);
        //notificationIntent.addCategory("android.intent.category.DEFAULT");
        PendingIntent broadcast = PendingIntent.getBroadcast(this, 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, 60);
        //cal.add(Calendar.DAY_OF_WEEK,day);
        /*cal.set(Calendar.HOUR_OF_DAY,22);
        cal.set(Calendar.MINUTE,00);
        cal.set(Calendar.SECOND,00);*/
        //alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, broadcast);
    }


}
