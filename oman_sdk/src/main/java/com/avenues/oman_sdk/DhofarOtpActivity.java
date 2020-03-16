package com.avenues.oman_sdk;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DhofarOtpActivity extends AppCompatActivity {
    orderDetails resp;
    TextView txt_amount, txt_privacy;
    TextView txt_order_id;
    //TextView txt_counter;
    EditText otp1, otp2, otp3, otp4;
    Call<String> call;
    Context ctx;
    String theme = "#53AA00";
    String logo_url = "";
    Button btnVerify;
    ConstraintLayout constraintLayout;
    ImageView img_merchantLogo;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dhofar_otp);

        ctx = DhofarOtpActivity.this;
        progress = new ProgressDialog(this);
        progress.setMessage("Please Wait...");
        progress.setCanceledOnTouchOutside(false);
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        txt_amount = (TextView) findViewById(R.id.txt_amount);
        txt_order_id = (TextView) findViewById(R.id.txt_order_id);
        //txt_counter = (TextView) findViewById(R.id.txt_counter);
        otp1 = (EditText) findViewById(R.id.edt_cvv);
        otp2 = (EditText) findViewById(R.id.otp2);
        otp3 = (EditText) findViewById(R.id.otp3);
        otp4 = (EditText) findViewById(R.id.otp4);
        txt_privacy = (TextView) findViewById(R.id.txt_privacy);
        btnVerify = (Button) findViewById(R.id.btn_verify);
        constraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayout);
        img_merchantLogo = (ImageView) findViewById(R.id.merchant_logo);
        otp1.requestFocus();

        try {
            resp = getIntent().getParcelableExtra("input");


            if (resp.getTheme_color() == null) {

            } else {
                theme = resp.getTheme_color();
            }


            if (resp.getMer_logo_url() == null) {

            } else {
                logo_url = resp.getMer_logo_url();
            }
            txt_order_id.setText(resp.getOrder_id());
            txt_amount.setText(resp.getCurrency() + " " + resp.getAmount());


            if (resp.getMer_logo_url().length() > 2)
                Picasso.get().load(logo_url).into(img_merchantLogo);
            else
                Picasso.get().load(R.drawable.company_logo).into(img_merchantLogo);

        } catch (Exception e) {
            callBack.getInstance().onFailure("100", "Error!!! Please Try again...");
            finish();
        }

/*
        new CountDownTimer(3 * 60 * 1000, 1000) { // adjust the milli seconds here

            public void onTick(long millisUntilFinished) {
                txt_counter.setText("Transaction will timeout in " + String.format("%d:%d minutes",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            public void onFinish() {
                txt_counter.setText("done!");
            }
        }.start();*/


        otp1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    s.append(s);
                    otp1.clearFocus();
                    otp2.requestFocus();
                    otp2.setCursorVisible(true);

                }
            }
        });


        otp2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    s.append(s);
                    otp2.clearFocus();
                    otp3.requestFocus();
                    otp3.setCursorVisible(true);

                }
            }
        });

        otp3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                int a = 1;
                a = a + 2;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int a = 1;
                a = a + 2;
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    s.append(s);
                    otp3.clearFocus();
                    otp4.requestFocus();
                    otp4.setCursorVisible(true);

                }
            }
        });

        otp4.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    if (otp4.getText().toString().length() == 0) {
                        otp4.clearFocus();
                        otp3.requestFocus();
                        otp3.setCursorVisible(true);
                    }
                }
                return false;
            }
        });

        otp3.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    if (otp3.getText().toString().length() == 0) {
                        otp3.clearFocus();
                        otp2.requestFocus();
                        otp2.setCursorVisible(true);
                    }
                }
                return false;
            }
        });

        otp2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    if (otp2.getText().toString().length() == 0) {
                        otp2.clearFocus();
                        otp1.requestFocus();
                        otp1.setCursorVisible(true);
                    }
                }
                return false;
            }
        });


        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processTransaction();
            }
        });
        txt_privacy.setTextColor(Color.parseColor(theme));
        btnVerify.setTextColor(Color.parseColor(theme));
        constraintLayout.setBackgroundColor(Color.parseColor(theme));
        GradientDrawable bgShape = (GradientDrawable) btnVerify.getBackground();
        bgShape.setStroke(4, Color.parseColor(theme));

    }

    @Override
    public void onBackPressed() {

    }

    public void processTransaction() {

        String otp = otp1.getText().toString() + otp2.getText().toString() + otp3.getText().toString() + otp4.getText().toString();

        if (otp.length() == 4) {
            DhofarRestClient.GitApiInterface service = DhofarRestClient.getGitApiInterface();
            show();
            try {

                call = service.processTransaction(resp.getTracking_id(), resp.getOrder_id(), otp);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        hide();
                        try {
                            if (response.body() != null) {
                                JSONObject jo = new JSONObject(response.body());
                                String accessCode = jo.getString("accessCode");
                                String encResponse = jo.getString("encResponse");

                                callBack.getInstance().onSuccess(encResponse);
                                finish();

                            } else {
                                Log.d("nullresp", response.body());
                                hide();
                                callBack.getInstance().onFailure("100", "Error!!! Please Try again...");
                                finish();
                            }
                        } catch (Exception e) {
                            hide();
                            callBack.getInstance().onFailure("100", "Error!!! Please Try again...");
                            finish();

                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        hide();
                        callBack.getInstance().onFailure("100", "Error!!! Please Try again...");
                        finish();
                    }
                });

            } catch (
                    Exception e) {
                callBack.getInstance().onFailure("100", "Error!!! Please Try again...");
                finish();
            }
        } else {
            Toast.makeText(ctx, "Please Enter OTP...", Toast.LENGTH_LONG).show();
        }

    }

    public void show() {


        if (progress != null && !progress.isShowing())
            progress.show();


    }

    public void hide() {


        if (progress != null && progress.isShowing())
            progress.dismiss();


    }


}
