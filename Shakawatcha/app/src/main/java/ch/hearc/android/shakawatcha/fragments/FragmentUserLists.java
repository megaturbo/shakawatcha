package ch.hearc.android.shakawatcha.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import ch.hearc.android.shakawatcha.R;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_userlists, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        refreshList();

        Button buttonCreate = (Button)getActivity().findViewById(R.id.userlists_button_create);
        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragmentNewList dialog = new DialogFragmentNewList();
                dialog.setTargetFragment(FragmentUserLists.this, DIALOG_FRAGMENT);
                dialog.show(getFragmentManager(), getResources().getString(R.string.create_new_list));
            }
        });

    }

    private void refreshList(){
        // Get user lists
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        UserLists userLists = UserLists.retrieve(sharedPreferences);

        // Create the adapter with user lists
        UserListsAdapter adapter = new UserListsAdapter(getActivity(), R.layout.fragment_userlists_item, userLists.getLists());

        // Create ListView and set the adapter
        listView = (ListView) getActivity().findViewById(R.id.userlists_listview);
        listView.setAdapter(adapter);
    }

    public void createNewList(String listName){
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        UserLists userLists = UserLists.retrieve(sharedPreferences);
        userLists.add(new MovieList(listName));
        UserLists.save(userLists, sharedPreferences);

        refreshList();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case DIALOG_FRAGMENT:
                if(resultCode == Activity.RESULT_OK){
                    String listName = data.getStringExtra("LIST_NAME");
                    createNewList(listName);
                }else if (resultCode == Activity.RESULT_CANCELED){
                    Log.d("YOLO", "CANCEL");
                }
                break;
        }
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
