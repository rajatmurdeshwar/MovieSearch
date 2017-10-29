package com.uttara.test.moviesearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText etName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        etName =(EditText)findViewById(R.id.edit_text);
        Log.d("moviesearch","MA-->onCreate");
    }


    public void searchMovie(View view) {
        Log.d("moviesearch","MA-->searchMovie");
        String name = etName.getText().toString();
        if(name==null || name.isEmpty())
        {
            Toast.makeText(this, "Enter Name", Toast.LENGTH_SHORT).show();
        }

        Intent intent = new Intent(this,MovieDetailActivity.class);
        intent.putExtra("name",name);
        startActivity(intent);

    }

    public void searchPopularMovies(View view){
        MoviesListFragment moviesListFragment = new MoviesListFragment();
  getSupportFragmentManager().beginTransaction().replace(R.id.constraintLayout, moviesListFragment,moviesListFragment.getTag()).commit();
    }
}
