package com.qualcomm.qti.biometrics.fingerprint.service;

public interface INativeCallback
{
    void onEnrolled(final int p0, final int p1);
    
    void onError(final int p0);
    
    void onMatched(final int p0, final String p1, final long p2, final byte[] p3);
    
    void onRemoved(final int p0);
    
    void onStatus(final int p0, final byte[] p1);
}
