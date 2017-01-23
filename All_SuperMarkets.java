package com.example.flash.superkatale;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class All_SuperMarkets extends AppCompatActivity {

    public static final String KEY_ID = "supermkt_id";
    public static final String KEY_NAME = "supermkt_name";
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all__super_markets);
        final Animation anim_translate = AnimationUtils.loadAnimation(this,R.anim.anim_translate);

        listView = (ListView)findViewById(R.id.listView);
        sendRequest();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.startAnimation(anim_translate);

                Intent dashboard = new Intent(getApplicationContext(),All_Categories.class);
                String supermkt_id = ((TextView)view.findViewById(R.id.spmktid)).getText().toString();
                String supermkt_name = ((TextView)view.findViewById(R.id.spmktname)).getText().toString();
                dashboard.putExtra(KEY_ID,supermkt_id);
                dashboard.putExtra(KEY_NAME,supermkt_name);

                startActivityForResult(dashboard,100);

            }
        });
    }
    private void sendRequest(){

        StringRequest stringRequest = new StringRequest(Config.JSON_ALL_SUPERMARKETS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        showJSON(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(All_SuperMarkets.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String json){
        ParseJSON pj = new ParseJSON(json);
        pj.parseJSON();

        Supernkt_CustomList cl = new Supernkt_CustomList(this, ParseJSON.supermkt_id,ParseJSON.supermkt_names,ParseJSON.supermkt_slogan,ParseJSON.supermkt_location);
        listView.setAdapter(cl);
    }
}
