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
import android.widget.CheckBox;
import android.widget.TextView;

import com.uihomies.androidfitness.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Profile extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Setting the fonts

        // Profile header
        TextView tv1=(TextView)findViewById(R.id.profileTitle);
        Typeface face1=Typeface.createFromAsset(getAssets(),"fonts/Marker_Felt_Bold.ttf");
        tv1.setTypeface(face1);

        // Profile elements
        TextView tv2=(TextView)findViewById(R.id.genderLabel);
        Typeface face2=Typeface.createFromAsset(getAssets(),"fonts/Marker_Felt.ttf");
        tv2.setTypeface(face2);
        TextView tv3=(TextView)findViewById(R.id.genderAnswer);
        Typeface face3=Typeface.createFromAsset(getAssets(),"fonts/Marker_Felt.ttf");
        tv3.setTypeface(face3);
        TextView tv4=(TextView)findViewById(R.id.dobLabel);
        Typeface face4=Typeface.createFromAsset(getAssets(),"fonts/Marker_Felt.ttf");
        tv4.setTypeface(face4);
        TextView tv5=(TextView)findViewById(R.id.dobAnswer);
        Typeface face5=Typeface.createFromAsset(getAssets(),"fonts/Marker_Felt.ttf");
        tv5.setTypeface(face5);
        TextView tv6=(TextView)findViewById(R.id.weightLabel);
        Typeface face6=Typeface.createFromAsset(getAssets(),"fonts/Marker_Felt.ttf");
        tv6.setTypeface(face6);
        TextView tv7=(TextView)findViewById(R.id.weightAnswer);
        Typeface face7=Typeface.createFromAsset(getAssets(),"fonts/Marker_Felt.ttf");
        tv7.setTypeface(face7);
        TextView tv8=(TextView)findViewById(R.id.athleticLevelLabel);
        Typeface face8=Typeface.createFromAsset(getAssets(),"fonts/Marker_Felt.ttf");
        tv8.setTypeface(face8);
        TextView tv9=(TextView)findViewById(R.id.athleticLevelAnswer);
        Typeface face9=Typeface.createFromAsset(getAssets(),"fonts/Marker_Felt.ttf");
        tv9.setTypeface(face9);

        // Next button
        TextView tv10=(TextView)findViewById(R.id.editProfileButton);
        Typeface face10=Typeface.createFromAsset(getAssets(),"fonts/DS_Marker_Felt.ttf");
        tv10.setTypeface(face10);

        // Setting profile answers and title
        SharedPreferences sharedpreferences = getSharedPreferences("appPreferences", Context.MODE_PRIVATE);
        String gender[] = {"", "Male", "Female", "None"};
        String athleticLevel[] = {"", "Beginner", "Average", "Athletic"};
        Date dob = new Date(sharedpreferences.getLong("userBirthday", 0));
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");

        tv1.setText(sharedpreferences.getString("userName", "Profile")); // Name
        tv3.setText(gender[sharedpreferences.getInt("userGender", 0)]); // Gender
        tv5.setText(dateFormat.format(dob)); // DOB
        tv7.setText(sharedpreferences.getInt("userWeight", 0) + " Kg"); // Weight
        tv9.setText(athleticLevel[sharedpreferences.getInt("userAthleticLevel", 0)]); // Athletic level
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Profile.this, MainMenuActivity.class);
        startActivity(intent);

        return;
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

    public void editButtonClick(View view) {

        // Go to sign up pages
        Intent intent = new Intent(Profile.this, SignupStep1.class);
        startActivity(intent);
    }
}
