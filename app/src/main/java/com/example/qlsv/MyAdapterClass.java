package com.example.qlsv;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapterClass extends ArrayAdapter<Room> {
    ArrayList<Room> classList = new ArrayList<Room>();
    public MyAdapterClass(Context context, int Resource, ArrayList<Room> objects) {
        super(context,Resource,objects);
        classList = objects;
    }
    @Override
    public View getView (int position, View convertView, ViewGroup parent) {
// TODO Auto-generated method stub
        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService (Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.my_class, null);
        TextView txtcodeclass = (TextView) v.findViewById(R.id.txtclasscode);
        TextView txtnameclass = (TextView) v.findViewById(R.id.txtclassname);
        TextView txtnumberclass = (TextView) v.findViewById(R.id.txtclassnumber);
// Tao tieu de listview
        if (position==0) {
            txtcodeclass.setBackgroundColor(Color.WHITE);
            txtnameclass.setBackgroundColor(Color.WHITE);
            txtnumberclass.setBackgroundColor(Color.WHITE);
        }
// gan gia trì cho listview theo position
            txtcodeclass.setText(classList.get(position).getCode_class());
            txtnameclass.setText(classList.get(position).getName_class());
            txtnumberclass.setText(classList.get(position).getClass_number());
            return v;
        }
        
}
