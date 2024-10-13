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

import java.util.ArrayList;

public class StudentListActivity extends Activity {
    Button btnOpenStudent;
    ListView lstStudent;
    SQLiteDatabase db;
    ArrayList<Student> studentList = new ArrayList<Student>();
    MyAdapterStudent adapter;
    int posselected = -1; // Giu Vi tri tren ListView //Khai báo các biến nhận kết quả trả về từ activity
    public static final int OPEN_STUDENT = 113;
    public static final int EDIT_STUDENT = 114;
    public static final int SAVE_STUDENT = 115;

    private void getStudentList() {
        db = openOrCreateDatabase(login.DATABASE_NAME, MODE_PRIVATE, null);
        Cursor c = db.rawQuery("select tblclass.id_class, tblclass.name_class, tblstudent.id_student, " + "tblstudent.code_student, tblstudent.name_student, tblstudent.gender_student," + "tblstudent.birthday student, tblstudent.address student from tbiclass," + "tblstudent where tblclass.id_class= tblstudent.id_class", null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            studentList.add(new Student(c.getString(0).toString(), c.getString(1).toString(), c.getString(2).toString(), c.getString(3).toString(), c.getString(4).toString(), c.getString(5).toString(), c.getString(6).toString(), c.getString(7).toString()));
            c.moveToNext();
            adapter = new MyAdapterStudent(this, android.R.layout.simple_list_item_1, studentList);
            lstStudent.setAdapter(adapter);
        }
    }

    public void comfirmDelete() {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        // Setting Alert Dialog Title
                alertDialogBuilder.setTitle("Xác nhận để xóa sinh viên..!!!");
        // Icon Of Alert Dialog
                alertDialogBuilder.setIcon(R.drawable.question);
        // Setting Alert Dialog Message
                alertDialogBuilder.setMessage("Bạn có chắc xóa sinh viên?");
                alertDialogBuilder.setCancelable (false);
                alertDialogBuilder.setPositiveButton ("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                //Đóng Activity hiện tại
                db = openOrCreateDatabase(login. DATABASE_NAME, MODE_PRIVATE, null);
                String id_stduent = studentList.get(posselected).getId_class();
                if (db.delete("tblstudent", "id_student=?", new String[]{id_stduent})!=-1) {
                    studentList.remove(posselected);// Xoa lop hoc ra khoi danh danh adapter.notifyDataSetChanged();// cap nhat lai adapter
                    Toast.makeText(getApplication(), "Xóa sinh viên thành công!!!", Toast.LENGTH_LONG).show();
                }
                    }
                });
                alertDialogBuilder.setNegativeButton ("Không đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

}
    @Override
    public void onCreateContextMenu (ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
// TODO Auto-generated method stub
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mnustudent, menu);
    }
    @Override
    public boolean onContextItemSelected (MenuItem item) {
// TODO Auto-generated method stub
        if(item.getItemId()==R.id.mnueditstudent) {
            Student student =
                    studentList.get(posselected);
            Bundle bundle = new Bundle();
            Intent intent =
                    new Intent(StudentListActivity.this, EditStudentActivity.class);
            bundle.putSerializable("student", student);
            intent.putExtra("data", bundle);
            startActivityForResult(intent, StudentListActivity.EDIT_STUDENT);
            return true;
        } else if (item.getItemId()==R.id.mnudeletestudent) {
            comfirmDelete();
            return true;
        }
        else if(item.getItemId()==R.id.mnudeletestudent) {
            comfirmDelete();
            return true;
        }else{
                Notify.exit(this);
                return true;
//            default:
//                return super.onContextItemSelected(item);
        }
    }
    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
// TODO Auto-generated method stub
        super.onActivityResult (requestCode, resultCode, data);
        switch (requestCode) {
            case StudentListActivity.OPEN_STUDENT:
                if (StudentListActivity.SAVE_STUDENT==resultCode) {
                Bundle bundle = data.getBundleExtra("data");
                Student student = (Student) bundle.getSerializable("student");
                studentList.add(student);
                adapter.notifyDataSetChanged();
            }
            break;
            case StudentListActivity.EDIT_STUDENT:
                Bundle bundle = data.getBundleExtra("data");
                Student student = (Student) bundle.getSerializable("student");
                studentList.set (posselected, student);
                adapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_list);
        btnOpenStudent = (Button) findViewById(R.id.btnOpenStudent);
        lstStudent = (ListView) findViewById(R.id.lstStudent);
        getStudentList();
        registerForContextMenu(lstStudent);
        btnOpenStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// TODO Auto-generated method stub
                Intent intent = new Intent(StudentListActivity.this, InsertStudentActivity.class);
                startActivityForResult(intent, StudentListActivity.OPEN_STUDENT);
            }
        });
// lay vi tri list view
        lstStudent.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) { // TODO Auto-generated method stub
                posselected = position;
                return false;
            }
        });
    }

}