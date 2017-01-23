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

import java.util.HashMap;
import java.util.Map;

public class CleanersandPaper_Category extends AppCompatActivity {

    public static final String LOGIN_URL = "http://192.168.0.2/SuperKatale_backend/all_cleanerandpaper.php";
    public static final String KEY_SPKTID = "supermkt_id";
    public static final String KEY_SPKTNAME = "supermkt_name";
    public static final String KEY_ID = "id";
    public static final String KEY_FULLD = "fulldescription";
    public static final String KEY_NAME = "description";
    public static final String KEY_PRICE = "price";
    public static final String KEY_URL = "image";

    ListView listView;
    TextView spmkt_id;
    TextView spmkt_name;

    String spmktname;
    String spmktid;
    String bread_id;
    String bread_price;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cleanersand_paper__category);
        final Animation anim_translate = AnimationUtils.loadAnimation(this,R.anim.anim_translate);

        spmkt_id = (TextView)findViewById(R.id.txspmktid);
        spmkt_name = (TextView)findViewById(R.id.txspmktname);

        Intent i = getIntent();
        spmktid = i.getStringExtra(KEY_SPKTID);
        spmktname = i.getStringExtra(KEY_SPKTNAME);
        spmkt_id.setText(spmktid);
        spmkt_name.setText(spmktname);

        listView = (ListView)findViewById(R.id.listView5);

        SendRequest();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.startAnimation(anim_translate);
                Intent purchase = new Intent(getApplicationContext(),AddCart.class);
                String item_id = ((TextView)view.findViewById(R.id.txid)).getText().toString();
                String spt_name = ((TextView) findViewById(R.id.txspmktname)).getText().toString();
                String spt_id = ((TextView) findViewById(R.id.txspmktid)).getText().toString();
                String item_price = ((TextView)view.findViewById(R.id.txprice)).getText().toString();
                String describe = ((TextView)view.findViewById(R.id.txdescription)).getText().toString();
                String fulldescribe = ((TextView)view.findViewById(R.id.txtfulldescription)).getText().toString();
                String url = ((TextView)view.findViewById(R.id.txturl)).getText().toString();

                purchase.putExtra(KEY_ID,item_id);
                purchase.putExtra(KEY_NAME,describe);
                purchase.putExtra(KEY_FULLD,fulldescribe);
                purchase.putExtra(KEY_SPKTNAME,spt_name);
                purchase.putExtra(KEY_SPKTID,spt_id);
                purchase.putExtra(KEY_PRICE,item_price);
                purchase.putExtra(KEY_URL,url);
                startActivityForResult(purchase, 100);
            }
        });


    }
    public void SendRequest(){
        final String spmktid = spmkt_id.getText().toString();
        final String spmktname = spmkt_name.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Toast.makeText(Bread_Category.this, response.toString(), Toast.LENGTH_LONG).show();
                        //  if(response.trim().equals("success")){
                        showJSON(response);

                        //  }else{
                        Toast.makeText(CleanersandPaper_Category.this, response, Toast.LENGTH_LONG).show();
                        //  }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CleanersandPaper_Category.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put(KEY_SPKTID,spmktid);
                map.put(KEY_SPKTNAME,spmktname);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
    private void showJSON(String json){
        ParseJSON_1 pj = new ParseJSON_1(json);
        pj.parseJSON_1();
        //Toast.makeText(Bread_Category.this,ParseJSON_1.description.toString(), Toast.LENGTH_LONG).show();
        //System.out.println("Eroor at "+ParseJSON_1.description.toString());

        Bread_CustomList cl = new Bread_CustomList (this, ParseJSON_1.id,ParseJSON_1.description,ParseJSON_1.price,ParseJSON_1.fulldescription,ParseJSON_1.imageurl );
        listView.setAdapter(cl);
    }
}
