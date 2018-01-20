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
import kz.edu.sdu.rauanassabayev.kundelik.Models.HomeWork;
import kz.edu.sdu.rauanassabayev.kundelik.Models.Subject;
import kz.edu.sdu.rauanassabayev.kundelik.R;

/**
 * Created by rauanassabayev on 1/17/18.
 */

public class HWListAdapter extends RecyclerView.Adapter<HWListAdapter.MyViewHolder> {

    private List<HomeWork> navBarItemsList;
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

    public HWListAdapter(List<HomeWork> navBarItemsList) {
        this.navBarItemsList = navBarItemsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
         itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_hw_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        final Typeface fontComfortaaRegular  = Typeface.createFromAsset(itemView.getContext().getAssets(), "Comfortaa-Regular.ttf");
        final HomeWork homeWork = navBarItemsList.get(position);
        final String hname = homeWork.getSubjectName();
        Date myDate = homeWork.getCreatedDate();
        String strDate = myDate.getDate()+"."+myDate.getMonth()+1+"."+(myDate.getYear()+"").substring(2,4);
        holder.date.setText(strDate);
        holder.hwText.setText(homeWork.getHwText());
        holder.hwText.setTypeface(fontComfortaaRegular);
        holder.date.setTypeface(fontComfortaaRegular);
        holder.ivRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Realm mRealm = Realm.getDefaultInstance();
                mRealm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        HomeWork rmHomeWork = realm.where(HomeWork.class).equalTo("id", homeWork.getId() + "").findFirst();
                        rmHomeWork.deleteFromRealm();
                    }
                });

                navBarItemsList.remove(position);
                HWListAdapter.this.notifyDataSetChanged();
                mRealm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        List<Subject> selectedSubjects = realm.where(Subject.class).equalTo("name", hname).findAll();
                        for(Subject subject : selectedSubjects) {
                            subject.setNotesCount(subject.getNotesCount()-1);
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