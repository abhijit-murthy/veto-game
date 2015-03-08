package com.example.viral.vetogame;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Viral on 3/5/2015.
 */
public class FindSectionFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_section_find, container, false);
        //Bundle args = getArguments();
        //((TextView) rootView.findViewById(android.R.id.text1)).setText(
        //getString(R.string.dummy_section_text, args.getInt(ARG_SECTION_NUMBER)));
        return rootView;
    }
}
