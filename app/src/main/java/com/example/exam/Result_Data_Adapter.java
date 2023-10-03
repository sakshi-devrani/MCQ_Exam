package com.example.exam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

public class Result_Data_Adapter extends ArrayAdapter<Result_Data_Model> {
    Context mContext;
    private static class ViewHolder {
        public View view;
        TextView id;
        TextView name;
        TextView subj;
        TextView result;
    }

    public Result_Data_Adapter(ArrayList<Result_Data_Model> data, Context context) {
        super(context, R.layout.result_layout, data);
        this.mContext = context;
    }
    private int lastPosition = -1;
    int count_correct_answer = 0;
    int count_wrong_answer = 0;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Result_Data_Model resultdataModel = getItem(position);
        Result_Data_Adapter.ViewHolder viewHolder;

        if (convertView == null) {

            viewHolder = new com.example.exam.Result_Data_Adapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.result_layout, parent, false);
            viewHolder.id = convertView.findViewById(R.id.result_stud_id);
            viewHolder.name = convertView.findViewById(R.id.result_stud_name);
            viewHolder.subj = convertView.findViewById(R.id.result_stud_subj);
            viewHolder.result = convertView.findViewById(R.id.result_stud_result);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        lastPosition = position;
        assert resultdataModel != null;
        viewHolder.id.setText(resultdataModel.getid());
        viewHolder.name.setText(resultdataModel.getname());
        viewHolder.subj.setText(resultdataModel.getsubj());
        viewHolder.result.setText(resultdataModel.getresult());
        return convertView;
    }
}
