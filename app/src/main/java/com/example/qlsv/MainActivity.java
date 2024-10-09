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
                Intent intent = new Intent(MainActivity.this, sinhvien.class);
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



//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//    }


    private void initDB() {
        db = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        String sql;
        try {
            if (!isTableExists(db, "tbluser")) {
                sql = "CREATE TABLE tbluser (id_user INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,";
                sql += "username TEXT NOT NULL, ";
                sql += "password TEXT NOT NULL)";
                db.execSQL(sql);
                sql = "insert into tbluser (username, password) values('admin', 'admin')";
                db.execSQL(sql);
            }
            if (!isTableExists(db, "tblclass")) {
                sql = "CREATE TABLE tblclass (";
                sql += "id_class INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,";
                sql += "code_class TEXT, ";
                sql += "name class TEXT,";
                sql += "number_student INTEGER);";
                db.execSQL(sql);
            }
            if (!isTableExists(db, "tblstudent")) {
                sql = "CREATE TABLE tblstudent (";
                sql += "id_student INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,";
                sql += "id_class INTEGER NOT NULL,";
                sql += "code_student TEXT NOT NULL,";
                sql += "name student TEXT,";
                sql += "gender student NUMERIC,";
                sql += "birthday_student TEXT,";
                sql += "address student TEXT);";
                db.execSQL(sql);
            }
        } catch (Exception ex) {
            Toast.makeText(this, "Khởi tạo cơ sở dữ liệu không thành công", Toast.LENGTH_LONG).show();
        }
    }
    private boolean isTableExists(SQLiteDatabase database, String tableName) {
        String query = "SELECT name FROM sqlite_master WHERE type='table' AND name='" + tableName + "'";
        Cursor cursor = database.rawQuery(query, null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
        cursor.close();
    }
        return false;
    }


}