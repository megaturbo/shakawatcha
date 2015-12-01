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

    /**
     * Crew object is for a Crew member
     *
     * @param crewJSON The TMdB JSON Crew member
     * @throws JSONException Make someone else works for me
     */
    public Crew(JSONObject crewJSON) throws JSONException {
        this.id = crewJSON.getInt(TAG_ID);
        this.department = crewJSON.getString(TAG_DEPARTMENT);
        this.job = crewJSON.getString(TAG_JOB);
        this.name = crewJSON.getString(TAG_NAME);
        this.profilePath = crewJSON.getString(TAG_PROFILE_PATH);
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
