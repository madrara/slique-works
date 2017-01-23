package com.example.flash.superkatale;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
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

public class Mechanical_services extends AppCompatActivity {

    public static final String KEY_CONTACT = "mservice_contact";

    private ListView listView;
    private TextView mser_contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanical_services);
        final Animation anim_translate = AnimationUtils.loadAnimation(this,R.anim.anim_translate);
        sendRequest();
        mser_contact = (TextView)findViewById(R.id.mservicecontact);


        listView = (ListView)findViewById(R.id.listView3);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.startAnimation(anim_translate);
                Intent seemechanic = new Intent(getApplicationContext(),See_Mechanic.class);

                String contact = ((TextView)view.findViewById(R.id.mservicecontact)).getText().toString();
                seemechanic.putExtra(KEY_CONTACT,contact);
                startActivityForResult(seemechanic,100);

            }
        });

    }
    private void sendRequest(){

        StringRequest stringRequest = new StringRequest(Config.JSON_MECHANICAL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        showJSON(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Mechanical_services.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String json){
        ParseJSON1 pj = new ParseJSON1(json);
        pj.parseJSON1();

        Mservice_CustomList cl = new Mservice_CustomList (this, ParseJSON1.mservice_id,ParseJSON1.mservice_slogan,ParseJSON1.mservice_name,ParseJSON1.mservice_location,ParseJSON1.mservice_contact);
        listView.setAdapter(cl);
    }

}