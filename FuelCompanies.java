package com.example.flash.superkatale;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class FuelCompanies extends AppCompatActivity {

    public static final String KEY_ID = "fuelco_id";
    public static final String KEY_NAME = "fuelco_name";

    private ListView listView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel_companies);
        final Animation anim_translate = AnimationUtils.loadAnimation(this,R.anim.anim_translate);
        sendRequest();
        listView2 = (ListView)findViewById(R.id.listView);
        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.startAnimation(anim_translate);
                Intent FuelCo = new Intent(getApplicationContext(),FuelCompany_Services.class);
                String fuelco_id = ((TextView)view.findViewById(R.id.fuecoid)).getText().toString();
                String fuelco_name = ((TextView)view.findViewById(R.id.fuelconame)).getText().toString();
                FuelCo.putExtra(KEY_ID,fuelco_id);
                FuelCo.putExtra(KEY_NAME,fuelco_name);
                startActivityForResult(FuelCo,100);


            }
        });

    }
    private void sendRequest(){

        StringRequest stringRequest = new StringRequest(Config.ALL_COMPANIES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        showJSON(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(FuelCompanies.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String json){
        ParseJSON2 pj = new ParseJSON2(json);
        pj.parseJSON2();

        Fuelco_CustomList cl = new Fuelco_CustomList(this, ParseJSON2.fuelco_id,ParseJSON2.fuelco_name,ParseJSON2.fuelco_slogan,ParseJSON2.fuelco_location);
        listView2.setAdapter(cl);
    }
}
