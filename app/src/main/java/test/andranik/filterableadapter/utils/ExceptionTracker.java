package test.andranik.filterableadapter.utils;

import test.andranik.filterableadapter.BuildConfig;

/**
 * Created by andranik on 3/7/17.
 */

public class ExceptionTracker {
    private static final String TAG = "ExceptionTracker";

    public static void trackException(Throwable t) {
        if (BuildConfig.DEBUG) {
            t.printStackTrace();
        }
        //TODO may we need to log it somewhere too
    }

}
