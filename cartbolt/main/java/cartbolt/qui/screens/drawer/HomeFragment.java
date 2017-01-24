package cartbolt.qui.screens.drawer;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;

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

import cartbolt.qui.adapters.HomeCategoryBaseAdapter;
import cartbolt.qui.entities.Homecategory;
import cartbolt.qui.screens.R;
import cartbolt.utils.AppController;
import cartbolt.utils.Globals;

public class HomeFragment extends Fragment {

    private ViewFlipper homeFlipper;
    private Animation slide_in_right, slide_out_left;
    private View homeCategoryView;
    private RelativeLayout relView;
    private LayoutInflater inflater;

    private HomeCategoryBaseAdapter adapter;
    private GridView homeGrid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //getSupportActionBar().setTitle(Globals.outletname);

        View view = inflater.inflate(R.layout.fragment_home,
                container, false);

        //HomeFragment.this.getActivity().getSupportActionBar().setTitle("Cartbolt");

        homeFlipper = (ViewFlipper) view.findViewById(R.id.homeFlipper);
        slide_in_right = AnimationUtils.loadAnimation(HomeFragment.this.getActivity(), R.anim.slide_in_right);
        slide_out_left = AnimationUtils.loadAnimation(HomeFragment.this.getActivity(), R.anim.slide_out_left);
        homeFlipper.setInAnimation(slide_in_right);
        homeFlipper.setOutAnimation(slide_out_left);
        homeFlipper.setFlipInterval(5000);
        homeFlipper.stopFlipping();

        homeGrid = (GridView) view.findViewById(R.id.homeGrid);
        fetchAndLoadCategories();

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    /*@Override
    protected void onResume() {
        super.onResume();
        fetchAndLoadCategories();
    }*/

    private void fetchAndLoadCategories(){
        // Tag used to cancel the request
        String tag_json_arry = "json_array_req";
        String url = Globals.server+"/types";

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
                final ProgressDialog pDialog = new ProgressDialog(HomeFragment.this.getActivity());
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
                        Toast.makeText(HomeFragment.this.getActivity(), "Encountered error. Please Try again", Toast.LENGTH_LONG).show();
                        pDialog.hide();
                    }
                });

                // Adding request to request queue
                AppController.getInstance().addToRequestQueue(req, tag_json_arry);
            } else {
                Toast.makeText(HomeFragment.this.getActivity(), "Unable to connect to the internet", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void decodeData(JSONArray response) {
        List<Homecategory> results = new ArrayList<Homecategory>();

        //Do something with the JSON string
        try{
            JSONArray json = new JSONArray(response.toString());
            System.out.println("#2 JSON: " + json.toString());
            //result.clear();
            for(int i=0; i<json.length(); i++){
                JSONObject chimps = json.getJSONObject(i);
                Homecategory mme = new Homecategory(chimps.get("name").toString());
                System.out.println("#testing...: " + mme.getName());
                if(chimps.get("name").toString().contentEquals("Supermarket")){
                    //Globals.typeofoutlet
                    mme.setImageurl("supermarkets.jpg");
                } else if(chimps.get("name").toString().contentEquals("Market")){
                    //Globals.typeofoutlet
                    mme.setImageurl("markets.jpg");
                } else if(chimps.get("name").toString().contentEquals("Hardware")){
                    //Globals.typeofoutlet
                    mme.setImageurl("garlic.jpg");
                } else if(chimps.get("name").toString().contentEquals("Pharmacy")){
                    //Globals.typeofoutlet
                    mme.setImageurl("cabbage.jpg");
                }
                results.add(i, mme);
            }
        }catch(JSONException jss){
            //hate them
        }

        if(results.isEmpty()){
            Toast.makeText(HomeFragment.this.getActivity(), "No results not found.", Toast.LENGTH_LONG).show();
        } else {
            List<Homecategory> nout = new ArrayList<Homecategory>();
            List<String> locations = new ArrayList<String>();
            for(int i=0; i<results.size(); i++){
                if(locations.contains(results.get(i).getName())){
                    //leave it
                } else {
                    locations.add(results.get(i).getName());
                    nout.add(new Homecategory(results.get(i).getName(), results.get(i).getImageurl()));
                }
            }
            adapter = new HomeCategoryBaseAdapter(HomeFragment.this.getActivity(), nout);
            homeGrid.setAdapter(adapter);
            homeFlipper.startFlipping();
        }
    }

    private boolean checkInternetConnection() {
        ConnectivityManager conMgr = (ConnectivityManager) HomeFragment.this.getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
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
