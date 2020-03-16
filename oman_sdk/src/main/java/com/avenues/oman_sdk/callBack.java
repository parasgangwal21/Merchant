package com.avenues.oman_sdk;

/**
 * Created by paras.gangwal on 7/9/17.
 */

public class callBack {

    public interface stateListener {
        void onSuccess(String response);

        void onFailure(String statusCode, String statusMessage);
    }

    private static callBack mInstance;
    private stateListener mListener;
    private String response;
    private String statusCode;
    private String statusMessage;

    private callBack() {
    }

    public static callBack getInstance() {
        if (mInstance == null) {
            mInstance = new callBack();
        }
        return mInstance;
    }

    public void setListener(stateListener listener) {
        mListener = listener;
    }

    public void onSuccess(String response) {
        if (mListener != null) {
            this.response = response;
            notifySuccess();
        }
    }

    public void onFailure(String statusCode, String statusMessage) {
        if (mListener != null) {
            this.statusMessage = statusMessage;
            this.statusCode = statusCode;
            notifyFailure();
        }
    }


    private void notifySuccess() {
        mListener.onSuccess(response);
    }

    private void notifyFailure() {
        mListener.onFailure(statusCode, statusMessage);
    }
}
