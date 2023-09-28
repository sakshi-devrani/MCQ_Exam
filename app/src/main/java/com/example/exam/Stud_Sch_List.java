package com.example.exam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Stud_Sch_List  extends ArrayAdapter<Stud_Data_Model> {

    Context mContext;
    public Stud_Sch_List(ArrayList<Stud_Data_Model> data, Context context) {
        super(context, R.layout.stud_sch_list, data);
        this.mContext = context;
    }

    private static class ViewHolder {
        TextView subj;
    }
    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Stud_Data_Model studdataModel = getItem(position);
        Stud_Sch_List.ViewHolder viewHolder;

        if (convertView == null) {

            viewHolder = new Stud_Sch_List.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.stud_sch_list, parent, false);
            viewHolder.subj = convertView.findViewById(R.id.schedule);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (Stud_Sch_List.ViewHolder) convertView.getTag();
        }
        lastPosition = position;
        assert studdataModel != null;
        viewHolder.subj.setText(studdataModel.getsubj());

        return convertView;
    }
}


