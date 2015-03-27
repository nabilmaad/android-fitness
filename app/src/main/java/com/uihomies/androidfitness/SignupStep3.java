package com.uihomies.androidfitness;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.uihomies.androidfitness.R;

import java.util.Calendar;
import java.util.Date;

public class SignupStep3 extends ActionBarActivity {
    private final static int currentYear = Calendar.getInstance().get(Calendar.YEAR);
    private final static String dayError = "Day must be a number between 1 and 31";
    private final static String monthError = "Month must be a number between 1 and 12";
    private final static String yearError = "Year must be between " + (currentYear - 100) + " and " + currentYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_step3);

        // Setting the fonts

        // Sign up header
        TextView tv1=(TextView)findViewById(R.id.signupTitle);
        Typeface face1=Typeface.createFromAsset(getAssets(),"fonts/Marker_Felt_Bold.ttf");
        tv1.setTypeface(face1);

        // Sign up question
        TextView tv2=(TextView)findViewById(R.id.birthdayLabel);
        Typeface face2=Typeface.createFromAsset(getAssets(),"fonts/Marker_Felt.ttf");
        tv2.setTypeface(face2);

        // Next button
        TextView tv3=(TextView)findViewById(R.id.nextButton);
        Typeface face3=Typeface.createFromAsset(getAssets(),"fonts/DS_Marker_Felt.ttf");
        tv3.setTypeface(face3);

        // Adding text watchers
        final EditText day = (EditText)findViewById(R.id.day);
        final EditText month = (EditText)findViewById(R.id.month);
        final EditText year = (EditText)findViewById(R.id.year);

        // Day
        day.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(day.getText().toString().length()==2)
                {
                    // Error handling
                    if(Integer.parseInt(day.getText().toString()) < 1 ||
                            Integer.parseInt(day.getText().toString()) > 31)
                        day.setError(dayError);
                    else
                        month.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });

        // Month
        month.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(month.getText().toString().length()==2)
                {
                    // Error handling
                    if(Integer.parseInt(month.getText().toString()) < 1 ||
                            Integer.parseInt(month.getText().toString()) > 12)
                        month.setError(monthError);
                    else
                        year.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });

        // Year
        year.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(year.getText().toString().length()==4)
                {
                    // Error handling
                    if(Integer.parseInt(year.getText().toString()) < (currentYear- 100) ||
                            Integer.parseInt(year.getText().toString()) > currentYear)
                        year.setError(yearError);
                    else {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(year.getWindowToken(), 0);
                    }
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });

        // Setting dob if already exists, and header title
        SharedPreferences sharedpreferences = getSharedPreferences("appPreferences", Context.MODE_PRIVATE);
        if(sharedpreferences.getLong("userBirthday", 0) != 0) {
            Date dob = new Date(sharedpreferences.getLong("userBirthday", 0));
            Calendar cal = Calendar.getInstance();
            cal.setTime(dob);
            day.setText(String.format("%02d", cal.get(Calendar.DAY_OF_MONTH)));
            month.setText(String.format("%02d", cal.get(Calendar.MONTH) + 1));
            year.setText(Integer.toString(cal.get(Calendar.YEAR)));
            tv1.setText("Edit Info");
        } else {
            tv1.setText("Sign Up");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_signup_step3, menu);
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
        EditText day = (EditText)findViewById(R.id.day);
        EditText month = (EditText)findViewById(R.id.month);
        EditText year = (EditText)findViewById(R.id.year);

        String dayString = day.getText().toString();
        String monthString = month.getText().toString();
        String yearString = year.getText().toString();

        // Error handling
        boolean valid = true;
        if(dayString.length() <= 0 || dayString.equals("0")) {
            day.setError(dayError);
            valid = false;
        }
        if(monthString.length() <= 0 || monthString.equals("0")) {
            month.setError(monthError);
            valid = false;
        }
        if(yearString.length() <= 0 || yearString.equals("0")) {
            year.setError(yearError);
            valid = false;
        }

        if(valid) {
            // Save user's birthday
            Calendar cal = Calendar.getInstance();
            cal.clear();
            cal.set(Integer.parseInt(yearString), Integer.parseInt(monthString) - 1, Integer.parseInt(dayString));
            SharedPreferences sharedpreferences = getSharedPreferences("appPreferences", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putLong("userBirthday", cal.getTime().getTime());
            editor.commit();

            // Load next activity
            Intent intent = new Intent(SignupStep3.this, SignupStep4.class);
            startActivity(intent);
        }
    }
}
