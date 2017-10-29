package com.uttara.test.moviesearch;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.uttara.test.moviesearch.MovieDetailActivity.API_KEY;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesListFragment extends Fragment {


RecyclerView recyclerView;


    public MoviesListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
View view = inflater.inflate(R.layout.fragment_movies_list, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Retrofit retrofit = RetrofitBuilder.getRetrofit();
        APIEndPoints service = retrofit.create(APIEndPoints.class);
        Call<Results> call = service.getPopularMovies(API_KEY);
        call.enqueue(new Callback<Results>() {
            @Override
            public void onResponse(Call<Results> call, Response<Results> response) {
                if(response.isSuccessful()){
                    List<MovieBean> bean = response.body().getResults();
                    recyclerView.setAdapter(new MoviesAdapter(getContext(),bean));
                }
            }

            @Override
            public void onFailure(Call<Results> call, Throwable t) {

            }
        });


        // Inflate the layout for this fragment
        return view;
    }

}
