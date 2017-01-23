package com.example.flash.superkatale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 */
public class ParseJSON_2 {
    public static String[] id;
    public static String[] quantity;
    public static String[] price;
    public static String[] fulldescription;
    public static String[] phone;
    public static String[] image;

    public static final String JSON_ARRAY = "cart";

    public static final String KEY_ID = "id";
    public static final String KEY_QNTY = "quantity";
    public static final String KEY_PRICE = "price";
    public static final String KEY_FUll = "fulldescription";
    public static final String KEY_PHONE = "phone_number";
    public static final String KEY_IMAGE = "image";
    private JSONArray users = null;

    private String json;

    public ParseJSON_2(String json){
        this.json = json;
    }

    protected void parseJSON_2(){
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
            users = jsonObject.getJSONArray(JSON_ARRAY);

            id = new String[users.length()];
            quantity = new String[users.length()];
            price = new String[users.length()];
            fulldescription = new String[users.length()];
            phone = new String[users.length()];
            image = new String[users.length()];


            for(int i=0;i<users.length();i++){
                JSONObject jo = users.getJSONObject(i);
                id[i] = jo.getString(KEY_ID);
                quantity[i] = jo.getString(KEY_QNTY);
                price[i] = jo.getString(KEY_PRICE);
                fulldescription[i] = jo.getString(KEY_FUll);
                phone[i] = jo.getString(KEY_PHONE);
                image[i] = jo.getString(KEY_IMAGE);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
