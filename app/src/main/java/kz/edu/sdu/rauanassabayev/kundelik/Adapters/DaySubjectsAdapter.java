package kz.edu.sdu.rauanassabayev.kundelik.Adapters;

/**
 * Created by rauanassabayev on 9/26/17.
 */
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import kz.edu.sdu.rauanassabayev.kundelik.Fragments.GameFragment;
import kz.edu.sdu.rauanassabayev.kundelik.Models.DaySubject;
import kz.edu.sdu.rauanassabayev.kundelik.R;

public class DaySubjectsAdapter extends RecyclerView.Adapter<DaySubjectsAdapter.MyViewHolder>{
    private List<DaySubject> dataSet;
    Typeface fontComfortaaRegular;
    Typeface fontComfotaaBold;
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_day,tv_countSubjects;
        View vLeftLine,vRightLine;
        public MyViewHolder(View itemView) {
            super(itemView);
            this.tv_day           = (TextView) itemView.findViewById(R.id.tv_day);
            this.tv_countSubjects = (TextView) itemView.findViewById(R.id.tv_countSubjects);
            vLeftLine             = (View) itemView.findViewById(R.id.vLeftLine);
            vRightLine            = (View) itemView.findViewById(R.id.vRightLine);
        }
    }
    public DaySubjectsAdapter(List<DaySubject> dataModels) {
        this.dataSet = dataModels;
    }
    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        final View mainview = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_day_subjects, parent, false);
        fontComfortaaRegular = Typeface.createFromAsset(parent.getContext().getAssets(), "Comfortaa-Regular.ttf");
        fontComfotaaBold = Typeface.createFromAsset(parent.getContext().getAssets(), "Comfortaa-Bold.ttf");
        MyViewHolder myViewHolder = new MyViewHolder(mainview);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder,int position) {
        TextView tv_day = holder.tv_day;
        TextView tv_countSubjects = holder.tv_countSubjects;
        tv_day.setTypeface(fontComfotaaBold);
        tv_countSubjects.setTypeface(fontComfortaaRegular);
        tv_day.setText(dataSet.get(position).getDay());
        tv_countSubjects.setText(dataSet.get(position).getCount()+" занятий");
        if(position == dataSet.size() - 1){
            View vLeftLine = holder.vLeftLine;
            View vRightLine = holder.vRightLine;
            vLeftLine.setVisibility(View.INVISIBLE);
            vRightLine.setVisibility(View.INVISIBLE);
        }
    }
    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}