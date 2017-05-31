package com.qualcomm.qti.biometrics.fingerprint.service;

public class Native
{
    private static final String JNI_LIB = "qfp-service";
    
    static {
        System.loadLibrary("qfp-service");
    }
    
    public native int cancel(final long p0);
    
    public native void close(final long p0);
    
    public native int disableFingerEvent(final long p0);
    
    public native int enableFingerEvent(final long p0, final int p1);
    
    public native int enroll(final long p0, final INativeCallback p1, final int p2, final String p3);
    
    public native int getLivenessEnabled(final long p0, final byte[] p1, final int[] p2);
    
    public native boolean isFingerprintEnabled();
    
    public native int match(final long p0, final INativeCallback p1, final int p2, final String p3, final byte[] p4, final String p5, final int p6);
    
    public native int notifyAlarm(final long p0);
    
    public native int notifyPowerState(final long p0, final int p1);
    
    public native long open();
    
    public native int registerAndroidServices(final IAndroidServices p0);
    
    public native int remove(final long p0, final INativeCallback p1, final String p2, final int p3);
    
    public native int retrieveUser(final long p0, final long p1, final String[] p2);
    
    public native int setLivenessEnabled(final long p0, final byte[] p1, final boolean p2);
}
