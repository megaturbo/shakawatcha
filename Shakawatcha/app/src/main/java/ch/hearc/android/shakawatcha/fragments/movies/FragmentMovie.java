package ch.hearc.android.shakawatcha.fragments.movies;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import ch.hearc.android.shakawatcha.R;
import ch.hearc.android.shakawatcha.fragments.DialogFragmentNewList;
import ch.hearc.android.shakawatcha.fragments.DialogFragmentUserLists;
import ch.hearc.android.shakawatcha.activities.MainActivity;
import ch.hearc.android.shakawatcha.objects.Movie;
import ch.hearc.android.shakawatcha.objects.Person.Actor;
import ch.hearc.android.shakawatcha.objects.Person.Crew;
import ch.hearc.android.shakawatcha.objects.utils.MovieList;
import ch.hearc.android.shakawatcha.objects.utils.SimpleMovie;
import ch.hearc.android.shakawatcha.objects.utils.UserLists;

/**
 * Created by thomas.roulin on 12.11.2015.
 */
public class FragmentMovie extends Fragment {

    private static final int DIALOG_FRAGMENT = 2;

    private ImageView ivBackdrop;
    private ImageView ivPoster;
    private TextView tvTitle;
    private TextView tvReleaseDate;
    private TextView tvDirector;
    private TextView tvWriter;
    private TextView tvCast;
    private TextView tvOverview;
    private Button buttonAddToList;

    private Movie movie;
    private String movieTitle;
    private int movieId;

    public static final String ARG_TITLE = "TITLE";
    public static final String ARG_ID = "ID";
    public static final String NO_DESCRIPTION = "No Oveview.";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle args = getArguments();
        movieTitle = args.getString(ARG_TITLE);
        movieId = args.getInt(ARG_ID);

        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ivBackdrop = (ImageView) getActivity().findViewById(R.id.movie_backdrop);
        ivPoster = (ImageView) getActivity().findViewById(R.id.movie_poster);
        tvTitle = (TextView) getActivity().findViewById(R.id.movie_title);
        tvReleaseDate = (TextView) getActivity().findViewById(R.id.movie_release_date);
        tvDirector = (TextView) getActivity().findViewById(R.id.movie_director);
        tvWriter = (TextView) getActivity().findViewById(R.id.movie_writer);
        tvCast = (TextView) getActivity().findViewById(R.id.movie_cast);
        tvOverview = (TextView) getActivity().findViewById(R.id.movie_overview);
        buttonAddToList = (Button) getActivity().findViewById(R.id.movie_button_addtolist);

        buttonAddToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragmentUserLists dialog = new DialogFragmentUserLists();
                dialog.setTargetFragment(FragmentMovie.this, DIALOG_FRAGMENT);
                dialog.show(getFragmentManager(), getResources().getString(R.string.add_to_list));

            }
        });

        tvTitle.setText(this.movieTitle);

        init();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case DIALOG_FRAGMENT:
                UserLists userLists = UserLists.retrieve(getActivity());
                MovieList list = userLists.getLists().get(data.getIntExtra("LIST_ID", 0));
                UserLists.addToList(movie, list.getName(), getActivity());

                Toast.makeText(getContext(), movie.getTitle() + " successfuly added to " + list.getName(), Toast.LENGTH_SHORT).show();
        }
    }

    public static FragmentMovie newInstance(String movieTitle, int movieId) {
        FragmentMovie fragmentMovie = new FragmentMovie();

        Bundle args = new Bundle();
        args.putString(ARG_TITLE, movieTitle);
        args.putInt(ARG_ID, movieId);
        fragmentMovie.setArguments(args);

        return fragmentMovie;
    }

    private void init() {
        requestMovie(this.movieId);
        requestMovieBackdrop(this.movieId);
    }

    /**
     * REQUEST
     */

    private void requestMovieCredits(int movieId) {
        System.out.println("========== BEFORE THE QUEUE ==========");
        System.out.println(getActivity().toString());
        System.out.println("==========  AFTER THE QUEUE ==========");

        RequestQueue queue = Volley.newRequestQueue(getActivity());


        String query = "http://api.themoviedb.org/3/movie/" + movieId + "/credits?api_key=" + Movie.API_KEY;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, query, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    showCredits(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("JEEZ", "Volley Error in FragmentMovie/requestMovieCredits");
            }
        });

        queue.add(stringRequest);
    }


    private void requestMovie(int id) {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String query = "http://api.themoviedb.org/3/movie/" + id + "?api_key=" + Movie.API_KEY;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, query, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    showMovie(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ((MainActivity) getActivity()).searchRandomMovie();
                Log.d("JEEZ", "Volley Error in FragmentMovie/requestMovie");
            }
        });

        queue.add(stringRequest);
    }

    private void requestMovieBackdrop(int id) {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String query = "http://api.themoviedb.org/3/movie/" + id + "/images?api_key=" + Movie.API_KEY;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, query, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    showBackdrop(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("JEEZ", "Volley Error in FragmentMovie/requestMovieBackdrop");
            }
        });

        queue.add(stringRequest);
    }

    /**
     * SHOW
     */

    private void showCredits(String response) throws JSONException {
        JSONObject creditsJSON = new JSONObject(response);

        JSONArray castJSON = creditsJSON.getJSONArray(Movie.TAG_CAST);
        JSONArray crewJSON = creditsJSON.getJSONArray(Movie.TAG_CREW);

        List<Actor> cast = movie.setCast(castJSON);
        movie.setCrew(crewJSON);

        List<Crew> directors = movie.getDepartment(Crew.TAG_DEPARTMENT_DIRECTING);
        List<Crew> writers = movie.getDepartment(Crew.TAG_DEPARTMENT_WRITING);

        if (directors.size() > 0) {
            tvDirector.setText(Html.fromHtml("<b>Director: </b>" + directors.get(0).getName()));
        }

        if (writers.size() > 0) {
            tvWriter.setText(Html.fromHtml("<b>Writer: </b>" + writers.get(0).getName()));
        }

        //TODO : BUGFIX ; WE DON'T KNOW WHY
        if (cast.size() > 2) {
            tvCast.setText(Html.fromHtml("<b>Stars: </b>" + cast.get(0).getName() + ", " + cast.get(1).getName() + ", " + cast.get(2).getName()));
        }

    }

    private void showBackdrop(String response) throws JSONException {
        JSONObject imagesJSON = new JSONObject(response);
        JSONArray backdropsJSON = imagesJSON.getJSONArray(Movie.TAG_BACKDROPS);
        String backdropPath = backdropsJSON.getJSONObject(0).getString(Movie.TAG_FILEPATH);

        Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w1280" + backdropPath).into(ivBackdrop);
    }

    private void showMovie(String response) throws JSONException {
        movie = new Movie(new JSONObject(response));
        requestMovieCredits(this.movieId);

        tvTitle.setText(movie.getTitle());
        tvReleaseDate.setText(Html.fromHtml("<b>Release: </b>" + movie.getReleaseDate()));

        tvOverview.setText(movie.getOverview() != "null" ? movie.getOverview() : NO_DESCRIPTION);


        String posterPath = movie.getPosterPath();
        Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w780/" + posterPath).into(ivPoster);
    }
}
