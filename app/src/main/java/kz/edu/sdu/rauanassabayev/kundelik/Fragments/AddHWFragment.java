package kz.edu.sdu.rauanassabayev.kundelik.Fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import kz.edu.sdu.rauanassabayev.kundelik.Models.HomeWork;
import kz.edu.sdu.rauanassabayev.kundelik.Models.Subject;
import kz.edu.sdu.rauanassabayev.kundelik.R;

import static android.content.Context.MODE_PRIVATE;

public class AddHWFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private final String TAG = "AddHWFragment";
    @BindView(R.id.tv_subject_name) TextView tvSubjectName;
    @BindView(R.id.stch_day_before) Switch stchDayBefore;
    @BindView(R.id.stch_choose_time) Switch stchChooseTime;
    @BindView(R.id.tv_stc_hw) TextView tvStcHW;
    @BindView(R.id.et_hw_text)EditText etHwText;
    @BindView(R.id.fab_save) FloatingActionButton fabSave;


    boolean notifyDayBefore = false;
    Date nofifyDate = null;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_hw, container, false);
        ButterKnife.bind(this,view);
        return view;


    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String subjectName = getActivity().getIntent().getExtras().getString("subjectName");
        Typeface fontComfortaaRegular = Typeface.createFromAsset(getActivity().getAssets(), "Comfortaa-Regular.ttf");
        tvSubjectName.setText(subjectName);
        stchDayBefore.setTypeface(fontComfortaaRegular);
        stchChooseTime.setTypeface(fontComfortaaRegular);
        tvStcHW.setTypeface(fontComfortaaRegular);
        etHwText.setTypeface(fontComfortaaRegular);
        etHwText.clearFocus();
        tvSubjectName.setTypeface(fontComfortaaRegular);

        Realm mRealm = Realm.getDefaultInstance();
        mRealm.beginTransaction();

        //RealmResults<HomeWork> homeWorks = mRealm.where(HomeWork.class).equalTo("subjectName",subjectName).findAllSorted("number");
        RealmResults<HomeWork> homeWorks = mRealm.where(HomeWork.class).findAll();
        Log.d(TAG,homeWorks.size()+"");

        for(HomeWork homeWork : homeWorks){
            Log.d(TAG,homeWork.getHwText()+"-"+homeWork.getCreatedDate().getYear()+"");
        }

        mRealm.commitTransaction();

    }

    @OnClick(R.id.fab_save)
    void onClickFabSave(){

        Realm mRealm = Realm.getDefaultInstance();
        mRealm.beginTransaction();
        Calendar calendar = Calendar.getInstance();
        Date date = new Date(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH),calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),calendar.get(Calendar.SECOND));
        HomeWork hw = mRealm.createObject(HomeWork.class,UUID.randomUUID().toString());
        hw.createHW(tvSubjectName.getText()+"",true,date,etHwText.getText().toString(),date);
        mRealm.commitTransaction();

        mRealm.beginTransaction();

        List<Subject> selectedSubjects = mRealm.where(Subject.class).equalTo("name", tvSubjectName.getText()+"").findAll();
        long to = mRealm.where(HomeWork.class).equalTo("subjectName", tvSubjectName.getText() + "").count();

        for(Subject subject : selectedSubjects) {
            subject.setNotesCount((int) to);
        }
        mRealm.commitTransaction();

        getActivity().finish();

    }



    @OnCheckedChanged(R.id.stch_day_before)
    void onDayBeforeSelected(CompoundButton button, boolean checked){
        notifyDayBefore = checked;
    }

    @OnCheckedChanged(R.id.stch_choose_time)
    void onChooseTimeSelected(CompoundButton button, boolean checked){

        if(checked){
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog dialog = new DatePickerDialog(getContext(), this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            dialog.show();
        }

    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        Calendar mcurrentTime        = Calendar.getInstance();
        final int myYear             = mcurrentTime.get(Calendar.YEAR);
        final int myMonth            = mcurrentTime.get(Calendar.MONTH);
        final int day                = mcurrentTime.get(Calendar.DAY_OF_MONTH);
        final int hour               = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        final int minute             = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                stchChooseTime.setText(day+"."+myMonth+"."+myYear+","+selectedHour+" : "+selectedMinute);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle(getString(R.string.dialog_select_finish_time));
        mTimePicker.show();


    }
}