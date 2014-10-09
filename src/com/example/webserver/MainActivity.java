package com.example.webserver;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;


//Implements does what??
public class MainActivity extends Activity implements OnClickListener {
	
	//declaring variables to be used
	Button search;
	WebView browser;
	EditText urlInput;
	String StrUrl;
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //declaring variables 
        search = (Button) findViewById(R.id.search);
        browser = (WebView) findViewById(R.id.browser);
        urlInput = (EditText) findViewById(R.id.url);
        
        search.setOnClickListener(this);
        
        
    }


	//Handler to read html and pass it to webview
    @SuppressLint("HandlerLeak") Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
           
        	String html = (String) msg.obj; //Extract the string from the Message
        	Log.e("",html);
            
            // text will contain the string "your string message"
        	
    		//myBrowser.loadData(html, "text/html", "UTF-8");
    		
    		browser.loadDataWithBaseURL(StrUrl, html, "text/html", "UTF-8","about:blank");
        }
    };
    
    @Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
		//
		switch (arg0.getId())
		{
		case R.id.search: 		
		
			//Beginning of the thread
		Thread inputText = new Thread()
		{
			@Override
			public void run()
			{
				URL url = null;	
				
				try{
					
					
					url = new URL(urlInput.getText().toString());
					StrUrl = url.toString();
					BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
					
					String response = "",tmpResponse = "";
					tmpResponse = reader.readLine();
					while(tmpResponse != null){
						response = response + tmpResponse;
						tmpResponse = reader.readLine();
						
					} // end while loop
					
					// Transfer the message to the handler
					
					Log.e("Browser output",response);
					Message msg = Message.obtain();
					msg.obj = response;
				    msg.setTarget(handler); // Set the Handler
					msg.sendToTarget(); //Send the message
					
				}catch(Exception e){
					e.printStackTrace();
					
				}
								
			}
			
		};
		
		//implement the thread
		inputText.start();
		
		break;
		default:
		
		}
	}
	//this is the end of the thread
	
	
} // end class
