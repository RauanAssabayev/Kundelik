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
import android.widget.Toast;
import java.util.Locale;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import kz.edu.sdu.rauanassabayev.kundelik.Fragments.GameFragment;
import kz.edu.sdu.rauanassabayev.kundelik.Fragments.NewsFragment;
import kz.edu.sdu.rauanassabayev.kundelik.Fragments.ProfileFragment;
import kz.edu.sdu.rauanassabayev.kundelik.Fragments.ScheduleFragment;
import kz.edu.sdu.rauanassabayev.kundelik.Fragments.TimeTableFragment;
import kz.edu.sdu.rauanassabayev.kundelik.Models.Subject;
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
            fragmentTransaction.add(R.id.fragmentContainer, new ScheduleFragment());
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
                    if(selectedFragment != 1){
                        currentFragment = new ProfileFragment();
                        fragmentTransaction.replace(R.id.fragmentContainer, currentFragment,"ProfileFragment");
                    }
                    onFragmentChangeAnimation(fragmentTransaction,1);
                    break;
                case R.id.action_schedule:
                    if(selectedFragment != 2){
                        currentFragment = new TimeTableFragment();
                        fragmentTransaction.replace(R.id.fragmentContainer, currentFragment,"ScheduleFragment");
                    }
                    onFragmentChangeAnimation(fragmentTransaction,2);
                    break;
                case R.id.action_game:
                    if(selectedFragment != 3){
                        currentFragment = new GameFragment();
                        fragmentTransaction.replace(R.id.fragmentContainer, currentFragment,"GameFragment");
                    }
                    onFragmentChangeAnimation(fragmentTransaction,3);
                    break;
                case R.id.action_news:
                    if(selectedFragment != 4){
                        currentFragment = new NewsFragment();
                        fragmentTransaction.replace(R.id.fragmentContainer, currentFragment,"NewsFragment");
                    }
                    onFragmentChangeAnimation(fragmentTransaction,4);
                    break;
                default:
                    Toast.makeText(getApplicationContext(),"WOW",Toast.LENGTH_SHORT).show();
                    break;
            }
            if (currentFragment != null) {
                //fragmentTransaction.addToBackStack(null);
                //fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.commit();
            }
            return true;
            }
        });
    }

    void onFragmentChangeAnimation(FragmentTransaction fragmentTransaction,int inSelectedFragment){
        switch (inSelectedFragment){
            case 1:
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
                selectedFragment = 1;
                break;
            case 2:
                if(selectedFragment == 1){
                    fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                }
                else if(selectedFragment == 3 || selectedFragment == 4){
                    fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
                }
                selectedFragment = 2;
                break;
            case 3:
                if(selectedFragment == 1 || selectedFragment == 2){
                    fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                }
                else if(selectedFragment == 4){
                    fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
                }
                selectedFragment = 3;
                break;
            case 4:
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                selectedFragment = 4;
                break;
            default:
                Toast.makeText(getApplicationContext(),"WOW",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
//        mRealm.close();

    }
}