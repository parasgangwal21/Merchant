package com.avenues.merchant;

import android.app.ProgressDialog;
import android.content.Context;
import android.text.Html;
import android.view.Gravity;
import android.widget.Toast;


public class LoadingDialog {

    static ProgressDialog progressDialog;


    public static void showLoadingDialog(Context context, String message) {

        if (!(progressDialog != null && progressDialog.isShowing())) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage(message);

            progressDialog.setCancelable(true);
            progressDialog.setCanceledOnTouchOutside(false);

            progressDialog.show();
        }

    }

    public static void cancelLoading() {
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.cancel();

    }
    public static void showmsg(Context context,String ttt)
    {
        Toast toast = Toast.makeText(context, Html.fromHtml("<font color='#FF0000' ><b>" + ttt + "</b></font>"), Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();

    }



}
