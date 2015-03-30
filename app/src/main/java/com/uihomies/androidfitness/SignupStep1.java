package com.uihomies.androidfitness;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.uihomies.androidfitness.R;

public class SignupStep1 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_step1);

        // Setting the fonts

        // Sign up header
        TextView tv1=(TextView)findViewById(R.id.signupTitle);
        Typeface face1=Typeface.createFromAsset(getAssets(),"fonts/Marker_Felt_Bold.ttf");
        tv1.setTypeface(face1);

        // Sign up question
        TextView tv2=(TextView)findViewById(R.id.nameLabel);
        Typeface face2=Typeface.createFromAsset(getAssets(),"fonts/Marker_Felt.ttf");
        tv2.setTypeface(face2);

        // Next button
        TextView tv3=(TextView)findViewById(R.id.nextButton);
        Typeface face3=Typeface.createFromAsset(getAssets(),"fonts/DS_Marker_Felt.ttf");
        tv3.setTypeface(face3);

        // Setting name if already exists, and the header title
        SharedPreferences sharedpreferences = getSharedPreferences("appPreferences", Context.MODE_PRIVATE);
        if(!sharedpreferences.getString("userName", "").equals("")) {
            EditText name = (EditText)findViewById(R.id.name);
            name.setText(sharedpreferences.getString("userName", ""));
        }

        if(sharedpreferences.getInt("userAthleticLevel", 0) != 0) {
            tv1.setText("Edit Info");
        }
        else {
            tv1.setText("Sign Up");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_signup_step1, menu);
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
        EditText nameTextField = (EditText) findViewById(R.id.name);
        String userName = nameTextField.getText().toString();

        // Error handling
        boolean valid = true;
        if(!userName.matches("[A-Z][a-z]*")){
            nameTextField.setError("Name must start with a capital letter and contain letters only.");
            valid = false;
        }

        if(valid) {
            // Save user's Name
            SharedPreferences sharedpreferences = getSharedPreferences("appPreferences", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("userName", userName);
            editor.commit();

            // Load next activity
            Intent intent = new Intent(SignupStep1.this, SignupStep2.class);
            startActivity(intent);
        }
    }
}
