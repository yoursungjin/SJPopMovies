package com.yoursungjin.sjpopmovies;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


import com.yoursungjin.sjpopmovies.api.PopMoviesClient;
import com.yoursungjin.sjpopmovies.data.MovieDataRoot;
import com.yoursungjin.sjpopmovies.data.source.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";

    private PopMoviesAdapter mMoviesAdapter;
    private PopMoviesClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.movie_toolbar);
        setSupportActionBar(toolbar);


        RecyclerView mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mMoviesAdapter = new PopMoviesAdapter(this);
        mRecyclerView.setAdapter(mMoviesAdapter);

        client = ServiceGenerator.createService(PopMoviesClient.class);
        fetchMoviesSortByMostPopular();

    }

    private void fetchMoviesSortByMostPopular() {
        Call<MovieDataRoot> call = client.moviesSortByMostPopular(ServiceGenerator.API_KEY);
        enqueueCall(call);
    }

    private void fetchMoviesSortByTopRated() {
        Call<MovieDataRoot> call = client.moviesSortByTopRated(ServiceGenerator.API_KEY);
        enqueueCall(call);
    }

    private void enqueueCall(Call<MovieDataRoot> call) {
        call.enqueue(new Callback<MovieDataRoot>() {
            @Override
            public void onResponse(Call<MovieDataRoot> call, Response<MovieDataRoot> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null)
                        mMoviesAdapter.setPopMoviesList(response.body().getResults());
                } else {
                    // error response, no access to resource
                    Log.d("Error", "Response is not successful");
                }
            }

            @Override
            public void onFailure(Call<MovieDataRoot> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        // The action bar will automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case R.id.item_sort_by_popular:
                fetchMoviesSortByMostPopular();
                return true;
            case R.id.item_sort_by_top_rated:
                fetchMoviesSortByTopRated();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


}
