package com.avenues.oman_sdk;

import android.os.Parcel;
import android.os.Parcelable;

public class orderDetails implements Parcelable {
    String tracking_id;
    String order_id;
    String currency;
    String amount;

    String customer_id;
    String theme_color;
    String mer_logo_url;

    public String getMer_environment() {
        return mer_environment;
    }

    public void setMer_environment(String mer_environment) {
        this.mer_environment = mer_environment;
    }

    String mer_environment;

    public String getTracking_id() {
        return tracking_id;
    }

    public void setTracking_id(String tracking_id) {
        this.tracking_id = tracking_id;
    }

    public static Creator<orderDetails> getCREATOR() {
        return CREATOR;
    }


    public orderDetails() {
    }

    public orderDetails(Parcel in) {

        order_id = in.readString();
        currency = in.readString();
        amount = in.readString();
        customer_id = in.readString();
        theme_color = in.readString();
        mer_logo_url = in.readString();
        tracking_id = in.readString();
        mer_environment = in.readString();
    }

    public static final Creator<orderDetails> CREATOR = new Creator<orderDetails>() {
        @Override
        public orderDetails createFromParcel(Parcel in) {
            return new orderDetails(in);
        }

        @Override
        public orderDetails[] newArray(int size) {
            return new orderDetails[size];
        }
    };


    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }


    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getTheme_color() {
        return theme_color;
    }

    public void setTheme_color(String theme_color) {
        this.theme_color = theme_color;
    }

    public String getMer_logo_url() {
        return mer_logo_url;
    }

    public void setMer_logo_url(String mer_logo_url) {
        this.mer_logo_url = mer_logo_url;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(order_id);
        dest.writeString(currency);
        dest.writeString(amount);
        dest.writeString(customer_id);
        dest.writeString(theme_color);
        dest.writeString(mer_logo_url);
        dest.writeString(tracking_id);
        dest.writeString(mer_environment);
    }
}
