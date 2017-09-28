package kz.edu.sdu.rauanassabayev.kundelik.Adapters;

/**
 * Created by rauanassabayev on 9/26/17.
 */
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

import kz.edu.sdu.rauanassabayev.kundelik.Models.DaySubject;
import kz.edu.sdu.rauanassabayev.kundelik.R;

public class DaySubjectsAdapter extends RecyclerView.Adapter<DaySubjectsAdapter.MyViewHolder> {
    private List<DaySubject> dataSet;
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }
    public DaySubjectsAdapter(List<DaySubject> dataModels) {
        this.dataSet = dataModels;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_day_subjects, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {
        //TextView tvsubject = holder.tvsubject;
        //TextView tvmd1 = holder.tvmd1;
    }
    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}