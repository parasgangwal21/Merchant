package com.avenues.merchant;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;

public class RestClient {

    private static GitApiInterface gitApiInterface;


    private static String URL = "https://certsecurepayment.bdpg.bankdhofar.com/";
    //private static String URL = "http://192.168.2.49:8080";

    public static GitApiInterface getGitApiInterface() {
        if (gitApiInterface == null) {

            OkHttpClient client = new OkHttpClient.Builder()
                    .cache(null)
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();




            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .client(client)
                    .addConverterFactory(new ToStringConverterFactory())
                    .build();

            gitApiInterface = retrofit.create(GitApiInterface.class);
        }
        return gitApiInterface;
    }



    public interface GitApiInterface {



        @FormUrlEncoded
        @POST
        Call<String> encrypt_NS(@Url String url,@Field("reg_id") String reg_id, @Field("access_code") String access_code, @Field("order_id") String order_id, @Field("currency") String currency, @Field("amount") String amount, @Field("redirect_url") String redirect_url, @Field("cancel_url") String cancel_url, @Field("mer_param_1") String mer_param_1, @Field("mer_param_2") String mer_param_2, @Field("mer_param_3") String mer_param_3, @Field("mer_param_4") String mer_param_4, @Field("mer_param_5") String mer_param_5, @Field("customer_id") String customer_id);


        @FormUrlEncoded
        @POST
        //Call<String> encrypt_S(@Url String url,@Field("reg_id") String reg_id, @Field("access_code") String access_code, @Field("order_id") String order_id, @Field("currency") String currency, @Field("amount") String amount, @Field("redirect_url") String redirect_url, @Field("cancel_url") String cancel_url, @Field("mer_param_1") String mer_param_1, @Field("mer_param_2") String mer_param_2, @Field("mer_param_3") String mer_param_3, @Field("mer_param_4") String mer_param_4, @Field("mer_param_5") String mer_param_5, @Field("customer_id") String customer_id, @Field("name_on_card") String name_on_card, @Field("card_number") String card_number, @Field("card_expiry") String card_expiry, @Field("card_cvv") String card_cvv);
        Call<String> encrypt_S(@Url String url,@Field("reg_id") String reg_id, @Field("access_code") String access_code, @Field("order_id") String order_id, @Field("currency") String currency, @Field("amount") String amount, @Field("redirect_url") String redirect_url, @Field("cancel_url") String cancel_url, @Field("mer_param_1") String mer_param_1, @Field("mer_param_2") String mer_param_2, @Field("mer_param_3") String mer_param_3, @Field("mer_param_4") String mer_param_4, @Field("mer_param_5") String mer_param_5, @Field("name_on_card") String name_on_card, @Field("card_number") String card_number, @Field("card_expiry") String card_expiry, @Field("card_cvv") String card_cvv);




        @Headers("Content-Type: application/json")
        @POST("wrapper/appTxn/createOrder")
        Call<String> createOrder(@Body String body);



        @FormUrlEncoded
        @POST
        Call<String> decrypt(@Url String url,@Field("enc_request") String enc_request,@Field("reg_id") String reg_id, @Field("access_code") String access_code);





    }



}
