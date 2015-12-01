package ch.hearc.android.shakawatcha.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;

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
import ch.hearc.android.shakawatcha.fragments.FragmentNewList;
import ch.hearc.android.shakawatcha.fragments.FragmentSearch;
import ch.hearc.android.shakawatcha.fragments.FragmentHome;
import ch.hearc.android.shakawatcha.fragments.movies.FragmentMovie;
import ch.hearc.android.shakawatcha.fragments.navigation.NavigationDrawerFragment;
import ch.hearc.android.shakawatcha.objects.Movie;

public class MainActivity extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {


    /**
     * Contains the result of the TMDB query
     */
    private ArrayList<Movie> movieSearchResults = new ArrayList<>();

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private static final String TAG_HOME = "HOME";
    private static final String TAG_NEW_LIST = "NEWLIST";
    private static final String TAG_SEARCH = "SEARCH";
    private static final String TAG_MOVIE = "MOVIE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

        Fragment fragment;
        String tag;

        switch (position) {
            case 0:
                fragment = FragmentHome.newInstance("Shakawatcha");
                tag = TAG_HOME;
                break;
            case 1:
                fragment = FragmentNewList.newInstance("New list");
                tag = TAG_NEW_LIST;
                Log.d("YOLO", TAG_NEW_LIST);
                break;
            default:
                fragment = PlaceholderFragment.newInstance(position + 1);
                tag = "JEEZ";
        }
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment, tag)
                .commit();
    }

    public void onSectionAttached(String title) {
        mTitle = title;
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main_activity2, menu);
            restoreActionBar();


            SearchView searchView =
                    (SearchView) menu.findItem(R.id.search).getActionView();


            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    onQueryTextChange(query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String query) {
                    if (query.length() > 2) {
                        /**
                         * If the query is long enough, show SearchFragment and process the query
                         */
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.container, FragmentSearch.newInstance(), TAG_SEARCH)
                                .commit();
                        searchMovie(query);
                    } else {

                        /**
                         *
                         */
//                        FragmentMovie fragmentMovie = (FragmentMovie) getSupportFragmentManager().findFragmentByTag(TAG_MOVIE);
//                        if(!fragmentMovie.isVisible()){
//                            onNavigationDrawerItemSelected(mNavigationDrawerFragment.getCurrentSelectedPosition());
//                        }
                    }
                    return false;
                }
            });

            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     * TODO: Don't use this class anymore, remove it, burn it, bury it. And use our Fragments
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String TITLE = "TITLE";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putString(TITLE, "jeez" + String.valueOf(sectionNumber));
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main_activity, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
            Log.d("YOLO", "Section attached");
            ((MainActivity) context).onSectionAttached(getArguments().getString("TITLE"));
        }
    }

    /*****************************************************************
     * MOVIEEEEZZZZZZZZZZZZ
     * <p/>
     * ****************************************************************
     */
    private void processResponse(String response) throws JSONException {

        JSONObject responseJSON = new JSONObject(response);
        JSONArray resultsJSON = responseJSON.getJSONArray(Movie.TAG_RESULTS);

        movieSearchResults.clear();

        int resultsLength = resultsJSON.length();
        for (int i = 0; i < resultsLength; i++) {
            movieSearchResults.add(new Movie(resultsJSON.getJSONObject(i)));
        }

        /**
         *  Update the SearchFragment if visible with the new movieSearchResults
         */
        FragmentSearch fragmentSearch = (FragmentSearch) getSupportFragmentManager().findFragmentByTag(TAG_SEARCH);
        if (fragmentSearch.isVisible()) {
            fragmentSearch.updateMovies(movieSearchResults);
        }

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

    public void showMovie(String movieTitle, int movieId) {

        FragmentMovie fragmentMovie = FragmentMovie.newInstance(movieTitle, movieId);

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragmentMovie, TAG_MOVIE)
                .commit();
    }

}
