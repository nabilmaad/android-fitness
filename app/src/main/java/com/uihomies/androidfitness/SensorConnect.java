package com.uihomies.androidfitness;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

import zephyr.android.HxMBT.BTClient;
import zephyr.android.HxMBT.ZephyrProtocol;


public class SensorConnect extends ActionBarActivity {

    // Zephyr specific
    /** Called when the activity is first created. */
    BluetoothAdapter adapter = null;
    BTClient _bt;
    ZephyrProtocol _protocol;
    NewConnectedListener _NConnListener;
    private final int HEART_RATE = 0x100;
    private final int INSTANT_SPEED = 0x101;

    // Zephyr specific
    final Handler Newhandler = new Handler(){
        public void handleMessage(Message msg)
        {
            TextView tv;
            switch (msg.what)
            {
                case HEART_RATE:
                    String HeartRatetext = msg.getData().getString("HeartRate");
                    tv = (TextView)findViewById(R.id.labelHeartRate);
                    System.out.println("Heart Rate Info is "+ HeartRatetext);
                    if (tv != null)tv.setText(HeartRatetext);
                    break;

                case INSTANT_SPEED:
                    String InstantSpeedtext = msg.getData().getString("InstantSpeed");
                    tv = (EditText)findViewById(R.id.labelInstantSpeed);
                    if (tv != null)tv.setText(InstantSpeedtext);

                    break;

            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_connect);

        // Setting the fonts

        // Sign up header
        TextView tv1=(TextView)findViewById(R.id.connectTitle);
        Typeface face1=Typeface.createFromAsset(getAssets(),"fonts/Marker_Felt_Bold.ttf");
        tv1.setTypeface(face1);

        // Sign up question
        TextView tv2=(TextView)findViewById(R.id.instructionLabel);
        Typeface face2=Typeface.createFromAsset(getAssets(),"fonts/Marker_Felt.ttf");
        tv2.setTypeface(face2);

        // Next button
        TextView tv3=(TextView)findViewById(R.id.nextButton);
        Typeface face3=Typeface.createFromAsset(getAssets(),"fonts/DS_Marker_Felt.ttf");
        tv3.setTypeface(face3);

        TextView tv4=(TextView)findViewById(R.id.ButtonConnect);
        Typeface face4=Typeface.createFromAsset(getAssets(),"fonts/DS_Marker_Felt.ttf");
        tv4.setTypeface(face4);

        TextView tv5=(TextView)findViewById(R.id.instructionLabel);
        Typeface face5=Typeface.createFromAsset(getAssets(),"fonts/Marker_Felt.ttf");
        tv5.setTypeface(face5);

        TextView tv6=(TextView)findViewById(R.id.heartRateLabel);
        Typeface face6=Typeface.createFromAsset(getAssets(),"fonts/Marker_Felt.ttf");
        tv6.setTypeface(face6);

        // Zephyr specific
        /*Sending a message to android that we are going to initiate a pairing request*/
        IntentFilter filter = new IntentFilter("android.bluetooth.device.action.PAIRING_REQUEST");
        /*Registering a new BTBroadcast receiver from the Main Activity context with pairing request event*/
        this.getApplicationContext().registerReceiver(new BTBroadcastReceiver(), filter);
        // Registering the BTBondReceiver in the application that the status of the receiver has changed to Paired
        IntentFilter filter2 = new IntentFilter("android.bluetooth.device.action.BOND_STATE_CHANGED");
        this.getApplicationContext().registerReceiver(new BTBondReceiver(), filter2);

        //Obtaining the handle to act on the CONNECT button
        String ErrorText  = "Not Connected to HxM ! !";
        System.out.println(ErrorText);

        Button btnConnect = (Button) findViewById(R.id.ButtonConnect);
        if (btnConnect != null)
        {
            // TODO When clicking connect with bluetooth off and no adapter, the app crashes. Error message needed.
            btnConnect.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String BhMacID = "00:07:80:9D:8A:E8";
                    //String BhMacID = "00:07:80:88:F6:BF";
                    adapter = BluetoothAdapter.getDefaultAdapter();

                    Set<BluetoothDevice> pairedDevices = adapter.getBondedDevices();

                    if (pairedDevices.size() > 0)
                    {
                        for (BluetoothDevice device : pairedDevices)
                        {
                            if (device.getName().startsWith("HXM"))
                            {
                                BluetoothDevice btDevice = device;
                                BhMacID = btDevice.getAddress();
                                break;

                            }
                        }


                    }

                    //BhMacID = btDevice.getAddress();
                    BluetoothDevice Device = adapter.getRemoteDevice(BhMacID);
                    String DeviceName = Device.getName();
                    _bt = new BTClient(adapter, BhMacID);
                    _NConnListener = new NewConnectedListener(Newhandler,Newhandler);
                    _bt.addConnectedEventListener(_NConnListener);

                    TextView tv1 = (TextView)findViewById(R.id.labelHeartRate);
                    tv1.setText("000");

                    //tv1 = 	(EditText)findViewById(R.id.labelSkinTemp);
                    //tv1.setText("0.0");

                    //tv1 = 	(EditText)findViewById(R.id.labelPosture);
                    //tv1.setText("000");

                    //tv1 = 	(EditText)findViewById(R.id.labelPeakAcc);
                    //tv1.setText("0.0");
                    if(_bt.IsConnected())
                    {
                        _bt.start();
                        String ErrorText  = "Connected to HxM "+DeviceName;
                        System.out.println(ErrorText);

                        // Make stuff visible
                        Button connectButton = (Button) findViewById(R.id.ButtonConnect);
                        connectButton.setBackgroundColor(Color.GRAY);

                        TextView tv = (TextView) findViewById(R.id.heartRateLabel);
                        tv.setVisibility(View.VISIBLE);
                        tv = (TextView) findViewById(R.id.labelHeartRate);
                        tv.setVisibility(View.VISIBLE);

                        Button playButton = (Button) findViewById(R.id.nextButton);
                        playButton.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        String ErrorText  = "Unable to Connect !";
                        System.out.println(ErrorText);
                    }
                }
            });
        }
//        /*Obtaining the handle to act on the DISCONNECT button*/
//        Button btnDisconnect = (Button) findViewById(R.id.ButtonDisconnect);
//        if (btnDisconnect != null)
//        {
//            btnDisconnect.setOnClickListener(new View.OnClickListener() {
//                @Override
//				/*Functionality to act if the button DISCONNECT is touched*/
//                public void onClick(View v) {
//                    // TODO Auto-generated method stub
//					/*Reset the global variables*/
//                    TextView tv = (TextView) findViewById(R.id.labelStatusMsg);
//                    String ErrorText  = "Disconnected from HxM!";
//                    tv.setText(ErrorText);
//
//					/*This disconnects listener from acting on received messages*/
//                    _bt.removeConnectedEventListener(_NConnListener);
//					/*Close the communication with the device & throw an exception if failure*/
//                    _bt.Close();
//
//                }
//            });
//        }
    }

    // Zephyr specific
    private class BTBondReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle b = intent.getExtras();
            BluetoothDevice device = adapter.getRemoteDevice(b.get("android.bluetooth.device.extra.DEVICE").toString());
            Log.d("Bond state", "BOND_STATED = " + device.getBondState());
        }
    }

    // Zephyr specific
    private class BTBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("BTIntent", intent.getAction());
            Bundle b = intent.getExtras();
            Log.d("BTIntent", b.get("android.bluetooth.device.extra.DEVICE").toString());
            Log.d("BTIntent", b.get("android.bluetooth.device.extra.PAIRING_VARIANT").toString());
            try {
                BluetoothDevice device = adapter.getRemoteDevice(b.get("android.bluetooth.device.extra.DEVICE").toString());
                Method m = BluetoothDevice.class.getMethod("convertPinToBytes", new Class[] {String.class} );
                byte[] pin = (byte[])m.invoke(device, "1234");
                m = device.getClass().getMethod("setPin", new Class [] {pin.getClass()});
                Object result = m.invoke(device, pin);
                Log.d("BTTest", result.toString());
            } catch (SecurityException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (NoSuchMethodException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sensor_connect, menu);
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
        // Load next activity
        Intent intent = new Intent(SensorConnect.this, Game.class);
        startActivity(intent);
    }
}
