package com.qualcomm.qti.biometrics.fingerprint.service;

public interface INativeCallback {
    void onEnrolled(int p0, int p1);

    void onError(int p0);

    void onMatched(int p0, String p1, long p2, byte[] p3);

    void onRemoved(int p0);

    void onStatus(int p0, byte[] p1);
}