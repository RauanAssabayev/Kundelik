package kz.edu.sdu.rauanassabayev.kundelik.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kz.edu.sdu.rauanassabayev.kundelik.R;

public class AddHWFragment extends Fragment {
    @BindView(R.id.tv_subject_name) TextView tvSubjectName;
    @BindView(R.id.rl_bottom) RelativeLayout rvBottom;
    @BindView(R.id.fl_root)FrameLayout flRoot;
    @BindView(R.id.et_hw_text)EditText etHwText;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_hw, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String subjectName=getArguments().getString("subjectName");
        etHwText = new android.support.v7.widget.AppCompatEditText(getContext()) {
            @Override
            public boolean onKeyPreIme(int keyCode, KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.KEYCODE_BACK){
                    rvBottom.setPadding(0,0,0,0);
                }
                return super.onKeyPreIme(keyCode, event);
            }
        };
        tvSubjectName.setText(subjectName);


    }

    @OnClick(R.id.et_hw_text)
    void rootClick(){
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isAcceptingText()) {
            float scale = getResources().getDisplayMetrics().density;
            int dpAsPixels = (int) (56*scale + 0.5f);
            rvBottom.setPadding(0,0,0,dpAsPixels);
        }
    }
}
