package ch.hearc.android.shakawatcha.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ch.hearc.android.shakawatcha.R;
import ch.hearc.android.shakawatcha.fragments.FragmentUserLists;
import ch.hearc.android.shakawatcha.objects.utils.MovieList;
import ch.hearc.android.shakawatcha.objects.utils.UserLists;


/**
 * Created by thomas.roulin on 11.10.2015.
 */
public class UserListsAdapter extends ArrayAdapter<MovieList> {

    Context context;
    int resource;
    ArrayList<MovieList> movieLists;
    FragmentUserLists fragmentUserLists;

    public UserListsAdapter(Context context, int resource, ArrayList<MovieList> movieLists, FragmentUserLists fragmentUserLists) {
        super(context, resource, movieLists);

        this.context = context;
        this.resource = resource;
        this.movieLists = movieLists;
        this.fragmentUserLists = fragmentUserLists;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        MovieListHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(resource, parent, false);

            holder = new MovieListHolder();
            holder.listNameTV = (TextView) row.findViewById(R.id.userlists_list_item_listname);
            holder.listSizeTV = (TextView) row.findViewById(R.id.userlists_list_item_number_movies);
            holder.buttonDelete = (Button) row.findViewById(R.id.userlists_button_delete);

            row.setTag(holder);
        } else {
            holder = (MovieListHolder) row.getTag();
        }


        final MovieList ml = movieLists.get(position);
        holder.listNameTV.setText(ml.getName());
        holder.listSizeTV.setText(ml.getMovies().size() + " movies");

        // Delete listener
//        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        switch (which) {
//                            case DialogInterface.BUTTON_POSITIVE:
//                                UserLists.remove(ml, ((Activity) context).getPreferences(Context.MODE_PRIVATE));
//                                if (fragmentUserLists != null) {
//                                    fragmentUserLists.refreshList();
//                                }
//                                Toast.makeText(context, ml.getName() + " has been removed.", Toast.LENGTH_LONG).show();
//                                break;
//
//                            case DialogInterface.BUTTON_NEGATIVE:
//                                break;
//                        }
//                    }
//                };
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
//                        .setNegativeButton("No", dialogClickListener).show();
//
//            }
//        });

        return row;
    }

    static class MovieListHolder {
        TextView listNameTV;
        TextView listSizeTV;
        Button buttonDelete;
    }
}
