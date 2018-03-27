package com.example.rohan.hau;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Dummy_activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    EditText v;
    ArrayList<Reading> read=new ArrayList<>();
    ArrayList<Entry> entries = new ArrayList<>();
    ArrayList<BarEntry> BarEntry = new ArrayList<>();
    ArrayList<String> labels = new ArrayList<>();
    private EditText mTextMessage,dayedit;
    RelativeLayout monthgraph,daygraph;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    monthgraph.setVisibility(View.VISIBLE);
                    daygraph.setVisibility(View.GONE);
                    return true;
                case R.id.navigation_dashboard:
                    monthgraph.setVisibility(View.GONE);

                    daygraph.setVisibility(View.VISIBLE);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy_activity);
        v=(EditText) findViewById(R.id.month);
        v.setText("fetching data");
        monthgraph=(RelativeLayout)findViewById(R.id.monthrelative);
        daygraph=(RelativeLayout)findViewById(R.id.dayrelative);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout1);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        FirebaseDatabase.getInstance().getReference().child("raspberry").child("admno6689")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Reading tem=new Reading();
                        tem.time="last";
                        tem.value=2000;
                        read.add(tem);
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Reading re=snapshot.getValue(Reading.class);
                            read.add(re);



                        }
                        v.setText("the data is ready to be displayed");

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
        mTextMessage = (EditText) findViewById(R.id.month);
        dayedit=(EditText) findViewById(R.id.day);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    }
    public void month(View v)
    {
        display_month(""+mTextMessage.getText());
        Plot_graph();
    }
    public void day(View v)
    {
        display_day(""+dayedit.getText());
        bar_graph();
    }
    Ringtone r;
    public void display_month(String month){
        entries.clear();
        labels.clear();
        int a=0,p=0,c=0;

        int k=read.size(),newreading;

        for(int i=1;i<k;i++)
        {

            if(read.get(i).time.split(":")[1].equals(month))
            {


                newreading = (read.get(i).value) - read.get(i-1).value;

                if((i)%24==0)
                {

                    p=a/24;
                    entries.add(new Entry(p,c));
                    labels.add(new String("" + read.get(i).time.split(":")[2]));
                    c++;
                    a=0;

                }

                a=a+newreading;

            }
        }

    }
    public void display_day(String day) {
        entries.clear();
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
    private void addNotification() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        .setContentTitle("Hau")
                        .setContentText("You have high current usage\n1\n2\n3\n4\n5\n6");


        Intent notificationIntent = new Intent(this, Dummy_activity.class);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        //Ringtone r = null;

        if (id == R.id.nav_camera) {

            startActivity(new Intent(Dummy_activity.this,MainActivity.class));

        } else if (id == R.id.nav_gallery) {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();


        } else if (id == R.id.nav_slideshow) {
            addNotification();

        } else if (id == R.id.nav_manage) {


        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(Dummy_activity.this,Main2Activity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout1);
        drawer.closeDrawer(GravityCompat.START);
        int a;

        return true;

    }
    //plot graph line graph
    public void Plot_graph()
    {

        LineChart chart = (LineChart) findViewById(R.id.chart);
        LineDataSet dataSet = new LineDataSet(entries,"Projects");
        LineData data = new LineData(labels, dataSet);
        chart.setData(data); // set the data and list of lables into chart
        // lineChart.
        chart.setDescription("DAY");  // set the description
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

}
