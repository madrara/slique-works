package com.example.flash.superkatale;

import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class AddCart extends AppCompatActivity {
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "description";
    public static final String KEY_FULLD = "fulldescription";
    public static final String KEY_SPKTID = "supermkt_id";
    public static final String KEY_PHONE = "phone_number";

    public static final String KEY_SPKTNAME = "supermkt_name";
    public static final String KEY_PRICE = "price";
    public static final String KEY_URL = "image";
    public static final String KEY_QUANTITY = "quantity";

    String name;
    String item_price;
    String fulldesc;
    String item_id;
    String spmkt_name;
    String spt_name;
    String spt_id;
    String url;

    TextView itemid;
    TextView item_name;
    TextView fulldescription;
    TextView spmtname;
    TextView spmtid;
    TextView txitemprice;
    TextView txurl;
    TextView txtemail;
    EditText qnty;
    NetworkImageView imageView;
    private ImageLoader imageLoader;
    Button AddCart;
    Button viewCart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cart);
        itemid = (TextView)findViewById(R.id.txitemid);
        item_name = (TextView)findViewById(R.id.txname);
        fulldescription = (TextView)findViewById(R.id.txfulldec);
        spmtname = (TextView)findViewById(R.id.txtsptname);
        spmtid = (TextView)findViewById(R.id.txtspmkid);
        txitemprice = (TextView)findViewById(R.id.textView11);
        txurl = (TextView)findViewById(R.id.tximage);
        AddCart = (Button)findViewById(R.id.button3);
        viewCart = (Button)findViewById(R.id.viewcart);
        qnty = (EditText)findViewById(R.id.editText2);
        txtemail = (TextView)findViewById(R.id.txtemai);

        //spinner values
        imageView = (NetworkImageView)findViewById(R.id.imageView);

        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String email = sharedPreferences.getString(Config.EMAIL_SHARED_PREF, "Not Available");
        txtemail.setText(email);


        Intent i = getIntent();
        item_id = i.getStringExtra(KEY_ID);
        fulldesc = i.getStringExtra(KEY_FULLD);
        name = i.getStringExtra(KEY_NAME);
        spt_name = i.getStringExtra(KEY_SPKTNAME);
        spt_id = i.getStringExtra(KEY_SPKTID);
        item_price = i.getStringExtra(KEY_PRICE);
        url = i.getStringExtra(KEY_URL);
        txurl.setText(url);
        txitemprice.setText(item_price);
        spmtid.setText(spt_id);
        spmtname.setText(spt_name);
        fulldescription.setText(fulldesc);
        item_name.setText(name);
        itemid.setText(item_id);

        LoadImage();

        AddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Confirm_Purchase();
                qnty.setText("");

            }
        });
        viewCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent viewcart = new Intent(getApplicationContext(),ViewCart.class);
                String phone = ((TextView)findViewById(R.id.txtemai)).getText().toString();
                String supermktname = ((TextView)findViewById(R.id.txtsptname)).getText().toString();
                viewcart.putExtra(KEY_NAME,supermktname);
                viewcart.putExtra(KEY_PHONE,phone);
                startActivityForResult(viewcart,100);

            }
        });
    }
    private void LoadImage(){
        String img_url = txurl.getText().toString().trim();
        if(img_url.equals("")){
            Toast.makeText(this,"Please enter a URL", Toast.LENGTH_LONG).show();
            return;
        }
        imageLoader = CustomVolleyRequest.getInstance(this.getApplicationContext())
                .getImageLoader();
        imageLoader.get(img_url, ImageLoader.getImageListener(imageView,
                R.drawable.shop, android.R.drawable
                        .ic_dialog_alert));
        imageView.setImageUrl(img_url, imageLoader);

    }


    private void Confirm_Purchase() {
        final String image_url = txurl.getText().toString().trim();
        final String item_price = txitemprice.getText().toString().trim();
        final String spmkt_name = spmtname.getText().toString().trim();
        final String item_qnty = qnty.getText().toString().trim();
        final String full_desc = fulldescription.getText().toString().trim();
        final String phone = txtemail.getText().toString().trim();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.JSON_SUPERMARKET_PURCHASE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Displays the toast from the server side
                        Toast.makeText(AddCart.this, response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AddCart.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_QUANTITY, item_qnty);
                params.put(KEY_PRICE, item_price);
                params.put(KEY_URL,image_url);
                params.put(KEY_SPKTNAME,spmkt_name);
                params.put(KEY_FULLD,full_desc);
                params.put(KEY_PHONE,phone);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}