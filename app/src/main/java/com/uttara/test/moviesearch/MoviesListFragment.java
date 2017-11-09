package com.uttara.test.moviesearch;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.uttara.test.moviesearch.MovieDetailActivity.API_KEY;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesListFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<MovieBean>> {


     RecyclerView recyclerView;
    List<MovieBean> listData;
    ProgressBar progressBar;
    boolean key;
    public static int LOADER_ID = 111;


    public MoviesListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies_list, container, false);
        key = getArguments().getBoolean("key");
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        getLoaderManager().initLoader(LOADER_ID,null,this);


        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public Loader<List<MovieBean>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<List<MovieBean>>(getActivity()) {
            List<MovieBean> bean= null;

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                if(bean!= null){
                    deliverResult(bean);
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    forceLoad();
                }
            }

            @Override
            public List<MovieBean> loadInBackground() {
                Retrofit retrofit = RetrofitBuilder.getRetrofit();
                APIEndPoints service = retrofit.create(APIEndPoints.class);
                if(key) {
                    Call<Results> call = service.getPopularMovies(API_KEY);
                    try {
                        bean = call.execute().body().getResults();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else{
                    Call<Results> call = service.getUpcomingMovies(API_KEY);
                    try {
                        bean = call.execute().body().getResults();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return bean;
            }

            @Override
            public void deliverResult(List<MovieBean> data) {
                super.deliverResult(data);
                listData = data;

            }
        };
    }

    @Override
    public void onLoadFinished(Loader<List<MovieBean>> loader, List<MovieBean> data) {

        if(data!= null) {
            progressBar.setVisibility(View.INVISIBLE);
            recyclerView.setAdapter(new MoviesAdapter(getContext(),data));
        }

    }

    @Override
    public void onLoaderReset(Loader<List<MovieBean>> loader) {
         getLoaderManager().restartLoader(LOADER_ID,null,this);
    }
}
