package com.example.flash.superkatale;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {

    private TextView textView;
    Button btn_profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Call some material design APIs here
        } else {
            // Implement this feature without material design
        }

        textView = (TextView) findViewById(R.id.textView);
        btn_profile = (Button)findViewById(R.id.btnprofile);

        //Fetching email from shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String email = sharedPreferences.getString(Config.EMAIL_SHARED_PREF,"Not Available");

        //Showing the current logged in email to textview
        textView.setText("Current User: " + email);

        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Main = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(Main);
            }
        });
    }
    private void logout(){
        //Creating an alert dialog to confirm logout
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to logout?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        //Getting out sharedpreferences
                        SharedPreferences preferences = getSharedPreferences(Config.SHARED_PREF_NAME,Context.MODE_PRIVATE);
                        //Getting editor
                        SharedPreferences.Editor editor = preferences.edit();

                        //Puting the value false for loggedin
                        editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, false);

                        //Putting blank value to email
                        editor.putString(Config.EMAIL_SHARED_PREF, "");

                        //Saving the sharedpreferences
                        editor.commit();

                        //Starting login activity
                        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                });

        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        //Showing the alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Adding our menu to toolbar
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menuLogout) {
            //calling logout method when the logout button is clicked
            logout();
        }
        if(id == R.id.profile){
        //calling the profile class/Method
            Toast.makeText(getApplicationContext(), "Change your profile information", Toast.LENGTH_SHORT).show();
            Intent Profile = new Intent(getApplicationContext(),Profile_Info.class);
            startActivity(Profile);
        }
        if(id ==R.id.settings ){
            //Call the settings class/method
            Toast.makeText(getApplicationContext(), "Change the system settings", Toast.LENGTH_SHORT).show();
            Intent settings = new Intent(getApplicationContext(),SettingsActivity.class);
            startActivity(settings);

        }
        if(id == R.id.notifyme){
            //Call the Notification Class/Method
            Toast.makeText(getApplicationContext(), "Subscribe for Notifications", Toast.LENGTH_SHORT).show();
            Intent Notify_me = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(Notify_me);

        }
        if(id == R.id.aboutus){
            //Call the About US Class/Method
            //Toast.makeText(getApplicationContext(), "To know more about..Get to Our website", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(),AboutUs.class);
            startActivity(i);

        }

        return super.onOptionsItemSelected(item);
    }
}