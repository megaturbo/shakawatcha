package ch.hearc.android.shakawatcha.objects.utils;

import java.util.ArrayList;
import java.util.List;

import ch.hearc.android.shakawatcha.objects.Movie;

/**
 * Created by thomas.roulin on 01.12.2015.
 */
public class SimpleMovie {

    private int id;
    private String name;

    public SimpleMovie(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static ArrayList<SimpleMovie> convert(List<Movie> movies) {
        ArrayList<SimpleMovie> simpleMovies = new ArrayList<>();
        for (Movie m : movies) {
            simpleMovies.add(new SimpleMovie(m.getId(), m.getTitle()));
        }
        return simpleMovies;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
