package com.qualcomm.qti.biometrics.fingerprint.service;

import android.content.*;
import android.util.*;
import android.os.*;

public class FingerprintAuthenticator implements AutoCloseable
{
    private final String AUTH_NAME;
    private final int MATCH_TIMOUT_MS;
    private final String TAG;
    private long mJniContext;
    private final Native mNative;
    
    public FingerprintAuthenticator(final Context context) throws RemoteException {
        this.TAG = "qfp-fido";
        this.AUTH_NAME = "qfp";
        this.MATCH_TIMOUT_MS = 10000;
        this.mJniContext = 0L;
        this.mNative = new Native();
        Log.d("qfp-fido", "FingerprintAuthenticator ctor");
        this.mJniContext = this.mNative.open();
        if (this.mJniContext == 0L) {
            throw new RemoteException("unable to connect to qfp service");
        }
    }
    
    private static String toHexString(final byte[] array) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; ++i) {
            sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1));
        }
        return sb.toString();
    }
    
    public void cancel(final byte[] array, final IFingerprintAuthenticatorListener fingerprintAuthenticatorListener) throws RemoteException {
        Log.d("qfp-fido", "FingerprintAuthenticator cancel " + toHexString(array));
        this.mNative.cancel(this.mJniContext);
        fingerprintAuthenticatorListener.onCancel(array);
    }
    
    @Override
    public void close() {
        if (this.mJniContext != 0L) {
            this.mNative.close(this.mJniContext);
            this.mJniContext = 0L;
        }
    }
    
    public void finalize() {
        this.close();
    }
    
    public void getEnrollmentStatus(final long lng, final IFingerprintAuthenticatorListener fingerprintAuthenticatorListener) throws RemoteException {
        boolean b = true;
        Log.d("qfp-fido", "FingerprintAuthenticator getEnrollmentStatus " + lng);
        final String[] array = { null };
        this.mNative.retrieveUser(this.mJniContext, lng, array);
        if (array[0] == null) {
            b = false;
        }
        fingerprintAuthenticatorListener.onEnrollmentStatus(lng, b);
    }
    
    public void verifyUser(final byte[] array, final String s, final IFingerprintAuthenticatorListener fingerprintAuthenticatorListener) throws RemoteException {
        this.verifyUserExt(array, s, 0, fingerprintAuthenticatorListener);
    }
    
    public void verifyUserExt(final byte[] array, final String str, int match, final IFingerprintAuthenticatorListener fingerprintAuthenticatorListener) throws RemoteException {
        Log.d("qfp-fido", "FingerprintAuthenticator verifyUserExt " + toHexString(array) + " " + str + " " + match);
        final JniCallback jniCallback = new JniCallback(array, fingerprintAuthenticatorListener);
        match = this.mNative.match(this.mJniContext, jniCallback, 10000, "", array, str, match);
        if (match != 0) {
            jniCallback.onError(match);
        }
    }
    
    private class JniCallback implements INativeCallback
    {
        private final IFingerprintAuthenticatorListener mListener;
        private final byte[] mNonce;
        
        public JniCallback(final byte[] mNonce, final IFingerprintAuthenticatorListener mListener) {
            this.mNonce = mNonce;
            this.mListener = mListener;
        }
        
        @Override
        public void onEnrolled(final int n, final int n2) {
            Log.e("qfp-fido", "FingerprintAuthenticator unexpected call to onEnrolled");
        }
        
        @Override
        public void onError(final int i) {
            Log.d("qfp-fido", "onError " + toHexString(this.mNonce) + " " + i);
            try {
                this.mListener.onUserVerificationResult(this.mNonce, i, "qfp", 0L, 0L, new byte[0]);
            }
            catch (RemoteException ex) {
                Log.e("qfp-fido", ex.getLocalizedMessage());
            }
        }
        
        @Override
        public void onMatched(final int i, final String str, final long lng, final byte[] array) {
            Log.d("qfp-fido", "onMatched " + str + ":" + i + " " + lng + " " + toHexString(array));
            if (i != 0) {
                return;
            }

            try {
                this.mListener.onUserVerificationResult(this.mNonce, 1, "qfp", lng, i, array);
            }
            catch (RemoteException ex) {
                Log.e("qfp-fido", ex.getLocalizedMessage());
            }
        }
        
        @Override
        public void onRemoved(final int n) {
            Log.e("qfp-fido", "FingerprintAuthenticator unexpected call to onRemoved");
        }
        
        @Override
        public void onStatus(final int n, final byte[] array) {
        }
    }
}
