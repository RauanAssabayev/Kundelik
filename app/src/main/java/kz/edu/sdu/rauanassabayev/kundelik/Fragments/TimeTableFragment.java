package kz.edu.sdu.rauanassabayev.kundelik.Fragments;

import android.app.TimePickerDialog;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;
import kz.edu.sdu.rauanassabayev.kundelik.Adapters.SubjectSpinnerAdapter;
import kz.edu.sdu.rauanassabayev.kundelik.Adapters.TimeTableAdapter;
import kz.edu.sdu.rauanassabayev.kundelik.Models.SpinnerSubjectItem;
import kz.edu.sdu.rauanassabayev.kundelik.Models.Subject;
import kz.edu.sdu.rauanassabayev.kundelik.R;

public class TimeTableFragment extends Fragment implements View.OnClickListener {
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Realm mRealm;
    List<Subject> subjectList = new ArrayList<>();
    @BindView(R.id.tvsFullDay)TextView tvsFullDay;
    @BindView(R.id.tvsScheduleTitle) TextView tvsScheduleTitle;
    //@BindView(R.id.tvsYear) TextView tvsYear;
    @BindView(R.id.rv_day_time_table)RecyclerView rvDayTimeTable;
    @BindView(R.id.tv_dayOfSubject) TextView tvDayOfSubject;
    @BindView(R.id.ib_addSubject) ImageButton ibAddSubject;
    @BindView(R.id.iv_back) ImageView ivBack;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time_table, container, false);
        ButterKnife.bind(this,view);
        Realm mRealm = Realm.getDefaultInstance(); // opens "myrealm.realm"
        try {
            mRealm.beginTransaction();
            Subject subject = mRealm.createObject(Subject.class, UUID.randomUUID().toString());
            subject.createSubject(0,0,"HISTORY OF KAZAKHSTAN 2","10:00","10:45",0,0);
            mRealm.commitTransaction();
        } finally {
            mRealm.close();
        }
        return view;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Realm.init(getActivity());
        rvDayTimeTable.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        rvDayTimeTable.setLayoutManager(mLayoutManager);
        //Subject d1 = new Subject(1,1,"HISTORY OF KAZAKHSTAN","10:00","10:45",1,1);
        //subjectList.add(d1);
        mRealm = Realm.getDefaultInstance();
        mRealm.beginTransaction();
        RealmResults<Subject> subjects = mRealm.where(Subject.class).findAll();
        if(!subjects.isEmpty()) {
            for(int i = subjects.size() - 1; i >= 0; i--) {
                Log.d("MYLOGS",subjects.get(i).getName());
            }
        }else{
            Log.d("MYLOGS","EMPTY");
        }
        mRealm.commitTransaction();

        ivBack.setOnClickListener(this);
        ibAddSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Typeface fontComfortaaRegular = Typeface.createFromAsset(getActivity().getAssets(), "Comfortaa-Regular.ttf");
                Typeface fontComfotaaBold     = Typeface.createFromAsset(getActivity().getAssets(), "Comfortaa-Bold.ttf");
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater       = getActivity().getLayoutInflater();
                final View dialogView         = inflater.inflate(R.layout.dialog_add_subject, null);
                ImageButton ibCloseNewSubject = dialogView.findViewById(R.id.ib_close_new_subject);
                TextView tvSelectSubject      = dialogView.findViewById(R.id.tv_select_subject);
                final TextView tvNewSubject   = dialogView.findViewById(R.id.tv_new_subject);
                TextView tvTimeFromSubject    = dialogView.findViewById(R.id.tv_time_from_subject);
                TextView tvTimeToSubject      = dialogView.findViewById(R.id.tv_time_to_subject);
                final TextView tvStart        = dialogView.findViewById(R.id.tv_start);
                final TextView tvFinish       = dialogView.findViewById(R.id.tv_finish);
                if(Build.VERSION.SDK_INT<22){
                    tvStart.setCompoundDrawables( null, null, null, null );
                    tvFinish.setCompoundDrawables( null, null, null, null );
                }
                Button btAddSubject           = dialogView.findViewById(R.id.bt_add_subject);
                Spinner spSelectSubject       = dialogView.findViewById(R.id.sp_select_subject);
                tvNewSubject.setTypeface(fontComfotaaBold);
                tvSelectSubject.setTypeface(fontComfortaaRegular);
                tvTimeFromSubject.setTypeface(fontComfortaaRegular);
                tvTimeToSubject.setTypeface(fontComfortaaRegular);
                btAddSubject.setTypeface(fontComfotaaBold);
                dialogBuilder.setView(dialogView);
                ArrayList<SpinnerSubjectItem> list=new ArrayList<>();
                createSubjectsList(list);
                SubjectSpinnerAdapter adapter=new SubjectSpinnerAdapter(getActivity(),R.layout.spinner_item_dg,R.id.tv_subject_name,list);
                spSelectSubject.setAdapter(adapter);
                final AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();
                ibCloseNewSubject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.cancel();
                    }
                });
                tvStart.bringToFront();
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
                            }
                        }, hour, minute, true);//Yes 24 hour time
                        mTimePicker.setTitle("Выберите время начало");
                        mTimePicker.show();
                    }
                });
                tvFinish.bringToFront();
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
                            }
                        }, hour, minute, true);//Yes 24 hour time
                        mTimePicker.setTitle("Выберите время завершение");
                        mTimePicker.show();
                    }
                });
            }
        });
        mAdapter = new TimeTableAdapter(subjectList);
        rvDayTimeTable.setAdapter(mAdapter);
        Typeface fontComfortaaRegular = Typeface.createFromAsset(getActivity().getAssets(), "Comfortaa-Regular.ttf");
        tvsFullDay.setTypeface(fontComfortaaRegular);
        Typeface fontComfotaaBold = Typeface.createFromAsset(getActivity().getAssets(), "Comfortaa-Bold.ttf");
        tvsScheduleTitle.setTypeface(fontComfotaaBold);
        //tvsYear.setTypeface(fontComfortaaRegular);
        tvDayOfSubject.setTypeface(fontComfotaaBold);
        setMainDate(tvsFullDay);
        setMainDate(tvDayOfSubject);
//        rvDayTimeTable.addOnItemTouchListener(
//                new RecyclerItemClickListener(getContext(), rvDayTimeTable  ,new RecyclerItemClickListener.OnItemClickListener() {
//                    @Override public void onItemClick(View view, int position) {
//                        AlertDialog.Builder builder;
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                            builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Dialog_Alert);
//                        } else {
//                            builder = new AlertDialog.Builder(getContext());
//                        }
//                        builder.setTitle("Delete entry")
//                                .setMessage("Are you sure you want to delete this entry?")
//                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        // continue with delete
//                                    }
//                                })
//                                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        // do nothing
//                                    }
//                                })
//                                .setIcon(android.R.drawable.ic_dialog_alert)
//                                .show();
//                    }
//                    @Override public void onLongItemClick(View view, int position) {
//                        // do whatever
//                    }
//                })
//        );
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

    private void createSubjectsList(ArrayList<SpinnerSubjectItem> list){
        list.add(new SpinnerSubjectItem("-Нажмите, чтобы выбрать -",R.drawable.ic_touch));
        list.add(new SpinnerSubjectItem(getString(R.string.subject_algebra), R.drawable.ic_subject_algebra));
        list.add(new SpinnerSubjectItem("Английский",                   R.drawable.ic_subject_english));
        list.add(new SpinnerSubjectItem("Астрономия",                   R.drawable.ic_subject_astronomy));
        list.add(new SpinnerSubjectItem("Биология",                     R.drawable.ic_subject_biology));
        list.add(new SpinnerSubjectItem("Всемирная история",            R.drawable.ic_subject_world_history));
        list.add(new SpinnerSubjectItem("География",                    R.drawable.ic_subject_geography));
        list.add(new SpinnerSubjectItem("Геометрия",                    R.drawable.ic_subject_geometry));
        list.add(new SpinnerSubjectItem("Естествознание",           R.drawable.ic_subject_natural_science));
        list.add(new SpinnerSubjectItem("Информатика",              R.drawable.ic_subject_informatics));
        list.add(new SpinnerSubjectItem("История Казахстана",       R.drawable.ic_subject_history_of_kazakhstan));
        list.add(new SpinnerSubjectItem("Казахский язык",           R.drawable.ic_subject_kazakh_language));
        list.add(new SpinnerSubjectItem("Краеведение",              R.drawable.ic_subject_regional_studies));
        list.add(new SpinnerSubjectItem("Литература",               R.drawable.ic_subject_literature));
        list.add(new SpinnerSubjectItem("Математика",               R.drawable.ic_subject_math));
        list.add(new SpinnerSubjectItem("Музыка",                   R.drawable.ic_subject_music));
        list.add(new SpinnerSubjectItem("НВП",                      R.drawable.ic_subject_bmt));
        list.add(new SpinnerSubjectItem("ОБЖ",                      R.drawable.ic_subject_lsf));
        list.add(new SpinnerSubjectItem("Обществознание",           R.drawable.ic_subject_social_scince));
        list.add(new SpinnerSubjectItem("Право",                    R.drawable.ic_subject_law));
        list.add(new SpinnerSubjectItem("Природоведение",           R.drawable.ic_subject_natural_history));
        list.add(new SpinnerSubjectItem("Религиозные исследования", R.drawable.ic_subject_religion)); //icon
        list.add(new SpinnerSubjectItem("Рисование",                R.drawable.ic_subject_art)); //icon
        list.add(new SpinnerSubjectItem("Русская литература",       R.drawable.ic_subject_russian_literature)); //icon
        list.add(new SpinnerSubjectItem("Русский язык",             R.drawable.ic_subject_russian_language));
        list.add(new SpinnerSubjectItem("Самопознание",             R.drawable.ic_subject_russian_language));
        list.add(new SpinnerSubjectItem("Технология",               R.drawable.ic_subject_technology));
        list.add(new SpinnerSubjectItem("Труд",                     R.drawable.ic_subject_labor));
        list.add(new SpinnerSubjectItem("Физика",                   R.drawable.ic_subject_physical_education));
        list.add(new SpinnerSubjectItem("Физическая культура",      R.drawable.ic_subject_physical_education));
        list.add(new SpinnerSubjectItem("Философия",                R.drawable.ic_subject_philosophy));
        list.add(new SpinnerSubjectItem("Химия",                    R.drawable.ic_subject_chemistry));
        list.add(new SpinnerSubjectItem("Челевок и общество",       R.drawable.ic_subject_individual_and_society));
        list.add(new SpinnerSubjectItem("Черчение",                 R.drawable.ic_subject_drawing));
        list.add(new SpinnerSubjectItem("Чистописание",             R.drawable.ic_subject_calligraphy));
        list.add(new SpinnerSubjectItem("Чтение",                   R.drawable.ic_subject_reading));
        list.add(new SpinnerSubjectItem("Экология",                 R.drawable.ic_subject_ecology));
        list.add(new SpinnerSubjectItem("Экономика",                R.drawable.ic_subject_economy));
    }

    private View.OnTouchListener spinnerOnTouch = new View.OnTouchListener() {
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_UP) {}
            return false;
        }
    };

    private static View.OnKeyListener spinnerOnKey = new View.OnKeyListener() {
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
                return true;
            } else {
                return false;
            }
        }
    };
}