package com.ravi_manasa.kismet;

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
    private static final int DATABASE_VERSION = 45;
 
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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAV);
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
                link.set_name(cursor.getString(1).trim());
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
		
		
    	
    	 addLink(new WebLinks("Aadhavan 2009 DVD HQ Surya","4eIIM2zuc3I","|"), db);
    	 addLink(new WebLinks("ABCD 2005 DVD HQ Shaam","tS6GKmNQSsI","|"), db);
    	 addLink(new WebLinks("Alai 2003 DVD HQ Simbhu","-Ne4HiKpEHY","|"), db);
    	 addLink(new WebLinks("Ambasamuthiram Ambani 2010 DVD HQ Karunas","MewB199Fcfg","|"), db);
    	 addLink(new WebLinks("Anbe Un Vasam 2004 DVD HQ Ashwin","FcOB9H6Z9EM","|"), db);
    	 addLink(new WebLinks("Appu 2000 DVD HQ Prasanth","Ct94k4TzxvU","|"), db);
    	 addLink(new WebLinks("April Maadhaathil 2002 DVD HQ Srikanth","uNlsl4-4b8M","|"), db);
    	 addLink(new WebLinks("Arindhum Ariyamalum 2005 Arya","lZiK2K3_wCk","|"), db);
    	 addLink(new WebLinks("Arputha Theevu 2007 DVD Prithviraj","bKMpv-SIiMY","|"), db);
    	 addLink(new WebLinks("Asathal 2001 Satyaraj","uslY8_alWiY","|"), db);
    	 addLink(new WebLinks("Attagasam 2004 DVD HQ Ajith","1_OqqOognjI","|"), db);
    	 addLink(new WebLinks("Autograph 2004 DVD HQ Cheran","RxuPcxwhHRs","|"), db);
    	 addLink(new WebLinks("Ayya 2005 DVD Sarath Kumar","Pisyi1I_LIY","|"), db);
    	 addLink(new WebLinks("Ayyavazhi 2008 Raja","nfJpt4UaTpo","|"), db);
    	 addLink(new WebLinks("Azhagiya Tamil Magan 2007 DVD Vijay","p8T-i-zReNI","|"), db);
    	 addLink(new WebLinks("Azhagu Nilayam 2008 .","R7IYLBYAO9A","|"), db);
    	 addLink(new WebLinks("Bharathi 2000 DVD HQ Shayaji Shindey","YLXJUHOTh9U","|"), db);
    	 addLink(new WebLinks("Billa 2007 Ajith","GTCWjjwQfEA","|"), db);
    	 addLink(new WebLinks("Boss Engira Baskaran 2010 DVD Arya","PVaKA4UI_FQ","|"), db);
    	 addLink(new WebLinks("Chinna Chinna Kannile 2000 DVD HQ Prakash Raj","t0dO2xOQxaU","|"), db);
    	 addLink(new WebLinks("Daas 2005 Ravi","NOYNShDe2s4","|"), db);
    	 addLink(new WebLinks("Dasavatharam 2008 DVD HQ Kamal Hassan","qL39dir5I0M","|"), db);
    	 addLink(new WebLinks("Dhavamai Dhavamirunthu 2007 Raj Kiran","i-i4_aDghho","|"), db);
    	 addLink(new WebLinks("Eazhaiyin Sirippil 2000 DVD HQ Prabhu Deva","CEXnFieLQY4","|"), db);
    	 addLink(new WebLinks("Eera Nilam 2003 Manoj K Bharathi","ULQmqEfQ9Yg","|"), db);
    	 addLink(new WebLinks("Eeram 2009 DVD Nanda","G4QJMlE720s","|"), db);
    	 addLink(new WebLinks("Ethiree 2004 DVD HQ Madhavan","lGzrCjQ1cBU","|"), db);
    	 addLink(new WebLinks("Five Star 2002 DVD HQ Prasanna","R894PAUYzvU","|"), db);
    	 addLink(new WebLinks("Gaja 2009 V N R","rJrvDstINlk","|"), db);
    	 addLink(new WebLinks("Gajendra 2004 DVD HQ Vijaykanth","FvnB8KnxWVY","|"), db);
    	 addLink(new WebLinks("Gomathi Nayagam 2004 DVD HQ Ponvannan","VKZcg8z7Rwc","|"), db);
    	 addLink(new WebLinks("Hello Mama 2001 DVD HQ Nagarjuna","-JA_QIDfKOs","|"), db);
    	 addLink(new WebLinks("Idhaya Thirudan 2005 DVD HQ Ravi","X5rFGuyvkj8","|"), db);
    	 addLink(new WebLinks("Imsai Arasan 23am Pulikesi 2006 DVD HQ Vadivelu","YW1zBYYCNq0","|"), db);
    	 addLink(new WebLinks("Inidhu Inidhu 2010 DVD Narayan","GDNn0Hu-zeM","|"), db);
    	 addLink(new WebLinks("Jayam 2003 DVD HQ Ravi","UmD0nhl4OTM","|"), db);
    	 addLink(new WebLinks("Jilla 2008 Ravi Teja","_gycpesfR8w","|"), db);
    	 addLink(new WebLinks("Kaadhal Kalatta 2002 DVD HQ Venkatesan","F8-zepmGerU","|"), db);
    	 addLink(new WebLinks("Kaaka Kaaka 2003 DVD Surya","ovj-5iM9wMY","|"), db);
    	 addLink(new WebLinks("Kadhal Rojave 2000 DVD George Vishnu","kYcay1I6G5E","|"), db);
    	 addLink(new WebLinks("Kalavaani 2010 DVD HQ Vimal","movv-k3Nd38","|"), db);
    	 addLink(new WebLinks("Kandukondain Kandukondain 2000 DVD Ajith","GuU_wy1csRA","|"), db);
    	 addLink(new WebLinks("Kannan Varuvaan 2000 DVD HQ Karthik","detCg8bDy8Y","|"), db);
    	 addLink(new WebLinks("Karpanai 2005 DVD Harish Ragavendra","aq1hZSj2Q9o","|"), db);
    	 addLink(new WebLinks("Kasu Irukkanum 2007 DVD HQ R Vishwa Kumar","-Z75EsmvQS4","|"), db);
    	 addLink(new WebLinks("Katrathu Kalavu 2010 DVD Krrishna","8vO0cme2fn0","|"), db);
    	 addLink(new WebLinks("Kattrullavarai 2005 DVD HQ Akash","3riivoKgFZ4","|"), db);
    	 addLink(new WebLinks("Kicha Vayasu 16 2005 DVD Simran","yiMJ8oozUts","|"), db);
    	 addLink(new WebLinks("Krishna Krishnaa 2001 DVD SV Sekhar","66Mr1YfM6PE","|"), db);
    	 addLink(new WebLinks("Kundakka Mandakka 2005 DVD HQ Parthiban","Eb9a98YBIeI","|"), db);
    	 addLink(new WebLinks("Kurukshethram 2006 DVD HQ Satyaraj","lwL0TAm03uk","|"), db);
    	 addLink(new WebLinks("Kuselan 2008 Rajnikanth","XsaLQb_edaQ","|"), db);
    	 addLink(new WebLinks("Kutty 2001 DVD Ramesh Aravind","9eMsqeGxlsI","|"), db);
    	 addLink(new WebLinks("Little John 2001 DVD Bentley Mitchum","RaxpuNJPYV0","|"), db);
    	 addLink(new WebLinks("Love Channel 2001 DVD HQ Eswar","H5N9zVGJ-Eo","|"), db);
    	 addLink(new WebLinks("Madurey 2004 Vijay","ANuwv96r5OY","|"), db);
    	 addLink(new WebLinks("Maikavalan 2007 DVD Suresh Gopi","52d2mTOuxo8","|"), db);
    	 addLink(new WebLinks("Maina 2010 Vidarth","HsKnHHFkKhc","|"), db);
    	 addLink(new WebLinks("Makkal Thilagam 2000 DVD HQ Suresh Gopi","Jxe6crY6Tqs","|"), db);
    	 addLink(new WebLinks("Malai Malai 2009 Arun Vijay","5QgmARpyhMM","|"), db);
    	 addLink(new WebLinks("Manmadhan 2004 DVD HQ Silambarasan","9xMojcb3ThM","|"), db);
    	 addLink(new WebLinks("Mayaavi 2005 DVD HQ Surya","erZI5kbKo40","|"), db);
    	 addLink(new WebLinks("Middle Class Madhavan 2001 DVD Prabhu","t1ZC0vRw_i4","|"), db);
    	 addLink(new WebLinks("Milaga 2010 Nataraj","yVH8_1GMz1M","|"), db);
    	 addLink(new WebLinks("Mugavaree 2000 Ajith","umk6W7lUYy8","|"), db);
    	 addLink(new WebLinks("Mumbai Express 2005 DVD Kamal Hassan","xMmJ7ypPX-o","|"), db);
    	 addLink(new WebLinks("Nagamma 2005 Prema","LFR9qQRkZa4","|"), db);
    	 addLink(new WebLinks("Nam Naadu 2007 DVD Sarath Kumar","XLCM7xxmaGc","|"), db);
    	 addLink(new WebLinks("Nandha 2001 DVD HQ Surya Sivakumar","Nq16ro_YaSA","|"), db);
    	 addLink(new WebLinks("Nenjirukkum Varai 2006 DVD Narein","BOkthfxUQCU","|"), db);
    	 addLink(new WebLinks("New 2004 DVD Surya","7tAslECcYmc","|"), db);
    	 addLink(new WebLinks("Nilaa Kalam 2001 DVD Prabhu Deva","6hNNNgXtLUE","|"), db);
    	 addLink(new WebLinks("Ninaithalae 2007 Suchin","HHrUzxmkf00","|"), db);
    	 addLink(new WebLinks("Odipolama 2009 DVD HQ Parimal","PUyyOny3s7M","|"), db);
    	 addLink(new WebLinks("Ottran 2002 DVD Arjun","KOHWDHmNy9g","|"), db);
    	 addLink(new WebLinks("Paarthale Paravasam 2001 DVD HQ Madhavan","Em2e1Pa8wIk","|"), db);
    	 addLink(new WebLinks("Paiya 2010 Karthik Sivakumar","lQIEN0n9-M8","|"), db);
    	 addLink(new WebLinks("Palayathu Amman 2000 DVD Ramki","nbyYuGnjhmA","|"), db);
    	 addLink(new WebLinks("Pandavar Bhoomi 2001 DVD Arun Kumar","coFtOtZwu1I","|"), db);
    	 addLink(new WebLinks("Pasanga 2009 DVD HQ Jeeva","r4aOXYBlwVg","|"), db);
    	 addLink(new WebLinks("Pattalathan 2006 DVD HQ CP Yogeswar","0tzMC4QHqTM","|"), db);
    	 addLink(new WebLinks("Pattiyal 2006 DVD HQ Arya","q0iOUYuN5qo","|"), db);
    	 addLink(new WebLinks("Pokkisham 2009 DVD Cheran","oy9jbOLF_O8","|"), db);
    	 addLink(new WebLinks("Pudhu Pettai 2006 Dhanush","xzv6I_zE0O8","|"), db);
    	 addLink(new WebLinks("Raavanan 2010 DVD HQ VikRam","vPw2-O7B84g","|"), db);
    	 addLink(new WebLinks("Rajakali Amaan 2000 DVD HQ Karan","Lo1neHrdTzY","|"), db);
    	 addLink(new WebLinks("Rajjiyam 2002 Vijaykanth","5X_sKiO1mo4","|"), db);
    	 addLink(new WebLinks("Rhythm 2000 DVD Arjun","daqXIUQYWQs","|"), db);
    	 addLink(new WebLinks("Saamy 2003 DVD HQ VikRam","-l1RoC7KMEY","|"), db);
    	 addLink(new WebLinks("Sadhu Miranda 2008 Prasanna","tHSKMDvfIhI","|"), db);
    	 addLink(new WebLinks("Silambattam 2008 Silambarasan","-Nr5-zhbt-4","|"), db);
    	 addLink(new WebLinks("Sillunnu Oru Kaadal 2006 DVD HQ Surya","rdCz2LHlay0","|"), db);
    	 addLink(new WebLinks("Sindhu Samaveli 2010 DVD HQ Harish Kalyan","rzI_cJVzsAg","|"), db);
    	 addLink(new WebLinks("Sirithaal Rasippen 2009 DVD Sathya","n9VW6syjwuA","|"), db);
    	 addLink(new WebLinks("Sivaji 2007 DVD HQ Rajnikanth","dAnV2D8UNcE","|"), db);
    	 addLink(new WebLinks("Solla Marandha Kadhai 2002 Cheran","5hMFC0xE96w","|"), db);
    	 addLink(new WebLinks("Sri Raja Rajeswari 2001 DVD HQ Ramki","R0SeNcU--tw","|"), db);
    	 addLink(new WebLinks("Sukran 2005 DVD Vijay","5WTJ9KZJdh8","|"), db);
    	 addLink(new WebLinks("Thavasi 2001 DVD Vijaykanth","xLGcPwTdyaY","|"), db);
    	 addLink(new WebLinks("Annan Kaattiya Vazhi 1991 DVD Ramarajan","3-Y2DgM6nqU","|"), db);
    	 addLink(new WebLinks("Arangetra Velai 1990 DVD HQ Prabhu","0WXhU0-Yixg","|"), db);
    	 addLink(new WebLinks("Arunachalam 1997 DVD Rajnikanth","1Q5Jy-_P26I","|"), db);
    	 addLink(new WebLinks("Aval varuvala 1998 DVD HQ Ajith","MaF3cMUsrY0","|"), db);
    	 addLink(new WebLinks("Avasara Police 100 1990 DVD K BhagyaRaja","GcSNQxn2Dxc","|"), db);
    	 addLink(new WebLinks("Avatharam 1995 DVD HQ Nassar","m9SPymcqnXU","|"), db);
    	 addLink(new WebLinks("Avvai Shanmugi 1996 DVD HQ Kamal Hassan","_Y39ebfceCo","|"), db);
    	 addLink(new WebLinks("Azhakan 1991 DVD HQ Mammootty","sVOZrhhTXQk","|"), db);
    	 addLink(new WebLinks("Band Master 1993 DVD Sarath Kumar","c7LW8TvGUq4","|"), db);
    	 addLink(new WebLinks("Bharathi Kannamma 1997 DVD HQ Vijayakumar","TaayiBxIAAQ","|"), db);
    	 addLink(new WebLinks("Brahmachari 1992 DVD HQ Nizhalgal Ravi","of-AvPMehb0","|"), db);
    	 addLink(new WebLinks("Captain Prabhakaran 1991 Vijaykanth","utB6SyrDKQw","|"), db);
    	 addLink(new WebLinks("Chandralekha 1995 DVD HQ Vijay","wElgrdsxzyU","|"), db);
    	 addLink(new WebLinks("Chellakannu 1995 DVD HQ Vignesh","yorOn3-_T4c","|"), db);
    	 addLink(new WebLinks("Chembaruthi 1992 DVD HQ Prashanth","5GjOz1itSdU","|"), db);
    	 addLink(new WebLinks("Cheran Pandian 1991 DVD Vijayakumar","gDPYDNbQPwU","|"), db);
    	 addLink(new WebLinks("Chinna Kannamma 1993 DVD HQ Karthik","3CzAw9OmXHA","|"), db);
    	 addLink(new WebLinks("Chinna Pasanga Naanga 1992 DVD HQ Murali","9iKuoxOLKS8","|"), db);
    	 addLink(new WebLinks("Chinna Thaai 1992 DVD Vignesh","JDSzDHXOeCg","|"), db);
    	 addLink(new WebLinks("Chinna Thambi 1991 DVD HQ Prabhu","9J_hfLFjzJg","|"), db);
    	 addLink(new WebLinks("Chinna Vathiyar 1995 Prabhu","OHbQpe8tvOs","|"), db);
    	 addLink(new WebLinks("Commissioner 1996 DVD HQ Suresh Gopi","3p_XdKTUW2Q","|"), db);
    	 addLink(new WebLinks("Coyamuthur Maaplaey 1996 DVD Vijay","7x0fKxJeInI","|"), db);
    	 addLink(new WebLinks("Deva 1995 DVD HQ Vijay","AmZgcZO6u_g","|"), db);
    	 addLink(new WebLinks("Devaragam 1999 DVD HQ Arvind Swamy","XBnd83ojCdM","|"), db);
    	 addLink(new WebLinks("Devi 1999 DVD HQ Prema","XDU-bIhy5gU","|"), db);
    	 addLink(new WebLinks("Dharma Prabhu 1992 DVD HQ Chiranjeevi","hEIRB145uHI","|"), db);
    	 addLink(new WebLinks("Dharma Seelan 1993 DVD HQ Prabhu","aaDoQb-o2Rw","|"), db);
    	 addLink(new WebLinks("Dhuruva Natchathiram 1990 DVD HQ Arjun","FBo4oPGW8as","|"), db);
    	 addLink(new WebLinks("Duet 1994 DVD HQ Kamal Hassan","BVHv3JsrHrg","|"), db);
    	 addLink(new WebLinks("Durgai Viratham 1995 DVD HQ Yamuna","1i7VnfFJiGs","|"), db);
    	 addLink(new WebLinks("En Pondatti Collector 1996 DVD HQ Jagapathi Babu","NwOhykMwMoM","|"), db);
    	 addLink(new WebLinks("Enga Mudhalali 1993 DVD HQ Vijaykanth","uKnSCBljwa0","|"), db);
    	 addLink(new WebLinks("Enga Thambi 1993 Prashanth","lrysAhwgjrw","|"), db);
    	 addLink(new WebLinks("Ennarukil Nee Irunthaal 1991 DVD HQ Guru","76h1v9mdkdw","|"), db);
    	 addLink(new WebLinks("Enrendrum Kaadal 1999 DVD Vijay","kdFl2jUqmYA","|"), db);
    	 addLink(new WebLinks("Ezhai Jaathi 1993 DVD HQ Vijaykanth","lv2jR_b8nJw","|"), db);
    	 addLink(new WebLinks("Ganga Gowri 1997 DVD HQ Arun Kumar","iPddY8zI7Xo","|"), db);
    	 addLink(new WebLinks("Gentleman 1993 DVD HQ Arjun","zer4X3w70BQ","|"), db);
    	 addLink(new WebLinks("Gokulathil Seethai 1996 DVD HQ Karthik","WZB7IE86UBA","|"), db);
    	 addLink(new WebLinks("Gopura Vasalile 1991 DVD HQ Karthik","OPMtPkKBFyM","|"), db);
    	 addLink(new WebLinks("Gunaa 1992 DVD Kamal Hassan","sloSKYnqyZE","|"), db);
    	 addLink(new WebLinks("Idhaya Vaasal 1991 DVD Ramesh Aravind","Wl7gnej6uDM","|"), db);
    	 addLink(new WebLinks("Idhuthanda Sattam 1992 Sarath Kumar","HSjl9Mb1d9g","|"), db);
    	 addLink(new WebLinks("Indian 1996 DVD HQ Kamal Hassan","rERlIiToZCM","|"), db);
    	 addLink(new WebLinks("Indira 1996 DVD HQ Aravinda Samy","-QidqcHgRII","|"), db);
    	 addLink(new WebLinks("Jallikkattu Kalai 1994 DVD HQ Prabhu","rxojQRuCAEs","|"), db);
    	 addLink(new WebLinks("Jathi Malli 1992 DVD HQ Vineeth","Aj9xBuhpLXE","|"), db);
    	 addLink(new WebLinks("Kaaval Nilayam 1991 DVD HQ Sarath Kumar","3LxH_TNga7U","|"), db);
    	 addLink(new WebLinks("Kadhal Mannan 1998 DVD HQ Ajith","cnvD-Zk6FCE","|"), db);
    	 addLink(new WebLinks("Kadhalar Dinam 1999 DVD HQ Kunal","LQb7Y7a9oWU","|"), db);
    	 addLink(new WebLinks("Kalaignan 1993 DVD HQ Kamal Hassan","giYCxy63268","|"), db);
    	 addLink(new WebLinks("Kalloori Vaasal 1996 Prashanth","aXcT2dBrKsw","|"), db);
    	 addLink(new WebLinks("Kanavey Kalayathey 1999 Murali","NEcyQ83Y820","|"), db);
    	 addLink(new WebLinks("Kanmani 1994 DVD Prasanth","bHQrkHDTy3w","|"), db);
    	 addLink(new WebLinks("Kannethirey Thondrinal 1998 DVD Prashanth","U4FYBoF6bqg","|"), db);
    	 addLink(new WebLinks("Kattumarakkaran 1995 Prabhu","f-mQDRz1_NY","|"), db);
    	 addLink(new WebLinks("Kizhakku Vaasal 1990 DVD Karthik","Np21eOQnOaU","|"), db);
    	 addLink(new WebLinks("Kumbakarai Thangaiah 1991 DVD HQ Prabhu.","n8KvwYW7QDY","|"), db);
    	 addLink(new WebLinks("Kurumbukkaran 1991 DVD HQ Murali","iFFm8BAnUk4","|"), db);
    	 addLink(new WebLinks("Kuruthipunal 1996 DVD Kamal Hassan","IHo1ffRulQw","|"), db);
    	 addLink(new WebLinks("Little Soldiers 1998 DVD Baby Kavya","20cMK5QFt1c","|"), db);
    	 addLink(new WebLinks("Love Birds 1997 DVD Prabhu Deva","wgBULwoBvYI","|"), db);
    	 addLink(new WebLinks("Maaman Magal 1995 Satyaraj","tVUd792cpHE","|"), db);
    	 addLink(new WebLinks("Mallu Vaetti Minor 1990 DVD HQ Satyaraj.","JYsHKgMYIlw","|"), db);
    	 addLink(new WebLinks("Manam Virumbudhe Unnai 1998 DVD HQ Prabhu","VI3ytqeKzqE","|"), db);
    	 addLink(new WebLinks("Mannan 1992 DVD HQ Rajnikanth","iP1Zh3UYE7E","|"), db);
    	 addLink(new WebLinks("Mannukku Mariyathai 1995 DVD HQ Vignesh","5B8hJO-bEJ8","|"), db);
    	 addLink(new WebLinks("Marumalarchi 1998 DVD HQ Mammootty","B35Bnq365Pg","|"), db);
    	 addLink(new WebLinks("Meera 1992 DVD HQ VikRam","d6uRu5iaw7I","|"), db);
    	 addLink(new WebLinks("Michael Madana Kamarajan 1991 DVD HQ Kamal Hassan","3qh152xQ8MQ","|"), db);
    	 addLink(new WebLinks("Minor Mappillai 1996 DVD Ajith","Fj-z1y2CavQ","|"), db);
    	 addLink(new WebLinks("Moovendhar 1998 Sarath Kumar","HHuYnrsekQA","|"), db);
    	 addLink(new WebLinks("Murai Maaman 1995 DVD JayaRam","gGWo_G2_HBw","|"), db);
    	 addLink(new WebLinks("Musthaffaa 1996 DVD Nepolean","ThfY5b0WJm8","|"), db);
    	 addLink(new WebLinks("Muthu 1995 DVD HQ Rajnikanth","EtKHeFxV3Y0","|"), db);
    	 addLink(new WebLinks("My Dear Lisa 1997 DVD HQ Brandu Bhakshi","jUFihBtIUIE","|"), db);
    	 addLink(new WebLinks("My Dear Marthandan 1990 DVD HQ Kamal Hassan","Hgw_qb6lqgQ","|"), db);
    	 addLink(new WebLinks("Naalaiya Theerpu 1992 DVD HQ Vijay","2AnaCDISHPs","|"), db);
    	 addLink(new WebLinks("Naalaya Seithi 1992 DVD HQ Prabhu","GI8c6dpRXXw","|"), db);
    	 addLink(new WebLinks("Naan Pesa Ninaipathellam 1993 Anand Babu","DqEoV-fDqFw","|"), db);
    	 addLink(new WebLinks("Nadigan 1990 DVD HQ Satyaraj","YQL07Sn2iMU","|"), db);
    	 addLink(new WebLinks("Nadodi Mannan 1995 DVD Sarath Kumar","QLJzrgI76JA","|"), db);
    	 addLink(new WebLinks("Nadodi Thendral 1992 DVD HQ Karthik","Hyj6whzr18o","|"), db);
    	 addLink(new WebLinks("Naga Bandham 1999 Sai Kumar","T6IOOppLdUU","|"), db);
    	 addLink(new WebLinks("Nallavan 1999 Vijaykanth","MSTXQEVK1Lo","|"), db);
    	 addLink(new WebLinks("Narasimha Nayakar 1993 DVD HQ Mammootty","fRL4K5-IgXw","|"), db);
    	 addLink(new WebLinks("Nattupura Pattu 1995 DVD Siva Kumar","OD2wqRuifAc","|"), db);
    	 addLink(new WebLinks("Nee Paadhi Nan Paadhi 1991 DVD HQ Raghuman","xdeedemBhPg","|"), db);
    	 addLink(new WebLinks("Nee Varuvai Ena 1999 DVD HQ Parthiban","cKsMLYWME3g","|"), db);
    	 addLink(new WebLinks("Nenja Thottu Chollu 1992 Pari","pVXuMqPv6bw","|"), db);
    	 addLink(new WebLinks("Nethaaji 1996 Sarath Kumar","HsNpGNjzHgA","|"), db);
    	 addLink(new WebLinks("Nilavey Mugam Kaattu 1999 DVD HQ Karthik","8nWVpAgeO6g","|"), db);
    	 addLink(new WebLinks("Oru Veedu Iru Vaasal 1990 DVD HQ Ganesh","ym40p1BfftQ","|"), db);
    	 addLink(new WebLinks("Ottapandayam 1993 DVD HQ Soundarya Kumar","k8y3Gx10cJg","|"), db);
    	 addLink(new WebLinks("Panakkaran 1990 DVD HQ Rajnikanth","LRNxd71jKFw","|"), db);
    	 addLink(new WebLinks("Pandian 1992 DVD HQ Rajnikanth","3SAkj5RuIcw","|"), db);
    	 addLink(new WebLinks("Pavithra 1994 DVD HQ Ajith","y6VHDsi4rZ4","|"), db);
    	 addLink(new WebLinks("Pavunnu Pavanuthan 1991 DVD K Bhagyaraja.","rzYQzpiCY1k","|"), db);
    	 addLink(new WebLinks("Periya Idathu Mappilai 1997 DVD HQ JayaRam","XTyBxCxgjWw","|"), db);
    	 addLink(new WebLinks("Periya Maruthu 1994 DVD HQ Vijaykanth.","sz_LquUlBpM","|"), db);
    	 addLink(new WebLinks("Pistha 1997 Karthik","kt2HB6aKLq0","|"), db);
    	 addLink(new WebLinks("Pon Manam 1998 Prabhu","Tt1EhLDywuw","|"), db);
    	 addLink(new WebLinks("Pongalo Pongal 1997 DVD Vignesh","-popewIf5DY","|"), db);
    	 addLink(new WebLinks("Ponnu Veetukkaran 1999 DVD Satyaraj","6_7UNCAt1IY","|"), db);
    	 addLink(new WebLinks("Poomani 1996 Murali","jYGb8min68M","|"), db);
    	 addLink(new WebLinks("Poonthottam 1998 DVD HQ Murali","khf3C8zo99M","|"), db);
    	 addLink(new WebLinks("Poovarasan 1996 Karthik","vm424wJ6Rx8","|"), db);
    	 addLink(new WebLinks("Priyamudan 1998 DVD Vijay","-xTE0j9AUBs","|"), db);
    	 addLink(new WebLinks("Pudhu Padagan 1990 DVD HQ Vijaykanth","dLsxJCpIJmI","|"), db);
    	 addLink(new WebLinks("Pudhuppattu 1990 DVD HQ Ramarajan","wTcUgElmw0o","|"), db);
    	 addLink(new WebLinks("Witness 1995 DVD HQ Raghuvaran","-sNrREmZ0ts","|"), db);
    	 addLink(new WebLinks("Aan Paavam 1985 DVD Janagaraj","RwYQf_v9hHU","|"), db);
    	 addLink(new WebLinks("Aayiram Pookkal Malarattum 1986 DVD HQ Mohan","620zGTkI2zI","|"), db);
    	 addLink(new WebLinks("Achamillai Achamillai 1984 DVD HQ Rajesh","vRXmKhDZNag","|"), db);
    	 addLink(new WebLinks("Adutha Varisu 1983 DVD HQ Rajnikanth","LrbPGZQDuw0","|"), db);
    	 addLink(new WebLinks("Agni Nakshatram 1988 DVD HQ Karthik","2kSOUiqYkxc","|"), db);
    	 addLink(new WebLinks("Agni Sakshi 1982 DVD HQ Rajnikanth","Kh1rIjcuBG8","|"), db);
    	 addLink(new WebLinks("Alaigal Ooivathilai 1981 DVD Karthik","tFjn6KOarKA","|"), db);
    	 addLink(new WebLinks("Ambikai Neril Vanthaal 1984 DVD Mohan","qWouzYHmN90","|"), db);
    	 addLink(new WebLinks("Anand 1987 DVD HQ Prabhu","_a_trCwNNnw","|"), db);
    	 addLink(new WebLinks("Anbe Odi Vaa 1984 DVD HQ Mohan","Cix0spNE38k","|"), db);
    	 addLink(new WebLinks("Anbulla Appa 1987 DVD HQ Raghuman","YEL93JNIrpo","|"), db);
    	 addLink(new WebLinks("Anbulla Rajanikant 1984 DVD Rajnikanth","1zPOQ2z365k","|"), db);
    	 addLink(new WebLinks("Andha Oru Nimidam 1985 DVD HQ Kamal Hassan","oGF7dqSnRpQ","|"), db);
    	 addLink(new WebLinks("Andur Muthal Indru Varai 1981 DVD HQ Shiva Kumar","Z6Cg5ZwOaOI","|"), db);
    	 addLink(new WebLinks("Anjatha Singam 1987 DVD HQ Prabhu","sgWPVk-Z4EA","|"), db);
    	 addLink(new WebLinks("Anney Anney 1983 DVD HQ Mouli","slnsT7JK7ZY","|"), db);
    	 addLink(new WebLinks("Apoorva Sahodarargal 1989 Kamal Hassan","PLQl4wfgXVo","|"), db);
    	 addLink(new WebLinks("Aruvadai Naal 1986 DVD HQ Prabhu","2phJEE-EDOs","|"), db);
    	 addLink(new WebLinks("Aval Sumangalithan 1985 DVD Karthik","NmVAPLTvenY","|"), db);
    	 addLink(new WebLinks("Bala Nagamma 1981 DVD HQ Sarath Babu","ry0nt9V7fwA","|"), db);
    	 addLink(new WebLinks("Bhama Rukmani 1980 DVD HQ K BhagyaRaja","RU9bZvxAxlk","|"), db);
    	 addLink(new WebLinks("Chain Jaipal 1985 DVD HQ Rajesh","LSLhJJHPA1U","|"), db);
    	 addLink(new WebLinks("Chidambara Rahasyam 1985 DVD HQ Delhi Ganesh","d5VxVjEw224","|"), db);
    	 addLink(new WebLinks("Chinna Kuyil Paaduthu 1987 DVD HQ Siva Kumar","4XFpXw7Pjjs","|"), db);
    	 addLink(new WebLinks("Chinna Thambi Periya Thambi 1987 DVD HQ Prabhu","GzNyKkOsijM","|"), db);
    	 addLink(new WebLinks("Chinna Veedu 1985 DVD HQ Bhagyaraj","v5bmXmLGW0Q","|"), db);
    	 addLink(new WebLinks("Customs Officer 1988 DVD HQ Krishna","x50Hkprl0yU","|"), db);
    	 addLink(new WebLinks("Daiva Piravi 1985 DVD Mohan","zeCYt2XQo_s","|"), db);
    	 addLink(new WebLinks("December Pookkal 1986 DVD HQ Kamal Hassan.","1Y_nS5GEPe4","|"), db);
    	 addLink(new WebLinks("Deiva Thirumanangal 1981 DVD HQ Chandra Mohan","gnGRoNLovsU","|"), db);
    	 addLink(new WebLinks("Dhampatyam 1987 DVD HQ Shivaji Ganesan","-k86OSBEC5Y","|"), db);
    	 addLink(new WebLinks("Dharma Pathni 1986 DVD Karthik","dPk1hxOEDyM","|"), db);
    	 addLink(new WebLinks("Dharmathin Thalaivan 1988 DVD HQ Rajnikanth","WDt5ld8LEcY","|"), db);
    	 addLink(new WebLinks("Dhavani Kannavukal 1983 DVD K BhagyaRaja","Plds8AzpClk","|"), db);
    	 addLink(new WebLinks("En Bommukutty Ammavukku 1988 DVD Satyaraj","SQ5xEWgjI8o","|"), db);
    	 addLink(new WebLinks("En Jeevan Paaduthe 1988 DVD Karthik","qK-s3BKZWpw","|"), db);
    	 addLink(new WebLinks("En Purushan Thaan Enakkum Mattum Thaan 1989 DVD HQ Vijaykanth","lthzYkkIQVw","|"), db);
    	 addLink(new WebLinks("Enakkul Oruvan 1984 DVD HQ Kamal Hassan","v0UEhUXgX0A","|"), db);
    	 addLink(new WebLinks("Enga Chinna Rasa 1987 DVD HQ Bhagyaraj","OXuzqA5ElJc","|"), db);
    	 addLink(new WebLinks("Enga Ooru Paattukkaaran 1987 DVD HQ Ramarajan","ty0Ea8O0Ve8","|"), db);
    	 addLink(new WebLinks("Engal Vaadyar 1980 Nagesh","a8VQ1xTIdwM","|"), db);
    	 addLink(new WebLinks("Engeyo Ketta Kural 1982 DVD HQ Rajnikanth","DVRPT3-PDHY","|"), db);
    	 addLink(new WebLinks("Erattai Manithan 1982 DVD HQ S S Rajendran","KgbyRDoZ1BU","|"), db);
    	 addLink(new WebLinks("Ganam Courtar Avargale 1988 DVD HQ Satyaraj","YV0ReSDdQMI","|"), db);
    	 addLink(new WebLinks("Geethanjali 1985 Murali","-gBhie-C2XI","|"), db);
    	 addLink(new WebLinks("Gopurangal Saayvathillai 1982 DVD HQ Mohan","DfmNZwrKfZc","|"), db);
    	 addLink(new WebLinks("Gun Fight Kanchana 1981 DVD Satyanarayana","jd4jEK7jmRc","|"), db);
    	 addLink(new WebLinks("Guru 1980 DVD HQ Kamal Hassan","u6-cQ2yJPQY","|"), db);
    	 addLink(new WebLinks("Guru Sisyan 1988 DVD HQ Rajnikanth","DrlSe8VZfn4","|"), db);
    	 addLink(new WebLinks("Idaya Kovil 1985 DVD HQ Mohan","cCmB6L8wYnQ","|"), db);
    	 addLink(new WebLinks("Ilamai Kaalangal 1983 DVD HQ Mohan","LS7Pz64bUiQ","|"), db);
    	 addLink(new WebLinks("Ilangeswaran 1987 DVD HQ K R Vijaya","EuzRhsEw2Q8","|"), db);
    	 addLink(new WebLinks("Indran Chandran 1989 DVD Kamal Hassan","MhU-O2Nlp3k","|"), db);
    	 addLink(new WebLinks("Jallikkattu 1987 DVD HQ Shivaji Ganesan","hzSc1O1iJRY","|"), db);
    	 addLink(new WebLinks("Jappanil Kalyanaraman 1984 DVD HQ Kamal Hassan","g20inwPQSbY","|"), db);
    	 addLink(new WebLinks("Jenma Natchathram 1980 Promod","4NcTBSH3xqk","|"), db);
    	 addLink(new WebLinks("Kaadhal Parisu 1987 DVD HQ Kamal Hassan","Q2Mzabi61XU","|"), db);
    	 addLink(new WebLinks("Kaakki Sattai 1985 DVD HQ Kamal Hassan","tmFQUTRn1dE","|"), db);
    	 addLink(new WebLinks("Kadaikan Parvai 1986 Pandian","_OKETpUh36E","|"), db);
    	 addLink(new WebLinks("Kadolara Kavithaigal 1986 DVD HQ Satyaraj","WWvQLnb5z6c","|"), db);
    	 addLink(new WebLinks("Kai Nattu 1989 DVD Raghuvaran","ojcf-mNknFU","|"), db);
    	 addLink(new WebLinks("Kakki Chattai 1985 DVD Kamal Hassan","tmFQUTRn1dE","|"), db);
    	 addLink(new WebLinks("Kallukkul Eeram 1980 Bharathi Raja","2PyzO7XilBg","|"), db);
    	 addLink(new WebLinks("Kalyana Agathigal 1985 DVD Ashok","X2L5BbWzikA","|"), db);
    	 addLink(new WebLinks("Kanney Raadha 1982 DVD HQ Karthik","VBU9x54lop4","|"), db);
    	 addLink(new WebLinks("Karagaattakkaaran 1989 DVD HQ Ramarajan","wkiEE-5V0t8","|"), db);
    	 addLink(new WebLinks("Katha Nayagan 1986 DVD HQ Pandiarajan","xU4UzZ9V7Hs","|"), db);
    	 addLink(new WebLinks("Kazhagu 1981 Rajnikanth.","kUVwvUdIQ10","|"), db);
    	 addLink(new WebLinks("Kelviyum Naaney Badhilum Naaney 1982 DVD HQ Karthik","Kfka8CN-uEg","|"), db);
    	 addLink(new WebLinks("Koil Yaanai 1986 DVD HQ Pandiyan","u7ZQW-2TyYw","|"), db);
    	 addLink(new WebLinks("Mahasakthi Mariammaa 1986 DVD HQ Rajesh","iVB28PPSRXI","|"), db);
    	 addLink(new WebLinks("Manaivi Solle Mandiram 1983 DVD HQ Mohan","FLLJb7Ts46E","|"), db);
    	 addLink(new WebLinks("Manal Kayiru 1982 DVD HQ Sekar","tUxwXOELtKo","|"), db);
    	 addLink(new WebLinks("Manamagale Vaa 1988 DVD Prabhu","Hiv8t5REaS8","|"), db);
    	 addLink(new WebLinks("Manathil Urudhi Vendum 1987 DVD HQ Chandrakant","HSd15IxQhX0","|"), db);
    	 addLink(new WebLinks("Mangai Oru Gangai 1987 DVD HQ Suresh","B0LkpLwy4TU","|"), db);
    	 addLink(new WebLinks("Mangamma Sabatham 1985 DVD Kamal Hassan","soBh-GEmAjY","|"), db);
    	 addLink(new WebLinks("Manjal Nila 1982 DVD Suresh","OU48iUv-fws","|"), db);
    	 addLink(new WebLinks("Mannukkul Vairam 1986 DVD HQ Shivaji Ganesan","ab8aJbMukAw","|"), db);
    	 addLink(new WebLinks("Meenakshi Thiruvilayadal 1989 DVD Vijaykanth","cds1xNtfZYI","|"), db);
    	 addLink(new WebLinks("Meendum Pallavi 1986 DVD HQ Jai Shankar","QX88iSzLZ3A","|"), db);
    	 addLink(new WebLinks("Melmaruvathur Adhiparasakthi 1985 DVD HQ Rajesh","jGi3uFT1mVw","|"), db);
    	 addLink(new WebLinks("Melmaruvathur Arputhangal 1986 DVD HQ Rajesh","rICOSgqCW2M","|"), db);
    	 addLink(new WebLinks("Michael Raaj 1987 DVD HQ Raguvaran","Oj7mik57vSQ","|"), db);
    	 addLink(new WebLinks("Moondru Mugham 1983 DVD Rajnikanth","nvlQq5OAVKE","|"), db);
    	 addLink(new WebLinks("Mounam Sammadham 1989 DVD HQ Mammootty","F8lIaeiHIxc","|"), db);
    	 addLink(new WebLinks("Naan Mahaan Alla 1984 DVD HQ Rajnikanth","QwhHXjAwmQM","|"), db);
    	 addLink(new WebLinks("Naan Paadum Paadal 1984 DVD HQ Mohan","5FkLnTXNlyM","|"), db);
    	 addLink(new WebLinks("Naane Raja Naane Manthiri 1986 DVD HQ Vijaykanth","0DhUQ_gWyHk","|"), db);
    	 addLink(new WebLinks("Naanum Oru Thozhilaali 1986 DVD Kamal Hassan.","YEUGX8lv2As","|"), db);
    	 addLink(new WebLinks("Nandri Meendum Varuga 1982 DVD HQ Prathap K Pothan","Oqol3ryaK5o","|"), db);
    	 addLink(new WebLinks("Natpu 1986 DVD HQ Karthik.","GB20X3NCFt0","|"), db);
    	 addLink(new WebLinks("Nayakan 1987 DVD HQ Kamal Hassan","UaSPHeBUE-k","|"), db);
    	 addLink(new WebLinks("Neengal Ketavai 1984 DVD HQ Thyagarajan","aW0LFJuRxz4","|"), db);
    	 addLink(new WebLinks("Nenjanggal 1982 DVD HQ Shivaji Ganesan","cjEXdS8orjk","|"), db);
    	 addLink(new WebLinks("Netri Kann 1981 DVD HQ Rajnikanth","4o1w4HyATzU","|"), db);
    	 addLink(new WebLinks("Ninaivellam Nitya 1982 DVD HQ Karthik.","x37QLpiOWnY","|"), db);
    	 addLink(new WebLinks("Ninaivu Chinnam 1989 DVD HQ Prabhu","z1GQlnLTlhw","|"), db);
    	 addLink(new WebLinks("Oomai Vizhigal 1986 DVD HQ Vijaykanth","DZ5KR4TWv0c","|"), db);
    	 addLink(new WebLinks("Oru Indhiya Kanavu 1983 DVD HQ Rajesh","ag_YsUwnx6Q","|"), db);
    	 addLink(new WebLinks("Oru Kai Oosai 1980 DVD HQ Bhagyaraj","aiebQt7ST94","|"), db);
    	 addLink(new WebLinks("Oru Kaidhiyin Diary 1984 DVD HQ Kamal Hassan","h9Dn6uvsDd8","|"), db);
    	 addLink(new WebLinks("Paadu Nilave 1987 Mohan","3MODuv6dKfw","|"), db);
    	 addLink(new WebLinks("Paarvayin Marupakkam 1982 DVD HQ Vijaykanth","ALX3YsRVQsw","|"), db);
    	 addLink(new WebLinks("Padikkatha Pannaiyar 1985 DVD HQ Shivaji Ganesan","m_fCSVa1W-M","|"), db);
    	 addLink(new WebLinks("Palaivana Solai 1981 DVD HQ Chandrasekhar","RyiAEtAE5uM","|"), db);
    	 addLink(new WebLinks("Payanangal Mudivathillai 1982 DVD HQ Mohan","Q4dxjCXXy4g","|"), db);
    	 addLink(new WebLinks("Penmani Aval Kanmani 1988 DVD HQ Visu","oHPn5DZdcL4","|"), db);
    	 addLink(new WebLinks("Pillainila 1985 DVD HQ .","6zWo-CUoEvk","|"), db);
    	 addLink(new WebLinks("Poikkal Kuthirai 1983 DVD HQ Kamal Hassan.","pwrR-xkDBvo","|"), db);
    	 addLink(new WebLinks("Pollathavan 1980 DVD HQ Rajnikanth","kE_3G_njZ6c","|"), db);
    	 addLink(new WebLinks("Ponmana Selvan 1989 DVD HQ Vijaykanth.","6gPzXsOkuHM","|"), db);
    	 addLink(new WebLinks("Poo Vilanghu 1984 DVD HQ Murali","Zca9tJB9lUc","|"), db);
    	 addLink(new WebLinks("Pookkalai Parikkatheergal 1986 Suresh","mG2FPXnjo5U","|"), db);
    	 addLink(new WebLinks("Poovizhi Vasalile 1987 DVD Satyaraj","37zv0bqUSxo","|"), db);
    	 addLink(new WebLinks("Pudhiavan 1984 DVD HQ Murali","B0RR4oeNSYU","|"), db);
    	 addLink(new WebLinks("Pudhu Kavithai 1982 DVD HQ Rajnikanth","fKhwfOOKfFA","|"), db);
    	 addLink(new WebLinks("Pudhu Pudhu Arthangal 1989 DVD Rahman","vsBaZgGGimA","|"), db);
    	 addLink(new WebLinks("Punnagai Mannan 1986 DVD HQ Kamal Hassan","ncqZVYUgli8","|"), db);
    	 addLink(new WebLinks("Rail Payanangalil 1981 Srinath","nFnyjjs76tQ","|"), db);
    	 addLink(new WebLinks("Raja Rajathan 1989 DVD HQ Ramarajan","0VqIUbsP64A","|"), db);
    	 addLink(new WebLinks("Raja Veetu Kannu Kutty 1984 Prabhu","nOclyhdeIrQ","|"), db);
    	 addLink(new WebLinks("Rajanadai 1989 Vijaykanth","Fcy2sOZaEuM","|"), db);
    	 addLink(new WebLinks("Rajathi Raja 1989 DVD HQ Rajnikanth","SNFs38SQPGU","|"), db);
    	 addLink(new WebLinks("Rajathi Rojakili 1985 DVD HQ Rajesh","N4pi8YbPYYA","|"), db);
    	 addLink(new WebLinks("Rama Lakshman 1981 DVD Kamal Hassan","D3uQczGphh4","|"), db);
    	 addLink(new WebLinks("Ranga 1982 DVD HQ Rajnikanth","HVu5C7_nLnE","|"), db);
    	 addLink(new WebLinks("Ranuva Veeran 1981 DVD HQ Rajnikanth","mrvMYzMe3gQ","|"), db);
    	 addLink(new WebLinks("Rishi Moolam 1980 DVD HQ Shivaji Ganesan","t22gFcv0fJQ","|"), db);
    	 addLink(new WebLinks("Sahadevan Mahadevan 1988 DVD Mohan","7fgCVfRUoNk","|"), db);
    	 addLink(new WebLinks("Sakala Kalaa Vaandukhal 1989 DVD HQ Senthil","ofxFa2DPunU","|"), db);
    	 addLink(new WebLinks("Salangai Oli 1983 DVD Kamal Hassan","HmEDoj-WoKA","|"), db);
    	 addLink(new WebLinks("Samayapurathale Satchi 1985 DVD Rajesh","GzbiUrGTL_Y","|"), db);
    	 addLink(new WebLinks("Sangili 1982 Prabhu.","GFoEBMHOvx4","|"), db);
    	 addLink(new WebLinks("Sarvam Sakthimayam 1986 DVD HQ Rajesh","G7dHl4HtCW4","|"), db);
    	 addLink(new WebLinks("Sathyam Neaya 1984 DVD HQ Vijaykanth","70n09fpoxJQ","|"), db);
    	 addLink(new WebLinks("Senthoora Poove 1988 DVD HQ Vijaykanth","0rCxaYmjqY8","|"), db);
    	 addLink(new WebLinks("Simla Special 1982 DVD HQ Kamal Hassan","yX82MQdLCYE","|"), db);
    	 addLink(new WebLinks("Sindhu Bhairavi 1985 DVD Siva Kumar","_4U5Ei3haT8","|"), db);
    	 addLink(new WebLinks("Siva 1989 DVD HQ Rajnikanth","rdRFa2dfK_Q","|"), db);
    	 addLink(new WebLinks("Sivappu Sooriyan 1983 DVD Rajnikanth","BgqY83ZTeW4","|"), db);
    	 addLink(new WebLinks("Sri Raghavendra 1985 DVD HQ Rajnikanth","Hro_4yIbXXw","|"), db);
    	 addLink(new WebLinks("Sukradesai 1984 DVD HQ Pandian","sh1v95UxTsI","|"), db);
    	 addLink(new WebLinks("Thaali Dhaanam 1989 DVD HQ Rajesh","WyGVjeuv7Bs","|"), db);
    	 addLink(new WebLinks("Thai Mookambhikai 1982 DVD HQ Karthik","QLwH4wpGY0c","|"), db);
    	 addLink(new WebLinks("Thai Naadu 1989 Satyaraj","PTdkeCDQyCU","|"), db);
    	 addLink(new WebLinks("Thambathigal 1983 DVD HQ Shiva Kumar","3onHABuOOqA","|"), db);
    	 addLink(new WebLinks("Thambikku Entha Ooru 1984 DVD HQ Rajnikanth","p3sUJ1lLPFE","|"), db);
    	 addLink(new WebLinks("Thanga Magan 1983 DVD HQ Rajnikanth","a2386OjSyjA","|"), db);
    	 addLink(new WebLinks("Thanikatu Raja 1982 DVD HQ Rajnikanth","rjqXnB59T-8","|"), db);
    	 addLink(new WebLinks("Tharasu 1984 DVD HQ Shivaji Ganesan","Cg6pD5fzhVQ","|"), db);
    	 addLink(new WebLinks("Thendraley Ennai Thodu 1985 DVD HQ Mohan","5QzrFI61NjA","|"), db);
    	 addLink(new WebLinks("Thillu Mullu 1981 DVD HQ Rajnikanth","JShmvAJ3j1Q","|"), db);
    	 addLink(new WebLinks("Thiramai 1985 DVD HQ Satyaraj","pZehN-939-s","|"), db);
    	 addLink(new WebLinks("Thirumathi Oru Vegumathi 1987 DVD HQ S V Sekar","NQmM9GVbIUA","|"), db);
    	 addLink(new WebLinks("Thooral Ninnu Pochhu 1982 DVD HQ K BhagyaRaja","g6TJpNNpj6E","|"), db);
    	 addLink(new WebLinks("Thudikkum Karangal 1983 DVD HQ Rajnikanth","pBDQFZQZef8","|"), db);
    	 addLink(new WebLinks("Thulasi 1987 DVD HQ Murali","nmnY4ZNeSos","|"), db);



    	
    	
    	
    	
    	
    }
}
