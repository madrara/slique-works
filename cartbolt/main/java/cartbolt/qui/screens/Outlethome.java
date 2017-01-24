package cartbolt.qui.screens;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

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

import cartbolt.qui.adapters.ListerItemBaseAdapter;
import cartbolt.qui.adapters.TabsAdapterLarge;
import cartbolt.qui.adapters.TabsAdapterSmall;
import cartbolt.qui.entities.Category;
import cartbolt.qui.entities.Item;
import cartbolt.qui.entities.Subcategory;
import cartbolt.utils.AppController;
import cartbolt.utils.Globals;

public class Outlethome extends AppCompatActivity implements SearchView.OnQueryTextListener {

    ViewPager mViewPager;
    private SearchView mSearchView;

    //for task
    String query = "/categories";
    private ImageView homebanner;

    static List<Category> categories = new ArrayList<Category>();
    static List<Subcategory> subcategories = new ArrayList<Subcategory>();

    static int TAB_COUNT;
    static List<String> values;

    public static int getTabCounts(){
        return TAB_COUNT;
    }

    public static List<Category> getCategories(){
        return categories;
    }

    public static List<Subcategory> getSubcategories(){
        return subcategories;
    }

    public static List<String> getTabValues(){
        return values;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outlethome);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle(Globals.outletname);
        homebanner = (ImageView) findViewById(R.id.homebanner);
        proceed();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Globals.outletid.isEmpty()){
            startActivity(new Intent(this, Homer.class));
        } else {
            proceed();
        }
    }

    private void proceed(){
        if(Globals.outletid.isEmpty()){
            startActivity(new Intent(this, Homer.class));
        } else {
            if (Globals.sizer.contentEquals("large")) {
                fetchTheLargeGuy();
            } else {
                fetchTheSmallGuy();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_outlethome, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search_listing);
        mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        mSearchView.setOnQueryTextListener(this);

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

    private void fetchTheLargeGuy(){
        // Tag used to cancel the request
        String tag_json_arry = "json_array_req";
        String url = Globals.server+"/categories?id="+Globals.outletid;

        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(url);
        if(entry != null){
            try {
                String data = new String(entry.data, "UTF-8");
                // handle data, like converting it to xml, json, bitmap etc.,
                try {
                    decodeDataLarge(new JSONArray(data.toString()));
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
                                decodeDataLarge(response);
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
                                decodeDataLarge(response);
                                pDialog.hide();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //VolleyLog.d(TAG, "Error: " + error.getMessage());
                        Toast.makeText(Outlethome.this, "Encountered error. Please Try again", Toast.LENGTH_LONG).show();
                        pDialog.hide();
                    }
                });

                // Adding request to request queue
                AppController.getInstance().addToRequestQueue(req, tag_json_arry);
            } else {
                Toast.makeText(Outlethome.this, "Unable to connect to the internet", Toast.LENGTH_LONG).show();
            }
        }

    }

    private void decodeDataLarge(JSONArray response) {
        try{
            JSONArray json = new JSONArray(response.toString());
            System.out.println("#2 JSON: " + json.toString());
            categories.clear();
            for(int i=0; i<json.length(); i++){
                JSONObject chimps = json.getJSONObject(i);
                Category mme = new Category(Integer.parseInt(chimps.get("id").toString()), chimps.get("name").toString());
                System.out.println("#testing...: " + mme.getName());
                categories.add(i, mme);
            }
        }catch(JSONException jss){
            //hate them
        }

        if(categories.isEmpty()){
            Toast.makeText(Outlethome.this, "No results not found.", Toast.LENGTH_LONG).show();
        } else {
            TAB_COUNT = categories.size();
            //Add categories as tab titles
            values = new ArrayList<String>();
            for(int g=0; g<TAB_COUNT; g++){
                values.add(categories.get(g).getName());
            }
            mViewPager = (ViewPager) findViewById(R.id.viewPager);
            mViewPager.setAdapter(new TabsAdapterLarge(getSupportFragmentManager()));

            //Globals.outletname
            ImageLoader imageLoader = AppController.getInstance().getImageLoader();
            // Loading image with placeholder and error image
            imageLoader.get(Globals.images+Globals.outletname.toLowerCase()+".png", ImageLoader.getImageListener(
                    homebanner, R.mipmap.carter, R.mipmap.ic_launcher));
                            /*Picasso.with(Outlethome.this)
                                    .load(Globals.server + "/images/"+"cabbage.jpg"/*+".jpg")*///)
            //.placeholder(R.mipmap.carter).fit().into(homebanner);
            //show outlet image
        }
    }

    private void fetchTheSmallGuy(){
        // Tag used to cancel the request
        String tag_json_arry = "json_array_req";
        String url = Globals.server+"/categories?id="+Globals.outletid;

        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(url);
        if(entry != null){
            try {
                String data = new String(entry.data, "UTF-8");
                // handle data, like converting it to xml, json, bitmap etc.,
                try {
                    decodeDataSmall(new JSONArray(data.toString()));
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
                                decodeDataSmall(response);
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
                                decodeDataSmall(response);
                                pDialog.hide();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //VolleyLog.d(TAG, "Error: " + error.getMessage());
                        Toast.makeText(Outlethome.this, "Encountered error. Please Try again", Toast.LENGTH_LONG).show();
                        pDialog.hide();
                    }
                });

                // Adding request to request queue
                AppController.getInstance().addToRequestQueue(req, tag_json_arry);
            } else {
                Toast.makeText(Outlethome.this, "Unable to connect to the internet", Toast.LENGTH_LONG).show();
            }
        }

    }

    private void decodeDataSmall(JSONArray response) {
        try{
            JSONArray json = new JSONArray(response.toString());
            System.out.println("#2 JSON: " + json.toString());
            categories.clear();
            for(int i=0; i<json.length(); i++){
                JSONObject chimps = json.getJSONObject(i);
                Category mme = new Category(Integer.parseInt(chimps.get("id").toString()), chimps.get("name").toString());
                System.out.println("#testing...: " + mme.getName());
                categories.add(i, mme);
            }
        }catch(JSONException jss){
            //hate them
        }

        if(categories.isEmpty()){
            Toast.makeText(Outlethome.this, "No results not found.", Toast.LENGTH_LONG).show();
        } else {
            TAB_COUNT = categories.size();
            //Add categories as tab titles
            values = new ArrayList<String>();
            for(int g=0; g<TAB_COUNT; g++){
                values.add(categories.get(g).getName());
            }
            mViewPager = (ViewPager) findViewById(R.id.viewPager);
            mViewPager.setAdapter(new TabsAdapterSmall(getSupportFragmentManager()));

            //Globals.outletname
            ImageLoader imageLoader = AppController.getInstance().getImageLoader();
            // Loading image with placeholder and error image
            imageLoader.get(Globals.images + Globals.outletname.toLowerCase()+".png", ImageLoader.getImageListener(
                    homebanner, R.mipmap.carter, R.mipmap.ic_launcher));
                            /*Picasso.with(Outlethome.this)
                                    .load(Globals.server + "/images/"+"cabbage.jpg"/*+".jpg")*///)
            //.placeholder(R.mipmap.carter).fit().into(homebanner);
            //show outlet image
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
    public boolean onQueryTextChange(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        //Toast.makeText(this, "Searching for "+s, Toast.LENGTH_LONG).show();
        setContentView(R.layout.screen_search);

        if(checkInternetConnection()){
            // Tag used to cancel the request
            String tag_json_arry = "json_array_req";
            String url = Globals.server+"/search"/*.php*/+"?id="+s.replace(" ", "%20").replace("\'", "%20")+"&id="+Globals.outletid;

            final ProgressDialog pDialog = new ProgressDialog(Outlethome.this);
            pDialog.setMessage("Searching...");
            pDialog.show();

            JsonArrayRequest req = new JsonArrayRequest(url,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            //Log.d(TAG, response.toString());
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
                            pDialog.hide();
                            if(results.isEmpty()){
                                Toast.makeText(Outlethome.this, "No results not found.", Toast.LENGTH_LONG).show();
                            } else {

                                ListerItemBaseAdapter thenewadapter = new ListerItemBaseAdapter(Outlethome.this, results);
                                ListView searchlister = (ListView) findViewById(R.id.searchlister);
                                searchlister.setAdapter(thenewadapter);
                            }
                            //pDialog.hide();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //VolleyLog.d(TAG, "Error: " + error.getMessage());
                    //pDialog.hide();
                }
            });

            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(req, tag_json_arry);
        } else {
            Toast.makeText(Outlethome.this, "Unable to connect to the internet", Toast.LENGTH_LONG).show();
        }


        /*final ProgressDialog dialogs = new ProgressDialog(this);
        dialogs.setMessage("Wait...");
        dialogs.setCancelable(false);
        dialogs.show();

        /*searchresults.clear();
        for(int i = 0; i < results.size(); i++){
            Item prd = results.get(i);
            if(prd.getName().equalsIgnoreCase(s)/* || prd.getLocation().equalsIgnoreCase(s)*///){
        //searchresults.add(results.get(i));
            /*}
        }
        if(searchresults.isEmpty()){
            Toast.makeText(FragmentTabSmall.this.getActivity(), "No results found for "+s, Toast.LENGTH_LONG).show();
        } else {
            adapter.setPieceList(searchresults);
            adapter.notifyDataSetChanged();
        }
        dialogs.dismiss();*/
        return true;
    }

}
