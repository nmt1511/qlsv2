package com.example.qlsv;

import android.app.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View.OnClickListener;
import androidx.activity.EdgeToEdge;


public class MainActivity extends Activity {
    public static final String DATABASE_NAME = "student.db";
    SQLiteDatabase db;
    Button btnOpenClass, btnOpenStudent, btnExitApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnOpenClass = (Button) findViewById(R.id.btnOpenClass);
        btnOpenStudent = (Button) findViewById(R.id.btnOpenStudent);
        btnExitApp = (Button) findViewById(R.id.btnExitApp);


        btnOpenClass.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(MainActivity.this, ClassList.class);
                startActivity(intent);
            }
        });
        btnOpenStudent.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(MainActivity.this, StudentListActivity.class);
                startActivity(intent);
            }
        });
        btnExitApp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Notify.exit(MainActivity.this);
            }
        });
    }





}