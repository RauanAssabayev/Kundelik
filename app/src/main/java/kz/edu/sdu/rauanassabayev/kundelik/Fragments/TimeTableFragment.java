package kz.edu.sdu.rauanassabayev.kundelik.Fragments;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
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

import static android.content.Context.MODE_PRIVATE;

public class TimeTableFragment extends Fragment{
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Realm mRealm;
    String subjectName = "";
    String subjectIcon = "";
    String fromTime="",toTime = "";
    RealmResults<Subject> subjects;
    ArrayList<SpinnerSubjectItem> list;
    @BindView(R.id.tvsFullDay)TextView tvsFullDay;
    @BindView(R.id.tvsScheduleTitle) TextView tvsScheduleTitle;
    @BindView(R.id.rv_day_time_table)RecyclerView rvDayTimeTable;
    @BindView(R.id.tv_dayOfSubject) TextView tvDayOfSubject;
    @BindView(R.id.ib_addSubject) ImageButton ibAddSubject;
    @BindView(R.id.iv_back) ImageView ivBack;
    private ItemTouchHelper mItemTouchHelper;
    int selectedDay = -1;

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
        Typeface fontComfortaaRegular = Typeface.createFromAsset(getActivity().getAssets(), "Comfortaa-Regular.ttf");
        tvsFullDay.setTypeface(fontComfortaaRegular);
        Typeface fontComfotaaBold = Typeface.createFromAsset(getActivity().getAssets(), "Comfortaa-Bold.ttf");
        tvsScheduleTitle.setTypeface(fontComfotaaBold);
        tvDayOfSubject.setTypeface(fontComfotaaBold);
        setMainDate(tvsFullDay);
        setMainDate(tvDayOfSubject);

        SharedPreferences prefs = getActivity().getSharedPreferences("DEFAULT_PREFS", MODE_PRIVATE);
        selectedDay = prefs.getInt("day", -1);
        dataChanged();


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

    public void dataChanged() {

        mRealm = Realm.getDefaultInstance();
        mRealm.beginTransaction();
        subjects = mRealm.where(Subject.class).equalTo("day",selectedDay).findAllSorted("number");
        //mRealm.delete(Subject.class);
        if(!subjects.isEmpty()) {

            mAdapter = new TimeTableAdapter(subjects);
            rvDayTimeTable.setLayoutManager(new LinearLayoutManager(getContext()));
            rvDayTimeTable.setAdapter(mAdapter);
            mRealm.commitTransaction();
            ItemTouchHelper.Callback callback = new RecyclerViewSwipeHelper(rvDayTimeTable,mAdapter,subjects,getContext(),getActivity());
            mItemTouchHelper = new ItemTouchHelper(callback);
            mItemTouchHelper.attachToRecyclerView(rvDayTimeTable);

        }else{
            Log.d("MYLOGS","EMPTY");
            mRealm.cancelTransaction();
        }
    }

    @OnClick(R.id.iv_back)
    void backToShedule(){
        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentContainer, new ScheduleFragment(), "ScheduleFragment");
        ft.commit();
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
        final  EditText etOwnSubject         = dialogView.findViewById(R.id.et_own_subject);
        rl_own_subject.setVisibility(View.GONE);

        if(Build.VERSION.SDK_INT<22) {
            tvStart.setCompoundDrawables(null, null, null, null);
            tvFinish.setCompoundDrawables(null, null, null, null);
            etOwnSubject.setCompoundDrawables(null, null, null, null);
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
                if(position == 0){
                    subjectName = "-1";
                }
                else if(position == 1){
                    rl_own_subject.setVisibility(View.VISIBLE);
                    etOwnSubject.setTypeface(fontComfortaaRegular);
                    etOwnSubject.requestFocus();
                    subjectName = "-1";
                    subjectIcon = getResources().getResourceEntryName(list.get(position).getImageId());
                }else{
                    rl_own_subject.setVisibility(View.GONE);
                    subjectName = list.get(position).getText();
                    subjectIcon = getResources().getResourceEntryName(list.get(position).getImageId());
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
                Calendar calendar = Calendar.getInstance();
                int currentDay = calendar.get(Calendar.DAY_OF_WEEK);
                try {
                    if(toTime.equals("") || fromTime.equals("") || subjectName.equals("")) {
                        Toast.makeText(getContext(),"Необходимо заполнить все поля формы!",Toast.LENGTH_LONG).show();
                    }else if(subjectName.equals("-1") && etOwnSubject.getText().toString().equals("")){
                        Toast.makeText(getContext(),"Необходимо заполнить все поля формы!",Toast.LENGTH_LONG).show();
                    }
                    else {
                        if(subjectName.equals("-1")){
                            subjectName = etOwnSubject.getText().toString();
                        }
                        mRealm.beginTransaction();
                        Subject subject = mRealm.createObject(Subject.class, UUID.randomUUID().toString());
                        int i = 1;
                        if(selectedDay == 0)i = 0;
                        subject.createSubject(selectedDay,subjects.size() + i,subjectName,toTime,fromTime,0,0,subjectIcon);
                        mRealm.commitTransaction();
                        dataChanged();
                        subjectName = "";
                        toTime = "";
                        fromTime = "";
                        alertDialog.cancel();
                    }
                } finally {}

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