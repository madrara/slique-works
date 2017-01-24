package cartbolt.qui.screens;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import cartbolt.utils.Globals;

public class Start extends AppCompatActivity {

    // PREFS
    private SharedPreferences prefs;
    String sendNum;
    String userName;
    private TextView startlogin, startsignup, startskip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        //hide keyboard
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        // PREFS STUFF
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        startsignup = (TextView) findViewById(R.id.startsignup);
        startlogin = (TextView) findViewById(R.id.startlogin);

        startlogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent mainMenu = new Intent(Start.this, Login.class);
                Start.this.startActivity(mainMenu);
                Start.this.finish();
            }
        });


        startsignup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent mainMenu = new Intent(Start.this, Signup.class);
                Start.this.startActivity(mainMenu);
                Start.this.finish();
            }
        });

        startskip = (TextView) findViewById(R.id.startskip);
        startskip.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                SharedPreferences.Editor edit = prefs.edit();
                edit.putString("usertype", "skipper");
                edit.putBoolean("firstTime", true);
                edit.commit();

                Intent mainMenu = new Intent(Start.this, Homer.class);
                Start.this.startActivity(mainMenu);
                Start.this.finish();
            }
        });

        // Boolean firstTime = prefs.getBoolean("firsTime", );
        Boolean check = prefs.contains("usertype");
        if (!check) {
            // alerter.alerter("Welcome", "its your first time");
            //Intent i = new Intent(this, Login.class);
            //startActivity(i);
        } else {
            Globals.regstatus = prefs.getString("usertype", "skipper");
            setContentView(R.layout.fragment_home);
            userName = prefs.getString("username", null);
            sendNum = prefs.getString("userid", null);
            //dualSim = prefs.getBoolean("dualSim", false);
            //Toast.makeText(Start.this, "Logged in as :"+userName+" and key :"+sendNum, Toast.LENGTH_LONG).show();
            Intent mainMenu = new Intent(Start.this, Homer.class);
            Start.this.startActivity(mainMenu);
            Start.this.finish();
        }

    }
}
