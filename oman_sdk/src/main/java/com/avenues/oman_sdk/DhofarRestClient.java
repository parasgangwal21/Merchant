package com.avenues.oman_sdk;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public class DhofarRestClient {

    private static GitApiInterface gitApiInterface;


    public static GitApiInterface getGitApiInterface() {
        if (gitApiInterface == null) {

            OkHttpClient client = new OkHttpClient.Builder()
                    .cache(null)
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(DhofarConstants.url)
                    .client(client)
                    .addConverterFactory(new ToStringConverterFactory())
                    .build();

            gitApiInterface = retrofit.create(GitApiInterface.class);
        }
        return gitApiInterface;
    }


    public interface GitApiInterface {


        @FormUrlEncoded
        @POST("appTxn/identifyCard")
        Call<String> identifyCard(@Field("tracking_id") String tracking_id, @Field("order_id") String order_id, @Field("card_bin") String card_bin);


        @FormUrlEncoded
        @POST("txn/registerTxn")
        Call<String> registerTxn(@Field("tracking_id") String tracking_id, @Field("name_on_card") String name_on_card, @Field("card_number") String card_number, @Field("card_expiry") String card_expiry, @Field("card_cvv") String card_cvv, @Field("card_name") String card_name, @Field("amount") String amount, @Field("currency") String currency, @Field("order_id") String order_id, @Field("card_token") String card_token, @Field("save_card") String save_card);


        @FormUrlEncoded
        @POST("txn/abortTxn")
        Call<String> abortTxn(@Field("tracking_id") String tracking_id, @Field("order_id") String order_id);


        @FormUrlEncoded
        @POST("appTxn/processTransaction")
        Call<String> processTransaction(@Field("tracking_id") String tracking_id, @Field("order_id") String order_id, @Field("otp") String otp);


        @FormUrlEncoded
        @POST("appTxn/fetchSavedCards")
        Call<String> fetchSavedCards(@Field("tracking_id") String tracking_id, @Field("order_id") String order_id);


        @Headers("Content-Type: application/json")
        @POST("appTxn/fetchMerchantType")
        Call<String> fetchMerchantType(@Body String body);


    }


}
