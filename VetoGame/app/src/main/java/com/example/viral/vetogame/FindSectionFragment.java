package com.example.viral.vetogame;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import api.RestClient;
import api.model.GameResponse;
import api.model.SuggestionResponse;
import api.model.YelpResponse;
import api.model.YelpSuggestionResponse;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Viral on 3/5/2015.
 */
public class FindSectionFragment extends Fragment implements SearchView.OnQueryTextListener {

    ArrayList<Suggestion> suggestions = new ArrayList<Suggestion>();
    private SuggestionAdapter adapter;
    private ListView suggestionList;
    private int radius;
    private String centerText;
    private LocationSectionFragment locationFragment;
    private SearchView searchView;
    private RestClient restClient;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_section_find, container, false);

        String tabLocation = ((NewSuggestion)getActivity()).getTabFragmentLocate();
        locationFragment = (LocationSectionFragment) getActivity().getSupportFragmentManager().findFragmentByTag(tabLocation);
        //radius = Integer.valueOf(locationFragment.getRadiusText().getText().toString());
        //centerText = locationFragment.getRadiusText().getText().toString();

        //Bundle args = getArguments();
        //((TextView) rootView.findViewById(android.R.id.text1)).setText(
        //getString(R.string.dummy_section_text, args.getInt(ARG_SECTION_NUMBER)));
        setHasOptionsMenu(true);

        ((NewSuggestion)getActivity()).setTabFragmentFind(getTag());

        suggestionList = (ListView) rootView.findViewById(R.id.suggestion_list);
        TextView emptyText = (TextView)rootView.findViewById(R.id.emptySuggestionsView);
        suggestionList.setEmptyView(emptyText);

        /*suggestions.add(new Suggestion("The Muffin Bakery","2.2",5,0.5));
        suggestions.add(new Suggestion("The Swamp","2.2",4,4.5));
        suggestions.add(new Suggestion("Good Burger","2.2",3,3));
        suggestions.add(new Suggestion("Honker Burger","2.2",2,2.5));
        suggestions.add(new Suggestion("The Shore Shack","2.2",4,3.9));
        suggestions.add(new Suggestion("Krusty Burger","2.2",3,1.6));
        suggestions.add(new Suggestion("Los Pollos Hermanos","2.2",3,2));
        suggestions.add(new Suggestion("Krusty Krab","2.2",1,5));*/

        adapter = new SuggestionAdapter(getActivity(),suggestions);
        suggestionList.setAdapter(adapter);
        /*while(locationFragment.getCurrentLoc()==null){
            if(locationFragment.getCurrentLoc()!=null){
                findSuggestions();
            }
        }*/
        //findSuggestions();
        return rootView;
    }

    public SuggestionAdapter getAdapter() {
        return adapter;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_new_suggestion, menu);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        //searchView = (SearchView) findViewById(R.id.search_invite_people);
        searchView = (SearchView) menu.findItem(R.id.search_suggestion).getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //System.out.println("pushed");
        if (id == R.id.action_save_suggestion) {
            //Toast.makeText(getActivity(), "clicked",Toast.LENGTH_SHORT).show();
            //radius = locationFragment.getRadius();
            //centerText = locationFragment.getCenterText().getText().toString();
            Intent intent = new Intent(getActivity(),
                    NewGame.class);
            radius = locationFragment.getRadius();
            centerText = locationFragment.getEventZipCode();
            intent.putExtra("radius", radius);
            intent.putExtra("center", centerText);
            intent.putExtra("suggestion",adapter.getSelected());
            getActivity().setResult(getActivity().RESULT_OK, intent);
            getActivity().finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextChange(String newText)
    {
        adapter.getFilter().filter(newText);
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query)
    {
        return false;
    }

    public void findSuggestions(){
        restClient = new RestClient();
        //System.out.println("YELP retro " + locationFragment.getEventZipCode() + " rad " + milesToMeters(locationFragment.getRadius()) + " food");
        restClient.getSuggestionInfo().getYelpSuggestionInitial(locationFragment.getEventZipCode(),
                ""+milesToMeters(locationFragment.getRadius()),"food", new Callback<YelpResponse>(){

            @Override
            public void success(YelpResponse suggestionResponses, Response response) {
                //System.out.println("YELP\n\n\n\n\n"+ suggestionResponses.toString() +"\n\n\n\n\n");
                //System.out.println("YELP total "+suggestionResponses.getTotal());
                //System.out.println("YELP businesses "+suggestionResponses.getBusinesses());
                List<YelpSuggestionResponse> suggestionReps = suggestionResponses.getBusinesses();
                //GoogleMap map = locationFragment.getMap();
                for(YelpSuggestionResponse rep:suggestionReps){
                    //System.out.println("Yelp name: "+rep.getSuggestionName());
                    //System.out.println("Yelp loc: " + rep.getLocation().toString());
                    //System.out.println("Yelp rating: "+rep.getRatingImg());
                    Suggestion newSuggestion = new Suggestion(rep.getSuggestionName());
                    newSuggestion.setLocation_string(rep.getLocation().toString());
                    newSuggestion.setImage(rep.getImage());
                    newSuggestion.setMobileURL(rep.getMobileURL());
                    newSuggestion.setRatingImg(rep.getRatingImg());
                    newSuggestion.setNumReviews(rep.getReviewCount());

                    /*MarkerOptions eventMark = new MarkerOptions()
                            .position(locationFragment.getLatLngFromZipCode(rep.getLocation().getPostalCode()))
                            .title(rep.getSuggestionName());
                    map.addMarker(eventMark);*/

                    adapter.addSuggestion(newSuggestion);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.i("Error ", error.getMessage());
            }
        });
    }

    public float milesToMeters(float miles){
        return (miles*1609.34f);
    }

    /*public float distanceBetweenLatLong(String location1, String location2){
        String [] location1Vals = location1.split(",");
        String [] location2Vals = location2.split(",");

        double lat1 = Double.valueOf(location1Vals[0]);
        double lon1 = Double.valueOf(location1Vals[1]);
        double lat2 = Double.valueOf(location2Vals[0]);
        double lon2 = Double.valueOf(location2Vals[1]);

        Location loc1 = new Location("");
        loc1.setLatitude(lat1);
        loc1.setLongitude(lon1);

        Location loc2 = new Location("");
        loc2.setLatitude(lat2);
        loc2.setLongitude(lon2);

        float distanceInMeters = loc1.distanceTo(loc2);

        distanceInMeters = metersToMiles(distanceInMeters);

        return distanceInMeters;
    }

    public float metersToMiles(float meters){
        return (meters/1609.34f);
    }

    /*public static Drawable LoadImageFromWeb(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }*/
}
