package com.blood.presure.Measurement;

import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;

public class MeasureStore {
    private int maximum = Integer.MIN_VALUE;
    private final CopyOnWriteArrayList<Measurement<Integer>> measurements = new CopyOnWriteArrayList<>();
    private int minimum = Integer.MAX_VALUE;
    private final int rollingAverageSize = 4;

    public MeasureStore() {
    }

    public void add(int i) {
        this.measurements.add(new Measurement(new Date(), Integer.valueOf(i)));
        if (i < this.minimum) {
            this.minimum = i;
        }
        if (i > this.maximum) {
            this.maximum = i;
        }
    }

    public CopyOnWriteArrayList<Measurement<Float>> getStdValues() {
        CopyOnWriteArrayList<Measurement<Float>> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        for (int i = 0; i < this.measurements.size(); i++) {
            int i2 = 0;
            for (int i3 = 0; i3 < 4; i3++) {
                i2 += ((Integer) this.measurements.get(Math.max(0, i - i3)).measurement).intValue();
            }
            Date date = this.measurements.get(i).timestamp;
            int i4 = this.minimum;
            copyOnWriteArrayList.add(new Measurement(date, Float.valueOf(((((float) i2) / 4.0f) - ((float) i4)) / ((float) (this.maximum - i4)))));
        }
        return copyOnWriteArrayList;
    }

    public CopyOnWriteArrayList<Measurement<Integer>> getLastStdValues(int i) {
        if (i >= this.measurements.size()) {
            return this.measurements;
        }
        CopyOnWriteArrayList<Measurement<Integer>> copyOnWriteArrayList = this.measurements;
        return new CopyOnWriteArrayList<>(copyOnWriteArrayList.subList((copyOnWriteArrayList.size() - 1) - i, this.measurements.size() - 1));
    }

    public Date getLastTimestamp() {
        CopyOnWriteArrayList<Measurement<Integer>> copyOnWriteArrayList = this.measurements;
        return copyOnWriteArrayList.get(copyOnWriteArrayList.size() - 1).timestamp;
    }
}
