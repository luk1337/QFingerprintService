package com.qualcomm.qti.biometrics.fingerprint.service;

import android.util.*;
import android.os.*;
import android.os.PowerManager.*;
import android.content.*;
import android.content.SharedPreferences.*;
import android.app.*;

public class AndroidServices implements IAndroidServices
{
    private static final String PREFS_SHOW_LIVENESS_ALERT_NAME = "PrefsShowLivenessAlert";
    static final int REQUEST_KEYGUARD = 0;
    static final int REQUEST_PAYMENT = 1;
    private static final String SHOW_LIVENESS_ALERT_STR = "ShowLivenessAlert";
    static final String TAG = "qfp-service";
    private static boolean livenessAlertIsShown;
    AlarmReceiver alarmReceiver;
    private Context mContext;
    private WakeLock mFullWakelock;
    private int mFullWakelockCount;
    private Handler mHandler;
    private WakeLock mWakelock;
    private int mWakelockCount;
    
    static {
        AndroidServices.livenessAlertIsShown = false;
    }
    
    public AndroidServices(final Context mContext) {
        this.alarmReceiver = new AlarmReceiver();
        this.mWakelockCount = 0;
        this.mFullWakelockCount = 0;
        this.mHandler = new Handler();
        Log.d("qfp-service", "AndroidServices ctor");
        this.mContext = mContext;
        final PowerManager powerManager = (PowerManager)mContext.getSystemService("power");
        this.mWakelock = powerManager.newWakeLock(1, "qfp-service");
        this.mFullWakelock = powerManager.newWakeLock(805306394, "qfp-service");
    }
    
    private void uiResetLivenessAlert() {
        final Editor edit = this.mContext.getSharedPreferences("PrefsShowLivenessAlert", 0).edit();
        edit.putString("ShowLivenessAlert", "Yes");
        edit.commit();
    }
    
    private void uiShowLivenessAlert() {
        if (AndroidServices.livenessAlertIsShown) {
            return;
        }
        if (this.mContext.getSharedPreferences("PrefsShowLivenessAlert", 0).getString("ShowLivenessAlert", "Yes").contains("No")) {
            return;
        }
    }
    
    @Override
    public int checkRequestingAppType() {
        if (((KeyguardManager)this.mContext.getSystemService("keyguard")).inKeyguardRestrictedInputMode()) {
            Log.d("qfp-service", "keyguard is locked at time of request");
            return 0;
        }
        Log.d("qfp-service", "keyguard is NOT locked at time of request");
        return 1;
    }
    
    @Override
    public void resetLivenessAlert() {
        synchronized (this) {
            this.mHandler.post((Runnable)new Runnable() {
                @Override
                public void run() {
                    AndroidServices.this.uiResetLivenessAlert();
                }
            });
        }
    }
    
    @Override
    public void setTimer(final int i) {
        synchronized (this) {
            Log.d("qfp-service", "setTimer " + i);
            if (i > 0) {
                this.alarmReceiver.set(this.mContext, i);
            }
            else {
                this.alarmReceiver.cancel(this.mContext);
            }
        }
    }
    
    @Override
    public void setWakelock(final boolean b, final boolean b2) {
        // monitorenter(this)
        Label_0089: {
            if (!b2) {
                break Label_0089;
            }
            Label_0044: {
                if (!b) {
                    break Label_0044;
                }
                try {
                    if (this.mWakelockCount++ == 0) {
                        Log.d("qfp-service", "acquire partial wakelock");
                        this.mWakelock.acquire();
                    }
                    else if (!b && --this.mWakelockCount == 0) {
                        Log.d("qfp-service", "release partial wakelock");
                        this.mWakelock.release();
                    }
                    return;
                }
                finally {
                }
                // monitorexit(this)
            }
        }
        if (b && this.mFullWakelockCount++ == 0) {
            Log.d("qfp-service", "acquire full wakelock");
            this.mFullWakelock.acquire();
            return;
        }
        if (!b && --this.mFullWakelockCount == 0) {
            Log.d("qfp-service", "release full wakelock");
            this.mFullWakelock.release();
        }
    }
    
    @Override
    public void showLivenessAlert() {
        synchronized (this) {
            this.mHandler.post((Runnable)new Runnable() {
                @Override
                public void run() {
                    AndroidServices.this.uiShowLivenessAlert();
                }
            });
        }
    }
    
    public static class AlarmReceiver extends BroadcastReceiver
    {
        Native jni;
        
        public AlarmReceiver() {
            this.jni = new Native();
        }
        
        public void cancel(final Context context) {
            ((AlarmManager)context.getSystemService("alarm")).cancel(PendingIntent.getBroadcast(context, 0, new Intent(context, (Class)AlarmReceiver.class), 0));
        }
        
        public void onReceive(final Context context, final Intent intent) {
            final long open = this.jni.open();
            if (open == 0L || open == -1L) {
                Log.e("qfp-service", "failed native open");
            }
            else {
                Log.d("qfp-service", "notify Alarm!");
                this.jni.notifyAlarm(open);
                this.jni.close(open);
            }
        }
        
        public void set(final Context context, final int n) {
            ((AlarmManager)context.getSystemService("alarm")).set(0, System.currentTimeMillis() + n, PendingIntent.getBroadcast(context, 0, new Intent(context, (Class)AlarmReceiver.class), 0));
        }
    }
}
