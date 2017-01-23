package com.example.flash.superkatale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 */
public class ParseJSON2 {
    public static String[] fuelco_id;
    public static String[] fuelco_name;
    public static String[] fuelco_slogan;
    public static String[] fuelco_location;

    public static final String JSON_ARRAY = "fuelcompanies";
    public static final String KEY_ID = "fuelco_id";
    public static final String KEY_NAME = "fuelco_name";
    public static final String KEY_SLOGAN = "fuelco_slogan";
    public static final String KEY_LOCATION = "fuelco_location";

    private JSONArray users = null;

    private String json;

    public ParseJSON2(String json){
        this.json = json;
    }
 
    protected void parseJSON2(){
        JSONObject jsonObject=null;
        try {
            jsonObject = new JSONObject(json);
            users = jsonObject.getJSONArray(JSON_ARRAY);

            fuelco_id = new String[users.length()];
            fuelco_name = new String[users.length()];
            fuelco_slogan = new String[users.length()];
            fuelco_location = new String[users.length()];

            for(int i=0;i<users.length();i++){
                JSONObject jo = users.getJSONObject(i);
                fuelco_id[i] = jo.getString(KEY_ID);
                fuelco_name[i] = jo.getString(KEY_NAME);
                fuelco_slogan[i] = jo.getString(KEY_SLOGAN);
                fuelco_location[i] = jo.getString(KEY_LOCATION);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
