package com.qualcomm.qti.biometrics.fingerprint.service;

public class Native {
    private static final String JNI_LIB = "qfp-service";

    static {
        System.loadLibrary(JNI_LIB);
    }

    public native int cancel(long p0);

    public native void close(long p0);

    public native int disableFingerEvent(long p0);

    public native int enableFingerEvent(long p0, int p1);

    public native int enroll(long p0, INativeCallback p1, int p2, String p3);

    public native int getLivenessEnabled(long p0, byte[] p1, int[] p2);

    public native boolean isFingerprintEnabled();

    public native int match(long p0, INativeCallback p1, int p2, String p3, byte[] p4, String p5, int p6);

    public native int notifyAlarm(long p0);

    public native int notifyPowerState(long p0, int p1);

    public native long open();

    public native int registerAndroidServices(IAndroidServices p0);

    public native int remove(long p0, INativeCallback p1, String p2, int p3);

    public native int retrieveUser(long p0, long p1, String[] p2);

    public native int setLivenessEnabled(long p0, byte[] p1, boolean p2);
}