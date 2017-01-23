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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Downloader;

import java.util.HashMap;
import java.util.Map;

public class Fuel_Purchase extends AppCompatActivity {
    public static final String KEY_ID = "fuelco_id";
    public static final String KEY_NAME = "fuelco_name";
    public static final String KEY_NO = "vehicle_no";
    public static final String KEY_PHONE = "phone_number";
    public static final String KEY_QNTY = "fuel_quantity";
    public static final String KEY_TYPE = "fuel_type";

    EditText etqnty;
    EditText etplate;
    EditText ettype;
    TextView tvfuelco_id;
    TextView tvfuelco_name;
    TextView tvlogged;
    Button btnpurchase;
    Button btnmakepay;
    String fuelco_id;
    String fuelco_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel__purchase);
        etqnty = (EditText)findViewById(R.id.etqnty);
        etplate = (EditText)findViewById(R.id.etplate);
        ettype = (EditText)findViewById(R.id.ettype);
        tvfuelco_id = (TextView)findViewById(R.id.tvfuelco_id);
        tvfuelco_name = (TextView)findViewById(R.id.tvfuelco_name);
        tvlogged = (TextView)findViewById(R.id.tvlogged);
        btnpurchase = (Button)findViewById(R.id.btnpurchase);
        btnmakepay = (Button)findViewById(R.id.btnmakepay);

        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String email = sharedPreferences.getString(Config.EMAIL_SHARED_PREF,"Not Available");

        //Showing the current logged in email to textview
        tvlogged.setText(email);

        Intent i = getIntent();
        fuelco_id = i.getStringExtra(KEY_ID);
        fuelco_name = i.getStringExtra(KEY_NAME);
        tvfuelco_id.setText(fuelco_id);
        tvfuelco_name.setText(fuelco_name);

        btnpurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPurchase();
                etqnty.setText("");
                etplate.setText("");
                ettype.setText("");

            }
        });
        btnmakepay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent buy = new Intent(getApplicationContext(),Buy.class);
                startActivity(buy);
            }
        });

    }
    private void sendPurchase(){
        final String fuel_qnty = etqnty.getText().toString().trim();
        final String fuel_type = ettype.getText().toString().trim();
        final String number_plate = etplate.getText().toString().trim();
        final String fuelco_name = tvfuelco_name.getText().toString().trim();
        final String phone_number = tvlogged.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.PURCHASE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Fuel_Purchase.this, response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Fuel_Purchase.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(KEY_QNTY,fuel_qnty);
                params.put(KEY_TYPE,fuel_type);
                params.put(KEY_PHONE,phone_number);
                params.put(KEY_NO, number_plate);
                params.put(KEY_NAME,fuelco_name);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
