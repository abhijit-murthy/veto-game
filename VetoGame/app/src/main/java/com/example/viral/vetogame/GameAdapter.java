package com.example.viral.vetogame;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Locale;

/**
 * Created by Viral on 2/19/2015.
 */
public class GameAdapter extends ArrayAdapter<Game> {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Game> games;
    private ArrayList<Game> displayList = new ArrayList<Game>();
    private Calendar calendar = Calendar.getInstance();

    public GameAdapter(Context context, ArrayList<Game> resource) {
        super(context,R.layout.past_game_adapter,resource);
        this.context = context;
        games = resource;
        for(Game game:games){
            displayList.add(game);
        }
    }

    public void replaceWith(Collection<Game> newGames) {
        this.games.clear();
        this.games.addAll(newGames);
        notifyDataSetChanged();
    }

    public void addGame(Game game){
        games.add(game);
        displayList.add(game);
        System.out.println("game added\nsize is: "+games.size());
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = inflateIfRequired(view, position, parent);
        bind(getItem(position), view);
        return view;
    }

    public String calcTimeRemaining (Calendar start, Calendar end )
    {
        long startTime = start.getTimeInMillis();
        long endTime = end.getTimeInMillis();
        long diffTime = endTime - startTime;
        // divide the milliseconds by # of milliseconds in a day to get days difference
        int days = (int)( diffTime / (1000 * 60 * 60 * 24) );
        int hours = (int)( diffTime / (1000 * 60 * 60) );
        int minutes = (int)( diffTime / (1000 * 60) );
        int seconds = (int)( diffTime / (1000 * 60) );
        if(days > 0){
            return(""+days+" d");
        }else if(hours > 0){
            return(""+hours+" h");
        }else if(minutes > 0){
            return(""+minutes+" m");
        }else if(seconds > 0){
            return(""+seconds+" s");
        }else{
            return("game over");
        }
    }

    private void bind(Game game, View view) {
        ViewHolder holder = (ViewHolder) view.getTag();
        if(holder == null){
            System.out.println("holder null");
        }else{
            System.out.println(" holder not null");
        }
        holder.gameNameTextView.setText(game.getGameName());
        holder.currentSuggestionTextView.setText(game.getCurrentSuggestion().toString());
        String timeRemaining = calcTimeRemaining(calendar,game.getTimeEnding());
        holder.timeRemainingTextView.setText(timeRemaining);
        holder.numberOfMembersTextView.setText(""+game.getNumberOfMembers());
    }

    private View inflateIfRequired(View view, int position, ViewGroup parent) {
        if (view == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            view = inflater.inflate(R.layout.game_adapter, parent, false);
            view.setTag(new ViewHolder(view));
        }
        return view;
    }


    static class ViewHolder {
        final TextView gameNameTextView;
        final TextView currentSuggestionTextView;
        final TextView timeRemainingTextView;
        final TextView numberOfMembersTextView;

        ViewHolder(View view) {
            gameNameTextView = (TextView) view.findViewWithTag("gameName");
            currentSuggestionTextView = (TextView) view.findViewWithTag("currentSuggestion");
            timeRemainingTextView = (TextView) view.findViewWithTag("timeRemaining");
            numberOfMembersTextView = (TextView) view.findViewWithTag("numberOfMembers");
        }
    }

    @Override
    public int getCount() {
        return displayList.size();
    }

    @Override
    public Game getItem(int position) {
        return displayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
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
                final ArrayList<Game> results = new ArrayList<Game>();
                if (constraint != null) {
                    if(games.size()>0){
                        for(Game game : games){
                            if (game.getGameName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                                results.add(game);
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
                displayList = (ArrayList<Game>) results.values;
                notifyDataSetChanged();
            }

        };
    }
}
