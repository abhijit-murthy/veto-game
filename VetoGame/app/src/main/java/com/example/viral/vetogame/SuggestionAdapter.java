package com.example.viral.vetogame;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by eunkikim on 3/5/15.
 */
public class SuggestionAdapter extends ArrayAdapter<Suggestion> {
    ArrayList<Suggestion> suggestions;
    Context context;
    ArrayList<Suggestion> suggestionItems;
    ArrayList<Suggestion> filteredList;
    Suggestion selected;
    HashMap<Integer, Integer> map;
    boolean toggle = false;

    public SuggestionAdapter(Context context, ArrayList<Suggestion> resource){
        super(context,R.layout.suggestion_adapter,resource);
        this.context = context;
        this.suggestions = resource;
        suggestionItems = resource;//new ArrayList<Suggestion>();
        toggle = false;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.suggestion_adapter, parent, false);
        TextView suggestion_name = (TextView) convertView.findViewById(R.id.suggestion_name);
        TextView suggestion_rating = (TextView) convertView.findViewById(R.id.suggestion_rating);
        TextView suggestion_distance = (TextView) convertView.findViewById(R.id.suggestion_distance);
        TextView suggested_name = (TextView) convertView.findViewById(R.id.suggested_name);
        CheckBox cb = (CheckBox) convertView.findViewById(R.id.suggestion_chk);
        //TextView supporters = (TextView) convertView.findViewById(R.id.supporters);

        //suggestion_name = suggestions.get(position).getName();
        suggestion_name.setText(suggestionItems.get(position).getName());
        suggestion_rating.setText("Distance: "+suggestionItems.get(position).getRating()+"/5");
        suggestion_distance.setText("Rating: "+suggestionItems.get(position).getDistance()+" miles");
        if(suggestionItems.get(position).getSuggestedBy() == null){
            suggested_name.setVisibility(View.GONE);
        }else{
            suggested_name.setText(suggestionItems.get(position).getSuggestedBy());
        }

        /*if(suggestionItems.get(position).getChecked())
            cb.setChecked(true);
        else
            cb.setChecked(false);

        cb.setOnClickListener(new View.OnClickListener() //the add tag button "+"
        {
            public void onClick(View view)
            {
                if(((CheckBox) view).isChecked()){
                    suggestionItems.get(position).setChecked(true);
                }else{
                    suggestionItems.get(position).setChecked(false);
                }
            }
        });*/

        return convertView;
    }

    public int checkedCount(){
        int count = 0;
        for(int i=0; i<suggestionItems.size(); i++){
            if(suggestionItems.get(i).getChecked())
                count++;
        }
        return count;
    }
}
