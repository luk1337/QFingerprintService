package com.qualcomm.qti.biometrics.fingerprint.service;

public interface IAndroidServices {
    int checkRequestingAppType();

    void resetLivenessAlert();

    void setTimer(int p0);

    void setWakelock(boolean p0, boolean p1);

    void showLivenessAlert();
}