package com.avenues.merchant;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StatusActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.response)
    TextView response;
    @BindView(R.id.response1)
    TextView response1;
    @BindView(R.id.stc)
    TextView stc;
    @BindView(R.id.stm)
    TextView stm;
    @BindView(R.id.stc1)
    TextView stc1;
    @BindView(R.id.stm1)
    TextView stm1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        ButterKnife.bind(this);

        String status = getIntent().getStringExtra("status");

        if (status.equalsIgnoreCase("success")) {
            response1.setText(getIntent().getStringExtra("response"));
            toolbar.setTitle("Payment Success");
            response.setVisibility(View.VISIBLE);
            response1.setVisibility(View.VISIBLE);

            stc.setVisibility(View.GONE);
            stc1.setVisibility(View.GONE);
            stm.setVisibility(View.GONE);
            stm1.setVisibility(View.GONE);
        } else {
            toolbar.setTitle("Payment Failed");

            stc1.setText(getIntent().getStringExtra("statusCode"));
            stm1.setText(getIntent().getStringExtra("statusMessage"));
            response.setVisibility(View.GONE);
            response1.setVisibility(View.GONE);
            stc.setVisibility(View.VISIBLE);
            stc1.setVisibility(View.VISIBLE);
            stm.setVisibility(View.VISIBLE);
            stm1.setVisibility(View.VISIBLE);
        }


    }
}
