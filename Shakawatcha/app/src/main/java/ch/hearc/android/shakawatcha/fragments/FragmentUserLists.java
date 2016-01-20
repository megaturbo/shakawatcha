package ch.hearc.android.shakawatcha.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;

import java.util.ArrayList;

import ch.hearc.android.shakawatcha.R;
import ch.hearc.android.shakawatcha.activities.ActivityMovieList;
import ch.hearc.android.shakawatcha.activities.MainActivity;
import ch.hearc.android.shakawatcha.adapters.UserListsAdapter;
import ch.hearc.android.shakawatcha.objects.utils.MovieList;
import ch.hearc.android.shakawatcha.objects.utils.UserLists;

/**
 * Created by thomas.roulin on 30.11.2015.
 */
public class FragmentUserLists extends Fragment {

    private static final int DIALOG_FRAGMENT = 1;
    private ListView listView;
    private ArrayList<MovieList> userLists;
    private int selectedListIndex = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_userlists, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listView = (ListView) getActivity().findViewById(R.id.userlists_listview);

        // Menu on long click for delete list
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                selectedListIndex = position;
                PopupMenu popupMenu = new PopupMenu(getActivity(), view);
                MenuInflater inflater = getActivity().getMenuInflater();
                inflater.inflate(R.menu.movielist, popupMenu.getMenu());
                popupMenu.show();
                return true;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), ActivityMovieList.class);
                i.putExtra(ActivityMovieList.ARG_MOVIE_LIST_ID, position);
                startActivity(i);
            }
        });

        refreshList();

        Button buttonCreate = (Button) getActivity().findViewById(R.id.userlists_button_create);
        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragmentNewList dialog = new DialogFragmentNewList();
                dialog.setTargetFragment(FragmentUserLists.this, DIALOG_FRAGMENT);
                dialog.show(getFragmentManager(), getResources().getString(R.string.create_new_list));
            }
        });

    }

    public void refreshList() {
        // Get user lists
        userLists = UserLists.retrieve(getActivity()).getLists();

        // Create the adapter with user lists
        UserListsAdapter adapter = new UserListsAdapter(getActivity(), R.layout.fragment_userlists_item, userLists, FragmentUserLists.this);

        // Create ListView and set the adapter
        listView.setAdapter(adapter);
    }

    public void createNewList(String listName) {
        UserLists userLists = UserLists.retrieve(getActivity());
        userLists.add(new MovieList(listName));
        UserLists.save(userLists, getActivity());

        refreshList();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case DIALOG_FRAGMENT:
                if (resultCode == Activity.RESULT_OK) {
                    String listName = data.getStringExtra("LIST_NAME");
                    createNewList(listName);
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    Log.d("YOLO", "CANCEL");
                }
                break;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        refreshList();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MainActivity) context).onSectionAttached(
                getArguments().getString("TITLE"));
    }

    public static FragmentUserLists newInstance(String title) {
        FragmentUserLists fragmentUserLists = new FragmentUserLists();

        Bundle bundle = new Bundle();
        bundle.putString("TITLE", title);
        fragmentUserLists.setArguments(bundle);

        return fragmentUserLists;
    }
}
