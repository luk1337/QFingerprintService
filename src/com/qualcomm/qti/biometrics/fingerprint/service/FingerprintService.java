package com.qualcomm.qti.biometrics.fingerprint.service;

import android.app.*;
import android.util.*;
import android.content.*;
import android.os.*;

public class FingerprintService extends Service
{
    static final String TAG = "qfp-service";
    IAndroidServices androidServices;
    SleepReceiver sleepReceiver;
    
    public FingerprintService() {
        this.sleepReceiver = new SleepReceiver();
    }
    
    public IBinder onBind(final Intent intent) {
        if (IFingerprintService.class.getName().equals(intent.getAction())) {
            try {
                Log.d("qfp-service", "returning FingerprintService for intent: " + intent.getAction());
                return (IBinder)new FingerprintServiceStub((Context)this);
            }
            catch (Exception ex) {
                Log.e("qfp-service", ex.getLocalizedMessage());
                return null;
            }
        }
        if (IFingerprintAuthenticator.class.getName().equals(intent.getAction())) {
            try {
                Log.d("qfp-service", "returning FingerprintAuthenticator for intent: " + intent.getAction());
                return (IBinder)new FingerprintAuthenticator((Context)this);
            }
            catch (Exception ex2) {
                Log.e("qfp-service", ex2.getLocalizedMessage());
                return null;
            }
        }
        if (intent != null) {
            Log.d("qfp-service", "onBind with intent " + intent.getAction());
        }
        else {
            Log.d("qfp-service", "onBind with null intent");
        }
        return null;
    }
    
    public void onCreate() {
        Log.d("qfp-service", "onCreate");
        this.registerReceiver((BroadcastReceiver)this.sleepReceiver, new IntentFilter("android.intent.action.SCREEN_OFF"));
        this.registerReceiver((BroadcastReceiver)this.sleepReceiver, new IntentFilter("android.intent.action.SCREEN_ON"));
        this.androidServices = new AndroidServices((Context)this);
        final Native native1 = new Native();
        native1.registerAndroidServices(this.androidServices);
        final long open = native1.open();
        if (open == 0L || open == -1L) {
            Log.e("qfp-service", "failed native open");
        }
        else {
            Log.d("qfp-service", "SUCCESS native open");
            int n;
            if (((PowerManager)this.getSystemService("power")).isInteractive()) {
                n = 2;
            }
            else {
                n = 1;
            }
            native1.notifyPowerState(open, n);
        }
    }
    
    public void onDestroy() {
        Log.e("qfp-service", "onDestroy");
    }
    
    public int onStartCommand(final Intent intent, final int n, final int n2) {
        Log.d("qfp-service", "onStartCommand return STICKY");
        return 1;
    }
    
    class SleepReceiver extends BroadcastReceiver
    {
        Native jni;
        
        SleepReceiver() {
            this.jni = new Native();
        }
        
        public void onReceive(final Context context, final Intent intent) {
            final long open = this.jni.open();
            if (open == 0L || open == -1L) {
                Log.e("qfp-service", "failed native open");
            }
            else {
                if ("android.intent.action.SCREEN_OFF".equals(intent.getAction())) {
                    Log.d("qfp-service", "screen off");
                    this.jni.notifyPowerState(open, 1);
                }
                else if ("android.intent.action.SCREEN_ON".equals(intent.getAction())) {
                    Log.d("qfp-service", "screen on");
                    this.jni.notifyPowerState(open, 2);
                }
                this.jni.close(open);
            }
        }
    }
}
