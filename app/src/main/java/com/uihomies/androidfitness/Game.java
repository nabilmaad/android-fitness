package com.uihomies.androidfitness;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import zephyr.android.HxMBT.*;
import com.uihomies.androidfitness.NewConnectedListener;

import com.uihomies.androidfitness.R;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.TimerTask;

public class Game extends ActionBarActivity {

    public final String TAG = "Game Activity";
    public static int myHeartRate = 80;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Setting the fonts

        // Stop Button
        Button tv1=(Button)findViewById(R.id.stopButton);
        Typeface face1=Typeface.createFromAsset(getAssets(),"fonts/Marker_Felt_Bold.ttf");
        tv1.setTypeface(face1);

        // Target Label
        TextView tv2=(TextView)findViewById(R.id.targetLabel);
        Typeface face2=Typeface.createFromAsset(getAssets(),"fonts/Marker_Felt_Bold.ttf");
        tv2.setTypeface(face2);

        // Heart rate Label
        TextView tv3=(TextView)findViewById(R.id.heartRateLabel);
        Typeface face3=Typeface.createFromAsset(getAssets(),"fonts/Marker_Felt.ttf");
        tv3.setTypeface(face3);

        // Simulation
        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updateTextView();
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        t.start();
    }

    public void updateTextView() {
        TextView tv3=(TextView)findViewById(R.id.heartRateLabel);
        tv3.setText(Integer.toString(myHeartRate));
        if(myHeartRate <= 100){
            myHeartRate++;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
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

    public void stopButtonClick(View view) {
        // Load next activity
        Intent intent = new Intent(Game.this, Summary.class);
        startActivity(intent);
    }
}
