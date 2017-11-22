package kz.edu.sdu.rauanassabayev.kundelik.Fragments;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;
import kz.edu.sdu.rauanassabayev.kundelik.Adapters.SubjectSpinnerAdapter;
import kz.edu.sdu.rauanassabayev.kundelik.Adapters.TimeTableAdapter;
import kz.edu.sdu.rauanassabayev.kundelik.Helpers.ListCreator;
import kz.edu.sdu.rauanassabayev.kundelik.Models.SpinnerSubjectItem;
import kz.edu.sdu.rauanassabayev.kundelik.Models.Subject;
import kz.edu.sdu.rauanassabayev.kundelik.R;
import kz.edu.sdu.rauanassabayev.kundelik.Utils.MyApplication;
import kz.edu.sdu.rauanassabayev.kundelik.Utils.RecyclerViewSwipeHelper;

public class TimeTableFragment extends Fragment implements View.OnClickListener {
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Realm mRealm;
    String subjectName = "";
    int subjectIcon = R.drawable.ic_subject_custom;
    String fromTime="09:00",toTime = "00:00";
    List<Subject> subjectList = new ArrayList<>();
    ArrayList<SpinnerSubjectItem> list;
    @BindView(R.id.tvsFullDay)TextView tvsFullDay;
    @BindView(R.id.tvsScheduleTitle) TextView tvsScheduleTitle;
    @BindView(R.id.rv_day_time_table)RecyclerView rvDayTimeTable;
    @BindView(R.id.tv_dayOfSubject) TextView tvDayOfSubject;
    @BindView(R.id.ib_addSubject) ImageButton ibAddSubject;
    @BindView(R.id.iv_back) ImageView ivBack;
    private ItemTouchHelper mItemTouchHelper;
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
        mRealm = Realm.getDefaultInstance();
        dataChanged();

        mAdapter = new TimeTableAdapter(subjectList);
        rvDayTimeTable.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvDayTimeTable.setAdapter(mAdapter);
        ItemTouchHelper.Callback callback = new RecyclerViewSwipeHelper(mAdapter,getContext());
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(rvDayTimeTable);

        Typeface fontComfortaaRegular = Typeface.createFromAsset(getActivity().getAssets(), "Comfortaa-Regular.ttf");
        tvsFullDay.setTypeface(fontComfortaaRegular);
        Typeface fontComfotaaBold = Typeface.createFromAsset(getActivity().getAssets(), "Comfortaa-Bold.ttf");

        tvsScheduleTitle.setTypeface(fontComfotaaBold);
        tvDayOfSubject.setTypeface(fontComfotaaBold);
        setMainDate(tvsFullDay);
        setMainDate(tvDayOfSubject);
    }

    private void setMainDate(TextView tv) {
        Calendar mCalendar= Calendar.getInstance();
        String month = mCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        String dayweek = mCalendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
        int day = mCalendar.get(Calendar.DAY_OF_MONTH);
        month = month.substring(0, 1).toUpperCase() + month.substring(1);
        dayweek   = dayweek.substring(0, 1).toUpperCase() + dayweek.substring(1);
        tv.setText(dayweek+", "+day+" "+month);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragmentContainer, new ScheduleFragment(), "ScheduleFragment");
                ft.commit();
            default:break;
        }
    }

    void dataChanged(){
        mRealm = Realm.getDefaultInstance();
        mRealm.beginTransaction();
        RealmResults<Subject> subjects = mRealm.where(Subject.class).findAllSorted("number");
        //mRealm.delete(Subject.class);
        subjectList.clear();
        if(!subjects.isEmpty()) {
            for(int i = 0; i < subjects.size(); i++) {
                subjectList.add(subjects.get(i));
            }
        }else{
            Log.d("MYLOGS","EMPTY");
        }
        mAdapter = new TimeTableAdapter(subjectList);
        rvDayTimeTable.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvDayTimeTable.setAdapter(mAdapter);
        mRealm.commitTransaction();
    }

    @OnClick(R.id.ib_addSubject)
    void addSubjectDialog(){
        final Typeface fontComfortaaRegular  = Typeface.createFromAsset(getActivity().getAssets(), "Comfortaa-Regular.ttf");
        Typeface fontComfotaaBold            = Typeface.createFromAsset(getActivity().getAssets(), "Comfortaa-Bold.ttf");
        AlertDialog.Builder dialogBuilder    = new AlertDialog.Builder(getContext());
        LayoutInflater inflater              = getActivity().getLayoutInflater();
        final View dialogView                = inflater.inflate(R.layout.dialog_add_subject, null);
        ImageButton ibCloseNewSubject        = dialogView.findViewById(R.id.ib_close_new_subject);
        TextView tvSelectSubject             = dialogView.findViewById(R.id.tv_select_subject);
        final TextView tvNewSubject          = dialogView.findViewById(R.id.tv_new_subject);
        TextView tvTimeFromSubject           = dialogView.findViewById(R.id.tv_time_from_subject);
        TextView tvTimeToSubject             = dialogView.findViewById(R.id.tv_time_to_subject);
        final  TextView tvStart              = dialogView.findViewById(R.id.tv_start);
        final  TextView tvFinish             = dialogView.findViewById(R.id.tv_finish);
        Button btAddSubject                  = dialogView.findViewById(R.id.bt_add_subject);
        final  Spinner spSelectSubject       = dialogView.findViewById(R.id.sp_select_subject);
        final  RelativeLayout rl_own_subject = dialogView.findViewById(R.id.rl_own_subject);
        final  EditText et_own_subject = dialogView.findViewById(R.id.et_own_subject);
        rl_own_subject.setVisibility(View.GONE);
        if(Build.VERSION.SDK_INT<22) {
            tvStart.setCompoundDrawables(null, null, null, null);
            tvFinish.setCompoundDrawables(null, null, null, null);
            et_own_subject.setCompoundDrawables(null, null, null, null);
        }
        tvNewSubject.setTypeface(fontComfotaaBold);
        tvSelectSubject.setTypeface(fontComfortaaRegular);
        tvTimeFromSubject.setTypeface(fontComfortaaRegular);
        tvTimeToSubject.setTypeface(fontComfortaaRegular);
        btAddSubject.setText(getText(R.string.text_add));
        btAddSubject.setTypeface(fontComfotaaBold);
        dialogBuilder.setView(dialogView);
        list = ListCreator.createSubjectsList(getContext());
        final SubjectSpinnerAdapter adapter=new SubjectSpinnerAdapter(getActivity(),R.layout.spinner_item_dg,R.id.tv_subject_name,list);
        spSelectSubject.setAdapter(adapter);
        spSelectSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if(position == 1){
                    rl_own_subject.setVisibility(View.VISIBLE);
                    et_own_subject.setTypeface(fontComfortaaRegular);
                    et_own_subject.requestFocus();
                }else{
                    rl_own_subject.setVisibility(View.GONE);
                    subjectName = list.get(position).getText();
                    subjectIcon = list.get(position).getImageId();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        ibCloseNewSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
            }
        });
        btAddSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedDay;
                Bundle extras = getActivity().getIntent().getExtras();
                if(extras == null) {
                    selectedDay = 0;
                } else {
                    selectedDay = extras.getInt("selectedDay");
                }
                Calendar calendar = Calendar.getInstance();
                int currentDay = calendar.get(Calendar.DAY_OF_WEEK);
                int day = selectedDay + currentDay;
                Toast.makeText(getActivity().getApplicationContext(),day+"",Toast.LENGTH_LONG).show();
                try {
                    mRealm.beginTransaction();
                    Subject subject = mRealm.createObject(Subject.class, UUID.randomUUID().toString());
                    subject.createSubject(0,subjectList.size()+1,subjectName,toTime,fromTime,0,0,subjectIcon);
                    mRealm.commitTransaction();
                    dataChanged();
                    //mAdapter.notifyDataSetChanged();
                    mAdapter = new TimeTableAdapter(subjectList);
                    rvDayTimeTable.setAdapter(mAdapter);
                } finally {}
                alertDialog.cancel();
            }
        });
        tvStart.bringToFront();
        tvStart.setTypeface(fontComfortaaRegular);
        tvStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                final int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        tvStart.setText(selectedHour+" : "+selectedMinute);
                        fromTime = selectedHour+":"+selectedMinute;
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle(getString(R.string.dialog_select_start_time));
                mTimePicker.show();
            }
        });
        tvFinish.bringToFront();
        tvFinish.setTypeface(fontComfortaaRegular);
        tvFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                final int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        tvFinish.setText(selectedHour+" : "+selectedMinute);
                        toTime = selectedHour+":"+selectedMinute;
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle(getString(R.string.dialog_select_finish_time));
                mTimePicker.show();
            }
        });
    }
}