package com.example.flash.superkatale;

import android.app.PendingIntent;
import android.content.Intent;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Message extends AppCompatActivity {
    public static final String KEY_SCONTACT = "mservice_contact";
    String ms_contacts;
    TextView txcontacts;
    Button btnsend;
    EditText etmessage;

    private SmsManager smsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        txcontacts = (TextView)findViewById(R.id.txtContacts);
        btnsend = (Button)findViewById(R.id.btnSend);
        etmessage = (EditText)findViewById(R.id.etMessage);

        this.smsManager = SmsManager.getDefault();

        Intent i = getIntent();
        ms_contacts = i.getStringExtra(KEY_SCONTACT);
        txcontacts.setText(ms_contacts);

        final PendingIntent sentIntent = PendingIntent.getActivity(this, 0, new Intent(this, Message.class), 0);
        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //Log.d(SyncStateContract.Constants.LOGTAG, "SmsExample sending SMS message via manager");
                String dest = txcontacts.getText().toString().trim();
                if (PhoneNumberUtils.isWellFormedSmsAddress(dest)) {

                    // dest, serviceCenter (null for default), message,
                    // sentIntent, deliveryIntent
                    // Set the second parameter to null. The scAddress relates
                    // to the address of the server on the cellular network that will handle
                    // the message, it is not the address of the sender.

                    smsManager.sendTextMessage(txcontacts.getText().toString(), null, etmessage.getText()
                            .toString(), sentIntent, null);

                    Toast.makeText(Message.this, "SMS message sent", Toast.LENGTH_LONG).show();
                    etmessage.setText("");

                } else {
                    Toast.makeText(Message.this, "SMS destination invalid - try again", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
    }
    @Override
    public void onPause() {
        super.onPause();
    }
}
