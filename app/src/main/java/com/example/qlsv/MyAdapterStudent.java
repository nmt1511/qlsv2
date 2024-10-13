package com.example.qlsv;

import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyAdapterStudent extends ArrayAdapter<Student> {

    ArrayList<Student> studentList = new ArrayList<>();

    public MyAdapterStudent(@NonNull Context context, int resource, @NonNull ArrayList<Student> objects) {
        super(context, resource, objects);
        studentList = objects;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View v = convertView;
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.my_student,null);
        ImageView imgStudent = (ImageView) v.findViewById(R.id.imgStudent);
        TextView txtclassstudent = (TextView) v.findViewById(R.id.txtStudentClass);
        TextView txtnamestudent = (TextView) v.findViewById(R.id.txtStudentName);
        TextView txtcodestudent = (TextView)v.findViewById(R.id.txtStudentCode);
        TextView txtnbirthdaystudent = (TextView) v.findViewById(R.id.txtStudentBirthday);
        TextView txtgenderStudent = (TextView) v.findViewById(R.id.txtStudentGender);
        TextView txtaddress = (TextView) v.findViewById(R.id.txtStudentAddress);
        //tạo tiêu đề listview
//        if(position == 0){
//            txtclassstudent.setBackgroundColor(Color.WHITE);
//            txtnamestudent.setBackgroundColor(Color.WHITE);
//            txtcodestudent.setBackgroundColor(Color.WHITE);
//            txtnbirthdaystudent.setBackgroundColor(Color.WHITE);
//            txtgenderStudent.setBackgroundColor(Color.WHITE);
//            txtaddress.setBackgroundColor(Color.WHITE);
//        }
        //gán giá trị cho listview theo position
        String gender;
        if(studentList.get(position).getGender_student().equals("1")){
            gender = "Nam";
            imgStudent.setImageResource(R.drawable.man);
        }
        else{
            imgStudent.setImageResource(R.drawable.woman);
            gender = "Nữ";
        }
        txtclassstudent.setText("Mã lớp: "+ studentList.get(position).getName_class());
        txtcodestudent.setText("MSSV: "+studentList.get(position).getCode_student());
        txtnamestudent.setText("Tên sinh viên: " + studentList.get(position).getName_student());
        txtnbirthdaystudent.setText("Ngày Sinh: " + studentList.get(position).getBirthday_student());
        txtgenderStudent.setText("Giới tính: "+ gender.toString());
        txtaddress.setText("Địa chỉ: " + studentList.get(position).getAddress_student());
        return v;
    }
}
