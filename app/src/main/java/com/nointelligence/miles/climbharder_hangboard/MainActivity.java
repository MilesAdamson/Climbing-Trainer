package com.nointelligence.miles.climbharder_hangboard;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobileAds.initialize(this, "ca-app-pub-8645540572237587~1124373125");
        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        // inserts the built in workouts if they don't already exist
        new BuiltInWorkouts(this).addDefaultWorkouts();
    }

    @Override
    public void onResume(){
        super.onResume();
        setContentView(R.layout.activity_main);
        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        adView.loadAd(adRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Checks for internet and continues if user has connection.
    // if there is no connection, ask them to enable wifi
    public void changeActivity(View v){
        ConnectionCheck connectionCheck = new ConnectionCheck(this);
        Intent intent;
            switch (v.getId()) {
                case R.id.layoutWorkoutMainActivity:
                    intent = new Intent(this, WorkoutList.class);
                    connectionCheck.navigate(intent);
                    break;

                case R.id.layoutLogbook:
                    intent = new Intent(this, Logbook.class);
                    connectionCheck.navigate(intent);
                    break;

                default:
                    // do nothing
                    break;
            }
    }

}
