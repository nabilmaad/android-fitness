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
        ((TextView) findViewById(R.id.profileTitle)).setTypeface(Typeface.createFromAsset(getAssets(),"fonts/DS_Marker_Felt.ttf"));

        // Profile elements
        ((TextView) findViewById(R.id.genderLabel)).setTypeface(Typeface.createFromAsset(getAssets(),"fonts/DS_Marker_Felt.ttf"));
        ((TextView) findViewById(R.id.genderAnswer)).setTypeface(Typeface.createFromAsset(getAssets(),"fonts/DS_Marker_Felt.ttf"));
        ((TextView) findViewById(R.id.dobLabel)).setTypeface(Typeface.createFromAsset(getAssets(),"fonts/DS_Marker_Felt.ttf"));
        ((TextView) findViewById(R.id.dobAnswer)).setTypeface(Typeface.createFromAsset(getAssets(),"fonts/DS_Marker_Felt.ttf"));
        ((TextView) findViewById(R.id.weightLabel)).setTypeface(Typeface.createFromAsset(getAssets(),"fonts/DS_Marker_Felt.ttf"));
        ((TextView) findViewById(R.id.weightAnswer)).setTypeface(Typeface.createFromAsset(getAssets(),"fonts/DS_Marker_Felt.ttf"));
        ((TextView) findViewById(R.id.athleticLevelLabel)).setTypeface(Typeface.createFromAsset(getAssets(),"fonts/DS_Marker_Felt.ttf"));
        ((TextView) findViewById(R.id.athleticLevelAnswer)).setTypeface(Typeface.createFromAsset(getAssets(),"fonts/DS_Marker_Felt.ttf"));

        // Next button
        ((TextView) findViewById(R.id.editProfileButton)).setTypeface(Typeface.createFromAsset(getAssets(),"fonts/DS_Marker_Felt.ttf"));

        // Setting profile answers and title
        SharedPreferences sharedpreferences = getSharedPreferences("appPreferences", Context.MODE_PRIVATE);
        String gender[] = {"", "Male", "Female", "None"};
        String athleticLevel[] = {"", "Beginner", "Average", "Athletic"};
        Date dob = new Date(sharedpreferences.getLong("userBirthday", 0));
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");

        ((TextView) findViewById(R.id.profileTitle)).setText(sharedpreferences.getString("userName", "Profile")); // Name
        ((TextView) findViewById(R.id.genderAnswer)).setText(gender[sharedpreferences.getInt("userGender", 0)]); // Gender
        ((TextView) findViewById(R.id.dobAnswer)).setText(dateFormat.format(dob)); // DOB
        ((TextView) findViewById(R.id.weightAnswer)).setText(sharedpreferences.getInt("userWeight", 0) + " Kg"); // Weight
        ((TextView) findViewById(R.id.athleticLevelAnswer)).setText(athleticLevel[sharedpreferences.getInt("userAthleticLevel", 0)]); // Athletic level
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
