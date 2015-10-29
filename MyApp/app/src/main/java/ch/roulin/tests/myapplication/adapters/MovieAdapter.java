package ch.roulin.tests.myapplication.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ch.roulin.tests.myapplication.R;
import ch.roulin.tests.myapplication.objects.Movie;

/**
 * Created by thomas.roulin on 11.10.2015.
 */
public class MovieAdapter extends ArrayAdapter<Movie> {

    Context context;
    int resource;
    ArrayList<Movie> movies;

    public MovieAdapter(Context context, int resource, ArrayList<Movie> movies) {
        super(context, resource, movies);

        this.context = context;
        this.resource = resource;
        this.movies = movies;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        MovieHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(resource, parent, false);

            holder = new MovieHolder();
            holder.posterIV = (ImageView) row.findViewById(R.id.list_item_movie_poster);
            holder.titleTV = (TextView) row.findViewById(R.id.list_item_movie_title);
            holder.directorTV = (TextView) row.findViewById(R.id.list_item_movie_director);

            row.setTag(holder);
        }else{
            holder = (MovieHolder)row.getTag();
        }


        final Movie m = movies.get(position);
        Picasso.with(context).load("http://image.tmdb.org/t/p/w92/" + m.getPosterPath()).into(holder.posterIV);
        holder.titleTV.setText(m.getTitle());
        holder.directorTV.setText(m.getReleaseYear());

        return row;
    }

    static class MovieHolder {
        ImageView posterIV;
        TextView titleTV;
        TextView directorTV;
    }
}
