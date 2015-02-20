package com.example.viral.vetogame;

import android.widget.ArrayAdapter;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * Created by eunkikim on 2/19/15.
 */
public class APeopleAdapter extends ArrayAdapter <APerson>{
    APerson[] personItems = null;
    Context context;

    public APeopleAdapter(Context context, APerson[] resource) {
        super(context,R.layout.list_person,resource);
        // TODO Auto-generated constructor stub
        this.context = context;
        this.personItems = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.list_person, parent, false);
        TextView name = (TextView) convertView.findViewById(R.id.person_name);
        CheckBox cb = (CheckBox) convertView.findViewById(R.id.person_chk);

        name.setText(personItems[position].getName());
        if(personItems[position].getValue() == 1)
            cb.setChecked(true);
        else
            cb.setChecked(false);
        return convertView;
    }
}
