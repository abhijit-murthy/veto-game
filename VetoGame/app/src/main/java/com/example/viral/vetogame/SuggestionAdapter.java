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

/**
 * Created by eunkikim on 3/5/15.
 */
public class SuggestionAdapter extends ArrayAdapter<Suggestion> {
    ArrayList<Suggestion> suggestions;
    Context context;

    public SuggestionAdapter(Context context, ArrayList<Suggestion> resource){
        super(context,R.layout.suggestion_adapter,resource);

        this.context = context;
        this.suggestions = resource;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.suggestion_adapter, parent, false);
        TextView suggestion_name = (TextView) convertView.findViewById(R.id.suggestion_name);
        TextView suggested_name = (TextView) convertView.findViewById(R.id.suggested_name);
        TextView supporters = (TextView) convertView.findViewById(R.id.supporters);

        //suggestion_name = suggestions.get(position).

        return convertView;
    }
}
