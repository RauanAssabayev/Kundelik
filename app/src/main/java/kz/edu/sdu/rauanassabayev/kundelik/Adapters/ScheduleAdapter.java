package kz.edu.sdu.rauanassabayev.kundelik.Adapters;

/**
 * Created by rauanassabayev on 9/26/17.
 */

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.List;
import io.realm.Realm;
import io.realm.RealmResults;
import kz.edu.sdu.rauanassabayev.kundelik.Fragments.ListHWFragment;
import kz.edu.sdu.rauanassabayev.kundelik.Fragments.ScheduleFragment;
import kz.edu.sdu.rauanassabayev.kundelik.Helpers.FontFactory;
import kz.edu.sdu.rauanassabayev.kundelik.Models.HomeWork;
import kz.edu.sdu.rauanassabayev.kundelik.Models.Subject;
import kz.edu.sdu.rauanassabayev.kundelik.R;
import kz.edu.sdu.rauanassabayev.kundelik.SubjectExtraActivity;


public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.MyViewHolder> {

    private final String TAG = "ScheduleAdapter";

    private List<Subject> dataSet;
    Typeface fontComfortaaRegular;
    Typeface fontComfotaaBold;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Context context;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_time_from_subject,tv_time_to_subject,tv_countSubjects,tv_subject_name,tv_notes_count,tv_attach_count;
        ImageView iv_ic_edit,iv_subject_icon;
        LinearLayout ll_action_subject;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.tv_countSubjects      = itemView.findViewById(R.id.tv_countSubject);
            this.iv_ic_edit            = itemView.findViewById(R.id.iv_ic_edit);
            this.tv_time_from_subject  = itemView.findViewById(R.id.tv_time_from_subject);
            this.tv_time_to_subject    = itemView.findViewById(R.id.tv_time_to_subject);
            this.tv_subject_name       = itemView.findViewById(R.id.tv_subject_name);
            this.tv_notes_count        = itemView.findViewById(R.id.tv_notes_count);
            this.tv_attach_count       = itemView.findViewById(R.id.tv_attach_count);
            this.iv_subject_icon       = itemView.findViewById(R.id.iv_subject_icon);
            this.ll_action_subject     = itemView.findViewById(R.id.ll_action_subject);
        }
    }

    public ScheduleAdapter(List<Subject> dataModels) {
        this.dataSet = dataModels;
    }

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        context = parent.getContext();
        final View mainview = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_subjects, parent, false);
        fontComfortaaRegular = FontFactory.getInstance().getFont((Activity) (parent.getContext()),"Comfortaa-Regular.ttf");
        fontComfotaaBold     = FontFactory.getInstance().getFont((Activity) (parent.getContext()), "Comfortaa-Bold.ttf");
        MyViewHolder myViewHolder = new MyViewHolder(mainview);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        TextView tv_countSubjects     = holder.tv_countSubjects;
        TextView tv_time_from_subject = holder.tv_time_from_subject;
        TextView tv_time_to_subject   = holder.tv_time_to_subject;
        TextView tv_subject_name      = holder.tv_subject_name;
        TextView tv_notes_count       = holder.tv_notes_count;
        TextView tv_attach_count      = holder.tv_attach_count;
        ImageView iv_subject_icon     = holder.iv_subject_icon;
        ImageView iv_ic_edit          = holder.iv_ic_edit;
        LinearLayout ll_action_subject= holder.ll_action_subject;

        iv_ic_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Bundle bundle=new Bundle();
//                bundle.putString("subjectName", dataSet.get(position).getName());
//                AddHWFragment HWFragment=new AddHWFragment();
//                HWFragment.setArguments(bundle);
//                fragmentManager = ((FragmentActivity) view.getContext()).getSupportFragmentManager();
//                fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.setCustomAnimations(
//                        R.anim.flip_right_in,
//                        R.anim.flip_right_out,
//                        R.anim.flip_left_in,
//                        R.anim.flip_left_out);
//                fragmentTransaction.replace(R.id.fragmentContainer,HWFragment);
//                fragmentTransaction.commit();

                Intent intent = new Intent(context, SubjectExtraActivity.class);
                intent.putExtra("action","add");
                intent.putExtra("subjectName",dataSet.get(position).getName());
                context.startActivity(intent);
            }
        });

        ll_action_subject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                final FragmentTransaction ft = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
//                ft.replace(R.id.fragmentContainer, new ListHWFragment(), "WeekScheduleListFragment");
//                ft.commit();
                  Intent intent = new Intent(context,SubjectExtraActivity.class);
                  intent.putExtra("action","list");
                  intent.putExtra("subjectName",dataSet.get(position).getName());
                  context.startActivity(intent);
            }
        });

        tv_countSubjects.setText(dataSet.get(position).getNumber()+"");
        tv_countSubjects.setTypeface(fontComfortaaRegular);
        tv_time_from_subject.setText(dataSet.get(position).getTimeFrom());
        tv_time_from_subject.setTypeface(fontComfortaaRegular);
        tv_time_to_subject.setText(dataSet.get(position).getTimeTo());
        tv_time_to_subject.setTypeface(fontComfortaaRegular);
        tv_subject_name.setText(dataSet.get(position).getName());
        tv_subject_name.setTypeface(fontComfortaaRegular);
        tv_notes_count.setText(dataSet.get(position).getNotesCount()+"");
        double avgGrades = Math.round(dataSet.get(position).getAvgGrades() * 100.0) / 100.0;
        tv_attach_count.setText(avgGrades+"");
        tv_attach_count.setTypeface(fontComfortaaRegular);
        tv_notes_count.setTypeface(fontComfortaaRegular);
        int resId = context.getResources().getIdentifier(dataSet.get(position).getIcon(), "drawable", context.getPackageName());
        iv_subject_icon.setImageResource(resId);

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

}