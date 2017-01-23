package com.example.flash.superkatale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 */
public class ParseJSON_1 {
    public static String[] id;
    public static String[] description;
    public static String[] price;
    public static String[] fulldescription;
    public static String[] imageurl;

    public static final String JSON_ARRAY = "bread";

    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "description";
    public static final String KEY_PRICE = "price";
    public static final String KEY_FUll = "fulldescription";
    public static final String KEY_URL = "image";
    private JSONArray users = null;

    private String json;

    public ParseJSON_1(String json){
        this.json = json;
    }

    protected void parseJSON_1(){
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
            users = jsonObject.getJSONArray(JSON_ARRAY);

            id = new String[users.length()];
            description = new String[users.length()];
            price = new String[users.length()];
            fulldescription = new String[users.length()];
            imageurl = new String[users.length()];

            for(int i=0;i<users.length();i++){
                JSONObject jo = users.getJSONObject(i);
                id[i] = jo.getString(KEY_ID);
                description[i] = jo.getString(KEY_NAME);
                price[i] = jo.getString(KEY_PRICE);
                fulldescription[i] = jo.getString(KEY_FUll);
                imageurl[i] = jo.getString(KEY_URL);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
