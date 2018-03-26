package com.example.rohan.hau;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.User;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Dummy_activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    TextView v;
    ArrayList<Reading> read=new ArrayList<>();
    ArrayList<BarEntry> BarEntry = new ArrayList<>();
    ArrayList<String> labels = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy_activity);
        //v=(TextView)findViewById(R.id.consumer);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout1);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        FirebaseDatabase.getInstance().getReference().child("raspberry").child("admno6512")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Reading re=snapshot.getValue(Reading.class);
                            read.add(re);


                            //Toast.makeText(Dummy_activity.this,""+re.time+re.value,Toast.LENGTH_LONG).show();
                        }
//                        v.setText("the data is ready to be displayed");

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }
    Ringtone r;
    private void addNotification() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        .setContentTitle("Notifications Example")
                        .setContentText("This is a test notification");

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

    /*void graph()
    {
       // BarChart chart = (BarChart) findViewById(R.id.bar_chart);
        ArrayList<BarEntry> BarEntry = new ArrayList<>();
        BarEntry.add(new BarEntry(2f, 0));
        BarEntry.add(new BarEntry(4f, 1));
        BarEntry.add(new BarEntry(6f, 2));
        BarEntry.add(new BarEntry(8f, 3));
        BarEntry.add(new BarEntry(7f, 4));
        BarEntry.add(new BarEntry(3f, 5));

        BarDataSet dataSet = new BarDataSet(BarEntry,"Projects");
        ArrayList<String> labels = new ArrayList<>();
        labels.add("Jan");
        labels.add("Feb");
        labels.add("Mar");
        labels.add("Apr");
        labels.add("May");
        labels.add("Jun");

        BarData data = new BarData(labels,dataSet);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        //chart.setData(data);
        //chart.setDescription("No of Projects");



    }*/


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        //Ringtone r = null;

        if (id == R.id.nav_camera) {
            //startActivity(new Intent(Dummy_activity.this,MainActivity.class));
            startActivity(new Intent(Dummy_activity.this,MainActivity.class));
          //  v.setText("you have been pressed");
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();


        } else if (id == R.id.nav_slideshow) {
            r.stop();

        } else if (id == R.id.nav_manage) {
          //  v.setText("");
            int k=read.size();
            for(int i=0;i<k;i++)
            //    v.setText(""+v.getText()+"\n"+read.get(i).time+read.get(i).value);


        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(Dummy_activity.this,Main2Activity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout1);
        drawer.closeDrawer(GravityCompat.START);
        int a;
        //hai my first commit
        return true;

    }
    public void day(View v)
    {
        display_day("02");
        bar_graph();
    }
    //plotgraph bargraph
    public void bar_graph()
    {
        BarChart chart = (BarChart) findViewById(R.id.barchart);
        BarDataSet dataSet = new BarDataSet(BarEntry,"UNITS");
        //labels.clear();BarEntry.clear();
        BarData data = new BarData(labels,dataSet);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        chart.setData(data);
        chart.setDescription("HOURS");

    }
    public void display_day(String day) {
        BarEntry.clear();
        labels.clear();
        int c = 0;

        int k = read.size(), newreading;
        for(int i=1;i<k;i++)
        {

            if(read.get(i).time.split(":")[1].equals(day.split("/")[0])&&read.get(i).time.split(":")[2].equals(day.split("/")[1]))
            {
                newreading = (read.get(i).value) - read.get(i-1).value;
                BarEntry.add(new BarEntry(newreading,c));
                labels.add(new String("" + read.get(i).time.split(":")[3]));
                c++;
            }
        }
    }

}
    // first bar graph
   /* BarChart chart = (BarChart) findViewById(R.id.bar_chart);
    ArrayList<BarEntry> BarEntry = new ArrayList<>();
        BarEntry.add(new BarEntry(2f, 0));
                BarEntry.add(new BarEntry(4f, 1));
                BarEntry.add(new BarEntry(6f, 2));
                BarEntry.add(new BarEntry(8f, 3));
                BarEntry.add(new BarEntry(7f, 4));
                BarEntry.add(new BarEntry(3f, 5));

                BarDataSet dataSet = new BarDataSet(BarEntry,"Projects");
                ArrayList<String> labels = new ArrayList<>();
        labels.add("Jan");
        labels.add("Feb");
        labels.add("Mar");
        labels.add("Apr");
        labels.add("May");
        labels.add("Jun");
        labels.add("Jul");
        labels.add("Aug");
        labels.add("Sep");
        labels.add("Oct");
        labels.add("Nov");
        labels.add("Dec");


        BarData data = new BarData(labels,dataSet);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        chart.setData(data);
        chart.setDescription("No of Projects");
       */


   // second line graph
/*
 // in this example, a LineChart is initialized from xml
        LineChart lineChart = (LineChart) findViewById(R.id.chart);
        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(4f, 0));
        entries.add(new Entry(8f, 1));
        entries.add(new Entry(6f, 2));
        entries.add(new Entry(2f, 3));
        entries.add(new Entry(18f, 4));
        entries.add(new Entry(9f, 5));

        LineDataSet dataset = new LineDataSet(entries,"days");

        ArrayList<String> labels = new ArrayList<>();
        labels.add("MON");
        labels.add("TUE");
        labels.add("WED");
        labels.add("THU");
        labels.add("FRI");
        labels.add("SUN");

        LineData data = new LineData(labels, dataset);
        lineChart.setData(data); // set the data and list of lables into chart
        // lineChart.
        lineChart.setDescription("Description");  // set the description
 */



