package cartbolt.qui.screens;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.Cache;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cartbolt.qui.adapters.OutletsBaseAdapter;
import cartbolt.qui.adapters.OutletsSpinnerBaseAdapter;
import cartbolt.qui.entities.Outlet;
import cartbolt.utils.AppController;
import cartbolt.utils.Globals;

public class Outlets extends AppCompatActivity {

    private Spinner outletspinner;
    private ListView outletslistview;
    List<Outlet> outlets = new ArrayList<Outlet>();
    List<String> locations = new ArrayList<String>();
    List<Outlet> shownoutlets = new ArrayList<Outlet>();
    private ImageView outletsbanner;

    private OutletsBaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outlets);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle(Globals.typeofoutlet);

        outletsbanner = (ImageView) findViewById(R.id.outletsbanner);
        outletspinner = (Spinner) findViewById(R.id.outletspinner);
        outletslistview = (ListView) findViewById(R.id.outletslistview);

        fetchAndLoadOutlets();

    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchAndLoadOutlets();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_outlets, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (item.getItemId() == android.R.id.home) {

			/*
			 * Intent carter = new Intent(this, Begin.class);
			 * startActivity(carter);
			 */
            onBackPressed();

        }

        // new - for menu actions //you can as well just use a switch...
        switch (item.getItemId()) {
            /*case R.id.action_search:
                // Intent carter = new Intent(this, Search.class);
                // startActivity(carter);
                return true;
            case R.id.action_cart:
                Intent cart = new Intent(this, Carty.class);
                startActivity(cart);
                return true;*/
            case R.id.action_settings:
                startActivity(new Intent(this, Signup.class));
                return true;
            case R.id.actions_about:
                startActivity(new Intent(this, About.class));
                return true;
            case R.id.actions_contact:
                startActivity(new Intent(this, Contactus.class));
                return true;
            case R.id.actions_help:
                startActivity(new Intent(this, Help.class));
                return true;
            case R.id.actions_logout:
                SharedPreferences prefs = PreferenceManager
                        .getDefaultSharedPreferences(this);
                SharedPreferences.Editor edit = prefs.edit();
                edit.clear().commit();
                Intent intent = new Intent(this, Start.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void fetchAndLoadOutlets(){
        // Tag used to cancel the request
        String tag_json_arry = "json_array_req";
        String url = Globals.server+"/outlets?id="+Globals.typeofoutlet;

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
                final ProgressDialog pDialog = new ProgressDialog(this);
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
                        Toast.makeText(Outlets.this, "Encountered error. Please Try again", Toast.LENGTH_LONG).show();
                        pDialog.hide();
                    }
                });

                // Adding request to request queue
                AppController.getInstance().addToRequestQueue(req, tag_json_arry);
            } else {
                Toast.makeText(Outlets.this, "Unable to connect to the internet", Toast.LENGTH_LONG).show();
            }
        }

    }

    private void decodeData(JSONArray response) {
        outlets.clear();

        //Do something with the JSON string
        try{
            JSONArray json = new JSONArray(response.toString());
            System.out.println("#2 JSON: " + json.toString());
            //result.clear();
            for(int i=0; i<json.length(); i++){
                JSONObject chimps = json.getJSONObject(i);
                Outlet mme = new Outlet(chimps.get("id").toString(), chimps.get("name").toString(),
                        chimps.get("location").toString(), chimps.get("type").toString(), chimps.get("size").toString());
                System.out.println("#testing...: " + mme.getName());
                outlets.add(i, mme);
            }
        }catch(JSONException jss){
            //hate them
        }

        if(outlets.isEmpty()){
            Toast.makeText(Outlets.this, "No results not found.", Toast.LENGTH_LONG).show();
        } else {
            //Toast.makeText(Outlets.this, response.toString(), Toast.LENGTH_LONG).show();

            //*handle outlet types images
            ImageLoader imageLoader = AppController.getInstance().getImageLoader();
            // Loading image with placeholder and error image
            /*imageLoader.get(Globals.server+"/images/"+"beans.png", ImageLoader.getImageListener(
                    outletsbanner, R.mipmap.carter, R.mipmap.ic_launcher));*/
            populateSpinner(outletspinner);
            outletspinner.setOnItemSelectedListener(new SpinnerSelect());
            //show outlet image
        }
    }

    private void populateSpinner(Spinner sp) {
        // TODO Auto-generated method stub
        locations.clear();
        locations.add("All locations");
        for(int i=0; i<outlets.size(); i++){
            if(locations.contains(outlets.get(i).getLocation())){
                //leave it
            } else {
                locations.add(outlets.get(i).getLocation());
            }
        }
        OutletsSpinnerBaseAdapter dataAdapter = new OutletsSpinnerBaseAdapter(Outlets.this, locations);
        sp.setAdapter(dataAdapter);
    }

    private class SpinnerSelect implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View v, int pos,
                                   long id) {

            if(pos==0){
                adapter = new OutletsBaseAdapter(Outlets.this, outlets);
                outletslistview.setAdapter(adapter);
            } else {
                String text = locations.get(pos);
                shownoutlets.clear();
                for(int y=0; y<outlets.size(); y++){
                    if(outlets.get(y).getLocation().contentEquals(text)){
                        shownoutlets.add(outlets.get(y));
                    }
                }
                adapter = new OutletsBaseAdapter(Outlets.this, shownoutlets);
                outletslistview.setAdapter(adapter);
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }
    }

    private boolean checkInternetConnection() {
        ConnectivityManager conMgr = (ConnectivityManager) getSystemService (Context.CONNECTIVITY_SERVICE);
        // ARE WE CONNECTED TO THE NET
        if (conMgr.getActiveNetworkInfo() != null
                && conMgr.getActiveNetworkInfo().isAvailable()
                && conMgr.getActiveNetworkInfo().isConnected()) {

            return true;

            /* New Handler to start the Menu-Activity
             * and close this Splash-Screen after some seconds.*/
        } else {
            return false;

            /*Intent connectionIntent = new Intent(TheEvoStikLeagueActivity.this, HomeActivity.class);
            TheEvoStikLeagueActivity.this.startActivity(connectionIntent);
            TheEvoStikLeagueActivity.this.finish();*/

        }
    }

}
