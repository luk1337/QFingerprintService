package com.qualcomm.qti.biometrics.fingerprint.service;

import android.content.Context;
import android.os.RemoteException;
import android.util.Log;

public class FingerprintAuthenticator implements AutoCloseable {
    private static final String TAG = "qfp-fido";
    private static final String AUTH_NAME = "qfp";
    private static final int MATCH_TIMOUT_MS = 10000;

    private Native mJni;
    private long mJniContext;

    public FingerprintAuthenticator(Context context) throws RemoteException {
        Log.d(TAG, "FingerprintAuthenticator ctor");

        mJni = new Native();
        mJniContext = mJni.open();

        if (mJniContext == 0) {
            throw new RemoteException("unable to connect to qfp service");
        }
    }

    private static String toHexString(byte[] array) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1));
        }

        return sb.toString();
    }

    public void cancel(byte[] array, IFingerprintAuthenticatorListener fingerprintAuthenticatorListener) throws RemoteException {
        Log.d(TAG, "FingerprintAuthenticator cancel " + toHexString(array));
        mJni.cancel(mJniContext);
        fingerprintAuthenticatorListener.onCancel(array);
    }

    @Override
    public void close() {
        if (mJniContext != 0) {
            mJni.close(mJniContext);
            mJniContext = 0;
        }
    }

    public void finalize() {
        close();
    }

    public void getEnrollmentStatus(long lng, IFingerprintAuthenticatorListener fingerprintAuthenticatorListener) throws RemoteException {
        final String[] array = {null};

        Log.d(TAG, "FingerprintAuthenticator getEnrollmentStatus " + lng);
        mJni.retrieveUser(mJniContext, lng, array);
        fingerprintAuthenticatorListener.onEnrollmentStatus(lng, array[0] != null);
    }

    public void verifyUser(byte[] array, String s, IFingerprintAuthenticatorListener fingerprintAuthenticatorListener) throws RemoteException {
        verifyUserExt(array, s, 0, fingerprintAuthenticatorListener);
    }

    public void verifyUserExt(byte[] array, String str, int match, IFingerprintAuthenticatorListener fingerprintAuthenticatorListener) throws RemoteException {
        Log.d(TAG, "FingerprintAuthenticator verifyUserExt " + toHexString(array) + " " + str + " " + match);
        JniCallback jniCallback = new JniCallback(array, fingerprintAuthenticatorListener);

        match = mJni.match(mJniContext, jniCallback, 10000, "", array, str, match);
        if (match != 0) {
            jniCallback.onError(match);
        }
    }

    private class JniCallback implements INativeCallback {
        private IFingerprintAuthenticatorListener mListener;
        private byte[] mNonce;

        public JniCallback(byte[] mNonce, IFingerprintAuthenticatorListener mListener) {
            this.mNonce = mNonce;
            this.mListener = mListener;
        }

        @Override
        public void onEnrolled(int n, int n2) {
            Log.e(TAG, "FingerprintAuthenticator unexpected call to onEnrolled");
        }

        @Override
        public void onError(int i) {
            Log.d(TAG, "onError " + toHexString(this.mNonce) + " " + i);

            try {
                mListener.onUserVerificationResult(mNonce, i, AUTH_NAME, 0L, 0L, new byte[0]);
            } catch (RemoteException ex) {
                Log.e(TAG, ex.getLocalizedMessage());
            }
        }

        @Override
        public void onMatched(int i, String str, long lng, byte[] array) {
            Log.d(TAG, "onMatched " + str + ":" + i + " " + lng + " " + toHexString(array));
            if (i != 0) {
                return;
            }

            try {
                mListener.onUserVerificationResult(mNonce, 1, AUTH_NAME, lng, i, array);
            } catch (RemoteException ex) {
                Log.e(TAG, ex.getLocalizedMessage());
            }
        }

        @Override
        public void onRemoved(int n) {
            Log.e(TAG, "FingerprintAuthenticator unexpected call to onRemoved");
        }

        @Override
        public void onStatus(int n, byte[] array) {
        }
    }
}