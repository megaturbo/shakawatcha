package ch.roulin.tests.myapplication.objects;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by thomas.roulin on 22.10.2015.
 */
public class MovieFactory {

    public static Movie createFromId(Context context, String movieId){
        RequestQueue queue = Volley.newRequestQueue(context);
        StringBuilder queryUrl = new StringBuilder("http://api.themoviedb.org/3/movie/" + movieId+"/");
        queryUrl.append("?api_key=");
        queryUrl.append(Movie.API_KEY);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, queryUrl.toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    return new Movie(new JSONObject(response));
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



}
