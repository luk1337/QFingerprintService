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

        private void notifyListenerMatched(int fingerprintId, String user) {
            Log.d(TAG, "notify listener scanned: fingerprintId" + fingerprintId + " hc: " + getId());

            for (int j = 0; j < mCallbacks.beginBroadcast(); j++) {
                try {
                    IFingerprintServiceCallback fingerprintServiceCallback = (IFingerprintServiceCallback) mCallbacks.getBroadcastItem(j);

                    Log.d(TAG, "removed callback to hc: " + getId(fingerprintServiceCallback));
                    fingerprintServiceCallback.onMatched(fingerprintId, user);
                } catch (RemoteException ex) {
                    Log.e("qfp-service", ex.getLocalizedMessage());
                }
            }

            mCallbacks.finishBroadcast();
        }

        private void notifyListenerRemoved(int fingerprintId) {
            Log.d(TAG, "notify listener remove: fingerprintId: " + fingerprintId + " hc: " + getId());

            for (int j = 0; j < mCallbacks.beginBroadcast(); j++) {
                try {
                    IFingerprintServiceCallback fingerprintServiceCallback = (IFingerprintServiceCallback) mCallbacks.getBroadcastItem(j);

                    Log.d(TAG, "removed callback to hc: " + getId(fingerprintServiceCallback));
                    fingerprintServiceCallback.onRemoved(fingerprintId);
                } catch (RemoteException ex) {
                    Log.e("qfp-service", ex.getLocalizedMessage());
                }
            }

            mCallbacks.finishBroadcast();
        }

        private void notifyListenersEnrolled(int fingerprintId, int remaining) {
            Log.d(TAG, "notify listeners enroll result: fingerprintId: " + fingerprintId + " remaining: " + remaining + " hc: " + getId());

            for (int k = 0; k < mCallbacks.beginBroadcast(); k++) {
                try {
                    IFingerprintServiceCallback fingerprintServiceCallback = (IFingerprintServiceCallback) mCallbacks.getBroadcastItem(k);

                    Log.d(TAG, "enrolled callback to hc: " + getId(fingerprintServiceCallback));
                    fingerprintServiceCallback.onEnrolled(fingerprintId, remaining);
                } catch (RemoteException ex) {
                    Log.e("qfp-service", ex.getLocalizedMessage());
                }
            }

            mCallbacks.finishBroadcast();
        }

        private void notifyListenersError(int error) {
            Log.d(TAG, "notify listener: error: " + error + " hc: " + getId());

            for (int j = 0; j < mCallbacks.beginBroadcast(); j++) {
                try {
                    IFingerprintServiceCallback fingerprintServiceCallback = (IFingerprintServiceCallback) mCallbacks.getBroadcastItem(j);

                    Log.d(TAG, "error callback to hc: " + getId(fingerprintServiceCallback));
                    fingerprintServiceCallback.onError(error);
                } catch (RemoteException ex) {
                    Log.e("qfp-service", ex.getLocalizedMessage());
                }
            }

            mCallbacks.finishBroadcast();
        }

        private void notifyListenersStatus(int status, byte[] array) {
            Log.d(TAG, "notify listener: status: " + status + " hc: " + getId());

            for (int j = 0; j < mCallbacks.beginBroadcast(); j++) {
                try {
                    IFingerprintServiceCallback fingerprintServiceCallback = (IFingerprintServiceCallback) mCallbacks.getBroadcastItem(j);

                    Log.d(TAG, "status callback to hc: " + getId(fingerprintServiceCallback));
                    fingerprintServiceCallback.onStatus(status, array);
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
        public void onEnrolled(int fingerprintId, int remaining) {
            Log.v(TAG, "received callback enroll result: fingerprintId: " + fingerprintId + " remaining: " + remaining + " hc: " + getId());
            notifyListenersEnrolled(fingerprintId, remaining);
        }

        @Override
        public void onError(int errorId) {
            Log.v(TAG, "received callback error: " + errorId + " hc: " + getId());
            notifyListenersError(errorId);
        }

        @Override
        public void onMatched(int fingerprintId, String user, long n, byte[] array) {
            Log.v(TAG, "received callback scanned: fingerprintId: " + fingerprintId + " User: " + user + " hc: " + getId());
            notifyListenerMatched(fingerprintId, user);
        }

        @Override
        public void onRemoved(int fingerprintId) {
            Log.v(TAG, "received callback removed: fingerprintId: " + fingerprintId + " hc: " + getId());
            notifyListenerRemoved(fingerprintId);
        }

        @Override
        public void onStatus(int status, byte[] array) {
            Log.v(TAG, "received callback status: " + status + " hc: " + getId());
            notifyListenersStatus(status, array);
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

        public void onCallbackDied(IFingerprintServiceCallback fingerprintServiceCallback, Object cookie) {
            Log.d(TAG, "CB Process died with cookie: " + cookie.toString() + "cb hc: " + Integer.toString(System.identityHashCode(fingerprintServiceCallback)));

            try {
                FingerprintServiceStub.this.cancel();
                kill();

                super.onCallbackDied(fingerprintServiceCallback, cookie);
            } catch (Exception ex) {
                // Do nothing
            }
        }
    }
}