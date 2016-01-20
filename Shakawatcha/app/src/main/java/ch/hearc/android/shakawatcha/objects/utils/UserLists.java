package ch.hearc.android.shakawatcha.objects.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import ch.hearc.android.shakawatcha.objects.Movie;

/**
 * Created by thomas.roulin on 01.12.2015.
 */
public class UserLists {

    private static final String PREF_USER_LISTS = "PREF_USER_LISTS";
    private ArrayList<MovieList> movieLists;

    public UserLists() {
        this.movieLists = new ArrayList<>();
    }

    public UserLists(ArrayList<MovieList> movieLists) {
        this.movieLists = movieLists;
    }

    public void add(MovieList movieList) {
        this.movieLists.add(movieList);
    }

    public MovieList get(int index){
        return this.movieLists.get(index);
    }

    public void remove(MovieList movieList) {
        this.movieLists.remove(movieList);
    }

    public void remove(int index) {
        this.movieLists.remove(index);
    }

    public ArrayList<MovieList> getLists() {
        return this.movieLists;
    }

    public void setLists(ArrayList<MovieList> movieLists) {
        this.movieLists = movieLists;
    }

    public List<String> getListNames() {
        List<String> listNames = new ArrayList<>();
        for (MovieList list : this.movieLists) {
            listNames.add(list.getName());
        }
        return listNames;
    }

    /**
     * Retrieve User Lists from SharedPrefrences
     *
     * @return The User Lists
     */
    public static UserLists retrieve(Context context) {

        // Retrieve User preferences data
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String userListsJSON = sharedPreferences.getString(PREF_USER_LISTS, "");

        // If SharedPreference does not exist
        if (userListsJSON.equals("")) {
            return new UserLists();
        }

        // Create Object from JSON
        Gson gson = new Gson();
        UserLists userLists = gson.fromJson(userListsJSON, UserLists.class);

        Log.d("SKW", "Userlists retrieved, count: " + userLists.movieLists.size());

        return userLists;
    }

    @Override
    public String toString() {
        return "UserLists{" +
                "movieLists=" + movieLists +
                '}';
    }

    /**
     * Save User Lists to SharedPreferencse
     *
     * @param userLists         ArrayList of user's MovieLists
     */
    public static void save(UserLists userLists, Context context) {

        // Create JSON representation of UserLists
        Gson gson = new Gson();
        String userListsJSON = gson.toJson(userLists);

        // Save it in SharedPreferences (apply() does it in background)
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editPreferences = sharedPreferences.edit();
        editPreferences.putString(PREF_USER_LISTS, userListsJSON);
        editPreferences.apply();

    }

    public static void remove(MovieList movieList, Context context) {
        UserLists userLists = retrieve(context);

        ArrayList<MovieList> lists = userLists.getLists();
        for (int i = lists.size() - 1; i >= 0; i--) {
            if (lists.get(i).getName().equals(movieList.getName())) {
                userLists.remove(i);
            }
        }
        userLists.setLists(lists);
        save(userLists, context);
    }

    public static void addToList(Movie movie, String listName, Context context) {
        UserLists userLists = retrieve(context);

        for (MovieList list :
                userLists.movieLists) {
            if(list.getName().equals(listName)){
                list.add(movie);
            }
        }

        save(userLists, context);
    }
}
