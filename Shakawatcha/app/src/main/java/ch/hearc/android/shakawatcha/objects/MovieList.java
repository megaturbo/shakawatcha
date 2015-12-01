package ch.hearc.android.shakawatcha.objects;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thomas.roulin on 30.11.2015.
 */
public class MovieList {
    private static final String PREF_MOVIE_LIST = "PREF_MOVIE_LIST";

    private String name;
    private List<SimpleMovie> movies;

    public MovieList(String name) {
        this.name = name;
        this.movies = new ArrayList<>();
    }

    public static ArrayList<MovieList> retrieve(SharedPreferences sharedPreferences){
        Gson gson = new Gson();
        ArrayList<MovieList> movieLists= new ArrayList<>();

        String object = sharedPreferences.getString(PREF_MOVIE_LIST, "");
        movieLists = gson.fromJson(object, ArrayList.class);
    }

    public static void save(List<SimpleMovie> simpleMovies){
        Gson gson = new Gson();
        String object = gson.toJson(simpleMovies);
        Log.d("YOLO", object);
    }
}
