package com.avenues.oman_sdk;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;

import org.json.JSONObject;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class DhofarCreditCardEditText extends AppCompatEditText {

    private SparseArray<Pattern> mCCPatterns = null;
    boolean flg = false;
    String type = "";
    String bin = "";
    DhofarCardNo cc;
    private int mCurrentDrawableResId = 0;
    private Drawable mCurrentDrawable;
    Context ctx;
    Call<String> call;
    ProgressDialog progress;

    public DhofarCreditCardEditText(Context context) {
        super(context);
        ctx = context;
        init();
    }

    public DhofarCreditCardEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        ctx = context;
        init();
    }

    public DhofarCreditCardEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ctx = context;
        init();
    }

    private void init() {



        if (mCCPatterns == null) {
            cc = (DhofarCardNo) ctx;
            mCCPatterns = new SparseArray<>();
            mCurrentDrawableResId = R.drawable.unknown_cc;
            mCurrentDrawable = getResources().getDrawable(mCurrentDrawableResId);




        }
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {

        if (mCCPatterns == null) {
            init();
        }

        if (text.length() == 16) {

            if (type.equalsIgnoreCase("Success")&&bin.equalsIgnoreCase(text.toString().substring(0,6))) {
                if (!Check(text.toString())) {
                    cc.error(true, "Enter valid domestic debit card");

                } else {
                    cc.error(false, "");

                }

            } else if (type.equalsIgnoreCase("Error")) {


            } else {

                if (!Check(text.toString())) {
                    cc.error(true, "Enter valid domestic debit card");

                } else {
                    identifyCard1(text.toString().substring(0,6));

                }

            }

        }


        if (text.length() == 6) {
            if (!flg) {
                flg = true;
                identifyCard(text.toString());
            }
        }

        else if (text.length() < 6) {
            if (call != null)
                call.cancel();
            flg = false;
            if (text.length() > 0)
                cc.error(false, "");
            mCurrentDrawableResId = R.drawable.unknown_cc;
            mCurrentDrawable = getResources().getDrawable(mCurrentDrawableResId);
        }



    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mCurrentDrawable == null) {
            return;
        }

        int rightOffset = 0;
        if (getError() != null && getError().length() > 0) {
            rightOffset = (int) getResources().getDisplayMetrics().density * 32;
        }

        int right = getWidth() - getPaddingRight() - rightOffset;

        int top = getPaddingTop();
        int bottom = getHeight() - getPaddingBottom();
        float ratio = (float) mCurrentDrawable.getIntrinsicWidth() / (float) mCurrentDrawable.getIntrinsicHeight();
        //int left = right - mCurrentDrawable.getIntrinsicWidth(); //If images are correct size.
        int left = (int) (right - ((bottom - top) * ratio)); //scale image depeding on height available.
        mCurrentDrawable.setBounds(left, top, right, bottom);

        mCurrentDrawable.draw(canvas);


    }

    public static boolean Check(String ccNumber) {
        int sum = 0;
        boolean alternate = false;
        for (int i = ccNumber.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(ccNumber.substring(i, i + 1));
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        return (sum % 10 == 0);
    }


    public void identifyCard(final String cardno) {
        DhofarRestClient.GitApiInterface service = DhofarRestClient.getGitApiInterface();
        //LoadingDialog.showLoadingDialog(mContext, "Loading");
        try {

            call = service.identifyCard(DhofarHomeActivity.resp.getTracking_id(), DhofarHomeActivity.resp.getOrder_id(), cardno);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    flg = false;
                    // LoadingDialog.cancelLoading();
                    try {
                        if (response.body() != null) {

                            JSONObject jo = new JSONObject(response.body());
                            String statusCode = jo.getString("statusCode");
                            if (statusCode.equalsIgnoreCase("0")) {
                                switch (jo.getString("cardName")) {
                                    case "Visa":
                                        mCurrentDrawableResId = R.drawable.cc_avenues_visa;
                                        mCurrentDrawable = getResources().getDrawable(mCurrentDrawableResId);
                                        bin=cardno;
                                        type = "Success";
                                        cc.cardType(jo.getString("cardName"));
                                        cc.error(false, "");
                                        break;

                                    case "MasterCard":
                                        mCurrentDrawableResId = R.drawable.cc_avenues_master;
                                        mCurrentDrawable = getResources().getDrawable(mCurrentDrawableResId);
                                        cc.cardType(jo.getString("cardName"));
                                        bin=cardno;
                                        type = "Success";
                                        cc.error(false, "");

                                        break;
                                    case "Amex":
                                        mCurrentDrawableResId = R.drawable.cc_avenues_amex;
                                        mCurrentDrawable = getResources().getDrawable(mCurrentDrawableResId);
                                        cc.cardType(jo.getString("cardName"));
                                        bin=cardno;
                                        type = "Success";
                                        cc.error(false, "");
                                        break;
                                    case "Discover":
                                        mCurrentDrawableResId = R.drawable.cc_avenues_discover;
                                        mCurrentDrawable = getResources().getDrawable(mCurrentDrawableResId);
                                        cc.cardType(jo.getString("cardName"));
                                        bin=cardno;
                                        type = "Success";
                                        cc.error(false, "");
                                        break;
                                    case "Diners":
                                        mCurrentDrawableResId = R.drawable.cc_avenues_dinersclub;
                                        mCurrentDrawable = getResources().getDrawable(mCurrentDrawableResId);
                                        cc.cardType(jo.getString("cardName"));
                                        bin=cardno;
                                        type = "Success";
                                        cc.error(false, "");
                                        break;
                                    case "JCB":
                                        mCurrentDrawableResId = R.drawable.cc_avenues_jcb;
                                        mCurrentDrawable = getResources().getDrawable(mCurrentDrawableResId);
                                        cc.cardType(jo.getString("cardName"));
                                        bin=cardno;
                                        type = "Success";
                                        cc.error(false, "");
                                        break;
                                    case "Maestro":
                                        mCurrentDrawableResId = R.drawable.cc_avenues_maestro;
                                        mCurrentDrawable = getResources().getDrawable(mCurrentDrawableResId);
                                        cc.cardType(jo.getString("cardName"));
                                        bin=cardno;
                                        type = "Success";
                                        cc.error(false, "");
                                        break;
                                    case "RuPay":
                                        mCurrentDrawableResId = R.drawable.cc_avenues_rupay;
                                        mCurrentDrawable = getResources().getDrawable(mCurrentDrawableResId);
                                        cc.cardType(jo.getString("cardName"));
                                        bin=cardno;
                                        type = "Success";
                                        cc.error(false, "");
                                        break;
                                }
                            } else {
                                bin="";
                                cc.cardType("");
                                type = jo.getString("statusMessage");
                                cc.error(true, "Enter valid domestic debit card");


                            }


                        } else {
                            bin="";
                            Log.d("nullresp", response.body());
                            type = "Error";
                            cc.cardType("Error");
                           /* LoadingDialog.cancelLoading();
                            LoadingDialog.showmsg(ctx, "Error!!! Please Try again...");*/
                        }
                    } catch (Exception e) {
                        bin="";
                        type = "Error";
                        cc.cardType("Error");
                       /* LoadingDialog.cancelLoading();
                        LoadingDialog.showmsg(ctx, "Error!!! Please Try again...");*/

                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    bin="";
                    flg = false;
                    type = "Error";
                    cc.cardType("Error");
                    t.printStackTrace();
                }
            });

        } catch (
                Exception e) {
            e.printStackTrace();
        }
    }


    public void identifyCard1(final String cardno) {
        progress = new ProgressDialog(ctx);
        progress.setMessage("Please Wait...");
        progress.setCanceledOnTouchOutside(false);
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        DhofarRestClient.GitApiInterface service = DhofarRestClient.getGitApiInterface();
        show();
        try {

            call = service.identifyCard(DhofarHomeActivity.resp.getTracking_id(), DhofarHomeActivity.resp.getOrder_id(), cardno);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    flg = false;
                    hide();
                    try {
                        if (response.body() != null) {

                            JSONObject jo = new JSONObject(response.body());
                            String statusCode = jo.getString("statusCode");
                            if (statusCode.equalsIgnoreCase("0")) {
                                switch (jo.getString("cardName")) {
                                    case "Visa":
                                        mCurrentDrawableResId = R.drawable.cc_avenues_visa;
                                        mCurrentDrawable = getResources().getDrawable(mCurrentDrawableResId);
                                        type = "Success";
                                        bin=cardno;
                                        cc.cardType(jo.getString("cardName"));
                                        cc.error(false, "");
                                        break;

                                    case "MasterCard":
                                        mCurrentDrawableResId = R.drawable.cc_avenues_master;
                                        mCurrentDrawable = getResources().getDrawable(mCurrentDrawableResId);
                                        cc.cardType(jo.getString("cardName"));
                                        bin=cardno;
                                        type = "Success";
                                        cc.error(false, "");

                                        break;
                                    case "Amex":
                                        mCurrentDrawableResId = R.drawable.cc_avenues_amex;
                                        mCurrentDrawable = getResources().getDrawable(mCurrentDrawableResId);
                                        cc.cardType(jo.getString("cardName"));
                                        type = "Success";
                                        bin=cardno;
                                        cc.error(false, "");
                                        break;
                                    case "Discover":
                                        mCurrentDrawableResId = R.drawable.cc_avenues_discover;
                                        mCurrentDrawable = getResources().getDrawable(mCurrentDrawableResId);
                                        cc.cardType(jo.getString("cardName"));
                                        bin=cardno;
                                        type = "Success";
                                        cc.error(false, "");
                                        break;
                                    case "Diners":
                                        mCurrentDrawableResId = R.drawable.cc_avenues_dinersclub;
                                        mCurrentDrawable = getResources().getDrawable(mCurrentDrawableResId);
                                        cc.cardType(jo.getString("cardName"));
                                        bin=cardno;
                                        type = "Success";
                                        cc.error(false, "");
                                        break;
                                    case "JCB":
                                        mCurrentDrawableResId = R.drawable.cc_avenues_jcb;
                                        mCurrentDrawable = getResources().getDrawable(mCurrentDrawableResId);
                                        cc.cardType(jo.getString("cardName"));
                                        bin=cardno;
                                        type = "Success";
                                        cc.error(false, "");
                                        break;
                                    case "Maestro":
                                        mCurrentDrawableResId = R.drawable.cc_avenues_maestro;
                                        mCurrentDrawable = getResources().getDrawable(mCurrentDrawableResId);
                                        cc.cardType(jo.getString("cardName"));
                                        bin=cardno;
                                        type = "Success";
                                        cc.error(false, "");
                                        break;
                                    case "RuPay":
                                        mCurrentDrawableResId = R.drawable.cc_avenues_rupay;
                                        mCurrentDrawable = getResources().getDrawable(mCurrentDrawableResId);
                                        cc.cardType(jo.getString("cardName"));
                                        bin=cardno;
                                        type = "Success";
                                        cc.error(false, "");
                                        break;
                                }
                            } else {
                                bin="";
                                cc.cardType("");
                                type = jo.getString("statusMessage");
                                cc.error(true, "Enter valid domestic debit card");


                            }


                        } else {
                            bin="";
                            Log.d("nullresp", response.body());
                            type = "Error";
                            cc.cardType("Error");
                           /* LoadingDialog.cancelLoading();
                            LoadingDialog.showmsg(ctx, "Error!!! Please Try again...");*/
                        }
                    } catch (Exception e) {
                        hide();
                        bin="";
                        type = "Error";
                        cc.cardType("Error");
                       /* LoadingDialog.cancelLoading();
                        LoadingDialog.showmsg(ctx, "Error!!! Please Try again...");*/

                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    hide();
                    bin="";
                    flg = false;
                    type = "Error";
                    cc.cardType("Error");
                    t.printStackTrace();
                }
            });

        } catch (
                Exception e) {
            e.printStackTrace();
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