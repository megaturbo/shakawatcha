package ch.hearc.android.shakawatcha.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import ch.hearc.android.shakawatcha.R;
import ch.hearc.android.shakawatcha.adapters.UserListsAdapter;
import ch.hearc.android.shakawatcha.objects.utils.MovieList;
import ch.hearc.android.shakawatcha.objects.utils.SimpleMovie;
import ch.hearc.android.shakawatcha.objects.utils.UserLists;

/**
 * Created by ikonoklast on 07.01.2016.
 */
public class DialogFragmentUserLists extends DialogFragment {

    private ListView listView;

    public static final int MOVIE_ADDED = 1;
    public static final int MOVIE_NOT_ADDED = 2;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_userlists, null);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view);
        listView = (ListView) view.findViewById(R.id.dialog_userlists_listview);
        refreshList();

        return builder.create();
    }

    public void refreshList() {
        // Get user lists
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        final UserLists userLists = UserLists.retrieve(sharedPreferences);

        // Create the adapter with user lists
        UserListsAdapter adapter = new UserListsAdapter(getActivity(), R.layout.fragment_userlists_item_no_delete, userLists.getLists(), null);

        // set the adapter
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent();
                i.putExtra("LIST_ID", position);
                getActivity().setIntent(i);
                getTargetFragment().onActivityResult(getTargetRequestCode(), MOVIE_ADDED, getActivity().getIntent());
                DialogFragmentUserLists.this.getDialog().cancel();
            }
        });
    }
}
