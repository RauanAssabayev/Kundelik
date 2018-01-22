package kz.edu.sdu.rauanassabayev.kundelik.Fragments;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;
import kz.edu.sdu.rauanassabayev.kundelik.Adapters.WeekScheduleListAdapter;
import kz.edu.sdu.rauanassabayev.kundelik.Models.Subject;
import kz.edu.sdu.rauanassabayev.kundelik.Models.WeekScheduleItem;
import kz.edu.sdu.rauanassabayev.kundelik.R;
import kz.edu.sdu.rauanassabayev.kundelik.Utils.RecyclerItemClickListener;

import static android.content.Context.MODE_PRIVATE;
public class WeekScheduleListFragment extends Fragment {
    private final String TAG = "WeekScheduleFragment";
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    List<WeekScheduleItem> daySubjectList = new ArrayList<>();
    @BindView(R.id.tvsFullDay)TextView tvsFullDay;
    @BindView(R.id.tvsScheduleTitle) TextView tvsScheduleTitle;
    @BindView(R.id.tvsYear) TextView tvsYear;
    @BindView(R.id.rv_day_subjects)RecyclerView rvDaySubjects;
    private Realm mRealm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        rvDaySubjects.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        rvDaySubjects.setLayoutManager(mLayoutManager);
        int days [] = new int[7];

        mRealm = Realm.getDefaultInstance();
        mRealm.beginTransaction();
        RealmResults<Subject> subjects = mRealm.where(Subject.class).findAll();
        for(Subject subject : subjects){
            days[subject.getDay()]= days[subject.getDay()]+1;
            Log.d(TAG,subject.getName());
        }
        mRealm.commitTransaction();

        daySubjectList.clear();
        String daysName [] = {"Пн","Вт","Ср","Чт","Пт","Сб","Вс"} ;
        for(int i = 0; i<7;i++){
            WeekScheduleItem day = new WeekScheduleItem(daysName[i],days[i]+"");
            daySubjectList.add(day);
        }

        mAdapter = new WeekScheduleListAdapter(daySubjectList);
        rvDaySubjects.setAdapter(mAdapter);
        Typeface fontComfortaaRegular = Typeface.createFromAsset(getActivity().getAssets(), "Comfortaa-Regular.ttf");
        tvsFullDay.setTypeface(fontComfortaaRegular);
        Typeface fontComfotaaBold = Typeface.createFromAsset(getActivity().getAssets(), "Comfortaa-Bold.ttf");
        tvsScheduleTitle.setTypeface(fontComfotaaBold);
        tvsYear.setTypeface(fontComfortaaRegular);
        setMainDate();

        rvDaySubjects.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), rvDaySubjects  ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        final FragmentTransaction ft = getFragmentManager().beginTransaction();
                        SharedPreferences.Editor editor = getActivity().getSharedPreferences("DEFAULT_PREFS", MODE_PRIVATE).edit();
                        editor.putInt("day", position);
                        editor.apply();
                        ft.replace(R.id.fragmentContainer, new ScheduleFragment(), "ScheduleFragment");
                        ft.addToBackStack("ScheduleFragment");
                        ft.commit();
                    }
                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

//        tv = (TextView) getActivity().findViewById(R.id.textView);
//        tv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final FragmentTransaction ft = getFragmentManager().beginTransaction();
//                ft.replace(R.id.fragmentContainer, new GameFragment(), "GameFragment");
//                ft.commit();
//            }
//        });
    }

    private void setMainDate() {
        Calendar mCalendar= Calendar.getInstance();
        String month = mCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        String dayweek = mCalendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
        int day = mCalendar.get(Calendar.DAY_OF_MONTH);
        month = month.substring(0, 1).toUpperCase() + month.substring(1);
        dayweek   = dayweek.substring(0, 1).toUpperCase() + dayweek.substring(1);
        String displayDate = dayweek+", "+day+" "+month;
        String displayYear = mCalendar.get(Calendar.YEAR)+"";
        tvsFullDay.setText(displayDate);
        tvsYear.setText(displayYear);
    }
}