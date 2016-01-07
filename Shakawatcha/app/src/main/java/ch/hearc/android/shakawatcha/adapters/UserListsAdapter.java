package ch.hearc.android.shakawatcha.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ch.hearc.android.shakawatcha.R;
import ch.hearc.android.shakawatcha.objects.Movie;
import ch.hearc.android.shakawatcha.objects.utils.MovieList;


/**
 * Created by thomas.roulin on 11.10.2015.
 */
public class UserListsAdapter extends ArrayAdapter<MovieList> {

    Context context;
    int resource;
    ArrayList<MovieList> movieLists;

    public UserListsAdapter(Context context, int resource, ArrayList<MovieList> movieLists) {
        super(context, resource, movieLists);

        this.context = context;
        this.resource = resource;
        this.movieLists = movieLists;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        MovieListHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(resource, parent, false);

            holder = new MovieListHolder();
            holder.listNameTV = (TextView) row.findViewById(R.id.userlists_list_item);
            holder.buttonDelete = (Button)row.findViewById(R.id.userlists_button_delete);

            row.setTag(holder);
        } else {
            holder = (MovieListHolder) row.getTag();
        }


        final MovieList ml = movieLists.get(position);
        holder.listNameTV.setText(ml.getName() + " (" + ml.getMovies().size() + " movies)");
        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                Log.d("YOLO", "Delete " + ml.getName());
            }
        });

        return row;
    }

    static class MovieListHolder {
        TextView listNameTV;
        Button buttonDelete;
    }
}
