package ch.roulin.tests.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by thomas.roulin on 08.10.2015.
 */
public class MovieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_activity);
        Intent intent = getIntent();
        int movieId = intent.getIntExtra("movie_id", -1);
    }
}
