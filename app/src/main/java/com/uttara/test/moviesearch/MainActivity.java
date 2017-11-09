package com.uttara.test.moviesearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private EditText etName;
    private Button popularBtn, upcomingBtn;
    private String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        etName =(EditText)findViewById(R.id.edit_text);
        popularBtn = (Button) findViewById(R.id.popular_movies);
        upcomingBtn = (Button) findViewById(R.id.upcoming_movies);


        etName.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            if(name!= null || !(name.isEmpty())) {
                                searchMovie(v);
                            } else {
                                Snackbar.make(findViewById(R.id.coordinatorLayout),"Enter name of movie",Snackbar.LENGTH_LONG).show();
                            }
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });
        searchPopularMovies();
        Log.d("moviesearch","MA-->onCreate");
    }


    public void searchMovie(View view) {
        Log.d("moviesearch","MA-->searchMovie");
        name = etName.getText().toString();

        if(name==null || name.isEmpty())
        {
            Snackbar.make(findViewById(R.id.coordinatorLayout),"Enter name of movie",Snackbar.LENGTH_LONG).show();
        } else {

            Intent intent = new Intent(this, MovieDetailActivity.class);
            intent.putExtra("name", name);
            startActivity(intent);
        }

    }

    public void searchPopularMovies(){
        final MoviesListFragment moviesListFragment = new MoviesListFragment();
        popularBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putBoolean("key",true);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.constraintLayout, moviesListFragment,moviesListFragment.getTag()).addToBackStack(null).commit();
                moviesListFragment.setArguments(b);
            }
        });
        upcomingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putBoolean("key",false);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.constraintLayout, moviesListFragment,moviesListFragment.getTag()).addToBackStack(null).commit();
                moviesListFragment.setArguments(b);
            }
        });


    }
}
