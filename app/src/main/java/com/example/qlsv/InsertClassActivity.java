package com.example.qlsv;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
public class InsertClassActivity extends Activity{
        Button btnSaveClass, btnClearClass, btnCloseClass;
        EditText edtClassCode, edtClassName, edtClassNumber;
        SQLiteDatabase db;
        private void initWidget() {
        btnSaveClass = (Button) findViewById(R.id.btnSaveInsertClass);
        btnClearClass = (Button) findViewById(R.id.btnClearInsertClass);
        btnCloseClass = (Button) findViewById(R.id.btnCloseInsertClass);
        edtClassCode = (EditText) findViewById(R.id.edtClassCode);
        edtClassName = (EditText) findViewById(R.id.edtClassName);
        edtClassNumber = (EditText) findViewById(R.id.edtClassNumber);
        }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView(R.layout.activity_insert_class);
        initWidget();
        btnSaveClass.setOnClickListener (new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                long id = saveClass();
                Bundle bundle = new Bundle();
                Intent intent = getIntent();
                if (id != -1) { // =-1 Thêm không thành công
                // Lớp học gồm 4 trường Id class, code class name class, number class
                    Room r = new Room(id + "", edtClassCode.getText().toString(),
                            edtClassName.getText().toString(), edtClassNumber.getText().toString());
                    bundle.putSerializable("room", r);
                    intent.putExtra("data", bundle);
                    setResult(ClassList.SAVE_CLASS, intent);
                    Toast.makeText(getApplication(), "Thêm lớp học thành công", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
        btnClearClass.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
// TODO Auto-generated method stub clearClass();
            }
        });
        btnCloseClass.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
// TODO Auto-generated method stub Notify.exit(InsertClassActivity.this);
            }
        });
        }

        private long saveClass() {
            try{
                db = openOrCreateDatabase(login.DATABASE_NAME, MODE_PRIVATE, null);
                ContentValues values = new ContentValues();
                values.put("code_class", edtClassCode.getText().toString());
                values.put("name_class", edtClassName.getText().toString());
                values.put("number_student", Integer.parseInt(edtClassNumber.getText().toString()));
                long id=db.insert("tblclass", null, values);
                if(id!=-1){
                   return id;
                }
            }
            catch (Exception ex){
                Toast.makeText(getApplication(), "Lỗi"+ex.getMessage(), Toast.LENGTH_LONG).show();
            }
            return -1;

        }
        private void clearClass(){
            edtClassCode.setText("");
            edtClassName.setText("");
            edtClassNumber.setText("");
        }
}
