package kz.edu.sdu.rauanassabayev.kundelik.Adapters;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Date;
import java.util.List;
import io.realm.Realm;
import kz.edu.sdu.rauanassabayev.kundelik.Models.Grade;
import kz.edu.sdu.rauanassabayev.kundelik.Models.Subject;
import kz.edu.sdu.rauanassabayev.kundelik.R;


/**
 * Created by rauanassabayev on 1/23/18.
 */

public class GradeListAdapter extends RecyclerView.Adapter<GradeListAdapter.MyViewHolder> {

    private List<Grade> navBarItemsList;
    View itemView;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView hwText, date;
        public ImageView ivRemove;
        public MyViewHolder(View view) {
            super(view);
            hwText   = view.findViewById(R.id.tv_hw_text);
            date     = view.findViewById(R.id.tv_created_date);
            ivRemove = view.findViewById(R.id.iv_remove);
        }
    }

    public GradeListAdapter(List<Grade> navBarItemsList) {
        this.navBarItemsList = navBarItemsList;
    }

    @Override
    public GradeListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_grade_list, parent, false);
        return new GradeListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GradeListAdapter.MyViewHolder holder, final int position) {

        final Typeface fontComfortaaRegular  = Typeface.createFromAsset(itemView.getContext().getAssets(), "Comfortaa-Regular.ttf");
        final Grade grade = navBarItemsList.get(position);
        final String hname = grade.getSubjectName();
        Date myDate = grade.getCreatedDate();

        String strDate = myDate.getDate()+"."+myDate.getMonth()+1+"."+(myDate.getYear()+"").substring(2,4)+","+myDate.getHours()+":"+myDate.getMinutes();

        holder.date.setText(strDate);
        holder.hwText.setText((grade.getGrade()+""));
        holder.hwText.setTypeface(fontComfortaaRegular);
        holder.date.setTypeface(fontComfortaaRegular);
        holder.ivRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Realm mRealm = Realm.getDefaultInstance();
                mRealm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Grade rmGrade = realm.where(Grade.class).equalTo("id", grade.getId() + "").findFirst();
                        rmGrade.deleteFromRealm();
                    }
                });

                navBarItemsList.remove(position);
                GradeListAdapter.this.notifyDataSetChanged();
                mRealm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        List<Subject> selectedSubjects = realm.where(Subject.class).equalTo("name", hname).findAll();
                        double to = realm.where(Grade.class).equalTo("subjectName", hname).average("grade");
                        for (Subject subject : selectedSubjects) {
                            subject.setAvgGrades((float)to);
                        }
                    }
                });

            }
        });

    }

    @Override
    public int getItemCount() {
        return navBarItemsList.size();
    }
}