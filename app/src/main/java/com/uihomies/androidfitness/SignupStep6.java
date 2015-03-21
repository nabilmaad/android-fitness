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
import android.widget.CheckBox;
import android.widget.TextView;

import com.uihomies.androidfitness.R;

public class SignupStep6 extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_step6);

        // Sign up header
        TextView tv1=(TextView)findViewById(R.id.signupTitle);
        Typeface face1=Typeface.createFromAsset(getAssets(),"fonts/Marker_Felt_Bold.ttf");
        tv1.setTypeface(face1);

        // Sign up question
        TextView tv2=(TextView)findViewById(R.id.audioFeedbackLabel);
        Typeface face2=Typeface.createFromAsset(getAssets(),"fonts/Marker_Felt.ttf");
        tv2.setTypeface(face2);

        // AUDIO FEEDBACK
        TextView tv3=(TextView)findViewById(R.id.audioFeedbackAnswer);
        Typeface face3=Typeface.createFromAsset(getAssets(),"fonts/Marker_Felt_Bold.ttf");
        tv3.setTypeface(face3);

        // Audio hint
        TextView tv4=(TextView)findViewById(R.id.audioFeedbackHint);
        Typeface face4=Typeface.createFromAsset(getAssets(),"fonts/Marker_Felt.ttf");
        tv4.setTypeface(face4);

        // Next button
        TextView tv5=(TextView)findViewById(R.id.doneButton);
        Typeface face5=Typeface.createFromAsset(getAssets(),"fonts/DS_Marker_Felt.ttf");
        tv5.setTypeface(face5);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_signup_step6, menu);
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

    public void doneButtonClick(View view) {
        CheckBox checkbox=(CheckBox)findViewById(R.id.audioCheckbox);

        // Save user's audio feedback preference
        SharedPreferences sharedpreferences = getSharedPreferences("appPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean("audioFeedback", checkbox.isChecked());
        editor.commit();

        // FIXME Go to home screen
        // Load next activity
//        Intent intent = new Intent(SignupStep6.this, SignupStep6.class);
//        startActivity(intent);
    }
}
