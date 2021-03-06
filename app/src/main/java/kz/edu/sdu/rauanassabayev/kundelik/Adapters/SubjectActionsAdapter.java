package kz.edu.sdu.rauanassabayev.kundelik.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import kz.edu.sdu.rauanassabayev.kundelik.Models.MenuSubjectActions;
import kz.edu.sdu.rauanassabayev.kundelik.R;

/**
 * Created by rauanassabayev on 12/17/17.
 */

public class SubjectActionsAdapter extends ArrayAdapter<MenuSubjectActions> implements View.OnClickListener{
    private ArrayList<MenuSubjectActions> dataSet;
    Context mContext;
    private class ViewHolder {
        TextView text;
        ImageView icon;
    }
    public SubjectActionsAdapter(ArrayList<MenuSubjectActions> data, Context context) {
       super(context, R.layout.row_nav_drawer, data);
        this.dataSet = data;
        this.mContext=context;
    }
    @Override
    public void onClick(View v) {
        int position=(Integer) v.getTag();
        Object object= getItem(position);
        MenuSubjectActions dataModel=(MenuSubjectActions)object;
        switch (v.getId()){
        }
    }
    private int lastPosition = -1;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        MenuSubjectActions dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        final View result;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_nav_drawer, parent, false);
            viewHolder.text = (TextView) convertView.findViewById(R.id.iv_menu_text);
            viewHolder.icon = (ImageView) convertView.findViewById(R.id.iv_menu_icon);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }
//            Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
//            result.startAnimation(animation);
        lastPosition = position;
        viewHolder.text.setText(dataModel.getText().toString());
        viewHolder.icon.setImageResource(dataModel.getIcon());
        // Return the completed view to render on screen
        return convertView;
    }
}