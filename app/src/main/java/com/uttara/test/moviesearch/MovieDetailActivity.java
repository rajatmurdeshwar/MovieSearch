package com.uttara.test.moviesearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MovieDetailActivity extends AppCompatActivity {

    ImageView imgMoviePoster;
    TextView tvMovieName;
    TextView tvYear;
    TextView tvRate;
    TextView tvGerne;
    TextView tvcontent;
    TextView originalLanguage;
    TextView popularity;
    TextView voteCount;
    CollapsingToolbarLayout collapsingToolbarLayout;

    String data;
    private String imagePath;
    static final String API_KEY = "84040e61f6d76";
    static final String IMAGE_URL="https://image.tmdb.org/t/p/w500";
    static final String MOVIE_TITLE ="title";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        data = intent.getStringExtra("name");
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        collapsingToolbarLayout.setTitleEnabled(false);
        toolbar.setTitle(data);
        imgMoviePoster = (ImageView) findViewById(R.id.expandedImage);
        tvMovieName = (TextView) findViewById(R.id.title);
        originalLanguage = (TextView) findViewById(R.id.originalLang_tv);
        popularity = (TextView) findViewById(R.id.popularity_tv);
        voteCount = (TextView) findViewById(R.id.voteCount_tv);
        tvYear = (TextView) findViewById(R.id.release_date_tv);
        tvRate = (TextView) findViewById(R.id.rating_tv);
        tvGerne = (TextView) findViewById(R.id.genere_tv);
        tvcontent = (TextView) findViewById(R.id.content_tv);

        //getSupportLoaderManager().initLoader(LOADER_ID,b,this);
        getMovieDetails();
    }

    private void getMovieDetails() {

        if (data != null) {
            //String movieName = data;
            Retrofit retrofit = RetrofitBuilder.getRetrofit();
            APIEndPoints service = retrofit.create(APIEndPoints.class);
            Call<Results> call = service.getMovieDetails(API_KEY,data);
            Log.d("moviessearch","in "+String.valueOf(service.hashCode()));
            call.enqueue(new Callback<Results>() {
                @Override
                public void onResponse(Call<Results> call, Response<Results> response) {
                    Log.d("moviesearch", "in M2A=--> fetchMovie->CallBack-->onResponse sucess "  + response.isSuccessful());
                    if(response.isSuccessful()) {
                        try {
                            Log.d("moviesearch", "in M2A=--> fetchMovie->CallBack-->onResponse " + response.body());
                            List<MovieBean> bean = response.body().getResults();

                            tvMovieName.setText(bean.get(0).getTitle());
                            Log.d("moviesearch", "in M2A=--> fetchMovie->CallBack-->onResponse " + bean.get(0).getTitle());
                            originalLanguage.setText(bean.get(0).getOriginalLanguage());
                            popularity.setText(Double.toString(bean.get(0).getPopularity()));
                            imagePath = bean.get(0).getPosterPath();
                            tvcontent.setText(bean.get(0).getOverview());
                            voteCount.setText(String.valueOf(bean.get(0).getVoteCount()));
                            tvYear.setText(bean.get(0).getReleaseDate());
                            tvRate.setText(Double.toString(bean.get(0).getVoteAverage()));
                            tvGerne.setText(String.valueOf(bean.get(0).getGenreIds().get(0)));
                            if(IMAGE_URL !=null && imagePath != null) {
                                Log.d("moviesearch",IMAGE_URL);
                                Picasso.with(MovieDetailActivity.this).load(IMAGE_URL+""+imagePath).into(imgMoviePoster);
                            }
                        }catch(Exception e) {
                            Log.e("moviesearch",e.getMessage());
                            e.printStackTrace();
                        }

                    }
                }

                @Override
                public void onFailure(Call<Results> call, Throwable t) {
                    Log.d("moviesearch", "in MA=--> fetchMovie->CallBack-->onResponse failure t" + t);
                    t.printStackTrace();

                }
            });
        }

    }
}
