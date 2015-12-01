package ch.hearc.android.shakawatcha.objects.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thomas.roulin on 30.11.2015.
 */
public class MovieList {

    private String name;
    private List<SimpleMovie> movies;

    public MovieList(String name) {
        this.name = name;
        this.movies = new ArrayList<>();
    }

    public MovieList(String name, ArrayList<SimpleMovie> movies) {
        this.name = name;
        this.movies = movies;
    }

    public void add(SimpleMovie movie) {
        this.movies.add(movie);
    }

    public void remove(int movieId) {
        for (SimpleMovie movie : this.movies) {
            if (movie.getId() == movieId) {
                this.movies.remove(movie);
            }
        }
    }
}
