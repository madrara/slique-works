package com.example.flash.superkatale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 */
public class ParseJSON1 {
    public static String[] mservice_id;
    public static String[] mservice_name;
    public static String[] mservice_slogan;
    public static String[] mservice_location;
    public static String[] mservice_contact;

    public static final String JSON_ARRAY = "service_company";

    public static final String KEY_ID = "mservice_id";
    public static final String KEY_NAME = "mservice_name";
    public static final String KEY_SLOGAN = "mservice_slogan";
    public static final String KEY_LOCATION = "mservice_location";
    public static final String KEY_CONTACT = "mservice_contact";

    private JSONArray users = null;

    private String json;

    public ParseJSON1(String json){
        this.json = json;
    }

    protected void parseJSON1(){
        JSONObject jsonObject=null;
        try {
            jsonObject = new JSONObject(json);
            users = jsonObject.getJSONArray(JSON_ARRAY);

            mservice_id = new String[users.length()];
            mservice_name = new String[users.length()];
            mservice_slogan = new String[users.length()];
            mservice_location = new String[users.length()];
            mservice_contact = new String[users.length()];

            for(int i=0;i<users.length();i++){
                JSONObject jo = users.getJSONObject(i);
                mservice_id[i] = jo.getString(KEY_ID);
                mservice_name[i] = jo.getString(KEY_NAME);
                mservice_slogan[i] = jo.getString(KEY_SLOGAN);
                mservice_location[i] = jo.getString(KEY_LOCATION);
                mservice_contact[i] = jo.getString(KEY_CONTACT);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
