package com.example.badsha.hackncstuddybuddy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.ParsePush;
import com.parse.ParseInstallation;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

public class MainPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        //in the mainn page now.


        final EditText subjectfield = (EditText) findViewById(R.id.subject); //this is our subject field

        final Button button = (Button) findViewById(R.id.data); // our button
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click

                String subjecttxt = subjectfield.getText().toString(); // so we can pass the string inside.
                Toast.makeText(getApplicationContext(), subjecttxt + "  session has been made ", Toast.LENGTH_SHORT).show();
                //query to the database regarding user.
                // query to the data base regarding subject
                ParseQuery<ParseObject> query = ParseQuery.getQuery("StudyGroup");
                query.whereEqualTo("subject", subjecttxt); //
                query.findInBackground(new FindCallback<ParseObject>() {


                    @Override
                    public void done(List<ParseObject> studyGroupList, ParseException e) {
                        if (e == null) { // if there is a matching study group
            /*
            Does a query with no matching values return an error or an empty list?
            */
                            if (studyGroupList.size() == 0) { //if there is no study group make one with this code!
                                String subjecttxt = subjectfield.getText().toString(); //from my textbox.
                                //get our current user
                                ParseUser currentUser = ParseUser.getCurrentUser(); //current user SHOULD have my user object.
                                ParseObject studyGroup = new ParseObject("StudyGroup"); //we're making our class.
                                //puts a subject
                                studyGroup.put("subject", subjecttxt); //change
                                studyGroup.put("creater", currentUser);
                                studyGroup.saveInBackground();
                                // subscribes client to listener for this event
                                ParsePush.subscribeInBackground(subjecttxt);

                            } else if (studyGroupList.size() < 2) { //if there is a study group
                                studyGroupList.get(0);
                                // subscribe new user to push event
                                ParsePush.subscribeInBackground(studyGroupList.get(0).getString("subject"));
                                // send push
                                ParsePush push = new ParsePush();
                                push.setChannel(studyGroupList.get(0).getString("subject"));
                                //push.setChannel("test");
                                push.setMessage("Hey! You have a study buddy! Head over to the UL in 15 minutes!");
                                push.sendInBackground();
                                Toast.makeText(getApplicationContext(), "There exists a study group!", Toast.LENGTH_SHORT).show();
                                //get rid of studyGroupList[0] object from database then
                                studyGroupList.get(0).deleteInBackground();
                            }
                        } else {
                            Log.d("Post retrieval", "Error: " + e.getMessage());
                        }
                    }
                });


            }
        });

        // where to go when the user presses enter
       /* EditText editText = (EditText) findViewById(R.id.search);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;



                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    //then do something when the user types their thing.  //using toast here just to see if my app works.
                    // Toast.makeText(getApplicationContext(), "this is my Toast message!!! =)",
                    //         Toast.LENGTH_LONG).show();


                    doNetworkStuff();
                    handled = true;
                }
                return handled;
            }
        });
*/
    }

    public void doNetworkStuff() {

        Thread t = new Thread() {
            @Override
            public void run() {
                super.run();
                String url = "https://hackncstudybuddybackend.mybluemix.net/adduser";
                String charset = "UTF-8";  // Or in Java 7 and later, use the constant: java.nio.charset.StandardCharsets.UTF_8.name()
                String email = "itsme@hotmail.com";
                String password = "";


                try {
                    String query = String.format("email=%s&password=%s", URLEncoder.encode(email, charset), URLEncoder.encode(password, charset));
                    Log.d("TEST", "Starting");
                    URLConnection connection = new URL(url + "?" + query).openConnection();
                    //URLConnection connection = new URL("https://canhazip.com").openConnection();
                    connection.connect();
                    InputStream response = connection.getInputStream();
                    BufferedReader bf = new BufferedReader(new InputStreamReader(response));
                    Log.d("TEST", bf.readLine());
                    //InputStream response = new URL(url).openStream();
                    Log.d("TEST", "STOPPING");


                } catch (UnsupportedEncodingException e) {

                } catch (MalformedURLException e) {

                } catch (IOException e) {

                }


                //  InputStream response = connection.getInputStream(); needed later for response

                //  InputStream response = new URL(url).openStream(); needed later for response


            }
        };
        t.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
