package com.example.qlsv;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;


public class Notify {
    public static void exit(Context context){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("xác nhận để thoát??>S");
        alertDialogBuilder.setMessage("Bạn có muốn thoát không?");
        alertDialogBuilder.setCancelable (false);
        alertDialogBuilder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface argo, int argl) {
                System.exit(1);
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
