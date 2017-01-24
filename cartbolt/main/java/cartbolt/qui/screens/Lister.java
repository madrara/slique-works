package cartbolt.qui.screens;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import cartbolt.utils.AppController;
import cartbolt.utils.Globals;

public class Lister extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private ListerItemBaseAdapter adapter;
    private ListView lister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lister);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Products");

        lister = (ListView) findViewById(R.id.lister);
        fetchItems();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchItems();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lister, menu);
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

        //for menu
        switch (item.getItemId()) {
            //case R.id.action_search:
            // Intent carter = new Intent(this, Search.class);
            // startActivity(carter);
            //return true;
            case R.id.action_cart:
                Intent cart = new Intent(this, Carty.class);
                startActivity(cart);
                return true;
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

    private void fetchItems(){
        // Tag used to cancel the request
        String tag_json_arry = "json_array_req";
        String url = Globals.server+"/items?id="+Globals.subcategoryid;

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
                        Toast.makeText(Lister.this, "Encountered error. Please Try again", Toast.LENGTH_LONG).show();
                        pDialog.hide();
                    }
                });

                // Adding request to request queue
                AppController.getInstance().addToRequestQueue(req, tag_json_arry);
            } else {
                Toast.makeText(Lister.this, "Unable to connect to the internet", Toast.LENGTH_LONG).show();
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
            Toast.makeText(Lister.this, "No results not found.", Toast.LENGTH_LONG).show();
        } else {

            adapter = new ListerItemBaseAdapter(Lister.this, results);
            lister.setAdapter(adapter);
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

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
