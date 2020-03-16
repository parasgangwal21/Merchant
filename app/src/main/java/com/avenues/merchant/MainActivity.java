package com.avenues.merchant;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.avenues.oman_sdk.DhofarApplication;
import com.avenues.oman_sdk.callBack;
import com.avenues.oman_sdk.orderDetails;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements callBack.stateListener {
    Context mContext;
    @BindView(R.id.button)
    Button btnPay;
    @BindView(R.id.constraintLayout2)
    ConstraintLayout constraintLayout2;
    @BindView(R.id.txt_reg_id)
    TextView txtRegId;
    @BindView(R.id.edt_reg_id)
    EditText edtRegId;
    @BindView(R.id.txt_access_code)
    TextView txtAccessCode;
    @BindView(R.id.edt_access_code)
    EditText edtAccessCode;
    @BindView(R.id.txt_order_id)
    TextView txtOrderId;
    @BindView(R.id.edt_order_id)
    EditText edtOrderId;
    @BindView(R.id.txt_currency)
    TextView txtCurrency;
    @BindView(R.id.edt_currency)
    EditText edtCurrency;
    @BindView(R.id.txt_amount)
    TextView txtAmount;
    @BindView(R.id.edt_amount)
    EditText edtAmount;
    @BindView(R.id.txt_redirect_url)
    TextView txtRedirectUrl;
    @BindView(R.id.edt_redirect_url)
    EditText edtRedirectUrl;
    @BindView(R.id.txt_cancel_url)
    TextView txtCancelUrl;
    @BindView(R.id.edt_cancel_url)
    EditText edtCancelUrl;
    @BindView(R.id.txt_mer_param_1)
    TextView txtMerParam1;
    @BindView(R.id.edt_mer_param_1)
    EditText edtMerParam1;
    @BindView(R.id.txt_mer_param_2)
    TextView txtMerParam2;
    @BindView(R.id.edt_mer_param_2)
    EditText edtMerParam2;
    @BindView(R.id.txt_mer_param_3)
    TextView txtMerParam3;
    @BindView(R.id.edt_mer_param_3)
    EditText edtMerParam3;
    @BindView(R.id.txt_mer_param_4)
    TextView txtMerParam4;
    @BindView(R.id.edt_mer_param_4)
    EditText edtMerParam4;
    @BindView(R.id.txt_mer_param_5)
    TextView txtMerParam5;
    @BindView(R.id.edt_mer_param_5)
    EditText edtMerParam5;
    @BindView(R.id.constraintLayout)
    ConstraintLayout constraintLayout;
    @BindView(R.id.txt_customer_id)
    TextView txtCustomerId;
    @BindView(R.id.edt_customer_id)
    EditText edtCustomerId;
    @BindView(R.id.txt_theme_color)
    TextView txtThemeColor;
    @BindView(R.id.edt_theme_color)
    EditText edtThemeColor;
    @BindView(R.id.txt_merchant_logo)
    TextView txtMerchantLogo;
    @BindView(R.id.edt_merchant_logo)
    EditText edtMerchantLogo;

    @BindView(R.id.edt_merchant_env)
    EditText edtMerchantEnv;
    ProgressDialog progress;
    @BindView(R.id.txt_merchant_env)
    TextView txtMerchantEnv;
    @BindView(R.id.txt_name_on_card)
    TextView txtNameOnCard;
    @BindView(R.id.edt_name_on_card)
    EditText edtNameOnCard;
    @BindView(R.id.txt_card_number)
    TextView txtCardNumber;
    @BindView(R.id.edt_card_number)
    EditText edtCardNumber;
    @BindView(R.id.txt_card_expiry)
    TextView txtCardExpiry;
    @BindView(R.id.edt_card_expiry)
    EditText edtCardExpiry;
    @BindView(R.id.txt_card_cvv)
    TextView txtCardCvv;
    @BindView(R.id.edt_card_cvv)
    EditText edtCardCvv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.txt_merchant_type)
    TextView txtMerchantType;
    @BindView(R.id.spinner_merchant_type)
    Spinner spinnerMerchantType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mContext = MainActivity.this;
        callBack.getInstance().setListener(this);


        spinnerMerchantType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {

                    Integer randomNum = new Random().nextInt((9999999) + 1);
                    edtRegId.setText("2");
                    edtAccessCode.setText("AVYX26EA85AL93XYLA");
                    edtOrderId.setText("ORD" + randomNum);
                    edtCurrency.setText("OMR");
                    edtAmount.setText("23.00");
                    edtRedirectUrl.setText("http://127.0.0.1/responseHandler");
                    edtCancelUrl.setText("http://127.0.0.1/cancelTxnHandler");
                    edtMerParam1.setText("First UDF");
                    edtMerParam2.setText("Second UDF");
                    edtMerParam3.setText("Third UDF");
                    edtMerParam4.setText("Fourth UDF");
                    edtMerParam5.setText("Fifth UDF");
                    edtCustomerId.setText("TEST0002");
                    edtThemeColor.setText("#123456");
                    edtMerchantLogo.setText("https://s.yimg.com/rz/p/yahoo_frontpage_en-US_s_f_p_205x58_frontpage_2x.png");
                    edtMerchantEnv.setText("app_staging");


                    txtCustomerId.setVisibility(View.VISIBLE);
                    edtCustomerId.setVisibility(View.VISIBLE);


                    txtNameOnCard.setVisibility(View.GONE);
                    edtNameOnCard.setVisibility(View.GONE);


                    txtCardNumber.setVisibility(View.GONE);
                    edtCardNumber.setVisibility(View.GONE);


                    txtCardExpiry.setVisibility(View.GONE);
                    edtCardExpiry.setVisibility(View.GONE);

                    txtCardCvv.setVisibility(View.GONE);
                    edtCardCvv.setVisibility(View.GONE);
                } else if (position == 1) {

                    Integer randomNum = new Random().nextInt((9999999) + 1);
                    edtRegId.setText("4");
                    edtAccessCode.setText("BDOK14FQ99KV24UQDL");
                    edtOrderId.setText("ORD" + randomNum);
                    edtCurrency.setText("OMR");
                    edtAmount.setText("23.00");
                    edtRedirectUrl.setText("http://127.0.0.1/responseHandler");
                    edtCancelUrl.setText("http://127.0.0.1/cancelTxnHandler");
                    edtMerParam1.setText("First UDF");
                    edtMerParam2.setText("Second UDF");
                    edtMerParam3.setText("Third UDF");
                    edtMerParam4.setText("Fourth UDF");
                    edtMerParam5.setText("Fifth UDF");


                    edtThemeColor.setText("#53AA00");
                    edtMerchantLogo.setText("https://s.yimg.com/rz/p/yahoo_frontpage_en-US_s_f_p_205x58_frontpage_2x.png");
                    edtMerchantEnv.setText("app_staging");
                    edtNameOnCard.setText("Test");
                    edtCardNumber.setText("4641570031333544");
                    edtCardExpiry.setText("10/23");
                    edtCardCvv.setText("311");

                    txtCustomerId.setVisibility(View.GONE);
                    edtCustomerId.setVisibility(View.GONE);


                    txtNameOnCard.setVisibility(View.VISIBLE);
                    edtNameOnCard.setVisibility(View.VISIBLE);


                    txtCardNumber.setVisibility(View.VISIBLE);
                    edtCardNumber.setVisibility(View.VISIBLE);


                    txtCardExpiry.setVisibility(View.VISIBLE);
                    edtCardExpiry.setVisibility(View.VISIBLE);

                    txtCardCvv.setVisibility(View.VISIBLE);
                    edtCardCvv.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


    @Override
    public void onSuccess(String response) {

        Intent i = new Intent(mContext, StatusActivity.class);
        i.putExtra("status", "success");
        i.putExtra("response", response);
        startActivity(i);
    }

    @Override
    public void onFailure(String statusCode, String statusMessage) {
        //String modelState = callBack.getInstance().getResponse();
        Intent i = new Intent(mContext, StatusActivity.class);
        i.putExtra("status", "failure");
        i.putExtra("statusCode", statusCode);
        i.putExtra("statusMessage", statusMessage);
        startActivity(i);
    }

    public void encrypt() {

        RestClient.GitApiInterface service = RestClient.getGitApiInterface();
        LoadingDialog.showLoadingDialog(mContext, "Loading");
        Call<String> call;
        if (spinnerMerchantType.getSelectedItemPosition() == 0) {
            call = service.encrypt_NS("http://192.168.3.141/ShoppingCarts_Kits/PHPKits/mobileutility/encrypt.php", edtRegId.getText().toString(), edtAccessCode.getText().toString(), edtOrderId.getText().toString(), edtCurrency.getText().toString(),edtAmount.getText().toString(), edtRedirectUrl.getText().toString(), edtCancelUrl.getText().toString(), edtMerParam1.getText().toString(), edtMerParam2.getText().toString(), edtMerParam3.getText().toString(), edtMerParam4.getText().toString(), edtMerParam5.getText().toString(), edtCustomerId.getText().toString());
        } else {
            call = service.encrypt_S("http://192.168.3.141/ShoppingCarts_Kits/PHPKits/mobileutility/encrypt.php", edtRegId.getText().toString(), edtAccessCode.getText().toString(), edtOrderId.getText().toString(), edtCurrency.getText().toString(), edtAmount.getText().toString(), edtRedirectUrl.getText().toString(), edtCancelUrl.getText().toString(), edtMerParam1.getText().toString(), edtMerParam2.getText().toString(), edtMerParam3.getText().toString(), edtMerParam4.getText().toString(), edtMerParam5.getText().toString(), edtNameOnCard.getText().toString(), edtCardNumber.getText().toString(), edtCardExpiry.getText().toString(), edtCardCvv.getText().toString());
        }

        //Call<String> call = service.encrypt_S("http://192.168.3.141/ShoppingCarts_Kits/PHPKits/mobileutility/encrypt.php", edtRegId.getText().toString(), edtAccessCode.getText().toString(), edtOrderId.getText().toString(), edtCurrency.getText().toString(), edtAmount.getText().toString(), edtRedirectUrl.getText().toString(), edtCancelUrl.getText().toString(), edtMerParam1.getText().toString(), edtMerParam2.getText().toString(), edtMerParam3.getText().toString(), edtMerParam4.getText().toString(), edtMerParam5.getText().toString(), edtCustomerId.getText().toString(),edtNameOnCard.getText().toString(),edtCardNumber.getText().toString(),edtCardExpiry.getText().toString(),edtCardCvv.getText().toString());
        //Call<String> call = service.encrypt_S("http://192.168.3.141/ShoppingCarts_Kits/PHPKits/mobileutility/encrypt.php", edtRegId.getText().toString(), edtAccessCode.getText().toString(), edtOrderId.getText().toString(), edtCurrency.getText().toString(), df.format(d), edtRedirectUrl.getText().toString(), edtCancelUrl.getText().toString(), edtMerParam1.getText().toString(), edtMerParam2.getText().toString(), edtMerParam3.getText().toString(), edtMerParam4.getText().toString(), edtMerParam5.getText().toString(), edtNameOnCard.getText().toString(),edtCardNumber.getText().toString(),edtCardExpiry.getText().toString(),edtCardCvv.getText().toString());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                //  LoadingDialog.cancelLoading();
                try {
                    if (response.body() != null) {

                        createOrder(response.body().replaceAll("\\n", ""));


                    } else {
                        Log.d("nullresp", response.body());
                        int a = 1;
                        a = a + 2;
                        LoadingDialog.cancelLoading();
                        LoadingDialog.showmsg(mContext, "Error!!! Please Try again...");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    LoadingDialog.cancelLoading();
                    LoadingDialog.showmsg(mContext, "Error!!! Please Try again...");

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                LoadingDialog.cancelLoading();
                LoadingDialog.showmsg(mContext, "Error!!! Please Try again...");
                t.printStackTrace();
            }
        });
    }


    public void createOrder(String enc_request) {
        RestClient.GitApiInterface service = RestClient.getGitApiInterface();
        //LoadingDialog.showLoadingDialog(mContext, "Loading");
        try {
            JSONObject j_req = new JSONObject();
            j_req.put("access_code", edtAccessCode.getText().toString());
            j_req.put("enc_request", enc_request);


            Call<String> call = service.createOrder(j_req.toString());
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    // LoadingDialog.cancelLoading();
                    try {
                        if (response.body() != null) {
                            JSONObject jo = new JSONObject(response.body());


                            if (jo.getString("msg_desc").equalsIgnoreCase("0")) {
                                String resp = jo.getString("enc_response");
                                decrypt(resp);
                            } else {
                                LoadingDialog.cancelLoading();
                                LoadingDialog.showmsg(mContext, jo.getString("msg_status"));
                            }

                        } else {
                            Log.d("nullresp", response.body());
                            LoadingDialog.cancelLoading();
                            LoadingDialog.showmsg(mContext, "Error!!! Please Try again...");
                        }
                    } catch (Exception e) {
                        LoadingDialog.cancelLoading();
                        LoadingDialog.showmsg(mContext, "Error!!! Please Try again...");

                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    LoadingDialog.cancelLoading();
                    LoadingDialog.showmsg(mContext, "Error!!! Please Try again...");
                    t.printStackTrace();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void decrypt(String enc_request) {
        RestClient.GitApiInterface service = RestClient.getGitApiInterface();


        Call<String> call = service.decrypt("http://192.168.3.141/ShoppingCarts_Kits/PHPKits/mobileutility/decrypt.php", enc_request,edtRegId.getText().toString(), edtAccessCode.getText().toString());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                LoadingDialog.cancelLoading();
                try {
                    if (response.body() != null) {
                        JSONObject resp = new JSONObject();
                        if (response.body().contains("&")) {
                            String[] data = response.body().replaceAll("\n", "").split("&");

                            for (int i = 0; i < data.length; i++) {
                                if (data[i].contains("=")) {
                                    String[] val = data[i].split("=");
                                    if(val.length==2)
                                    resp.put(val[0], val[1]);
                                }
                            }

                            orderDetails order = new orderDetails();

                            order.setTracking_id(resp.getString("tracking_id"));
                            order.setOrder_id(resp.getString("order_id"));
                            order.setCurrency(resp.getString("currency"));
                            order.setAmount(resp.getString("amount"));
                            order.setCustomer_id(edtCustomerId.getText().toString());
                            order.setTheme_color(edtThemeColor.getText().toString());
                            order.setMer_logo_url(edtMerchantLogo.getText().toString());
                            order.setMer_environment(edtMerchantEnv.getText().toString());
                            DhofarApplication.startTransaction(mContext, order);
                        } else {
                            LoadingDialog.cancelLoading();
                            LoadingDialog.showmsg(mContext, "Error!!! Please Try again...");

                        }


                    } else {
                        Log.d("nullresp", response.body());
                        LoadingDialog.cancelLoading();
                        LoadingDialog.showmsg(mContext, "Error!!! Please Try again...");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    LoadingDialog.cancelLoading();
                    LoadingDialog.showmsg(mContext, "Error!!! Please Try again...");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                LoadingDialog.cancelLoading();
                LoadingDialog.showmsg(mContext, "Error!!! Please Try again...");
                t.printStackTrace();
            }
        });
    }

    @OnClick(R.id.button)
    public void onViewClicked() {
        encrypt();

    }
}
