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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class ViewCart extends AppCompatActivity {
    public static final String KEY_SPKTID = "supermkt_id";
    public static final String KEY_ID = "id";

    public static final String KEY_QNTY = "quantity";
    public static final String KEY_PRICE = "price";
    public static final String KEY_FUll = "fulldescription";
    public static final String KEY_PHONE = "phone_number";
    public static final String KEY_IMAGE = "image";

    private ListView listView;
    private TextView tvspmktid;
    private TextView tvcontact;

    private TextView textdescribe;
    private TextView textimage_url;
    private TextView textpone;
    private TextView textprice;
    private TextView textid;
    private TextView textqnty;


    String sid;
    String contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart);
        final Animation anim_translate = AnimationUtils.loadAnimation(this,R.anim.anim_translate);
        tvspmktid = (TextView)findViewById(R.id.tvspmktid);
        tvcontact = (TextView)findViewById(R.id.tvcontact);

        textdescribe = (TextView)findViewById(R.id.textdescribe);
        textimage_url = (TextView)findViewById(R.id.textimage_url);
        textpone = (TextView)findViewById(R.id.textphone);
        textprice = (TextView)findViewById(R.id.textprice);
        textid =(TextView)findViewById(R.id.textID);
        textqnty = (TextView)findViewById(R.id.textqnty);


        Intent i = getIntent();
        sid = i.getStringExtra(KEY_SPKTID);
        contact = i.getStringExtra(KEY_PHONE);
        tvspmktid.setText(sid);
        tvcontact.setText(contact);


        listView = (ListView)findViewById(R.id.listView2);
        SendRequest();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.startAnimation(anim_translate);
                Intent cartdetail = new Intent(getApplicationContext(),CartDetail.class);
                String imageurl = ((TextView)view.findViewById(R.id.textimage_url)).getText().toString();
                String describe = ((TextView)view.findViewById(R.id.textdescribe)).getText().toString();
                String phone = ((TextView)view.findViewById(R.id.textphone)).getText().toString();
                String quantity = ((TextView)view.findViewById(R.id.textqnty)).getText().toString();
                String price = ((TextView)view.findViewById(R.id.textprice)).getText().toString();
                String iid = ((TextView)view.findViewById(R.id.textID)).getText().toString();

                cartdetail.putExtra(KEY_IMAGE,imageurl);
                cartdetail.putExtra(KEY_FUll,describe);
                cartdetail.putExtra(KEY_PHONE,phone);
                cartdetail.putExtra(KEY_QNTY,quantity);
                cartdetail.putExtra(KEY_PRICE,price);
                cartdetail.putExtra(KEY_ID,iid);

                startActivityForResult(cartdetail, 100);
            }
        });

    }
    public void SendRequest(){
        final String contact = tvcontact.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.JSON_CARTVIEW ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Toast.makeText(Bread_Category.this, response.toString(), Toast.LENGTH_LONG).show();
                        //  if(response.trim().equals("success")){
                        showJSON(response);

                        //  }else{
                        Toast.makeText(ViewCart.this, response, Toast.LENGTH_LONG).show();
                        //  }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ViewCart.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put(KEY_PHONE,contact);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
    private void showJSON(String json){
        ParseJSON_2 pj = new ParseJSON_2(json);
        pj.parseJSON_2();
        //Toast.makeText(Bread_Category.this,ParseJSON_1.description.toString(), Toast.LENGTH_LONG).show();
        //System.out.println("Eroor at "+ParseJSON_1.description.toString());

        Cart_CustomList cl = new Cart_CustomList (this, ParseJSON_2.id,ParseJSON_2.quantity,ParseJSON_2.price,ParseJSON_2.fulldescription,ParseJSON_2.image,ParseJSON_2.phone );
        listView.setAdapter(cl);
    }
}
