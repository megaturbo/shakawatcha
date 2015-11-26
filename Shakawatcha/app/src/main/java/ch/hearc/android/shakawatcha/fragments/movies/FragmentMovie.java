package ch.hearc.android.shakawatcha.fragments.movies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ch.hearc.android.shakawatcha.R;

/**
 * Created by thomas.roulin on 12.11.2015.
 */
public class FragmentMovie extends Fragment {

    private TextView tvTitle;

    private String movieTitle;
    private int movieId;

    public static final String ARG_TITLE = "TITLE";
    public static final String ARG_ID = "ID";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle args = getArguments();
        movieTitle = args.getString(ARG_TITLE);
        movieId = args.getInt(ARG_ID);

        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        tvTitle = (TextView) getActivity().findViewById(R.id.movie_test);
        tvTitle.setText(this.movieTitle);
    }

    public static FragmentMovie newInstance(String movieTitle, int movieId) {
        FragmentMovie fragmentMovie = new FragmentMovie();

        Bundle args = new Bundle();
        args.putString(ARG_TITLE, movieTitle);
        args.putInt(ARG_ID, movieId);
        fragmentMovie.setArguments(args);

        return fragmentMovie;
    }
}
