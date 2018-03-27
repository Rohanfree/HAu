package com.example.rohan.hau;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mAuth = FirebaseAuth.getInstance();

        final FirebaseUser user1=mAuth.getCurrentUser();
        //NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView =  navigationView.getHeaderView(0);
        TextView nav_user = (TextView)hView.findViewById(R.id.thechanger);
        nav_user.setText(user1.getDisplayName());
        ImageView him=(ImageView)hView.findViewById(R.id.imageView);
        //him.setImageResource(R.drawable.logo);
        FirebaseDatabase.getInstance().getReference().child("raspberry").child("admno6689").orderByKey().limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Reading re;
                TextView t=(TextView)findViewById(R.id.textView9);
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    re=snapshot.getValue(Reading.class);
                    int units=re.value-2000;
                    t.setText(t.getText()+""+units+"units");
                    t=(TextView)findViewById(R.id.textView13);
                    if(units<=300){
                        t.setText(""+(units*5)+"/-");
                    }
                    else if(units<=400){
                        t.setText(""+(units*5.70)+"/-");
                    }
                    else if(units<=500){
                        t.setText(""+(units*6.10)+"/-");
                    }
                    else{
                        t.setText(""+(units*7.5)+"/-");
                    }
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Handle possible errors.
            }
        });


        FirebaseDatabase.getInstance().getReference().child("user")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            user use = snapshot.getValue(user.class);
                            //v.setText(""+v.getText()+""+re.time+"@@"+re.value+"\n");
                            //Toast.makeText(Dummy_activity.this,""+re.time+re.value,Toast.LENGTH_LONG).show();
                            if(user1.getEmail().equals(use.address)) {
                                TextView v=(TextView)findViewById(R.id.n1);
                                v.setText(""+use.name);
                                v=(TextView)findViewById(R.id.textView4);
                                v.setText(""+use.cno);
                                v=(TextView)findViewById(R.id.textView5);
                                v.setText(""+use.mobile);
                                v=(TextView)findViewById(R.id.textView6);
                                v.setText(""+use.piname);
                            }




                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
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

        if (id == R.id.nav_camera) {

            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            startActivity(new Intent(MainActivity.this,Dummy_activity.class));

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
///////////////////////////////////////////////////////////////////////////////////////


