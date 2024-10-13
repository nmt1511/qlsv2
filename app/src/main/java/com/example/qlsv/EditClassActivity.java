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

public class EditClassActivity extends Activity {
    Button btnSaveClass, btnClearClass, btnCloseClass;
    EditText edtCode, edtName, edtNumber;
    String id_class;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_class);
        initWidget();
        getData();
// Gần các sự kiện cho các Button
        btnSaveClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Bundle bundle = new Bundle();
                Intent intent = getIntent();
                if (saveClass()) {
                    Room r = new Room(id_class, edtCode.getText().toString(),
                            edtName.getText().toString(), edtNumber.getText().toString());
                    bundle.putSerializable("room", r);
                    intent.putExtra("data", bundle);
                    setResult(ClassList.SAVE_CLASS, intent);
                    Toast.makeText(getApplication(), "Cập nhật lớp học thành công", Toast.LENGTH_LONG).show();
                    finish();

                }
            }
        });
        btnClearClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                clearClass();
            }
        });
        btnCloseClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub Notify.exit(EditClassActivity.this);
            }
        });
    }

    private void initWidget() {
        btnSaveClass = (Button) findViewById(R.id.btnSaveEditClass);
        btnClearClass = (Button) findViewById(R.id.btnClearEdit);
        btnCloseClass = (Button) findViewById(R.id.btnCloseEditClass);
        edtCode = (EditText) findViewById(R.id.edtEditClassCode);
        edtName = (EditText) (EditText) findViewById(R.id.edtEditClassName);
        findViewById(R.id.edtEditClassNumber);
    }

    private void getData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("data");
        Room room = (Room) bundle.getSerializable("room");
        id_class = room.getId_class();
        edtCode.setText(room.getCode_class());
        edtName.setText(room.getName_class());
        edtNumber.setText(room.getClass_number());
    }

    private boolean saveClass() {
        try {
            SQLiteDatabase db = openOrCreateDatabase(login.DATABASE_NAME, MODE_PRIVATE, null);
            ContentValues values = new ContentValues();
            values.put("code_class", edtCode.getText().toString());
            values.put("name_class", edtName.getText().toString());
            values.put("number_student", Integer.parseInt(edtNumber.getText().toString()));
            if (db.update("tblclass", values, "id_class=?", new String[]{id_class}) != -1)
                return true;
        } catch (Exception ex) {
            Toast.makeText(getApplication(), "Cập nhật lớp học không thành công", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    private void clearClass() {
        edtCode.setText("");
        edtName.setText("");
        edtNumber.setText("");
    }


}

