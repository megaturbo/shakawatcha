package ch.hearc.android.shakawatcha.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ch.hearc.android.shakawatcha.R;
import ch.hearc.android.shakawatcha.activities.MainActivity;

/**
 * Created by thomas.roulin on 05.11.2015.
 */
public class FragmentHome extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_title, container, false);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MainActivity) context).onSectionAttached(
                getArguments().getString("TITLE"));
    }

    public static FragmentHome newInstance(String title) {
        FragmentHome fragmentHome = new FragmentHome();

        Bundle bundle = new Bundle();
        bundle.putString("TITLE", title);
        fragmentHome.setArguments(bundle);

        return fragmentHome;
    }
}
