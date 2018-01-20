package kz.edu.sdu.rauanassabayev.kundelik.Adapters;

/**
 * Created by rauanassabayev on 10/14/17.
 */
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import kz.edu.sdu.rauanassabayev.kundelik.Models.SpinnerSubjectItem;
import kz.edu.sdu.rauanassabayev.kundelik.R;

public class SubjectSpinnerAdapter extends ArrayAdapter<SpinnerSubjectItem> {
    int groupid;
    Activity context;
    ArrayList<SpinnerSubjectItem> list;
    LayoutInflater inflater;
    public SubjectSpinnerAdapter(Activity context, int groupid, int id, ArrayList<SpinnerSubjectItem> list){
        super(context,id,list);
        this.list=list;
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.groupid=groupid;
    }

    public View getView(int position, View convertView, ViewGroup parent ){
        View itemView                 = inflater.inflate(groupid,parent,false);
        ImageView imageView           = (ImageView)itemView.findViewById(R.id.iv_subject_icon);
        imageView.setImageResource(list.get(position).getImageId());
        TextView textView             = (TextView)itemView.findViewById(R.id.tv_subject_name);
        textView.setText(list.get(position).getText());
        Typeface fontComfortaaRegular = Typeface.createFromAsset(parent.getContext().getAssets(), "Comfortaa-Regular.ttf");
        textView.setTypeface(fontComfortaaRegular);
        return itemView;
    }

    public View getDropDownView(int position, View convertView, ViewGroup parent){
        return getView(position,convertView,parent);
    }
}