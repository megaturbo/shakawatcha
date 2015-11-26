package ch.hearc.android.shakawatcha.fragments.movies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ch.hearc.android.shakawatcha.R;

/**
 * Created by thomas.roulin on 12.11.2015.
 */
public class FragmentMovie extends Fragment {

    private TextView title;
    private int movieId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        title = (TextView)getActivity().findViewById(R.id.movie_test);
    }

    public void setMovie(int id) {
        title.setText(id);
    }
}
