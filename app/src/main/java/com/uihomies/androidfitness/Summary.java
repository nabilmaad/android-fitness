package com.uihomies.androidfitness;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.uihomies.androidfitness.R;

public class Summary extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        ((TextView) findViewById(R.id.summaryTitle)).setTypeface(Typeface.createFromAsset(getAssets(), "fonts/DS_Marker_Felt.ttf"));
        ((TextView) findViewById(R.id.heartRateTitle)).setTypeface(Typeface.createFromAsset(getAssets(), "fonts/DS_Marker_Felt.ttf"));
        ((TextView) findViewById(R.id.averageTitle)).setTypeface(Typeface.createFromAsset(getAssets(), "fonts/DS_Marker_Felt.ttf"));
        ((TextView) findViewById(R.id.restingTitle)).setTypeface(Typeface.createFromAsset(getAssets(), "fonts/DS_Marker_Felt.ttf"));
        ((TextView) findViewById(R.id.maxTitle)).setTypeface(Typeface.createFromAsset(getAssets(), "fonts/DS_Marker_Felt.ttf"));
        ((TextView) findViewById(R.id.avgValue)).setTypeface(Typeface.createFromAsset(getAssets(), "fonts/DS_Marker_Felt.ttf"));
        ((TextView) findViewById(R.id.restValue)).setTypeface(Typeface.createFromAsset(getAssets(), "fonts/DS_Marker_Felt.ttf"));
        ((TextView) findViewById(R.id.maxValue)).setTypeface(Typeface.createFromAsset(getAssets(), "fonts/DS_Marker_Felt.ttf"));
        ((TextView) findViewById(R.id.distanceTitle)).setTypeface(Typeface.createFromAsset(getAssets(), "fonts/DS_Marker_Felt.ttf"));
        ((TextView) findViewById(R.id.distValue)).setTypeface(Typeface.createFromAsset(getAssets(), "fonts/DS_Marker_Felt.ttf"));
        ((TextView) findViewById(R.id.timeTitle)).setTypeface(Typeface.createFromAsset(getAssets(), "fonts/DS_Marker_Felt.ttf"));
        ((TextView) findViewById(R.id.timeValue)).setTypeface(Typeface.createFromAsset(getAssets(), "fonts/DS_Marker_Felt.ttf"));
        ((TextView) findViewById(R.id.okButton)).setTypeface(Typeface.createFromAsset(getAssets(), "fonts/DS_Marker_Felt.ttf"));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_summary, menu);
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

    public void okButtonClick(View view) {
        // Load next activity
        Intent intent = new Intent(Summary.this, MainMenuActivity.class);
        startActivity(intent);
    }
}
