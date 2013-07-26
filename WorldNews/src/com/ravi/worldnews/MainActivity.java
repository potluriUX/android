package com.ravi.worldnews;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

@SuppressLint("NewApi")
public class MainActivity extends Activity {

	private Button button;
	private TextView text;
	private EditText inputSearch;
	private String nameOfCountry;

	public void onCreate(Bundle savedInstanceState) {
		final Context context = this;

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final DatabaseHandler db = new DatabaseHandler(this);
		Bundle b = getIntent().getExtras();
		List<WebLinks> links = null;
		if(b != null){
			String countryname = b.getString("countryname");
			nameOfCountry = countryname;
			
			links = db.getLinksByCountry(countryname.toLowerCase());
			LinearLayout ll = (LinearLayout)findViewById(R.id.linearLayout1);
			TextView tv = (TextView)findViewById(R.id.country_text);   
			tv.setText("Searching By Country: " + countryname + "\nPress HOME for all Countries");

		}
		else{
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
		data = (WebLinks[])list2.toArray(new WebLinks[0]);
		final ListView listview = (ListView) findViewById(R.id.listView1);

		/*final StableArrayAdapter adapter = new StableArrayAdapter(this,
				android.R.layout.simple_list_item_1, list);!!!!!!important*/
		final ArrayAdapter<WebLinks> adapter2 = new ArrayAdapter<WebLinks>(this,
				android.R.layout.simple_list_item_1, list2);
		final OurArrayAdapter adapter3 = new OurArrayAdapter(this,
				R.layout.list_item, data);
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

				intent.putExtras(b);

				startActivity(intent);
			}
			
		});

		inputSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2,
					int arg3) {
				// When user changed the Text
				adapter3.getFilter().filter(cs);
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


	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		if(nameOfCountry != null){
			inflater.inflate(R.menu.main2, menu);// by country in menu main.xml
		}

		else{
			inflater.inflate(R.menu.main, menu);// home in menu main.xml
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