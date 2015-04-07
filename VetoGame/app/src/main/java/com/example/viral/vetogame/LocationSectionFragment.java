package com.example.viral.vetogame;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;

import android.graphics.Color;
import android.location.Location;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
//import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Viral on 3/5/2015.
 */
public class LocationSectionFragment extends Fragment {

    private SeekBar seekBar;
    private EditText radiusText;

    private int radius = 1;
    private EditText centerText;
    private int currentZipCode = 0;
    private int eventZipCode = 0;

    private GoogleMap map;
    private Circle circle;
    private LatLng eventLoc;
    private LatLng currentLoc;
    private int zoom = 11;
    private boolean zipCodeEntered = false;

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
                setZoom(radius);
                setCircle(currentLoc,radius);
            }
        });

        radiusText.addTextChangedListener(new TextWatcher(){
            int r;
            public void afterTextChanged(Editable s) {
                //Toast.makeText(getActivity(),"after "+s.toString(),Toast.LENGTH_SHORT).show();
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
                    setCircle(currentLoc,radius);
                }else{
                    Toast.makeText(getActivity(),"Enter a radius between 1 and 50",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //TODO: uncomment after getting zipcode is implemented
        /*centerText.addTextChangedListener(new TextWatcher(){
            int loc;
            public void afterTextChanged(Editable s) {
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){
            }
            public void onTextChanged(CharSequence s, int start, int before, int count){
                if(s.length()==5) {
                    zipCodeEntered = true;
                    loc = Integer.parseInt(s.toString());
                }else{
                    loc = 0;//TODO: change to current location zipcode
                    //eventLoc = lat and log of event zipcode
                    zipCodeEntered = false;
                }
                if(loc == 0){
                    eventZipCode = currentZipCode;
                }
                setCircle(currentLoc,radius);
            }
        });*/

        ((NewSuggestion)getActivity()).setTabFragmentL(getTag());

        final LocationManager manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();
        }

        map = ((MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.mapView)).getMap();

        if (map!=null) {

            map.setMyLocationEnabled(true);

            map.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                @Override
                public void onMyLocationChange(Location arg0) {
                    currentLoc = new LatLng(arg0.getLatitude(), arg0.getLongitude());
                    MarkerOptions mark = new MarkerOptions().position(currentLoc).title("You");
                    map.addMarker(mark);
                    //TODO: add an if statement to change location if zipcode is entered
                    CameraUpdate cameraUpdate;
                    if(!zipCodeEntered) {
                        cameraUpdate = CameraUpdateFactory.newLatLng(currentLoc);
                    }else{
                        cameraUpdate = CameraUpdateFactory.newLatLng(eventLoc);
                    }
                    map.animateCamera(cameraUpdate);
                }
            });
        }

        return rootView;
    }

    public void setCircle(LatLng newLoc, int rad){
        setZoom(rad);
        if(newLoc!=null){
            if(circle!=null){
                circle.remove();
            }
            circle = map.addCircle(new CircleOptions()
                    .center(newLoc)
                    .radius(milesToMeters(rad))
                    .strokeColor(Color.BLUE)
                    .fillColor(0x95addfff));//Light blue that is 95% transparent
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(newLoc, zoom);
            map.animateCamera(cameraUpdate);
        }
    }

    public EditText getRadiusText() {
        return radiusText;
    }

    public EditText getCenterText() {
        return centerText;
    }

    public double milesToMeters(int miles){
        return (miles * 1609.34);
    }

    public void setZoom(int rad){
        if(rad<3){
            zoom = 13;
        }else if(rad<4){
            zoom = 12;
        }else if(rad<7){
            zoom = 11;
        }else if(rad<11){
            zoom = 10;
        }else if(rad<23){
            zoom = 9;
        }else if(rad<51){
            zoom = 8;
        }
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save_suggestion) {
            return true;
        }
        return false;
    }*/

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Your GPS is disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        getActivity().finish();
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
}
