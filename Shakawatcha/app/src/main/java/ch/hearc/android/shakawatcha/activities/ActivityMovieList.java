package ch.hearc.android.shakawatcha.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import ch.hearc.android.shakawatcha.R;
import ch.hearc.android.shakawatcha.adapters.MovieAdapter;
import ch.hearc.android.shakawatcha.objects.utils.MovieList;
import ch.hearc.android.shakawatcha.objects.utils.UserLists;

/**
 * Created by ikonoklast on 14.01.2016.
 */
public class ActivityMovieList extends AppCompatActivity {

    public static final String ARG_MOVIE_LIST_ID = "ARG_MOVIE_LIST_ID";

    private MovieAdapter adapter;
    private ListView listView;
    private MovieList movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_movielist);

        int movieListId = getIntent().getIntExtra(ARG_MOVIE_LIST_ID, 0);
        UserLists userLists = UserLists.retrieve(this);

        movieList = userLists.get(movieListId);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(movieList.getName());

        adapter = new MovieAdapter(this,
                R.layout.fragment_search_item, movieList.getMovies());

        listView = (ListView) findViewById(R.id.movielist_listview);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                PopupMenu popup = new PopupMenu(ActivityMovieList.this, view);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.actions, popup.getMenu());
                popup.show();
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu_delete_movie:
//                UserLists.removeFromList()
                return true;
            case R.id.action_remove_list:

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                UserLists.remove(movieList, ActivityMovieList.this);
                                finish();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movielist, menu);
        return true;
    }
}
