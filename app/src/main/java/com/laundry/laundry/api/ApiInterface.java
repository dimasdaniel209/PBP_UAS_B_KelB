package com.laundry.laundry.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("setrika")
    Call<SetrikaResponse> getAllSetrika(@Query("data") String data);

    @GET("setrika/{id}")
    Call<SetrikaResponse> getSetrikaById(@Path("id")String id,
                                      @Query("data") String data);

    @POST("setrika")
    @FormUrlEncoded
    Call<SetrikaResponse> createSetrika(@Field("berat") double berat,
                                     @Field("jumlah_pakaian") int jumlah_pakaian,
                                     @Field("jenis_pakaian") String jenis_pakaian);

    @POST("setrika/update/{id}")
    @FormUrlEncoded
    Call<SetrikaResponse> updateSetrika(@Path("id") String id,
                                     @Field("berat") double berat,
                                     @Field("jumlah_pakaian") int jumlah_pakaian,
                                     @Field("jenis_pakaian") String jenis_pakaian);

    @POST("setrika/delete/{id}")
    @FormUrlEncoded
    Call<SetrikaResponse> deleteSetrika(@Path("id") String id,
                                     @Field("id") String data);

    @GET("sepatu")
    Call<SepatuResponse> getAllSepatu(@Query("data") String data);

    @GET("sepatu/{id}")
    Call<SepatuResponse> getSepatuById(@Path("id")String id,
                                     @Query("data") String data);

    @POST("sepatu")
    @FormUrlEncoded
    Call<SepatuResponse> createSepatu(@Field("jenis_layanan") String jenis_layanan,
                                    @Field("kondisi") String kondisi,
                                    @Field("jenis_sepatu") String jenis_sepatu);

    @POST("sepatu/update/{id}")
    @FormUrlEncoded
    Call<SepatuResponse> updateSepatu(@Path("id") String id,
                                    @Field("jenis_layanan") String jenis_layanan,
                                    @Field("kondisi") String kondisi,
                                    @Field("jenis_sepatu") String jenis_sepatu);

    @POST("sepatu/delete/{id}")
    @FormUrlEncoded
    Call<SepatuResponse> deleteSepatu(@Path("id") String id,
                                    @Field("id") String data);
}
