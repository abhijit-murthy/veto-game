package com.example.viral.vetogame;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by eunkikim on 2/19/15.
 */
public class PeopleAdapter extends ArrayAdapter <Person> {
    ArrayList<Person> personItems;
    ArrayList<Person> personInvited;
    Context context;
    boolean toggle = false;

    public PeopleAdapter(Context context, ArrayList<Person> resource) {
        super(context,R.layout.list_person,resource);

        // TODO Auto-generated constructor stub
        this.context = context;
        personItems = new ArrayList<Person>();
        this.personItems = resource;
        toggle = false;
    }

    public PeopleAdapter(Context context, ArrayList<Person> invited, boolean t) {
        super(context,R.layout.list_person,invited);

        // TODO Auto-generated constructor stub
        this.context = context;
        personInvited = new ArrayList<Person>();
        this.personInvited = invited;
        toggle = t;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.list_person, parent, false);
        TextView name = (TextView) convertView.findViewById(R.id.person_name);
        CheckBox cb = (CheckBox) convertView.findViewById(R.id.person_chk);

        if(toggle){
            name.setText(personInvited.get(position).getName());
            cb.setChecked(true);

            cb.setOnClickListener(new View.OnClickListener() //the add tag button "+"
            {
                public void onClick(View view)
                {
                    if(!((CheckBox) view).isChecked()){

                        notifyDataSetChanged();
                    }
                }
            });
        }else{
            name.setText(personItems.get(position).getName());
            if(personItems.get(position).getChecked())
                cb.setChecked(true);
            else
                cb.setChecked(false);

            cb.setOnClickListener(new View.OnClickListener() //the add tag button "+"
            {
                public void onClick(View view)
                {
                    if(((CheckBox) view).isChecked()){
                        personItems.get(position).setChecked(true);
                    }else{
                        personItems.get(position).setChecked(false);
                    }
                }
            });
        }

        return convertView;
    }
}
