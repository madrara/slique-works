package com.example.flash.superkatale;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Buy extends AppCompatActivity {

    private Button btnmobile;
    private  Button btnmastercard;
    private  Button btninterswitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);

        btnmobile = (Button)findViewById(R.id.btnmobile);
        btninterswitch = (Button)findViewById(R.id.btninterswitch);
        btnmastercard = (Button)findViewById(R.id.btnmastercard);

        btnmobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //payment using mobile money
            }
        });
        btninterswitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //payment using interswitch
            }
        });
        btnmastercard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //payment using mastercard
            }
        });
    }
}
