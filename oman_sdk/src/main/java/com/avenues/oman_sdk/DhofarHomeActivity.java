package com.avenues.oman_sdk;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.TintableCompoundButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DhofarHomeActivity extends AppCompatActivity implements DhofarCardNo {


    TextView txt_amount, txt_privacy, txt_addCard, txt_saved_card;
    TextView txt_order_id;
    TextInputLayout input_card_no, input_expiry, input_name, input_cvv;
    EditText edtExpiry, edt_name, edt_cvv;
    Button btnPay;
    static orderDetails resp;
    DhofarCreditCardEditText creditCardEditText;
    Call<String> call;
    Context ctx;
    String card_type = "";
    ArrayList<DhofarCard> cards = new ArrayList<>();
    RecyclerView recyclerList;
    String saved_cvv = "", token = "";
    ConstraintLayout cc_newCard, cc_savedCard, constraintLayout;
    String theme = "#53AA00";
    String logo_url = "";
    ImageView img_merchantLogo;
    CheckBox chk;
    ColorStateList colorStateList;
    ProgressDialog progress;
    LinearLayout ll_input_chk;
    String check = "N";
    String mer_type = "";
    NestedScrollView scroll;
    savedCardAdapter savedCardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dhofar_home);

        ctx = DhofarHomeActivity.this;
        txt_amount = (TextView) findViewById(R.id.txt_amount);
        txt_privacy = (TextView) findViewById(R.id.txt_privacy);
        txt_addCard = (TextView) findViewById(R.id.txt_addCard);
        txt_order_id = (TextView) findViewById(R.id.txt_order_id);
        txt_saved_card = (TextView) findViewById(R.id.txt_saved_card);
        input_card_no = (TextInputLayout) findViewById(R.id.input_card_no);
        input_expiry = (TextInputLayout) findViewById(R.id.input_expiry);
        input_name = (TextInputLayout) findViewById(R.id.input_name);
        input_cvv = (TextInputLayout) findViewById(R.id.input_cvv);
        btnPay = (Button) findViewById(R.id.btn_pay);
        creditCardEditText = (DhofarCreditCardEditText) findViewById(R.id.edt_card_no);
        edt_name = (EditText) findViewById(R.id.edt_name);
        edtExpiry = (EditText) findViewById(R.id.edt_expiry);
        edt_cvv = (EditText) findViewById(R.id.edt_cvv);
        scroll = (NestedScrollView) findViewById(R.id.scroll);
        chk = (CheckBox) findViewById(R.id.chk);

        img_merchantLogo = (ImageView) findViewById(R.id.merchant_logo);
        cc_newCard = (ConstraintLayout) findViewById(R.id.cc_newCard);
        cc_savedCard = (ConstraintLayout) findViewById(R.id.cc_savedCard);
        constraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayout);
        ll_input_chk = (LinearLayout) findViewById(R.id.ll_input_chk);


        recyclerList = (RecyclerView) findViewById(R.id.recycler_list);
        recyclerList.setHasFixedSize(true);
        recyclerList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        edtExpiry.addTextChangedListener(new TwoDigitsCardTextWatcher(edtExpiry));

        progress = new ProgressDialog(this);
        progress.setMessage("Please Wait...");
        progress.setCanceledOnTouchOutside(false);
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        savedCardAdapter = new savedCardAdapter(ctx);
        try {
            resp = getIntent().getParcelableExtra("input");


            if (resp.getMer_environment() == null) {

                callBack.getInstance().onFailure("201", "Invalid Payment environment");
                finish();
            } else if (resp.getOrder_id() == null) {

                callBack.getInstance().onFailure("202", "Invalid orderId");
                finish();
            } else if (resp.getCurrency() == null) {

                callBack.getInstance().onFailure("203", "Invalid currency");
                finish();
            } else if (resp.getAmount() == null) {

                callBack.getInstance().onFailure("204", "Invalid amount");
                finish();
            } else {

                if (resp.getMer_environment().equalsIgnoreCase("app_staging") || resp.getMer_environment().equalsIgnoreCase("app_live")) {

                    if (resp.getMer_environment().equalsIgnoreCase("app_staging")) {
                        DhofarConstants.url = "https://certsecurepayment.bdpg.bankdhofar.com/wrapper/";
                    } else {
                        DhofarConstants.url = "https://certsecurepayment.bdpg.bankdhofar.com/wrapper/";
                    }

                    logo_url = resp.getMer_logo_url();
                    if (resp.getTheme_color() != null)
                        if (resp.getTheme_color().length() == 7)
                            theme = resp.getTheme_color();

                    colorStateList = new ColorStateList(
                            new int[][]{
                                    new int[]{-android.R.attr.state_checked}, // unchecked
                                    new int[]{android.R.attr.state_checked}  // checked
                            },
                            new int[]{
                                    Color.parseColor("#7A7A7A"),
                                    Color.parseColor(theme)
                            }
                    );


                    txt_order_id.setText(resp.getOrder_id());
                    txt_amount.setText(resp.getCurrency() + " " + resp.getAmount());
                    if (resp.getMer_logo_url() != null) {
                        if (resp.getMer_logo_url().length() > 2)
                            Picasso.get().load(logo_url).into(img_merchantLogo);
                        else
                            Picasso.get().load(R.drawable.company_logo).into(img_merchantLogo);
                    } else
                        Picasso.get().load(R.drawable.company_logo).into(img_merchantLogo);

                    creditCardEditText.setCustomSelectionActionModeCallback(new ActionMode.Callback() {

                        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                            return false;
                        }

                        public void onDestroyActionMode(ActionMode mode) {
                        }

                        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                            return false;
                        }

                        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                            return false;
                        }
                    });

                    creditCardEditText.setLongClickable(false);
                    creditCardEditText.setTextIsSelectable(false);
                    btnPay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (cc_newCard.getVisibility() == View.VISIBLE) {

                                String exp = edtExpiry.getText().toString();
                                Calendar c = Calendar.getInstance();
                                int year = c.get(Calendar.YEAR) % 100;
                                if (!(creditCardEditText.getText().length() == 16)) {
                                    input_card_no.setError("Enter valid domestic debit card");
                                    creditCardEditText.requestFocus();
                                } else if (!(exp.length() == 5) && !exp.contains("/")) {
                                    input_expiry.setError("Enter valid expiry (MM/YY)");
                                    edtExpiry.requestFocus();
                                } else if (exp.split("/").length != 2) {
                                    input_expiry.setError("Enter valid expiry (MM/YY)");
                                    edtExpiry.requestFocus();
                                } else if (!(exp.split("/")[0].length() == 2 && exp.split("/")[1].length() == 2)) {
                                    input_expiry.setError("Enter valid expiry (MM/YY)");
                                    edtExpiry.requestFocus();
                                } else if (!(Integer.parseInt(exp.split("/")[0]) <= 12 && Integer.parseInt(exp.split("/")[0]) > 0)) {
                                    input_expiry.setError("Enter valid expiry (MM/YY)");
                                    edtExpiry.requestFocus();
                                } else if (Integer.parseInt(exp.split("/")[1]) < year) {
                                    input_expiry.setError("Enter valid expiry (MM/YY)");
                                    edtExpiry.requestFocus();
                                } else if (edt_name.getText().toString().length() < 3) {
                                    input_expiry.setError(null);
                                    input_name.setError("Enter valid name");
                                    edt_name.requestFocus();
                                } else if (!(edt_cvv.getText().toString().length() == 3)) {
                                    input_expiry.setError(null);
                                    input_name.setError(null);
                                    input_cvv.setError("Enter CVV");
                                    edt_cvv.requestFocus();
                                } else if (card_type.equalsIgnoreCase("")) {
                                    input_card_no.setError("Enter valid domestic debit card");
                                    creditCardEditText.requestFocus();
                                } else {

                                    input_expiry.setError(null);

                                    if (input_card_no.getError() == null) {

                                        if (input_name.getError() == null)
                                            registerTxn();
                                        else {
                                            input_name.setError("Enter valid name");
                                            edt_name.requestFocus();
                                        }
                                    } else {
                                        input_card_no.setError(input_card_no.getError());
                                        creditCardEditText.requestFocus();
                                    }


                                }
                            } else {
                                if (saved_cvv.length() == 3) {
                                    registerTxn();
                                } else
                                    Toast.makeText(ctx, "Enter CVV", Toast.LENGTH_LONG).show();
                            }

                        }
                    });
                    fetchMerchantType();
                    txt_privacy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.bankdhofar.com/en-GB/Privacy_Policy.aspx"));
                            startActivity(browserIntent);
                        }
                    });


                    txt_saved_card.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            for (int i = 0; i < cards.size(); i++) {
                                cards.get(i).setCvv("");
                            }
                            savedCardAdapter.notifyDataSetChanged();
                            txt_saved_card.setVisibility(View.GONE);
                            cc_newCard.setVisibility(View.GONE);
                            cc_savedCard.setVisibility(View.VISIBLE);
                        }
                    });
                    txt_addCard.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            cc_newCard.setVisibility(View.VISIBLE);
                            cc_savedCard.setVisibility(View.GONE);

                            creditCardEditText.setText("");
                            input_card_no.setError(null);
                            edtExpiry.setText("");
                            input_expiry.setErrorEnabled(false);
                            edt_name.setText("");
                            input_name.setErrorEnabled(false);
                            edt_cvv.setText("");
                            input_cvv.setErrorEnabled(false);
                            if (cards.size() > 0)
                                txt_saved_card.setVisibility(View.VISIBLE);
                        }
                    });


                    txt_addCard.setTextColor(Color.parseColor(theme));
                    txt_privacy.setTextColor(Color.parseColor(theme));
                    btnPay.setTextColor(Color.parseColor(theme));
                    constraintLayout.setBackgroundColor(Color.parseColor(theme));
                    ((TintableCompoundButton) chk).

                            setSupportButtonTintList(colorStateList);

                    GradientDrawable bgShape = (GradientDrawable) btnPay.getBackground();
                    bgShape.setStroke(4, Color.parseColor(theme));
                } else {
                    callBack.getInstance().onFailure("209", "Invalid Payment environment");
                    finish();
                }


            }
        } catch (Exception e) {
            callBack.getInstance().onFailure("100", "Error!!! Please Try again...");
            finish();
        }

        chk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    check = "Y";
                } else {
                    check = "N";
                }
            }
        });

        edt_cvv.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length() == 3)
                    input_cvv.setErrorEnabled(false);

                else if (s.length() > 0) {
                    input_cvv.setErrorEnabled(true);
                    input_cvv.setError("Enter CVV");
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // TODO Auto-generated method stub
            }
        });


        edt_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length() > 3) {

                    if (!(s.toString().matches("[a-zA-Z]+"))) {
                        input_name.setErrorEnabled(true);
                        input_name.setError("Enter valid name");
                    } else
                        input_name.setErrorEnabled(false);
                } else if (s.length() > 0) {
                    input_name.setErrorEnabled(true);
                    input_name.setError("Enter valid name");
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // TODO Auto-generated method stub
            }
        });


    }

    @Override
    public void onBackPressed() {


        new AlertDialog.Builder(ctx)
                .setTitle("Cancel Transaction?")
                .setMessage("Do you want to cancel this transaction !!!")


                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        abortTxn();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })

                .show();

    }

    @Override
    public void error(boolean flg, String error) {
        if (flg)
            input_card_no.setError(error);
        else
            input_card_no.setError(null);
    }

    @Override
    public void cardType(String type) {
        card_type = type;
    }

    public void registerTxn() {
        DhofarRestClient.GitApiInterface service = DhofarRestClient.getGitApiInterface();
        show();
        try {
            if (cc_newCard.getVisibility() == View.VISIBLE) {
                call = service.registerTxn(resp.getTracking_id(), edt_name.getText().toString(), creditCardEditText.getText().toString(), edtExpiry.getText().toString(), edt_cvv.getText().toString(), card_type, resp.getAmount(), resp.getCurrency(), resp.getOrder_id(), "", check);
            } else {
                call = service.registerTxn(resp.getTracking_id(), "", "", "", saved_cvv, card_type, resp.getAmount(), resp.getCurrency(), resp.getOrder_id(), token, check);
            }


            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    hide();
                    try {
                        if (response.body() != null) {
                            JSONObject jo = new JSONObject(response.body());
                            String statusCode = jo.getString("statusCode");
                            if (statusCode.equalsIgnoreCase("0")) {
                                Intent i = new Intent(DhofarHomeActivity.this, DhofarOtpActivity.class);
                                i.putExtra("input", resp);
                                startActivity(i);
                                finish();
                            } else {

                                callBack.getInstance().onFailure(jo.getString("statusCode"), jo.getString("statusMessage"));
                                finish();

                            }


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
    }


    public void abortTxn() {
        DhofarRestClient.GitApiInterface service = DhofarRestClient.getGitApiInterface();
        show();
        try {

            call = service.abortTxn(resp.getTracking_id(), resp.getOrder_id());
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    hide();
                    try {
                        if (response.body() != null) {
                            callBack.getInstance().onFailure("101", "Transaction Aborted by User");
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
    }


    public void fetchSavedCards() {
        DhofarRestClient.GitApiInterface service = DhofarRestClient.getGitApiInterface();
        show();
        try {

            // call = service.fetchSavedCards(options.getString("trackingId"), options.getString("orderId"));
            call = service.fetchSavedCards(resp.getTracking_id(), resp.getOrder_id());
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    hide();
                    try {
                        if (response.body() != null) {
                            JSONObject jo = new JSONObject(response.body());
                            String statusCode = jo.getString("statusCode");
                            if (statusCode.equalsIgnoreCase("0")) {
                                JSONArray ja = jo.getJSONArray("savedInfoList");

                                for (int i = 0; i < ja.length(); i++) {

                                    JSONObject jj = ja.getJSONObject(i);
                                    DhofarCard c = new DhofarCard();
                                    c.setCardHolderName(jj.getString("cardHolderName"));
                                    c.setCardMasked(jj.getString("cardMasked"));
                                    c.setCardName(jj.getString("cardName"));
                                    c.setCardToken(jj.getString("cardToken"));
                                    c.setCvv("");
                                    if (i == 0)
                                        c.setFlg(true);
                                    else c.setFlg(false);
                                    cards.add(c);

                                }

                                recyclerList.setAdapter(savedCardAdapter);


                                if (ja.length() > 0) {
                                    token = cards.get(0).getCardToken();
                                    cc_savedCard.setVisibility(View.VISIBLE);
                                    cc_newCard.setVisibility(View.GONE);
                                    scroll.setVisibility(View.VISIBLE);
                                } else {
                                    cc_savedCard.setVisibility(View.GONE);
                                    cc_newCard.setVisibility(View.VISIBLE);
                                    scroll.setVisibility(View.VISIBLE);


                                }

                            } else {
                                cc_savedCard.setVisibility(View.GONE);
                                cc_newCard.setVisibility(View.VISIBLE);
                                scroll.setVisibility(View.VISIBLE);
                            }

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
    }


    class savedCardAdapter extends RecyclerView.Adapter<savedCardAdapter.ViewHolder> {

        Context mContext;

        public savedCardAdapter(Context context) {
            this.mContext = context;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView txt_name, txt_cardNo;
            EditText edt_cvv;
            ImageView img_type;
            AppCompatRadioButton radio;

            public ViewHolder(View itemView) {
                super(itemView);

                txt_name = (TextView) itemView.findViewById(R.id.txt_name);
                txt_cardNo = (TextView) itemView.findViewById(R.id.txt_cardNo);
                img_type = (ImageView) itemView.findViewById(R.id.img_type);
                edt_cvv = (EditText) itemView.findViewById(R.id.edt_cvv);
                radio = (AppCompatRadioButton) itemView.findViewById(R.id.radio);


                ((TintableCompoundButton) radio).setSupportButtonTintList(colorStateList);


            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_card, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {


            final DhofarCard s = cards.get(position);


            holder.txt_name.setText(s.getCardHolderName());
            holder.txt_cardNo.setText(s.getCardMasked());
            holder.edt_cvv.setText("");
            if (s.isFlg()) {
                holder.radio.setChecked(true);
                holder.edt_cvv.setVisibility(View.VISIBLE);
            } else {
                holder.radio.setChecked(false);
                holder.edt_cvv.setVisibility(View.GONE);
            }


            holder.radio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {

                        saved_cvv = "";
                        for (int i = 0; i < cards.size(); i++) {
                            if (i == position) {
                                token = cards.get(position).getCardToken();
                                cards.get(position).setFlg(true);
                            } else
                                cards.get(i).setFlg(false);
                        }

                        notifyDataSetChanged();
                    }
                }
            });

            holder.edt_cvv.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    saved_cvv = s.toString();

                }
            });


        }

        @Override
        public int getItemCount() {
            return cards.size();
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


    public void fetchMerchantType() {
        DhofarRestClient.GitApiInterface service = DhofarRestClient.getGitApiInterface();
        show();
        try {
            JSONObject j_req = new JSONObject();
            j_req.put("order_id", resp.getOrder_id());
            j_req.put("tracking_id", resp.getTracking_id());
            call = service.fetchMerchantType(j_req.toString());
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    hide();
                    try {
                        if (response.body() != null) {
                            JSONObject jo = new JSONObject(response.body());
                            String statusCode = jo.getString("msg_status");
                            if (statusCode.equalsIgnoreCase("0")) {
                                mer_type = jo.getString("enc_response");

                                if (mer_type.equalsIgnoreCase("NS")) {
                                    if (resp.getCustomer_id() != null) {
                                        if (resp.getCustomer_id().length() > 0) {
                                            ll_input_chk.setVisibility(View.VISIBLE);
                                            fetchSavedCards();
                                        } else {
                                            ll_input_chk.setVisibility(View.GONE);
                                            scroll.setVisibility(View.VISIBLE);
                                        }
                                    } else {
                                        scroll.setVisibility(View.VISIBLE);
                                        ll_input_chk.setVisibility(View.GONE);
                                    }

                                } else if (mer_type.equalsIgnoreCase("S")) {
                                    Intent i = new Intent(DhofarHomeActivity.this, DhofarOtpActivity.class);
                                    i.putExtra("input", resp);
                                    startActivity(i);
                                    finish();
                                }

                            } else {
                                callBack.getInstance().onFailure(jo.getString("msg_status"), jo.getString("msg_desc"));
                            }

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
    }
}
