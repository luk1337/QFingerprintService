package com.qualcomm.qti.biometrics.fingerprint.service;

import android.content.*;
import android.util.*;
import android.os.*;

public class ShutdownReceiver extends BroadcastReceiver
{
    private final String TAG;
    
    public ShutdownReceiver() {
        this.TAG = "qfp-service";
    }
    
    public static final int getUserId(final int n) {
        return n / 100000;
    }
    
    public void onReceive(final Context context, final Intent intent) {
        if (!new Native().isFingerprintEnabled()) {
            Log.i("qfp-service", "fingerprint not enabled");
            return;
        }
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            Log.d("qfp-service", "boot complete");
            context.startService(new Intent(context, (Class)FingerprintService.class));
        }
        else if ("android.intent.action.ACTION_SHUTDOWN".equals(intent.getAction())) {
            final int userId = getUserId(android.os.Process.myUid());
            if (userId == 0) {
                Log.d("qfp-service", "got shutdown from main space");
                final Native native1 = new Native();
                final long open = native1.open();
                if (open == 0L || open == -1L) {
                    Log.e("qfp-service", "failed native open");
                    return;
                }
                final int notifyPowerState = native1.notifyPowerState(open, 0);
                if (notifyPowerState != 0) {
                    Log.e("qfp-service", "failed to shutdown " + notifyPowerState);
                }
                context.stopService(new Intent(context, (Class)FingerprintService.class));
            }
            else {
                Log.d("qfp-service", "got shutdown from second space (Ignore it), userId: " + userId);
            }
        }
    }
}
