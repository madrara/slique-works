package cartbolt.qui.screens;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cartbolt.qui.entities.Item;
import cartbolt.utils.AppController;
import cartbolt.utils.Cart;
import cartbolt.utils.Globals;

public class Details extends AppCompatActivity {

    private TextView n, p, etspec;
    private String id, name, price, description, thepic;
    private Intent intent;
    private Spinner deux, closer;
    private ImageView detfullimgs;

    private EditText etQty, etspecnone;
    private Button b;
    private int yd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //hide keyboard
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Product Details");

        setContentView(R.layout.activity_details);

        n = (TextView) findViewById(R.id.detnamenone);
        p = (TextView) findViewById(R.id.detpricenone);
        etQty = (EditText) findViewById(R.id.cartqot);
        etspecnone = (EditText) findViewById(R.id.etspecnone);
        b = (Button) findViewById(R.id.cartyjet);
        detfullimgs = (ImageView) findViewById(R.id.detfullimgs);

        goRightAhead();

    }

    //other methods
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_no_search, menu);
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

    private void goRightAhead(){
        // Tag used to cancel the request
        String tag_json_arry = "json_array_req";

        String url = Globals.server/*"http://www.cartbolt.ug/raw"*/+"/details?id="+Globals.itemid;

        System.out.println("Starting here");

        //ProgressDialog pDialog = new ProgressDialog(this);
        //pDialog.setMessage("Loading...");
        //pDialog.show();

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
                                Item mme = new Item(Integer.parseInt(chimps.get("id")
                                        .toString()), chimps.get("name").toString(),
                                        Double.parseDouble(chimps.get("price")
                                                .toString()), "empty");
                                mme.setImage(chimps.get("pic").toString());
                                thepic = chimps.get("pic").toString();
                                //mme.setSplitter(chimps.get("caselevel").toString());
                                results.add(i, mme);
                            }
                        }catch (JSONException jss){

                        }

                        if(results.isEmpty()){
                            Toast.makeText(Details.this, "No results not found.", Toast.LENGTH_LONG).show();
                        } else {
                            yd = results.get(0).getId();
                            name = results.get(0).getName();
                            price = results.get(0).getPrice().toString();

                            n.setText(name);
                            p.setText(price);

                            b.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View arg0) {

                                    //Toast.makeText(Details.this, "Clicked!!!", Toast.LENGTH_SHORT).show();

                                    String etTest = etQty.getText().toString();
                                    if(etTest.isEmpty()){ etTest = "1";}
                                    if(Integer.parseInt(etTest) > 0){
                                        Item bella = new Item(name, Double.parseDouble(price));
                                        bella.setQuantity(Integer.parseInt(etTest));
                                        bella.setId(yd);
                                        bella.setImage(thepic);
                                        bella.setOutletName(Globals.outletname);
                                        description = etspecnone.getText().toString();
                                        if(description.isEmpty()){
                                            bella.setDescription("");
                                        } else {
                                            bella.setDescription(description);
                                        }
                                        bella.inCart(bella);
                                        Cart.neworders.add(String.valueOf(yd) + ":" + etQty.getText().toString());
                                        Details.this.finish();
                                        Toast.makeText(Details.this, "Item has been added to Cart", Toast.LENGTH_SHORT).show();
                                        //Details.this.finish();
                                    } else {
                                        Toast.makeText(Details.this, "Please set Quantity first", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });

                            ImageLoader imageLoader = AppController.getInstance().getImageLoader();
                            // Loading image with placeholder and error image
                            imageLoader.get(Globals.images + results.get(0).getImage(), ImageLoader.getImageListener(
                                    detfullimgs, R.mipmap.carter, R.mipmap.carter));

                        }
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

        // Adding request to request queue
        //AppController.getInstance().addToRequestQueue(req, tag_json_arry);

    }

}
