package com.uihomies.androidfitness;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.uihomies.androidfitness.R;

public class SettingsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Check checkbox state
        CheckBox checkbox = (CheckBox)findViewById(R.id.audioCheckbox);
        SharedPreferences sharedpreferences = getSharedPreferences("appPreferences", Context.MODE_PRIVATE);
        boolean audioFeedback = sharedpreferences.getBoolean("audioFeedback", false);
        if(audioFeedback) {
            checkbox.setChecked(true);
        }
        else checkbox.setChecked(false);
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                SharedPreferences sharedpreferences = getSharedPreferences("appPreferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putBoolean("audioFeedback", buttonView.isChecked());
                if(buttonView.isChecked())
                    Toast.makeText(getApplicationContext(), "Audio feedback turned on.", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(), "Audio feedback turned off.", Toast.LENGTH_SHORT).show();
                editor.commit();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
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

    public void deleteClicked(View view) {
        //open alert view
        //Profile deleted.
        //Change to sign up
        //Launch sign up
    }
}
