package com.example.hp.firstapp;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import com.android.internal.telephony.ITelephony;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberUtils;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
public class MyCallControllerActivity extends AppCompatActivity {
    /** Called when the activity is first created. */
    CheckBox blockAll_cb;//,blockcontacts_cb;
    BroadcastReceiver CallBlocker;
    TelephonyManager telephonyManager;
    ITelephony telephonyService;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_call_controller);
        initviews();
        blockAll_cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                CallBlocker =new BroadcastReceiver()
                {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        // TODO Auto-generated method stub
                        telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
                        //Java Reflections
                        Class c = null;
                        try {
                            c = Class.forName(telephonyManager.getClass().getName());
                        } catch (ClassNotFoundException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        Method m = null;
                        try {
                            m = c.getDeclaredMethod("getITelephony");
                        } catch (SecurityException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (NoSuchMethodException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        m.setAccessible(true);
                        try {
                            telephonyService = (ITelephony)m.invoke(telephonyManager);
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
                        telephonyManager.listen(callBlockListener, PhoneStateListener.LISTEN_CALL_STATE);
                    }//onReceive()
                    PhoneStateListener callBlockListener = new PhoneStateListener()
                    {
                        public void onCallStateChanged(int state, String incomingNumber)
                        {
                            if(state==TelephonyManager.CALL_STATE_RINGING)
                            {
                                if(blockAll_cb.isChecked())
                                {
                                    try {
                                        telephonyService.endCall();
                                       // telephonyService.answerRingingCall();

                                    } catch (RemoteException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    };
                };//BroadcastReceiver
                IntentFilter filter= new IntentFilter("android.intent.action.PHONE_STATE");
                registerReceiver(CallBlocker, filter);
            }
        });
    }
    public void initviews()
    {
        blockAll_cb=(CheckBox)findViewById(R.id.cbBlockAll);
        //blockcontacts_cb=(CheckBox)findViewById(R.id.cbBlockContacts);
    }
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if (CallBlocker != null)
        {
            unregisterReceiver(CallBlocker);
            CallBlocker = null;
        }
    }
}