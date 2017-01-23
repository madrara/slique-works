package com.example.flash.superkatale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 */
public class ParseJSON {
    public static String[] supermkt_id;
    public static String[] supermkt_names;
    public static String[] supermkt_slogan;
    public static String[] supermkt_location;

    public static final String JSON_ARRAY = "supermkts";
    public static final String KEY_ID = "supermkt_id";
    public static final String KEY_NAME = "supermkt_names";
    public static final String KEY_SLOGAN = "supermkt_slogan";
    public static final String KEY_LOCATION = "supermkt_location";

    private JSONArray users = null;
 
    private String json;
 
    public ParseJSON(String json){
        this.json = json;
    }
 
    protected void parseJSON(){
        JSONObject jsonObject=null;
        try {
            jsonObject = new JSONObject(json);
            users = jsonObject.getJSONArray(JSON_ARRAY);

            supermkt_id = new String[users.length()];
            supermkt_names = new String[users.length()];
            supermkt_slogan = new String[users.length()];
            supermkt_location = new String[users.length()];

            for(int i=0;i<users.length();i++){
                JSONObject jo = users.getJSONObject(i);
                supermkt_id[i] = jo.getString(KEY_ID);
                supermkt_names[i] = jo.getString(KEY_NAME);
                supermkt_slogan[i] = jo.getString(KEY_SLOGAN);
                supermkt_location[i] = jo.getString(KEY_LOCATION);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
