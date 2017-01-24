package cartbolt.qui.screens;

import android.app.ProgressDialog;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cartbolt.qui.adapters.CheckoutSpinnerBaseAdapter;
import cartbolt.utils.AppController;
import cartbolt.utils.Cart;
import cartbolt.utils.Globals;
import cartbolt.utils.JSONer;

public class Checkout extends AppCompatActivity {

    private Spinner emode, checkloc, checkarea;
    private TextView crt, del, grand;

    private Button theultimate, cancel;
    private CheckBox checkterms;
    private TextView checktermslink;
    private TextView ckname, ckemail ,ckphone;

    private EditText ckaread;

    //for sending
    private String userid, username, userphone, useremail;
    private String arealoctxt, dectxt, typetxt, ttltxt, mkuptxt, deltxt, grandtxt;
    private String theuserlocation = "Kampala";
    private String theusertime = "bike-15";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        //hide keyboard
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Additional delivery details");


        userid = PreferenceManager.getDefaultSharedPreferences(this).getString("userid", "1");
        username = PreferenceManager.getDefaultSharedPreferences(this).getString("username", "username");
        userphone = PreferenceManager.getDefaultSharedPreferences(this).getString("userphone", "07X2985674");
        useremail = PreferenceManager.getDefaultSharedPreferences(this).getString("useremail", "user@email.com");

        ckname = (TextView) findViewById(R.id.ckname);
        ckemail = (TextView) findViewById(R.id.ckemail);
        ckphone = (TextView) findViewById(R.id.ckphone);

        ckname.setText(username);
        ckphone.setText(userphone);
        ckemail.setText(useremail);

        ckaread = (EditText) findViewById(R.id.ckaread);
        //ckname.setText(PreferenceManager.getDefaultSharedPreferences(this).getString("username", "Username"));
        checkterms = (CheckBox) findViewById(R.id.checkterms);
        checktermslink = (TextView) findViewById(R.id.checktermslink);
        checktermslink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(Checkout.this, Terms.class));
            }
        });

        emode = (Spinner) findViewById(R.id.exitmoder);
        populateSpinner(emode);
        emode.setOnItemSelectedListener(new SpinnerSelect());

        checkloc = (Spinner) findViewById(R.id.checkloc);
        populateLocationsSpinner(checkloc);
        checkloc.setOnItemSelectedListener(new LocationsSpinnerSelect());

        checkarea = (Spinner) findViewById(R.id.checkarea);

        cancel = (Button) findViewById(R.id.btc);
        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
				/*Intent cn = new Intent(Confirmscreen.this, Cartyscreen.class);
				startActivity(cn);*/
            }
        });

        crt = (TextView) findViewById(R.id.delttl);
        del = (TextView) findViewById(R.id.delfee);
        grand = (TextView) findViewById(R.id.delgrand);
    }

    //other methods
    private void populateSpinner(Spinner sp) {
        // TODO Auto-generated method stub
        List<String> list = new ArrayList<String>();
        list.add("Within Kampala    : 1hr - 3000");
        list.add("Within Kampala    : 2hr - 2000");
        list.add("K'la - Nakawa     : 1hr - 5000");
        list.add("K'la - Nakawa     : 2hr - 3000");
        list.add("K'la - Bugolobi   : 1hr - 5000");
        list.add("K'la - Bugolobi   : 2hr - 3000");
        list.add("Withnin Nakawa    : 1hr - 3000");
        list.add("Withnin Nakawa    : 2hr - 2000");
        list.add("Nakawa - Bugolobi : 1hr - 3000");
        list.add("Nakawa Nakawa     : 2hr - 2000");
        list.add("Within Bugolobi   : 1hr - 3000");
        list.add("Withni Bugolobi   : 2hr - 2000");
        CheckoutSpinnerBaseAdapter dataAdapter = new CheckoutSpinnerBaseAdapter(this, list);
        sp.setAdapter(dataAdapter);
    }

    private class SpinnerSelect implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View v, int pos,
                                   long id) {

            switch(pos){
                case 0:
                    Cart.delivery = 3000;
                    crt = (TextView) findViewById(R.id.delttl);
                    Double d = Cart.total;
                    crt.setText(d.toString());

                    del = (TextView) findViewById(R.id.delfee);
                    Double det = Cart.delivery;
                    del.setText(det.toString());

                    grand = (TextView) findViewById(R.id.delgrand);
                    Double mm = Cart.total + Cart.delivery;
                    grand.setText(mm.toString());

                    theusertime = "Within K'la";
                    typetxt = "1";
                    break;
                case 1:
                    Cart.delivery = 2000;
                    crt = (TextView) findViewById(R.id.delttl);
                    Double d1 = Cart.total;
                    crt.setText(d1.toString());

                    del = (TextView) findViewById(R.id.delfee);
                    Double det1 = Cart.delivery;
                    del.setText(det1.toString());

                    grand = (TextView) findViewById(R.id.delgrand);
                    Double mm1 = Cart.total + Cart.delivery;
                    grand.setText(mm1.toString());

                    theusertime = "Within K'la";
                    typetxt = "2";
                    break;
                case 2:
                    Cart.delivery = 5000;
                    crt = (TextView) findViewById(R.id.delttl);
                    Double d2 = Cart.total;
                    crt.setText(d2.toString());

                    del = (TextView) findViewById(R.id.delfee);
                    Double det2 = Cart.delivery;
                    del.setText(det2.toString());

                    grand = (TextView) findViewById(R.id.delgrand);
                    Double mm2 = Cart.total + Cart.delivery;
                    grand.setText(mm2.toString());

                    theusertime = "K'la - Nakawa";
                    typetxt = "3";
                    break;
                case 3:
                    Cart.delivery = 3000;
                    crt = (TextView) findViewById(R.id.delttl);
                    Double d3 = Cart.total;
                    crt.setText(d3.toString());

                    del = (TextView) findViewById(R.id.delfee);
                    Double det3 = Cart.delivery;
                    del.setText(det3.toString());

                    grand = (TextView) findViewById(R.id.delgrand);
                    Double mm3 = Cart.total + Cart.delivery;
                    grand.setText(mm3.toString());

                    theusertime = "K'la - Nakawa";
                    typetxt = "4";
                    break;
                case 4:
                    Cart.delivery = 50000;
                    crt = (TextView) findViewById(R.id.delttl);
                    Double d4 = Cart.total;
                    crt.setText(d4.toString());

                    del = (TextView) findViewById(R.id.delfee);
                    Double det4 = Cart.delivery;
                    del.setText(det4.toString());

                    grand = (TextView) findViewById(R.id.delgrand);
                    Double mm4 = Cart.total + Cart.delivery;
                    grand.setText(mm4.toString());

                    theusertime = "K'la - Bugolobi";
                    typetxt = "5";
                    break;
                case 5:
                    Cart.delivery = 3000;
                    crt = (TextView) findViewById(R.id.delttl);
                    Double d5 = Cart.total;
                    crt.setText(d5.toString());

                    del = (TextView) findViewById(R.id.delfee);
                    Double det5 = Cart.delivery;
                    del.setText(det5.toString());

                    grand = (TextView) findViewById(R.id.delgrand);
                    Double mm5 = Cart.total + Cart.delivery;
                    grand.setText(mm5.toString());

                    theusertime = "Within-K'la";
                    typetxt = "6";
                    break;
                case 6:
                    Cart.delivery = 3000;
                    crt = (TextView) findViewById(R.id.delttl);
                    Double d6 = Cart.total;
                    crt.setText(d6.toString());

                    del = (TextView) findViewById(R.id.delfee);
                    Double det6 = Cart.delivery;
                    del.setText(det6.toString());

                    grand = (TextView) findViewById(R.id.delgrand);
                    Double mm6 = Cart.total + Cart.delivery;
                    grand.setText(mm6.toString());

                    theusertime = "Within-Nakawa";
                    typetxt = "7";
                    break;
                case 7:
                    Cart.delivery = 2000;
                    crt = (TextView) findViewById(R.id.delttl);
                    Double dz = Cart.total;
                    crt.setText(dz.toString());

                    del = (TextView) findViewById(R.id.delfee);
                    Double detz = Cart.delivery;
                    del.setText(detz.toString());

                    grand = (TextView) findViewById(R.id.delgrand);
                    Double mmz = Cart.total + Cart.delivery;
                    grand.setText(mmz.toString());

                    theusertime = "Nakawa - Bugolobi : ";
                    typetxt = "8";
                    break;
                case 8:
                    Cart.delivery = 3000;
                    crt = (TextView) findViewById(R.id.delttl);
                    Double d1d = Cart.total;
                    crt.setText(d1d.toString());

                    del = (TextView) findViewById(R.id.delfee);
                    Double det1d = Cart.delivery;
                    del.setText(det1d.toString());

                    grand = (TextView) findViewById(R.id.delgrand);
                    Double mm1d = Cart.total + Cart.delivery;
                    grand.setText(mm1d.toString());

                    theusertime = "Nakawa - Bugoglobi ";
                    typetxt = "9";
                    break;
                case 9:
                    Cart.delivery = 2000;
                    crt = (TextView) findViewById(R.id.delttl);
                    Double d2r = Cart.total;
                    crt.setText(d2r.toString());

                    del = (TextView) findViewById(R.id.delfee);
                    Double det2r = Cart.delivery;
                    del.setText(det2r.toString());

                    grand = (TextView) findViewById(R.id.delgrand);
                    Double mm2r = Cart.total + Cart.delivery;
                    grand.setText(mm2r.toString());

                    theusertime = "Nakawa - Bugolobi";
                    typetxt = "10";
                    break;
                case 10:
                    Cart.delivery = 3000;
                    crt = (TextView) findViewById(R.id.delttl);
                    Double d3y = Cart.total;
                    crt.setText(d3y.toString());

                    del = (TextView) findViewById(R.id.delfee);
                    Double det3y = Cart.delivery;
                    del.setText(det3y.toString());

                    grand = (TextView) findViewById(R.id.delgrand);
                    Double mm31y = Cart.total + Cart.delivery;
                    grand.setText(mm31y.toString());

                    theusertime = "Within Bugolobi";
                    typetxt = "11";
                    break;
                case 11:
                    Cart.delivery = 2000;
                    crt = (TextView) findViewById(R.id.delttl);
                    Double d311 = Cart.total;
                    crt.setText(d311.toString());

                    del = (TextView) findViewById(R.id.delfee);
                    Double det311 = Cart.delivery;
                    del.setText(det311.toString());

                    grand = (TextView) findViewById(R.id.delgrand);
                    Double mm311 = Cart.total + Cart.delivery;
                    grand.setText(mm311.toString());

                    theusertime = "Within Bugolobi";
                    typetxt = "12";
                    break;

            }


            theultimate = (Button) findViewById(R.id.theultimate);
            theultimate.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
					/*Intent yerr = new Intent(Confirmscreen.this, Start.class);
					startActivity(yerr);*/
                    if (checkterms.isChecked()) {
                        //new OrderingTask().execute();
                        // Tag used to cancel the request
                        try {
                            goGoGo();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {
                        Toast.makeText(Checkout.this, "Please view and confirm with the terms and conditions",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }
    }

    private void populateLocationsSpinner(Spinner sp) {
        // TODO Auto-generated method stub
        List<String> list = new ArrayList<String>();
        list.add("Set location");
        list.add("K'la Central");
        list.add("Nakawa");
        list.add("Bugolobi");
        CheckoutSpinnerBaseAdapter dataAdapter = new CheckoutSpinnerBaseAdapter(this, list);
        sp.setAdapter(dataAdapter);
    }

    private class LocationsSpinnerSelect implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View v, int pos,
                                   long id) {

            switch(pos){
                case 0:
                    populateNullSpinner(checkarea);
                    theuserlocation = "None";
                    Globals.theuserlocation = theuserlocation;
                    break;
                case 1:
                    populateKlaSpinner(checkarea);
                    checkarea.setOnItemSelectedListener(new KlaSpinnerSelect());
                    theuserlocation = "Kampala Central";
                    Globals.theuserlocation = theuserlocation;
                    break;
                case 2:
                    populateNakawaSpinner(checkarea);
                    checkarea.setOnItemSelectedListener(new NakawaSpinnerSelect());
                    theuserlocation = "Nakawa";
                    Globals.theuserlocation = theuserlocation;
                    break;
                case 3:
                    populateBugolobiSpinner(checkarea);
                    checkarea.setOnItemSelectedListener(new BugolobiSpinnerSelect());
                    theuserlocation = "Bugolobi";
                    Globals.theuserlocation = theuserlocation;
                    break;
                default/*case 4*/:
                    theuserlocation = "None";
                    Globals.theuserlocation = theuserlocation;
                    break;
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }
    }

    private void populateNullSpinner(Spinner sp) {
        // TODO Auto-generated method stub
        List<String> list = new ArrayList<String>();
        list.add("Set Area");
        CheckoutSpinnerBaseAdapter dataAdapter = new CheckoutSpinnerBaseAdapter(this, list);
        sp.setAdapter(dataAdapter);
    }

    private void populateKlaSpinner(Spinner sp) {
        // TODO Auto-generated method stub
        List<String> list = new ArrayList<String>();
        list.add("Set Area");
        list.add("Sheraton Hotel");
        list.add("Nasser Road");
        list.add("Kiseka");
        list.add("Dewinton Road");
        list.add("Serena Hotel");
        list.add("Uganda House");
        list.add("Nakasero Hill");
        list.add("City Square");
        list.add("Golf Course");
        list.add("Centenary Park");
        CheckoutSpinnerBaseAdapter dataAdapter = new CheckoutSpinnerBaseAdapter(this, list);
        sp.setAdapter(dataAdapter);
    }

    private class KlaSpinnerSelect implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View v, int pos,
                                   long id) {

            switch(pos){
                case 0:
                    //
                    break;
                case 1:
                    arealoctxt = "Sheraton Hotel - "+theuserlocation;
                    break;
                case 2:
                    arealoctxt = "Nasser Road - "+theuserlocation;
                    break;
                case 3:
                    arealoctxt = "Kiseka - "+theuserlocation;
                    break;
                case 4:
                    arealoctxt = "Dewinton Road - "+theuserlocation;
                    break;
                case 5:
                    arealoctxt = "Serena Hotel - "+theuserlocation;
                    break;
                case 6:
                    arealoctxt = "Uganda House - "+theuserlocation;
                    break;
                case 7:
                    arealoctxt = "Nakasero Hill - "+theuserlocation;
                    break;
                case 8:
                    arealoctxt = "City Square - "+theuserlocation;
                    break;
                case 9:
                    arealoctxt = "Golf Course - "+theuserlocation;
                    break;
                case 10:
                    arealoctxt = "Centenary Park - "+theuserlocation;
                    break;
                default/*case 4*/:
                    //
                    break;
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }
    }

    private void populateNakawaSpinner(Spinner sp) {
        // TODO Auto-generated method stub
        List<String> list = new ArrayList<String>();
        list.add("Set Area");
        list.add("URA");
        list.add("MUBS");
        list.add("UMA");
        list.add("Akamwesi");
        list.add("Mbuya");
        list.add("New Vision");
        list.add("Lugogo");
        CheckoutSpinnerBaseAdapter dataAdapter = new CheckoutSpinnerBaseAdapter(this, list);
        sp.setAdapter(dataAdapter);
    }

    private class NakawaSpinnerSelect implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View v, int pos,
                                   long id) {

            switch(pos){
                case 0:
                    //
                    break;
                case 1:
                    arealoctxt = "URA - "+theuserlocation;
                    break;
                case 2:
                    arealoctxt = "MUBS - "+theuserlocation;
                    break;
                case 3:
                    arealoctxt = "UMA - "+theuserlocation;
                    break;
                case 4:
                    arealoctxt = "Akamwesi - "+theuserlocation;
                    break;
                case 5:
                    arealoctxt = "Mbuya - "+theuserlocation;
                    break;
                case 6:
                    arealoctxt = "New VIsion - "+theuserlocation;
                    break;
                case 7:
                    arealoctxt = "Lugogo - "+theuserlocation;
                    break;
                default/*case 4*/:
                    //
                    break;
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }
    }

    private void populateBugolobiSpinner(Spinner sp) {
        // TODO Auto-generated method stub
        List<String> list = new ArrayList<String>();
        list.add("Set Area");
        list.add("Village Mall");
        list.add("Silver Springs");
        list.add("MTN");
        list.add("UCC");
        list.add("Royal Suites");
        list.add("Mulwana Road");
        CheckoutSpinnerBaseAdapter dataAdapter = new CheckoutSpinnerBaseAdapter(this, list);
        sp.setAdapter(dataAdapter);
    }

    private class BugolobiSpinnerSelect implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View v, int pos,
                                   long id) {

            switch(pos){
                case 0:
                    //
                    break;
                case 1:
                    arealoctxt = "Village Mall - "+theuserlocation;
                    break;
                case 2:
                    arealoctxt = "Silver Springs - "+theuserlocation;
                    break;
                case 3:
                    arealoctxt = "MTN - "+theuserlocation;
                    break;
                case 4:
                    arealoctxt = "UCC - "+theuserlocation;
                    break;
                case 5:
                    arealoctxt = "Royal Suites - "+theuserlocation;
                    break;
                case 6:
                    arealoctxt = "Mulwana Road - "+theuserlocation;
                    break;
                default/*case 4*/:
                    //
                    break;
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }
    }

    private void goGoGo() throws JSONException {
        String tag_json_arry = "json_array_req";

        ttltxt = String.valueOf(Cart.total);
        mkuptxt = String.valueOf(Cart.markup);
        deltxt = String.valueOf(Cart.delivery);
        grandtxt = String.valueOf(Cart.total + Cart.delivery);

        dectxt = ckaread.getText().toString();
        if(dectxt.isEmpty()){
            dectxt = "";
        }

        String url = /*Globals.server*/"http://www.cartbolt.ug/raw" + "/order.php?j=" + JSONer.buildJSONOrderArray(userid, username, userphone, useremail,
                arealoctxt, dectxt, typetxt, ttltxt, mkuptxt, deltxt, grandtxt).toString();
        System.out.println(url);

        final ProgressDialog pDialog = new ProgressDialog(Checkout.this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Log.d(TAG, response.toString());
                        pDialog.hide();
                        System.out.println(response.toString());
                        Toast.makeText(Checkout.this, "Received", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Checkout.this, Homer.class));
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
    }
}
