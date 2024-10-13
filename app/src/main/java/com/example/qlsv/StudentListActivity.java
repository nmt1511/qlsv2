package com.example.qlsv;
import android.annotation.SuppressLint;
import android.app.Activity;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;

public class StudentListActivity extends Activity {
    Button btnOpenStudent;
    ListView lstStudent;
    SQLiteDatabase db;
    ArrayList<Student> studentList = new ArrayList<Student>();
    MyAdapterStudent adapterStudent;
    int posselected = -1;
    public static final int OPEN_STUDENT = 113;
    public static final int EDIT_STUDENT = 114;
    public static final int SAVE_STUDENT = 115;
    public static final int SAVE_EDIT_STUDENT = 116;

    private void getStudentList() {
        db = openOrCreateDatabase(login.DATABASE_NAME, MODE_PRIVATE, null);
        Cursor c = db.rawQuery("select tblclass.id_class, tblclass.name_class, tblstudent.id_student, " + "tblstudent.code_student, tblstudent.name_student, tblstudent.gender_student," + "tblstudent.birthday student, tblstudent.address student from tbiclass," + "tblstudent where tblclass.id_class= tblstudent.id_class", null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            studentList.add(new Student(c.getString(0).toString(), c.getString(1).toString(), c.getString(2).toString(), c.getString(3).toString(), c.getString(4).toString(), c.getString(5).toString(), c.getString(6).toString(), c.getString(7).toString()));
            c.moveToNext();
            adapterStudent = new MyAdapterStudent(this, android.R.layout.simple_list_item_1, studentList);
            lstStudent.setAdapter(adapterStudent);
        }
    }

    private void confirmDelete(){
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("xác nhận để xóa sinh viên.");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage("Bạn có chắc xóa sinh viên?");
        builder.setCancelable(false);
        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                db = openOrCreateDatabase(login.DATABASE_NAME, MODE_PRIVATE,null);
                String id_student = studentList.get(posselected).getId_student();
                if(db.delete("tblstudent","id_student=?", new String[] {id_student}) != -1){
                    studentList.remove(posselected);
                    adapterStudent.notifyDataSetChanged();
                    Toast.makeText(getApplication(),"Xóa sinh viên thành công!!!",Toast.LENGTH_LONG).show();
                }
            }
        });
        builder.setNegativeButton("Không đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mnustudent, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.mnueditstudent){
            Student student = studentList.get(posselected);
            Bundle bundle = new Bundle();
            Intent intentedit = new Intent(StudentListActivity.this, EditStudentActivity.class);
            bundle.putSerializable("student",student);;
            intentedit.putExtra("data", bundle);
            startActivityForResult(intentedit, StudentListActivity.EDIT_STUDENT);
            return true;
        }else if(item.getItemId() == R.id.mnudeletestudent){
            confirmDelete();
            return true;
        } else if (item.getItemId() == R.id.mnuclosestudent) {
            Notify.exit(this);
            return true;
        }else
            return super.onContextItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case StudentListActivity.OPEN_STUDENT:
                if(resultCode == StudentListActivity.SAVE_STUDENT){
                    Bundle bundle = data.getBundleExtra("data");
                    Student student = (Student) bundle.getSerializable("student");
                    studentList.add(student);
                    adapterStudent.notifyDataSetChanged();
                }
                break;
            case StudentListActivity.EDIT_STUDENT:
                if(resultCode == StudentListActivity.SAVE_EDIT_STUDENT){
                    Bundle bundle = data.getBundleExtra("data");
                    Student student = (Student) bundle.getSerializable("student");
                    studentList.set(posselected, student);
                    adapterStudent.notifyDataSetChanged();
                }
                break;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        btnOpenStudent = (Button) findViewById(R.id.btnOpenStudent);
        lstStudent = (ListView) findViewById(R.id.lstStudent);
        getStudentList();
        registerForContextMenu(lstStudent);
        btnOpenStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentListActivity.this, InsertStudentActivity.class);
                startActivityForResult(intent, StudentListActivity.OPEN_STUDENT);
            }
        });
        lstStudent.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                posselected = i;
                return false;
            }
        });
    }

}