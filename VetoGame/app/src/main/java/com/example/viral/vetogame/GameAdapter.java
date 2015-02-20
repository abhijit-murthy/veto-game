package com.example.viral.vetogame;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Viral on 2/19/2015.
 */
public class GameAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<AGame> games;

    public GameAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
        this.games = new ArrayList<AGame>();
    }

    public void replaceWith(Collection<AGame> newGames) {
        this.games.clear();
        this.games.addAll(newGames);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return games.size();
    }

    @Override
    public AGame getItem(int position) {
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

    private void bind(AGame game, View view) {
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.gameNameTextView.setText(game.getGameName());
        holder.currentSuggestionTextView.setText(game.getCurrentSuggestion().toString());
        //TODO: get current time and subtract from end time.
        holder.timeRemainingTextView.setText(""+(game.getTimeEnding().getTime()));

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
        final TextView currentSuggestionTextView;
        final TextView timeRemainingTextView;
        final TextView numberOfMembersTextView;

        ViewHolder(View view) {
            gameNameTextView = (TextView) view.findViewWithTag("Game Name");
            currentSuggestionTextView = (TextView) view.findViewWithTag("currentSuggestion");
            timeRemainingTextView = (TextView) view.findViewWithTag("@string/time_remaining");
            numberOfMembersTextView = (TextView) view.findViewWithTag("numberOfMembers");
        }
    }
}
