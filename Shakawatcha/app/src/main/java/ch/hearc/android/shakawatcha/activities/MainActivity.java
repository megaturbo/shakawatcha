package ch.hearc.android.shakawatcha.activities;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ch.hearc.android.shakawatcha.R;
import ch.hearc.android.shakawatcha.fragments.FragmentSearch;
import ch.hearc.android.shakawatcha.fragments.FragmentTitle;
import ch.hearc.android.shakawatcha.fragments.movies.FragmentMovie;
import ch.hearc.android.shakawatcha.objects.Movie;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Movie> movies = new ArrayList<>();

    private FragmentTitle fragmentTitle = new FragmentTitle();
    private FragmentSearch fragmentSearch = new FragmentSearch();
    private FragmentMovie fragmentMovie = new FragmentMovie();

    private static final String TAG_TITLE = "TITLE";
    private static final String TAG_SEARCH = "SEARCH";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showFragment(TAG_TITLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                onQueryTextChange(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                if (query.length() > 2) {
                    showFragment(TAG_SEARCH);
                    searchMovie(query);
                } else {
                    showFragment(TAG_TITLE);
                }
                return false;
            }
        });

        return true;
    }

    private void searchMovie(String query) {

        if (query.length() == 0) {
            return;
        }

        // TMDB Request
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        StringBuilder queryUrl = new StringBuilder("http://api.themoviedb.org/3/search/movie");
            queryUrl.append("?api_key=");
        queryUrl.append(Movie.API_KEY);
        queryUrl.append("&query=");
        queryUrl.append(query.replace(" ", "+"));

        StringRequest stringRequest = new StringRequest(Request.Method.GET, queryUrl.toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    processResponse(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("JEEZ", "Volley Error");
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void processResponse(String response) throws JSONException {

        JSONObject responseJSON = new JSONObject(response);
        JSONArray resultsJSON = responseJSON.getJSONArray(Movie.TAG_RESULTS);

        movies.clear();

        int resultsLength = resultsJSON.length();
        for (int i = 0; i < resultsLength; i++) {
            movies.add(new Movie(resultsJSON.getJSONObject(i)));
        }

        fragmentSearch.updateMovies(movies);
    }



    /**
     * Attach a new fragment to the view and detach the old one
     * @param TAG_NEW The new fragment to attach
     */
    private void showFragment(String TAG_NEW) {
        final FragmentManager fm = getSupportFragmentManager();
        final FragmentTransaction ft = fm.beginTransaction();

        if (TAG_NEW == TAG_TITLE) {
            ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            ft.replace(R.id.framelayout_main, fragmentTitle);
        } else if (TAG_NEW == TAG_SEARCH){
            ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            ft.replace(R.id.framelayout_main, fragmentSearch);
        }

        // Add to back stack
        ft.addToBackStack(null);

        // Commit changes.
        ft.commit();
    }

    public void showMovie(int id){

        fragmentMovie.setMovie(id);

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.framelayout_main, fragmentMovie)
                .commit();
    }
}
