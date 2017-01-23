package com.example.flash.superkatale;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FuelCompany_Services extends AppCompatActivity {
    public static final String KEY_ID = "fuelco_id";
    public static final String KEY_NAME = "fuelco_name";
    private String fuelco_ids;
    private String fuelco_names;

    Button btnBuy_fuel;
    Button btnMechanical;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel_company__services);

        Intent i = getIntent();
        fuelco_ids = i.getStringExtra(KEY_ID);
        fuelco_names = i.getStringExtra(KEY_NAME);
        TextView fuelco_id = (TextView)findViewById(R.id.fuecoid);
        TextView fuelco_name = (TextView)findViewById(R.id.tvfuel_name);

        fuelco_id.setText(fuelco_ids);
        fuelco_name.setText(fuelco_names);

        btnBuy_fuel = (Button)findViewById(R.id.btn_buyfuel);
        btnMechanical = (Button)findViewById(R.id.btn_mechanical);

        btnBuy_fuel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent buy_fuel = new Intent(getApplication(),Fuel_Purchase.class);
                String fuelco_id = ((TextView)findViewById(R.id.fuecoid)).getText().toString();
                String fuelco_name = ((TextView)findViewById(R.id.tvfuel_name)).getText().toString();
                buy_fuel.putExtra(KEY_ID,fuelco_id);
                buy_fuel.putExtra(KEY_NAME,fuelco_name);
                startActivityForResult(buy_fuel,100);
            }
        });

        btnMechanical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Mservices = new Intent(getApplicationContext(),Mechanical_services.class);
                startActivity(Mservices);
            }
        });
    }
}
