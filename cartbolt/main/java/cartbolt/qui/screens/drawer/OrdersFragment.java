package cartbolt.qui.screens.drawer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cartbolt.qui.adapters.OrderBaseAdapter;
import cartbolt.qui.entities.Order;
import cartbolt.qui.screens.R;
import cartbolt.utils.AppController;
import cartbolt.utils.Globals;

public class OrdersFragment extends Fragment {

    private ListView listView;
    private OrderBaseAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("opening the orders");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_orders, container, false);

        listView = (ListView) view.findViewById(R.id.listvieworders);
        fectchItems();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    protected Context getBaseContext() {
        return null;
    }

    private void fectchItems(){
        // Tag used to cancel the request
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        String theid = prefs.getString("userid", "");

        String tag_json_arry = "json_array_req";
        String url = Globals.server+"/userorders?id="+theid;
        System.out.println("#2 URL ****************************** : " + url);

        /*Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(url);
        if(entry != null){
            try {
                String data = new String(entry.data, "UTF-8");
                // handle data, like converting it to xml, json, bitmap etc.,
                try {
                    decodeData(new JSONArray(data.toString()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            if(checkInternetConnection()){
                JsonArrayRequest req = new JsonArrayRequest(url,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                //Log.d(TAG, response.toString());
                                decodeData(response);
                                //pDialog.hide();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //VolleyLog.d(TAG, "Error: " + error.getMessage());
                        //pDialog.hide();
                    }
                });

                // invalidate cache
                AppController.getInstance().getRequestQueue().getCache().invalidate(url, true);
            } else {
                //Toast.makeText(Outlets.this, "Unable to connect to the internet", Toast.LENGTH_LONG).show();
            }


        } else {*/

        if(checkInternetConnection()){
            // Cached response doesn't exists. Make network call here
            final ProgressDialog pDialog = new ProgressDialog(OrdersFragment.this.getActivity());
            pDialog.setMessage("Loading...");
            pDialog.show();

            JsonArrayRequest req = new JsonArrayRequest(url,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            //Log.d(TAG, response.toString());
                            decodeData(response);
                            pDialog.hide();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //VolleyLog.d(TAG, "Error: " + error.getMessage());
                    Toast.makeText(OrdersFragment.this.getActivity(), "Encountered error. Please Try again", Toast.LENGTH_LONG).show();
                    pDialog.hide();
                }
            });

            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(req, tag_json_arry);
        } else {
            Toast.makeText(OrdersFragment.this.getActivity(), "Unable to connect to the internet", Toast.LENGTH_LONG).show();
        }
        //}

    }

    private void decodeData(JSONArray response) {
        List<Order> results = new ArrayList<Order>();
        try{
            JSONArray json = new JSONArray(response.toString());
            System.out.println("#2 JSON: " + json.toString());
            //result.clear();
            //JSONArray counts = json.getJSONArray(0);
            for(int i=json.length()-1; i>=0; i--){
                JSONObject chimps = json.getJSONObject(i);
                Order mme = new Order(chimps.get("id").toString(), chimps.get("name").toString(),
                        chimps.get("location").toString(), chimps.get("delivery").toString(), chimps.get("ordertime").toString());
                System.out.println("#testing...: " + mme.getName());
                results.add(mme);
            }
        }catch (JSONException jss){

        }

        if(results.isEmpty()){
            Toast.makeText(OrdersFragment.this.getActivity(), "You do not have any orders!", Toast.LENGTH_LONG).show();
        } else {

            adapter = new OrderBaseAdapter(OrdersFragment.this.getActivity(), results);
            listView.setAdapter(adapter);
            //pDialog.hide();
        }
    }

    private boolean checkInternetConnection() {
        ConnectivityManager conMgr = (ConnectivityManager) OrdersFragment.this.getActivity().getSystemService (Context.CONNECTIVITY_SERVICE);
        // ARE WE CONNECTED TO THE NET
        if (conMgr.getActiveNetworkInfo() != null
                && conMgr.getActiveNetworkInfo().isAvailable()
                && conMgr.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            return false;
        }
    }

}
