package com.example.exam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class admin_home_listveiw_Adapter extends ArrayAdapter<Data_Model> {

    Context mContext;

    private static class ViewHolder {
        TextView name;
        TextView email;
        TextView contact;
        TextView id;

    }

    public admin_home_listveiw_Adapter(ArrayList<Data_Model> data, Context context) {
        super(context, R.layout.admin_home_listview, data);
        this.mContext = context;

    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Data_Model dataModel = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.admin_home_listview, parent, false);
            viewHolder.name = convertView.findViewById(R.id.stud_name);
            viewHolder.email = convertView.findViewById(R.id.stud_email);
            viewHolder.contact = convertView.findViewById(R.id.stud_contact);
            viewHolder.id = convertView.findViewById(R.id.stud_id);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        lastPosition = position;
        assert dataModel != null;
        viewHolder.name.setText(dataModel.getname());
        viewHolder.email.setText(dataModel.getemail());
        viewHolder.contact.setText(dataModel.getContact());
        viewHolder.id.setText(dataModel.getid());
        return convertView;
    }
}

