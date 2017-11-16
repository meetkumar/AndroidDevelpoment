package com.example.meetkumarpatel.helix;

import android.app.ListActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by meetkumarpatel on 11/15/17.
 */

public class DataParser {

    private HashMap<String, String> getPlace(JSONObject placeJson){
        HashMap<String,String> googleMap = new HashMap<>();
        String name = "--NA--";
        String itemId = "--NA--";
        String msrp = "";
        String salesPrice = "";
        String reference = "";
        try {
            if(!placeJson.isNull("name")){
                name = placeJson.getString("name");
            }
            if(!placeJson.isNull("itemId")){
                itemId = placeJson.getString("itemId");
            }
            if(!placeJson.isNull("msrp")){
                msrp = placeJson.getString("msrp");
            }
            if(!placeJson.isNull("salePrice")){
                salesPrice = placeJson.getString("salePrice");
            }
            googleMap.put("name", name);
            googleMap.put("itemId",itemId);
            googleMap.put("msrp",msrp);
            googleMap.put("sales_price",salesPrice);
        }
        catch (JSONException e)
        {
                e.printStackTrace();
        }
    return googleMap;
    }

    private List<HashMap<String,String>> getDeals(JSONArray jsonArray){
        int count = jsonArray.length();
        List<HashMap<String,String>> dealsList = new ArrayList<>();
        HashMap<String,String> dealsMap = null;

        for(int i=0;i<count;i++){
            try {
                dealsMap = getPlace((JSONObject) jsonArray.get(i));
                dealsList.add(dealsMap);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return dealsList;
    }

    public List<HashMap<String,String>> parse(String jsonData){
        JSONArray jsonArray = null;
        JSONObject jsonObject;

        try {
            jsonObject = new JSONObject(jsonData);
            jsonArray = jsonObject.getJSONArray("items");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return getDeals(jsonArray);
    }
}
