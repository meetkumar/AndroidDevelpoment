package com.example.meetkumarpatel.helix;

import android.os.AsyncTask;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by meetkumarpatel on 11/15/17.
 */

public class GetNearbyDeals extends AsyncTask<Object, String, String>{
    GoogleMap mMap;
    String url;
    String googlePlaces;

    @Override
    protected String doInBackground(Object... objects) {
        mMap = (GoogleMap) objects[0];
        url = (String)objects[1];
        HttpConnector httpConnector = new HttpConnector();
        try {
            googlePlaces = httpConnector.readUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return googlePlaces;
    }

    @Override
    protected void onPostExecute(String s) {
        List<HashMap<String,String>> nearbyDeals = null;
        DataParser parser = new DataParser();
        nearbyDeals = parser.parse(s);
        showNearbyPlaces(nearbyDeals);
    }

    private void showNearbyPlaces(List<HashMap<String,String>> nearbyDealsList){
        for(int i=0;i<nearbyDealsList.size();i++){
            MarkerOptions markerOptions = new MarkerOptions();
            HashMap<String,String> googlePlace = nearbyDealsList.get(i);

            String name = googlePlace.get("name");
            String itemId = googlePlace.get("itemId");
            String msrp = googlePlace.get("msrp");
            String salesPrice = googlePlace.get("sales_price");

            double lat = 37.511302;
            double lng = -121.943195;

            LatLng latLng = new LatLng(lat,lng);
            markerOptions.position(latLng);
            markerOptions.title(itemId+" : "+name+" MSRP : "+msrp+" Sales Price : "+salesPrice);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        }
    }
}
