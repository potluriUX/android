package com.ravi.hindinewmovies;



import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;














import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends Activity {

	//private WebView webView;
	@SuppressLint("NewApi")

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview);

		//webView = (WebView) findViewById(R.id.webView1);
	//	webView.getSettings().setJavaScriptEnabled(true);
		   AdView adView =   (AdView)this.findViewById(R.id.adView2);
	        AdRequest adRequest = new AdRequest.Builder()
	       // .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
	        .build();
	     //   adView.setAdSize(AdSize.BANNER);
	        //adView.setAdUnitId("ca-app-pub-3730266544385182/1506030791");ca-app-pub-3730266544385182/1506030791
	       
	       // AdView adView = new AdView(this);
	       adView.loadAd(adRequest);
		Bundle b = getIntent().getExtras();
		String value = b.getString("key");
		String value2 = b.getString("key2");
		String videoId = "Fee5vbFLYM4";
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:"+value.trim())); 
		intent.putExtra("VIDEO_ID", value); 
		startActivity(intent); 
		/*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
	            // Show the Up button in the action bar.
			  //getActionBar().hide();
			  getActionBar().setTitle(value2);
	            getActionBar().setDisplayHomeAsUpEnabled(false);
	        }*/

		WebViewClient yourWebClient = new WebViewClient()
		{
		   // Override page so it's load on my view only
		   @Override
		   public boolean shouldOverrideUrlLoading(WebView  view, String  url)
		   {
		         // This line we let me load only pages inside Firstdroid Webpage
		         if ( url.contains("firstdroid") == true )
		           //Load new URL Don't override URL Link
		        return false;
		         view.loadUrl(url);      
		   // Return true to override url loading (In this case do nothing).
		   return true;
		    }
		};

		
	
	//	webView.setWebViewClient(yourWebClient);
	//	webView.loadUrl( value);
		/*Bundle b = getIntent().getExtras();

		String value = b.getString("key");
		String customHtml = "<html><body><h1>Hello, WebView</h1></body></html>" + value;
		webView.loadData(customHtml, "text/html", "UTF-8");*/

	}


	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(event.getAction() == KeyEvent.ACTION_DOWN){
            switch(keyCode)
            {
            case KeyEvent.KEYCODE_BACK:
        //      if(webView.canGoBack() == true){
         //       	webView.goBack();
              //  }else{
           //         finish();
             //   }
           //     return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }
	


}
