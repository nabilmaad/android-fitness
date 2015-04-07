package com.uihomies.androidfitness;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;


public class SignupStepX extends ActionBarActivity {

    private final static String heightError = "Height must be a number between 10 and 999";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_step_x);

        // Set the fonts

        // Sign up header
        TextView tv1=(TextView)findViewById(R.id.signupTitle);
        Typeface face1=Typeface.createFromAsset(getAssets(),"fonts/Marker_Felt_Bold.ttf");
        tv1.setTypeface(face1);

        // Sign up question
        TextView tv2=(TextView)findViewById(R.id.heightLabel);
        Typeface face2=Typeface.createFromAsset(getAssets(),"fonts/Marker_Felt.ttf");
        tv2.setTypeface(face2);

        // Sign up next button
        TextView tv3=(TextView)findViewById(R.id.nextButton);
        Typeface face3=Typeface.createFromAsset(getAssets(),"fonts/DS_Marker_Felt.ttf");
        tv3.setTypeface(face3);

        // Setting height if already exists, and header title
        SharedPreferences sharedpreferences = getSharedPreferences("appPreferences", Context.MODE_PRIVATE);
        if(sharedpreferences.getInt("userHeight", 0) != 0) {
            EditText height = (EditText)findViewById(R.id.height);
            height.setText(Integer.toString(sharedpreferences.getInt("userHeight", 0)));
            tv1.setText("EDIT INFO");
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
        getMenuInflater().inflate(R.menu.menu_signup_step_x, menu);
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

    public void nextButtonClick(View view) {
        EditText height = (EditText)findViewById(R.id.height);
        Switch heightSwitch = (Switch)findViewById(R.id.heightSwitch);

        String heightString = height.getText().toString();

        // Error handling
        boolean valid = true;
        if(heightString.length() <= 1 ||
                heightString.equals("0")) {
            height.setError(heightError);
            valid = false;
        }

        if(valid) {
            // Save user's height in cm
            SharedPreferences sharedpreferences = getSharedPreferences("appPreferences", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            if(!heightSwitch.isChecked()) // Already in cm
                editor.putInt("userHeight", Integer.parseInt(heightString));
            else // Convert inches to cm
                editor.putInt("userHeight", (int)(Double.parseDouble(heightString) * 2.54));
            editor.commit();

            // Load next activity
            Intent intent = new Intent(SignupStepX.this, SignupStep5.class);
            startActivity(intent);
        }
    }
}
