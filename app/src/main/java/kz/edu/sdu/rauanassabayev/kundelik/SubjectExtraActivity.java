package kz.edu.sdu.rauanassabayev.kundelik;
import android.graphics.Typeface;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.edu.sdu.rauanassabayev.kundelik.Adapters.ViewPagerAdapter;
import kz.edu.sdu.rauanassabayev.kundelik.Fragments.AddHWFragment;
import kz.edu.sdu.rauanassabayev.kundelik.Fragments.AddGradeFragment;

public class SubjectExtraActivity extends AppCompatActivity {

    @BindView(R.id.vp_subject_actions) ViewPager vpSubjectActions;
    @BindView(R.id.tl_subject_actions) TabLayout tlSubjectActions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_extra);
        ButterKnife.bind(this);
        Typeface fontComfortaaRegular = Typeface.createFromAsset(getAssets(), "Comfortaa-Regular.ttf");
        setupViewPager(vpSubjectActions);

        tlSubjectActions.setupWithViewPager(vpSubjectActions);
        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText("Задачи");
        tabOne.setTypeface(fontComfortaaRegular);
        tlSubjectActions.getTabAt(0).setCustomView(tabOne);
        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText("Оценки");
        tabTwo.setTypeface(fontComfortaaRegular);
        tlSubjectActions.getTabAt(1).setCustomView(tabTwo);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new AddHWFragment(),"Задачи");
        adapter.addFrag(new AddGradeFragment(),"Оценки");
        viewPager.setAdapter(adapter);
    }
}