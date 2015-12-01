package ch.hearc.android.shakawatcha.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import ch.hearc.android.shakawatcha.R;
import ch.hearc.android.shakawatcha.activities.MainActivity;
import ch.hearc.android.shakawatcha.objects.utils.MovieList;
import ch.hearc.android.shakawatcha.objects.utils.UserLists;

/**
 * Created by thomas.roulin on 30.11.2015.
 */
public class FragmentNewList extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_newlist, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button buttonCreate = (Button)getActivity().findViewById(R.id.button_newlist_create);
        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText)getActivity().findViewById(R.id.newlist_edittext);

                SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
                ArrayList<MovieList> userLists = UserLists.retrieve(sharedPreferences);
                userLists.add(new MovieList(editText.getText().toString()));
                UserLists.save(userLists, sharedPreferences);
            }
        });

        TextView textView = (TextView)getActivity().findViewById(R.id.newlist_title);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MainActivity) context).onSectionAttached(
                getArguments().getString("TITLE"));
    }

    public static FragmentNewList newInstance(String title) {
        FragmentNewList fragmentNewList = new FragmentNewList();

        Bundle bundle = new Bundle();
        bundle.putString("TITLE", title);
        fragmentNewList.setArguments(bundle);

        return fragmentNewList;
    }
}
