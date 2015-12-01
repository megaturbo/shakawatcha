package ch.hearc.android.shakawatcha.objects.Person;

import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by thomas.roulin on 29.11.2015.
 */
public class Actor {

    public static final String TAG_ID = "id";
    public static final String TAG_CHARACTER = "character";
    public static final String TAG_NAME = "name";
    public static final String TAG_PROFILE_PATH = "profile_path";

    private final int id;
    private final String character;
    private final String name;
    private final String profilePath;

    /**
     * Looks like an Actor, a person. Working in movie, y'know.
     *
     * @param actorJSON
     * @throws JSONException
     */
    public Actor(JSONObject actorJSON) throws JSONException {
        this.id = actorJSON.getInt(TAG_ID);
        this.character = actorJSON.getString(TAG_CHARACTER);
        this.name = actorJSON.getString(TAG_NAME);
        this.profilePath = actorJSON.getString(TAG_PROFILE_PATH);
    }

    public int getId() {
        return id;
    }

    public String getCharacter() {
        return character;
    }

    public String getName() {
        return name;
    }

    public String getProfilePath() {
        return profilePath;
    }
}
