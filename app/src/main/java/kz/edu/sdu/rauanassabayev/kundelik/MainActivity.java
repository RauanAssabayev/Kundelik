package kz.edu.sdu.rauanassabayev.kundelik;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import java.util.Locale;
import kz.edu.sdu.rauanassabayev.kundelik.Fragments.GameFragment;
import kz.edu.sdu.rauanassabayev.kundelik.Fragments.NewsFragment;
import kz.edu.sdu.rauanassabayev.kundelik.Fragments.ProfileFragment;
import kz.edu.sdu.rauanassabayev.kundelik.Fragments.WeekScheduleListFragment;
import kz.edu.sdu.rauanassabayev.kundelik.Utils.BottomNavigationViewHelper;

public class MainActivity extends AppCompatActivity {
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    int selectedFragment = 2;
    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        Resources res = getApplicationContext().getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.locale = new Locale("kk");
        res.updateConfiguration(conf, dm);
//        Intent refresh = new Intent(MainActivity.this, MainActivity.class);
//        startActivity(refresh);
//        finish();
        setContentView(R.layout.activity_main);
//        mRealm = Realm.getDefaultInstance();
//        mRealm.beginTransaction();
//        mRealm.delete(Subject.class);
//        mRealm.commitTransaction();
        BottomNavigationView menuView = findViewById(R.id.bottom_navigation);
        if(savedInstanceState == null){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragmentContainer, new WeekScheduleListFragment());
            fragmentTransaction.commit();
        }
        new BottomNavigationViewHelper().disableShiftMode(menuView);
        menuView.setSelectedItemId(R.id.action_schedule);
        menuView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment currentFragment = null;
            fragmentTransaction  = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.action_profile:
                    currentFragment = new ProfileFragment();
                    fragmentTransaction.replace(R.id.fragmentContainer, currentFragment,"ProfileFragment");
                    break;
                case R.id.action_schedule:
                    currentFragment = new WeekScheduleListFragment();
                    fragmentTransaction.replace(R.id.fragmentContainer, currentFragment,"WeekScheduleListFragment");
                    break;
                case R.id.action_game:
                    currentFragment = new GameFragment();
                    fragmentTransaction.replace(R.id.fragmentContainer, currentFragment,"GameFragment");
                    break;
                case R.id.action_news:
                    currentFragment = new NewsFragment();
                    fragmentTransaction.replace(R.id.fragmentContainer, currentFragment,"NewsFragment");
                    break;
                default:
                    Toast.makeText(getApplicationContext(),"WOW IT'S COOL",Toast.LENGTH_SHORT).show();
                    break;
            }
            if (currentFragment != null) {
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.commit();
            }
            return true;
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
//        mRealm.close();
    }
}