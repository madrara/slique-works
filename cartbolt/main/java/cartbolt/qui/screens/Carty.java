package cartbolt.qui.screens;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;

import cartbolt.qui.adapters.CartBaseAdapter;
import cartbolt.qui.entities.Item;
import cartbolt.utils.Cart;

public class Carty extends AppCompatActivity {

    private ListView list;
    private CartBaseAdapter adapter;;

    private Button pro, clear;
    private TextView ttl, label, /*markup,*/ dateText;

    // PREFS
    SharedPreferences prefs;
    String firstTime;
    private Boolean check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carty);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Shopping Cart");

        //getSupportActionBar().setTitle(""););
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        check = prefs.getBoolean(firstTime, true);

        Cart.total = 0;
        for(int rey = 0; rey<Cart.cartlist.size(); rey++){
            System.out.println("Counting : " + rey);
            double deb = Cart.cartlist.get(rey).getPrice() * Cart.cartlist.get(rey).getQuantity();
            Cart.total = Cart.total + deb;
        }

        label = (TextView) findViewById(R.id.tvttl);
        pro = (Button) findViewById(R.id.proceed);
        pro.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if(Cart.total < 10000){
                    Toast.makeText(Carty.this, "You need atleast Ush.10,000/= worth of Shopping", Toast.LENGTH_SHORT).show();
                } else {
                    if(/*check*/prefs.getString("usertype", "skipper").contentEquals("skipper")) {
                        //Toast.makeText(Carty.this, "nigga aint registered", Toast.LENGTH_SHORT).show();
                        Intent we = new Intent(Carty.this, Nonregcheckout.class);
                        startActivity(we);
                    } else {
                        Intent we = new Intent(Carty.this, Checkout.class);
                        startActivity(we);
                    }
                }
            }
        });

        adapter = new CartBaseAdapter(Carty.this, Cart.cartlist/*testitems*/);

        // Locate the ListView in listview_main.xml and bind list using adapter
        list = (ListView) findViewById(R.id.listviewd);
        list.setAdapter(adapter);
        //markup = (TextView) findViewById(R.id.markup);
        if(Cart.total >= 100000){
            Cart.markup = Cart.total * 0.05;
            //markup.setText(String.valueOf(Cart.markup));
        } else if(Cart.total >= 40000 && Cart.total < 100000){
            Cart.markup = Cart.total * 0.1;
            //markup.setText(String.valueOf(Cart.markup));
        } else if(Cart.total < 40000){
            Cart.markup = Cart.total * 0.15;
            //markup.setText(String.valueOf(Cart.markup));
        }
        ttl = (TextView) findViewById(R.id.tvttlb);
        Cart.marktotal = Cart.total/* + Cart.markup*/;
        ttl.setText(String.valueOf(Cart.marktotal)/*.split(".")[0]*/);

        dateText = (TextView) findViewById(R.id.dateText);
        Date now = new Date();
        DateFormat formatter = DateFormat.getDateInstance().getInstance();
        String dateStr = formatter.format(now);
        dateText.setText(dateStr.split(" ")[0]);

        clear = (Button) findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Cart.cartlist.clear();
                Cart.total = 0;
                adapter.notifyDataSetChanged();
                //final String theprice = String.valueOf(pp.getPrice().intValue());
                ttl.setText("UGX "+String.valueOf(Cart.total)+"/=");
                Toast.makeText(Carty.this, "Cart has been emptied", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_carty, menu);
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
                //Intent cart = new Intent(this, Carty.class);
                //startActivity(cart);
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

}
