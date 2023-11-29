package com.blood.presure.Interface;

public interface MeasuringCallbacks {
    void fingerCantBeDetected();

    void fingerNotDetected();

    void measuringFinished(int i);

    void measuringStarted();

    void measuringStoped();

    void vallyDetected(int i);
}
