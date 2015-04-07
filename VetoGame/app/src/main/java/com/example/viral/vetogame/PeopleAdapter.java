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
    //ArrayList<Person> personItems;  // all people
    //ArrayList<Person> filteredList;
    //ArrayList<Person> personInvited;    // selected people in a list
    //HashMap<Integer, Integer> map;  // relationship between all people and invited people
    private Context context;
    private boolean toggle = false; // check "show invited people" button is on/off

    private HashMap<Integer,Person> users = new HashMap<Integer,Person>(); //all of the users
    private ArrayList<Person> displayList; // the current list the view displays
    private int numberInvited = 0;

    // when it needs to show all people
    public PeopleAdapter(Context context, ArrayList<Person> resource) {
        super(context,R.layout.person_adapter,resource);

        this.context = context;
        toggle = false;

        //System.out.println("test2 size: "+resource.size());
        for(Person person:resource){
            users.put(person.getId(),person);
        }
        displayList = resource;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.person_adapter, parent, false);
        TextView name = (TextView) convertView.findViewById(R.id.person_name);
        CheckBox cb = (CheckBox) convertView.findViewById(R.id.person_chk);

        name.setText(displayList.get(position).getName());

        cb.setOnClickListener(new View.OnClickListener() //the add tag button "+"
        {
            public void onClick(View view)
            {
                // if unchecked, remove from both original list and invited people list
                //System.out.println("Check box pressed");
                Person selected;
                if(!((CheckBox) view).isChecked()){
                    //System.out.println("Unchecked");
                    selected = users.get(displayList.get(position).getId());
                    selected.setChecked(false);
                    users.put(selected.getId(),selected);
                    if(toggle){
                        //System.out.println("remove");
                        displayList.remove(position);
                        notifyDataSetChanged();
                    }else{
                        //System.out.println("change");
                        displayList.get(position).setChecked(false);
                    }
                    numberInvited--;
                }else if(((CheckBox) view).isChecked()) {
                    //System.out.println("Checked");
                    selected = users.get(displayList.get(position).getId());
                    selected.setChecked(true);
                    users.put(selected.getId(),selected);
                    displayList.get(position).setChecked(true);
                    numberInvited++;
                }
            }
        });

        if(displayList.get(position).getChecked()){
            cb.setChecked(true);
        }else{
            cb.setChecked(false);
        }
        notifyDataSetChanged();

        return convertView;
    }

    public void setToggle(boolean toggle) {
        this.toggle = toggle;
        displayList.clear();
        if(this.toggle){
            //System.out.println("numInvited "+ numberInvited);
            for(Person person: users.values()){
                if(person.getChecked()){
                    //System.out.println("is checked");
                    displayList.add(person);
                }
            }
        }else{
            for(Person person: users.values()){
                displayList.add(person);
            }
        }
        notifyDataSetChanged();
    }

    public int getNumberInvited() {
        return numberInvited;
    }

    @Override
    public int getCount() {
        return displayList.size();
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
                if (constraint != null) {
                    if(users.size()>0){
                        for(Person person : users.values()){

                            if (person.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                                if(toggle){
                                    if(person.getChecked()){
                                        results.add(person);
                                    }
                                }else{
                                    results.add(person);
                                }

                            }
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
                displayList = (ArrayList<Person>) results.values;
                notifyDataSetChanged();
            }

        };
    }
}