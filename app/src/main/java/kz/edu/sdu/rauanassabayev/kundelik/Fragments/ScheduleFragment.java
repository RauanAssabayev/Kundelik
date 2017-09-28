package kz.edu.sdu.rauanassabayev.kundelik.Fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.edu.sdu.rauanassabayev.kundelik.Adapters.DaySubjectsAdapter;
import kz.edu.sdu.rauanassabayev.kundelik.Models.DaySubject;
import kz.edu.sdu.rauanassabayev.kundelik.R;

public class ScheduleFragment extends Fragment {

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    List<DaySubject> daySubjectList = new ArrayList<>();
    @BindView(R.id.tvsFullDay)TextView tvsFullDay;
    @BindView(R.id.rv_day_subjects)RecyclerView rvDaySubjects;
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
        DaySubject d1 = new DaySubject("11","222)");
        daySubjectList.add(d1);
        mAdapter = new DaySubjectsAdapter(daySubjectList);
        rvDaySubjects.setAdapter(mAdapter);

        setMainDate();

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
        int day = mCalendar.DAY_OF_WEEK_IN_MONTH;
        month = month.substring(0, 1).toUpperCase() + month.substring(1);
        dayweek   = dayweek.substring(0, 1).toUpperCase() + dayweek.substring(1);
        tvsFullDay.setText(dayweek+", "+day+" "+month);
    }
}