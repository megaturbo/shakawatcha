package ch.hearc.android.shakawatcha.fragments;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ch.hearc.android.shakawatcha.R;
import ch.hearc.android.shakawatcha.objects.Movie;


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
            holder.posterIV = (ImageView) row.findViewById(R.id.search_item_poster);
            holder.titleTV = (TextView) row.findViewById(R.id.search_item_title);
            holder.yearIV = (TextView) row.findViewById(R.id.search_item_year);

            row.setTag(holder);
        }else{
            holder = (MovieHolder)row.getTag();
        }


        final Movie m = movies.get(position);
        Picasso.with(context).load("http://image.tmdb.org/t/p/w92/" + m.getPosterPath()).into(holder.posterIV);
        holder.titleTV.setText(m.getTitle());
        holder.yearIV.setText(m.getReleaseYear());

        return row;
    }

    static class MovieHolder {
        ImageView posterIV;
        TextView titleTV;
        TextView yearIV;
    }
}
