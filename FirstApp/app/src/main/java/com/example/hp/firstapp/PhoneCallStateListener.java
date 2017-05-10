package com.example.hp.firstapp;
import java.lang.reflect.Method;
import java.util.Arrays;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.telephony.PhoneStateListener;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.android.internal.telephony.ITelephony;

public class PhoneCallStateListener extends PhoneStateListener {

    private Context context;
    Boolean flag=true,status;
    String []suite;

    public PhoneCallStateListener(Context context){

        suite=new String[Blknum.getSuite().length];
        suite= Arrays.copyOf(Blknum.getSuite(), Blknum.getSuite().length);
        for(int i=0;i<Blknum.getSuite().length;i++)
        {
            System.out.print(suite[i]+" ");
        }
        status=Blknum.getStatus();
        this.context = context;
    }


    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
       // SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(context);
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(context, 0,
                new Intent(SENT), 0);

        PendingIntent deliveredPI = PendingIntent.getBroadcast(context, 0,
                new Intent(DELIVERED), 0);
        suite= Arrays.copyOf(Blknum.getSuite(), Blknum.getSuite().length);
        status=Blknum.getStatus();
        if(status) {
            for (int i = 0; i < suite.length; i++) {
                if (incomingNumber.equalsIgnoreCase("+91" + suite[i])) {
                    flag = false;
                    break;
                }
            }
        }
        else
        {
            flag=false;
        }
        System.out.println(flag);
        switch (state) {

            case TelephonyManager.CALL_STATE_RINGING:

              //  String block_number = prefs.getString("block_number", null);

                AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                //Turn ON the mute
                audioManager.setStreamMute(AudioManager.STREAM_RING, true);
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                try {
                  //  Toast.makeText(context, "in "+block_number, Toast.LENGTH_LONG).show();
                    Class clazz = Class.forName(telephonyManager.getClass().getName());
                    Method method = clazz.getDeclaredMethod("getITelephony");
                    method.setAccessible(true);
                    ITelephony telephonyService = (ITelephony) method.invoke(telephonyManager);
                    //Checking incoming call number
                 //   System.out.println("Call "+block_number);

                    if (flag) {
                        //telephonyService.silenceRinger();//Security exception problem
                        telephonyService = (ITelephony) method.invoke(telephonyManager);
                        telephonyService.silenceRinger();
                     //   System.out.println(" in  "+block_number);
                        telephonyService.endCall();
                        try {
                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage("+91"+incomingNumber, null, "I am driving right now. Please call later",sentPI, deliveredPI);
                          //  Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
                        }

                        catch (Exception e) {
                         //   Toast.makeText(getApplicationContext(), "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
                }
                //Turn OFF the mute
                audioManager.setStreamMute(AudioManager.STREAM_RING, false);
                break;
            case PhoneStateListener.LISTEN_CALL_STATE:

        }
        super.onCallStateChanged(state, incomingNumber);
    }}