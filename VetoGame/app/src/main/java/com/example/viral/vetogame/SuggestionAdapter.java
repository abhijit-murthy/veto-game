package com.example.viral.vetogame;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import org.apache.http.client.protocol.RequestAddCookies;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by eunkikim on 3/5/15.
 */
public class SuggestionAdapter extends ArrayAdapter<Suggestion> {
    private Context context;
    private HashMap<String,Suggestion> users = new HashMap<String,Suggestion>();
    private ArrayList<Suggestion> currentList;
    private String selected;

    // when it needs to show all people
    public SuggestionAdapter(Context context, ArrayList<Suggestion> resource) {
        super(context,R.layout.suggestion_adapter,resource);

        this.context = context;
        //System.out.println("test2 size: "+resource.size());
        for(Suggestion suggestion:resource){
            users.put(suggestion.getName(),suggestion);
        }
        currentList = resource;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = inflateIfRequired(convertView, position, parent);
        bind(getItem(position), convertView);
        return convertView;
    }

    public Suggestion getSelected() {
        return users.get(selected);
    }

    @Override
    public int getCount() {
        return currentList.size();
    }

    @Override
    public Suggestion getItem(int position) {
        return currentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private void bind(final Suggestion suggestion, View view) {
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.suggestion_name.setText(suggestion.getName());
        holder.suggestion_rating.setText("Distance: "+suggestion.getRating()+"/5");
        holder.suggestion_distance.setText("Rating: "+suggestion.getDistance()+" miles");
        if(suggestion.getSuggestedBy() == null){
            holder.suggested_name.setVisibility(View.GONE);
        }else {
            holder.suggested_name.setText(suggestion.getSuggestedBy());
        }

        holder.rb.setOnClickListener(new View.OnClickListener() //the add tag button "+"
        {
            public void onClick(View view)
            {
                // if unchecked, remove from both original list and invited people list
                //System.out.println("Check box pressed");
                //System.out.println("button value "+((RadioButton) view).isChecked());
                if(selected == null) {
                    //System.out.println("First selection");
                    selected = suggestion.getName();
                    suggestion.setChecked(true);
                    users.put(suggestion.getName(), suggestion);
                    System.out.println("selected: "+selected);
                }else{
                    //System.out.println("another selection");
                    users.get(selected).setChecked(false);
                    selected = suggestion.getName();
                    suggestion.setChecked(true);
                    users.put(suggestion.getName(), suggestion);
                }
                notifyDataSetChanged();
                //System.out.println("button value2 "+((RadioButton) view).isChecked());
            }
        });

        if(suggestion.getChecked()) {
            //System.out.println("Button set true");
            holder.rb.setChecked(true);
        }else {
            //System.out.println("Button set false");
            holder.rb.setChecked(false);
        }
    }

    private View inflateIfRequired(View view, int position, ViewGroup parent) {
        if (view == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            view = inflater.inflate(R.layout.suggestion_adapter, parent, false);
            view.setTag(new ViewHolder(view));
        }
        return view;
    }


    static class ViewHolder {
        final TextView suggestion_name;
        final TextView suggestion_rating;
        final TextView suggestion_distance;
        final TextView suggested_name;
        final RadioButton rb;

        ViewHolder(View view) {
            suggestion_name = (TextView) view.findViewById(R.id.suggestion_name);
            suggestion_rating = (TextView) view.findViewById(R.id.suggestion_rating);
            suggestion_distance = (TextView) view.findViewById(R.id.suggestion_distance);
            suggested_name = (TextView) view.findViewById(R.id.suggested_name);
            rb = (RadioButton) view.findViewById(R.id.suggestion_radio_button);
        }
    }

}
