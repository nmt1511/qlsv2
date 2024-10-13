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
        public View getView ( int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View v = convertView;
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.my_student, null);
            ImageView imgstudent = (ImageView) v.findViewById(R.id.imgStudent);
            TextView txtclassstudent = (TextView) v.findViewById(R.id.txtStudentClass);
            TextView txtnamestudent = (TextView) v.findViewById(R.id.txtStudentName);
            TextView txtbirthdaystudent = (TextView) v.findViewById(R.id.txtStudentBirthday);
            TextView txtgenderstudent = (TextView) v.findViewById(R.id.txtStudentGender);
            TextView txtaddressstudent = (TextView) v.findViewById(R.id.txtStudentAddress);
            if (position == 0) {
                txtclassstudent.setBackgroundColor(Color.WHITE);
                txtnamestudent.setBackgroundColor(Color.WHITE);
                txtbirthdaystudent.setBackgroundColor(Color.WHITE);
                txtgenderstudent.setBackgroundColor(Color.WHITE);
                txtaddressstudent.setBackgroundColor(Color.WHITE);
            }
            imgstudent.setImageResource(R.drawable.ic_launcher_background);
            txtclassstudent.setText("Ma lớp: " + studentList.get(position).getName_class());
            txtnamestudent.setText("Tên sinh viên: " + studentList.get(position).getName_student());
            txtbirthdaystudent.setText("Ngay sinh: " + studentList.get(position).getBirthday_student());
            txtgenderstudent.setText("gioi tính: " + studentList.get(position).getGender_student());
            txtaddressstudent.setText("Địa chỉ: " + studentList.get(position).getAddress_student());
            return v;
        }
    }
