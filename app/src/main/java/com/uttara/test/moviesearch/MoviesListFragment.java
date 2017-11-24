package com.uttara.test.moviesearch;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.io.IOException;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.uttara.test.moviesearch.MovieDetailActivity.API_KEY;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesListFragment extends Fragment implements MoviesAdapter.ListItemClickListener{


    private RecyclerView recyclerView;
    private static final String TAG = MoviesListFragment.class.getSimpleName();
    private ContentLoadingProgressBar progressBar;
    private boolean key;
    private CompositeDisposable _disposables;
    private APIEndPoints service;
    private List<MovieBean> bean;



    public MoviesListFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies_list, container, false);
        key = getArguments().getBoolean("key");
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        progressBar = (ContentLoadingProgressBar) view.findViewById(R.id.progress_bar);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        _disposables = new CompositeDisposable();

        service = new RetrofitBuilder().getStarWarsDetails();
        getMoviesList();

        return view;
    }

    private void getMoviesList() {

        _disposables.add(service.getPopularMovies(API_KEY).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Results>() {
                                   @Override
                                   public void onNext(@NonNull Results results) {
                                       getMovieListDetails(results);
                                   }

                                   @Override
                                   public void onError(@NonNull Throwable e) {
                                       Log.d(TAG," onError "+e.getMessage());
                                   }

                                   @Override
                                   public void onComplete() {
                                       Log.d(TAG," onCompleted");
                                   }
                               }
                )
        );
    }

    private void getMovieListDetails(Results results) {
        bean = results.getResults();
        recyclerView.setAdapter(new MoviesAdapter(getContext(),bean,this));
        Log.d(TAG,"  getMoviesList  ");
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Log.d(TAG," "+clickedItemIndex);
       Log.d(TAG,bean.get(clickedItemIndex).getTitle());
        Intent i = new Intent(getActivity(),MovieDetailActivity.class);
        i.putExtra("MovieName",bean.get(clickedItemIndex).getTitle());
        startActivity(i);
    }


}
