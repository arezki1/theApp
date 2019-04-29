package com.arezki.finalapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pusher.pushnotifications.PushNotifications;


public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    private WebView myWebview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseApp.initializeApp(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get the webview reference
        myWebview = findViewById(R.id.webView);
        //get websetting to enable javaScript
        WebSettings webSettings = myWebview.getSettings();
        // set up javaScript
        webSettings.setJavaScriptEnabled(true);
        //set the webview to open the url that shows the camera feed
        myWebview.loadUrl("http://192.168.137.211:8000/index.html");
        //set up an instance of the webView client and error handling

        //http://www.vetbossel.in/android-webview-webpage-not-available/

        myWebview.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView webView, int errorCode, String description, String failingUrl) {
                try {
                    webView.stopLoading();
                } catch (Exception e) {
                }

                if (webView.canGoBack()) {
                    webView.goBack();
                }

                webView.loadUrl("about:blank");
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("Error");
                alertDialog.setMessage("Check your internet or the stream camera and try again");
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Try Again", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        startActivity(getIntent());
                    }
                });

                alertDialog.show();
                super.onReceivedError(webView, errorCode, description, failingUrl);
            }

        });
        // Locate the picture button in activity_main.xml
        Button camera = (Button) findViewById(R.id.camera);

        //click to take a picture
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PushNotifications.start(getApplicationContext(), "2d2522d4-b7d2-4b94-87f8-e6cb8817bd10");
                PushNotifications.subscribe("hello");
                //get the camera reference from firebase database
                final DatabaseReference camera = database.getReference("camera");

                //set its value to "on"
                camera.setValue("on");
            }
        });
        // Locate the whatsapp button in activity_main.xml
        Button whatsapp = (Button) findViewById(R.id.whatsapp);

        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // putting the pnone number here
                String contact = "+353 83 327 6906";

                //make a url for the whatsApp application
                String url = "https://api.whatsapp.com/send?phone=" + contact;
                try {
                    //get the package manager instance
                    PackageManager pm = getApplicationContext().getPackageManager();
                    pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                } catch (PackageManager.NameNotFoundException e) {
                    Toast.makeText(getApplicationContext(),"Whatsapp app not installed in your phone",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });


    }
    public class myWebClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            view.loadUrl(url);
            return true;

        }



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
