package ch.hearc.android.shakawatcha.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import ch.hearc.android.shakawatcha.R;
import ch.hearc.android.shakawatcha.activities.MainActivity;
import ch.hearc.android.shakawatcha.adapters.MovieAdapter;
import ch.hearc.android.shakawatcha.objects.Movie;
import ch.hearc.android.shakawatcha.objects.utils.MovieList;
import ch.hearc.android.shakawatcha.objects.utils.SimpleMovie;
import ch.hearc.android.shakawatcha.objects.utils.UserLists;

/**
 * Created by thomas.roulin on 11.10.2015.
 */
public class FragmentSearch extends ListFragment {

    private MovieAdapter adapter;
    private ArrayList<Movie> movies = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapter = new MovieAdapter(getActivity(),
                R.layout.fragment_search_item, movies);
        setListAdapter(adapter);

        ListView list = getListView();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // wat
                ArrayList<SimpleMovie> simpleMovies = SimpleMovie.convert(movies);
                ArrayList<MovieList> userList = new ArrayList<>();
                userList.add(new MovieList("Cool list", simpleMovies));
                userList.add(new MovieList("Better list", simpleMovies));
                UserLists.save(userList, getActivity().getPreferences(Context.MODE_PRIVATE));

                Movie clickedMovie = movies.get(position);
                ((MainActivity)getActivity()).showMovie(clickedMovie.getTitle(), clickedMovie.getId());
            }
        });
    }

    public static FragmentSearch newInstance(){
        FragmentSearch fragmentSearch = new FragmentSearch();
        return fragmentSearch;
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
