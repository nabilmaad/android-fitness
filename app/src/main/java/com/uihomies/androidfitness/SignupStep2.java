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
import android.widget.EditText;
import android.widget.TextView;

import com.uihomies.androidfitness.R;

public class SignupStep2 extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_step2);

        // Setting the fonts

        // Sign up header
        TextView tv1=(TextView)findViewById(R.id.signupTitle);
        Typeface face1=Typeface.createFromAsset(getAssets(),"fonts/Marker_Felt_Bold.ttf");
        tv1.setTypeface(face1);

        // Sign up question
        TextView tv2=(TextView)findViewById(R.id.genderLabel);
        Typeface face2=Typeface.createFromAsset(getAssets(),"fonts/Marker_Felt.ttf");
        tv2.setTypeface(face2);

        // Next button
        TextView tv3=(TextView)findViewById(R.id.nextButton);
        Typeface face3=Typeface.createFromAsset(getAssets(),"fonts/DS_Marker_Felt.ttf");
        tv3.setTypeface(face3);

        // Setting gender if already exists, and header title
        SharedPreferences sharedpreferences = getSharedPreferences("appPreferences", Context.MODE_PRIVATE);
        int gender = sharedpreferences.getInt("userGender", 0);
        if(gender == 1) {
            Button maleButton = (Button)findViewById(R.id.maleButton);
            maleButton.setActivated(true);
        } else if (gender == 2) {
            Button femaleButton = (Button)findViewById(R.id.femaleButton);
            femaleButton.setActivated(true);
        } else if (gender == 3) {
            Button noneButton = (Button)findViewById(R.id.noneButton);
            noneButton.setActivated(true);
        }

        if(sharedpreferences.getInt("userAthleticLevel", 0) != 0) {
            tv1.setText("EDIT INFO");
        }
        else {
            tv1.setText("SIGN UP");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_signup_step2, menu);
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

    public void maleButtonClick(View view) {
        Button maleButton = (Button) findViewById(R.id.maleButton);
        maleButton.setActivated(true);

        // Deactivate the other 2
        Button femaleButton = (Button) findViewById(R.id.femaleButton);
        Button noneButton = (Button) findViewById(R.id.noneButton);
        femaleButton.setActivated(false);
        noneButton.setActivated(false);
    }

    public void femaleButtonClick(View view) {
        Button femaleButton = (Button) findViewById(R.id.femaleButton);
        femaleButton.setActivated(true);

        // Deactivate the other 2
        Button maleButton = (Button) findViewById(R.id.maleButton);
        Button noneButton = (Button) findViewById(R.id.noneButton);
        maleButton.setActivated(false);
        noneButton.setActivated(false);
    }

    public void noneButtonClick(View view) {
        Button noneButton = (Button) findViewById(R.id.noneButton);
        noneButton.setActivated(true);

        // Deactivate the other 2
        Button maleButton = (Button) findViewById(R.id.maleButton);
        Button femaleButton = (Button) findViewById(R.id.femaleButton);
        maleButton.setActivated(false);
        femaleButton.setActivated(false);
    }

    public void nextButtonClick(View view) {
        int gender = 0;

        // Male
        Button maleButton = (Button) findViewById(R.id.maleButton);
        if(maleButton.isActivated())
            gender = 1;
        else {
            // Female
            Button femaleButton = (Button) findViewById(R.id.femaleButton);
            if(femaleButton.isActivated())
                gender = 2;
            else {
                // None
                Button noneButton = (Button) findViewById(R.id.noneButton);
                if(noneButton.isActivated())
                    gender = 3;
            }
        }

        // Error handling
        boolean valid = true;
        if(gender == 0) {
            valid = false;

            // Show alert
            new AlertDialog.Builder(this)
                    .setTitle("Select Gender")
                    .setMessage("You must select a gender to continue.")
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
            editor.putInt("userGender", gender);
            editor.commit();

            // Load next activity
            Intent intent = new Intent(SignupStep2.this, SignupStep3.class);
            startActivity(intent);
        }
    }
}
