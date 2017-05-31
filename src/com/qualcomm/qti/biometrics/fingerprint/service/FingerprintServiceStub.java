package com.qualcomm.qti.biometrics.fingerprint.service;

import android.app.ActivityManagerNative;
import android.content.Context;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

public class FingerprintServiceStub implements AutoCloseable {
    private static final String TAG = "qfp-service";

    private Context mContext;
    private long mJniContext;
    private final Native mJni;

    public FingerprintServiceStub(Context mContext) throws RemoteException {
        this.mContext = mContext;

        mJni = new Native();
        mJniContext = mJni.open();

        if (mJniContext == 0) {
            throw new RemoteException("unable to connect to qfp service");
        }
    }

    private String getUserName() throws RemoteException {
        return Integer.toString(ActivityManagerNative.getDefault().getCurrentUser().id);
    }

    private void throwIfFail(int i) throws RemoteException {
        if (i != 0) {
            throw new RemoteException("Error: " + i);
        }
    }

    public void cancel() throws RemoteException {
        Log.d(TAG, "cancel()");
        throwIfFail(mJni.cancel(mJniContext));
    }

    @Override
    public void close() {
        synchronized (this) {
            if (mJniContext != 0) {
                mJni.close(mJniContext);
            }

            mJniContext = 0;
        }
    }

    public void disableFingerEvent() throws RemoteException {
        Log.d(TAG, "disableFingerEvent()");
        throwIfFail(mJni.disableFingerEvent(mJniContext));
    }

    public void enableFingerEvent(int i) throws RemoteException {
        Log.d(TAG, "enableFingerEvent(" + i + ")");
        throwIfFail(mJni.enableFingerEvent(mJniContext, i));
    }

    public void enroll(int i, IFingerprintServiceCallback fingerprintServiceCallback) throws RemoteException {
        String userName = getUserName();

        Log.d(TAG, "enroll(" + i + ") - " + userName);
        throwIfFail(mJni.enroll(mJniContext, new JniCallback(fingerprintServiceCallback), i, userName));
    }

    public void finalize() {
        close();
    }

    public boolean getLivenessEnabled(final byte[] array) throws RemoteException {
        final int[] array2 = {0};

        Log.d(TAG, "getLivenessEnabled()");
        throwIfFail(mJni.getLivenessEnabled(mJniContext, array, array2));
        return array2[0] != 0;
    }

    public void match(final int i, final IFingerprintServiceCallback fingerprintServiceCallback) throws RemoteException {
        String userName = this.getUserName();

        Log.d(TAG, "match(" + i + ") - " + userName);
        throwIfFail(mJni.match(mJniContext, new JniCallback(fingerprintServiceCallback), i, userName, new byte[0], "", 0));
    }

    public void matchAny(final int i, final IFingerprintServiceCallback fingerprintServiceCallback) throws RemoteException {
        Log.d(TAG, "matchAny(" + i + ")");
        throwIfFail(mJni.match(mJniContext, new JniCallback(fingerprintServiceCallback), i, "", new byte[0], "", 0));
    }

    public void remove(final int i, final IFingerprintServiceCallback fingerprintServiceCallback) throws RemoteException {
        String userName = this.getUserName();

        Log.d(TAG, "remove(" + i + ")");
        throwIfFail(mJni.remove(mJniContext, new JniCallback(fingerprintServiceCallback), userName, i));
    }

    public void setLivenessEnabled(final byte[] array, final boolean b) throws RemoteException {
        Log.d(TAG, "setLivenessEnabled()");
        throwIfFail(mJni.setLivenessEnabled(mJniContext, array, b));
    }

    public String version() throws RemoteException {
        return new String("1.0");
    }

    protected class JniCallback implements INativeCallback {
        private final QfpRemoteCallbackList mCallbacks;

        public JniCallback(final IFingerprintServiceCallback fingerprintServiceCallback) {
            mCallbacks = new QfpRemoteCallbackList();

            Log.d(TAG, "JniCallback ctor:  hc: " + getId() + " cb hc: " + getId(fingerprintServiceCallback));
            mCallbacks.register(fingerprintServiceCallback);
        }

        private String getId() {
            return Integer.toString(hashCode());
        }

        private String getId(Object o) {
            return Integer.toString(System.identityHashCode(o));
        }

        private void notifyListenerMatched(int i, String s) {
            Log.d(TAG, "notify listener scanned: fingerprintId" + i + " hc: " + getId());

            for (int j = 0; j < mCallbacks.beginBroadcast(); j++) {
                try {
                    IFingerprintServiceCallback fingerprintServiceCallback = (IFingerprintServiceCallback) mCallbacks.getBroadcastItem(j);

                    Log.d(TAG, "removed callback to hc: " + getId(fingerprintServiceCallback));
                    fingerprintServiceCallback.onMatched(i, s);
                } catch (RemoteException ex) {
                    Log.e("qfp-service", ex.getLocalizedMessage());
                }
            }

            mCallbacks.finishBroadcast();
        }

        private void notifyListenerRemoved(int i) {
            Log.d(TAG, "notify listener remove: fingerprintId: " + i + " hc: " + getId());

            for (int j = 0; j < mCallbacks.beginBroadcast(); j++) {
                try {
                    IFingerprintServiceCallback fingerprintServiceCallback = (IFingerprintServiceCallback) mCallbacks.getBroadcastItem(j);

                    Log.d(TAG, "removed callback to hc: " + getId(fingerprintServiceCallback));
                    fingerprintServiceCallback.onRemoved(i);
                } catch (RemoteException ex) {
                    Log.e("qfp-service", ex.getLocalizedMessage());
                }
            }

            mCallbacks.finishBroadcast();
        }

        private void notifyListenersEnrolled(int i, int j) {
            Log.d(TAG, "notify listeners enroll result: fingerprintId: " + i + " remaining: " + j + " hc: " + getId());

            for (int k = 0; k < mCallbacks.beginBroadcast(); k++) {
                try {
                    IFingerprintServiceCallback fingerprintServiceCallback = (IFingerprintServiceCallback) mCallbacks.getBroadcastItem(j);

                    Log.d(TAG, "enrolled callback to hc: " + getId(fingerprintServiceCallback));
                    fingerprintServiceCallback.onEnrolled(i, j);
                } catch (RemoteException ex) {
                    Log.e("qfp-service", ex.getLocalizedMessage());
                }
            }

            mCallbacks.finishBroadcast();
        }

        private void notifyListenersError(int i) {
            Log.d(TAG, "notify listener: error: " + i + " hc: " + getId());

            for (int j = 0; j < mCallbacks.beginBroadcast(); j++) {
                try {
                    IFingerprintServiceCallback fingerprintServiceCallback = (IFingerprintServiceCallback) mCallbacks.getBroadcastItem(j);

                    Log.d(TAG, "error callback to hc: " + getId(fingerprintServiceCallback));
                    fingerprintServiceCallback.onError(i);
                } catch (RemoteException ex) {
                    Log.e("qfp-service", ex.getLocalizedMessage());
                }
            }

            mCallbacks.finishBroadcast();
        }

        private void notifyListenersStatus(int i, byte[] array) {
            Log.d(TAG, "notify listener: status: " + i + " hc: " + getId());

            for (int j = 0; j < mCallbacks.beginBroadcast(); j++) {
                try {
                    IFingerprintServiceCallback fingerprintServiceCallback = (IFingerprintServiceCallback) mCallbacks.getBroadcastItem(j);

                    Log.d(TAG, "status callback to hc: " + getId(fingerprintServiceCallback));
                    fingerprintServiceCallback.onStatus(i, array);
                } catch (RemoteException ex) {
                    Log.e("qfp-service", ex.getLocalizedMessage());
                }
            }

            mCallbacks.finishBroadcast();
        }

        public void finalize() {
            mCallbacks.kill();
        }

        @Override
        public void onEnrolled(int i, int j) {
            Log.v(TAG, "received callback enroll result: fingerprintId: " + i + " remaining: " + j + " hc: " + getId());
            notifyListenersEnrolled(i, j);
        }

        @Override
        public void onError(int i) {
            Log.v(TAG, "received callback error: " + i + " hc: " + getId());
            notifyListenersError(i);
        }

        @Override
        public void onMatched(int i, String str, long n, byte[] array) {
            Log.v(TAG, "received callback scanned: fingerprintId: " + i + " User: " + str + " hc: " + getId());
            notifyListenerMatched(i, str);
        }

        @Override
        public void onRemoved(int i) {
            Log.v(TAG, "received callback removed: fingerprintId: " + i + " hc: " + getId());
            notifyListenerRemoved(i);
        }

        @Override
        public void onStatus(int i, byte[] array) {
            Log.v(TAG, "received callback stats: " + i + " hc: " + getId());
            notifyListenersStatus(i, array);
        }
    }

    class QfpRemoteCallbackList extends RemoteCallbackList<IFingerprintServiceCallback> {
        public void onCallbackDied(IFingerprintServiceCallback fingerprintServiceCallback) {
            Log.d(TAG, "CB Process diedcb hc: " + Integer.toString(System.identityHashCode(fingerprintServiceCallback)));

            try {
                FingerprintServiceStub.this.cancel();
                kill();

                super.onCallbackDied(fingerprintServiceCallback);
            } catch (Exception ex) {
                // Do nothing
            }
        }

        public void onCallbackDied(IFingerprintServiceCallback fingerprintServiceCallback, Object o) {
            Log.d(TAG, "CB Process died with cookie: " + o.toString() + "cb hc: " + Integer.toString(System.identityHashCode(fingerprintServiceCallback)));

            try {
                FingerprintServiceStub.this.cancel();
                kill();

                super.onCallbackDied(fingerprintServiceCallback, o);
            } catch (Exception ex) {
                // Do nothing
            }
        }
    }
}