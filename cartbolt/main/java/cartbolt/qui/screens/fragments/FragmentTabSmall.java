package cartbolt.qui.screens.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cartbolt.qui.adapters.ListerItemBaseAdapter;
import cartbolt.qui.entities.Item;
import cartbolt.qui.screens.Outlethome;
import cartbolt.qui.screens.R;
import cartbolt.utils.AppController;
import cartbolt.utils.Globals;

public class FragmentTabSmall extends Fragment {

    public int tabIndex;
    ListView listview;
    ListerItemBaseAdapter adapter;

    public List<Item> results = new ArrayList<Item>();
    List<Item> searchresults = new ArrayList<Item>();

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        //new PrdsTask().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tab_small,
                container, false);
        System.out.println("OULET ID :" + Globals.outletid + " tabIndex :" + tabIndex);

        // start here
        listview = (ListView) view.findViewById(R.id.listviewersmall);

        theTheFetch();

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    protected Context getBaseContext() {
        return null;
    }

    private void theTheFetch(){
        // Tag used to cancel the request
        String tag_json_arry = "json_array_req";
        String url = Globals.server+"/directitems?id="+Outlethome.getCategories().get(tabIndex).getId();

        Cache cache = AppController.getInstance().getRequestQueue().getCache();
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


        } else {

            if(checkInternetConnection()){
                // Cached response doesn't exists. Make network call here
                final ProgressDialog pDialog = new ProgressDialog(FragmentTabSmall.this.getActivity());
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
                        Toast.makeText(FragmentTabSmall.this.getActivity(), "Encountered error. Please Try again", Toast.LENGTH_LONG).show();
                        pDialog.hide();
                    }
                });

                // Adding request to request queue
                AppController.getInstance().addToRequestQueue(req, tag_json_arry);
            } else {
                Toast.makeText(FragmentTabSmall.this.getActivity(), "Unable to connect to the internet", Toast.LENGTH_LONG).show();
            }
        }

    }

    private void decodeData(JSONArray response) {
        List<Item> results = new ArrayList<Item>();
        try{
            JSONArray json = new JSONArray(response.toString());
            System.out.println("#2 JSON: " + json.toString());
            //result.clear();
            for(int i=0; i<json.length(); i++){
                JSONObject chimps = json.getJSONObject(i);
                Item mme = new Item(Integer.parseInt(chimps.get("id").toString()), chimps.get("name").toString(),
                        chimps.get("pic").toString());
                mme.setPrice(Double.parseDouble(chimps.get("price").toString()));
                System.out.println("#testing...: " + mme.getName());
                results.add(i, mme);
            }
        }catch (JSONException jss){

        }

        if(results.isEmpty()){
            //Toast.makeText(FragmentTabSmall.this.getActivity(), "No results not found.", Toast.LENGTH_LONG).show();
        } else {

            adapter = new ListerItemBaseAdapter(FragmentTabSmall.this.getActivity(), results);
            listview.setAdapter(adapter);
        }
    }

    private boolean checkInternetConnection() {
        ConnectivityManager conMgr = (ConnectivityManager) FragmentTabSmall.this.getActivity().getSystemService (Context.CONNECTIVITY_SERVICE);
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
