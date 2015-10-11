package com.example.badsha.hackncstuddybuddy;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;

/**
 * Created by badsha on 10/10/2015.
 */

//hi
public class StuddyBuddyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "0EJ9xe5vhrguxdG33dZvgGGxvpHWxlpRHuJK36bG", "zkybOz5JpkQNWkxxjTfUiIlqxDciMYwvaKWzNjI3");
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}

//"6psA123wnXGsFK7GYBBfqR9tdTrmvlQEkjtqoBTz", "nJpKCRvQbMaosyq3GaOdHa7vE9aq7cT3KsQx5JRg"