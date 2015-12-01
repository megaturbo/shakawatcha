package ch.hearc.android.shakawatcha.objects.utils;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by thomas.roulin on 01.12.2015.
 */
public class UserLists {

    private static final String PREF_USER_LISTS = "PREF_USER_LISTS";
    private ArrayList<MovieList> movieLists;

    public UserLists(ArrayList<MovieList> movieLists) {
        this.movieLists = movieLists;
    }

    /**
     * Retrieve User Lists from SharedPrefrences
     *
     * @param sharedPreferences Where to retrieve
     * @return The User Lists
     */
    public static ArrayList<MovieList> retrieve(SharedPreferences sharedPreferences) {

        // Retrieve User preferences data
        String userListsJSON = sharedPreferences.getString(PREF_USER_LISTS, "");

        if(userListsJSON.equals("")) {
            return new ArrayList<>();
        }

        // Create Object from JSON
        Gson gson = new Gson();
        UserLists userLists = gson.fromJson(userListsJSON, UserLists.class);

        return userLists.movieLists;
    }

    /**
     * Save User Lists to SharedPreferencse
     * @param userLists ArrayList of user's MovieLists
     * @param sharedPreferences Where to save
     */
    public static void save(ArrayList<MovieList> userLists, SharedPreferences sharedPreferences) {

        // Create JSON representation of UserLists
        Gson gson = new Gson();
        String userListsJSON = gson.toJson(userLists);
        Log.d("YOLO", userListsJSON);

        // Save it in SharedPreferences (apply() does it in background)
//        SharedPreferences.Editor editPreferences = sharedPreferences.edit();
//        editPreferences.putString(PREF_USER_LISTS, userListsJSON);
//        editPreferences.apply();
    }
}
