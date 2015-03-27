package com.uihomies.androidfitness;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.uihomies.androidfitness.R;

public class SignupStep5 extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_step5);

        // Sign up header
        TextView tv1=(TextView)findViewById(R.id.signupTitle);
        Typeface face1=Typeface.createFromAsset(getAssets(),"fonts/Marker_Felt_Bold.ttf");
        tv1.setTypeface(face1);

        // Sign up question
        TextView tv2=(TextView)findViewById(R.id.athleticLevelLabel);
        Typeface face2=Typeface.createFromAsset(getAssets(),"fonts/Marker_Felt.ttf");
        tv2.setTypeface(face2);

        // Next button
        TextView tv3=(TextView)findViewById(R.id.nextButton);
        Typeface face3=Typeface.createFromAsset(getAssets(),"fonts/DS_Marker_Felt.ttf");
        tv3.setTypeface(face3);

        // Setting athletic level if already exists, and header title
        SharedPreferences sharedpreferences = getSharedPreferences("appPreferences", Context.MODE_PRIVATE);
        int athleticLevel = sharedpreferences.getInt("userAthleticLevel", 0);
        if(athleticLevel == 1) {
            Button beginnerButton = (Button)findViewById(R.id.beginnerButton);
            beginnerButton.setActivated(true);
            tv1.setText("Edit Info");
        } else if (athleticLevel == 2) {
            Button averageButton = (Button)findViewById(R.id.averageButton);
            averageButton.setActivated(true);
            tv1.setText("Edit Info");
        } else if (athleticLevel == 3) {
            Button athleticButton = (Button)findViewById(R.id.athleticButton);
            athleticButton.setActivated(true);
            tv1.setText("Edit Info");
        } else {
            tv1.setText("Sign Up");
        }

        // Setting next button text
        TextView nextButton = (TextView)findViewById(R.id.nextButton);
        if(athleticLevel == 0)
            nextButton.setText(getResources().getString(R.string.signupNext));
        else
            nextButton.setText(getResources().getString(R.string.signupDone));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_signup_step5, menu);
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

    public void beginnerButtonClick(View view) {
        Button beginnerButton = (Button) findViewById(R.id.beginnerButton);
        beginnerButton.setActivated(true);

        // Deactivate the other 2
        Button averageButton = (Button) findViewById(R.id.averageButton);
        Button athleticButton = (Button) findViewById(R.id.athleticButton);
        averageButton.setActivated(false);
        athleticButton.setActivated(false);
    }

    public void averageButtonClick(View view) {
        Button averageButton = (Button) findViewById(R.id.averageButton);
        averageButton.setActivated(true);

        // Deactivate the other 2
        Button beginnerButton = (Button) findViewById(R.id.beginnerButton);
        Button athleticButton = (Button) findViewById(R.id.athleticButton);
        beginnerButton.setActivated(false);
        athleticButton.setActivated(false);
    }

    public void athleticButtonClick(View view) {
        Button athleticButton = (Button) findViewById(R.id.athleticButton);
        athleticButton.setActivated(true);

        // Deactivate the other 2
        Button beginnerButton = (Button) findViewById(R.id.beginnerButton);
        Button averageButton = (Button) findViewById(R.id.averageButton);
        beginnerButton.setActivated(false);
        averageButton.setActivated(false);
    }

    public void nextButtonClick(View view) {
        int athleticLevel = 0;

        // Beginner
        Button beginnerButton = (Button) findViewById(R.id.beginnerButton);
        if(beginnerButton.isActivated())
            athleticLevel = 1;
        else {
            // Average
            Button averageButton = (Button) findViewById(R.id.averageButton);
            if(averageButton.isActivated())
                athleticLevel = 2;
            else {
                // Athletic
                Button athleticButton = (Button) findViewById(R.id.athleticButton);
                if(athleticButton.isActivated())
                    athleticLevel = 3;
            }
        }

        // Error handling
        boolean valid = true;
        if(athleticLevel == 0) {
            valid = false;

            // Show alert
            new AlertDialog.Builder(this)
                    .setTitle("Select Athletic Level")
                    .setMessage("You must select an athletic level to continue.")
                    .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }

        if(valid) {
            // Save user's gender
            SharedPreferences sharedpreferences = getSharedPreferences("appPreferences", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putInt("userAthleticLevel", athleticLevel);
            editor.commit();

            // Load next activity (depending on whether it's signup or edit info)
            TextView title=(TextView)findViewById(R.id.signupTitle);
            Intent intent;
            if(title.getText().equals("Sign Up"))
                intent = new Intent(SignupStep5.this, SignupStep6.class);
            else
                intent = new Intent(SignupStep5.this, Profile.class);
            startActivity(intent);
        }
    }
}
