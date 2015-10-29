package ch.roulin.tests.myapplication;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

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

import ch.roulin.tests.myapplication.fragments.SearchListFragment;
import ch.roulin.tests.myapplication.fragments.TitleFragment;
import ch.roulin.tests.myapplication.objects.Movie;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ArrayList<Movie> movies = new ArrayList<>();

    private boolean isSearching = false;
    private TitleFragment mTitleFragment = new TitleFragment();
    private SearchListFragment mSearchListFragment = new SearchListFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showSearchFragment(false);

        // Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        // Navigation View
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    private void showSearchFragment(boolean isSearching) {
        final FragmentManager fm = getSupportFragmentManager();
        final FragmentTransaction ft = fm.beginTransaction();

        if (isSearching) {
            ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            ft.replace(R.id.frame_layout_search, mSearchListFragment);
            this.isSearching = true;
        } else {
            ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            ft.replace(R.id.frame_layout_search, mTitleFragment);
            this.isSearching = false;
        }

        ft.addToBackStack(null);

        // Commit changes.
        ft.commit();
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

        mSearchListFragment.updateMovies(movies);
    }


    /**************
     * ANDROID
     *************/
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            showSearchFragment(false);
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                onQueryTextChange(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() > 2) {
                    showSearchFragment(true);
                    searchMovie(newText);
                } else if (isSearching) {
                    showSearchFragment(false);
                }
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camara) {

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
