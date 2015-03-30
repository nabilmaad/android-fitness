package com.uihomies.androidfitness;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.uihomies.androidfitness.R;

public class SettingsActivity extends ActionBarActivity {
    boolean profileExists = true;
    Button deleteButton;
    TextView profileWarning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        deleteButton = (Button) findViewById(R.id.deleteProfileButton);
        profileWarning = (TextView) findViewById(R.id.profileWarning);
        if(profileExists)
        {
            deleteButton.setText("DELETE PROFILE");
            deleteButton.setBackgroundColor(Color.parseColor("#E74C3C"));
            profileWarning.setText("Erases all profile and progress information. Cannot be undone.");
        }
        else{
            deleteButton.setText("SIGN UP");
            deleteButton.setBackgroundColor(Color.parseColor("#2ECC71"));
            profileWarning.setText("Please create a profile to play the game.");
        }
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
        if(profileExists) {
            //open alert view
            new AlertDialog.Builder(this)
                    .setTitle("Delete profile")
                    .setMessage("Are you sure you want to delete your profile?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(), "Profile deleted.", Toast.LENGTH_SHORT).show();
                            profileExists = false;
                            deleteButton.setText("SIGN UP");
                            deleteButton.setBackgroundColor(Color.parseColor("#2ECC71"));
                            profileWarning.setText("Please create a profile to play the game.");
                            //Launch sign up
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
        //Emulate a profile being created
        else{
            Toast.makeText(getApplicationContext(), "Profile created.", Toast.LENGTH_SHORT).show();
            profileExists = true;
            deleteButton.setText("DELETE PROFILE");
            deleteButton.setBackgroundColor(Color.parseColor("#E74C3C"));
            profileWarning.setText("Erases all profile and progress information. Cannot be undone.");
        }
    }
}
