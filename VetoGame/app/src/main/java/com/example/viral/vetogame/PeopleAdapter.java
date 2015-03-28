package com.example.viral.vetogame;

import android.widget.ArrayAdapter;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by eunkikim on 2/19/15.
 */
public class PeopleAdapter extends ArrayAdapter <Person> {
    ArrayList<Person> personItems;  // all people
    ArrayList<Person> filteredList;
    ArrayList<Person> personInvited;    // selected people in a list
    HashMap<Integer, Integer> map;  // relationship between all people and invited people
    Context context;
    boolean toggle = false; // check "show invited people" button is on/off

    // when it needs to show all people
    public PeopleAdapter(Context context, ArrayList<Person> resource) {
        super(context,R.layout.person_adapter,resource);

        // TODO Auto-generated constructor stub
        this.context = context;
        personItems = new ArrayList<Person>();
        this.personItems = resource;
        toggle = false;
    }

    // when it needs to show only invited people
    public PeopleAdapter(Context context, ArrayList<Person> resource, ArrayList<Person> invited) {
        super(context,R.layout.person_adapter,invited);

        // TODO Auto-generated constructor stub
        this.context = context;
        personItems = new ArrayList<Person>();
        this.personItems = resource;
        personInvited = new ArrayList<Person>();
        this.personInvited = invited;
        map = new HashMap<Integer, Integer>();
        toggle = true;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.person_adapter, parent, false);
        TextView name = (TextView) convertView.findViewById(R.id.person_name);
        CheckBox cb = (CheckBox) convertView.findViewById(R.id.person_chk);

        if(toggle){ // show invited people
            name.setText(personInvited.get(position).getName());
            cb.setOnClickListener(new View.OnClickListener() //the add tag button "+"
            {
                public void onClick(View view)
                {
                    // if unchecked, remove from both original list and invited people list
                    if(!((CheckBox) view).isChecked()){
                        //personItems.get(map.get(position)).setChecked(false);
                        personItems.get(map.get(personInvited.get(position).getId())).setChecked(false);
                        personInvited.remove(position);
                        notifyDataSetChanged();
                    }
                }
            });

            cb.setChecked(true);
        }else{  // show all people
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

    public int checkedCount(){
        int count = 0;
        for(int i=0; i<personItems.size(); i++){
            if(personItems.get(i).getChecked())
                count++;
        }
        return count;
    }

    public void setMap(HashMap map) {
        this.map = map;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {

            /* (non-Javadoc)
             * @see android.widget.Filter#performFiltering(java.lang.CharSequence)
             */
            @Override
            protected Filter.FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<Person> results = new ArrayList<Person>();
                filteredList = personItems;
                if (constraint != null) {
                    if (filteredList != null && filteredList.size() > 0) {
                        for (final Person person : filteredList) {
                            if (person.getName().toLowerCase()
                                    .contains(constraint.toString()))
                                results.add(person);
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            /* (non-Javadoc)
             * @see android.widget.Filter#publishResults(java.lang.CharSequence, android.widget.Filter.FilterResults)
             */
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredList = (ArrayList<Person>) results.values;
                //personItems = (ArrayList<Person>) results.values;
                notifyDataSetChanged();
            }

        };
    }
}
