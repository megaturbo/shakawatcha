package ch.hearc.android.shakawatcha.objects;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by thomas.roulin on 10.10.2015.
 */
public class Movie {

    public static final String API_KEY = "2bd0c05a0619072ececfe8b45fed5e9d";
    public static final String TAG_ID = "id";
    public static final String TAG_TITLE = "title";
    public static final String TAG_RELEASE_DATE = "release_date";
    public static final String TAG_POSTER_PATH = "poster_path";
    public static final String TAG_VOTE_AVERAGE = "vote_average";
    public static final String TAG_JOB = "job";
    public static final String TAG_NAME = "name";
    public static final String TAG_RESULTS = "results";

    private final int id;
    private final String title;
    private final String releaseDate;
    private final String posterPath;
    private final double voteAverage;

    public Movie(JSONObject movieJSON) throws JSONException {
        this.id = movieJSON.getInt(TAG_ID);
        this.title = movieJSON.getString(TAG_TITLE);
        this.releaseDate = movieJSON.getString(TAG_RELEASE_DATE);
        this.posterPath = movieJSON.getString(TAG_POSTER_PATH);
        this.voteAverage = movieJSON.getDouble(TAG_VOTE_AVERAGE);
    }

    @Override
    public String toString() {
        return title + "  - (" + releaseDate.split("-")[0] + ")";
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getReleaseYear() {
        return releaseDate.split("-")[0];
    }
}
