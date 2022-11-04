package org.easypsycho;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.TimeUtils;

public class Core {
    private static final double nanosPerMilli = 1000000d;
    private static final double nanosPerSecond = 1000000000d;

    public static long getNanoTime() {
        return TimeUtils.nanoTime();
    }

    public static double nanosToMillis(long nanos) {
        return nanos / nanosPerMilli;
    }

    public static double nanosToSecond(long nanos) {
        return nanos / nanosPerSecond;
    }

    public static long intervalNanos(long preNanosTime) {
        return getNanoTime() - preNanosTime;
    }

    public static double intervalSecond(long preNanosTime) {
        return (getNanoTime() - preNanosTime) / nanosPerSecond;
    }

    public static double intervalMillis(long preNanosTime) {
        return (getNanoTime() - preNanosTime) / nanosPerMilli;
    }

    public static void quit(){
        EasyPsycho.app.exit();
    }
}
