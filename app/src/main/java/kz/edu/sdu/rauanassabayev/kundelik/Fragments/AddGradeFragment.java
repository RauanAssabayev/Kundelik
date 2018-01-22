package kz.edu.sdu.rauanassabayev.kundelik.Fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import kz.edu.sdu.rauanassabayev.kundelik.Helpers.FontFactory;
import kz.edu.sdu.rauanassabayev.kundelik.Models.Grade;
import kz.edu.sdu.rauanassabayev.kundelik.Models.Subject;
import kz.edu.sdu.rauanassabayev.kundelik.R;

/**
 * A simple {@link Fragment} subclass.
 */

public class AddGradeFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.tv_subject_name) TextView tvSubjectName;
    @BindView(R.id.tv_stc_grade) TextView tvStcGrade;
    @BindView(R.id.tv_stc_tip) TextView tvStcTip;
    @BindView(R.id.tv_tip) TextView tvTip;
    @BindView(R.id.bt_grade1)Button btGrade1;
    @BindView(R.id.bt_grade2)Button btGrade2;
    @BindView(R.id.bt_grade3)Button btGrade3;
    @BindView(R.id.bt_grade4)Button btGrade4;
    @BindView(R.id.bt_grade5)Button btGrade5;
    @BindView(R.id.fab_save) FloatingActionButton fabSave;
    Button selectedGrade;
    int gradeValue = 0;
    public AddGradeFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_grade, container, false);
        ButterKnife.bind(this,view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String subjectName = getActivity().getIntent().getExtras().getString("subjectName");
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        final Typeface fontComfortaaRegular  = FontFactory.getInstance().getFont(getActivity(),"Comfortaa-Regular.ttf");

        tvSubjectName.setText(subjectName);
        tvStcGrade.setTypeface(fontComfortaaRegular);
        tvSubjectName.setTypeface(fontComfortaaRegular);
        tvStcTip.setTypeface(fontComfortaaRegular);
        tvTip.setTypeface(fontComfortaaRegular);
        btGrade1.setTypeface(fontComfortaaRegular);
        btGrade2.setTypeface(fontComfortaaRegular);
        btGrade3.setTypeface(fontComfortaaRegular);
        btGrade4.setTypeface(fontComfortaaRegular);
        btGrade5.setTypeface(fontComfortaaRegular);
        btGrade1.setOnClickListener(this);
        btGrade2.setOnClickListener(this);
        btGrade3.setOnClickListener(this);
        btGrade4.setOnClickListener(this);
        btGrade5.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.bt_grade1:
                setSelectedGrade(btGrade1,1);
                break;
            case R.id.bt_grade2:
                setSelectedGrade(btGrade2,2);
                break;
            case R.id.bt_grade3:
                setSelectedGrade(btGrade3,3);
                break;
            case R.id.bt_grade4:
                setSelectedGrade(btGrade4,4);
                break;
            case R.id.bt_grade5:
                setSelectedGrade(btGrade5,5);
                break;

        }
    }


    @OnClick(R.id.fab_save)
    void onClickFabSave(){
        if(gradeValue != 0) {
            Realm mRealm = Realm.getDefaultInstance();

            mRealm.beginTransaction();
            Calendar calendar = Calendar.getInstance();
            Date date = new Date(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
            Grade grade = mRealm.createObject(Grade.class, UUID.randomUUID().toString());
            grade.create(tvSubjectName.getText() + "", gradeValue, date);
            mRealm.commitTransaction();

            mRealm.beginTransaction();
            List<Subject> selectedSubjects = mRealm.where(Subject.class).equalTo("name", tvSubjectName.getText() + "").findAll();
            double to = mRealm.where(Grade.class).equalTo("subjectName", tvSubjectName.getText() + "").average("grade");
            for (Subject subject : selectedSubjects) {
                subject.setAvgGrades((float)to);
            }
            mRealm.commitTransaction();

            getActivity().finish();
        }else{
            Toast.makeText(getContext(),"Выберите оценку",Toast.LENGTH_SHORT).show();
        }
    }

    void setSelectedGrade(Button btGrade,int grade){
        this.gradeValue = grade;
        if(selectedGrade != null){
            selectedGrade.setBackground(getResources().getDrawable(R.drawable.bg_rounded_primary));
            selectedGrade.setTextColor(getResources().getColor(R.color.colorPrimaryWhite));
        }
        btGrade.setBackground(getResources().getDrawable(R.drawable.bg_rounded_primary_dark));
        btGrade.setTextColor(getResources().getColor(R.color.colorPrimaryWhite));
        selectedGrade = btGrade;
    }
}