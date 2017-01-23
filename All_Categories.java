package com.example.flash.superkatale;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class All_Categories extends AppCompatActivity {
    public static final String KEY_SPKTID = "supermkt_id";
    public static final String KEY_SPKTNAME = "supermkt_name";

    TextView spmkt_name;
    TextView spmkt_id;
    String name;
    String id;
    Button btnBreadandBakery;
    Button btnBeaverages;
    Button btncannedandjarred;
    Button btnDairy;
    Button btnDryandBaking;
    Button btnCleaners;
    Button btnPersonalcare;
    Button btnOthers;
    Button btnMeat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all__categories);

        spmkt_id = (TextView)findViewById(R.id.txtspmkt_id);
        spmkt_name = (TextView)findViewById(R.id.txtspmkt_name);

        Intent i = getIntent();
        id = i.getStringExtra(KEY_SPKTID);
        name = i.getStringExtra(KEY_SPKTNAME);
        spmkt_id.setText(id);
        spmkt_name.setText(name);

        btnBreadandBakery = (Button)findViewById(R.id.btnBreadandBakery);
        btnBeaverages = (Button)findViewById(R.id.Beaverages);
        btncannedandjarred = (Button)findViewById(R.id.CannedandJarred);
        btnDairy = (Button)findViewById(R.id.DairyandMeat);
        btnDryandBaking = (Button)findViewById(R.id.DryandBaking);
        btnCleaners = (Button)findViewById(R.id.btnCleaners);
        btnPersonalcare = (Button)findViewById(R.id.button11);
        btnOthers = (Button)findViewById(R.id.btnOthers);
        btnMeat = (Button)findViewById(R.id.btnMeat);



        btnBreadandBakery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent BreadCategory = new Intent(getApplicationContext(),Bread_Category.class);
                String spmktid = ((TextView)findViewById(R.id.txtspmkt_id)).getText().toString();
                String spmktname = ((TextView)findViewById(R.id.txtspmkt_name)).getText().toString();
                BreadCategory.putExtra(KEY_SPKTID,spmktid);
                BreadCategory.putExtra(KEY_SPKTNAME,spmktname);
                startActivityForResult(BreadCategory,100);


            }
        });
        btnBeaverages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Beaverages_Category = new Intent(getApplicationContext(),Beaverages_Category.class);
                String spmktid = ((TextView)findViewById(R.id.txtspmkt_id)).getText().toString();
                String spmktname = ((TextView)findViewById(R.id.txtspmkt_name)).getText().toString();
                Beaverages_Category.putExtra(KEY_SPKTID,spmktid);
                Beaverages_Category.putExtra(KEY_SPKTNAME,spmktname);
                startActivityForResult(Beaverages_Category,100);

            }
        });
        btnPersonalcare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent care = new Intent(getApplicationContext(),Personal_Care.class);
                String spmktid = ((TextView)findViewById(R.id.txtspmkt_id)).getText().toString();
                String spmktname = ((TextView)findViewById(R.id.txtspmkt_name)).getText().toString();
                care.putExtra(KEY_SPKTID,spmktid);
                care.putExtra(KEY_SPKTNAME,spmktname);
                startActivityForResult(care,100);
            }
        });
        btncannedandjarred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cannedandjarred = new Intent(getApplicationContext(),CanndandJarred_Category.class);
                String spmkt_id = ((TextView)findViewById(R.id.txtspmkt_id)).getText().toString();
                String spmkt_name = ((TextView)findViewById(R.id.txtspmkt_name)).getText().toString();

                cannedandjarred.putExtra(KEY_SPKTID,spmkt_id);
                cannedandjarred.putExtra(KEY_SPKTNAME,spmkt_name);
                startActivityForResult(cannedandjarred,100);
            }
        });
        btnDairy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dairy = new Intent(getApplicationContext(),Dairy_Category.class);
                String spmkt_id = ((TextView)findViewById(R.id.txtspmkt_id)).getText().toString();
                String spmkt_name = ((TextView)findViewById(R.id.txtspmkt_name)).getText().toString();

                dairy.putExtra(KEY_SPKTID,spmkt_id);
                dairy.putExtra(KEY_SPKTNAME,spmkt_name);
                startActivityForResult(dairy,100);
            }
        });
        btnDryandBaking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent baking = new Intent(getApplicationContext(),DryandBaking_Category.class);
                String spmkt_id = ((TextView)findViewById(R.id.txtspmkt_id)).getText().toString();
                String spmkt_name = ((TextView)findViewById(R.id.txtspmkt_name)).getText().toString();

                baking.putExtra(KEY_SPKTID,spmkt_id);
                baking.putExtra(KEY_SPKTNAME,spmkt_name);
                startActivityForResult(baking,100);
            }
        });
        btnCleaners.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent personal = new Intent(getApplicationContext(),CleanersandPaper_Category.class);
                String spmkt_id = ((TextView)findViewById(R.id.txtspmkt_id)).getText().toString();
                String spmkt_name = ((TextView)findViewById(R.id.txtspmkt_name)).getText().toString();

                personal.putExtra(KEY_SPKTID,spmkt_id);
                personal.putExtra(KEY_SPKTNAME,spmkt_name);
                startActivityForResult(personal,100);
            }
        });
        btnOthers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent others = new Intent(getApplicationContext(),Others_Category.class);
                String spmkt_id = ((TextView)findViewById(R.id.txtspmkt_id)).getText().toString();
                String spmkt_name = ((TextView)findViewById(R.id.txtspmkt_name)).getText().toString();

                others.putExtra(KEY_SPKTID,spmkt_id);
                others.putExtra(KEY_SPKTNAME,spmkt_name);
                startActivityForResult(others,100);
            }
        });
        btnMeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent meat = new Intent(getApplicationContext(),Meat.class);
                String spmkt_id = ((TextView)findViewById(R.id.txtspmkt_id)).getText().toString();
                String spmkt_name = ((TextView)findViewById(R.id.txtspmkt_name)).getText().toString();

                meat.putExtra(KEY_SPKTID,spmkt_id);
                meat.putExtra(KEY_SPKTNAME,spmkt_name);
                startActivityForResult(meat,100);

            }
        });
    }
}
