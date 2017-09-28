package kz.edu.sdu.rauanassabayev.kundelik;

import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import kz.edu.sdu.rauanassabayev.kundelik.Fragments.GameFragment;
import kz.edu.sdu.rauanassabayev.kundelik.Fragments.NewsFragment;
import kz.edu.sdu.rauanassabayev.kundelik.Fragments.ProfileFragment;
import kz.edu.sdu.rauanassabayev.kundelik.Fragments.ScheduleFragment;
import kz.edu.sdu.rauanassabayev.kundelik.Utils.BottomNavigationViewHelper;

public class MainActivity extends AppCompatActivity {
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    int selectedFragment = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView menuView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, new ScheduleFragment());
        fragmentTransaction.commit();
        new BottomNavigationViewHelper().disableShiftMode(menuView);
        menuView.setSelectedItemId(R.id.action_schedule);
        menuView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment currentFragment = null;
            fragmentTransaction  = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.action_profile:
                    if(selectedFragment != 1)currentFragment = new ProfileFragment();
                    onFragmentChangeAnimation(fragmentTransaction,1);
                    break;
                case R.id.action_schedule:
                    if(selectedFragment != 2)currentFragment = new ScheduleFragment();
                    onFragmentChangeAnimation(fragmentTransaction,2);
                    break;
                case R.id.action_game:
                    if(selectedFragment != 3)currentFragment = new GameFragment();
                    onFragmentChangeAnimation(fragmentTransaction,3);
                    break;
                case R.id.action_news:
                    if(selectedFragment != 4)currentFragment = new NewsFragment();
                    onFragmentChangeAnimation(fragmentTransaction,4);
                    break;
                default:
                    Toast.makeText(getApplicationContext(),"WOW",Toast.LENGTH_SHORT).show();
                    break;
            }
            if (currentFragment != null) {
                fragmentTransaction.replace(R.id.fragmentContainer, currentFragment);
                fragmentTransaction.addToBackStack(null);
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
                    fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);}
                else if(selectedFragment == 3 || selectedFragment == 4){
                    fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);}
                selectedFragment = 2;
                break;
            case 3:
                if(selectedFragment == 1 || selectedFragment == 2){
                    fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);}
                else if(selectedFragment == 4){
                    fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);}
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
}