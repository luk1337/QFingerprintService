package com.qualcomm.qti.biometrics.fingerprint.service;

public interface IAndroidServices
{
    int checkRequestingAppType();
    
    void resetLivenessAlert();
    
    void setTimer(final int p0);
    
    void setWakelock(final boolean p0, final boolean p1);
    
    void showLivenessAlert();
}
