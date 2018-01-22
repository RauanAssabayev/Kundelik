package kz.edu.sdu.rauanassabayev.kundelik;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Locale;

import io.realm.Realm;
import kz.edu.sdu.rauanassabayev.kundelik.Fragments.GameFragment;
import kz.edu.sdu.rauanassabayev.kundelik.Fragments.NewsFragment;
import kz.edu.sdu.rauanassabayev.kundelik.Fragments.ProfileFragment;
import kz.edu.sdu.rauanassabayev.kundelik.Fragments.WeekScheduleListFragment;
import kz.edu.sdu.rauanassabayev.kundelik.Helpers.FontFactory;
import kz.edu.sdu.rauanassabayev.kundelik.Models.HomeWork;
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


        SharedPreferences prefs = getSharedPreferences("DEFAULT_PREFS", MODE_PRIVATE);
        String lang = prefs.getString("lang", "-1");

        if(lang.equals("-1")){
            Typeface fontComfotaaBold  = FontFactory.getInstance().getFont(this,"Comfortaa-Bold.ttf");
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            View chooseLangView = inflater.inflate(R.layout.static_choose_lang,null);
            builder.setView(chooseLangView);
            builder.setTitle("Выберите язык");
            TextView tvKZLang = chooseLangView.findViewById(R.id.tv_lang_kz);
            TextView tvRULang = chooseLangView.findViewById(R.id.tv_lang_ru);
            LinearLayout llKZLang = chooseLangView.findViewById(R.id.ll_lang_kz);
            LinearLayout llRULang = chooseLangView.findViewById(R.id.ll_lang_ru);
            llKZLang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor = getSharedPreferences("DEFAULT_PREFS", MODE_PRIVATE).edit();
                    editor.putString("lang", "kz");
                    editor.apply();
                    Intent refresh = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(refresh);
                    finish();
                }
            });

            llRULang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor = getSharedPreferences("DEFAULT_PREFS", MODE_PRIVATE).edit();
                    editor.putString("lang", "ru");
                    editor.apply();
                    Intent refresh = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(refresh);
                    finish();
                }
            });

            tvKZLang.setTypeface(fontComfotaaBold);
            tvRULang.setTypeface(fontComfotaaBold);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        }

        if(lang.equals("kz")){
            Resources res = getApplicationContext().getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            android.content.res.Configuration conf = res.getConfiguration();
            conf.locale = new Locale("kk");
            res.updateConfiguration(conf, dm);
        }


//        Intent refresh = new Intent(MainActivity.this, MainActivity.class);
//        startActivity(refresh);
//        finish();

        setContentView(R.layout.activity_main);

//        Realm mRealm = Realm.getDefaultInstance();
//        mRealm.beginTransaction();
//        mRealm.delete(HomeWork.class);
//        mRealm.commitTransaction();

        BottomNavigationView menuView = findViewById(R.id.bottom_navigation);
        if(savedInstanceState == null){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragmentContainer, new WeekScheduleListFragment());
            fragmentTransaction.commit();
        }
        //new BottomNavigationViewHelper().disableShiftMode(menuView);
        menuView.setSelectedItemId(R.id.action_schedule);
        menuView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment currentFragment = null;

            int count = fragmentManager.getBackStackEntryCount();
            for(int i = 0; i < count; ++i) {
                fragmentManager.popBackStack();
            }

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
                while (fragmentManager.getBackStackEntryCount() > 0){
                    fragmentManager.popBackStackImmediate();
                }
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