package com.example.flash.superkatale;

import android.content.Intent;
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

public class Edit_Cart extends AppCompatActivity {
    public static final String KEY_IMAGE = "image";
    public static final String KEY_QNTY = "quantity";
    public static final String KEY_ID = "id";
    public static final String KEY_PHONE = "phone_number";

    Button btnremove;
    Button btnedit;
    TextView tvID;
    TextView tvimage;
    TextView tvid;
    TextView tvphone;
    EditText etquantity;
    String image_url;
    String quantity;
    String IID;
    String phone;

    NetworkImageView imageView;
    private ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__cart);
        btnremove = (Button) findViewById(R.id.btnRemove);
        btnedit = (Button) findViewById(R.id.btnEdit);

        tvimage = (TextView) findViewById(R.id.tvimageurl);
        etquantity = (EditText) findViewById(R.id.etqnty);
        imageView = (NetworkImageView) findViewById(R.id.imageviewcart);
        tvid = (TextView) findViewById(R.id.tvID);
        tvphone = (TextView)findViewById(R.id.tvphone);


        Intent i = getIntent();
        image_url = i.getStringExtra(KEY_IMAGE);
        quantity = i.getStringExtra(KEY_QNTY);
        phone = i.getStringExtra(KEY_PHONE);
        IID = i.getStringExtra(KEY_ID);

        etquantity.setText(quantity);
        tvimage.setText(image_url);
        tvid.setText(IID);
        tvphone.setText(phone);
        LoadImage();

        btnremove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call the delete method
                RemoveItem();
            }
        });
        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call the edit method
                UpDateCart();
            }
        });
    }
    //Class that fetches the images (image module)
    private void LoadImage() {
        String img_url = tvphone.getText().toString().trim();
        if (img_url.equals("")) {
            Toast.makeText(this, "Please enter a URL", Toast.LENGTH_LONG).show();
            return;
        }
        imageLoader = CustomVolleyRequest.getInstance(this.getApplicationContext())
                .getImageLoader();
        imageLoader.get(img_url, ImageLoader.getImageListener(imageView,
                R.drawable.shop, android.R.drawable
                        .ic_dialog_alert));
        imageView.setImageUrl(img_url, imageLoader);
    }
    private void RemoveItem() {
        final String item_id = tvid.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.JSON_DELETE ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int success = 1;
                        //new piece of code
                        if (success == 1) {
                            // successfully updated
                            Intent i = getIntent();
                            Toast.makeText(Edit_Cart.this, response, Toast.LENGTH_LONG).show();
                            // send result code 100 to notify about product update
                            setResult(100,i);
                            finish();
                        } else {
                            // failed to update product
                            Toast.makeText(Edit_Cart.this, response, Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Edit_Cart.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_ID, item_id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void UpDateCart() {
        final String item_id = tvid.getText().toString().trim();
        final String item_qnty = etquantity.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.JSON_UPDATE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Edit_Cart.this, response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Edit_Cart.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_ID, item_id);
                params.put(KEY_QNTY,item_qnty);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
