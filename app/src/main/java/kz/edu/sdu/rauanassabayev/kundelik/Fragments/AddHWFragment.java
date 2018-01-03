package kz.edu.sdu.rauanassabayev.kundelik.Fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kz.edu.sdu.rauanassabayev.kundelik.R;

public class AddHWFragment extends Fragment {
    @BindView(R.id.tv_subject_name) TextView tvSubjectName;
    @BindView(R.id.stch_day_before) Switch stchDayBefore;
    @BindView(R.id.stch_choose_time) Switch stchChooseTime;
    @BindView(R.id.tv_stc_hw) TextView tvStcHW;
    @BindView(R.id.et_hw_text)EditText etHwText;
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
        tvSubjectName.setTypeface(fontComfortaaRegular);
    }
}
