package com.uttara.test.moviesearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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

public class MovieDetailActivity extends AppCompatActivity {

    private ImageView imgMoviePoster;
    private TextView tvMovieName, tvYear, tvRate, tvGerne, tvContent, originalLanguage, popularity, voteCount;

    private static final String TAG = MovieDetailActivity.class.getSimpleName();
    private String data;
    private String imagePath;
    static final String API_KEY = "84040e61f6d76d593351bdce14a4c860";
    static final String IMAGE_URL="https://image.tmdb.org/t/p/w500";
    static final String MOVIE_TITLE ="title";
    private CompositeDisposable _disposables;
    private APIEndPoints service;
    private List<MovieBean> list;
    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_movie_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initCollapsingToolbar();

        Intent intent = getIntent();
        data = intent.getStringExtra("name");
        data = intent.getStringExtra("MovieName");

        _disposables = new CompositeDisposable();
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        //ratingBar.setNumStars(10);
        imgMoviePoster = (ImageView) findViewById(R.id.expandedImage);
        tvMovieName = (TextView) findViewById(R.id.title);
        originalLanguage = (TextView) findViewById(R.id.originalLang_tv);
        popularity = (TextView) findViewById(R.id.popularity_tv);
        voteCount = (TextView) findViewById(R.id.voteCount_tv);
        tvYear = (TextView) findViewById(R.id.release_date_tv);
        tvRate = (TextView) findViewById(R.id.rating_tv);
        tvGerne = (TextView) findViewById(R.id.genere_tv);
        tvContent = (TextView) findViewById(R.id.content_tv);


        service = new RetrofitBuilder().getStarWarsDetails();
        getMovieDetails();
    }

    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_data));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(_disposables!= null && !_disposables.isDisposed()) {
            _disposables.dispose();
        }
    }

    private void getMovieDetails() {
        _disposables.add(service
                .getMovieDetails(API_KEY, data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Results>() {

                    @Override
                    public void onNext(@NonNull Results results) {
                       getMovieSearchDetails(results);
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

    private void getMovieSearchDetails(Results results) {
       list = results.getResults();
        int i=0;
        if(list!= null) {
            Log.d(TAG," "+list);
            tvMovieName.setText(list.get(i).getTitle());
            tvContent.setText(list.get(i).getOverview());
            tvGerne.setText(String.valueOf(list.get(i).getGenreIds().get(i)));
            ratingBar.setRating(Float.parseFloat(Double.toString(list.get(i).getVoteAverage())));
            Picasso.with(this).load(IMAGE_URL+""+list.get(i).getPosterPath()).into(imgMoviePoster);
            originalLanguage.setText(list.get(i).getOriginalLanguage());
            tvYear.setText(list.get(i).getReleaseDate());
            voteCount.setText(String.valueOf(list.get(i).getVoteCount()));
            popularity.setText(Double.toString(list.get(i).getPopularity()));
        }
    }

}

