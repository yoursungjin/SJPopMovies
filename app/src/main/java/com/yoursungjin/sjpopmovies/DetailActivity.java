package com.yoursungjin.sjpopmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yoursungjin.sjpopmovies.api.PopMoviesClient;
import com.yoursungjin.sjpopmovies.data.detail.MovieDetail;
import com.yoursungjin.sjpopmovies.data.source.ServiceGenerator;
import com.yoursungjin.sjpopmovies.util.MovieDataUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailActivity extends AppCompatActivity {

    private ImageView movieThumbnail;
    private TextView movieTitle;
    private TextView releaseDate;
    private TextView runningTime;
    private TextView voteAverage;
    private TextView overview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        movieThumbnail = findViewById(R.id.imageView_movieThumbnail);
        movieTitle = findViewById(R.id.textView_movie_title);
        releaseDate = findViewById(R.id.textView_release_date);
        runningTime = findViewById(R.id.textView_running_time);
        voteAverage = findViewById(R.id.textView_vote_average);
        overview = findViewById(R.id.textView_overview);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int movie_Id = bundle.getInt("movie_Id", -1);
            //Result movie = getIntent().getExtras().getParcelable("movie");
            fetchMovieByID(movie_Id);
        }

    }


    private void fetchMovieByID(int movie_Id) {
        PopMoviesClient client = ServiceGenerator.createService(PopMoviesClient.class);
        Call<MovieDetail> call = client.movieByID(movie_Id, ServiceGenerator.API_KEY);
        call.enqueue(new Callback<MovieDetail>() {
            @Override
            public void onResponse(Call<MovieDetail> call, Response<MovieDetail> response) {
                if (response.isSuccessful()) {
                    MovieDetail movie = response.body();
                    if (movie != null) {
                        Picasso.with(movieThumbnail.getContext()).load(MovieDataUtil.posterURL(movie.getPosterPath())).into(movieThumbnail);
                        movieTitle.setText(movie.getTitle());
                        releaseDate.setText(MovieDataUtil.releaseYear(movie.getReleaseDate()));
                        runningTime.setText(MovieDataUtil.runningTimeMinutes(movie.getRuntime()));
                        voteAverage.setText(MovieDataUtil.voteAverageTen(movie.getVoteAverage()));
                        overview.setText(movie.getOverview());
                    }
                } else {
                    // error response, no access to resource
                    Log.d("Error", "Response is not successful");
                }
            }

            @Override
            public void onFailure(Call<MovieDetail> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }

        });
    }
}
