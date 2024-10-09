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

    }