package kz.edu.sdu.rauanassabayev.kundelik.Adapters;

/**
 * Created by rauanassabayev on 9/26/17.
 */
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import kz.edu.sdu.rauanassabayev.kundelik.Models.WeekSubject;
import kz.edu.sdu.rauanassabayev.kundelik.R;

public class DaySubjectsAdapter extends RecyclerView.Adapter<DaySubjectsAdapter.MyViewHolder>{
    private List<WeekSubject> dataSet;
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
    public DaySubjectsAdapter(List<WeekSubject> dataModels) {
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
        if(Build.VERSION.SDK_INT<20){
            tv_countSubjects.setGravity(Gravity.CENTER | Gravity.TOP);
            tv_day.setGravity(Gravity.CENTER | Gravity.TOP);
            tv_countSubjects.setPadding(0,15,0,0);
            tv_day.setPadding(0,15,0,0);
        }
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