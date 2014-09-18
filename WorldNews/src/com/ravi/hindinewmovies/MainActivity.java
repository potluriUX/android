package com.ravi.hindinewmovies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ListView;
import com.google.android.gms.ads.*;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;


@SuppressLint("NewApi")
public class MainActivity extends Activity {

	private Button button;
	private TextView text;
	private EditText inputSearch;
	private String nameOfCountry;
	InterstitialAd interstitial;

	public void onCreate(Bundle savedInstanceState) {
		final Context context = this;

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);//R.layout.layout_file_name 
		
		 interstitial = new InterstitialAd(MainActivity.this);
	        // Insert the Ad Unit ID
	        interstitial.setAdUnitId("ca-app-pub-3730266544385182/7453002796");
	       AdView adView =   (AdView)this.findViewById(R.id.adView);
	        AdRequest adRequest = new AdRequest.Builder()
	       // .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
	        .build();
	     //   adView.setAdSize(AdSize.BANNER);
	        //adView.setAdUnitId("ca-app-pub-3730266544385182/1506030791");ca-app-pub-3730266544385182/1506030791
	       
	       // AdView adView = new AdView(this);
	       adView.loadAd(adRequest);
	        // Load ads into Interstitial Ads
	        //interstitial.loadAd(adRequest);
	        interstitial.loadAd(adRequest);
	        
	        // Prepare an Interstitial Ad Listener
	        interstitial.setAdListener(new AdListener() {
	            public void onAdLoaded() {
	                // Call displayInterstitial() function
	                displayInterstitial();
	            }
	        });
		
		final DatabaseHandler db = new DatabaseHandler(this);
		Bundle b = getIntent().getExtras();
		List<WebLinks> links = null;
		if(b != null){//search by country means the country  page not home page.
			String countryname = b.getString("countryname");//getting country name
			nameOfCountry = countryname;
			
			links = db.getLinksByCountry(countryname.toLowerCase());
			LinearLayout ll = (LinearLayout)findViewById(R.id.linearLayout1);// android:id="@+id/linearLayout1"
			//TextView tv = (TextView)findViewById(R.id.country_text);   //android:id="@+id/country_text"
			//tv.setText("Searching By Country: " + countryname + "\nPress HOME for all Countries");

		}
		else{//no data passed means home page of all news papaers.
			links = db.getAllLinks();
			LinearLayout ll = (LinearLayout)findViewById(R.id.linearLayout1);
			TextView tv = (TextView)findViewById(R.id.country_text);   
			tv.setVisibility(View.GONE);
		}
		/*final List<String> list = new ArrayList<String>();important*/
		final List<WebLinks> list2 = new ArrayList<WebLinks>();
		WebLinks[] data = null;
		//Log.d("countryname", countryname);
		for (WebLinks cn : links) {
			
			/*list.add(cn.get_name());*/
			list2.add(cn);

		}
		links = db.getAllFavs();
for (WebLinks cn : links) {
			
			/*list.add(cn.get_name());*/
			Log.d("favname" , cn.get_name());

		}
		data = (WebLinks[])list2.toArray(new WebLinks[0]);
		final ListView listview = (ListView) findViewById(R.id.listView1);

		/*final StableArrayAdapter adapter = new StableArrayAdapter(this,
				android.R.layout.simple_list_item_1, list);!!!!!!important*/
		/*final ArrayAdapter<WebLinks> adapter2 = new ArrayAdapter<WebLinks>(this,
				android.R.layout.simple_list_item_1, list2);*/
		final OurArrayAdapter adapter3 = new OurArrayAdapter(this,
				R.layout.list_item, data);//we pass layout file also to the adapter
		listview.setAdapter(adapter3);

		button = (Button) findViewById(R.id.buttonUrl);

		inputSearch = (EditText) findViewById(R.id.inputSearch);

		listview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				/*
				 * Toast.makeText(getApplicationContext(),
				 * "Click ListItem Number " + position, Toast.LENGTH_LONG)
				 * .show();
				 */
				Intent intent = new Intent(context, WebViewActivity.class);
				Bundle b = new Bundle();
				
				
				String selection = parent.getItemAtPosition(position)
						.toString();
				WebLinks testLink = (WebLinks) parent.getItemAtPosition(position);
			
				
				WebLinks w = db.getLink(selection);

				b.putString("key", w.get_link());
				b.putString("key2", w.get_name());
				intent.putExtras(b);

				startActivity(intent);
			}
			
		});

		inputSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2,
					int arg3) {
				// When user changed the Text
				adapter3.getFilter().filter(cs);//ourarrayadapter adapter3
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
			}
		});

	}

	  public void displayInterstitial() {
	        // If Ads are loaded, show Interstitial else show nothing.
	        if (interstitial.isLoaded()) {
	            interstitial.show();
	        }
	    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		if(nameOfCountry != null){
			inflater.inflate(R.menu.main2, menu);// by country in menu main.xml
		}

		else{
		//	inflater.inflate(R.menu.main, menu);// home in menu main.xml
		}
		return true;////////////////////////////////////////////////////////change to true
	}

	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{

		case R.id.menu_exit:

			Intent intent = new Intent(this, SecondMainActivity.class);
			Bundle b = new Bundle();

			b.putString("key", "jjj");

			intent.putExtras(b);

			startActivity(intent);
			return true;
		case R.id.menu_home:

			intent = new Intent(this, MainActivity.class);
			b = new Bundle();

			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}


}