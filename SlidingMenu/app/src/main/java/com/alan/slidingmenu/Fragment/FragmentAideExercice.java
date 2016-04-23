package com.alan.slidingmenu.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alan.slidingmenu.R;

/**
 * Created by alanmocaer on 01/02/16.
 */
public class FragmentAideExercice extends Fragment {

    @Override
    public void onActivityCreated(Bundle b) {
        super.onActivityCreated(b);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.activity_aide_exercice, container, false);
        Bundle args = getArguments();
       /* ((TextView) rootView.findViewById(R.id.TextFragment1)).setText(
                Integer.toString(args.getInt(ARG_OBJECT)));*/
        return rootView;

    }
}
