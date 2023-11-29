package com.blood.presure.Measurement;

import java.util.Date;

 public class Measurement<T> {
    public final T measurement;
    public final Date timestamp;

     Measurement(Date date, T t) {
        this.timestamp = date;
        this.measurement = t;
    }
}
