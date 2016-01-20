package ch.hearc.android.shakawatcha.objects.utils;

import java.util.ArrayList;
import java.util.List;

import ch.hearc.android.shakawatcha.objects.Movie;

/**
 * Created by thomas.roulin on 30.11.2015.
 */
public class MovieList {

    private String name;
    private ArrayList<Movie> movies;

    public MovieList(String name) {
        this.name = name;
        this.movies = new ArrayList<>();
    }

    public MovieList(String name, ArrayList<Movie> movies) {
        this.name = name;
        this.movies = movies;
    }

    public void add(Movie movie) {
        this.movies.add(movie);
    }

    public void remove(int movieId) {
        for (Movie movie : this.movies) {
            if (movie.getId() == movieId) {
                this.movies.remove(movie);
            }
        }
    }

    public String getName() {
        return name;
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public String toString() {
        return "MovieList{" +
                "name='" + name + '\'' +
                '}';
    }
}
