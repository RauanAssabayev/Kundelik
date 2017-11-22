package kz.edu.sdu.rauanassabayev.kundelik.Helpers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;


/**
 * Created by rauanassabayev on 11/3/17.
 */

public class Message {
    private final Context context;
    public Message(Context con) {
        this.context=con;
    }
    public void alertbox() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Blah")
                .setCancelable(false)
                .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}