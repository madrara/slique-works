package com.example.flash.superkatale;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class See_Mechanic extends AppCompatActivity {
    public static final String KEY_SCONTACT = "mservice_contact";

    TextView contacts;
    String ms_contacts;
    Button btnsms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see__mechanic);
        contacts = (TextView)findViewById(R.id.txtcontacts);
        btnsms = (Button)findViewById(R.id.btnMessage);


        Intent i = getIntent();
        ms_contacts = i.getStringExtra(KEY_SCONTACT);
        contacts.setText(ms_contacts);
        btnsms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent message = new Intent(getApplicationContext(),Message.class);
                message.putExtra(KEY_SCONTACT,ms_contacts);
                startActivityForResult(message,100);
            }
        });
    }
    public void phoneCall(View view){

        String number = contacts.getText().toString();
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + number));
        startActivity(callIntent);
    }

}
