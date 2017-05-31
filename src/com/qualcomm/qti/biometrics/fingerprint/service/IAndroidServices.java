package com.qualcomm.qti.biometrics.fingerprint.service;

public interface IAndroidServices {
    int checkRequestingAppType();

    void resetLivenessAlert();

    void setTimer(int delay);

    void setWakelock(boolean active, boolean partial);

    void showLivenessAlert();
}