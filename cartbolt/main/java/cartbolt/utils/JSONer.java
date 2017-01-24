package cartbolt.utils;


import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

/**
 * Created by Job on 16-Jun-16.
 */
public class JSONer {

    public static String buildJSONOrderArray(String id, String name, String phone, String email,
                  String arealoc, String description, String type, String ttl, String mkup, String del, String grand) throws JSONException {

        JSONArray results = new JSONArray();
        //for Order info
        JSONObject hr = new JSONObject();
        hr.put("id", id);
        hr.put("name", name);
        hr.put("phone", phone);
        hr.put("email", email);
        results.add(hr);

        //more info
        JSONObject nr = new JSONObject();
        nr.put("arealoc", arealoc);
        nr.put("description", description);
        nr.put("stat", "fresh");
        nr.put("type", type);
        nr.put("ttl", ttl);
        nr.put("mkup", mkup);
        nr.put("del", del);
        nr.put("grand", grand);
        results.add(nr);

        //clients = clientjet.findAll();
        for(int c = 0; c < Cart.cartlist.size(); c++){
            JSONObject cl = new JSONObject();
            cl.put("id", Cart.cartlist.get(c).getId());
            cl.put("name", Cart.cartlist.get(c).getName());
            cl.put("quantity", Cart.cartlist.get(c).getQuantity());
            cl.put("price", Cart.cartlist.get(c).getPrice());
            cl.put("description", Cart.cartlist.get(c).getDescription());
            cl.put("pic", Cart.cartlist.get(c).getImage());
            cl.put("outlet", Cart.cartlist.get(c).getOutletName());
            results.add(cl);
            //results.optJSONObject(c);
        }

        //out.println(results.toString());
        return results.toString().replace(" ", "%20").replace("\'","-");

    }

}
