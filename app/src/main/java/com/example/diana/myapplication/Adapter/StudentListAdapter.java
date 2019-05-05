package com.example.diana.myapplication.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.diana.myapplication.Model.Student;
import com.example.diana.myapplication.R;

import java.util.ArrayList;

public class StudentListAdapter extends ArrayAdapter<Student> {

    private Context mContext;
    int mResource;

    public StudentListAdapter(Context context, int resource, ArrayList<Student> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String fullName = getItem(position).getFullName();
        String number = getItem(position).getNumber();
        String markInfo = getItem(position).getMarkInfo();

        Student student = new Student(fullName, number, markInfo);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvFullName = (TextView) convertView.findViewById(R.id.textView1);
        TextView tvNumber = (TextView) convertView.findViewById(R.id.textView2);
        TextView tvMarkInfo = (TextView) convertView.findViewById(R.id.textView3);

        tvFullName.setText(fullName);
        tvNumber.setText(number);
        tvMarkInfo.setText(markInfo);

        return convertView;
    }
}
