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

public class login extends Activity {
    public static final String DATABASE_NAME = "student.db";
    SQLiteDatabase db;
    EditText edtUsername, edtPassword;
    Button btnCloseLogin, btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initDB();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUsername = (EditText) findViewById(R.id.edtUsername);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnCloseLogin = (Button) findViewById(R.id.btnCloseLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                if (username.isEmpty()) {
                    Toast.makeText(login.this, "Vui lòng nhập tài khoản", Toast.LENGTH_LONG).show();
                    edtUsername.requestFocus();
                } else if (password.isEmpty()) {
                    Toast.makeText(login.this, "Vui lòng nhập mật khẩu", Toast.LENGTH_LONG).show();
                    edtPassword.requestFocus();
                } else if (isUser(edtUsername.getText().toString(), edtPassword.getText().toString())) {

                    Intent intent = new Intent(login.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(login.this, "Đăng nhập thất bại", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnCloseLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
        private boolean isUser(String username, String password){
            try{
                db = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
                Cursor c = db.rawQuery("select * from tbluser where username = ? and password = ?", new String[]{username, password});
                c.moveToFirst();
                if(c.getCount() > 0){
                    c.moveToFirst();
                    return true;
                }
            }catch (Exception ex){
                Toast.makeText(this, "Lỗi đăng nhập", Toast.LENGTH_LONG).show();

            }
            return false;
        }

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
                sql += "name_class TEXT,";
                sql += "number_student INTEGER);";
                db.execSQL(sql);
            }
            if (!isTableExists(db, "tblstudent")) {
                sql = "CREATE TABLE tblstudent (";
                sql += "id_student INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,";
                sql += "id_class INTEGER NOT NULL,";
                sql += "code_student TEXT NOT NULL,";
                sql += "name_student TEXT,";
                sql += "gender_student NUMERIC,";
                sql += "birthday_student TEXT,";
                sql += "address_student TEXT);";
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