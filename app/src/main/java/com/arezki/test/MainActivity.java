package com.arezki.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
//import com.pusher.pushnotifications.PushNotifications;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    private WebView myWebview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseApp.initializeApp(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get the webview reference
        myWebview=findViewById(R.id.webView);
        //get websetting to enable javaScript
        WebSettings webSettings=myWebview.getSettings();
        // set up javaScript
        webSettings.setJavaScriptEnabled(true);
        //set the webview to open the url that shows the camera feed
        myWebview.loadUrl("http://192.168.137.113:8000/index.html");
        //set up an instance of the webView client
        myWebview.setWebViewClient(new WebViewClient());

        // Locate the button in activity_main.xml
        Button camera = (Button) findViewById(R.id.camera);

        //start the mapas activity on clicke here
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get the camera reference from firebase database
                final DatabaseReference camera = database.getReference("camera");

                //set its value to "on"
                camera.setValue("on");
            }
        });

   }

    @Override
    public void onBackPressed() {

        if(myWebview.canGoBack()){

            myWebview.goBack();
        }
        else
        super.onBackPressed();
    }
}
