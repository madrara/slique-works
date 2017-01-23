package com.example.flash.superkatale;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Specific_ItemCategory extends AppCompatActivity {

    public static final String KEY_ID = "category_id";
    public static final String KEY_CATNAME = "category_name";
    public static final String KEY_SPKTID = "supermkt_id";
    public static final String KEY_SPKTNAME = "supermkt_name";

    TextView category_id;
    TextView category_name;
    String name;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific__item_category);
        category_id = (TextView)findViewById(R.id.txtcat_id);
        category_name = (TextView)findViewById(R.id.txtcat_name);

        Intent i = getIntent();
        id = i.getStringExtra(KEY_ID);
        name = i.getStringExtra(KEY_CATNAME);

        category_id.setText(id);
        category_name.setText(name);

    }

}
