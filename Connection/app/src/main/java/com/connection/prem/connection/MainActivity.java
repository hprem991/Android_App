package com.connection.prem.connection;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;



// Importing My Service

import  com.connection.prem.connection.Location.*;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        // This is where C++ class / library Loads if you are using JNI application
        //System.loadLibrary("native-lib");
    }


    // Accessing Location Services here
    //Activity activity =  ()
    Neighbour myNeighbourHood = new Neighbour(this);
    Neighbour getMyNeighbourHood = new Neighbour( getApplication().getApplicationContext());



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText("Hello Prem !!! "+myNeighbourHood.getLatitute());
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
