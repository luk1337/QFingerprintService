package com.qualcomm.qti.biometrics.fingerprint.service;

import android.app.*;
import android.util.*;
import android.content.*;
import android.os.*;

public class FingerprintService extends Service {
    private static final String TAG = "qfp-service";

    private SleepReceiver sleepReceiver;

    public FingerprintService() {
        sleepReceiver = new SleepReceiver();
    }

    public IBinder onBind(Intent intent) {
        if (intent == null) {
            Log.d(TAG, "onBind with null intent");
            return null;
        }

        if (intent.getAction().equals(IFingerprintService.class.getName())) {
            try {
                Log.d(TAG, "returning FingerprintService for intent: " + intent.getAction());
                return (IBinder) new FingerprintServiceStub(this);
            } catch (Exception ex) {
                Log.e(TAG, ex.getLocalizedMessage());
            }
        } else if (intent.getAction().equals(IFingerprintAuthenticator.class.getName())) {
            try {
                Log.d(TAG, "returning FingerprintAuthenticator for intent: " + intent.getAction());
                return (IBinder) new FingerprintAuthenticator(this);
            } catch (Exception ex) {
                Log.e(TAG, ex.getLocalizedMessage());
            }
        } else {
            Log.d(TAG, "onBind with intent " + intent.getAction());
        }

        return null;
    }

    public void onCreate() {
        Log.d(TAG, "onCreate");

        registerReceiver((BroadcastReceiver) sleepReceiver, new IntentFilter(Intent.ACTION_SCREEN_OFF));
        registerReceiver((BroadcastReceiver) sleepReceiver, new IntentFilter(Intent.ACTION_SCREEN_ON));
        IAndroidServices androidServices = new AndroidServices(this);

        Native jni = new Native();
        jni.registerAndroidServices(androidServices);

        final long open = jni.open();
        if (open == 0L || open == -1L) {
            Log.e(TAG, "failed native open");
            return;
        }

        Log.d(TAG, "SUCCESS native open");
        jni.notifyPowerState(open,
                ((PowerManager) getSystemService("power")).isInteractive() ? 2 : 1);
    }

    public void onDestroy() {
        Log.e(TAG, "onDestroy");
    }

    public int onStartCommand(Intent intent, int n, int n2) {
        Log.d(TAG, "onStartCommand return STICKY");
        return 1;
    }

    class SleepReceiver extends BroadcastReceiver {
        private Native mJni;

        SleepReceiver() {
            mJni = new Native();
        }

        public void onReceive(Context context, Intent intent) {
            long open = mJni.open();
            if (open == 0 || open == -1) {
                Log.e(TAG, "failed native open");
                return;
            }

            if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                Log.d(TAG, "screen off");
                mJni.notifyPowerState(open, 1);
            } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
                Log.d(TAG, "screen on");
                mJni.notifyPowerState(open, 2);
            }

            mJni.close(open);
        }
    }
}