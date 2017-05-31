package com.qualcomm.qti.biometrics.fingerprint.service;

public interface INativeCallback {
    void onEnrolled(int fingerprintId, int remaining);

    void onError(int errorId);

    void onMatched(int fingerprintId, String user, long p2, byte[] p3);

    void onRemoved(int fingerprintId);

    void onStatus(int status, byte[] p1);
}