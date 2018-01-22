package kz.edu.sdu.rauanassabayev.kundelik.Fragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;
import kz.edu.sdu.rauanassabayev.kundelik.Adapters.GradeListAdapter;
import kz.edu.sdu.rauanassabayev.kundelik.Adapters.HWListAdapter;
import kz.edu.sdu.rauanassabayev.kundelik.Helpers.FontFactory;
import kz.edu.sdu.rauanassabayev.kundelik.Models.Grade;
import kz.edu.sdu.rauanassabayev.kundelik.Models.HomeWork;
import kz.edu.sdu.rauanassabayev.kundelik.R;

/**
 * A simple {@link Fragment} subclass.
 */

public class ListGradeFragment extends Fragment {
    private GradeListAdapter mAdapter;
    private List<Grade> gradeList = new ArrayList<>();
    @BindView(R.id.tv_stc_grade)
    TextView tv_stc_grade;
    @BindView(R.id.tv_subject_name)TextView tvSubjectName;
    @BindView(R.id.recycler_view)RecyclerView recyclerView;
    public ListGradeFragment() {}
    String subjectName = "-1";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_grade, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Typeface fontComfortaaRegular  = FontFactory.getInstance().getFont(getActivity(),"Comfortaa-Regular.ttf");
        subjectName = getActivity().getIntent().getExtras().getString("subjectName");
        tvSubjectName.setText(subjectName);
        tvSubjectName.setTypeface(fontComfortaaRegular);
        tv_stc_grade.setTypeface(fontComfortaaRegular);
        prepareHWData();
    }

    public void prepareHWData() {
        Realm mRealm = Realm.getDefaultInstance();
        mRealm.beginTransaction();
        RealmResults<Grade> grades = mRealm.where(Grade.class).equalTo("subjectName",subjectName).findAll();
        mRealm.commitTransaction();
        for(Grade grade : grades)gradeList.add(grade);
        Collections.reverse(gradeList);
        mAdapter = new GradeListAdapter(gradeList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }
}
