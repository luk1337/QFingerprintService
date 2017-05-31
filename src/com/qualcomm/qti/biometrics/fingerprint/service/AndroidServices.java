package com.qualcomm.qti.biometrics.fingerprint.service;

import android.app.AlarmManager;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;

public class AndroidServices implements IAndroidServices {
    private static final String TAG = "qfp-service";
    private static final String PREFS_SHOW_LIVENESS_ALERT_NAME = "PrefsShowLivenessAlert";
    private static final String SHOW_LIVENESS_ALERT_STR = "ShowLivenessAlert";
    private static final int REQUEST_KEYGUARD = 0;
    private static final int REQUEST_PAYMENT = 1;

    private Context mContext;

    private AlarmReceiver mAlarmReceiver;
    private Handler mHandler;

    private WakeLock mWakelock;
    private int mWakelockCount;
    private WakeLock mFullWakelock;
    private int mFullWakelockCount;

    private static boolean livenessAlertIsShown;

    static {
        AndroidServices.livenessAlertIsShown = false;
    }

    public AndroidServices(Context mContext) {
        Log.d(TAG, "AndroidServices ctor");
        this.mContext = mContext;

        mAlarmReceiver = new AlarmReceiver();
        mHandler = new Handler();

        PowerManager powerManager = (PowerManager) mContext.getSystemService("power");
        mWakelock = powerManager.newWakeLock(1, "qfp-service");
        mFullWakelock = powerManager.newWakeLock(805306394, "qfp-service");
    }

    private void uiResetLivenessAlert() {
        final Editor edit = mContext.getSharedPreferences(PREFS_SHOW_LIVENESS_ALERT_NAME, 0).edit();
        edit.putString(SHOW_LIVENESS_ALERT_STR, "Yes");
        edit.commit();
    }

    private void uiShowLivenessAlert() {
        if (AndroidServices.livenessAlertIsShown) {
            return;
        }

        if (mContext.getSharedPreferences(PREFS_SHOW_LIVENESS_ALERT_NAME, 0).getString(SHOW_LIVENESS_ALERT_STR, "Yes").contains("No")) {
            return;
        }
    }

    @Override
    public int checkRequestingAppType() {
        if (((KeyguardManager) mContext.getSystemService("keyguard")).inKeyguardRestrictedInputMode()) {
            Log.d(TAG, "keyguard is locked at time of request");
            return REQUEST_KEYGUARD;
        }

        Log.d(TAG, "keyguard is NOT locked at time of request");
        return REQUEST_PAYMENT;
    }

    @Override
    public void resetLivenessAlert() {
        synchronized (this) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    AndroidServices.this.uiResetLivenessAlert();
                }
            });
        }
    }

    @Override
    public void setTimer(int delay) {
        synchronized (this) {
            Log.d(TAG, "setTimer " + delay);
            if (delay > 0) {
                mAlarmReceiver.set(mContext, delay);
            } else {
                mAlarmReceiver.cancel(mContext);
            }
        }
    }

    @Override
    public void setWakelock(boolean active, boolean partial) {
        if (partial) {
            if (active && mWakelockCount++ == 0) {
                Log.d(TAG, "acquire partial wakelock");
                mWakelock.acquire();
            } else if (!active && mWakelockCount-- == 0) {
                Log.d(TAG, "release partial wakelock");
                mWakelock.release();
            }
        } else {
            if (active && mFullWakelockCount++ == 0) {
                Log.d(TAG, "acquire full wakelock");
                mFullWakelock.acquire();
                return;
            } else if (!active && mFullWakelockCount-- == 0) {
                Log.d(TAG, "release full wakelock");
                mFullWakelock.release();
            }
        }
    }

    @Override
    public void showLivenessAlert() {
        synchronized (this) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    AndroidServices.this.uiShowLivenessAlert();
                }
            });
        }
    }

    public static class AlarmReceiver extends BroadcastReceiver {
        Native mJni;

        public AlarmReceiver() {
            mJni = new Native();
        }

        public void cancel(Context context) {
            ((AlarmManager) context.getSystemService("alarm")).cancel(PendingIntent.getBroadcast(context, 0, new Intent(context, AlarmReceiver.class), 0));
        }

        public void onReceive(Context context, Intent intent) {
            long open = mJni.open();
            if (open == 0 || open == -1) {
                Log.e(TAG, "failed native open");
            } else {
                Log.d(TAG, "notify Alarm!");
                mJni.notifyAlarm(open);
                mJni.close(open);
            }
        }

        public void set(Context context, int delay) {
            ((AlarmManager) context.getSystemService("alarm")).set(0, System.currentTimeMillis() + delay, PendingIntent.getBroadcast(context, 0, new Intent(context, AlarmReceiver.class), 0));
        }
    }
}
