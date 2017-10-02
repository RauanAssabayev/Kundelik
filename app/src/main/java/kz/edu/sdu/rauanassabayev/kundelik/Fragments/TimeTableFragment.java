package kz.edu.sdu.rauanassabayev.kundelik.Fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.edu.sdu.rauanassabayev.kundelik.Adapters.DaySubjectsAdapter;
import kz.edu.sdu.rauanassabayev.kundelik.Adapters.TimeTableAdapter;
import kz.edu.sdu.rauanassabayev.kundelik.Models.DaySubject;
import kz.edu.sdu.rauanassabayev.kundelik.R;
import kz.edu.sdu.rauanassabayev.kundelik.Utils.RecyclerItemClickListener;

public class TimeTableFragment extends Fragment {

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    List<DaySubject> daySubjectList = new ArrayList<>();
    @BindView(R.id.tvsFullDay)TextView tvsFullDay;
    @BindView(R.id.tvsScheduleTitle) TextView tvsScheduleTitle;
    @BindView(R.id.tvsYear) TextView tvsYear;
    @BindView(R.id.rv_day_time_table)RecyclerView rvDayTimeTable;
    @BindView(R.id.tv_dayOfSubject) TextView tvDayOfSubject;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time_table, container, false);
        ButterKnife.bind(this,view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvDayTimeTable.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        rvDayTimeTable.setLayoutManager(mLayoutManager);

        DaySubject d1 = new DaySubject("Пн","1");
        DaySubject d2 = new DaySubject("Вт","2");
        DaySubject d3 = new DaySubject("Ср","3");
        DaySubject d4 = new DaySubject("Чт","4");
        DaySubject d5 = new DaySubject("Пт","5");
        DaySubject d6 = new DaySubject("Сб","6");
        DaySubject d7 = new DaySubject("Вс","7");
        daySubjectList.add(d1);
        daySubjectList.add(d2);
        daySubjectList.add(d3);
        daySubjectList.add(d4);
        daySubjectList.add(d5);
        daySubjectList.add(d6);
        daySubjectList.add(d7);
        mAdapter = new TimeTableAdapter(daySubjectList);
        rvDayTimeTable.setAdapter(mAdapter);


        Typeface fontComfortaaRegular = Typeface.createFromAsset(getActivity().getAssets(), "Comfortaa-Regular.ttf");
        tvsFullDay.setTypeface(fontComfortaaRegular);
        Typeface fontComfotaaBold = Typeface.createFromAsset(getActivity().getAssets(), "Comfortaa-Bold.ttf");
        tvsScheduleTitle.setTypeface(fontComfotaaBold);
        tvsYear.setTypeface(fontComfortaaRegular);
        tvDayOfSubject.setTypeface(fontComfotaaBold);
        setMainDate(tvsFullDay);
        setMainDate(tvDayOfSubject);
        rvDayTimeTable.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), rvDayTimeTable  ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Toast.makeText(getActivity().getApplicationContext(),"GOGO",Toast.LENGTH_LONG).show();
                        final FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.fragmentContainer, new TimeTableFragment(), "GameFragment");
                        ft.commit();
                    }
                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

    }
    private void setMainDate(TextView tv) {
        Calendar mCalendar= Calendar.getInstance();
        String month = mCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        String dayweek = mCalendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
        int day = mCalendar.DAY_OF_WEEK_IN_MONTH;
        month = month.substring(0, 1).toUpperCase() + month.substring(1);
        dayweek   = dayweek.substring(0, 1).toUpperCase() + dayweek.substring(1);
        tv.setText(dayweek+", "+day+" "+month);
    }
}