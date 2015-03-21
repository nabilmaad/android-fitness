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

import com.uihomies.androidfitness.R;

import java.util.Calendar;

public class SignupStep4 extends ActionBarActivity {

    private final static String weightError = "Weight must be a number between 10 and 999";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_step4);

        // Setting the fonts

        // Sign up header
        TextView tv1=(TextView)findViewById(R.id.signupTitle);
        Typeface face1=Typeface.createFromAsset(getAssets(),"fonts/Marker_Felt_Bold.ttf");
        tv1.setTypeface(face1);

        // Sign up question
        TextView tv2=(TextView)findViewById(R.id.weightLabel);
        Typeface face2=Typeface.createFromAsset(getAssets(),"fonts/Marker_Felt.ttf");
        tv2.setTypeface(face2);

        // Next button
        TextView tv3=(TextView)findViewById(R.id.nextButton);
        Typeface face3=Typeface.createFromAsset(getAssets(),"fonts/DS_Marker_Felt.ttf");
        tv3.setTypeface(face3);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_signup_step4, menu);
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
        EditText weight = (EditText)findViewById(R.id.weight);
        Switch weightSwitch = (Switch)findViewById(R.id.weightSwitch);

        String weightString = weight.getText().toString();

        // Error handling
        boolean valid = true;
        if(weightString.length() <= 1 ||
                weightString.equals("0")) {
            weight.setError(weightError);
            valid = false;
        }

        if(valid) {
            // Save user's weight in Kg
            SharedPreferences sharedpreferences = getSharedPreferences("appPreferences", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            if(!weightSwitch.isChecked()) // Already in Kg
                editor.putInt("userWeight", Integer.parseInt(weightString));
            else // Convert Lbs to Kg
                editor.putInt("userWeight", (int)(Double.parseDouble(weightString) / 2.2));
            editor.commit();

            // Load next activity
            Intent intent = new Intent(SignupStep4.this, SignupStep5.class);
            startActivity(intent);
        }
    }
}
