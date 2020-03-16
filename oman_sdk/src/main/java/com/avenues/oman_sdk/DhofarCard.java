package com.avenues.oman_sdk;

public class DhofarCard {

    String cardToken;
    String cardMasked;
    String cardHolderName;

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    String cvv;

    public boolean isFlg() {
        return flg;
    }

    public void setFlg(boolean flg) {
        this.flg = flg;
    }

    boolean flg;

    public String getCardToken() {
        return cardToken;
    }

    public void setCardToken(String cardToken) {
        this.cardToken = cardToken;
    }

    public String getCardMasked() {
        return cardMasked;
    }

    public void setCardMasked(String cardMasked) {
        this.cardMasked = cardMasked;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    String cardName;
}
