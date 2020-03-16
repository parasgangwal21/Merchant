package com.avenues.oman_sdk;

import android.content.Context;
import android.content.Intent;

public final class DhofarApplication {
    private static DhofarApplication dofar_instance = null;


    private DhofarApplication() {

    }

    public static synchronized DhofarApplication getCCInstance() {
        if (dofar_instance == null)
            dofar_instance = new DhofarApplication();
        return dofar_instance;
    }


    public static void startTransaction(Context context, orderDetails orderDetails) {
        try {

            Intent i = new Intent(context, DhofarHomeActivity.class);
            i.putExtra("input", orderDetails);
            context.startActivity(i);

        } catch (Exception e) {
            getCCInstance().clearInstance();
            return;
        }
    }


    protected synchronized void clearInstance() {
        dofar_instance = null;

    }
}
