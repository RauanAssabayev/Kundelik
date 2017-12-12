package kz.edu.sdu.rauanassabayev.kundelik;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by rauanassabayev on 11/23/17.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().name(Realm.DEFAULT_REALM_NAME).deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(config);
    }
}
