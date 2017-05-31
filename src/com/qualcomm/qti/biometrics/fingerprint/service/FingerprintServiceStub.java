package com.qualcomm.qti.biometrics.fingerprint.service;

import android.content.*;
import android.app.*;
import android.util.*;
import android.os.*;

public class FingerprintServiceStub implements AutoCloseable
{
    private final String TAG;
    private Context mContext;
    private long mJniContext;
    private final Native mNative;
    
    public FingerprintServiceStub(final Context mContext) throws RemoteException {
        this.TAG = "qfp-service";
        this.mJniContext = 0L;
        this.mContext = null;
        this.mNative = new Native();
        this.mContext = mContext;
        this.mJniContext = this.mNative.open();
        if (this.mJniContext == 0L) {
            throw new RemoteException("unable to connect to qfp service");
        }
    }
    
    private String getUserName() throws RemoteException {
        return Integer.toString(ActivityManagerNative.getDefault().getCurrentUser().id);
    }
    
    private void throwIfFail(final int i) throws RemoteException {
        if (i != 0) {
            throw new RemoteException("Error: " + i);
        }
    }
    
    public void cancel() throws RemoteException {
        Log.d("qfp-service", "cancel()");
        this.throwIfFail(this.mNative.cancel(this.mJniContext));
    }
    
    @Override
    public void close() {
        synchronized (this) {
            if (this.mJniContext != 0L) {
                this.mNative.close(this.mJniContext);
            }
            this.mJniContext = 0L;
        }
    }
    
    public void disableFingerEvent() throws RemoteException {
        Log.d("qfp-service", "disableFingerEvent()");
        this.throwIfFail(this.mNative.disableFingerEvent(this.mJniContext));
    }
    
    public void enableFingerEvent(final int i) throws RemoteException {
        Log.d("qfp-service", "enableFingerEvent(" + i + ")");
        this.throwIfFail(this.mNative.enableFingerEvent(this.mJniContext, i));
    }
    
    public void enroll(final int i, final IFingerprintServiceCallback fingerprintServiceCallback) throws RemoteException {
        final String userName = this.getUserName();
        Log.d("qfp-service", "enroll(" + i + ") - " + userName);
        this.throwIfFail(this.mNative.enroll(this.mJniContext, new JniCallback(fingerprintServiceCallback), i, userName));
    }
    
    public void finalize() {
        this.close();
    }
    
    public boolean getLivenessEnabled(final byte[] array) throws RemoteException {
        boolean b = true;
        Log.d("qfp-service", "getLivenessEnabled()");
        final int[] array2 = { 0 };
        this.throwIfFail(this.mNative.getLivenessEnabled(this.mJniContext, array, array2));
        if (array2[0] == 0) {
            b = false;
        }
        return b;
    }
    
    public void match(final int i, final IFingerprintServiceCallback fingerprintServiceCallback) throws RemoteException {
        final String userName = this.getUserName();
        Log.d("qfp-service", "match(" + i + ") - " + userName);
        this.throwIfFail(this.mNative.match(this.mJniContext, new JniCallback(fingerprintServiceCallback), i, userName, new byte[0], "", 0));
    }
    
    public void matchAny(final int i, final IFingerprintServiceCallback fingerprintServiceCallback) throws RemoteException {
        Log.d("qfp-service", "matchAny(" + i + ")");
        this.throwIfFail(this.mNative.match(this.mJniContext, new JniCallback(fingerprintServiceCallback), i, "", new byte[0], "", 0));
    }
    
    public void remove(final int i, final IFingerprintServiceCallback fingerprintServiceCallback) throws RemoteException {
        final String userName = this.getUserName();
        Log.d("qfp-service", "remove(" + i + ")");
        this.throwIfFail(this.mNative.remove(this.mJniContext, new JniCallback(fingerprintServiceCallback), userName, i));
    }
    
    public void setLivenessEnabled(final byte[] array, final boolean b) throws RemoteException {
        Log.d("qfp-service", "setLivenessEnabled()");
        this.throwIfFail(this.mNative.setLivenessEnabled(this.mJniContext, array, b));
    }
    
    public String version() throws RemoteException {
        return new String("1.0");
    }
    
    protected class JniCallback implements INativeCallback
    {
        private final QfpRemoteCallbackList mCallbacks;
        
        public JniCallback(final IFingerprintServiceCallback fingerprintServiceCallback) {
            this.mCallbacks = new QfpRemoteCallbackList();
            Log.d("qfp-service", "JniCallback ctor:  hc: " + this.getId() + " cb hc: " + this.getId(fingerprintServiceCallback));
            this.mCallbacks.register(fingerprintServiceCallback);
        }
        
        private String getId() {
            return Integer.toString(this.hashCode());
        }
        
        private String getId(final Object o) {
            return Integer.toString(System.identityHashCode(o));
        }
        
        private void notifyListenerMatched(final int i, final String s) {
            Log.d("qfp-service", "notify listener scanned: fingerprintId" + i + " hc: " + this.getId());
            final int beginBroadcast = this.mCallbacks.beginBroadcast();
            int j = 0;
            while (j < beginBroadcast) {
                try {
                    final IFingerprintServiceCallback fingerprintServiceCallback = (IFingerprintServiceCallback)this.mCallbacks.getBroadcastItem(j);
                    Log.d("qfp-service", "removed callback to hc: " + this.getId(fingerprintServiceCallback));
                    fingerprintServiceCallback.onMatched(i, s);
                    ++j;
                    continue;
                }
                catch (RemoteException ex) {
                    Log.e("qfp-service", ex.getLocalizedMessage());
                }
                break;
            }
            this.mCallbacks.finishBroadcast();
        }
        
        private void notifyListenerRemoved(final int i) {
            Log.d("qfp-service", "notify listener remove: fingerprintId: " + i + " hc: " + this.getId());
            final int beginBroadcast = this.mCallbacks.beginBroadcast();
            int j = 0;
            while (j < beginBroadcast) {
                try {
                    final IFingerprintServiceCallback fingerprintServiceCallback = (IFingerprintServiceCallback)this.mCallbacks.getBroadcastItem(j);
                    Log.d("qfp-service", "removed callback to hc: " + this.getId(fingerprintServiceCallback));
                    fingerprintServiceCallback.onRemoved(i);
                    ++j;
                    continue;
                }
                catch (RemoteException ex) {
                    Log.e("qfp-service", ex.getLocalizedMessage());
                }
                break;
            }
            this.mCallbacks.finishBroadcast();
        }
        
        private void notifyListenersEnrolled(final int i, final int j) {
            Log.d("qfp-service", "notify listeners enroll result: fingerprintId: " + i + " remaining: " + j + " hc: " + this.getId());
            final int beginBroadcast = this.mCallbacks.beginBroadcast();
            int k = 0;
            while (k < beginBroadcast) {
                try {
                    final IFingerprintServiceCallback fingerprintServiceCallback = (IFingerprintServiceCallback)this.mCallbacks.getBroadcastItem(k);
                    Log.d("qfp-service", "enrolled callback to hc: " + this.getId(fingerprintServiceCallback));
                    fingerprintServiceCallback.onEnrolled(i, j);
                    ++k;
                    continue;
                }
                catch (RemoteException ex) {
                    Log.e("qfp-service", ex.getLocalizedMessage());
                }
                break;
            }
            this.mCallbacks.finishBroadcast();
        }
        
        private void notifyListenersError(final int i) {
            Log.d("qfp-service", "notify listener: error: " + i + " hc: " + this.getId());
            final int beginBroadcast = this.mCallbacks.beginBroadcast();
            int j = 0;
            while (j < beginBroadcast) {
                try {
                    final IFingerprintServiceCallback fingerprintServiceCallback = (IFingerprintServiceCallback)this.mCallbacks.getBroadcastItem(j);
                    Log.d("qfp-service", "error callback to hc: " + this.getId(fingerprintServiceCallback));
                    fingerprintServiceCallback.onError(i);
                    ++j;
                    continue;
                }
                catch (RemoteException ex) {
                    Log.e("qfp-service", ex.getLocalizedMessage());
                }
                break;
            }
            this.mCallbacks.finishBroadcast();
        }
        
        private void notifyListenersStatus(final int i, final byte[] array) {
            Log.d("qfp-service", "notify listener: status: " + i + " hc: " + this.getId());
            final int beginBroadcast = this.mCallbacks.beginBroadcast();
            int j = 0;
            while (j < beginBroadcast) {
                try {
                    final IFingerprintServiceCallback fingerprintServiceCallback = (IFingerprintServiceCallback)this.mCallbacks.getBroadcastItem(j);
                    Log.d("qfp-service", "status callback to hc: " + this.getId(fingerprintServiceCallback));
                    fingerprintServiceCallback.onStatus(i, array);
                    ++j;
                    continue;
                }
                catch (RemoteException ex) {
                    Log.e("qfp-service", ex.getLocalizedMessage());
                }
                break;
            }
            this.mCallbacks.finishBroadcast();
        }
        
        public void finalize() {
            this.mCallbacks.kill();
        }
        
        @Override
        public void onEnrolled(final int i, final int j) {
            Log.v("qfp-service", "received callback enroll result: fingerprintId: " + i + " remaining: " + j + " hc: " + this.getId());
            this.notifyListenersEnrolled(i, j);
        }
        
        @Override
        public void onError(final int i) {
            Log.v("qfp-service", "received callback error: " + i + " hc: " + this.getId());
            this.notifyListenersError(i);
        }
        
        @Override
        public void onMatched(final int i, final String str, final long n, final byte[] array) {
            Log.v("qfp-service", "received callback scanned: fingerprintId: " + i + " User: " + str + " hc: " + this.getId());
            this.notifyListenerMatched(i, str);
        }
        
        @Override
        public void onRemoved(final int i) {
            Log.v("qfp-service", "received callback removed: fingerprintId: " + i + " hc: " + this.getId());
            this.notifyListenerRemoved(i);
        }
        
        @Override
        public void onStatus(final int i, final byte[] array) {
            Log.v("qfp-service", "received callback stats: " + i + " hc: " + this.getId());
            this.notifyListenersStatus(i, array);
        }
    }
    
    class QfpRemoteCallbackList extends RemoteCallbackList<IFingerprintServiceCallback>
    {
        public void onCallbackDied(final IFingerprintServiceCallback fingerprintServiceCallback) {
            Log.d("qfp-service", "CB Process diedcb hc: " + Integer.toString(System.identityHashCode(fingerprintServiceCallback)));
            while (true) {
                try {
                    FingerprintServiceStub.this.cancel();
                    this.kill();
                    super.onCallbackDied(fingerprintServiceCallback);
                }
                catch (Exception ex) {
                    continue;
                }
                break;
            }
        }
        
        public void onCallbackDied(final IFingerprintServiceCallback fingerprintServiceCallback, final Object o) {
            Log.d("qfp-service", "CB Process died with cookie: " + o.toString() + "cb hc: " + Integer.toString(System.identityHashCode(fingerprintServiceCallback)));
            while (true) {
                try {
                    FingerprintServiceStub.this.cancel();
                    this.kill();
                    super.onCallbackDied(fingerprintServiceCallback, o);
                }
                catch (Exception ex) {
                    continue;
                }
                break;
            }
        }
    }
}
