package com.example.viral.vetogame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Viral on 3/5/2015.
 */
public class FindSectionFragment extends Fragment {

    ArrayList<Suggestion> suggestions = new ArrayList<Suggestion>();
    private SuggestionAdapter adapter;
    private ListView suggestionList;
    private int radius;
    private String centerText;
    private LocationSectionFragment locationFragment;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_section_find, container, false);

        String tabLocation = ((NewSuggestion)getActivity()).getTabFragmentL();
        locationFragment = (LocationSectionFragment) getActivity().getSupportFragmentManager().findFragmentByTag(tabLocation);

        //Bundle args = getArguments();
        //((TextView) rootView.findViewById(android.R.id.text1)).setText(
        //getString(R.string.dummy_section_text, args.getInt(ARG_SECTION_NUMBER)));
        setHasOptionsMenu(true);

        suggestionList = (ListView) rootView.findViewById(R.id.suggestion_list);
        TextView emptyText = (TextView)rootView.findViewById(R.id.emptySuggestionsView);
        suggestionList.setEmptyView(emptyText);

        suggestions.add(new Suggestion("Muffin Shop","2.2",5,1.5));
        suggestions.add(new Suggestion("The Swamp","2.2",4,10));
        suggestions.add(new Suggestion("Good Burger","2.2",3,15));
        suggestions.add(new Suggestion("Honker Burger","2.2",2,11.5));
        suggestions.add(new Suggestion("The Shore Shack","2.2",4,45));
        suggestions.add(new Suggestion("Krusty Burger","2.2",3,22.3));
        suggestions.add(new Suggestion("Los Pollos Hermanos","2.2",3,5.2));
        suggestions.add(new Suggestion("Krusty Krab","2.2",1,50));

        adapter = new SuggestionAdapter(getActivity(),suggestions);
        suggestionList.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_new_suggestion, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        System.out.println("pushed");
        if (id == R.id.action_save_suggestion) {
            radius = locationFragment.getRadius();
            centerText = locationFragment.getCenterText().getText().toString();
            Intent intent = new Intent(getActivity(),
                    NewGame.class);
            intent.putExtra("radius", radius);
            intent.putExtra("center", centerText);
            getActivity().setResult(getActivity().RESULT_OK, intent);
            getActivity().finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
