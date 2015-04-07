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
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import zephyr.android.HxMBT.*;
import com.uihomies.androidfitness.NewConnectedListener;

import com.uihomies.androidfitness.R;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Set;
import java.util.TimerTask;

public class Game extends ActionBarActivity implements TextToSpeech.OnInitListener {

    public final String TAG = "Game Activity";
    public static int myHeartRate = 80;
    public static int iterationsToWait = 0;
    public static int iterationsToWaitAgain = 0;
    public static boolean iAmDone = false;
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
    }

   @Override
   public void onResume(){
       super.onResume();

       // PASTED
       myHeartRate = 80;
       iterationsToWait = 0;
       iterationsToWaitAgain = 0;
       iAmDone = false;

       // Hiding status and navigation bar
       View decorView = getWindow().getDecorView();
       int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
               | View.SYSTEM_UI_FLAG_FULLSCREEN;
       decorView.setSystemUiVisibility(uiOptions);

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
               System.out.println("Went in run");
               try {
                   while (!isInterrupted()) {
                       System.out.println("Went in ! interrupted");
                       Thread.sleep(500);
                       runOnUiThread(new Runnable() {
                           @Override
                           public void run() {
                               playWithTheHeartRate();
                           }
                       });
                       if(iAmDone) {
                           System.out.println("Went in iAmDone");
                           interrupt();
                           // Load next activity
                           Intent intent = new Intent(Game.this, Summary.class);
                           startActivity(intent);
                       }
                   }
                   System.out.println("Went in AFTER ! interrupted");
               } catch (InterruptedException e) {
               }
           }
       };

       t.start();
   }

    @Override
    public void onWindowFocusChanged(boolean hasFocus){
        super.onWindowFocusChanged(hasFocus);
        // Hiding status and navigation bar
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    public void targetReached() {
        ImageView iv = (ImageView)findViewById(R.id.rocketImage);
        iv.setImageResource(R.drawable.bigrocketgreen);
        TextView tv=(TextView)findViewById(R.id.targetLabel);
        String msg = "HOLD AT 100 BPM";
        tv.setText(msg);
        speakOut(msg);
    }

    public void playWithTheHeartRate() {
        if(myHeartRate > 100) {
            targetReached();
            iterationsToWait++;
            if(iterationsToWait > 10) {
                continueHeartSimulation();
            }
        } else {
            TextView tv = (TextView) findViewById(R.id.heartRateLabel);
            tv.setText(Integer.toString(myHeartRate));
            myHeartRate++;
        }
    }

    public void continueHeartSimulation() {
        if(myHeartRate > 120) {
            secondTargetReached();
            iterationsToWaitAgain++;
            if(iterationsToWaitAgain > 10) {
                finishIt();
            }
        }
        else {
            ImageView iv = (ImageView) findViewById(R.id.rocketImage);
            iv.setImageResource(R.drawable.bigrocketblue);
            TextView tv = (TextView) findViewById(R.id.targetLabel);
            tv.setText("120 BPM");
            TextView tv1 = (TextView) findViewById(R.id.heartRateLabel);
            tv1.setText(Integer.toString(myHeartRate));
            myHeartRate++;
        }
    }

    public void secondTargetReached() {
        ImageView iv = (ImageView)findViewById(R.id.rocketImage);
        iv.setImageResource(R.drawable.bigrocketgreen);
        TextView tv=(TextView)findViewById(R.id.targetLabel);
        tv.setText("HOLD AT 120 BPM");
    }

    public void finishIt() {
        iAmDone = true;
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

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.CANADA);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                speakOut("");
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }

    private void speakOut(String msg) {
        tts.speak(msg, TextToSpeech.QUEUE_FLUSH, null);
    }
}
