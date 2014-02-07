package com.ravi.worldnews;

import java.util.ArrayList;
import java.util.List;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {
	 
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 4;
 
    // Database Name
    private static final String DATABASE_NAME = "linksManager";
 
    // Contacts table name
    private static final String TABLE_CONTACTS = "links";
    private static final String TABLE_FAV = "fav";
 
    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_LINK = "link";
    private static final String KEY_COUNTRY = "country";
    
 
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_LINK + " TEXT," + KEY_COUNTRY+ " TEXT)";
        String CREATE_CONTACTS_TABLE2 = "CREATE TABLE " + TABLE_FAV + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_LINK + " TEXT," + KEY_COUNTRY+ " TEXT)";
       
        db.execSQL(CREATE_CONTACTS_TABLE);
        db.execSQL(CREATE_CONTACTS_TABLE2);
       // addLink(new WebLinks("Google", "www.google.com", "usa"), db); //only http:// is added in webview
     		createData(db);
		
		
		
		
		

        
      
    }
 
    public void addFav(WebLinks link) {
        
   SQLiteDatabase db = this.getWritableDatabase();
        
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, link.get_name()); // Contact Name
        values.put(KEY_LINK, link.get_link()); // Contact Phone Number
        values.put(KEY_COUNTRY, link.get_country()); 
        //Log.d("Name: ", "asdf");
        // Inserting Row
        
        
        //Log.d("Name: ", "asdf");
        // Inserting Row
        db.insert(TABLE_FAV, null, values);
        
    }
    // Deleting single fav
   	public void deleteFav(int id) {
   	    SQLiteDatabase db = this.getWritableDatabase();
   	    db.delete(TABLE_FAV, KEY_ID + " = ?", new String[] { String.valueOf(id) });
   	    db.close();
   	}
		
	

	// Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
 
        // Create tables again
        onCreate(db);
    }
    public List<WebLinks> getAllFavs() {
        List<WebLinks> linkList = new ArrayList<WebLinks>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_FAV;
     
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
       
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	
                WebLinks link = new WebLinks();
                link.set_id(Integer.parseInt(cursor.getString(0)));
                link.set_name(cursor.getString(1));
                link.set_link(cursor.getString(2));
                link.set_country(cursor.getString(3));
                // Adding contact to list
                linkList.add(link);
            } while (cursor.moveToNext());
        }
     
        // return contact list
        return linkList;
    }
    public List<WebLinks> getAllLinks() {
        List<WebLinks> linkList = new ArrayList<WebLinks>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;
     
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
       
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	
                WebLinks link = new WebLinks();
                link.set_id(Integer.parseInt(cursor.getString(0)));
                link.set_name(cursor.getString(1));
                link.set_link(cursor.getString(2));
                link.set_country(cursor.getString(3));
                // Adding contact to list
                linkList.add(link);
            } while (cursor.moveToNext());
        }
     
        // return contact list
        return linkList;
    }
    public List<WebLinks> getAllLinks(Boolean countryFilteringFlag) {
        List<WebLinks> linkList = new ArrayList<WebLinks>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;
     
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
       
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	
                WebLinks link = new WebLinks();
                link.set_id(Integer.parseInt(cursor.getString(0)));
                link.set_name(cursor.getString(1));
                link.set_link(cursor.getString(2));
                link.set_country(cursor.getString(3));
                link._countryFilteringFlag = countryFilteringFlag;
                // Adding contact to list
                linkList.add(link);
            } while (cursor.moveToNext());
        }
     
        // return contact list
        return linkList;
    }
    WebLinks getLink(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
                KEY_NAME, KEY_LINK, KEY_COUNTRY }, KEY_NAME + "=?",
                new String[] { name }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        WebLinks link = new WebLinks(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3));
        // return contact
        return link;
    }
    WebLinks getLinkByID(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
                KEY_NAME, KEY_LINK, KEY_COUNTRY }, KEY_ID + "=?",
                new String[] { id }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        WebLinks link = new WebLinks(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3));
        // return contact
        return link;
    }
    public List<WebLinks> getLinksByCountry(String name) {
    	 List<WebLinks> linkList = new ArrayList<WebLinks>();
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
                KEY_NAME, KEY_LINK, KEY_COUNTRY }, KEY_COUNTRY + " LIKE ? ",
                new String[] { name+"%" }, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
            	
                WebLinks link = new WebLinks();
                link.set_id(Integer.parseInt(cursor.getString(0)));
                link.set_name(cursor.getString(1));
                link.set_link(cursor.getString(2));
                link.set_country(cursor.getString(3));
                // Adding contact to list
                linkList.add(link);
            } while (cursor.moveToNext());
        }
     
        // return contact list
        return linkList;
    }
    public void addLink(WebLinks link, SQLiteDatabase db) {
       
        
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, link.get_name()); // Contact Name
        values.put(KEY_LINK, link.get_link()); // Contact Phone Number
        values.put(KEY_COUNTRY, link.get_country()); 
        //Log.d("Name: ", "asdf");
        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        
    }
    public void addLink(WebLinks link) {
        SQLiteDatabase db = this.getWritableDatabase();
        
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, link.get_name()); // Contact Name
        values.put(KEY_LINK, link.get_link()); // Contact Phone Number
        values.put(KEY_COUNTRY, link.get_country()); 
        //Log.d("Name: ", "asdf");
        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        
    }
    private void createData(SQLiteDatabase db) {
    	
    	addLink(new WebLinks("Times Of India", "http://m.timesofindia.com", "india"), db);
        addLink(new WebLinks("NDTV", "http://m.ndtv.com", "india"), db);
	addLink(new WebLinks(	"ABC News",		" http://m.abcnews.com/",						"usa"	), db);
	addLink(new WebLinks(	"Agence France Presse",	        	"http://afpmobile.com/",						"usa"	), db);
	addLink(new WebLinks(	"AJC",	        	"http://ww2.ajcmobile.com/",						"usa"	), db);
	addLink(new WebLinks(	"AOL News",	        	"http://m.aol.com/portal/articleList?feed=topnews&amp;icid=tn_cat",						"usa"	), db);
	addLink(new WebLinks(	"AP News",	        	"http://m.apnews.com/ap/index.htm",						"usa"	), db);
	addLink(new WebLinks(	"Baltimore Sun",	        	"http://mobile.baltimoresun.com/",						"usa"	), db);
	addLink(new WebLinks(	"BBC",	        	"http://www.bbc.co.uk/mobile/pda/",						"uk"	), db);
	addLink(new WebLinks(	"Bloomberg Business Week",	        	"http://businessweek.mobi/",						"usa"	), db);
	addLink(new WebLinks(	"Boston Herald",	        	"http://www.bostonherald.com/mobile/",						"usa"	), db);
	addLink(new WebLinks(	"CBC News",	        	"http://www.cbc.ca/mobile/",						"usa"	), db);
	addLink(new WebLinks(	"CBN",	        	"http://mobile.cbn.com/newsindex.aspx",						"usa"	), db);
	addLink(new WebLinks(	"Chicago Tribune",	        	"http://mobile.chicagotribune.com/",						"usa"	), db);
	addLink(new WebLinks(	"cpatl.mobi",	        	"http://www.cpatl.mobi/",						"usa"	), db);
	addLink(new WebLinks(	"CBS",	        	"http://wap.cbsnews.com/",						"usa"	), db);
	addLink(new WebLinks(	"CNBC",	        	"http://mobile.cnbc.com/",						"usa"	), db);
	addLink(new WebLinks(	"CS Monitor",	        	"http://www.csmonitor.com/textedition",						"usa"	), db);
	addLink(new WebLinks(	"DAILY MAIL",	        	"http://www.dailymail.co.uk/pages/text/index.html?in_page_id=1766",						"uk"	), db);
	addLink(new WebLinks(	"Daily Mirror",	        	"http://m.mirror.co.uk/",						"uk"	), db);
	addLink(new WebLinks(	"Dallas Morning News",	        	"http://m.dallasnews.com/",						"usa"	), db);
	addLink(new WebLinks(	"Der Spiegel (English)",	        	"http://mobile.spiegel.de/rubriken.do;jsessionid=YuFCsha8Ia4x14Tl63NcjA**?id=international",						"usa"	), db);
	addLink(new WebLinks(	"Detroit Free Press",	        	"http://m.freep.com/",						"usa"	), db);
	addLink(new WebLinks(	"Detroit News",	        	"http://m.detnews.com/",						"usa"	), db);
	addLink(new WebLinks(	"Deutsche Welle (English)",	        	"http://pda.dw-world.de/english/",						"usa"	), db);
	addLink(new WebLinks(	"Drudge Report",	        	"http://www.idrudgereport.com/",						"usa"	), db);
	addLink(new WebLinks(	"ESPN",	        	"http://m.espn.go.com/wireless/index?w=17l43&amp;i=COM",						"usa"	), db);
	addLink(new WebLinks(	"Financial Times",	        	"http://wap.ft.com/",						"usa"	), db);
	addLink(new WebLinks(	"Forbes",	        	"http://mobile.forbes.com/",						"usa"	), db);
	addLink(new WebLinks(	"Fox News",	        	"http://www.foxnews.mobi/",						"usa"	), db);
	addLink(new WebLinks(	"Fox Business",	        	"http://m.foxbusiness.com/",						"usa"	), db);
	addLink(new WebLinks(	"Gannett Mobile",	        	"http://www.idrudgereport.com/mobileFriendlyGannett.aspx",						"usa"	), db);
	addLink(new WebLinks(	"Google News",	        	"http://www.google.com/m/news?hl=en",						"usa"	), db);
	addLink(new WebLinks(	"Hollywood Reporter",	        	"http://mobile.hollywoodreporter.com/",						"usa"	), db);
	addLink(new WebLinks(	"Houston Chronicle",	        	"http://mobile.chron.com/",						"usa"	), db);
	addLink(new WebLinks(	"Huffington Post",	        	"http://m.huffingtonpost.com/",						"usa"	), db);
	addLink(new WebLinks(	"International Herald Tribune",	        	"http://mobile.iht.com/",						"usa"	), db);
	addLink(new WebLinks(	"Indianapolis Star",	        	"http://m.indystar.com/",						"usa"	), db);
	addLink(new WebLinks(	"Japan Today",	        	"http://www.japantoday.com/smartphone/",						"usa"	), db);
											
											
											
	addLink(new WebLinks(	"London Times",	        	"http://timesmobile.mobi/",						"uk"	), db);
	addLink(new WebLinks(	"Miami Herald",	        	"http://mh.vrvm.com/mh",						"usa"	), db);
	addLink(new WebLinks(	"Minneapolis Star Tribune",	        	"http://www.startribune.com/mobile/",						"usa"	), db);
	addLink(new WebLinks(	"Moscow Times",	        	"http://pda.moscowtimes.ru/",						"usa"	), db);
	addLink(new WebLinks(	"National Post (Canada)",	        	"http://www.nationalpost.com/m/index.html",						"usa"	), db);
	addLink(new WebLinks(	"Newsday",	        	"http://mobile.newsday.com/",						"usa"	), db);
	addLink(new WebLinks(	"Newsmax",	        	"http://www.newsmax.com/m/",						"usa"	), db);
	addLink(new WebLinks(	"NewsOK",	        	"http://m.newsok.com/",						"usa"	), db);
	addLink(new WebLinks(	"Newsweek",	        	"http://mobile.newsweek.com/",						"usa"	), db);
	addLink(new WebLinks(	"NPR",	        	"http://m.npr.org/",						"usa"	), db);
	addLink(new WebLinks(	"NY1",	        	"http://www.ny1.com/text/default.aspx",						"usa"	), db);
	addLink(new WebLinks(	"NY Post",	        	"http://m.nypost.com/",						"usa"	), db);
	addLink(new WebLinks(	"NY Times",	        	"http://mobile.nytimes.com/",						"usa"	), db);
	addLink(new WebLinks(	"Orange County Register",	        	"http://www.ocregister.com/",						"usa"	), db);
	addLink(new WebLinks(	"Orlando Sentinel.com",	        	"http://www.orlandosentinel.com/iphone/",						"usa"	), db);
	addLink(new WebLinks(	"Palm Beach Post",	        	"http://m.palmbeachpost.com/",						"usa"	), db);
	addLink(new WebLinks(	"People",	        	"http://m.people.com/",						"usa"	), db);
	addLink(new WebLinks(	"Philadelphia Inquirer",	        	"http://m.philly.com/",						"usa"	), db);
	addLink(new WebLinks(	"Plain Dealer",	        	"http://mobile.plaind.com/",						"usa"	), db);
	addLink(new WebLinks(	"Politico",	        	"http://mobile.politico.com/",						"usa"	), db);
	addLink(new WebLinks(	"Real Clear Politics",	        	"http://www.realclearpolitics.com/mobile/",						"usa"	), db);
	addLink(new WebLinks(	"Reuters",	        	"http://mobile.reuters.com/",						"usa"	), db);
	addLink(new WebLinks(	"Sacramento Bee",	        	"http://m.sacbee.com/",						"usa"	), db);
	addLink(new WebLinks(	"Salon",	        	"http://www.salon.com/partner/avantgo",						"usa"	), db);
	addLink(new WebLinks(	"San Diego Union-Tribune",	        	"http://www.signon.mobi/",						"usa"	), db);
	addLink(new WebLinks(	"San Francisco Chronicle",	        	"http://mobile.sfgate.com/",						"usa"	), db);
	addLink(new WebLinks(	"Seattle Times",	        	"http://seattletimes.nwsource.com/text/",						"usa"	), db);
	addLink(new WebLinks(	"Slashdot",	        	"http://wwww.slashdot.org/",						"usa"	), db);
	addLink(new WebLinks(	"Slate",	        	"http://mobile.slate.com/",						"usa"	), db);
	addLink(new WebLinks(	"South China Morning Post",	        	"http://mobile.scmp.com/",						"usa"	), db);
	addLink(new WebLinks(	"Stars and Stripes",	        	"http://www.stripes.com/mobile/",						"usa"	), db);
	addLink(new WebLinks(	"The Sun",	        	"http://www.thesun.mobi/",						"usa"	), db);
	addLink(new WebLinks(	"Sun Sentinel",	        	"http://mobile.sun-sentinel.com/",						"usa"	), db);
	addLink(new WebLinks(	"Sydney Morning Herald",	        	"http://www.smh.com.au/text/index.html",						"australia"	), db);
	addLink(new WebLinks(	"Tampa Bay Online",	        	"http://m.tbo.com/",						"usa"	), db);
	addLink(new WebLinks(	"Telegraph",	        	"http://m.telegraph.co.uk/",						"uk"	), db);
	addLink(new WebLinks(	"The Hill",	        	"http://mobile.thehill.com/",						"usa"	), db);
	addLink(new WebLinks(	"Time",	        	"http://mobile.time.com/",						"usa"	), db);
	addLink(new WebLinks(	"Today's Zaman",	        	"http://www.todayszaman.com/mobile.action",						"usa"	), db);
	addLink(new WebLinks(	"The Toronto Star",	        	"http://mobile.thestar.com/",						"usa"	), db);
	addLink(new WebLinks(	"UPI",	        	"http://m.upi.com/",						"usa"	), db);
	addLink(new WebLinks(	"US News and World Report",	        	"http://www.usnews.com/mobile/",						"usa"	), db);
	addLink(new WebLinks(	"USAToday",	        	"http://m.usatoday.com/",						"usa"	), db);
	addLink(new WebLinks(	"VOA",	        	"http://m.voanews.com/",						"usa"	), db);
	addLink(new WebLinks(	"Washington Post",	        	"http://mobile.washingtonpost.com/",						"usa"	), db);
	addLink(new WebLinks(	"Weather Underground",	        	"http://i.wund.com/",						"usa"	), db);
	addLink(new WebLinks(	"WFTV",	        	"http://wap.wftv.com/",						"usa"	), db);
	addLink(new WebLinks(	"WorldNetDaily",	        	"http://mobile.wnd.com/",						"usa"	), db);
	addLink(new WebLinks(	"WSJ",	        	"http://mobile2.wsj.com/",						"usa"	), db);
	addLink(new WebLinks(	"Asian Age",		"http://www.asianage.com/",						"india"	), db);
	addLink(new WebLinks(	"Asian News International (ANI)",		"http://www.aniin.com/",						"india"	), db);
	addLink(new WebLinks(	"Business Line",		"http://www.thehindubusinessline.com/",						"india"	), db);
	addLink(new WebLinks(	"Business Review India",		"http://www.businessreviewindia.in/",						"india"	), db);
	addLink(new WebLinks(	"Business Standard",		"http://www.business-standard.com/",						"india"	), db);
	addLink(new WebLinks(	"CanIndia",		"http://canindia.com/",						"india"	), db);
	addLink(new WebLinks(	"DNA (Daily News & Analysis)",		"http://www.dnaindia.com/",						"india"	), db);
	addLink(new WebLinks(	"Economic Revolution, The",		"http://www.theeconomicrevolution.com/",						"india"	), db);
	addLink(new WebLinks(	"Economic Times",		"http://economictimes.indiatimes.com/",						"india"	), db);
	addLink(new WebLinks(	"Express India",		"http://www.expressindia.com/",						"india"	), db);
	addLink(new WebLinks(	"Financial Express",		"http://www.financialexpress.com/",						"india"	), db);
	addLink(new WebLinks(	"Hard News",		"http://www.hardnewsmedia.com/",						"india"	), db);
	addLink(new WebLinks(	"Hindu",		"http://www.hinduonnet.com/",						"india"	), db);
	addLink(new WebLinks(	"Hindu Group of Publications",		"http://www.hinduonline.com/",						"india"	), db);
	addLink(new WebLinks(	"Hindustan Times",		"http://www.hindustantimes.com/",						"india"	), db);
	addLink(new WebLinks(	"India Daily",		"http://www.indiadaily.com/",						"india"	), db);
	addLink(new WebLinks(	"Indian Express",		"http://www.indianexpress.com/",						"india"	), db);
	addLink(new WebLinks(	"India Vision",		"http://www.indiavision.com/",						"india"	), db);
	addLink(new WebLinks(	"India West",		"http://www.indiawest.com/",						"india"	), db);
	addLink(new WebLinks(	"India Wires",		"http://indiawires.com/",						"india"	), db);
	addLink(new WebLinks(	"Mid-Day",		"http://www.mid-day.com/",						"india"	), db);
	addLink(new WebLinks(	"Milli Gazette",		"http://www.milligazette.com/",						"india"	), db);
	addLink(new WebLinks(	"Mumbai Mirror",		"http://www.mumbaimirror.com/",						"india"	), db);
	addLink(new WebLinks(	"ePolitix.com",		"http://www.epolitix.com/",						"uk"	), db);
	addLink(new WebLinks(	"Expatica - UK",		"http://www.expatica.co.uk/",						"uk"	), db);
	addLink(new WebLinks(	"Evening Standard",		"http://www.thisislondon.co.uk/",						"uk"	), db);
	addLink(new WebLinks(	"Financial Times",		"http://www.ft.com/",						"uk"	), db);
	addLink(new WebLinks(	"Guardian",		"http://www.guardian.co.uk/",						"uk"	), db);
	addLink(new WebLinks(	"Independent",		"http://www.independent.co.uk/",						"uk"	), db);
	addLink(new WebLinks(	"International Business Times",		"http://www.ibtimes.co.uk/news/uk/",						"uk"	), db);
	addLink(new WebLinks(	"ITN",		"http://www.itn.co.uk/",						"uk"	), db);
	addLink(new WebLinks(	"Liberal",		"http://www.theliberal.co.uk/",						"uk"	), db);
	addLink(new WebLinks(	"Loot",		"http://www.loot.com/",						"uk"	), db);
	addLink(new WebLinks(	"Mirror",		"http://www.mirror.co.uk/",						"uk"	), db);
	addLink(new WebLinks(	"Morning Star",		"http://www.morningstaronline.co.uk/",						"uk"	), db);
	addLink(new WebLinks(	"New Left Review",		"http://www.newleftreview.org/",						"uk"	), db);
	addLink(new WebLinks(	"New Statesman",		"http://www.newstatesman.com/",						"uk"	), db);
	addLink(new WebLinks(	"NewsNow",		"http://www.newsnow.co.uk/",						"uk"	), db);
	addLink(new WebLinks(	"Observer",		"http://observer.guardian.co.uk/",						"uk"	), db);
	addLink(new WebLinks(	"Orange News",		"http://web.orange.co.uk/p/news/home",						"uk"	), db);
	addLink(new WebLinks(	"Press Gazette",		"http://www.pressgazette.co.uk/",						"uk"	), db);
	addLink(new WebLinks(	"Private Eye",		"http://www.private-eye.co.uk/",						"uk"	), db);
	addLink(new WebLinks(	"Prospect Magazine",		"http://www.prospect-magazine.co.uk/",						"uk"	), db);
	addLink(new WebLinks(	"Red Pepper",		"http://www.redpepper.org.uk/",						"uk"	), db);
	addLink(new WebLinks(	"Reuters",		"http://www.reuters.co.uk/",						"uk"	), db);
	addLink(new WebLinks(	"Scotsman",		"http://www.scotsman.com/",						"uk"	), db);
	addLink(new WebLinks(	"Sky News",		"http://news.sky.com/",						"uk"	), db);
	addLink(new WebLinks(	"Socialist Worker",		"http://www.socialistworker.co.uk/",						"uk"	), db);
	addLink(new WebLinks(	"Spectator",		"http://www.spectator.co.uk/",						"uk"	), db);
	addLink(new WebLinks(	"spiked",		"http://www.spiked-online.com/",						"uk"	), db);
	addLink(new WebLinks(	"Stage",		"http://www.thestage.co.uk/",						"uk"	), db);
	addLink(new WebLinks(	"Sun",		"http://www.thesun.co.uk/",						"uk"	), db);
	addLink(new WebLinks(	"Kashmir Monitor",		"http://kashmirmonitor.org/",						"india"	), db);
	addLink(new WebLinks(	"Kashmir Observer",		"http://www.kashmirobserver.com/",						"india"	), db);
	addLink(new WebLinks(	"Kashmir Times",		"http://www.kashmirtimes.com/",						"india"	), db);
	addLink(new WebLinks(	"Kaumudi",		"http://www.kaumudi.com/",						"india"	), db);
	addLink(new WebLinks(	"Kolkata Mirror",		"http://www.kolkatamirror.com/",						"india"	), db);
	addLink(new WebLinks(	"Malayala Manorama",		"http://www.manoramaonline.com/",						"india"	), db);
	addLink(new WebLinks(	"Navhind Times",		"http://www.navhindtimes.com/",						"india"	), db);
	addLink(new WebLinks(	"New Kerala",		"http://www.newkerala.com/",						"india"	), db);
	addLink(new WebLinks(	"News Today",		"http://www.newstodaynet.com/",						"india"	), db);
	addLink(new WebLinks(	"Northlines",		"http://www.thenorthlines.com/",						"india"	), db);
	addLink(new WebLinks(	"Orissa India",		"http://www.orissaindia.com/",						"india"	), db);
	addLink(new WebLinks(	"Pioneer",		"http://www.dailypioneer.com/",						"india"	), db);
	addLink(new WebLinks(	"Pragativadi",		"http://www.pragativadi.com/",						"india"	), db);
	addLink(new WebLinks(	"Punjab Newsline",		"http://www.punjabnewsline.com/",						"india"	), db);
	addLink(new WebLinks(	"Ranchi Express",		"http://www.ranchiexpress.com/",						"india"	), db);
	addLink(new WebLinks(	"Samachar",		"http://www.mysoresamachar.com/",						"india"	), db);
	addLink(new WebLinks(	"Sentinel",		"http://www.sentinelassam.com/",						"india"	), db);
	addLink(new WebLinks(	"Star of Mysore",		"http://www.starofmysore.com/",						"india"	), db);
	addLink(new WebLinks(	"State Times",		"http://www.statetimes.in/",						"india"	), db);
	addLink(new WebLinks(	"Times of Assam",		"http://www.timesofassam.com/",						"india"	), db);
	addLink(new WebLinks(	"Tribune",		"http://www.tribuneindia.com/",						"india"	), db);
	addLink(new WebLinks(	"AsiaXPAT",		"http://www.asiaxpat.com/",						"china"	), db);
	addLink(new WebLinks(	"Beijing Review",		"http://www.bjreview.com.cn/",						"china"	), db);
	addLink(new WebLinks(	"Beijing This Month",		"http://www.btmbeijing.com/",						"china"	), db);
	addLink(new WebLinks(	"Business China",		"http://en.21cbh.com/",						"china"	), db);
	addLink(new WebLinks(	"CCTV International",		"http://english.cctv.com/",						"china"	), db);
	addLink(new WebLinks(	"China Daily",		"http://www.chinadaily.com.cn/",						"china"	), db);
	addLink(new WebLinks(	"China Digital Times",		"http://chinadigitaltimes.net/",						"china"	), db);
	addLink(new WebLinks(	"China Internet Information Center",		"http://www.china.org.cn/",						"china"	), db);
	addLink(new WebLinks(	"China Now",		"http://www.chinanowmag.com/",						"china"	), db);
	addLink(new WebLinks(	"China Perspective",		"http://www.thechinaperspective.com/",						"china"	), db);
	addLink(new WebLinks(	"China Radio International",		"http://www.chinabroadcast.cn/",						"china"	), db);
	addLink(new WebLinks(	"China Securities Journal",		"http://www.cs.com.cn/english/",						"china"	), db);
	addLink(new WebLinks(	"China Today",		"http://www.chinatoday.com.cn/",						"china"	), db);
	addLink(new WebLinks(	"ChinaOnTV",		"http://www.chinaontv.com/",						"china"	), db);
	addLink(new WebLinks(	"Eastday.com",		"http://english.eastday.com/",						"china"	), db);
	addLink(new WebLinks(	"Economic Observer",		"http://www.eeo.com.cn/ens/",						"china"	), db);
	addLink(new WebLinks(	"Epoch Times ,The",		"http://www.theepochtimes.com/",						"china"	), db);
	addLink(new WebLinks(	"International Business Times",		"http://hken.ibtimes.com/",						"china"	), db);
	addLink(new WebLinks(	"People's Daily",		"http://english.peopledaily.com.cn/",						"china"	), db);
	addLink(new WebLinks(	"PLA Daily",		"http://english.pladaily.com.cn/",						"china"	), db);
	addLink(new WebLinks(	"Shanghai Daily",		"http://www.shanghaidaily.com/",						"china"	), db);
	addLink(new WebLinks(	"Shanghai Star",		"http://app1.chinadaily.com.cn/star/",						"china"	), db);
	addLink(new WebLinks(	"Shenzhen Daily",		"http://www.szdaily.com/",						"china"	), db);
	addLink(new WebLinks(	"Sinopolis",		"http://www.sinofile.net/",						"china"	), db);
	addLink(new WebLinks(	"South China Morning Post",		"http://www.scmp.com/",						"china"	), db);
	addLink(new WebLinks(	"Xinhua News Agency",		"http://www.xinhuanet.com/english/index.htm",						"china"	), db);
	addLink(new WebLinks(	"Asahi Shimbun",		"http://www.asahi.com/english/",						"japan"	), db);
	addLink(new WebLinks(	"Fukuoka Now",		"http://www.fukuoka-now.com/",						"japan"	), db);
	addLink(new WebLinks(	"Glocom Platform",		"http://www.glocom.org/",						"japan"	), db);
	addLink(new WebLinks(	"Japan Echo",		"http://www.japanecho.co.jp/en/index.html",						"japan"	), db);
	addLink(new WebLinks(	"Japan Focus",		"http://www.japanfocus.org/",						"japan"	), db);
	addLink(new WebLinks(	"Japan Internet Report (JIR)",		"http://www.jir.net/",						"japan"	), db);
	addLink(new WebLinks(	"Japan Marketing News",		"http://www.japanmarketingnews.com/",						"japan"	), db);
	addLink(new WebLinks(	"Japan Media Review",		"http://www.ojr.org/japan/media/section.php",						"japan"	), db);
	addLink(new WebLinks(	"Japan Times",		"http://www.japantimes.co.jp/",						"japan"	), db);
	addLink(new WebLinks(	"Japan Today",		"http://www.japantoday.com/",						"japan"	), db);
	addLink(new WebLinks(	"Japan Update",		"http://www.japanupdate.com/",						"japan"	), db);
	addLink(new WebLinks(	"J@pan Inc.",		"http://www.japaninc.net/",						"japan"	), db);
	addLink(new WebLinks(	"Japander.com",		"http://www.japander.com/",						"japan"	), db);
	addLink(new WebLinks(	"Japanzine",		"http://www.seekjapan.jp/",						"japan"	), db);
	addLink(new WebLinks(	"Kyodo",		"http://english.kyodonews.jp/",						"japan"	), db);
	addLink(new WebLinks(	"Kyoto Shimbun",		"http://www.kyoto-np.co.jp/kp/english/",						"japan"	), db);
	addLink(new WebLinks(	"Mainichi Daily News",		"http://mdn.mainichi.jp/",						"japan"	), db);
	addLink(new WebLinks(	"Metropolis",		"http://www.metropolis.co.jp/",						"japan"	), db);
	addLink(new WebLinks(	"Modern Tokyo Times",		"http://moderntokyotimes.com/",						"japan"	), db);
	addLink(new WebLinks(	"Nikkei Net",		"http://www.nni.nikkei.co.jp/",						"japan"	), db);
	addLink(new WebLinks(	"NewsOnJapan.com",		"http://newsonjapan.com/",						"japan"	), db);
	addLink(new WebLinks(	"Tokyo Journal",		"http://www.tokyo.to/",						"japan"	), db);
	addLink(new WebLinks(	"Tokyo Families",		"http://www.tokyofamilies.com/",						"japan"	), db);
	addLink(new WebLinks(	"Tokyo Weekender",		"http://www.tokyoweekender.com/",						"japan"	), db);
	addLink(new WebLinks(	"Web Japan",		"http://web-japan.org/",						"japan"	), db);
	addLink(new WebLinks(	"Yomiuri Shimbun",		"http://www.yomiuri.co.jp/dy/",						"japan"	), db);
	addLink(new WebLinks(	"BBC Country Profile: Brazil",		"http://news.bbc.co.uk/1/hi/world/americas/country_profiles/1227110.stm",						"brazil"	), db);
	addLink(new WebLinks(	"Brazilink",		"http://www.brazilink.org/",						"brazil"	), db);
	addLink(new WebLinks(	"Brazzil",		"http://www.brazzil.com/",						"brazil"	), db);
	addLink(new WebLinks(	"Brazil Dispatch",		"http://www.brazildispatch.com/",						"brazil"	), db);
	addLink(new WebLinks(	"Economist - Brazil",		"http://www.economist.com/countries/Brazil/",						"brazil"	), db);
	addLink(new WebLinks(	"gringoes.com",		"http://www.gringoes.com/",						"brazil"	), db);
	addLink(new WebLinks(	"InfoBrazil.Com",		"http://www.infobrazil.com/",						"brazil"	), db);
	addLink(new WebLinks(	"Jungle Drums Online",		"http://jungledrumsonline.com/",						"brazil"	), db);
	addLink(new WebLinks(	"Rio Times",		"http://riotimesonline.com/",						"brazil"	), db);
	addLink(new WebLinks(	"Agentura.ru",		"http://www.agentura.ru/english/",						"russia"	), db);
	addLink(new WebLinks(	"AK&M - News Agency",		"http://www.akm.ru/eng/",						"russia"	), db);
	addLink(new WebLinks(	"Eurasian Home",		"http://www.eurasianhome.org/xml/t/default.xml?lang=en&amp;s=-1",						"russia"	), db);
	addLink(new WebLinks(	"eXile",		"http://exiledonline.com/",						"russia"	), db);
	addLink(new WebLinks(	"Expatica - Russia",		"http://www.expatica.ru/",						"russia"	), db);
	addLink(new WebLinks(	"Interfax",		"http://www.interfax.com/",						"russia"	), db);
	addLink(new WebLinks(	"Inside Russia and Eurasia",		"http://www.russia-eurasia.net/",						"russia"	), db);
	addLink(new WebLinks(	"ITAR-TASS",		"http://www.itar-tass.com/eng/",						"russia"	), db);
	addLink(new WebLinks(	"La Russophobe",		"http://larussophobe.wordpress.com/",						"russia"	), db);
	addLink(new WebLinks(	"Moscow News",		"http://www.mnweekly.ru/",						"russia"	), db);
	addLink(new WebLinks(	"Moscow Times",		"http://www.themoscowtimes.com/",						"russia"	), db);
	addLink(new WebLinks(	"Neftegaz",		"http://english.neftegaz.ru/en/",						"russia"	), db);
	addLink(new WebLinks(	"Novaya Gazeta",		"http://en.novayagazeta.ru/",						"russia"	), db);
	addLink(new WebLinks(	"Other Russia, The",		"http://www.theotherrussia.org/",						"russia"	), db);
	addLink(new WebLinks(	"Pravda.ru",		"http://english.pravda.ru/",						"russia"	), db);
	addLink(new WebLinks(	"Prima-News",		"http://www.prima-news.ru/eng/",						"russia"	), db);
	addLink(new WebLinks(	"Prime-Tass",		"http://www.prime-tass.com/",						"russia"	), db);
	addLink(new WebLinks(	"Radio Free Europe - Russia",		"http://www.rferl.org/section/Russia/161.html",						"russia"	), db);
	addLink(new WebLinks(	"Radio Voice of Russia",		"http://english.ruvr.ru/",						"russia"	), db);
	addLink(new WebLinks(	"RBC News - RosBusinessConsulting",		"http://www.rbcnews.com/",						"russia"	), db);
	addLink(new WebLinks(	"Regnum",		"http://www.regnum.ru/english/",						"russia"	), db);
	addLink(new WebLinks(	"RIA-Novosti",		"http://en.rian.ru/",						"russia"	), db);
	addLink(new WebLinks(	"RusPress",		"http://rus-press.com/",						"russia"	), db);
	addLink(new WebLinks(	"Russia Beyond the Headlines",		"http://rbth.ru/",						"russia"	), db);
	addLink(new WebLinks(	"Russia in Global Affairs",		"http://eng.globalaffairs.ru/",						"russia"	), db);
	addLink(new WebLinks(	"Russia Profile",		"http://www.russiaprofile.org/",						"russia"	), db);
	addLink(new WebLinks(	"Russia Today",		"http://www.russiatoday.ru/",						"russia"	), db);
	addLink(new WebLinks(	"Russian Life",		"http://www.russianlife.com/",						"russia"	), db);
	addLink(new WebLinks(	"Siberian Light - Blogging Russia",		"http://www.siberianlight.net/",						"russia"	), db);
	addLink(new WebLinks(	"St. Petersburg Times",		"http://www.sptimesrussia.com/",						"russia"	), db);
	addLink(new WebLinks(	"Vladivostok Times",		"http://vladivostoktimes.ru/",						"russia"	), db);
	addLink(new WebLinks(	"American Institute for Contemporary German Studies",		"http://www.aicgs.org/",						"germany"	), db);
	addLink(new WebLinks(	"Bild",		"http://www.bild.de/BILD/news/bild-english/home/home.html",						"germany"	), db);
	addLink(new WebLinks(	"Brit, The",		"http://www.thebrit.co.uk/germany/",						"germany"	), db);
	addLink(new WebLinks(	"Bundesregierung Deutschland",		"http://www.bundesregierung.de/Webs/Breg/EN/Homepage/home.html",						"germany"	), db);
	addLink(new WebLinks(	"Deutsche Welle",		"http://www.dw-world.de/",						"germany"	), db);
	addLink(new WebLinks(	"Deutsche Aussenpolitik",		"http://www.deutsche-aussenpolitik.de/",						"germany"	), db);
	addLink(new WebLinks(	"Deutschland",		"http://www.magazine-deutschland.de/",						"germany"	), db);
	addLink(new WebLinks(	"Expatica.com - Germany",		"http://www.expatica.com/de/main.html",						"germany"	), db);
	addLink(new WebLinks(	"Ex-Berliner",		"http://www.exberliner.net/",						"germany"	), db);
	addLink(new WebLinks(	"German News",		"http://www.germannews.com/index_e.asp",						"germany"	), db);
	addLink(new WebLinks(	"Germany Info",		"http://www.germany.info/Vertretung/usa/en/Startseite.html",						"germany"	), db);
	addLink(new WebLinks(	"Local, The",		"http://www.thelocal.de/",						"germany"	), db);
	addLink(new WebLinks(	"Munich Times",		"http://www.munich-times.com/",						"germany"	), db);
	addLink(new WebLinks(	"New Berlin Magazine",		"http://www.newberlinmagazine.com/",						"germany"	), db);
	addLink(new WebLinks(	"signandsight.com",		"http://www.signandsight.com/",						"germany"	), db);
	addLink(new WebLinks(	"Spiegel",		"http://www.spiegel.de/international",						"germany"	), db);
	addLink(new WebLinks(	"Agence France-Presse (AFP)",		"http://www.afp.com/english/home/",						"france"	), db);
	addLink(new WebLinks(	"Connexion",		"http://www.connexionfrance.com/",						"france"	), db);
	addLink(new WebLinks(	"Dordogne Today",		"http://www.dordognetoday.com/",						"france"	), db);
	addLink(new WebLinks(	"Expatica.com - France",		"http://www.expatica.com/fr/main.html",						"france"	), db);
	addLink(new WebLinks(	"France 24",		"http://www.france24.com/",						"france"	), db);
	addLink(new WebLinks(	"France Diplomatie",		"http://www.diplomatie.gouv.fr/en/",						"france"	), db);
	addLink(new WebLinks(	"French Property News",		"http://www.french-property-news.com/",						"france"	), db);
	addLink(new WebLinks(	"French Week",		"http://www.french-week.com/",						"france"	), db);
	addLink(new WebLinks(	"International Herald Tribune",		"http://www.iht.com/",						"france"	), db);
	addLink(new WebLinks(	"Le Monde Diplomatique",		"http://mondediplo.com/",						"france"	), db);
	addLink(new WebLinks(	"Local, The",		"http://www.thelocal.fr/",						"france"	), db);
	addLink(new WebLinks(	"Lost in France",		"http://www.lost-in-france.com/",						"france"	), db);
	addLink(new WebLinks(	"Metropole Paris",		"http://www.metropoleparis.com/",						"france"	), db);
	addLink(new WebLinks(	"News in Normandy",		"http://newsinnormandy.com/",						"france"	), db);
	addLink(new WebLinks(	"Normandy Advertiser",		"http://www.normandyadvertiser.fr/",						"france"	), db);
	addLink(new WebLinks(	"Paris Notes",		"http://www.parisnotes.com/",						"france"	), db);
	addLink(new WebLinks(	"Paris Voice",		"http://parisvoice.com/",						"france"	), db);
	addLink(new WebLinks(	"Paris Woman Journal",		"http://www.pariswoman.com/",						"france"	), db);
	addLink(new WebLinks(	"Radio France Internationale (RFI)",		"http://www.rfi.fr/",						"france"	), db);
	addLink(new WebLinks(	"Riviera Magazine",		"http://www.riviera-magazine.com/",						"france"	), db);
	addLink(new WebLinks(	"Riviera Times",		"http://www.rivieratimes.com/",						"france"	), db);
	addLink(new WebLinks(	"Voltaire Network",		"http://www.voltairenet.org/",						"france"	), db);
	addLink(new WebLinks(	"Gringo Gazette",		"http://www.gringogazette.com/",						"mexico"	), db);
	addLink(new WebLinks(	"Guadalajara Reporter",		"http://www.guadalajarareporter.com/",						"mexico"	), db);
	addLink(new WebLinks(	"La Jerga",		"http://www.lajerga.com/",						"mexico"	), db);
	addLink(new WebLinks(	"Mexico Connect",		"http://www.mexconnect.com/",						"mexico"	), db);
	addLink(new WebLinks(	"Mexico File",		"http://www.mexicofile.com/",						"mexico"	), db);
	addLink(new WebLinks(	"Mexico Online",		"http://www.mexonline.com/",						"mexico"	), db);
	addLink(new WebLinks(	"News Mexico",		"http://www.thenewsmexico.com/",						"mexico"	), db);
	addLink(new WebLinks(	"Oaxaca Times",		"http://www.oaxacatimes.com/",						"mexico"	), db);
	addLink(new WebLinks(	"ABS-CBN News",		"http://www.abs-cbnnews.com/",						"philippines"	), db);
	addLink(new WebLinks(	"Asian Journal",		"http://www.asianjournalusa.com/",						"philippines"	), db);
	addLink(new WebLinks(	"Balita Pinoy",		"http://www.balitapinoy.net/",						"philippines"	), db);
	addLink(new WebLinks(	"Balita.org",		"http://www.balita.com/",						"philippines"	), db);
	addLink(new WebLinks(	"Bayanihan News",		"http://www.bayanihannews.com.au/",						"philippines"	), db);
	addLink(new WebLinks(	"Bulatlat",		"http://www.bulatlat.com/",						"philippines"	), db);
	addLink(new WebLinks(	"Business World",		"http://www.bworldonline.com/",						"philippines"	), db);
	addLink(new WebLinks(	"CBCP News",		"http://www.cbcpnews.com/",						"philippines"	), db);
	addLink(new WebLinks(	"Daily Tribune",		"http://www.tribune.net.ph/",						"philippines"	), db);
	addLink(new WebLinks(	"Filipino Express",		"http://www.filipinoexpress.com/",						"philippines"	), db);
	addLink(new WebLinks(	"Filipino Migrant News",		"http://www.pinoy.net.nz/",						"philippines"	), db);
	addLink(new WebLinks(	"GMA NEWS.TV",		"http://www.gmanews.tv/index.html",						"philippines"	), db);
	addLink(new WebLinks(	"Inquirer.net",		"http://www.inquirer.net/",						"philippines"	), db);
	addLink(new WebLinks(	"Malaya",		"http://www.malaya.com.ph/",						"philippines"	), db);
	addLink(new WebLinks(	"Manila Bulletin",		"http://www.mb.com.ph/",						"philippines"	), db);
	addLink(new WebLinks(	"Manila Times",		"http://www.manilatimes.net/",						"philippines"	), db);
	addLink(new WebLinks(	"Newsbreak",		"http://www.newsbreak.com.ph/",						"philippines"	), db);
	addLink(new WebLinks(	"Philippine Center for Investigative Journalism (PCIJ)",		"http://pcij.org/",						"philippines"	), db);
	addLink(new WebLinks(	"Philippine News",		"http://www.philippinenews.com/",						"philippines"	), db);
	addLink(new WebLinks(	"Philippines News Agency",		"http://www.pna.gov.ph/",						"philippines"	), db);
	addLink(new WebLinks(	"Philippine Star",		"http://www.philstar.com/",						"philippines"	), db);
	addLink(new WebLinks(	"Sun Star",		"http://www.sunstar.com.ph/",						"philippines"	), db);
	addLink(new WebLinks(	"Adnkronos International (AKI)",		"http://www.adnkronos.com/AKI/",						"italy"	), db);
	addLink(new WebLinks(	"ANSA - Agenzia Nazionale Stampa Associata",		"http://www.ansa.it/site/notizie/awnplus/english/english.html",						"italy"	), db);
	addLink(new WebLinks(	"Corriere della Sera",		"http://www.corriere.it/english/",						"italy"	), db);
	addLink(new WebLinks(	"Dolce Vita",		"http://www.dolcevita.com/",						"italy"	), db);
	addLink(new WebLinks(	"Easy Milano",		"http://www.easymilano.it/",						"italy"	), db);
	addLink(new WebLinks(	"Florence Newspaper",		"http://www.florencenewspaper.it/",						"italy"	), db);
	addLink(new WebLinks(	"Florentine",		"http://www.theflorentine.net/",						"italy"	), db);
	addLink(new WebLinks(	"Football Italia Magazine",		"http://www.football-italia.net/",						"italy"	), db);
	addLink(new WebLinks(	"Hello Milano",		"http://www.hellomilano.it/",						"italy"	), db);
	addLink(new WebLinks(	"In Rome Now",		"http://www.inromenow.com/",						"italy"	), db);
	addLink(new WebLinks(	"Italia-Online.co.uk",		"http://www.italia-online.co.uk/",						"italy"	), db);
	addLink(new WebLinks(	"Italy Magazine",		"http://www.italymag.co.uk/",						"italy"	), db);
	addLink(new WebLinks(	"LaSpecula.com",		"http://www.laspecula.com/",						"italy"	), db);
	addLink(new WebLinks(	"Life in Italy",		"http://www.lifeinitaly.com/",						"italy"	), db);
	addLink(new WebLinks(	"Ministry of Foreign Affairs",		"http://www.esteri.it/",						"italy"	), db);
	addLink(new WebLinks(	"Only In Italy",		"http://www.onlyinitaly.com/",						"italy"	), db);
	addLink(new WebLinks(	"Panorama Italia",		"http://www.panoramitalia.com/",						"italy"	), db);
	addLink(new WebLinks(	"Rai International",		"http://www.international.rai.it/radio/multilingue/presentazioni/inglese.shtml",						"italy"	), db);
	addLink(new WebLinks(	"Roman Forum",		"http://www.theromanforum.com/",						"italy"	), db);
	addLink(new WebLinks(	"Sikania",		"http://www.sikania.it/sitosikania/index.php",						"italy"	), db);
	addLink(new WebLinks(	"The American",		"http://www.theamericanmag.com/",						"italy"	), db);
	addLink(new WebLinks(	"Valley Life",		"http://www.valleylife.it/",						"italy"	), db);
	addLink(new WebLinks(	"Wanted in Roma",		"http://www.wantedinrome.com/",						"italy"	), db);
	addLink(new WebLinks(	"www.chiesa",		"http://chiesa.espresso.repubblica.it/?eng=y",						"italy"	), db);
	addLink(new WebLinks(	"Calgary Herald",		"http://www.calgaryherald.com/",						"canada"	), db);
	addLink(new WebLinks(	"Edmonton Journal",		"http://www.edmontonjournal.com/",						"canada"	), db);
	addLink(new WebLinks(	"Montreal Gazette",		"http://www.montrealgazette.com/",						"canada"	), db);
	addLink(new WebLinks(	"Ottawa Citizen",		"http://www.ottawacitizen.com/",						"canada"	), db);
	addLink(new WebLinks(	"Vancouver Sun",		"http://www.vancouversun.com/",						"canada"	), db);
	addLink(new WebLinks(	"Agora Cosmopolitan",		"http://www.agoracosmopolitan.com/",						"canada"	), db);
	addLink(new WebLinks(	"Atlantic Business",		"http://www.abmonline.ca/",						"canada"	), db);
	addLink(new WebLinks(	"Business Review Canada",		"http://www.businessreviewcanada.ca/",						"canada"	), db);
	addLink(new WebLinks(	"Canadian Broadcasting Corporation (CBC)",		"http://www.cbc.ca/",						"canada"	), db);
	addLink(new WebLinks(	"Canadian Business",		"http://www.canadianbusiness.com/",						"canada"	), db);
	addLink(new WebLinks(	"Canadian Dimension",		"http://www.canadiandimension.com/",						"canada"	), db);
	addLink(new WebLinks(	"Canadian Press",		"http://www.thecanadianpress.com/",						"canada"	), db);
	addLink(new WebLinks(	"Canoe",		"http://www.canoe.ca/",						"canada"	), db);
	addLink(new WebLinks(	"Embassy",		"http://www.embassymag.ca/",						"canada"	), db);
	addLink(new WebLinks(	"Globe and Mail",		"http://www.theglobeandmail.com/",						"canada"	), db);
	addLink(new WebLinks(	"Hill Times",		"http://www.thehilltimes.ca/",						"canada"	), db);
	addLink(new WebLinks(	"Maclean's Newsmagazine",		"http://www.macleans.ca/",						"canada"	), db);
	addLink(new WebLinks(	"National Post",		"http://www.nationalpost.com/",						"canada"	), db);
	addLink(new WebLinks(	"rabble.ca",		"http://rabble.ca/",						"canada"	), db);
	addLink(new WebLinks(	"Straight Goods",		"http://www.straightgoods.ca/",						"canada"	), db);
	addLink(new WebLinks(	"This Magazine",		"http://www.thismagazine.ca/",						"canada"	), db);
	addLink(new WebLinks(	"Toronto Standard",		"http://www.torontostandard.com/",						"canada"	), db);
	addLink(new WebLinks(	"Toronto Star",		"http://www.thestar.com/",						"canada"	), db);
	addLink(new WebLinks(	"Toronto Sun",		"http://www.torontosun.com/",						"canada"	), db);
	addLink(new WebLinks(	"Walrus",		"http://www.walrusmagazine.com/",						"canada"	), db);
	addLink(new WebLinks(	"Western Standard",		"http://www.westernstandard.ca/",						"canada"	), db);
	addLink(new WebLinks(	"African Herald Express",		"http://www.africanheraldexpress.com/",						"nigeria"	), db);
	addLink(new WebLinks(	"AllAfrica.com: Nigeria",		"http://allafrica.com/nigeria/",						"nigeria"	), db);
	addLink(new WebLinks(	"Announcer Express",		"http://www.announcerexpressonline.com/",						"nigeria"	), db);
	addLink(new WebLinks(	"Business Day",		"http://www.businessdayonline.com/",						"nigeria"	), db);
	addLink(new WebLinks(	"Business Hallmark",		"http://www.bizhallmark.com/",						"nigeria"	), db);
	addLink(new WebLinks(	"Business News",		"http://businessnews.com.ng/",						"nigeria"	), db);
	addLink(new WebLinks(	"Business World Intelligence",		"http://www.businessworldng.com/",						"nigeria"	), db);
	addLink(new WebLinks(	"Century Newsfront",		"http://newsfrontonline.com/newspub/",						"nigeria"	), db);
	addLink(new WebLinks(	"Complete Sports",		"http://www.completesportsnigeria.com/",						"nigeria"	), db);
	addLink(new WebLinks(	"Daily Trust",		"http://www.dailytrust.com/",						"nigeria"	), db);
	addLink(new WebLinks(	"Eagle Reporters",		"http://www.eaglereporters.com/",						"nigeria"	), db);
	addLink(new WebLinks(	"Freshfacts",		"http://www.freshfactsonlinenews.net/",						"nigeria"	), db);
	addLink(new WebLinks(	"Gamji.com",		"http://www.gamji.com/",						"nigeria"	), db);
	addLink(new WebLinks(	"Guardian",		"http://www.ngrguardiannews.com/",						"nigeria"	), db);
	addLink(new WebLinks(	"Lagos Business News",		"http://www.lagosislandnews.com/",						"nigeria"	), db);
	addLink(new WebLinks(	"Lagos Literary and Arts Journal",		"http://www.lagosliteraryjournal.com/",						"nigeria"	), db);
	addLink(new WebLinks(	"Liberty Report",		"http://myondostate.com/w3/",						"nigeria"	), db);
	addLink(new WebLinks(	"Naijaleaks",		"http://www.naijaleaks.org/",						"nigeria"	), db);
	addLink(new WebLinks(	"Nation, The",		"http://www.thenationonlineng.net/web3/",						"nigeria"	), db);
	addLink(new WebLinks(	"National Accord",		"http://www.nationalaccordnewspaper.com/",						"nigeria"	), db);
	addLink(new WebLinks(	"National Daily",		"http://www.nationaldailyngr.com/",						"nigeria"	), db);
	addLink(new WebLinks(	"National Mirror",		"http://www.nationalmirroronline.net/",						"nigeria"	), db);
	addLink(new WebLinks(	"New Nigerian",		"http://www.newnigeriannews.com/",						"nigeria"	), db);
	addLink(new WebLinks(	"Nigerian Beacon",		"http://nigerianbeacon.com/",						"nigeria"	), db);
	addLink(new WebLinks(	"Nigerian Inquirer",		"http://www.nigerianinquirer.com/",						"nigeria"	), db);
	addLink(new WebLinks(	"Nigerians Abroad",		"http://nigeriansabroadlive.com/",						"nigeria"	), db);
	addLink(new WebLinks(	"Sydney Morning Herald",		"http://www.smh.com.au/",						"australia"	), db);
	addLink(new WebLinks(	"Age, The",		"http://www.theage.com.au/",						"australia"	), db);
	addLink(new WebLinks(	"Australian Broadcasting Corporation (ABC)",		"http://www.abc.net.au/",						"australia"	), db);
	addLink(new WebLinks(	"Business Review Australia",		"http://www.businessreviewaustralia.com/",						"australia"	), db);
	addLink(new WebLinks(	"Business Review Weekly",		"http://www.brw.com.au/",						"australia"	), db);
	addLink(new WebLinks(	"Canberra Times",		"http://www.canberratimes.com.au/",						"australia"	), db);
	addLink(new WebLinks(	"Crikey",		"http://www.crikey.com.au/",						"australia"	), db);
	addLink(new WebLinks(	"Financial Review",		"http://www.afr.com/",						"australia"	), db);
	addLink(new WebLinks(	"Green Left Weekly",		"http://www.greenleft.org.au/",						"australia"	), db);
	addLink(new WebLinks(	"International Business Times",		"http://au.ibtimes.com/",						"australia"	), db);
	addLink(new WebLinks(	"National Indigenous Times",		"http://www.nit.com.au/",						"australia"	), db);
	addLink(new WebLinks(	"newmatilda.com",		"http://newmatilda.com/",						"australia"	), db);
	addLink(new WebLinks(	"Ninemsn",		"http://ninemsn.com.au/",						"australia"	), db);
	addLink(new WebLinks(	"On Line Opinion",		"http://www.onlineopinion.com.au/",						"australia"	), db);
	addLink(new WebLinks(	"Special Broadcasting Service (SBS)",		"http://www.sbs.com.au/",						"australia"	), db);
	addLink(new WebLinks(	"West Australian",		"http://www.thewest.com.au/",						"australia"	), db);
	addLink(new WebLinks(	"ABHaber",		"http://www.abhaber.com/english/",						"turkey"	), db);
	addLink(new WebLinks(	"AGOS",		"http://www.agos.com.tr/",						"turkey"	), db);
	addLink(new WebLinks(	"Anadolu Agency (AA)",		"http://www.aa.com.tr/en/ingilizce-haberler/",						"turkey"	), db);
	addLink(new WebLinks(	"Cihan Media Services (CMS)",		"http://www.cihanmedya.com/",						"turkey"	), db);
	addLink(new WebLinks(	"Defence Turkey",		"http://www.defence-turkey.com/",						"turkey"	), db);
	addLink(new WebLinks(	"Didim Today",		"http://www.didimtoday.com/",						"turkey"	), db);
	addLink(new WebLinks(	"Hurriyet",		"http://www.hurriyet.com.tr/english/",						"turkey"	), db);
	addLink(new WebLinks(	"Hurriyet Daily News and Economic Review",		"http://www.hurriyetdailynews.com/",						"turkey"	), db);
	addLink(new WebLinks(	"Journal of Turkish Weekly",		"http://www.turkishweekly.net/",						"turkey"	), db);
	addLink(new WebLinks(	"Land of Lights, The",		"http://www.landoflights.net/",						"turkey"	), db);
	addLink(new WebLinks(	"MEMRI: Turkey",		"http://www.memri.org/content/en/country.htm?country=turke",						"turkey"	), db);
	addLink(new WebLinks(	"Sabah",		"http://english.sabah.com.tr/",						"turkey"	), db);
	addLink(new WebLinks(	"Tourism Travel Naration",		"http://www.turizmtatilseyahat.com/en/",						"turkey"	), db);
	addLink(new WebLinks(	"Turkey Daily News",		"http://www.turkeydailynews.com/",						"turkey"	), db);
	addLink(new WebLinks(	"Turkey Herald",		"http://www.turkeyherald.com/",						"turkey"	), db);
	addLink(new WebLinks(	"Turkish Press",		"http://www.turkishpress.com/",						"turkey"	), db);
	addLink(new WebLinks(	"Voices",		"http://www.voicesnewspaper.com/",						"turkey"	), db);
	addLink(new WebLinks(	"Yeni Safak",		"http://yenisafak.com.tr/English/",						"turkey"	), db);
	addLink(new WebLinks(	"Zaman",		"http://www.todayszaman.com/",						"turkey"	), db);
	addLink(new WebLinks(	"Andalucia",		"http://www.andalucia.com/magazine/",						"spain"	), db);
	addLink(new WebLinks(	"Canary News, The",		"http://www.thecanarynews.com/",						"spain"	), db);
	addLink(new WebLinks(	"CapCreus",		"http://capcreus.com/",						"spain"	), db);
	addLink(new WebLinks(	"Costa News",		"http://www.costa-news.com/",						"spain"	), db);
	addLink(new WebLinks(	"Costa Tropical News",		"http://www.costatropicalnews.com/",						"spain"	), db);
	addLink(new WebLinks(	"El Sun News",		"http://www.elsunnews.es/",						"spain"	), db);
	addLink(new WebLinks(	"Essential",		"http://www.essentialmagazine.com/",						"spain"	), db);
	addLink(new WebLinks(	"Euro Weekly News",		"http://www.euroweeklynews.com/",						"spain"	), db);
	addLink(new WebLinks(	"Expatica.com - Spain",		"http://www.expatica.com/es/",						"spain"	), db);
	addLink(new WebLinks(	"Fuertenews",		"http://www.fuertenews.com/",						"spain"	), db);
	addLink(new WebLinks(	"Ibiza International Magazine",		"http://www.ibiza-magazine.com/",						"spain"	), db);
	addLink(new WebLinks(	"Ibiza Spotlight",		"http://www.ibiza-spotlight.com/",						"spain"	), db);
	addLink(new WebLinks(	"In Madrid",		"http://www.in-madrid.com/",						"spain"	), db);
	addLink(new WebLinks(	"Island Connections",		"http://www.islandconnections.eu/",						"spain"	), db);
	addLink(new WebLinks(	"Javes News",		"http://www.javeanews.co.uk/",						"spain"	), db);
	addLink(new WebLinks(	"Living Tenerife",		"http://www.livingtenerife.com/",						"spain"	), db);
	addLink(new WebLinks(	"Local Connections",		"http://www.localconnections.biz/",						"spain"	), db);
	addLink(new WebLinks(	"Local, The",		"http://www.thelocal.es/",						"spain"	), db);
	addLink(new WebLinks(	"Los Gigantes",		"http://losgigantes.com/news/",						"spain"	), db);
	addLink(new WebLinks(	 "Bahrain News Agency", 		 "http://english.bna.bh/", 						 "Bahrain" 	), db);
	addLink(new WebLinks(	 "Bahrain Post", 		 "http://bahrainpost.com/", 						 "Bahrain" 	), db);
	addLink(new WebLinks(	 "Gulf Daily News", 		 "http://www.gulf-daily-news.com/", 						 "Bahrain" 	), db);
	addLink(new WebLinks(	 "Gulf Weekly", 		 "http://gulfweeklyworldwide.com/", 						 "Bahrain" 	), db);
	addLink(new WebLinks(	 "Cyprus Mail", 		 "http://www.cyprus-mail.com/news/", 						 "Cyprus" 	), db);
	addLink(new WebLinks(	 "Cyprus Weekly", 		 "http://www.cyprusweekly.com.cy/main/default.aspx", 						 "Cyprus" 	), db);
	addLink(new WebLinks(	 "Al-Ahram Weekly", 		 "http://weekly.ahram.org.eg/", 						 "Egypt" 	), db);
	addLink(new WebLinks(	 "Business Today Egypt", 		 "http://www.businesstodayegypt.com/", 						 "Egypt" 	), db);
	addLink(new WebLinks(	 "Daily News Egypt", 		 "http://www.dailystaregypt.com/", 						 "Egypt" 	), db);
	addLink(new WebLinks(	 "Egypt Today", 		 "http://www.egypttoday.com/", 						 "Egypt" 	), db);
	addLink(new WebLinks(	 "Iran Daily", 		 "http://www.iran-daily.com/1388/3498/html/", 						 "Iran" 	), db);
	addLink(new WebLinks(	 "Islamic Republic News Agency (IRNA)", 		 "http://www.irna.ir/", 						 "Iran" 	), db);
	addLink(new WebLinks(	 "Tehran Globe", 		 "http://tehranglobe.com/", 						 "Iran" 	), db);
	addLink(new WebLinks(	 "Tehran Times", 		 "http://www.tehrantimes.com/", 						 "Iran" 	), db);
	addLink(new WebLinks(	 "Iraqi Daily/A> (English)", 		 "http://iraqdaily.com/", 						 "Iraq" 	), db);
	addLink(new WebLinks(	 "Iraqi Net", 		 "http://www.iraq.net/", 						 "Iraq" 	), db);
	addLink(new WebLinks(	 "Top of Page", 		 "http://www.refdesk.com/paper_a.html#top", 						 "Israel" 	), db);
	addLink(new WebLinks(	 "Globes", 		 "http://www.globes.co.il/", 						 "Israel" 	), db);
	addLink(new WebLinks(	 "Ha'aretz - Israel Online", 		 "http://www.haaretz.co.il/", 						 "Israel" 	), db);
	addLink(new WebLinks(	 "Israel Herald", 		 "http://www.israelherald.com/", 						 "Israel" 	), db);
	addLink(new WebLinks(	 "Jerusalem Post", 		 "http://www.jpost.com/", 						 "Israel" 	), db);
	addLink(new WebLinks(	 "JTA News", 		 "http://www.jta.org/index.asp", 						 "Israel" 	), db);
	addLink(new WebLinks(	 "YnetNews", 		 "http://www.ynetnews.com/", 						 "Israel" 	), db);
	addLink(new WebLinks(	 "Jordan Times", 		 "http://www.jordantimes.com/", 						 "jordan" 	), db);
	addLink(new WebLinks(	 "Al-Qabas Newspaper", 		 "http://www.alqabas.com.kw/", 						 "Kuwait" 	), db);
	addLink(new WebLinks(	 "Kuwait Times", 		 "http://www.kuwaittimes.net/", 						 "Kuwait" 	), db);
	addLink(new WebLinks(	 "Al Anwar", 		 "http://www.alanwar.com/", 						 "Lebanon" 	), db);
	addLink(new WebLinks(	 "An Nahar", 		 "http://www.annaharonline.com/", 						 "Lebanon" 	), db);
	addLink(new WebLinks(	 "Beirut Online News", 		 "http://www.beirut-online.net/portal/index.php?id=1", 						 "Lebanon" 	), db);
	addLink(new WebLinks(	 "Daily Star", 		 "http://www.dailystar.com.lb/", 						 "Lebanon" 	), db);
	addLink(new WebLinks(	 "Oman Daily Observer", 		 "http://www.omanobserver.com/", 						 "Oman" 	), db);
	addLink(new WebLinks(	 "Oman Tribune", 		 "http://www.omantribune.com/", 						 "Oman" 	), db);
	addLink(new WebLinks(	 "The Times of Oman", 		 "http://www.timesofoman.com/", 						 "Oman" 	), db);
	addLink(new WebLinks(	 "GulfTimes.com", 		 "http://www.gulftimes.com/", 						 "Qatar" 	), db);
	addLink(new WebLinks(	 "Gulf-Times", 		 "http://www.gulf-times.com/", 						 "Qatar" 	), db);
	addLink(new WebLinks(	 "Al-Jazirah", 		 "http://www.al-jazirah.com/", 						 "Saudi Arabia" 	), db);
	addLink(new WebLinks(	 "Al-Riyadh", 		 "http://www.alriyadh-np.com/", 						 "Saudi Arabia" 	), db);
	addLink(new WebLinks(	 "Arab News", 		 "http://www.arabnews.com/", 						 "Saudi Arabia" 	), db);
	addLink(new WebLinks(	 "Asharq Al-Awsat", 		 "http://www.asharqalawsat.com/", 						 "Saudi Arabia" 	), db);
	addLink(new WebLinks(	 "Saudi Gazette", 		 "http://www.saudigazette.com.sa/", 						 "Saudi Arabia" 	), db);
	addLink(new WebLinks(	 "7 Days", 		 "http://www.7days.ae/", 						 "UAE" 	), db);
	addLink(new WebLinks(	 "Dubai Chronicle", 		 "http://www.dubaichronicle.com/", 						 "UAE" 	), db);
	addLink(new WebLinks(	 "Khaleej Times Online", 		 "http://www.khaleejtimes.com/index00.asp", 						 "UAE" 	), db);
	addLink(new WebLinks(	 "Yemen Observer", 		 "http://www.yobserver.com/", 						 "Yemen" 	), db);
	addLink(new WebLinks(	 "Yemen Post", 		 "http://www.yemenpost.net/", 						 "Yemen" 	), db);
	addLink(new WebLinks(	 "Yemen Times", 		 "http://yementimes.com/index.shtml", 						 "Yemen" 	), db);
	addLink(new WebLinks(	 "An Nahar", 		 "http://www.annaharonline.com/", 						 "Lebanon" 	), db);
	addLink(new WebLinks(	 "Beirut Online News", 		 "http://www.beirut-online.net/portal/index.php?id=1", 						 "Lebanon" 	), db);
	addLink(new WebLinks(	 "Daily Star", 		 "http://www.dailystar.com.lb/", 						 "Lebanon" 	), db);
	addLink(new WebLinks(	 "Oman Daily Observer", 		 "http://www.omanobserver.com/", 						 "Oman" 	), db);
	addLink(new WebLinks(	 "Oman Tribune", 		 "http://www.omantribune.com/", 						 "Oman" 	), db);
	addLink(new WebLinks(	 "The Times of Oman", 		 "http://www.timesofoman.com/", 						 "Oman" 	), db);
	addLink(new WebLinks(	 "GulfTimes.com", 		 "http://www.gulftimes.com/", 						 "Qatar" 	), db);
	addLink(new WebLinks(	 "Gulf-Times", 		 "http://www.gulf-times.com/", 						 "Qatar" 	), db);
	addLink(new WebLinks(	 "Al-Jazirah", 		 "http://www.al-jazirah.com/", 						 "Saudi Arabia" 	), db);
	addLink(new WebLinks(	 "Al-Riyadh", 		 "http://www.alriyadh-np.com/", 						 "Saudi Arabia" 	), db);
	addLink(new WebLinks(	 "Arab News", 		 "http://www.arabnews.com/", 						 "Saudi Arabia" 	), db);
	addLink(new WebLinks(	 "Asharq Al-Awsat", 		 "http://www.asharqalawsat.com/", 						 "Saudi Arabia" 	), db);
	addLink(new WebLinks(	 "Saudi Gazette", 		 "http://www.saudigazette.com.sa/", 						 "Saudi Arabia" 	), db);
	addLink(new WebLinks(	 "7 Days", 		 "http://www.7days.ae/", 						 "UAE" 	), db);
	addLink(new WebLinks(	 "Dubai Chronicle", 		 "http://www.dubaichronicle.com/", 						 "UAE" 	), db);
	addLink(new WebLinks(	 "Khaleej Times Online", 		 "http://www.khaleejtimes.com/index00.asp", 						 "UAE" 	), db);
	addLink(new WebLinks(	 "Yemen Observer", 		 "http://www.yobserver.com/", 						 "Yemen" 	), db);
	addLink(new WebLinks(	 "Yemen Post", 		 "http://www.yemenpost.net/", 						 "Yemen" 	), db);
	addLink(new WebLinks(	 "Yemen Times", 		 "http://yementimes.com/index.shtml", 						 "Yemen" 	), db);
	addLink(new WebLinks(	 "Basa Press", 		 "http://www.basa.md/en", 						 "Maldova" 	), db);
	addLink(new WebLinks(	 "Info-Prim Neo", 		 "http://www.info-prim.md/?", 						 "Maldova" 	), db);
	addLink(new WebLinks(	 "Infotag", 		 "http://www.infotag.md/en/", 						 "Maldova" 	), db);
	addLink(new WebLinks(	 "Interlic", 		 "http://en.interlic.md/", 						 "Maldova" 	), db);
	addLink(new WebLinks(	 "Moldova Azi", 		 "http://www.azi.md/en", 						 "Maldova" 	), db);
	addLink(new WebLinks(	 "Moldova.org", 		 "http://www.moldova.org/", 						 "Maldova" 	), db);
	addLink(new WebLinks(	 "Moldpres", 		 "http://www.moldpres.md/default.aspx", 						 "Maldova" 	), db);
	addLink(new WebLinks(	 "Algemeen Dagblad", 		 "http://www.ad.nl/", 						 "Netherlands" 	), db);
	addLink(new WebLinks(	 "Cobouw", 		 "http://www.cobouw.nl/", 						 "Netherlands" 	), db);
	addLink(new WebLinks(	 "Dagblad De Limburger", 		 "http://www.limburger.nl/", 						 "Netherlands" 	), db);
	addLink(new WebLinks(	 "Het Financieele Dagblad", 		 "http://www.fd.nl/home/", 						 "Netherlands" 	), db);
	addLink(new WebLinks(	 "Eindhovens Dagblad", 		 "http://www.ed.nl/", 						 "Netherlands" 	), db);
	addLink(new WebLinks(	 "Haagsche Courant", 		 "http://www.haagschecourant.nl/", 						 "Netherlands" 	), db);
	addLink(new WebLinks(	 "Haarlems Dagblad", 		 "http://www.haarlemsdagblad.nl/", 						 "Netherlands" 	), db);
	addLink(new WebLinks(	 "Het Belang van Limburg", 		 "http://www.hbvl.be/", 						 "Netherlands" 	), db);
	addLink(new WebLinks(	 "Het Parool", 		 "http://www.parool.nl/", 						 "Netherlands" 	), db);
	addLink(new WebLinks(	 "NRC Handelsblad", 		 "http://www.nrc.nl/", 						 "Netherlands" 	), db);
	addLink(new WebLinks(	 "Rotterdams Dagblad", 		 "http://www.rotterdamsdagblad.nl/", 						 "Netherlands" 	), db);
	addLink(new WebLinks(	 "Tctubantia", 		 "http://www.tctubantia.nl/", 						 "Netherlands" 	), db);
	addLink(new WebLinks(	 "Trouw", 		 "http://www.trouw.nl/", 						 "Netherlands" 	), db);
	addLink(new WebLinks(	 "De Volkskrant", 		 "http://www.volkskrant.nl/", 						 "Netherlands" 	), db);
	addLink(new WebLinks(	 "Adressa.no", 		 "http://www.adressa.no/", 						 "Norway" 	), db);
	addLink(new WebLinks(	 "Aftenposten", 		 "http://www.aftenposten.no/", 						 "Norway" 	), db);
	addLink(new WebLinks(	 "Agderposten", 		 "http://www.agderposten.no/", 						 "Norway" 	), db);
	addLink(new WebLinks(	 "An.no", 		 "http://www.an.no/", 						 "Norway" 	), db);
	addLink(new WebLinks(	 "Avisa Sor-Trondelag", 		 "http://www.avisa-st.no/", 						 "Norway" 	), db);
	addLink(new WebLinks(	 "Bt.no", 		 "http://www.bt.no/", 						 "Norway" 	), db);
	addLink(new WebLinks(	 "Bergensavisen", 		 "http://www.ba.no/", 						 "Norway" 	), db);
	addLink(new WebLinks(	 "Dag og Tid", 		 "http://www.dagogtid.no/", 						 "Norway" 	), db);
	addLink(new WebLinks(	 "Dagbladet", 		 "http://www.dagbladet.no/", 						 "Norway" 	), db);
	addLink(new WebLinks(	 "Dagens Naringsliv", 		 "http://www.dn.no/", 						 "Norway" 	), db);
	addLink(new WebLinks(	 "Dagsavisen", 		 "http://www.dagsavisen.no/", 						 "Norway" 	), db);
	addLink(new WebLinks(	 "fvn.no", 		 "http://fvn.no/", 						 "Norway" 	), db);
	addLink(new WebLinks(	 "Firdaposten", 		 "http://www.firdaposten.no/", 						 "Norway" 	), db);
	addLink(new WebLinks(	 "Fredriksstad Blad", 		 "http://www.f-b.no/", 						 "Norway" 	), db);
	addLink(new WebLinks(	 "Fremover", 		 "http://www.fremover.no/", 						 "Norway" 	), db);
	addLink(new WebLinks(	 "gd.no", 		 "http://www.gd.no/", 						 "Norway" 	), db);
	addLink(new WebLinks(	 "Hamar Arbeiderblad", 		 "http://www.hamar-arbeiderblad.no/", 						 "Norway" 	), db);
	addLink(new WebLinks(	 "Hamar Dagblad", 		 "http://www.hamar-dagblad.no/", 						 "Norway" 	), db);
	addLink(new WebLinks(	 "Haugesunds Avis", 		 "http://www.haugesunds-avis.no/", 						 "Norway" 	), db);
	addLink(new WebLinks(	 "Laagendalsposten", 		 "http://www.laagendalsposten.no/", 						 "Norway" 	), db);
	addLink(new WebLinks(	 "Nettavisen", 		 "http://pub.tv2.no/nettavisen/english/", 						 "Norway" 	), db);
	addLink(new WebLinks(	 "Nettavisen", 		 "http://www.nettavisen.no/", 						 "Norway" 	), db);
	addLink(new WebLinks(	 "Nordlys", 		 "http://www.nordlys.no/", 						 "Norway" 	), db);
	addLink(new WebLinks(	 "Norway Post, The", 		 "http://www.norwaypost.no/", 						 "Norway" 	), db);
	addLink(new WebLinks(	 "Norway Today", 		 "http://www.norwaytoday.info/", 						 "Norway" 	), db);
	addLink(new WebLinks(	 "Oppland Arbeiderblad", 		 "http://www.oppland-arbeiderblad.no/", 						 "Norway" 	), db);
	addLink(new WebLinks(	 "Ostlands-Posten", 		 "http://www.ostlands-posten.no/", 						 "Norway" 	), db);
	addLink(new WebLinks(	 "Porsgrunns Dagblad", 		 "http://www.pd.no/", 						 "Norway" 	), db);
	addLink(new WebLinks(	 "Ringerikes Blad", 		 "http://www.rb.no/", 						 "Norway" 	), db);
	addLink(new WebLinks(	 "Rogalands Avis", 		 "http://www.rogavis.no/", 						 "Norway" 	), db);
	addLink(new WebLinks(	 "Sandefjords Blad", 		 "http://www.sandefjords-blad.no/", 						 "Norway" 	), db);
	addLink(new WebLinks(	 "Sunnmorsposten", 		 "http://www.smp.no/", 						 "Norway" 	), db);
	addLink(new WebLinks(	 "Teknisk Ukeblad", 		 "http://www.tekblad.no/", 						 "Norway" 	), db);
	addLink(new WebLinks(	 "Telemarksavisa", 		 "http://www.ta.no/", 						 "Norway" 	), db);
	addLink(new WebLinks(	 "Tonsbergs Blad", 		 "http://www.tonsbergs-blad.no/", 						 "Norway" 	), db);
	addLink(new WebLinks(	 "Tronder-Avisa", 		 "http://www.tronderavisa.no/", 						 "Norway" 	), db);
	addLink(new WebLinks(	 "Verdens Gang", 		 "http://www.vg.no/", 						 "Norway" 	), db);
	addLink(new WebLinks(	 "Dziennik Polski", 		 "http://www.dziennik.krakow.pl/", 						 "Poland" 	), db);
	addLink(new WebLinks(	 "Gazeta Wyborcza", 		 "http://www.gazeta.pl/", 						 "Poland" 	), db);
	addLink(new WebLinks(	 "Krakow Post", 		 "http://www.krakowpost.com/", 						 "Poland" 	), db);
	addLink(new WebLinks(	 "Rzeczpospolita", 		 "http://www.rzeczpospolita.pl/", 						 "Poland" 	), db);
	addLink(new WebLinks(	 "Tygodnik Powszechny", 		 "http://www.tygodnik.com.pl/", 						 "Poland" 	), db);
	addLink(new WebLinks(	 "Trybuna", 		 "http://www.trybuna.com.pl/", 						 "Poland" 	), db);
	addLink(new WebLinks(	 "Warsaw Voice, The", 		 "http://www.warsawvoice.com.pl/", 						 "Poland" 	), db);
	addLink(new WebLinks(	 "Zycie Warszawy", 		 "http://www.zw.com.pl/temat/1.html", 						 "Poland" 	), db);
	addLink(new WebLinks(	 "A Bola", 		 "http://www.abola.pt/", 						 "Portugal" 	), db);
	addLink(new WebLinks(	 "Correio da Manha", 		 "http://www.correiomanha.pt/", 						 "Portugal" 	), db);
	addLink(new WebLinks(	 "Diario de Noticias", 		 "http://www.dnoticias.pt/", 						 "Portugal" 	), db);
	addLink(new WebLinks(	 "O Jogo", 		 "http://www.ojogo.pt/", 						 "Portugal" 	), db);
	addLink(new WebLinks(	 "Jornal de Negocios", 		 "http://www.jornaldenegocios.pt/", 						 "Portugal" 	), db);
	addLink(new WebLinks(	 "Jornal de Noticias (", 		 "http://jn.sapo.pt/paginainicial/", 						 "Portugal" 	), db);
	addLink(new WebLinks(	 "Publico", 		 "http://www.publico.clix.pt/", 						 "Portugal" 	), db);
	addLink(new WebLinks(	 "Record", 		 "http://www.record.pt/", 						 "Portugal" 	), db);
	addLink(new WebLinks(	 "Adevarul", 		 "http://www.adevarulonline.ro/", 						 "Romania" 	), db);
	addLink(new WebLinks(	 "Cotidianul", 		 "http://www.cotidianul.ro/", 						 "Romania" 	), db);
	addLink(new WebLinks(	 "Evenimentul Zilei Online", 		 "http://www.evenimentulzilei.ro//", 						 "Romania" 	), db);
	addLink(new WebLinks(	 "Nine O'Clock", 		 "http://www.nineoclock.ro/", 						 "Romania" 	), db);
	addLink(new WebLinks(	 "ZIUA", 		 "http://www.ziua.ro/", 						 "Romania" 	), db);
    }
}
