package ch.hearc.android.shakawatcha.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ch.hearc.android.shakawatcha.R;
import ch.hearc.android.shakawatcha.activities.MainActivity;

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
                //TODO Create MovieList
            }
        });
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
