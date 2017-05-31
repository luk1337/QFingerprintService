package com.qualcomm.qti.biometrics.fingerprint.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Process;
import android.util.Log;

public class ShutdownReceiver extends BroadcastReceiver {
    private static final String TAG = "qfp-service";

    public static int getUserId(int uid) {
        return uid / 100000;
    }

    public void onReceive(Context context, Intent intent) {
        Native jni = new Native();
        if (jni.isFingerprintEnabled()) {
            Log.i(TAG, "fingerprint not enabled");
            return;
        }

        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            Log.d(TAG, "boot complete");
            context.startService(new Intent(context, FingerprintService.class));
        } else if (intent.getAction().equals(Intent.ACTION_SHUTDOWN)) {
            int userId = getUserId(Process.myUid());
            if (userId != 0) {
                Log.d(TAG, "got shutdown from second space (Ignore it), userId: " + userId);
                return;
            }

            Log.d(TAG, "got shutdown from main space");

            long open = jni.open();
            if (open == 0 || open == -1) {
                Log.e(TAG, "failed native open");
                return;
            }

            int notifyPowerState = jni.notifyPowerState(open, 0);
            if (notifyPowerState != 0) {
                Log.e(TAG, "failed to shutdown " + notifyPowerState);
            }

            context.stopService(new Intent(context, FingerprintService.class));
        }
    }
}