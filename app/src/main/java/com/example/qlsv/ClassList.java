package com.example.qlsv;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
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
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ClassList extends Activity {
    ListView lstClass;
    Button btnopenclass;
    ArrayList<Room> classList = new ArrayList<Room>();
    MyAdapterClass adapter;
    SQLiteDatabase db;
    int posselected = -1; // Giu Vi tri tren ListView
    //Khai báo các biến nhận kết quả trả về từ activity
    public static final int OPEN_CLASS = 113;
    public static final int EDIT_CLASS = 114;
    public static final int SAVE_CLASS = 115;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_list);
        lstClass = (ListView) findViewById(R.id.lstclass);
        btnopenclass = (Button) findViewById(R.id.btnOpenClass);
        getClassList();
        registerForContextMenu(lstClass);
        btnopenclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            // TODO Auto-generated method stub
                Intent intent = new Intent(ClassList.this, InsertClassActivity.class);
                startActivityForResult(intent, ClassList.OPEN_CLASS);
            }
        });

//Gắn sự kiện cho Listview để lấy vị trí để xóa 1 đối tượng khỏi arrayList (Đè lên màn hình di động)
        lstClass.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                // TODO Auto-generated method stub
                posselected = position;
                return false;
            }
        });
    }


    @Override
    public void onCreateContextMenu (ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
// TODO Auto-generated method stub
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mnuclass, menu);
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onContextItemSelected (MenuItem item) {
// TODO Auto-generated method stub
        switch (item.getItemId())
        {
            case R.id.mnueditclass:
                Room room = classList.get(posselected);
                Bundle bundle = new Bundle();
                Intent intent = new Intent(ClassList.this, EditClassActivity.class);
                bundle.putSerializable("room", room);
                intent.putExtra("data", bundle);
                startActivityForResult(intent, ClassList.EDIT_CLASS);
                return true;
            case R.id.mnudeleteclass:
                comfirmDelete();
                return true;
            case R.id.mnucloseclass:
                Notify.exit(this);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
// TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ClassList.OPEN_CLASS:
                if (resultCode == ClassList.SAVE_CLASS){
                Bundle bundle = data.getBundleExtra("data");
                    Room room = (Room) bundle.getSerializable("room");
                classList.add(room);
                adapter.notifyDataSetChanged();
            }
            break;
            case ClassList.EDIT_CLASS:
                if (resultCode == ClassList.SAVE_CLASS){
                Bundle bundle =data.getBundleExtra("data");
                Room room = (Room)bundle.getSerializable("lop");
                classList.set(posselected, room);
                adapter.notifyDataSetChanged();
            }
            break;
        }
    }


    private void getClassList() {
        try {
// Them tieu de danh sach lop hoc
            classList.add(new Room("Mä lớp ", "Tên lớp", "Sỉ số"));
            db = openOrCreateDatabase(login.DATABASE_NAME, MODE_PRIVATE, null);
            Cursor c = db.query("tblclass", null, null, null, null, null, null);
            c.moveToFirst();
            while (!c.isAfterLast()) {
                classList.add(new Room(c.getInt(0) + "", c.getString(1).toString(), c.getString(2).toString(), c.getInt(3) + ""));
                c.moveToNext();
            }
            adapter = new MyAdapterClass(this, android.R.layout.simple_list_item_1, classList);
            lstClass.setAdapter(adapter);
        } catch (Exception ex) {
            Toast.makeText(getApplication(), "Loi" + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    // Canh bao xoa lop hoc
    public void comfirmDelete () {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
// Setting Alert Dialog Title
        alertDialogBuilder.setTitle("Xác nhận để xóa lớp học..!!!");
        // Icon Of Alert Dialog
        alertDialogBuilder.setIcon(R.drawable.ic_launcher_background);
        // Setting Alert Dialog Message
        alertDialogBuilder.setMessage("Bạn có chắc xóa lớp học?");
        alertDialogBuilder.setCancelable (false); alertDialogBuilder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface argo, int argl) {
                //Đông Activity hiện tại
                db = openOrCreateDatabase(login.DATABASE_NAME, MODE_PRIVATE, null);
                String id_class = classList.get(posselected).getId_class();
                if (db.delete("tblclass", "id_class=?", new String[]{id_class}) != -1) {
                    classList.remove(posselected);// Xoa lop hoc ra khoi danh danh adapter.notifyDataSetChanged();// cap nhat lai adapter Toast.makeText(getApplication(), "Xóa lớp học thành công!!!", Toast.LENGTH_LONG).show();

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

}
