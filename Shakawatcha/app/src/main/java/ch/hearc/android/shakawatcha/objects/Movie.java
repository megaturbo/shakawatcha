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

import java.util.ArrayList;
import java.util.List;

import ch.hearc.android.shakawatcha.objects.Person.Actor;
import ch.hearc.android.shakawatcha.objects.Person.Crew;

/**
 * Created by thomas.roulin on 10.10.2015.
 */
public class Movie {

    /**
     * Constants used for JSON process
     */
    public static final String API_KEY = "2bd0c05a0619072ececfe8b45fed5e9d";
    public static final String TAG_ID = "id";
    public static final String TAG_TITLE = "title";
    public static final String TAG_RELEASE_DATE = "release_date";
    public static final String TAG_POSTER_PATH = "poster_path";
    public static final String TAG_VOTE_AVERAGE = "vote_average";
    public static final String TAG_RESULTS = "results";
    public static final String TAG_CAST = "cast";
    public static final String TAG_CREW = "crew";
    public static final String TAG_BACKDROPS = "backdrops";
    public static final String TAG_FILEPATH = "file_path";

    /**
     * Movie attributes
     */
    private final int id;
    private final String title;
    private final String releaseDate;
    private final String posterPath;
    private final double voteAverage;

    private List<Actor> cast;
    private List<Crew> crew;


    public Movie(JSONObject movieJSON) throws JSONException {
        this.id = movieJSON.getInt(TAG_ID);
        this.title = movieJSON.getString(TAG_TITLE);
        this.releaseDate = movieJSON.getString(TAG_RELEASE_DATE);
        this.posterPath = movieJSON.getString(TAG_POSTER_PATH);
        this.voteAverage = movieJSON.getDouble(TAG_VOTE_AVERAGE);
        this.cast = new ArrayList<>();
        this.crew = new ArrayList<>();
    }

    /**
     * SETTERS
     */

    public void setCast(JSONArray castJSON) throws JSONException {
        for (int i = 0; i < castJSON.length(); i++) {
            this.cast.add(new Actor(castJSON.getJSONObject(i)));
        }
    }

    public void setCrew(JSONArray crewJSON) throws JSONException {
        for (int i = 0; i < crewJSON.length(); i++) {
            this.crew.add(new Crew(crewJSON.getJSONObject(i)));
        }
    }

    /**
     * toString
     */
    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", id=" + id +
                '}';
    }

    /**
     * GETTERS
     */
    public Crew getCrewMember(String job) {
        for (Crew c : this.crew) {
            if (c.getJob().equals(job)) {
                return c;
            }
        }
        return null;
    }

    public List<Crew> getCrew() {
        return crew;
    }

    public List<Actor> getCast() {
        return cast;
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
