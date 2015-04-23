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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Viral on 2/19/2015.
 */
public class PastGameAdapter extends ArrayAdapter<Game> {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Game> games;
    private ArrayList<Game> displayList = new ArrayList<Game>();
    private Calendar calendar = Calendar.getInstance();

    public PastGameAdapter(Context context, ArrayList<Game> resource) {
        super(context,R.layout.past_game_adapter,resource);
        this.context = context;
        games = resource;
        for(Game game:games){
            this.displayList.add(game);
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

    private void bind(Game game, View view) {
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.gameNameTextView.setText(game.getGameName());
        holder.winningSuggestionTextView.setText(game.getCurrentSuggestion().getName());
        holder.winnerTextView.setText(game.getWinner());

        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String eventTime = sdf.format(game.getEventTime().getTime());

        holder.timePastTextView.setText(eventTime);
        holder.numberOfMembersTextView.setText(""+game.getNumberOfMembers());
    }

    private View inflateIfRequired(View view, int position, ViewGroup parent) {
        if (view == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            view = inflater.inflate(R.layout.past_game_adapter, parent, false);
            view.setTag(new ViewHolder(view));
        }
        return view;
    }


    static class ViewHolder {
        final TextView gameNameTextView;
        final TextView winningSuggestionTextView;
        final TextView winnerTextView;
        final TextView timePastTextView;
        final TextView numberOfMembersTextView;

        ViewHolder(View view) {
            gameNameTextView = (TextView) view.findViewWithTag("gameName");
            winningSuggestionTextView = (TextView) view.findViewWithTag("winningSuggestion");
            winnerTextView = (TextView)view.findViewWithTag("winnerName");
            timePastTextView = (TextView) view.findViewWithTag("timePast");
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
