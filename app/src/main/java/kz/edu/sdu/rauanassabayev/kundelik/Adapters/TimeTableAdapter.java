package kz.edu.sdu.rauanassabayev.kundelik.Adapters;

/**
 * Created by rauanassabayev on 9/26/17.
 */
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import kz.edu.sdu.rauanassabayev.kundelik.Models.Subject;
import kz.edu.sdu.rauanassabayev.kundelik.R;

public class TimeTableAdapter extends RecyclerView.Adapter<TimeTableAdapter.MyViewHolder>{
    private List<Subject> dataSet;
    Typeface fontComfortaaRegular;
    Typeface fontComfotaaBold;
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_time_from_subject,tv_countSubjects,tv_subject_name;
        ImageView iv_ic_edit;
        public MyViewHolder(View itemView) {
            super(itemView);
              this.tv_countSubjects      = itemView.findViewById(R.id.tv_countSubject);
              this.iv_ic_edit            = itemView.findViewById(R.id.iv_ic_edit);
              this.tv_time_from_subject  = itemView.findViewById(R.id.tv_time_from_subject);
              this.tv_subject_name       = itemView.findViewById(R.id.tv_subject_name);
        }
    }
    public TimeTableAdapter(List<Subject> dataModels) {
        this.dataSet = dataModels;
    }
    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        final View mainview = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_subjects, parent, false);
        fontComfortaaRegular = Typeface.createFromAsset(parent.getContext().getAssets(), "Comfortaa-Regular.ttf");
        fontComfotaaBold = Typeface.createFromAsset(parent.getContext().getAssets(), "Comfortaa-Bold.ttf");
        MyViewHolder myViewHolder = new MyViewHolder(mainview);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder,int position) {
          TextView tv_countSubjects     = holder.tv_countSubjects;
          TextView tv_time_from_subject = holder.tv_time_from_subject;
          TextView tv_subject_name      = holder.tv_subject_name;
          ImageView iv_ic_edit = holder.iv_ic_edit;
          iv_ic_edit.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  Toast.makeText(view.getContext(),"WORKS",Toast.LENGTH_LONG).show();
              }
          });
          tv_countSubjects.setText(dataSet.get(position).getDay()+"");
          tv_countSubjects.setTypeface(fontComfortaaRegular);
          tv_time_from_subject.setText(dataSet.get(position).getTimeFrom());
          tv_time_from_subject.setTypeface(fontComfortaaRegular);
          tv_subject_name.setText(dataSet.get(position).getName());
          tv_subject_name.setTypeface(fontComfortaaRegular);
          //iv_ic_edit.setTypeface(fontComfortaaRegular);
    }
    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}