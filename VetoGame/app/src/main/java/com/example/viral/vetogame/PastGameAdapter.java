package com.example.viral.vetogame;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Locale;

/**
 * Created by Viral on 2/19/2015.
 */
public class PastGameAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<Game> games;
    private Calendar calendar = Calendar.getInstance();

    public PastGameAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
        this.games = new ArrayList<Game>();
    }

    public void replaceWith(Collection<Game> newGames) {
        this.games.clear();
        this.games.addAll(newGames);
        notifyDataSetChanged();
    }

    public void addGame(Game game){
        games.add(game);
        System.out.println("game added\nsize is: "+games.size());
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return games.size();
    }

    @Override
    public Game getItem(int position) {
        return games.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = inflateIfRequired(view, position, parent);
        bind(getItem(position), view);
        return view;
    }

    public String calcTimePast (Calendar timeEnding, Calendar currentTime)
    {
        long endTime = timeEnding.getTimeInMillis();
        long curTime = currentTime.getTimeInMillis();
        long diffTime = curTime - endTime;
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
        holder.winningSuggestionTextView.setText(game.getCurrentSuggestion().toString());
        holder.winnerTextView.setText(game.getWinner().toString());
        String timeRemaining = calcTimePast(game.getTimeEnding(),calendar);
        holder.timePastTextView.setText(timeRemaining);
        holder.numberOfMembersTextView.setText(""+game.getNumberOfMembers());
    }

    private View inflateIfRequired(View view, int position, ViewGroup parent) {
        if (view == null) {
            view = inflater.inflate(R.layout.game_adapter, null);
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
}
