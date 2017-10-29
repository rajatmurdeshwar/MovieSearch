package com.uttara.test.moviesearch;




import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitBuilder {
    private static Retrofit obj = null;
    public static final String BASE_URL ="https://api.themoviedb.org/3/";
    public static Retrofit getRetrofit()
    {
        if(obj==null)
        {
            obj= new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return obj;
    }
}
