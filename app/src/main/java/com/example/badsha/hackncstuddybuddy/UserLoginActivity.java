package com.example.badsha.hackncstuddybuddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by badsha on 10/10/2015.
 */
public class UserLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_log_in);

        final EditText pass = (EditText) findViewById(R.id.password); //this is our passwordf
        final EditText user = (EditText) findViewById(R.id.username); //this is our username
        final Button button = (Button) findViewById(R.id.loginbutton);
        final Button button2 = (Button) findViewById(R.id.signupbutton);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String usertxt = user.getText().toString();
                String passwordtxt = pass.getText().toString();

                ParseUser.logInInBackground(usertxt, passwordtxt, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) { //if user is not null then go
                        if (user != null) { // add bad entries.
                            Intent intent = new Intent(UserLoginActivity.this, MainPageActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "You are not signed up. Please sign up!", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usertxtsignup = user.getText().toString();
                String passwordtxtsignup = pass.getText().toString();

                if (usertxtsignup.equals("") || passwordtxtsignup.equals("")) {

                    Toast.makeText(getApplicationContext(), "Do you not see this text field is empty?!", Toast.LENGTH_SHORT).show();
                } else {

                    ParseUser user = new ParseUser();
                    user.setUsername(usertxtsignup);
                    user.setPassword(passwordtxtsignup);

                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {

                            if (e == null) {

                                Toast.makeText(getApplicationContext(), "You have Signed up!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), " Error Code: " + e.getCode(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }

            }
        });
    }

        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_main_page, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected (MenuItem item){
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_settings) {
                return true;
            }

            return super.onOptionsItemSelected(item);
        }
    }
