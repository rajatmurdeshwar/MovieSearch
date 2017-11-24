package com.uttara.test.moviesearch;




import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitBuilder {

    public static final String BASE_URL ="https://api.themoviedb.org/3/";
    public static Retrofit getRetrofit() {

        return  new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public APIEndPoints getStarWarsDetails() {
        final Retrofit retrofit = getRetrofit();
        return retrofit.create(APIEndPoints.class);
    }
}
