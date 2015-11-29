package ch.hearc.android.shakawatcha.objects.Person;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by thomas.roulin on 29.11.2015.
 */
public class Crew {

    public static final String TAG_ID = "id";
    public static final String TAG_DEPARTMENT = "department";
    public static final String TAG_JOB = "job";
    public static final String TAG_NAME = "name";
    public static final String TAG_PROFILE_PATH = "profile_path";

    public static final String TAG_DEPARTMENT_DIRECTING = "Directing";
    public static final String TAG_DEPARTMENT_WRITING = "Writing";


    private final int id;
    private final String department;
    private final String job;
    private final String name;
    private final String profilePath;

    public Crew(JSONObject actorJSON) throws JSONException {
        this.id = actorJSON.getInt(TAG_ID);
        this.department = actorJSON.getString(TAG_DEPARTMENT);
        this.job = actorJSON.getString(TAG_JOB);
        this.name = actorJSON.getString(TAG_NAME);
        this.profilePath = actorJSON.getString(TAG_PROFILE_PATH);
    }

    public int getId() {
        return id;
    }

    public String getDepartment() {
        return department;
    }

    public String getJob() {
        return job;
    }

    public String getName() {
        return name;
    }

    public String getProfilePath() {
        return profilePath;
    }
}
