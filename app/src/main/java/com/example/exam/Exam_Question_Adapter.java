package com.example.exam;
import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;
    public class Exam_Question_Adapter extends ArrayAdapter<Question_data_model> {

        Context mContext;
        private static class ViewHolder {
            public View view;
            TextView que;
            TextView o1;
            TextView o2;
            TextView o3;
            TextView o4;
        }

        public Exam_Question_Adapter(ArrayList<Question_data_model> data, Context context) {
            super(context, R.layout.activity_exam_panel);
            this.mContext = context;
        }
        private int lastPosition = -1;

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Question_data_model quedataModel = getItem(position);
            com.example.exam.Exam_Question_Adapter.ViewHolder viewHolder;

            if (convertView == null) {

                viewHolder = new com.example.exam.Exam_Question_Adapter.ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.activity_exam_panel, parent, false);
                viewHolder.que = convertView.findViewById(R.id.stud_exam_que);
                viewHolder.o1 = convertView.findViewById(R.id.stud_exam_o1);
                viewHolder.o2 = convertView.findViewById(R.id.stud_exam_o2);
                viewHolder.o3 = convertView.findViewById(R.id.stud_exam_o3);
                viewHolder.o4 = convertView.findViewById(R.id.stud_exam_o4);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (com.example.exam.Exam_Question_Adapter.ViewHolder) convertView.getTag();
            }
            lastPosition = position;
            assert quedataModel != null;
            viewHolder.que.setText(quedataModel.getque());
            viewHolder.o1.setText(quedataModel.geto1());
            viewHolder.o2.setText(quedataModel.geto2());
            viewHolder.o3.setText(quedataModel.geto3());
            viewHolder.o4.setText(quedataModel.geto4());
            return convertView;
        }
}
