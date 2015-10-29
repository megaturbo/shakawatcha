package ch.roulin.tests.myapplication.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import ch.roulin.tests.myapplication.MovieActivity;
import ch.roulin.tests.myapplication.R;
import ch.roulin.tests.myapplication.adapters.MovieAdapter;
import ch.roulin.tests.myapplication.objects.Movie;

/**
 * Created by thomas.roulin on 11.10.2015.
 */
public class SearchListFragment extends ListFragment {

    private MovieAdapter adapter;
    private ArrayList<Movie> movies = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_movie, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapter = new MovieAdapter(getActivity(),
                R.layout.fragment_list_movie_item, movies);
        setListAdapter(adapter);
        ListView list = getListView();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), MovieActivity.class);
                i.putExtra("movie_id", movies.get(position).getId());
                startActivity(i);
            }
        });
    }

    public void updateMovies(ArrayList<Movie> movies) {
        this.movies.clear();
        for(Movie m: movies){
            this.movies.add(m);
        }

        if(adapter!=null){
            adapter.notifyDataSetChanged();
        }
    }
}
