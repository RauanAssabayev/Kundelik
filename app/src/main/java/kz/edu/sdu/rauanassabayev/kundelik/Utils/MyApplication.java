package kz.edu.sdu.rauanassabayev.kundelik.Utils;

import android.app.Application;
import android.content.Context;

/**
 * Created by rauanassabayev on 10/31/17.
 */

public class MyApplication extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        MyApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }
}
