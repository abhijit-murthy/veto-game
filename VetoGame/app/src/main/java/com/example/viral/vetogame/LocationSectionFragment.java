package com.example.viral.vetogame;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;

/**
 * Created by Viral on 3/5/2015.
 */
public class LocationSectionFragment extends Fragment {

    private SeekBar seekBar;
    private EditText radiusText;

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    private int radius;
    private EditText centerText;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_section_location, container, false);
        setHasOptionsMenu(true);
        seekBar = (SeekBar) rootView.findViewById(R.id.RadiusSeekBar);
        radiusText = (EditText) rootView.findViewById(R.id.RadiusEditText);
        centerText = (EditText) rootView.findViewById(R.id.ZipCodeEditText);

        seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                progress = progressValue;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                radiusText.setText(""+(progress+1));
            }
        });

        radiusText.addTextChangedListener(new TextWatcher(){
            int r;
            public void afterTextChanged(Editable s) {
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){
            }
            public void onTextChanged(CharSequence s, int start, int before, int count){
                if(s.length()>0) {
                    r = Integer.parseInt(s.toString());
                }else{
                    r = 1;
                }
                setRadius(r);
                if(r<=50 && r>0) {
                    seekBar.setProgress((r-1));
                }else{
                    Toast.makeText(getActivity(),"Enter a radius between 1 and 50",Toast.LENGTH_SHORT).show();
                }
            }
        });

        ((NewSuggestion)getActivity()).setTabFragmentL(getTag());

        return rootView;
    }

    public EditText getRadiusText() {
        return radiusText;
    }

    public EditText getCenterText() {
        return centerText;
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save_suggestion) {
            return true;
        }
        return false;
    }*/
}
