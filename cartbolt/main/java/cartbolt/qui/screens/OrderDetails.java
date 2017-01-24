package cartbolt.qui.screens;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cartbolt.qui.adapters.OrderCartBaseAdapter;
import cartbolt.qui.entities.OrderItem;
import cartbolt.utils.AppController;
import cartbolt.utils.Globals;

public class OrderDetails extends AppCompatActivity {

    String id;
    private ListView list;
    OrderCartBaseAdapter adapter;
    //List<OrderItem> tiers = new ArrayList<OrderItem>();

    private Button pro;
    private TextView ttl, label,/* markup,*/ dateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Order Details");
        list = (ListView) findViewById(R.id.listviewd);

        id = getIntent().getStringExtra("id");
        //intialize();
        // Tag used to cancel the request
        String tag_json_arry = "json_array_req";
        String url = Globals.server+"/order?id="+id;

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        System.out.println("#2 URL ****************************** : " + url);

        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Log.d(TAG, response.toString());
                        try{
                            JSONArray json = new JSONArray(response.toString());
                            System.out.println("#2 JSON ****************************** : " + json.toString());

                            JSONArray orderdetails = json.getJSONArray(0);
                            JSONArray ownerdetails = json.getJSONArray(1);

                            JSONArray products = json.getJSONArray(2);
                            Globals.cartlist.clear();

                            //tiers.clear();
                            for(int i=0; i<products.length(); i++){
                                JSONObject chimps = products.getJSONObject(i);
                                System.out.println("#HERE - JSON AAA: *************** " + products.length());
                                System.out.println("#HERE - JSON OrderDetails print: *************** " + products.getJSONObject(i).toString());
                                //Toast.makeText(OrderDetails.this, products.getJSONObject(i).toString(), Toast.LENGTH_LONG).show();
                                //Toast.makeText(OrderDetails.this, "Listing ******** "+chimps.get("id").toString(), Toast.LENGTH_LONG).show();
                                OrderItem mme = new OrderItem(Integer.parseInt(chimps.get("id").toString()), chimps.get("product").toString(), chimps.get("name").toString(),
                                        chimps.get("order").toString(), chimps.get("quantity").toString(),
                                        chimps.get("outlet").toString(), chimps.get("description").toString(),
                                        chimps.get("pic").toString(), chimps.get("person").toString(), chimps.get("price").toString());
                                //chimps.set("#testing...: " + mme.getName());
                                //Toast.makeText(OrderDetails.this, "Saved ******** "+mme.getName().toString(), Toast.LENGTH_LONG).show();
                                //Globals.cartlist.add(i, mme);
                                Globals.cartlist.add(i, mme);
                                //tiers.add(i, mme);
                            }

                            JSONArray counts = json.getJSONArray(0);
                            String userid = counts.getJSONObject(0).get("id").toString();
                            String username = counts.getJSONObject(0).get("name").toString();
                            String userlocation = counts.getJSONObject(0).get("location").toString();
                            String userdelivery = counts.getJSONObject(0).get("delivery").toString();
                            String userordertime = counts.getJSONObject(0).get("ordertime").toString();
                            //Globals.products = counts.getJSONObject(0).get("products").toString();

                            Globals.total = 0;
                            for(int rey = 0; rey<Globals.cartlist.size(); rey++){
                                System.out.println("Counting : " + rey);
                                double deb = Double.parseDouble(Globals.cartlist.get(rey).getPrice()) * Double.parseDouble(Globals.cartlist.get(rey).getQuantity());
                                Globals.total = Globals.total + deb;
                            }

                            label = (TextView) findViewById(R.id.tvttl);
                            pro = (Button) findViewById(R.id.orderclear);
                            pro.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View arg0) {
                                    if(Globals.total < 10000){
                                        Toast.makeText(OrderDetails.this, "You need atleast Ush.10,000/= worth of Shopping", Toast.LENGTH_SHORT).show();
                                    } else {
                                        //yeah!!
                                    }
                                }
                            });

                            // Locate the ListView in listview_main.xml and bind list using adapter
                            //list = (ListView) findViewById(R.id.listviewd);
                            //list.setAdapter(adapter);
                            //markup = (TextView) findViewById(R.id.markup);
                            if(Globals.total >= 100000){
                                Globals.markup = Globals.total * 0.05;
                                //markup.setText(String.valueOf(Globals.markup));
                            } else if(Globals.total >= 40000 && Globals.total < 100000){
                                Globals.markup = Globals.total * 0.1;
                                //markup.setText(String.valueOf(Globals.markup));
                            } else if(Globals.total < 40000){
                                Globals.markup = Globals.total * 0.15;
                                //markup.setText(String.valueOf(Globals.markup));
                            }

                            ttl = (TextView) findViewById(R.id.tvttlb);
                            Globals.marktotal = Globals.total/* + Globals.markup*/;
                            //ttl.setText(String.valueOf(Globals.marktotal)/*.split(".")[0]*/);

                            switch(userdelivery){
                                case "1":
                                    ttl.setText(String.valueOf(Globals.marktotal + 3000.0));
                                    break;
                                case "2":
                                    ttl.setText(String.valueOf(Globals.marktotal + 2000.0));
                                    break;
                                case "3":
                                    ttl.setText(String.valueOf(Globals.marktotal + 5000.0));
                                    break;
                                case "4":
                                    ttl.setText(String.valueOf(Globals.marktotal + 3000.0));
                                    break;
                                case "5":
                                    ttl.setText(String.valueOf(Globals.marktotal + 5000.0));
                                    break;
                                case "6":
                                    ttl.setText(String.valueOf(Globals.marktotal + 3000.0));
                                    break;
                                case "7":
                                    ttl.setText(String.valueOf(Globals.marktotal + 3000.0));
                                    break;
                                case "8":
                                    ttl.setText(String.valueOf(Globals.marktotal + 2000.0));
                                    break;
                                case "9":
                                    ttl.setText(String.valueOf(Globals.marktotal + 3000.0));
                                    break;
                                case "10":
                                    ttl.setText(String.valueOf(Globals.marktotal + 2000.0));
                                    break;
                                case "11":
                                    ttl.setText(String.valueOf(Globals.marktotal + 3000.0));
                                    break;
                                case "12":
                                    ttl.setText(String.valueOf(Globals.marktotal + 2000.0));
                                    break;
                                default:
                                    ttl.setText(String.valueOf(Globals.marktotal));
                                    break;
                            }

                            dateText = (TextView) findViewById(R.id.dateText);
                            //Date now = new Date();
                            //DateFormat formatter = DateFormat.getDateInstance().getInstance();
                            //String dateStr = formatter.format(now);
                            dateText.setText(userordertime.split(" ")[0]);

                            //adapter = new CartBaseAdapter(OrderDetails.this, Globals.cartlist);
                            //list.setAdapter(adapter);

                        }catch(JSONException jss){
                            //hate them
                        }

                        //Rendering
                        adapter = new OrderCartBaseAdapter(OrderDetails.this, Globals.cartlist);
                        list.setAdapter(adapter);

                        pDialog.hide();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //VolleyLog.d(TAG, "Error: " + error.getMessage());
                pDialog.hide();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req, tag_json_arry);

    }

    private void intialize() {
        // Tag used to cancel the request
        String tag_json_arry = "json_array_req";
        String url = Globals.server+"/order.php?id="+id;

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
            }*/

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


        /*} else*/ {

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
                        Toast.makeText(OrderDetails.this, "Encountered error. Please Try again", Toast.LENGTH_LONG).show();
                        pDialog.hide();
                    }
                });

                // Adding request to request queue
                //AppController.getInstance().addToRequestQueue(req, tag_json_arry);
            } else {
                Toast.makeText(OrderDetails.this, "Unable to connect to the internet", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void decodeData(JSONArray response) {
        //Toast.makeText(OrderDetails.this, response.toString(), Toast.LENGTH_LONG).show();
        //System.out.println("#2 JSON OrderDetails: *************** " + response.toString());

        Globals.cartlist.clear();
        //tiers.clear();
        try{
            JSONArray json = new JSONArray(response.toString());
            System.out.println("#2 JSON: " + json.toString());

            JSONArray orderdetails = json.getJSONArray(0);
            /*Globals.fresh = counts.getJSONObject(0).get("fresh").toString();
            Globals.finished = counts.getJSONObject(0).get("finished").toString();
            Globals.orders = counts.getJSONObject(0).get("orders").toString();
            Globals.users = counts.getJSONObject(0).get("users").toString();
            Globals.outletcount = counts.getJSONObject(0).get("outlets").toString();
            Globals.products = counts.getJSONObject(0).get("products").toString();*/

            JSONArray ownerdetails = json.getJSONArray(1);
            /*Globals.outlets.clear();
            for(int i=0; i<outs.length(); i++){
                JSONObject chimps = outs.getJSONObject(i);
                Outlet mme = new Outlet(chimps.get("id").toString(), chimps.get("name").toString(),
                        chimps.get("location").toString(), chimps.get("typer").toString(), chimps.get("sizer").toString());
                //chimps.set("#testing...: " + mme.getName());
                Globals.outlets.add(i, mme);
            }*/

            //System.out.println("#2 JSON OrderDetails print: *************** " + json.getJSONArray(2).toString());
            //Toast.makeText(OrderDetails.this, json.getJSONArray(2).toString(), Toast.LENGTH_LONG).show();
            JSONArray products = json.getJSONArray(2);
            //Globals.cartlist.clear();
            for(int i=0; i<products.length(); i++){
                JSONObject chimps = products.getJSONObject(i);
                System.out.println("#HERE - JSON OrderDetails print: *************** " + products.getJSONObject(i).toString());
                Toast.makeText(OrderDetails.this, products.getJSONObject(i).toString(), Toast.LENGTH_LONG).show();
                OrderItem mme = new OrderItem(Integer.parseInt(chimps.get("id").toString()), chimps.get("product").toString(), chimps.get("name").toString(),
                        chimps.get("order").toString(), chimps.get("quantity").toString(),
                        chimps.get("outlet").toString(), chimps.get("description").toString(),
                        chimps.get("pic").toString(), chimps.get("person").toString(), chimps.get("price").toString());
                //Globals.cartlist.add(i, mme);
                //Globals.cartlist.add(i, mme);

                Globals.cartlist.add(i, mme);
                //tiers.add(i, mme);
            }

            //CartBaseAdapter adapter = new CartBaseAdapter(OrderDetails.this, tiers);
            //list.setAdapter(adapter);

        }catch(JSONException jss){
            //hate them
        }
        OrderCartBaseAdapter adapter = new OrderCartBaseAdapter(OrderDetails.this, Globals.cartlist/*tiers*/);
        list.setAdapter(adapter);

    }

    private boolean checkInternetConnection() {
        ConnectivityManager conMgr = (ConnectivityManager) getSystemService (Context.CONNECTIVITY_SERVICE);
        // ARE WE CONNECTED TO THE NET
        if (conMgr.getActiveNetworkInfo() != null
                && conMgr.getActiveNetworkInfo().isAvailable()
                && conMgr.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            return false;
        }
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
            this.onBackPressed();

        }

        return super.onOptionsItemSelected(item);
    }

}
