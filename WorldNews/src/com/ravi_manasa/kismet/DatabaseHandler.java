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
    private static final int DATABASE_VERSION = 48;
 
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
		
		
    	
    	 addLink(new WebLinks("Oohalu Gusagusalade 2014 DVD HQ Naga Shourya","IJ_5VNr8mDE","|"), db);
    	 addLink(new WebLinks("Dilunnodu 2014 DVD HQ Sairam Shankar","lHL6Yj9HQ-g","|"), db);
    	 addLink(new WebLinks("Rowdy 2014 DVD HQ Mohan Babu","uljeOc8AkM0","|"), db);
    	 addLink(new WebLinks("Hrudaya Kaleyam 2014 DVD HQ Sampoornesh Babu","g-Xqs0x4ShA","|"), db);
    	 addLink(new WebLinks("Paisa 2014 DVD Nani","LRdW910JmD4","|"), db);
    	 addLink(new WebLinks("Malligadu Marriage Bureau 2014 DVD HQ Srikanth","z_SlZjibCy8","|"), db);
    	 addLink(new WebLinks("Malligadu Marriage Bureau 2014 DVD Srikanth","z_SlZjibCy8","|"), db);
    	 addLink(new WebLinks("Pandavulu Pandavulu Tummeda 2014 DVD HQ Mohan Babu","8lqh754GybU","|"), db);
    	 addLink(new WebLinks("Love You Bangaram 2014 DVD HQ Rahul","p-MudyhJPLk","|"), db);
    	 addLink(new WebLinks("Yevadu 2014 DVD HQ Ram Charan","fob0ZaRHapA","|"), db);
    	 addLink(new WebLinks("Kshatriya 2014 DVD HQ Srikanth","BNJ_OHLXfy4","|"), db);
    	 addLink(new WebLinks("Uyyala Jampala 2013 DVD HQ Raj Tarun","AzSVRKCcHBY","|"), db);
    	 addLink(new WebLinks("Villa 2013 DVD HQ Ashok Selvan","Ei-_KuTs0DI","|"), db);
    	 addLink(new WebLinks("Doosukeltha 2013 DVD HQ Vishnu","YKWFUy4GtNk","|"), db);
    	 addLink(new WebLinks("Ramayya Vastavayya 2013 DVD HQ Junior NTR","gTLhFllz8OA","|"), db);
    	 addLink(new WebLinks("Police Game 2013 DVD HQ Srihari","JRGDVgp60-4","|"), db);
    	 addLink(new WebLinks("Attharintiki Daaredhi 2013 DVD HQ Pavan Kalyan","p9xrgKSt82o","|"), db);
    	 addLink(new WebLinks("Mahesh 2013 DVD HQ Sundeep Kishan","nqxcA55R8jc","|"), db);
    	 addLink(new WebLinks("Break Up 2013 DVD HQ Ranadhir","b2TvTHg0VIk","|"), db);
    	 addLink(new WebLinks("Potugadu 2013 DVD HQ Manchu Manoj Kumar","kiLeK8wSU7Q","|"), db);
    	 addLink(new WebLinks("Adda 2013 DVD HQ Sushant","zF3LmxpGzDE","|"), db);
    	 addLink(new WebLinks("Sri Jagadguru Adi Shankara 2013 DVD HQ Kousik Babu","ELFZ1GUQ07o","|"), db);
    	 addLink(new WebLinks("Dalam 2013 DVD HQ Naveen Chandra","B1y1wQOMfys","|"), db);
    	 addLink(new WebLinks("Anna 2013 DVD HQ Vijay","vSEU1Fqt1E4","|"), db);
    	 addLink(new WebLinks("Romance 2013 DVD HQ Prince","845uKmC_2RI","|"), db);
    	 addLink(new WebLinks("Om 2013 DVD HQ Kalyan Ram","qaoRKS3EkCE","|"), db);
    	 addLink(new WebLinks("Kevvu Keka 2013 DVD HQ Allari Naresh","ndR5WLt4EjA","|"), db);
    	 addLink(new WebLinks("Sahasam 2013 DVD HQ Gopichand","PzEQMcg2u5k","|"), db);
    	 addLink(new WebLinks("Singam 2013 DVD HQ Surya","pU_MIfciKFE","|"), db);
    	 addLink(new WebLinks("Prema Katha Chithram 2013 DVD HQ Sudhir Babu","_rOj_rMcGNg","|"), db);
    	 addLink(new WebLinks("Gurudu 2013 DVD HQ Shivaji","1zpiOsHg8oU","|"), db);
    	 addLink(new WebLinks("Iddarammayilatho 2013 DVD HQ Allu Arjun","Rv3DLPFbQgA","|"), db);
    	 addLink(new WebLinks("Love Cycle 2013 DVD Sree","PGKx8aOU_X8","|"), db);
    	 addLink(new WebLinks("Shadow 2013 DVD Venkatesh","UQowQS63azg","|"), db);
    	 addLink(new WebLinks("NH 4 2013 DVD HQ Siddharth","75mHJ4pkAFc","|"), db);
    	 addLink(new WebLinks("Gunde Jaari Gallanthayyinde 2013 DVD HQ Nitin","49VD5OC6bL4","|"), db);
    	 addLink(new WebLinks("Chinna Cinema 2013 DVD HQ Arjun Kalyan","jgH_TbLNKW0","|"), db);
    	 addLink(new WebLinks("Crazy 2013 DVD HQ Arya","LvDzy7OsDlg","|"), db);
    	 addLink(new WebLinks("Baadshah 2013 DVD HQ Junior NTR","mVJQ8nAnTsw","|"), db);
    	 addLink(new WebLinks("Jaffa 2013 DVD HQ Brahmanandam","8QOP8WWoVf4","|"), db);
    	 addLink(new WebLinks("Aravind 2 2013 DVD HQ Mangam Srinivas","9prx9br3VRg","|"), db);
    	 addLink(new WebLinks("100 Kotlu 2008 DVD Baladitya","Qxq31_26ni4","|"), db);
    	 addLink(new WebLinks("12 B 2001 DVD HQ Shaam","epVB7xp2cNE","|"), db);
    	 addLink(new WebLinks("123 from Amalapuram 2005 DVD Ravi Prakash","rKpsGKIux-8","|"), db);
    	 addLink(new WebLinks("13 B 2009 DVD HQ Madhavan","Mzm9hZNu3wU","|"), db);
    	 addLink(new WebLinks("143 2004 DVD HQ Sairam Shankar","MR-Dig2Tf84","|"), db);
    	 addLink(new WebLinks("16 Days 2009 DVD HQ Charmi","4aeXfQ9da28","|"), db);
    	 addLink(new WebLinks("18,20 Love Story 2009 DVD HQ Shivaji","J8eICS55QjI","|"), db);
    	 addLink(new WebLinks("1977 2010 DVD HQ Sarath Kumar","PQEi-gGlNr8","|"), db);
    	 addLink(new WebLinks("2 Much 2002 DVD HQ Bala Kumar","jzk75XHHO3U","|"), db);
    	 addLink(new WebLinks("7G Brindavan Colony 2004 DVD HQ Ravi Krishna","OsAxOHT9UTw","|"), db);
    	 addLink(new WebLinks("A Aa E Ee 2009 DVD HQ Srikanth","-zh-ME-HheU","|"), db);
    	 addLink(new WebLinks("A Film By Aravind 2005 DVD HQ Rajiv Kanakala","ue0Uf9eTkF4","|"), db);
    	 addLink(new WebLinks("Aa Intlo 2009 DVD HQ Chinna","T5q57n_zzlY","|"), db);
    	 addLink(new WebLinks("Aa Okkadu 2009 DVD HQ Ajay","UfnKCnXL7TY","|"), db);
    	 addLink(new WebLinks("Aa Roje 2007 DVD Yashwant","s0yCCV7u_jw","|"), db);
    	 addLink(new WebLinks("Aadavari Matalaku Ardhalu Verule 2007 DVD HQ Venkatesh","YYHVhcapx1g","|"), db);
    	 addLink(new WebLinks("Aadhi Lakshmi 2006 DVD HQ Srikanth","wnO_hK7QOEE","|"), db);
    	 addLink(new WebLinks("Aaduthu Paaduthu 2002 DVD HQ Srikanth","9oKSV0zr0Xg","|"), db);
    	 addLink(new WebLinks("Aah Roju 2005 DVD Vishwam","uJtbtGD22iI","|"), db);
    	 addLink(new WebLinks("Aahuthi 2002 DVD HQ Chandrababu","2rapsM1jwZA","|"), db);
    	 addLink(new WebLinks("Aakasa Ramanna 2010 DVD Allari Naresh","MqsG_yAWdiE","|"), db);
    	 addLink(new WebLinks("Aakasamantha 2009 DVD HQ Prakash Raj","9A96HXvHk9c","|"), db);
    	 addLink(new WebLinks("Aalayam 2008 DVD HQ Shivaji","DJxCSUSf3Mc","|"), db);
    	 addLink(new WebLinks("Aapadamokkulavaadu 2008 DVD HQ Nagendra Babu","pkzKIJN-i4M","|"), db);
    	 addLink(new WebLinks("Aaru 2005 DVD HQ Surya","pNL-J1ke7XQ","|"), db);
    	 addLink(new WebLinks("Aarya 2004 Allu Arjun","BVFD4IXuLHg","|"), db);
    	 addLink(new WebLinks("Aarya MBBS 2008 DVD Madhavan","Du4CpD_92mk","|"), db);
    	 addLink(new WebLinks("Aata 2007 DVD HQ Siddharth","sclWQGy3sTs","|"), db);
    	 addLink(new WebLinks("Aata Modalaindi 2008 DVD HQ Sanga Kumar","q38n0UqFCc8","|"), db);
    	 addLink(new WebLinks("Aatadistha 2008 DVD HQ Nitin","4sD03-mT250","|"), db);
    	 addLink(new WebLinks("Aavaham 2003 DVD HQ Sudeep","0iHsNU3sUwU","|"), db);
    	 addLink(new WebLinks("Abaddam 2006 DVD HQ Uday Kiran","jR9TJfdy0iU","|"), db);
    	 addLink(new WebLinks("Abbayee Premalo Paddadu 2004 DVD HQ Ramana","DZtNAMG7MP0","|"), db);
    	 addLink(new WebLinks("Abhay 2001 DVD HQ Kamal Hassan","ypLTNHM6TdE","|"), db);
    	 addLink(new WebLinks("Abhi 2000 DVD Mohanlal","f070Yq6Sw5g","|"), db);
    	 addLink(new WebLinks("Abraham & Lincoln 2007 DVD Kalabavan Mani","voMrfp-BV_4","|"), db);
    	 addLink(new WebLinks("Action No. 1 2002 DVD HQ Ram","LfTT6fXaC90","|"), db);
    	 addLink(new WebLinks("Adavi 2009 Nitin","Ppi5b9tjL2c","|"), db);
    	 addLink(new WebLinks("Adavi Chukka 2000 DVD HQ Vijayashanthi","z8FXuLnTEb0","|"), db);
    	 addLink(new WebLinks("Adavi Ramudu 2004 DVD HQ Prabhas","6MkNIApwx_A","|"), db);
    	 addLink(new WebLinks("Adhinetha 2009 DVD HQ Jagapathi Babu","kxR3cmmooBA","|"), db);
    	 addLink(new WebLinks("Adhipati 2001 Mohan Babu","CSRSLNVvcLM","|"), db);
    	 addLink(new WebLinks("Adi Nuvve 2010 DVD HQ Chaitanya","qk0Y9NNfFTs","|"), db);
    	 addLink(new WebLinks("Adirindayya Chandram 2005 DVD Shivaji","HtQo7ko9A9Q","|"), db);
    	 addLink(new WebLinks("Adivaram Adavallaku Selavu 2007 DVD HQ Shivaji","GmYXZ9-HugA","|"), db);
    	 addLink(new WebLinks("Adrustam 2002 DVD HQ Tarun","ms8RjSQGr8Q","|"), db);
    	 addLink(new WebLinks("Adurs 2010 DVD HQ Junior NTR","1dgczT_QmKs","|"), db);
    	 addLink(new WebLinks("Alasyam Amrutham 2010 DVD Nikhil","Z1ztE3Qo_SA","|"), db);
    	 addLink(new WebLinks("Alexa 2005 DVD HQ Haranath","C9L55K7kAM8","|"), db);
    	 addLink(new WebLinks("Allare Allari 2007 DVD HQ Venu","HGPFPJ0GBqI","|"), db);
    	 addLink(new WebLinks("Allari 2002 DVD HQ Allari Naresh","9rxu9i8Myfs","|"), db);
    	 addLink(new WebLinks("Allari Bullodu 2005 DVD HQ Nitin","t1yQ84muvtc","|"), db);
    	 addLink(new WebLinks("Allari Pidugu 2005 DVD HQ Balakrishna","MeZRvGJo2us","|"), db);
    	 addLink(new WebLinks("Allari Ramudu 2002 DVD HQ Junior NTR","_29c-X9EDjc","|"), db);
    	 addLink(new WebLinks("Allarodu 2005 DVD HQ Rajendra Prasad","sFicqTj-Yi0","|"), db);
    	 addLink(new WebLinks("Allulla Mazaka 2007 DVD HQ Aziz Nasar","irksY0JAU8E","|"), db);
    	 addLink(new WebLinks("Amaravathi 2009 Ravi Babu","BQrFyMHOekU","|"), db);
    	 addLink(new WebLinks("Amma Cheppindi 2006 DVD HQ Suhasini","SXxZufXCd9Q","|"), db);
    	 addLink(new WebLinks("Amma Nanna O Tamil Ammai 2003 DVD HQ Ravi Teja","52QtjUD5g74","|"), db);
    	 addLink(new WebLinks("Ammai Bagundi 2004 DVD HQ Shivaji","CvECzUxrcZk","|"), db);
    	 addLink(new WebLinks("Ammai Kosam 2001 DVD HQ Vineeth","wjCY-sIBvqE","|"), db);
    	 addLink(new WebLinks("Ammailu Abbailu 2003 DVD HQ Vijay","8su7b0rUO2k","|"), db);
    	 addLink(new WebLinks("Ammaye Navvithe 2001 DVD HQ Rajendra Prasad","JyHCuKQqcKc","|"), db);
    	 addLink(new WebLinks("Ammo Bomma 2001 DVD HQ Uma Shankari","Iqkm-vxJSa0","|"), db);
    	 addLink(new WebLinks("Ammulu 2003 DVD HQ Suman","Pp5fJGn4fgg","|"), db);
    	 addLink(new WebLinks("Anand 2004 DVD HQ Raja","4rauwQI-CdU","|"), db);
    	 addLink(new WebLinks("Ananthapuram 2009 DVD HQ Jai","5nQnaNAAMc4","|"), db);
    	 addLink(new WebLinks("Anasuya 2007 DVD HQ Bhoomika Chawla","gz2yH9rDrDQ","|"), db);
    	 addLink(new WebLinks("Andagadu 2005 DVD HQ Rajendra Prasad","e2AnwJXqx7o","|"), db);
    	 addLink(new WebLinks("Andala Ramudu 2006 DVD HQ Sunil","sYckQ1fvX0w","|"), db);
    	 addLink(new WebLinks("Andamaina Manasulo 2008 DVD HQ Rajiv","ld7GTDqiszY","|"), db);
    	 addLink(new WebLinks("Andari Bandhuvaya 2010 DVD HQ Sharwanand","Jku2ptiPKbU","|"), db);
    	 addLink(new WebLinks("Andarivadu 2005 DVD Chiranjeevi","WghHTM_g9JA","|"), db);
    	 addLink(new WebLinks("Andaru Dongale Dorikithe 2004 DVD HQ Rajendra Prasad","KNTQWTrceK0","|"), db);
    	 addLink(new WebLinks("Andhrawala 2004 DVD HQ Junior NTR","fh1HaMiGOfA","|"), db);
    	 addLink(new WebLinks("Andhrudu 2005 DVD HQ Gopichand","Z8gsgLVMGGM","|"), db);
    	 addLink(new WebLinks("Anjali I Love You 2004 DVD HQ Vikram Produturi","jC6fwlTisuM","|"), db);
    	 addLink(new WebLinks("Anjaneyulu 2009 DVD HQ Ravi Teja","2bJCSbKWJOE","|"), db);
    	 addLink(new WebLinks("Annavaram 2006 DVD HQ Pavan Kalyan","vbPD7DxkIPM","|"), db);
    	 addLink(new WebLinks("Annayya 2000 Chiranjeevi","OTIBXp6oEIU","|"), db);
    	 addLink(new WebLinks("Antha Oka Maya 2009 DVD HQ Suman","vqjtZ4XHzkQ","|"), db);
    	 addLink(new WebLinks("Anukokunda Oka Roju 2005 DVD HQ Charmi","S4UZ8CmIQIU","|"), db);
    	 addLink(new WebLinks("Anumanaspadam 2007 DVD HQ Aryan Rajesh","hf1fsz_TAvk","|"), db);
    	 addLink(new WebLinks("Anveshana 2002 DVD Ravi Teja","gc2HCqmbJkQ","|"), db);
    	 addLink(new WebLinks("Appuchesi Pappukudu 2008 DVD HQ Rajendra Prasad","YfGeCfLNfmU","|"), db);
    	 addLink(new WebLinks("Appudappudu 2003 DVD HQ Raja","UbHgB3XTxII","|"), db);
    	 addLink(new WebLinks("Aptudu 2004 DVD HQ Rajasekhar","V6bfXsBbqpM","|"), db);
    	 addLink(new WebLinks("Are 2005 Kesava Teerdha","cnd2tvk47R8","|"), db);
    	 addLink(new WebLinks("Arjun 2004 DVD HQ Mahesh Babu","lG4F8lGygow","|"), db);
    	 addLink(new WebLinks("Asadhyudu 2006 DVD HQ Kalyan Ram","n5p2t4tVhOw","|"), db);
    	 addLink(new WebLinks("Ashala Palle Ki 2006 DVD HQ Vatsha Sanny","QJ3GMAimydk","|"), db);
    	 addLink(new WebLinks("Ashok 2006 DVD HQ Junior NTR","2ASsgzQm4QY","|"), db);
    	 addLink(new WebLinks("Astram 2006 DVD HQ Vishnu","-8P98ECHGIk","|"), db);
    	 addLink(new WebLinks("Athade Oka Sainyam 2004 DVD HQ Jagapathi Babu","BbpWbt8Ovkc","|"), db);
    	 addLink(new WebLinks("Athadu 2005 DVD HQ Mahesh Babu","8jgkq8Y2h-k","|"), db);
    	 addLink(new WebLinks("Athanokkade 2005 DVD HQ Kalyan Ram","Axo1qkT75vo","|"), db);
    	 addLink(new WebLinks("Athanu 2001 DVD HQ Sai Kumar","UkAzsvpuwb8","|"), db);
    	 addLink(new WebLinks("Athidi 2007 DVD HQ Mahesh Babu","zAwhnLJsbFo","|"), db);
    	 addLink(new WebLinks("Athili Sathibabu LKG 2007 DVD HQ Allari Naresh","mLvMHWXSJQU","|"), db);
    	 addLink(new WebLinks("Avakai Biryani 2008 DVD HQ Kamal Kamaraju","xjFySDXLe_M","|"), db);
    	 addLink(new WebLinks("Avunanna Kadanna 2005 DVD HQ Uday Kiran","c7NZRqOE5bc","|"), db);
    	 addLink(new WebLinks("Avunu Valliddaru Istapaddaru 2002 DVD HQ Ravi Teja","8LeK4OtNkBc","|"), db);
    	 addLink(new WebLinks("Awara 2010 DVD HQ Karthi Shivakumar","4ynLYpTI2FU","|"), db);
    	 addLink(new WebLinks("Azad 2000 DVD HQ Nagarjuna","9vQjTYphT04","|"), db);
    	 addLink(new WebLinks("Baalu 2005 DVD HQ Pavan Kalyan","uPdXi6xIBlc","|"), db);
    	 addLink(new WebLinks("Baanam 2009 DVD Nara Rohit","nFDbwVY0wVg","|"), db);
    	 addLink(new WebLinks("Badmash 2010 DVD HQ Naga Siddharth","brTwtyfNIj4","|"), db);
    	 addLink(new WebLinks("Badrachalam 2001 DVD Srihari","9NYrFnZ9dFQ","|"), db);
    	 addLink(new WebLinks("Badri 2000 DVD HQ Pavan Kalyan","OI88Kth_cqM","|"), db);
    	 addLink(new WebLinks("Bagunnaraa 2000 DVD HQ Vadde Naveen","AlZF4Ty2Un4","|"), db);
    	 addLink(new WebLinks("Bahumati 2007 DVD HQ Venu","QizAwPzDgnc","|"), db);
    	 addLink(new WebLinks("Bangaram 2006 DVD HQ Pavan Kalyan","Z-MGHnSW2vI","|"), db);
    	 addLink(new WebLinks("Bangaru Babu 2009 DVD HQ Jagapathi Babu","RcKgQqxIX0c","|"), db);
    	 addLink(new WebLinks("Bangaru Konda 2007 DVD HQ Rishi","q3ngr2FofO0","|"), db);
    	 addLink(new WebLinks("Bathukamma 2008 DVD HQ Sindhu Tolani.","lZcbbjFn40E","|"), db);
    	 addLink(new WebLinks("Bava 2010 DVD HQ Siddharth","fqA4Lpun0ro","|"), db);
    	 addLink(new WebLinks("Bava Nachadu 2001 DVD HQ Nagarjuna","rYJ5ihyyb_4","|"), db);
    	 addLink(new WebLinks("Bendu Apparao RMP 2009 DVD Allari Naresh","5vfBc-p5etE","|"), db);
    	 addLink(new WebLinks("Bezawada Police Station 2002 DVD HQ Keshav","F30dFed1dUE","|"), db);
    	 addLink(new WebLinks("Bhadra 2005 DVD HQ Ravi Teja","a08-34byBDY","|"), db);
    	 addLink(new WebLinks("Bhadradri 2008 DVD HQ Srihari","9b5ST__bgmk","|"), db);
    	 addLink(new WebLinks("Bhagavathi 2002 DVD HQ Vijay","_Zp7Fi1anTA","|"), db);
    	 addLink(new WebLinks("Bhageeratha 2005 DVD HQ Ravi Teja","iF85OTOAGLg","|"), db);
    	 addLink(new WebLinks("Bhagyalakshmi Bumper Draw 2006 DVD HQ Rajendra Prasad","k_NJVdJuAKs","|"), db);
    	 addLink(new WebLinks("Bhairava 2010 DVD HQ Srihari","2U5mPCotxhY","|"), db);
    	 addLink(new WebLinks("Bhale Dongalu 2008 DVD HQ Tarun","PhQ8nx60eKo","|"), db);
    	 addLink(new WebLinks("Bhalevadivi Basu 2001 DVD HQ Balakrishna","L0P-zP6dF4o","|"), db);
    	 addLink(new WebLinks("Bharani 2007 Vishaal","rkjhBgQWbS8","|"), db);
    	 addLink(new WebLinks("Bharat Chandra 2005 DVD HQ Suresh Gopi","2CcMNdaC_34","|"), db);
    	 addLink(new WebLinks("Bharath Chandra 2006 DVD HQ Suresh Gopi","-qSC81AIwcI","|"), db);
    	 addLink(new WebLinks("Bhayya 2007 DVD HQ Vishaal","Mpwu5AgeR-g","|"), db);
    	 addLink(new WebLinks("Bheemili 2010 DVD HQ Nani","hu8NDQ8R3zU","|"), db);
    	 addLink(new WebLinks("Bhookailas 2006 DVD HQ Venu Madhav","QU_ZF68guOA","|"), db);
    	 addLink(new WebLinks("Billa 2009 DVD HQ Prabhas","ISyVjDtLAe4","|"), db);
    	 addLink(new WebLinks("Bindaas 2010 DVD HQ Manchu Manoj Kumar","FHShC1mYkpQ","|"), db);
    	 addLink(new WebLinks("Black & White 2008 DVD HQ Rajiv Kanakala","Baj5KxJVgVI","|"), db);
    	 addLink(new WebLinks("Blade Babji 2008 DVD HQ Allari Naresh","4BWgZQJvBgs","|"), db);
    	 addLink(new WebLinks("Bobby 2002 DVD HQ Mahesh Babu","VFRmKKHfepY","|"), db);
    	 addLink(new WebLinks("Bommana Brothers Chanda Sisters 2008 DVD HQ Allari Naresh","GROKNJIKCDg","|"), db);
    	 addLink(new WebLinks("Bommarillu 2006 DVD HQ Siddharth","OimlZuJ94uI","|"), db);
    	 addLink(new WebLinks("Boss 2006 DVD HQ Nagarjuna","Iwz_53VZafU","|"), db);
    	 addLink(new WebLinks("Boys 2003 DVD HQ Siddharth","SywEGQY5c5s","|"), db);
    	 addLink(new WebLinks("Brahmachari 2002 DVD HQ Kamal Hassan","lgHQ-u765HA","|"), db);
    	 addLink(new WebLinks("Brahmalokam To Yamalokam Via Bhulokam 2010 DVD HQ Rajendra Prasad","zybiuSfMXak","|"), db);
    	 addLink(new WebLinks("Brahmanandam Drama Company 2008 DVD HQ Brahmanandam","MELl7qdPEes","|"), db);
    	 addLink(new WebLinks("Brindavanam 2010 DVD HQ Junior NTR","7dG4j63mQ04","|"), db);
    	 addLink(new WebLinks("Broker 2010 DVD HQ R P Patnaik","MQzE3bg1iF0","|"), db);
    	 addLink(new WebLinks("Buddhimantudu 2009 DVD HQ Upendra","-iC8MQvd-e4","|"), db);
    	 addLink(new WebLinks("Budget Padmanabham 2001 DVD HQ Jagapathi Babu","KTHzjpCJ49Y","|"), db);
    	 addLink(new WebLinks("Bujjigadu 2008 DVD HQ Prabhas","pDinQ62WKcI","|"), db);
    	 addLink(new WebLinks("Bullet 2009 DVD HQ Suresh Gopi","WLMzdcYpD9E","|"), db);
    	 addLink(new WebLinks("Bumper Offer 2009 DVD HQ Sairam Shankar","i1e6HZLTN6E","|"), db);
    	 addLink(new WebLinks("Bunny 2005 DVD HQ Allu Arjun","3NmD_Bh3rBQ","|"), db);
    	 addLink(new WebLinks("Caraa Majaka 2010 DVD HQ Rajiv Kanakala","_qFzZbGDRq4","|"), db);
    	 addLink(new WebLinks("Cash 2002 DVD HQ Gemini","rPabKNqb7Jg","|"), db);
    	 addLink(new WebLinks("Chiruta 2007 DVD HQ Ram Charan","8sBmXO4D3nY","|"), db);
    	 addLink(new WebLinks("Chukkallo Chandrudu 2006 DVD HQ Siddharth","-pwfRwUagX0","|"), db);
    	 addLink(new WebLinks("City 2003 DVD HQ Mukesh","P8p5waWi494","|"), db);
    	 addLink(new WebLinks("City War 2008 Vinod Kumar","9P2YMfEWW5M","|"), db);
    	 addLink(new WebLinks("Collage Days 2008 DVD Arya","TtnxHbuDMlg","|"), db);
    	 addLink(new WebLinks("Commisioner Narsimha Naidu 2009 DVD HQ Vijaykanth","JAc2wxXC7Lc","|"), db);
    	 addLink(new WebLinks("Commissoner Rudrama Naidu 2001 DVD HQ Mammootty","4fFiWGwPSNw","|"), db);
    	 addLink(new WebLinks("Criminal 2000 Ajith","9PugP8bW050","|"), db);
    	 addLink(new WebLinks("Current 2009 DVD HQ Sushant","k8i6snrlcu4","|"), db);
    	 addLink(new WebLinks("Daddy 2001 DVD Chiranjeevi","-oZh1AqgoI8","|"), db);
    	 addLink(new WebLinks("Dammunnodu 2008 DVD HQ Rishi","uaj5LJHPBpY","|"), db);
    	 addLink(new WebLinks("Danger 2005 DVD HQ Allari Naresh","DS-pVfVfSi0","|"), db);
    	 addLink(new WebLinks("Darling 2010 DVD HQ Prabhas","JJSrthcbMlU","|"), db);
    	 addLink(new WebLinks("Dasanna 2010 DVD HQ Srihari","mCDhyf31CfU","|"), db);
    	 addLink(new WebLinks("Deal 2007 Surya","hO1xMMcaS3E","|"), db);
    	 addLink(new WebLinks("Deepavali 2008 DVD HQ Venu","_iEVmznZjcE","|"), db);
    	 addLink(new WebLinks("Desamuduru 2007 DVD HQ Allu Arjun","77sI_bufxpY","|"), db);
    	 addLink(new WebLinks("Deva 2007 DVD HQ Surya","HaoEdNJlGDI","|"), db);
    	 addLink(new WebLinks("Devadasu 2006 DVD HQ Ram","YIpie-ImFdE","|"), db);
    	 addLink(new WebLinks("Devarakonda Veeraiah Koothuru Kosam 2008 DVD HQ R Narayana Murthy","ou38bfYTyrg","|"), db);
    	 addLink(new WebLinks("Devi Nagamma 2002 DVD HQ Prema","wVwnbNP8T0w","|"), db);
    	 addLink(new WebLinks("Devi Putrudu 2001 DVD HQ Venkatesh","c8m2umBw1ZQ","|"), db);
    	 addLink(new WebLinks("Devullu 2001 DVD HQ Raasi","JCxGBa6sG4A","|"), db);
    	 addLink(new WebLinks("Dhana 51 2005 DVD Sumanth","URbTy6JME_8","|"), db);
    	 addLink(new WebLinks("Dhanalakshmi I Love You 2002 DVD HQ Allari Naresh","3AO6TaMksk0","|"), db);
    	 addLink(new WebLinks("Dhee 2007 DVD HQ Vishnu","WfU3wdzgP3I","|"), db);
    	 addLink(new WebLinks("Dheera 2009 DVD HQ Jeevan","FS3V_5z64N8","|"), db);
    	 addLink(new WebLinks("Dhum 2003 Jagapathi Babu","5UMn0GM5YnE","|"), db);
    	 addLink(new WebLinks("Dil 2003 DVD HQ Nitin","HjFQN_1C1OM","|"), db);
    	 addLink(new WebLinks("Dishyum Dishyum 2006 DVD Jeeva","G0D9lzM8bvs","|"), db);
    	 addLink(new WebLinks("Dollar Dreams 2000 DVD HQ Satya","d88UFDeccww","|"), db);
    	 addLink(new WebLinks("Don 2007 DVD HQ Nagarjuna","dsID_vkKVTU","|"), db);
    	 addLink(new WebLinks("Don Seenu 2010 DVD Ravi Teja","TGA3KSpH1JE","|"), db);
    	 addLink(new WebLinks("Donga Dongadi 2004 DVD HQ Manchu Manoj Kumar","ckuZGjtHDXY","|"), db);
    	 addLink(new WebLinks("Donga Sachinollu 2008 DVD HQ Krishna Bhagavan","uxRl1DjDD4U","|"), db);
    	 addLink(new WebLinks("Dongala Bandi 2008 DVD HQ Allari Naresh","8StQfReWTCA","|"), db);
    	 addLink(new WebLinks("Dongata 2003 DVD HQ Jagapathi Babu","DriwwVU8qek","|"), db);
    	 addLink(new WebLinks("Dongodi Pelli 2006 DVD HQ Rajendra Babu","n8Rj_ZtX1Nc","|"), db);
    	 addLink(new WebLinks("Dongodu 2003 Ravi Teja","UpC4D3uA0lI","|"), db);
    	 addLink(new WebLinks("Dopidi 2010 DVD HQ Vivek","_CFU5KKJ0Q8","|"), db);
    	 addLink(new WebLinks("Dosth 2004 DVD HQ Siva Balaji","KV3_VHNLUTo","|"), db);
    	 addLink(new WebLinks("Dubai Seenu 2007 Ravi Teja","8_nUQEMjFY0","|"), db);
    	 addLink(new WebLinks("Dum Dum Dum 2001 DVD HQ Madhavan","ilsSvHthiWk","|"), db);
    	 addLink(new WebLinks("E 2007 DVD HQ Jeeva","H8Lwmg4XLW0","|"), db);
    	 addLink(new WebLinks("Eenadu 2000 DVD HQ Mammootty","uozO1WWEMBA","|"), db);
    	 addLink(new WebLinks("Ek Niranjan 2009 DVD HQ Prabhas","UP7_WTHwrik","|"), db);
    	 addLink(new WebLinks("Ekaloveudu 2008 DVD HQ Uday Kiran","VMdX0IsEUk0","|"), db);
    	 addLink(new WebLinks("Ela Cheppanu 2003 DVD HQ Sreya","OujmIItn2gY","|"), db);
    	 addLink(new WebLinks("Em Pillo Em Pillado 2010 DVD HQ Tanish","zOPN-4aNJjo","|"), db);
    	 addLink(new WebLinks("Erra Samudram 2008 DVD HQ R Narayana Murthy","ymy5QE2fAn0","|"), db);
    	 addLink(new WebLinks("Evadaina Sare 2007 DVD HQ Kalabavan Mani","B3EZvhy8DkA","|"), db);
    	 addLink(new WebLinks("Evadaite Nakenti 2007 DVD HQ Rajasekhar","3wYtXxJpQBg","|"), db);
    	 addLink(new WebLinks("Evadi Gola Vadidi 2005 DVD HQ Aryan Rajesh","RcY97SLirtA","|"), db);
    	 addLink(new WebLinks("Evadra Rowdy 2001 DVD HQ Srihari","7PwtLtKBwyg","|"), db);
    	 addLink(new WebLinks("Evarinaina Edirista 2007 Akash","uiGq-gmWYLU","|"), db);
    	 addLink(new WebLinks("Family Circus 2001 DVD HQ Jagapathi Babu","nSc20ieWBdU","|"), db);
    	 addLink(new WebLinks("February 14 Necklace Road 2000 DVD Suman","ziE-iY_UaTI","|"), db);
    	 addLink(new WebLinks("Fitting Master 2009 DVD HQ Allari Naresh","PDJrErk4bmQ","|"), db);
    	 addLink(new WebLinks("Flash News 2008 DVD HQ Rajiv Kanakala","e3CuX7oalMQ","|"), db);
    	 addLink(new WebLinks("Friends 2002 DVD HQ Shivaji","Ge5Zgye1hI4","|"), db);
    	 addLink(new WebLinks("Game 2006 DVD HQ Mohan Babu","5FmZDEKMZtM","|"), db);
    	 addLink(new WebLinks("Gamyam 2008 DVD HQ Allari Naresh","MoaIRcR8DTE","|"), db);
    	 addLink(new WebLinks("Gana 2006 Brahmaji","mF-Z8ywT224","|"), db);
    	 addLink(new WebLinks("Ganesh 2009 DVD Ram","3uDnHMJYfko","|"), db);
    	 addLink(new WebLinks("Gangotri 2003 DVD HQ Allu Arjun","rwZXM5CJ5is","|"), db);
    	 addLink(new WebLinks("Gemini 2002 DVD HQ Venkatesh","cenLF_yc7PE","|"), db);
    	 addLink(new WebLinks("Gharshana 2004 DVD HQ Venkatesh","ffduFgFbg4A","|"), db);
    	 addLink(new WebLinks("Ghatikudu 2009 DVD HQ Surya","OIJ6v9Li76E","|"), db);
    	 addLink(new WebLinks("Giliginthalu 2009 DVD Ali.","5Y3GekA_eco","|"), db);
    	 addLink(new WebLinks("Giri 2004 DVD Arjun","LkZvusGXJnk","|"), db);
    	 addLink(new WebLinks("Goa 2003 DVD HQ Sumit Roy","srfWsEVTli4","|"), db);
    	 addLink(new WebLinks("Godava 2007 DVD HQ Vaibhav","F3fxejyG3JU","|"), db);
    	 addLink(new WebLinks("Godfather 2010 DVD HQ Indrajeet","Xk94bKPE4xg","|"), db);
    	 addLink(new WebLinks("Good Boy 2005 DVD HQ Rohit","c236fiinE0M","|"), db);
    	 addLink(new WebLinks("Gopi Gopika Godavari 2009 DVD Venu","IIYsBnmc0VY","|"), db);
    	 addLink(new WebLinks("Goppinti Alludu 2000 DVD HQ Balakrishna","WTuBX3lfFlc","|"), db);
    	 addLink(new WebLinks("Gorintaaku 2008 DVD HQ Rajasekhar","vcANinYk6ts","|"), db);
    	 addLink(new WebLinks("Gowri 2004 DVD HQ Sumanth","vxpR80VoBoo","|"), db);
    	 addLink(new WebLinks("Gowtam SSC 2005 DVD HQ Navdeep","wYHSMTx6beA","|"), db);
    	 addLink(new WebLinks("Grahanam 2004 Tanikella Bharani","8Ao67pPVeyU","|"), db);
    	 addLink(new WebLinks("Gudu Gudu Gunjam 2010 DVD HQ Rajendra Prasad","sYHYLrNV_eg","|"), db);
    	 addLink(new WebLinks("Gudumba Shankar 2004 DVD HQ Pavan Kalyan","cZrjoRkUUB8","|"), db);
    	 addLink(new WebLinks("Gunde Jhallumandi 2008 DVD HQ Uday Kiran","VDSgPymojAo","|"), db);
    	 addLink(new WebLinks("Guri 2004 DVD HQ Srihari","KSHS42e2mHw","|"), db);
    	 addLink(new WebLinks("Hai 2002 Aryan Rajesh","lKp_7ukE7eo","|"), db);
    	 addLink(new WebLinks("Hai Subramanyam 2005 DVD HQ SriRam","NtxZKeU8KxQ","|"), db);
    	 addLink(new WebLinks("Hanuman Junction 2001 DVD HQ Jagapathi Babu","Xm4sVdIWGKo","|"), db);
    	 addLink(new WebLinks("Hanumanthu 2006 DVD HQ Srihari","ZUigNIOZsDw","|"), db);
    	 addLink(new WebLinks("Happy 2006 DVD HQ Allu Arjun","8ZU7JtcWwPs","|"), db);
    	 addLink(new WebLinks("Happy Days 2007 DVD HQ Sandesh","FTMox3mbWHA","|"), db);
    	 addLink(new WebLinks("Happy Happy Ga 2010 DVD HQ Varun Sandesh","zSfcS1Bbj8g","|"), db);
    	 addLink(new WebLinks("Hare Ram 2008 DVD HQ Kalyan Ram","0xm2dqZFU9o","|"), db);
    	 addLink(new WebLinks("Heart Beats 2008 DVD Simran","9BaA_AHkroU","|"), db);
    	 addLink(new WebLinks("Hero 2008 DVD HQ Nitin","7bIoEFsGozk","|"), db);
    	 addLink(new WebLinks("High School 2010 DVD HQ Kiran Rathod","m0RFxY0aF6o","|"), db);
    	 addLink(new WebLinks("Himsinche 23va Raju Pulakesi 2007 DVD HQ Vadivelu","-WmheT6skBo","|"), db);
    	 addLink(new WebLinks("Hisss 2010 Mallika Sherawat","oo63op1-T6g","|"), db);
    	 addLink(new WebLinks("Hitech Students 2003 DVD HQ Sai Kiran","eHZnPUIpzNI","|"), db);
    	 addLink(new WebLinks("Holi 2002 DVD Uday Kiran","FdesJ0e3slo","|"), db);
    	 addLink(new WebLinks("Homam 2008 DVD HQ Jagapathi Babu","tts2dTuEgDc","|"), db);
    	 addLink(new WebLinks("Hungama 2005 DVD HQ Ali","wTtMdmSNM9U","|"), db);
    	 addLink(new WebLinks("Ice 2006 Vijaya Bhaskar","ZoNV2bloXN4","|"), db);
    	 addLink(new WebLinks("Idiot 2002 DVD HQ Ravi Teja","5CsGcWXt-8o","|"), db);
    	 addLink(new WebLinks("Indhu 2010 DVD Charmi","fGoeGiKOJgg","|"), db);
    	 addLink(new WebLinks("Indian Beauty 2006 DVD HQ Colin Mcphee","2CpSNhtK5lU","|"), db);
    	 addLink(new WebLinks("Ji 2005 DVD HQ Ajith","llCKchNI5G0","|"), db);
    	 addLink(new WebLinks("Jodi No. 1 2003 DVD HQ Uday Kiran","GPW2-GpE_jA","|"), db);
    	 addLink(new WebLinks("John Apparao 40 Plus 2008 DVD HQ Krishna Bhagavan","T_AyRp0Amow","|"), db);
    	 addLink(new WebLinks("Josh 2009 DVD HQ Naga Chaitanya","n6op2UcrOFg","|"), db);
    	 addLink(new WebLinks("Judgement 2004 DVD HQ Arjun","kxyu1kEWS40","|"), db);
    	 addLink(new WebLinks("Kaani 2004 DVD HQ Sai Kiran","PP0GTgsvghE","|"), db);
    	 addLink(new WebLinks("Kaasi 2004 DVD HQ J D Chakravarthi","3GI_jCoXvBk","|"), db);
    	 addLink(new WebLinks("Kaasipatnam Choodara Babu 2008 DVD HQ Vijay Anand","q0BWw0ZTil8","|"), db);
    	 addLink(new WebLinks("Kabaddi Kabaddi 2003 DVD HQ Jagapathi Babu","btuXaIGtJjU","|"), db);
    	 addLink(new WebLinks("Kabirdas 2003 DVD HQ Vijayachandar","e0TjUK0E5p0","|"), db);
    	 addLink(new WebLinks("Kalasala 2009 DVD HQ Akhil","qv7hTZerPKg","|"), db);
    	 addLink(new WebLinks("Kalavaramaye Madilo 2009 DVD HQ Kamal Kamaraju","juOig09a1-8","|"), db);
    	 addLink(new WebLinks("Kalidasu 2008 DVD HQ Sushant","Af19HKB5rgE","|"), db);
    	 addLink(new WebLinks("Kalisundam Raa 2000 DVD Venkatesh","7TBWPHLClNA","|"), db);
    	 addLink(new WebLinks("Kalisunte 2006 DVD HQ Navdeep","fashJjIzCvY","|"), db);
    	 addLink(new WebLinks("Kalusukovalani 2002 DVD HQ Uday Kiran","7GVcg7kcmps","|"), db);
    	 addLink(new WebLinks("Kalyana Ramudu 2003 DVD HQ Venu","SSfNEPbwVG0","|"), db);
    	 addLink(new WebLinks("Kanchanamala Cable TV 2005 DVD HQ Srikanth","qoE_FZ40rs8","|"), db);
    	 addLink(new WebLinks("Kanthri 2008 DVD HQ Junior NTR","kIhUHb9E8uc","|"), db);
    	 addLink(new WebLinks("Kantri Mogudu 2010 DVD HQ Upendra","vnENF9hSSF4","|"), db);
    	 addLink(new WebLinks("Karma 2010 DVD Sesh Adavi","wAcFoYUtnKs","|"), db);
    	 addLink(new WebLinks("Karthavyam 2005 DVD HQ SriRam","m_-f5jx8ATw","|"), db);
    	 addLink(new WebLinks("Kathi 2010 DVD HQ Kalyan Ram","6FhxFbXBqGk","|"), db);
    	 addLink(new WebLinks("Katti Kantharao 2010 DVD Allari Naresh","g4RX_KefO9s","|"), db);
    	 addLink(new WebLinks("Kavya's Diary 2009 DVD Manjula Swaroop","7dI2xXksYTc","|"), db);
    	 addLink(new WebLinks("Kedi 2010 DVD HQ Nagarjuna","I-OQi4PtIrc","|"), db);
    	 addLink(new WebLinks("Keka 2008 DVD HQ Raja","uqahN8cVaZ4","|"), db);
    	 addLink(new WebLinks("Khadgam 2002 DVD HQ Ravi Teja","yYG_eYobWtk","|"), db);
    	 addLink(new WebLinks("Khatarnak 2006 DVD HQ Ravi Teja","w85gQ258xwQ","|"), db);
    	 addLink(new WebLinks("King 2008 DVD HQ Nagarjuna","1lks8_hBZTU","|"), db);
    	 addLink(new WebLinks("Kitakitalu 2006 DVD HQ Allari Naresh","qfAJzgojb6k","|"), db);
    	 addLink(new WebLinks("Kittu Animated Movie 2007 DVD HQ .","1hMJfnnCHyU","|"), db);
    	 addLink(new WebLinks("Kodanda Ramudu 2000 DVD HQ J D Chakravarthi","TXGG3IgMYkI","|"), db);
    	 addLink(new WebLinks("Kokila 2006 DVD HQ Raja","-7-o8_rZmW0","|"), db);
    	 addLink(new WebLinks("Komaram Puli 2010 DVD Pavan Kalyan","bT12il0valE","|"), db);
    	 addLink(new WebLinks("Konchem Ishtam Konchem Kashtam 2009 DVD Siddharth","JRV9NiKJKoM","|"), db);
    	 addLink(new WebLinks("Konchem Touchlo Vunte Cheputanu 2004 DVD HQ Shivaji","GVPosXlH-uk","|"), db);
    	 addLink(new WebLinks("Konte Kurrallu 2006 DVD HQ Santosh Pavan","tgeGJoCLhMg","|"), db);
    	 addLink(new WebLinks("Kotha Bangaru Lokam 2008 DVD HQ Varun Sandesh","4iZILQ6LHLY","|"), db);
    	 addLink(new WebLinks("Kouravudu 2000 DVD HQ Nagendra babu","BrUaPZXPNpM","|"), db);
    	 addLink(new WebLinks("Kousalya Supraja Rama 2008 DVD Srikanth","tCKCHvyrQIs","|"), db);
    	 addLink(new WebLinks("Krishna 2008 DVD HQ Ravi Teja","cuewRj4Lepk","|"), db);
    	 addLink(new WebLinks("Krishnarjuna 2008 DVD HQ Vishnu","OTyDTQo_B4Y","|"), db);
    	 addLink(new WebLinks("Kshemamga velli Labhamgaa Randi 2000 DVD HQ Srikanth","L7rTthS5_fg","|"), db);
    	 addLink(new WebLinks("Kshudra 2008 DVD HQ Priyanka","Acmtv5RKGNI","|"), db);
    	 addLink(new WebLinks("Kuberulu 2008 DVD HQ Ali","E80uQ17L1dM","|"), db);
    	 addLink(new WebLinks("Kurradu 2009 DVD Varun Sandesh","yQsmYL2WCvY","|"), db);
    	 addLink(new WebLinks("Kushi 2001 DVD HQ Pavan Kalyan","9ARiy0nzfF0","|"), db);
    	 addLink(new WebLinks("Kushi Kushigaa 2004 DVD HQ Jagapathi Babu","lHC4ndkHSV0","|"), db);
    	 addLink(new WebLinks("Laila Majnu 2007 DVD Harivarun","k_CWuiL4c-0","|"), db);
    	 addLink(new WebLinks("Lakshmi 2006 DVD HQ Venkatesh","_H2w0CpD5fo","|"), db);
    	 addLink(new WebLinks("Lakshmi Ganesh 2004 DVD HQ Sai Kumar","e1uZ4sI0wBM","|"), db);
    	 addLink(new WebLinks("Lakshmi Kalyanam 2007 DVD HQ Kalyan Ram","pCZEA5GLwIY","|"), db);
    	 addLink(new WebLinks("Lakshmi Puthrudu 2008 DVD HQ Uday Kiran","mIvxmSYL6j8","|"), db);
    	 addLink(new WebLinks("Lakshyam 2007 DVD HQ Gopichand","Be173flredk","|"), db);
    	 addLink(new WebLinks("Leela Mahal Center 2004 DVD HQ Aryan Rajesh","U5gODWZ9qXs","|"), db);
    	 addLink(new WebLinks("Letha Manasulu 2004 DVD HQ Srikanth","9nwRFJY3v7Q","|"), db);
    	 addLink(new WebLinks("Life Style 2009 DVD HQ Nischal","hNaQX9pijYQ","|"), db);
    	 addLink(new WebLinks("Maa Alludu Very Good 2003 DVD HQ Rajendra Prasad","G9cIpkNtXWY","|"), db);
    	 addLink(new WebLinks("Maa Annayya 2000 DVD HQ Rajasekhar","B5h2Du2LITo","|"), db);
    	 addLink(new WebLinks("Maa Ayana Sundaraiah 2001 DVD Srihari","4Ve2L-QfKjk","|"), db);
    	 addLink(new WebLinks("Maa Bapubommaki Pellanta 2003 DVD Ajay Raghavendra","DqMhwUmeZJ0","|"), db);
    	 addLink(new WebLinks("Maa iddari Madhya 2006 DVD HQ Bharath","Z30xQlIkYQ8","|"), db);
    	 addLink(new WebLinks("Maa Nanna Chiranjeevi 2010 DVD HQ Jagapathi Babu","3VFTFdND1Xs","|"), db);
    	 addLink(new WebLinks("Madhumasam 2007 DVD HQ Sumanth","vRHo59cC3Is","|"), db);
    	 addLink(new WebLinks("Mafia King 2001 DVD HQ Lal","G_KBgyTBhqM","|"), db);
    	 addLink(new WebLinks("Magadheera 2009 DVD HQ Ram Charan","plDUrJSUSbY","|"), db);
    	 addLink(new WebLinks("Maha 2006 DVD Bharat","troetf6nGks","|"), db);
    	 addLink(new WebLinks("Maha Muduru 2009 Vijay","JSAOvWPF8_s","|"), db);
    	 addLink(new WebLinks("Maha Yagnam 2008 Nasseer","v1NDBkkb3_8","|"), db);
    	 addLink(new WebLinks("Maharadhi 2007 DVD HQ Balakrishna","vqMXrKwqGNc","|"), db);
    	 addLink(new WebLinks("Maharaja Sree 2007 DVD HQ Rishi","WdwABTVIzzg","|"), db);
    	 addLink(new WebLinks("Maisamma IPS 2007 DVD HQ Mumaith Khan","aFfNfXSNmN8","|"), db);
    	 addLink(new WebLinks("Maja 2005 DVD HQ VikRam","3GtBWViv8j8","|"), db);
    	 addLink(new WebLinks("Majunu 2001 DVD HQ Prasanth","iNIuXnoGOOg","|"), db);
    	 addLink(new WebLinks("Mallanna 2009 DVD HQ VikRam","0jOVnVYN_ZI","|"), db);
    	 addLink(new WebLinks("Mallepuvvu 2008 DVD HQ Murali","WNTxllmflg0","|"), db);
    	 addLink(new WebLinks("Mallika 2009 DVD HQ Ajith","5dUxo-Pz5iI","|"), db);
    	 addLink(new WebLinks("Malliswari 2004 DVD Venkatesh","x5CrGjJnkZw","|"), db);
    	 addLink(new WebLinks("Manasanta Nuvve 2001 DVD Uday Kiran","whQYS2QzhUM","|"), db);
    	 addLink(new WebLinks("Manasantha 2004 DVD SriRam","vgdfZDT7dEw","|"), db);
    	 addLink(new WebLinks("Manasu Palike Mounaragam 2006 DVD Sneha","NBWxz9mvq8s","|"), db);
    	 addLink(new WebLinks("Manasu Pilichindi 2009 DVD HQ Kiran","6CfQ0rfoYAQ","|"), db);
    	 addLink(new WebLinks("Manasundi Kaani 2005 DVD HQ SriRam","Cg4b26DXQJg","|"), db);
    	 addLink(new WebLinks("Manasunna Maaraju 2000 DVD HQ Rajasekhar","VidCwVTfseM","|"), db);
    	 addLink(new WebLinks("Mangatayaru Tiffin Centre 2008 DVD HQ Mumaith Khan","jm4UnC-BeI0","|"), db);
    	 addLink(new WebLinks("Manmadha 2004 DVD Simbu","UhRrq8RUZgg","|"), db);
    	 addLink(new WebLinks("Manmadha Baanam 2010 DVD HQ Kamal Hassan","CqpLzUTHKzM","|"), db);
    	 addLink(new WebLinks("Manmadhudu 2002 DVD HQ Nagarjuna","g7JLL-isMV4","|"), db);
    	 addLink(new WebLinks("Manoharam 2000 DVD HQ Jagapathi Babu","TpWbDUlQCXs","|"), db);
    	 addLink(new WebLinks("Mantra 2007 DVD HQ Shivaji","wJEImcSZfRU","|"), db);
    	 addLink(new WebLinks("Maro Charitra 2010 DVD HQ Varun Sandesh","2q1LggcTYzk","|"), db);
    	 addLink(new WebLinks("Marrichettu 2004 DVD HQ J D Chakravarthi","ljYQDwBCEPA","|"), db);
    	 addLink(new WebLinks("Maryada Ramanna 2010 DVD HQ Sunil","OhmOT3FiYag","|"), db);
    	 addLink(new WebLinks("Maska 2009 DVD HQ Ram","TQfRyYXzM-c","|"), db);
    	 addLink(new WebLinks("Mass 2004 DVD HQ Nagarjuna","HJ1rYE9Nric","|"), db);
    	 addLink(new WebLinks("Mayabazar 2006 DVD HQ Raja","ycwxnZSyyF0","|"), db);
    	 addLink(new WebLinks("Mayajalam 2006 Srikanth","GK9YnxwisJ0","|"), db);
    	 addLink(new WebLinks("Mayam 2004 DVD HQ Tusshar Kapoor","vwn738L_MC0","|"), db);
    	 addLink(new WebLinks("Mazaa 2005 DVD HQ VikRam","lyI9KHJUxKE","|"), db);
    	 addLink(new WebLinks("Mee Aayana Jagratha 2000 DVD HQ Rajendra Prasad","uxU4iWRFgxU","|"), db);
    	 addLink(new WebLinks("Mee Intikoste Em Istaaru Maa Intkoste Em Testaaru 2004 DVD HQ Aditya Om","0SH7aTvFcXA","|"), db);
    	 addLink(new WebLinks("Mee Shreyobhilaashi 2007 DVD HQ Rajendra Prasad","2n4OqVABrJg","|"), db);
    	 addLink(new WebLinks("Meenakshi 2005 DVD HQ Kamalini Mukherjee","McAXIsanmAE","|"), db);
    	 addLink(new WebLinks("Mega Alludu 2008 DVD HQ Jayaram","79yEqI8qP1g","|"), db);
    	 addLink(new WebLinks("Meghamala O Pellam Gola 2005 DVD HQ Santosh Pavan","zDcNQYDVGQ4","|"), db);
    	 addLink(new WebLinks("Mental Krishna 2009 DVD HQ Posani Krishna Murali","vznaS-zfKXA","|"), db);
    	 addLink(new WebLinks("Mesthri 2009 DVD HQ Dasari Narayana Rao","Wddf0WMdA_s","|"), db);
    	 addLink(new WebLinks("Michael Madana Kamaraju 2008 DVD HQ Srikanth","2j1AoIC1H54","|"), db);
    	 addLink(new WebLinks("Missamma 2003 DVD HQ Bhoomika Chawla","W5P8_auX_RY","|"), db);
    	 addLink(new WebLinks("Mitrudu 2009 DVD HQ Balakrishna","P8xlv23YX1k","|"), db);
    	 addLink(new WebLinks("Modati Cinema 2005 DVD HQ Navdeep","zAFhvj14Q8A","|"), db);
    	 addLink(new WebLinks("Namo Venkatesa 2010 DVD HQ Venkatesh","aCkH1xXDzHo","|"), db);
    	 addLink(new WebLinks("Nandanavanam 120 km 2006 DVD Ajay Varma","9hAQSJgmQ1E","|"), db);
    	 addLink(new WebLinks("Narahari 2002 DVD HQ Sai Kumar","4K2VmCrjKSM","|"), db);
    	 addLink(new WebLinks("Narasimha Naidu 2001 DVD HQ Balakrishna","3HGjPv_JZuQ","|"), db);
    	 addLink(new WebLinks("Narasimhudu 2005 DVD HQ Junior NTR","ReaaDP-5xTg","|"), db);
    	 addLink(new WebLinks("Natudu 2006 DVD VikRam","kb5JgUJGbTE","|"), db);
    	 addLink(new WebLinks("Nava Manmadhudu 2006 Santosh","6g2NELVvDQQ","|"), db);
    	 addLink(new WebLinks("Nava Vasantham 2007 DVD HQ Tarun","qjhuIuLh49c","|"), db);
    	 addLink(new WebLinks("Navvu Navvinchu 2003 DVD HQ Suman","4X4igmuLBI4","|"), db);
    	 addLink(new WebLinks("Navvuthu Batakalira 2001 DVD HQ J D Chakravarthi","Bz4GQ1m3RsM","|"), db);
    	 addLink(new WebLinks("Nayakudu 2005 Rajasekhar","5f6mVV19o34","|"), db);
    	 addLink(new WebLinks("Nee Manasu Naaku Telusu 2003 DVD HQ Tarun","f-FWMnAKJuw","|"), db);
    	 addLink(new WebLinks("Nee Premakai 2002 DVD Abbas","riyAhQtyyfQ","|"), db);
    	 addLink(new WebLinks("Nee Sneham 2002 DVD Uday Kiran","tRvEdcqDQIM","|"), db);
    	 addLink(new WebLinks("Nee Sukhame Ne Koruthunna 2008 DVD HQ Raja","ujzYENDjGNw","|"), db);
    	 addLink(new WebLinks("Nee Thodu Kavali 2002 DVD HQ Deepak","hsQdNB8BuwU","|"), db);
    	 addLink(new WebLinks("Neeke Manasichaanu 2003 Srikanth","loacp0xeDUU","|"), db);
    	 addLink(new WebLinks("Neeku Nenu Naaku Nuvvu 2003 DVD HQ Uday Kiran","SwbwjgvWLuM","|"), db);
    	 addLink(new WebLinks("Neelo Naalo 2009 DVD Shaam","Hrvke50eEPc","|"), db);
    	 addLink(new WebLinks("Neethone Vuntanu 2002 DVD HQ Upendra","k8sar52pQVI","|"), db);
    	 addLink(new WebLinks("Neevalle Neevalle 2007 DVD Vinay","9uD-kz4uI5E","|"), db);
    	 addLink(new WebLinks("Neevente Nenunta 2000 DVD Kiran","POrJGVOZkYU","|"), db);
    	 addLink(new WebLinks("Nene Ambani 2010 DVD HQ Arya","_FMoVBEzJfs","|"), db);
    	 addLink(new WebLinks("Neninthe 2008 DVD HQ Ravi Teja","Zg6NFCE9xfs","|"), db);
    	 addLink(new WebLinks("Nenu 2004 DVD HQ Allari Naresh","3oZ56pF-97o","|"), db);
    	 addLink(new WebLinks("Nenu pelliki ready 2003 DVD HQ Srikanth","Y-5KVpdKiY0","|"), db);
    	 addLink(new WebLinks("Nenu Saitam 2004 DVD HQ Madhala Ravi","2HMzksQ8gRY","|"), db);
    	 addLink(new WebLinks("Nenu Seetamahalakshmi 2003 DVD HQ Rohit","7MentDjjJyM","|"), db);
    	 addLink(new WebLinks("Nenunnanu 2004 DVD HQ Nagarjuna","RO4xXvBkAwk","|"), db);
    	 addLink(new WebLinks("News 2005 DVD Upendra","NTgv0BQTj3k","|"), db);
    	 addLink(new WebLinks("Nice Guy 2006 Anand","hhoxsiej2Eg","|"), db);
    	 addLink(new WebLinks("Nikki N Neeraj 2007 DVD HQ Shivaji","JTRmhxa2HQo","|"), db);
    	 addLink(new WebLinks("Nikkis Engagement 2005 Rekha Nikki","caNp4gbbb_8","|"), db);
    	 addLink(new WebLinks("Nilambari 2002 DVD HQ Suman","e76dRztz6zM","|"), db);
    	 addLink(new WebLinks("Nimisham 2004 DVD HQ Ravi Kabra","1LtK-9eYloc","|"), db);
    	 addLink(new WebLinks("Ninna Nedu Repu 2008 DVD HQ Ravi Krishna","aE9Jd3KYMOU","|"), db);
    	 addLink(new WebLinks("Ninne Istapaddanu 2003 Tarun","Upn0cH-0228","|"), db);
    	 addLink(new WebLinks("Ninne Premista 2000 DVD Srikanth","FUC3G_EljAw","|"), db);
    	 addLink(new WebLinks("Ninnu Choosaka 2000 DVD HQ Madhavan","NQWxDxRCeOU","|"), db);
    	 addLink(new WebLinks("Nireekshana 2005 DVD HQ Aryan Rajesh","bGG6DgxGW-Q","|"), db);
    	 addLink(new WebLinks("No 2004 NT Rathnaa","qy16aODhRcg","|"), db);
    	 addLink(new WebLinks("No Entry 2006 DVD Aditya Om","0vIPo-Wn8lo","|"), db);
    	 addLink(new WebLinks("Note Book 2007 DVD Rajiv","oltmi61QzSc","|"), db);
    	 addLink(new WebLinks("Nuvvante Nakistam 2005 DVD HQ Aryan Rajesh","2ZqwMgWYzw0","|"), db);
    	 addLink(new WebLinks("Nuvve Nuvve 2002 DVD HQ Tarun","-OF9cEFiM4o","|"), db);
    	 addLink(new WebLinks("Nuvvostanante Nenoddantana 2005 DVD HQ Siddharth","c_diMSAaojU","|"), db);
    	 addLink(new WebLinks("Nuvvu Leka Nenu Lenu 2002 DVD HQ Tarun","cdyxzlu7Kwo","|"), db);
    	 addLink(new WebLinks("Nuvvu Naaku Nachav 2001 DVD HQ Venkatesh","RqMZao60QYM","|"), db);
    	 addLink(new WebLinks("Nuvvu Nenu 2001 DVD HQ Uday Kiran","puTXexmmtxM","|"), db);
    	 addLink(new WebLinks("Nuvvu Nenu Prema 2007 DVD HQ Surya","SbebJZR_mD0","|"), db);
    	 addLink(new WebLinks("Nuvvu Vastavani 2000 DVD HQ Nagarjuna","jx6D1oRkr8M","|"), db);
    	 addLink(new WebLinks("Nyayam Kavali 2008 DVD Amith Rao","OoJnICkL9wI","|"), db);
    	 addLink(new WebLinks("O Chinadana 2002 DVD HQ Srikanth","FXe12ZLrNT4","|"), db);
    	 addLink(new WebLinks("Oh Inti Illalu 2010 DVD HQ Jayaram","2aPYjQb-DNM","|"), db);
    	 addLink(new WebLinks("Oka Oorilo 2005 DVD Tarun","0gDGc9CDaXA","|"), db);
    	 addLink(new WebLinks("Oka Pellam Muddu Rendo Pellam Vaddu 2004 DVD HQ Rajendra Prasad","Gms_KBu8cKA","|"), db);
    	 addLink(new WebLinks("Oka V Chitram 2006 DVD Pradeep Shetty","wpVLcQ0StqE","|"), db);
    	 addLink(new WebLinks("Okka Magaadu 2008 DVD HQ Balakrishna","_al_JsWHYD0","|"), db);
    	 addLink(new WebLinks("Okkade 2005 DVD HQ Srihari","CBig_lspt_4","|"), db);
    	 addLink(new WebLinks("Okkadu 2003 DVD HQ Mahesh Babu","QtpshVkb4G0","|"), db);
    	 addLink(new WebLinks("Okkadu Chaalu 2005 DVD HQ Rajasekhar","aLYZypDzYdY","|"), db);
    	 addLink(new WebLinks("Okkadunnadu 2007 DVD HQ Gopichand","5tSGSv6xysY","|"), db);
    	 addLink(new WebLinks("Ontari 2008 DVD HQ Gopi Chand","t6OMGNZO57M","|"), db);
    	 addLink(new WebLinks("Ooha Chitram 2009 DVD HQ Vamsi Krishna","IJZmZHR0Kj8","|"), db);
    	 addLink(new WebLinks("Operation Duryodhana 2007 DVD HQ Srikanth","RB9U4Go1b68","|"), db);
    	 addLink(new WebLinks("Orange 2010 DVD HQ Ram Charan","bigVOl6FrVQ","|"), db);
    	 addLink(new WebLinks("Orey Pandu 2005 DVD HQ Sachin","2fc4wK29d4A","|"), db);
    	 addLink(new WebLinks("Ori Nee Prema Bangaram Kaanu 2003 DVD HQ Rajesh","LTfMd120L-w","|"), db);
    	 addLink(new WebLinks("Ottesi Cheputunna 2003 Srikanth","9DVlbh4-7Zc","|"), db);
    	 addLink(new WebLinks("Oy 2009 DVD HQ Siddharth","1QH2qvSwbGA","|"), db);
    	 addLink(new WebLinks("Paandu 2005 DVD Jagapathi Babu","hPM2vIIyp4c","|"), db);
    	 addLink(new WebLinks("Paata 2007 DVD Madhusudhan","A6hg7npo_vY","|"), db);
    	 addLink(new WebLinks("Palanati Brahmanaidu 2003 DVD HQ Balakrishna","YCgJxUBt65Q","|"), db);
    	 addLink(new WebLinks("Pallakilo Pellikuthuru 2004 DVD HQ Goutham","Y9WuP4KcsEM","|"), db);
    	 addLink(new WebLinks("Panchatantram 2002 DVD HQ Kamal Hassan","zI1ppt6MDO8","|"), db);
    	 addLink(new WebLinks("Pandem 2005 DVD Jagapathi Babu","1Md7pVerH5I","|"), db);
    	 addLink(new WebLinks("Pandem Kodi 2006 DVD Vishaal","4ywwqNXYcvQ","|"), db);
    	 addLink(new WebLinks("Pandugadu 2009 DVD HQ Puneet Rajkumar","xxX41UIYWYA","|"), db);
    	 addLink(new WebLinks("Pandurangadu 2008 DVD HQ Balakrishna","fNfP48yc9js","|"), db);
    	 addLink(new WebLinks("Panjaram 2010 DVD HQ Kavya Madhavan","hrnoBW-JYTQ","|"), db);
    	 addLink(new WebLinks("Paravasam 2001 DVD HQ Madhavan","TWMWFSViVmE","|"), db);
    	 addLink(new WebLinks("Pardhu 2008 Raghava Lawrence","OmwI7jBqHBg","|"), db);
    	 addLink(new WebLinks("Party 2006 DVD HQ Sashank","1c3uoUeN8NE","|"), db);
    	 addLink(new WebLinks("Parugu 2008 DVD HQ Allu Arjun","ylx6aLz9md4","|"), db);
    	 addLink(new WebLinks("Pedababu 2004 DVD HQ Jagapathi Babu","i6BwUDS3VRY","|"), db);
    	 addLink(new WebLinks("Peddamma Talli 2003 DVD Sai Kumar","U6jZbqt_TEw","|"), db);
    	 addLink(new WebLinks("Peddarayudu Chinnarayudu 2005 DVD HQ Satya Raj","bq4SWD6PgEs","|"), db);
    	 addLink(new WebLinks("Pellaina Kothalo 2006 DVD HQ Jagapathi Babu","ZXGbggvQJ30","|"), db);
    	 addLink(new WebLinks("Pellaindi Kaani 2008 DVD HQ Allari Naresh","Tv74F32dMOA","|"), db);
    	 addLink(new WebLinks("Pellam Pichodu 2005 DVD HQ Rajendra Prasad","0Lvmu2BlHyA","|"), db);
    	 addLink(new WebLinks("Pellam Voorelithe 2003 DVD Srikanth","YPo4h1rgJOE","|"), db);
    	 addLink(new WebLinks("Pellamtho Panenti 2003 DVD HQ Venu","XNdoLAxU0d8","|"), db);
    	 addLink(new WebLinks("Pelli Kani Prasad 2008 Sivaji","DDTykVPUTlE","|"), db);
    	 addLink(new WebLinks("Pista 2009 DVD HQ Vishaal","pcHc2JR0s30","|"), db);
    	 addLink(new WebLinks("Please Naaku Pellaindi 2005 Raghu","UxVJrVHkiAw","|"), db);
    	 addLink(new WebLinks("Please Sorry Thanks 2006 DVD HQ Aditya Om","nxVbzbkq-O4","|"), db);
    	 addLink(new WebLinks("Pogaru 2006 DVD HQ Vishaal","ScF7gpGG9C4","|"), db);
    	 addLink(new WebLinks("Pokiri 2006 DVD HQ Mahesh Babu","D1FXu_SGMKs","|"), db);
    	 addLink(new WebLinks("Pokiri Pilla 2006 DVD Suresh Gopi","lxpGCsGKqUM","|"), db);
    	 addLink(new WebLinks("Police Ante Veedera 2007 DVD HQ Suresh Gopi","X9FiiLOItdM","|"), db);
    	 addLink(new WebLinks("Police Ante Veedera 2001 Sarath Kumar","qpKHFWBZbys","|"), db);
    	 addLink(new WebLinks("Police Karthavyam 2003 DVD HQ Arjun","1xWOONwYw1E","|"), db);
    	 addLink(new WebLinks("Police Police 2008 DVD HQ Srikanth","SHx-zWTjVKI","|"), db);
    	 addLink(new WebLinks("Police Sisters 2002 DVD HQ Roja","Cs2zeFDhDiQ","|"), db);
    	 addLink(new WebLinks("Political Rowdy 2005 DVD HQ Mohan Babu","UEXVl0Fuv-k","|"), db);
    	 addLink(new WebLinks("Poramboku 2007 DVD HQ Navdeep","Fm8O-Rzws4I","|"), db);
    	 addLink(new WebLinks("Pothe Poni 2006 DVD HQ Siva Balaji","gG0vcLp_bKk","|"), db);
    	 addLink(new WebLinks("Pournami 2006 DVD HQ Prabhas","xRt4t2HhW1k","|"), db);
    	 addLink(new WebLinks("Pourudu 2008 DVD HQ Sumanth","RYU_uuZ-IsM","|"), db);
    	 addLink(new WebLinks("Praanam 2003 Allari Naresh","_4StVz3vIuY","|"), db);
    	 addLink(new WebLinks("Pralaya Rudrudu 2007 DVD Upendra","RC6R4LgKeXY","|"), db);
    	 addLink(new WebLinks("Prathi Roju 2010 DVD HQ Bindu Madhavi","XPjwaW47Ck8","|"), db);
    	 addLink(new WebLinks("Pravarakhyudu 2009 DVD HQ Jagapathi Babu","R-RbjOfvLGk","|"), db);
    	 addLink(new WebLinks("Prayanam 2009 DVD HQ Manchu Manoj Kumar","YtVQp6-Nadc","|"), db);
    	 addLink(new WebLinks("Prayatnam 2005 DVD HQ Prudhvi Raj","HpjyFm7suWo","|"), db);
    	 addLink(new WebLinks("Prema Chadarangam 2004 DVD HQ Vishaal","DEbj6dsb7u4","|"), db);
    	 addLink(new WebLinks("Prema Geetham 2007 Dhirish","pInCbhLJYeQ","|"), db);
    	 addLink(new WebLinks("Prema Sandadi 2001 DVD HQ Srikanth","_6Vza61Ghik","|"), db);
    	 addLink(new WebLinks("Premabhishekam 2008 DVD HQ Venu Madhav","Eg_ofKQwz4M","|"), db);
    	 addLink(new WebLinks("Premalo Pavani Kalyan 2003 DVD HQ Deepak","-EylbuYWHsg","|"), db);
    	 addLink(new WebLinks("Premante Inthe 2006 Navdeep","olvpE4xFtoQ","|"), db);
    	 addLink(new WebLinks("Premante Maade 2004 DVD HQ Vinay Babu","FoQiDdBX07s","|"), db);
    	 addLink(new WebLinks("Premanuragam 2000 DVD Salman Khan","81J8x1zYszc","|"), db);
    	 addLink(new WebLinks("Prematho Raa! 2001 DVD HQ Venkatesh","tAQa4m3paK8","|"), db);
    	 addLink(new WebLinks("Premayanamaha 2004 DVD HQ Saandip","ucLVTW_gSlA","|"), db);
    	 addLink(new WebLinks("Premikulu 2005 DVD HQ Yuvaraj","rLONeAwgmwo","|"), db);
    	 addLink(new WebLinks("Preminchaaka 2006 DVD Santosh","lyPRjQQUJNc","|"), db);
    	 addLink(new WebLinks("Preminchu 2001 DVD HQ Laya","1dqG6eALoHo","|"), db);
    	 addLink(new WebLinks("Preminchukunnam Pelliki Randi 2004 DVD HQ Aditya Om","lW11YD2374Y","|"), db);
    	 addLink(new WebLinks("Premiste 2005 DVD HQ Bharat","sVU85bWIPWY","|"), db);
    	 addLink(new WebLinks("Premisthu 2009 DVD Srinath","MjWEFeR-OFE","|"), db);
    	 addLink(new WebLinks("Priyamaina Neeku 2001 DVD HQ Tarun","akJWfneE5Iw","|"), db);
    	 addLink(new WebLinks("Priyaraagalu 2000 DVD HQ Jagapathi Babu","zGW1km1NUO8","|"), db);
    	 addLink(new WebLinks("Priyasakhi 2006 DVD HQ Madhavan","ZraNJI8LvoA","|"), db);
    	 addLink(new WebLinks("Punnami Nagu 2009 DVD HQ Mumaith Khan","XjU64P2CiFM","|"), db);
    	 addLink(new WebLinks("Puttintiki Raa Chelli 2004 DVD HQ Arjun","qzClcL6Vhk4","|"), db);
    	 addLink(new WebLinks("Raagam 2006 Shabna Azmi","KKUPMJQ0csQ","|"), db);
    	 addLink(new WebLinks("Raana 2008 DVD HQ Arjun","wHEn79g43qQ","|"), db);
    	 addLink(new WebLinks("Raatri 2009 DVD HQ Jeeva.","ZRQuc0I0zRM","|"), db);
    	 addLink(new WebLinks("Raavee Na Cheliya 2001 Sai Kiran","Y7XQ5TQB7TQ","|"), db);
    	 addLink(new WebLinks("Radha Gopalam 2005 DVD HQ Srikanth","ZL-UzqpX5Fw","|"), db);
    	 addLink(new WebLinks("Ragada 2010 DVD HQ Nagarjuna","wV1kBeVhhco","|"), db);
    	 addLink(new WebLinks("Raghavan 2007 DVD HQ Kamal Hassan","KA-afHpMBiw","|"), db);
    	 addLink(new WebLinks("Raghuram 2008 DVD Vinod Kumar","i_6IgyRYL7U","|"), db);
    	 addLink(new WebLinks("Railway Gate 2007 DVD Prudhvi Raj","upbqur4wutk","|"), db);
    	 addLink(new WebLinks("Rainbow 2008 DVD Rahul","jHk47sg8zN4","|"), db);
    	 addLink(new WebLinks("Raju Bhai 2007 DVD HQ Manoj","W1qk-3U_JjU","|"), db);
    	 addLink(new WebLinks("Raju Maharaju 2009 DVD HQ Sarvanand","TM0FtRlPEAk","|"), db);
    	 addLink(new WebLinks("Raksha 2008 DVD HQ Jagapathi Babu","N_9rLX1A-84","|"), db);
    	 addLink(new WebLinks("Rakshana 2005 DVD HQ SriRam","STFUoc2shUc","|"), db);
    	 addLink(new WebLinks("Rakta Charitra 2010 DVD HQ Vivek Oberoi","jjXm4zyPUb4","|"), db);
    	 addLink(new WebLinks("Raktha Kanneeru 2004 DVD HQ Upendra","cXp6Yh75brg","|"), db);
    	 addLink(new WebLinks("Ram 2006 DVD HQ Nitin","xSaFnED16is","|"), db);
    	 addLink(new WebLinks("Rama Rama Krishna Krishna 2010 DVD HQ Ram","mPFjZoofR-I","|"), db);
    	 addLink(new WebLinks("Ramalayam Veedhilo 2006 DVD HQ Santosh Pavan","mrwmeUafZyI","|"), db);
    	 addLink(new WebLinks("Ramana 2002 DVD Rajendra Babu","mMe7WOZeO8Q","|"), db);
    	 addLink(new WebLinks("Ramaraju 2002 DVD HQ Murali","rpjWE7yTyjM","|"), db);
    	 addLink(new WebLinks("Ranam 2006 DVD Gopichand","eS3zcbEC-uQ","|"), db);
    	 addLink(new WebLinks("Ravanna 2000 DVD HQ Rajasekhar","rQLlYrMWCdY","|"), db);
    	 addLink(new WebLinks("Rayalaseema Ramanna Chowdary 2000 DVD HQ Mohan Babu","nKEO5vrr4nI","|"), db);
    	 addLink(new WebLinks("Ready 2008 Ram","c4XlDzSGaic","|"), db);
    	 addLink(new WebLinks("Rechipo 2009 DVD HQ Nitin","HYHRqmrR94s","|"), db);
    	 addLink(new WebLinks("Relax 2005 DVD HQ Rohan","OKDdetBlMlE","|"), db);
    	 addLink(new WebLinks("Rendu 2007 DVD Madhavan","BA6QIEMaoQs","|"), db);
    	 addLink(new WebLinks("Rhythm 2000 DVD HQ Arjun","mj5I6oVcz8s","|"), db);
    	 addLink(new WebLinks("Robo 2010 DVD HQ Rajnikanth","x0t8BBus1Xw","|"), db);
    	 addLink(new WebLinks("Room Mates 2006 DVD HQ Allari Naresh","EfrrIhoaIuk","|"), db);
    	 addLink(new WebLinks("Rs 999 Matrame 2009 DVD HQ Koutilya","ldPVmLZZKRQ","|"), db);
    	 addLink(new WebLinks("Sachin 2009 DVD HQ Vijay","NdCftq6hU5g","|"), db);
    	 addLink(new WebLinks("Sada Mee Sevalo 2005 DVD HQ Venu","hmCIcZ-BR6M","|"), db);
    	 addLink(new WebLinks("Sadhyam 2010 DVD HQ Jagapathi Babu","BP-jAOEKvWg","|"), db);
    	 addLink(new WebLinks("Sainikudu 2006 DVD HQ Mahesh Babu","i8jqyH8SsR4","|"), db);
    	 addLink(new WebLinks("Sakhi 2000 DVD HQ Madhavan","8NkXPpsC5Bk","|"), db);
    	 addLink(new WebLinks("Sakutumba Saparivara Sametamga 2000 DVD HQ Srikanth","b3gWGKN5VHg","|"), db);
    	 addLink(new WebLinks("Salute 2008 DVD HQ Vishaal","KVVgO28Gx-4","|"), db);
    	 addLink(new WebLinks("Samanyudu 2006 DVD HQ Jagapathi Babu","HRX7hua2jBs","|"), db);
    	 addLink(new WebLinks("Samara Simha 2007 DVD Upendra","zLMRaGopjeE","|"), db);
    	 addLink(new WebLinks("Samardhudu 2009 DVD HQ Krishnam Raju","Y0EF2tGNgDc","|"), db);
    	 addLink(new WebLinks("Samba 2004 DVD HQ Junior NTR","cx9TdlsoKj4","|"), db);
    	 addLink(new WebLinks("Sambaram 2003 DVD HQ Nitin","T_u3HWYs5_w","|"), db);
    	 addLink(new WebLinks("Sampangi 2001 Deepak","kG3DdEqwSG8","|"), db);
    	 addLink(new WebLinks("Samurai 2002 VikRam","9gykuzc2JBM","|"), db);
    	 addLink(new WebLinks("Sandade Sandadi 2002 DVD HQ Jagapathi Babu","Ryf8OJKbLAQ","|"), db);
    	 addLink(new WebLinks("Sandhadi 2008 Sashi Pavan","cjZajmqCTec","|"), db);
    	 addLink(new WebLinks("Sangamam 2008 DVD HQ Rohit Khurana","nU__BO6YZ_g","|"), db);
    	 addLink(new WebLinks("Santhi Sandesam 2004 DVD HQ Krishna","amxE9Qm55uQ","|"), db);
    	 addLink(new WebLinks("Santosham 2002 DVD HQ Nagarjuna","ojaN0_0Gtnw","|"), db);
    	 addLink(new WebLinks("Sarada Saradaga 2006 DVD HQ Srikanth","3FTM0EtS2nM","|"), db);
    	 addLink(new WebLinks("Saradaga Kasepu 2010 DVD HQ Allari Naresh","3nwrYX-AzjI","|"), db);
    	 addLink(new WebLinks("Sarai Veerraju 2009 DVD HQ Ajay","atKuQ9MGfPk","|"), db);
    	 addLink(new WebLinks("Sardar Papanna 2006 DVD Krishna","FGb_xrvGVM4","|"), db);
    	 addLink(new WebLinks("Saroja 2008 DVD HQ Vaibhav","D2e7KndYEgo","|"), db);
    	 addLink(new WebLinks("Sarvam DVD HQ Arya","6KZ1RjjjQyI","|"), db);
    	 addLink(new WebLinks("Sasirekha Parinayam 2009 DVD HQ Tarun","u_rJjdptKZc","|"), db);
    	 addLink(new WebLinks("Sathyam IPS 2008 Prithviraj","U336XPeUEZU","|"), db);
    	 addLink(new WebLinks("Sathyam IPS 2009 DVD Prudhvi Raj","U336XPeUEZU","|"), db);
    	 addLink(new WebLinks("Sathyame Sivam 2003 Kamal Hassan","yUEPVBIXFm4","|"), db);
    	 addLink(new WebLinks("Sidhu from Sikaakulam 2008 Allari Naresh","HrHRuCQrnYE","|"), db);
    	 addLink(new WebLinks("Simha 2010 DVD HQ Balakrishna","LEEvEM2F7Q8","|"), db);
    	 addLink(new WebLinks("Simha Baludu 2002 DVD HQ Arjun","8eDDW6v7pgY","|"), db);
    	 addLink(new WebLinks("Simha Raasi 2001 DVD HQ Rajasekhar","ppi4T9xhj9k","|"), db);
    	 addLink(new WebLinks("Simhadri 2003 DVD HQ Junior NTR","Nf4t_qjbb2U","|"), db);
    	 addLink(new WebLinks("Sindhooram 2002 DVD HQ Brahmaji","UveOcjnKo_E","|"), db);
    	 addLink(new WebLinks("Singamalai 2007 DVD HQ Arjun","dSNwcPQ-FR4","|"), db);
    	 addLink(new WebLinks("Sitakoka Chiluka 2006 DVD HQ Navdeep","qwDddi8fbnI","|"), db);
    	 addLink(new WebLinks("Siva 2005 DVD HQ Pradeep","Zn1Ojd2FydM","|"), db);
    	 addLink(new WebLinks("Siva Putrudu 2004 DVD HQ VikRam","QnhmGAAy_og","|"), db);
    	 addLink(new WebLinks("Siva Rama Raju 2002 DVD HQ Jagapathi Babu","hEnkEgC4Qa0","|"), db);
    	 addLink(new WebLinks("Sivakasi 2006 Arjun","D9-QkntaHxk","|"), db);
    	 addLink(new WebLinks("Sivanna 2001 DVD Sai Kumar","RKax8OoNLpE","|"), db);
    	 addLink(new WebLinks("Slokam 2005 DVD HQ Sai Kumar","CHlwU1d0uKQ","|"), db);
    	 addLink(new WebLinks("SMS Mem Vayasuku Vacham 2000 DVD HQ Abhinayasri","6lMRFHiuMO0","|"), db);
    	 addLink(new WebLinks("Sneham 2010 Naren","1Dq5FGJP_8A","|"), db);
    	 addLink(new WebLinks("Snehamante Idera 2001 DVD HQ Nagarjuna","wZ5kZJwLOFg","|"), db);
    	 addLink(new WebLinks("Snehithudaa 2001 DVD HQ Shivaji","9uUJnfYM5go","|"), db);
    	 addLink(new WebLinks("Snehituda 2009 DVD HQ Nani","spp8t_QbnNQ","|"), db);
    	 addLink(new WebLinks("Soggadi Saradalu 2004 Santosh Pavan","VP88Zt3hOOA","|"), db);
    	 addLink(new WebLinks("Soggadu 2005 DVD HQ Tarun","SzRGNbWpXfM","|"), db);
    	 addLink(new WebLinks("Somberi 2008 DVD HQ Ali","wCaeYbyS_C8","|"), db);
    	 addLink(new WebLinks("Something Special 2006 DVD Samrat","OYsErATUO8A","|"), db);
    	 addLink(new WebLinks("Sontha Vooru 2009 DVD HQ Raja","r9dw2ObGsKw","|"), db);
    	 addLink(new WebLinks("Sontham 2002 DVD Aryan Rajesh","kDro5bBnZkE","|"), db);
    	 addLink(new WebLinks("Sorry Naaku Pellaindi 2004 DVD Raghu","pKjhOmqNXV4","|"), db);
    	 addLink(new WebLinks("Sowryam 2008 DVD HQ Gopichand","LkISwEq6l_c","|"), db);
    	 addLink(new WebLinks("Sravana Masam 2005 DVD HQ Krishna","Q7VG1pwAc4c","|"), db);
    	 addLink(new WebLinks("Sreeramachandrulu 2004 DVD HQ Rajendra Prasad","wiR_iiwFm_Y","|"), db);
    	 addLink(new WebLinks("Sri 2005 DVD HQ Manchu Manoj Kumar","6YRKH6Pp4SU","|"), db);
    	 addLink(new WebLinks("Sri Anjaneyam 2004 DVD HQ Nitin","E2vBO64G0vo","|"), db);
    	 addLink(new WebLinks("Sri Krishna 2006 2006 DVD HQ Srikanth","yWm50ApSh90","|"), db);
    	 addLink(new WebLinks("Sri Manjunatha 2001 DVD HQ Chiranjeevi","0N1a0AXjwZE","|"), db);
    	 addLink(new WebLinks("Sri Ramadasu 2006 DVD HQ Nagarjuna","Gay60WDF58w","|"), db);
    	 addLink(new WebLinks("Sri Sai Mahima 2000 DVD HQ Eeswari Rao","hx2pzvkzVrA","|"), db);
    	 addLink(new WebLinks("Sri Sailam 2009 DVD HQ Srihari","r8nZwbVaR3U","|"), db);
    	 addLink(new WebLinks("Sri Satyanarayana Swamy 2007 DVD HQ Suman","o4toAhCMqEo","|"), db);
    	 addLink(new WebLinks("Stalin 2006 DVD HQ Chiranjeevi","Z9qoFMaDeSI","|"), db);
    	 addLink(new WebLinks("Sthreela Kosam 2010 DVD HQ Khushboo","1tt_SFvSwF4","|"), db);
    	 addLink(new WebLinks("Student 2007 DVD HQ Nishanth","qR6fRW88uc0","|"), db);
    	 addLink(new WebLinks("Student No. 1 2001 DVD Junior NTR","5HoJf9Qq4Xo","|"), db);
    	 addLink(new WebLinks("Stuvartupuram 2009 DVD HQ Mammootty","YERjhpJUaWc","|"), db);
    	 addLink(new WebLinks("Style 2006 DVD HQ Prabhu Deva","1mkh9i8x_fA","|"), db);
    	 addLink(new WebLinks("Subbu 2001 DVD HQ Junior NTR","17yKXgTz0IM","|"), db);
    	 addLink(new WebLinks("Subhamasthu 2005 DVD HQ Jagapathi Babu","BpvbKMJSvTM","|"), db);
    	 addLink(new WebLinks("Subhapradam 2010 DVD HQ Allari Naresh","HefQOOGSwwo","|"), db);
    	 addLink(new WebLinks("Subhash Chandra Bose 2005 DVD HQ Venkatesh","cGwZ4_iMaWk","|"), db);
    	 addLink(new WebLinks("Sundarakanda 2008 DVD HQ Charmi","QUIsfM5x3Tg","|"), db);
    	 addLink(new WebLinks("Sundarangudu 2004 DVD HQ Surya","LzQQvSHj69w","|"), db);
    	 addLink(new WebLinks("Sundaraniki Tondarekkuva 2006 DVD HQ Baladitya","TZoHyvnGmzQ","|"), db);
    	 addLink(new WebLinks("Super 2005 DVD HQ Nagarjuna","FssaYlp_V3Y","|"), db);
    	 addLink(new WebLinks("Super Cowboy 2010 DVD HQ Lawrence Raghavendra","zIShDMvKmb4","|"), db);
    	 addLink(new WebLinks("Surya SO Krishnan 2008 DVD HQ Surya","LaR_dv3Wqko","|"), db);
    	 addLink(new WebLinks("Suryavamsham 2004 DVD HQ Venkatesh","lCvnc3AGbDs","|"), db);
    	 addLink(new WebLinks("Swagatham 2008 DVD HQ Jagapathi Babu","1HokOFHyd1g","|"), db);
    	 addLink(new WebLinks("Swamy 2004 DVD HQ Hari Krishna","NbeZ0SxXOxQ","|"), db);
    	 addLink(new WebLinks("Swamy IPS 2008 DVD HQ VikRam","G60VcFaZXvE","|"), db);
    	 addLink(new WebLinks("Swarabhishekam 2004 DVD HQ K Viswanath","-Kdh0knH-EA","|"), db);
    	 addLink(new WebLinks("Swetha Naagu 2004 DVD HQ Soundarya","_CqNyYfcreo","|"), db);
    	 addLink(new WebLinks("Sye 2004 DVD HQ Nitin","1_T9JgUmuPc","|"), db);
    	 addLink(new WebLinks("Sye Aata 2010 DVD HQ Charmi","cdL3AvofcX4","|"), db);
    	 addLink(new WebLinks("Taarak 2003 DVD HQ NT Rathnaa","KVmYg1DL9TM","|"), db);
    	 addLink(new WebLinks("Tagore 2003 DVD Chiranjeevi","sDIQrNyPCzM","|"), db);
    	 addLink(new WebLinks("Takkari 2007 DVD Nitin","MaVG34FWSqY","|"), db);
    	 addLink(new WebLinks("Takkari Donga 2002 DVD HQ Mahesh Babu","TeW4llZBUKE","|"), db);
    	 addLink(new WebLinks("Tamasha Chuddam Randi 2008 DVD HQ Venu Chandra","KdFh-e2x58M","|"), db);
    	 addLink(new WebLinks("Target 2008 DVD HQ Mumaith Khan","QVrPJTH8l3c","|"), db);
    	 addLink(new WebLinks("Tata Birla Madhyalo Laila 2006 DVD HQ Shivaji","I-bLZcnApGU","|"), db);
    	 addLink(new WebLinks("Tenali 2000 DVD HQ Kamal Hassan","t0WyUqcnKsM","|"), db);
    	 addLink(new WebLinks("Thakita Thakita 2010 DVD HQ Harshavardhan Rane","FAOtKZ51GFg","|"), db);
    	 addLink(new WebLinks("Thank You Subba Rao 2001 DVD Srihari","QMuOlsD6PB4","|"), db);
    	 addLink(new WebLinks("Thegimpu 2006 Sreeman","Wu7FrdHktMw","|"), db);
    	 addLink(new WebLinks("Thinnama Padukunnama Tellarinda 2007 DVD HQ Ali","wcSjXQHvimM","|"), db);
    	 addLink(new WebLinks("Tholi Valapu 2001 Gopichand","YxdLZEwDADk","|"), db);
    	 addLink(new WebLinks("Thoota 2009 DVD HQ Jeevan","sxwEPWbiXaY","|"), db);
    	 addLink(new WebLinks("Thotti Gang 2002 DVD HQ Allari Naresh","XyweHWE-y5E","|"), db);
    	 addLink(new WebLinks("Tiger Harischandra Prasad 2003 Hari Krishna","S1fa9YyFVqs","|"), db);
    	 addLink(new WebLinks("Tirumala Tirupati Venkatesa 2000 DVD HQ Srikanth","oAQCcmIyOtk","|"), db);
    	 addLink(new WebLinks("Toss 2007 DVD HQ Upendra","dzcwb3uY9aQ","|"), db);
    	 addLink(new WebLinks("Tulasi 2007 DVD HQ Venkatesh","EmN7J5m_Ohc","|"), db);
    	 addLink(new WebLinks("U & I 2010 DVD HQ Rohan","BiVhQ3MXFe8","|"), db);
    	 addLink(new WebLinks("Udatha Udatha Uooch 2010 DVD HQ Chitram Seenu","ZjDfzWP8Q3I","|"), db);
    	 addLink(new WebLinks("Ullasamga Utsahamga 2008 DVD HQ Yaso Sagar","VUKpewYO9xo","|"), db);
    	 addLink(new WebLinks("Uncle 2000 DVD Tarun","vyp4EzyZLck","|"), db);
    	 addLink(new WebLinks("Vaana 2008 DVD Vinay","6fYj4kwSBbA","|"), db);
    	 addLink(new WebLinks("Vallabha 2006 DVD HQ Simbu","Uf8Bhb1gexE","|"), db);
    	 addLink(new WebLinks("Vallidari Vayasu Padahare 2008 DVD HQ Tharun Chandra","AmcfY2JCzNk","|"), db);
    	 addLink(new WebLinks("Vamsi 2000 DVD HQ Mahesh Babu","LR7uPPSOmnc","|"), db);
    	 addLink(new WebLinks("Vamsoddarakudu 2000 DVD Balakrishna","eRUbL_qSEQA","|"), db);
    	 addLink(new WebLinks("Vandemataram 2001 DVD Vijayashanthi","LnrnyDtoHec","|"), db);
    	 addLink(new WebLinks("Varasudochadu 2010 DVD HQ Bharat","MUR2wjmzD_0","|"), db);
    	 addLink(new WebLinks("Varudu 2010 DVD HQ Allu Arjun","ZyBJte1oy7A","|"), db);
    	 addLink(new WebLinks("Vasantham 2003 DVD HQ Venkatesh","9CeqGk2hVFU","|"), db);
    	 addLink(new WebLinks("Vasthad 2004 DVD HQ Thriller Manju","LmYxzaAQ5Lk","|"), db);
    	 addLink(new WebLinks("Vasu 2002 DVD HQ Venkatesh","A_7WEyJ0DU8","|"), db);
    	 addLink(new WebLinks("Vayasu Pilichindi 2004 DVD HQ Ramya Krishna","bcW0vngACs0","|"), db);
    	 addLink(new WebLinks("Veedokkade 2009 DVD HQ Surya","qlyCfEpub3Y","|"), db);
    	 addLink(new WebLinks("Veedu Mamoolodu Kadu 2008 DVD HQ Rishi","hmqfX9V77WY","|"), db);
    	 addLink(new WebLinks("Veera Telangaana 2010 DVD HQ R Narayana Murthy","tC54DcJyPZc","|"), db);
    	 addLink(new WebLinks("Veerabhadra 2006 DVD HQ Balakrishna","2N8fFzuz7sU","|"), db);
    	 addLink(new WebLinks("Veeri Veeri Gummadi Pandu 2005 DVD HQ Ali","CSPceOFIk78","|"), db);
    	 addLink(new WebLinks("Venkat Tho Alivelu 2005 DVD HQ Dilip","LBe1-IQ0b7c","|"), db);
    	 addLink(new WebLinks("Venky 2004 DVD HQ Ravi Teja","wzlwD3N-v8c","|"), db);
    	 addLink(new WebLinks("Vennela 2005 DVD HQ Raja","SrYEA_GjM5g","|"), db);
    	 addLink(new WebLinks("Veta 2008 DVD Baladitya","ZYoLGG6EjeM","|"), db);
    	 addLink(new WebLinks("Vignesh 2004 DVD HQ Bhagavan","Qw_Gp7_ou1g","|"), db);
    	 addLink(new WebLinks("Vijay IPS 2008 DVD HQ Sumanth","Vkg52Oc9w1s","|"), db);
    	 addLink(new WebLinks("Vijayam 2003 DVD Raja","qGgpzDTnHks","|"), db);
    	 addLink(new WebLinks("Aavida Maa Aavide 1998 DVD HQ Nagarjuna","D7c0zKMk3Kk","|"), db);
    	 addLink(new WebLinks("Abbai Gari Pelli 1997 DVD HQ Suman","9mo4LBbkHxc","|"), db);
    	 addLink(new WebLinks("Adavallaku Matrame 1996 DVD HQ Revathi","F6aqJk8m7tM","|"), db);
    	 addLink(new WebLinks("Aditya 369 1991 DVD HQ Balakrishna","oYziuMU2Ubs","|"), db);
    	 addLink(new WebLinks("Agreement 1992 DVD Nagendra Babu","e-JsRWUGfBQ","|"), db);
    	 addLink(new WebLinks("Ahankari 1992 DVD HQ Rajasekhar","-R3ZqXHWnx8","|"), db);
    	 addLink(new WebLinks("Akka Mogudu 1992 DVD Rajasekhar","kxDVqvqGKxw","|"), db);
    	 addLink(new WebLinks("Akkada Ammayi Ikkada Abbayi 1996 DVD Pavan Kalyan","aE7JzgAN_50","|"), db);
    	 addLink(new WebLinks("Alexander 1992 DVD HQ Suman","jRJ0JJHoCRg","|"), db);
    	 addLink(new WebLinks("Alibaba Aradajanu Dongalu 1994 DVD HQ Rajendra Prasad","Yj5gKkLFS9w","|"), db);
    	 addLink(new WebLinks("All Rounder 1998 DVD Rajendra Prasad","cwUU8rVZSQs","|"), db);
    	 addLink(new WebLinks("Allari Mogudu Anumanam Pellam 1996 Kamal Hassan","jTHQo08vIA0","|"), db);
    	 addLink(new WebLinks("Allari Pellam 1998 DVD HQ Shivaji Raja","UJ7Co3R6ilA","|"), db);
    	 addLink(new WebLinks("Allari Priyudu 1993 DVD HQ Rajasekhar","t6s3I1VSEs4","|"), db);
    	 addLink(new WebLinks("Alluda Mazaaka 1995 DVD HQ Chiranjeevi","RMu62yhTalE","|"), db);
    	 addLink(new WebLinks("Amma Donga 1995 DVD HQ Krishna","8cm62atkZqo","|"), db);
    	 addLink(new WebLinks("Amma Naa Kodala 1993 DVD HQ Vinod Kumar","9twyYNOpMxk","|"), db);
    	 addLink(new WebLinks("Amma Naa Koduku 1993 Raghu","LQxlk9gi2cs","|"), db);
    	 addLink(new WebLinks("Amma Rajinama 1991 DVD HQ Brahmanandam.","iG1dJxAzIco","|"), db);
    	 addLink(new WebLinks("Ammaleni Puttillu 1995 DVD Ooha","ctScUsU4Rbk","|"), db);
    	 addLink(new WebLinks("Amrutha Varshini 1997 DVD HQ Ramesh Arvind","A8xP0aX1_7k","|"), db);
    	 addLink(new WebLinks("Anaganaga Oka Ammai 1999 DVD Srikanth","DzFeYRXDoig","|"), db);
    	 addLink(new WebLinks("Anaganaga Oka Roju 1997 DVD HQ J D Chakravarthi","R8GIOJJduFk","|"), db);
    	 addLink(new WebLinks("Andhra Vaibhavam 1999 DVD Murali Mohan","CYPdiBeD0Aw","|"), db);
    	 addLink(new WebLinks("Anjali 1990 DVD HQ Raghu Varan","x-41JA-W5KM","|"), db);
    	 addLink(new WebLinks("Ankusham 1990 DVD HQ Rajasekhar","23rQ_bB_bGk","|"), db);
    	 addLink(new WebLinks("Annamayya 1997 Nagarjuna","tQWF56DAHgg","|"), db);
    	 addLink(new WebLinks("Antam 1992 DVD HQ Nagarjuna","piHZE9lKqQg","|"), db);
    	 addLink(new WebLinks("Anthahpuram 1998 DVD HQ Soundarya","d_98VsHF8XA","|"), db);
    	 addLink(new WebLinks("Apadbhandavudu 1992 DVD HQ Chiranjeevi","lUH1HkOulbQ","|"), db);
    	 addLink(new WebLinks("Appula Appa Rao 1991 DVD HQ Rajendra Prasad","Dr7leB4x2_8","|"), db);
    	 addLink(new WebLinks("April 1st Vidudhala 1991 DVD HQ Rajendra Prasad","PJgPdPQyZ4o","|"), db);
    	 addLink(new WebLinks("Arunachalam 1997 DVD HQ Rajnikanth","PCkob0FCzW8","|"), db);
    	 addLink(new WebLinks("Arundhati 1999 DVD HQ Soundarya","IIcE4jahhRg","|"), db);
    	 addLink(new WebLinks("Ashwamedham 1992 DVD HQ Balakrishna","7isUcusRt5M","|"), db);
    	 addLink(new WebLinks("Attaku Koduku Mamaku Alludu 1993 DVD Vinod Kumar","CQqkneugGNk","|"), db);
    	 addLink(new WebLinks("Ayanagaru 1998 Srikanth","AKjuxnTjhsQ","|"), db);
    	 addLink(new WebLinks("Ayanaku Iddaru 1995 DVD HQ Jagapathi Babu","uxPdL4jz51s","|"), db);
    	 addLink(new WebLinks("Badili 1995 DVD HQ Anand","89zgDWst8ew","|"), db);
    	 addLink(new WebLinks("Bala Ramayanam 1996 DVD Junior NTR","0tL2omnJ4PM","|"), db);
    	 addLink(new WebLinks("Bala Veerulu 1999 Babu Mohan","E0QhSTiXlGg","|"), db);
    	 addLink(new WebLinks("Balarama Krishnulu 1992 DVD HQ Jagapathi Babu","pBxIENP5Qo4","|"), db);
    	 addLink(new WebLinks("Bamma Maata Bangaru Baata 1990 DVD HQ Rajendra Prasad","Qx9sfpYfilI","|"), db);
    	 addLink(new WebLinks("Bavagaru Bagunnara 1998 DVD HQ Chiranjeevi","LwertoUDtNk","|"), db);
    	 addLink(new WebLinks("Bhairava Dweepam 1994 DVD HQ Balakrishna","QMpiKYIGY1E","|"), db);
    	 addLink(new WebLinks("Bhale Mavayya 1994 DVD HQ Suman","I2yYIIaNaig","|"), db);
    	 addLink(new WebLinks("Bhale Pellam 1994 DVD HQ Jagapathi Babu","XVqqDHN0scQ","|"), db);
    	 addLink(new WebLinks("Bhamane Satya Bhamane 1996 DVD HQ Kamal Hassan","iSLGUjuYE2M","|"), db);
    	 addLink(new WebLinks("Big Boss 1995 DVD HQ Chiranjeevi","4shubYdAvdY","|"), db);
    	 addLink(new WebLinks("Bobbili Raja 1990 DVD HQ Venkatesh","wX1HYVWXfK4","|"), db);
    	 addLink(new WebLinks("Brahmachari Mogudu 1994 DVD HQ Rajendra Prasad","evyZz1VDEVA","|"), db);
    	 addLink(new WebLinks("Brahmarishi Vishwamitra 1991 DVD HQ NTR","1VBOxgNNC6s","|"), db);
    	 addLink(new WebLinks("Brundavanam 1992 DVD HQ Rajendra Prasad","V-liCbyK2M4","|"), db);
    	 addLink(new WebLinks("Captain Prabhakar 1992 DVD HQ Vijaykanth","xlrR3_tSeRc","|"), db);
    	 addLink(new WebLinks("Chaitanya 1991 DVD Nagarjuna","021Bq9SUy6Q","|"), db);
    	 addLink(new WebLinks("Chamanthi 1992 DVD HQ Bhanumathi RamaKrishna","xSnbZ_IOKBg","|"), db);
    	 addLink(new WebLinks("Chandralekha 1998 DVD HQ Nagarjuna","Ph_3pkaSFlg","|"), db);
    	 addLink(new WebLinks("China Rayudu 1992 DVD HQ Venkatesh","nuQOIGg0JVA","|"), db);
    	 addLink(new WebLinks("Chinna Alludu 1990 DVD HQ Suman","oMDSye_812M","|"), db);
    	 addLink(new WebLinks("Chinnabbayi 1997 Venkatesh","kO3HQQyTGEo","|"), db);
    	 addLink(new WebLinks("Chitram Bhalare Vichitram 1992 DVD HQ Naresh","BsthVDF3fZ0","|"), db);
    	 addLink(new WebLinks("Choodalani Vundi 1998 DVD HQ Chiranjeevi","coeq3djtPlk","|"), db);
    	 addLink(new WebLinks("Collector Garu 1997 DVD HQ Mohan Babu","9aJ7kKFwyFs","|"), db);
    	 addLink(new WebLinks("Coolie No. 1 1991 DVD HQ Venkatesh","9JeCH14TznA","|"), db);
    	 addLink(new WebLinks("Dabbu Bhale Jabbu 1992 DVD HQ Raj Kumar","Qvv7EGQVQ6Q","|"), db);
    	 addLink(new WebLinks("Dalapathi 1992 DVD HQ Rajnikanth","xAgnM81WQnc","|"), db);
    	 addLink(new WebLinks("Deergha Sumangali Bhava 1998 DVD HQ Rajasekhar","IWtHZvx6OXk","|"), db);
    	 addLink(new WebLinks("Devi 1999 DVD HQ Prema","IhoabvEFIRg","|"), db);
    	 addLink(new WebLinks("Devudu 1997 DVD Balakrishna","iaz5_tcqbv0","|"), db);
    	 addLink(new WebLinks("Deyyam 1996 DVD HQ J D Chakravarthi","mk3m3u2FwSg","|"), db);
    	 addLink(new WebLinks("Dharma Chakram 1996 DVD HQ Venkatesh","PpzBsMjA8A8","|"), db);
    	 addLink(new WebLinks("Dharma Kshetram 1992 DVD HQ Balakrishna","oiheHsU1BRA","|"), db);
    	 addLink(new WebLinks("Doctor Bhavani 1990 DVD HQ Sarada","Uxrz-5aondc","|"), db);
    	 addLink(new WebLinks("Donga Rascal 1994 DVD HQ Srikanth","lP70PABtp3Y","|"), db);
    	 addLink(new WebLinks("Doshi Nirdoshi 1990 DVD Sobhan Babu","FFZw9wKJ9nI","|"), db);
    	 addLink(new WebLinks("Edurinti Mogudu Pakkinti Pellam 1991 DVD HQ Rajendra Prasad","Cgi4ua_4b34","|"), db);
    	 addLink(new WebLinks("Egire Pavurama 1997 DVD HQ J D Chakravarthi","0U3_tenVLQ8","|"), db);
    	 addLink(new WebLinks("Evandi Pelli Chesukondi 1997 DVD HQ Suman","xH8rlGvwlNA","|"), db);
    	 addLink(new WebLinks("Fifty Fifty 1997 DVD HQ Sanjay Dutt","-7b3ybMuqrU","|"), db);
    	 addLink(new WebLinks("Gaayam 1993 DVD HQ Jagapathi Babu","UYb6M0h9Bis","|"), db);
    	 addLink(new WebLinks("Gandeevam 1994 Balakrishna","Q6FqAiYgUfE","|"), db);
    	 addLink(new WebLinks("Ganesh 1998 DVD HQ Venkatesh","YXU2PiLuRpU","|"), db);
    	 addLink(new WebLinks("Gang Leader 1991 DVD Chiranjeevi","i0A6bcQ7zE4","|"), db);
    	 addLink(new WebLinks("Gentleman 1993 DVD HQ Arjun","QXyYCJDWloQ","|"), db);
    	 addLink(new WebLinks("Gharaana Mogudu 1992 DVD HQ Chiranjeevi","gWQBxg3kxJ4","|"), db);
    	 addLink(new WebLinks("Gharana Bullodu 1995 DVD HQ Nagarjuna","EpLY51Jvd9k","|"), db);
    	 addLink(new WebLinks("Gillikajjalu 1998 DVD Srikanth","L63JZsiBtk8","|"), db);
    	 addLink(new WebLinks("Gokulamlo Seetha 1997 DVD HQ Pavan Kalyan","jSZEGWV8ndg","|"), db);
    	 addLink(new WebLinks("Golmaal Govindam 1992 DVD HQ Rajendra Prasad","1h4NA66xTp8","|"), db);
    	 addLink(new WebLinks("Goonda Police 1998 DVD HQ Ramki","qx8TA2-NiEk","|"), db);
    	 addLink(new WebLinks("Govinda Govinda 1994 DVD HQ Nagarjuna","ScWhvm3FqCU","|"), db);
    	 addLink(new WebLinks("Gowramma 1992 DVD HQ Nizhalgal Ravi","TKND6pquJuE","|"), db);
    	 addLink(new WebLinks("Gowramma Nee Mogudevaramma 1990 Mohan","yFa6IXN_ydk","|"), db);
    	 addLink(new WebLinks("Gulabi 1996 DVD HQ J D Chakravarthi","Kvlp8F3B23A","|"), db);
    	 addLink(new WebLinks("Harishchandra 1999 DVD HQ J D Chakravarthi","_l1oHFcjAno","|"), db);
    	 addLink(new WebLinks("Hello Alludu 1994 DVD HQ Suman","JTM1hrKIudA","|"), db);
    	 addLink(new WebLinks("Hello Brother 1994 DVD HQ Nagarjuna","g8EunpSvpek","|"), db);
    	 addLink(new WebLinks("Hitler 1997 DVD HQ Chiranjeevi","ivUZZBXAcoE","|"), db);
    	 addLink(new WebLinks("Iddaru Mitrulu 1999 DVD HQ Chiranjeevi","kTu1FLK1iDI","|"), db);
    	 addLink(new WebLinks("Iddaru Pellala Muddula Police 1991 DVD HQ Rajendra Prasad","HztYz7a2ps0","|"), db);
    	 addLink(new WebLinks("Idem Pellam Baboi 1990 DVD Rajendra Prasad","jir0S6TIhyU","|"), db);
    	 addLink(new WebLinks("Inspector Ashwini 1993 DVD HQ Ashwini Nachappa","KuSag6zeQ3U","|"), db);
    	 addLink(new WebLinks("Intinta Deepavali 1990 DVD HQ Chandra Mohan","3LCuX_C2j4o","|"), db);
    	 addLink(new WebLinks("Intlo Illalu Vantintlo Priyuralu 1996 DVD HQ Venkatesh","wD0-fQqIpoI","|"), db);
    	 addLink(new WebLinks("Jaitra Yatra 1991 DVD HQ Nagarjuna","w69TqUgP2Os","|"), db);
    	 addLink(new WebLinks("Jamba Lakidi Pamba 1993 DVD HQ Naresh","uQV4iI4DB3A","|"), db);
    	 addLink(new WebLinks("Jayammu Nischayammu Raa 1990 DVD HQ Rajendra Prasad","PkqJLkXuCQ8","|"), db);
    	 addLink(new WebLinks("Jeans 1998 DVD HQ Prasanth","GXao7KtgGqw","|"), db);
    	 addLink(new WebLinks("Mama Alludu 1990 DVD HQ Dasari Narayana Rao","tdYqIrwKlC8","|"), db);
    	 addLink(new WebLinks("Mama Bagunnavaa 1997 DVD Naresh","IZ150I-l0Ag","|"), db);
    	 addLink(new WebLinks("Manasichi Choodu 1998 DVD Vadde Naveen","KL97EecpIRI","|"), db);
    	 addLink(new WebLinks("Manavarali Pelli 1993 DVD Harish","gtle8ctDq9U","|"), db);
    	 addLink(new WebLinks("Mayadari Mosagadu 1999 DVD HQ Vinod Kumar","zpqIhbqjsrc","|"), db);
    	 addLink(new WebLinks("Mayalodu 1993 DVD HQ Rajendra Prasad","bebrZvJ035E","|"), db);
    	 addLink(new WebLinks("Mayor Chakravarthy 1996 Sarath Kumar","GTDSQ8068nw","|"), db);
    	 addLink(new WebLinks("Mechanic Alludu 1993 DVD Chiranjeevi","ZbsZapFgsG8","|"), db);
    	 addLink(new WebLinks("Merupu Kalalu 1997 DVD Aravind Swamy","o_H-vquI2cM","|"), db);
    	 addLink(new WebLinks("Mondi Mogudu Penki Pellam 1992 DVD HQ Suman","UjtpMHzaQoM","|"), db);
    	 addLink(new WebLinks("Money 1993 DVD HQ J D Chakravarthi","Gz84TP35tY4","|"), db);
    	 addLink(new WebLinks("Money Money 1995 DVD Jagapathi Babu","-v4wDTq9WFM","|"), db);
    	 addLink(new WebLinks("Moratodu Naa Mogudu 1992 Rajasekhar","z5JfDrBWq5g","|"), db);
    	 addLink(new WebLinks("Mr Pellam 1993 DVD HQ Rajendra Prasad","zqpIARLDzqw","|"), db);
    	 addLink(new WebLinks("Muddula Mogudu 1997 Balakrishna","Jdj6phbYZEM","|"), db);
    	 addLink(new WebLinks("Muttu 1996 DVD Rajnikanth","ds8Z5zZbmXM","|"), db);
    	 addLink(new WebLinks("Naidu Gari Kutumbam 1996 DVD HQ Krishnam Raju","ZgAMHg3FKdM","|"), db);
    	 addLink(new WebLinks("Narasimha 1999 DVD HQ Rajnikanth","p-0pZiMoHSY","|"), db);
    	 addLink(new WebLinks("Nari Nari Naduma Murari 1990 DVD HQ Balakrishna","b3321EU9kpw","|"), db);
    	 addLink(new WebLinks("Navayugam 1990 DVD HQ Rajendra Prasad","ziXZlKEnrLg","|"), db);
    	 addLink(new WebLinks("Navvandi Lovvandi 1998 DVD HQ Kamal Hassan","sh2jggSK5kA","|"), db);
    	 addLink(new WebLinks("Nee Prema Kosam 1994 DVD Vineeth","MU5lFEMlndk","|"), db);
    	 addLink(new WebLinks("Neekosam 1990 DVD HQ Ravi Teja","DQFMEBgbUn4","|"), db);
    	 addLink(new WebLinks("Neti Dowrjanyam 1990 Vinod Kumar","Kv7zqFWeFuk","|"), db);
    	 addLink(new WebLinks("Neti Siddhartha 1990 DVD HQ Nagarjuna","SLUbJtlBtHM","|"), db);
    	 addLink(new WebLinks("Ninne Pelladatha 1996 DVD HQ Nagarjuna","3TNmQ1XM2fM","|"), db);
    	 addLink(new WebLinks("Nippu Ravva 1993 Balakrishna","UZPzFIRV6HY","|"), db);
    	 addLink(new WebLinks("Nirnayam 1991 DVD HQ Nagarjuna","Z_XfntrXaRo","|"), db);
    	 addLink(new WebLinks("Number One 1994 DVD HQ Krishna","rG9_1qMN81o","|"), db);
    	 addLink(new WebLinks("O Panaipothundi Baboo..! 1998 DVD HQ Suresh","r9kORy38vyk","|"), db);
    	 addLink(new WebLinks("Oke Okkadu 1999 DVD HQ Arjun","yCNXW7_1czk","|"), db);
    	 addLink(new WebLinks("Omkaram 1997 DVD HQ Rajasekhar","RO-KpglRsFQ","|"), db);
    	 addLink(new WebLinks("Ooha 1996 DVD HQ Vikram","WG3iiMklutc","|"), db);
    	 addLink(new WebLinks("Pachani Samsaram 1992 DVD Krishna","xXOXOTae0e0","|"), db);
    	 addLink(new WebLinks("Palleturi Mogudu 1994 DVD HQ Suman","2OIoyYeHd6w","|"), db);
    	 addLink(new WebLinks("Palleturi Pellam 1991 DVD HQ Sarath Babu","AgvVlSp73yo","|"), db);
    	 addLink(new WebLinks("Palnati Pourusham 1994 DVD HQ Krishnam Raju","1FZ5HPTwIUw","|"), db);
    	 addLink(new WebLinks("Pandaga 1998 DVD HQ Srikanth","1vIdP4q_uO8","|"), db);
    	 addLink(new WebLinks("Panthulu Gari Pellam 1992 DVD HQ K Bhagyaraj","gGAlCytThOM","|"), db);
    	 addLink(new WebLinks("Paruvu Prathista 1993 DVD Suman","_uYHIZBFrN0","|"), db);
    	 addLink(new WebLinks("Parvathalu Panakalu 1992 DVD HQ Dasari Narayana Rao","CfTx3dkEfHY","|"), db);
    	 addLink(new WebLinks("Pavithra Bandham 1996 DVD HQ Venkatesh","thenBj1dnUQ","|"), db);
    	 addLink(new WebLinks("Pavitra Prema 1998 DVD HQ Balakrishna","vEkW9ln6bLA","|"), db);
    	 addLink(new WebLinks("Pedda Annayya 1997 DVD HQ Balakrishna","9M_MkKy3rTs","|"), db);
    	 addLink(new WebLinks("Pedda Rayudu 1995 DVD HQ Mohan Babu","JCe4U8GbAjo","|"), db);
    	 addLink(new WebLinks("Peddamanushulu 1999 DVD Suman","2boEg8NKx-U","|"), db);
    	 addLink(new WebLinks("Peddarikam 1992 DVD HQ Jagapathi Babu","_AtiqTO_Syk","|"), db);
    	 addLink(new WebLinks("Pekata Paparao 1993 DVD HQ Rajendra Prasad","llFoKzztbZY","|"), db);
    	 addLink(new WebLinks("Pelli Pandiri 1998 DVD HQ Jagapathi Babu","5DWoUqpoQeU","|"), db);
    	 addLink(new WebLinks("Pelli Pustakam 1991 DVD HQ Rajendra Prasad","Ceh9tJ9aogw","|"), db);
    	 addLink(new WebLinks("Pellichesukundam 1997 DVD HQ Venkatesh","YPTOzi4ix2s","|"), db);
    	 addLink(new WebLinks("Pilla Nachindi 1999 DVD Srikanth","Z_g762MWaU0","|"), db);
    	 addLink(new WebLinks("Pokiri Raja 1994 DVD HQ Venkatesh","SwC04GV2Nmc","|"), db);
    	 addLink(new WebLinks("Police Alludu 1994 DVD HQ Krishna","QBX5xbcU2Ss","|"), db);
    	 addLink(new WebLinks("Police Bullet 1991 DVD HQ Rajnikanth","I6ZU_Y06zAk","|"), db);
    	 addLink(new WebLinks("Police Commando 1994 Vijaykanth","pEn1YgsF3G0","|"), db);
    	 addLink(new WebLinks("Police Lockup 1993 DVD HQ Vinod Kumar","PQNTSTXPl8g","|"), db);
    	 addLink(new WebLinks("Praana Daata 1993 ANR","pHBKaCUEjDQ","|"), db);
    	 addLink(new WebLinks("Prema & Co 1994 DVD HQ Naresh","NdQoLD-xiPE","|"), db);
    	 addLink(new WebLinks("Prema Katha 1999 DVD HQ Sumanth","41AJrmuQVB4","|"), db);
    	 addLink(new WebLinks("Prema Khaidi 1990 DVD Harish","Tb64Kx3FbKo","|"), db);
    	 addLink(new WebLinks("Prema Lekha DVD HQ Ajith","dDr1Ec5EKvo","|"), db);
    	 addLink(new WebLinks("Prema Paavuralu 1990 Salman Khan","PY1KRohxAmY","|"), db);
    	 addLink(new WebLinks("Prema Panjaram 1991 Mohan Babu","bhgyKv50yRQ","|"), db);
    	 addLink(new WebLinks("Premaku Velayara 1999 DVD HQ J D Chakravarthi","uRX_gnXbobM","|"), db);
    	 addLink(new WebLinks("Premalayam 1994 DVD Salman Khan","KFB7Y0BmpiQ","|"), db);
    	 addLink(new WebLinks("Premanjali 1996 DVD HQ Ramesh Arvind","GepgjZMkn_U","|"), db);
    	 addLink(new WebLinks("Preme Naa Pranam 1993 DVD HQ Aamani","pdWP3IHy1CE","|"), db);
    	 addLink(new WebLinks("Premikula Roju 1999 DVD HQ Kunal","sbuZl1ZPoAg","|"), db);
    	 addLink(new WebLinks("President Gari Pellam 1992 DVD HQ Nagarjuna","4QlpDI-fjFc","|"), db);
    	 addLink(new WebLinks("Preyasi Raave 1999 DVD HQ Srikanth","OY38N-i8ET4","|"), db);
    	 addLink(new WebLinks("Priyam 1997 Arun Kumar","CgMQ4YSGIgQ","|"), db);
    	 addLink(new WebLinks("Priyamaina Srivaaru 1997 DVD HQ Suman","EBuOHUBxQfA","|"), db);
    	 addLink(new WebLinks("Public Rowdy 1992 Bhanuchander","zNqgjlKBLr4","|"), db);
    	 addLink(new WebLinks("Puttinti Pattu Cheera 1990 DVD HQ Suresh","EPIAWkXL7x4","|"), db);
    	 addLink(new WebLinks("Raatri 1992 DVD HQ Revathi","zY94Cx6Vguo","|"), db);
    	 addLink(new WebLinks("Raja Kumarudu 1999 DVD HQ Mahesh Babu","z4FK_IKIwQE","|"), db);
    	 addLink(new WebLinks("Raja Simham 1995 DVD HQ Rajasekhar","X6xsyqWBm3o","|"), db);
    	 addLink(new WebLinks("Raja Vikramarka 1990 DVD HQ Chiranjeevi","zTE1sURgu_E","|"), db);
    	 addLink(new WebLinks("Rajendrudru Gajendrudru 1993 DVD HQ Rajendra Prasad","VDo5Rp6aZBQ","|"), db);
    	 addLink(new WebLinks("Rakshana 1993 DVD HQ Nagarjuna","jYJ3ENJ5g-g","|"), db);
    	 addLink(new WebLinks("Raktha Tharpanam 1992 DVD HQ Krishna","5-qBlHm8yKw","|"), db);
    	 addLink(new WebLinks("Rambantu 1996 DVD HQ Rajendra Prasad","0Wj-2vSzHvg","|"), db);
    	 addLink(new WebLinks("Ramudochhadu 1996 DVD HQ Nagarjuna","l-9WlnJ539o","|"), db);
    	 addLink(new WebLinks("Rangeli 1997 DVD HQ Aamir Khan","8CwI8fwK_7E","|"), db);
    	 addLink(new WebLinks("Ratha Saradhi 1993 DVD HQ ANR","Z7mwXYMor1s","|"), db);
    	 addLink(new WebLinks("Rikshavodu 1995 Chiranjeevi","B79kxGwJuic","|"), db);
    	 addLink(new WebLinks("Roja 1992 DVD HQ Arvind Swamy","1Y4dMgHYDMw","|"), db);
    	 addLink(new WebLinks("Rowdy Alludu 1991 DVD HQ Chiranjeevi","hRoeuPZakqs","|"), db);
    	 addLink(new WebLinks("Rowdy Annayya 1993 Krishna","WbpBbn2WYDE","|"), db);
    	 addLink(new WebLinks("Rowdy Gari Pellam 1991 DVD HQ Mohan Babu","Zh9zurW4Ves","|"), db);
    	 addLink(new WebLinks("Rowdy Inspector 1992 DVD HQ Balakrishna","_kDrOp4s0rI","|"), db);
    	 addLink(new WebLinks("Rowdyism Nasinchali 1990 DVD HQ Rajasekhar","zs8erZvSw6M","|"), db);
    	 addLink(new WebLinks("S P Parshuram 1994 DVD HQ Chiranjeevi","_n1Z3OOsKx8","|"), db);
    	 addLink(new WebLinks("Samaram 1994 Ramki","GxbITWVZ5rI","|"), db);
    	 addLink(new WebLinks("Samarasimha Reddy 1999 Balakrishna","-tui7TNMa4w","|"), db);
    	 addLink(new WebLinks("Samsarala Mechanic 1992 DVD HQ Dasari Narayana Rao","LTnn-3BlMRY","|"), db);
    	 addLink(new WebLinks("Samudram 1999 DVD HQ Jagapathi Babu","GaWQSY39zQA","|"), db);
    	 addLink(new WebLinks("Santhi Kranthi 1991 DVD HQ Nagarjuna","xjmWDagmdYM","|"), db);
    	 addLink(new WebLinks("Saradha Bullodu 1996 DVD HQ Venkatesh","__zDidjknuc","|"), db);
    	 addLink(new WebLinks("Saranam Saranam Manikanta 1993 DVD Sarath Babu","frhBvKlaoLI","|"), db);
    	 addLink(new WebLinks("Sarigamalu 1994 DVD HQ Vineeth","8BjhkkMdqBI","|"), db);
    	 addLink(new WebLinks("Satya 1998 DVD J D Chakravarthi","vUAWnc5GYl0","|"), db);
    	 addLink(new WebLinks("Seenu 1999 DVD Venkatesh","vgsNNWyeeu0","|"), db);
    	 addLink(new WebLinks("Seetharamula Kalyanam Chothamu Rarandi 1998 DVD HQ Venkat","fuKseZqJXY8","|"), db);
    	 addLink(new WebLinks("Shabash Ramu 1993 DVD HQ Vinod Kumar","wBUrct_1Zr0","|"), db);
    	 addLink(new WebLinks("Simha Garjana 1995 DVD Krishnam Raju","SeID5eJ96U8","|"), db);
    	 addLink(new WebLinks("Sivayya 1998 DVD Rajasekhar","tnau6SsRse4","|"), db);
    	 addLink(new WebLinks("Sneham Kosam 1999 DVD Chiranjeevi","QFkFx10hLSs","|"), db);
    	 addLink(new WebLinks("Sogasu Chooda Tharamaa 1995 Naresh","fGEsmKeH_nU","|"), db);
    	 addLink(new WebLinks("Yamajathakudu 1999 DVD HQ Mohan Babu","rx8fsaYAgBo","|"), db);
    	 addLink(new WebLinks("Yoddha 1992 DVD HQ Mohanlal","-peqIQg8Nto","|"), db);
    	 addLink(new WebLinks("Yuvaratna Rana 1998 DVD HQ Balakrishna","6ZDUyz9mans","|"), db);
    	 addLink(new WebLinks("47 Rojulu 1981 DVD HQ Chiranjeevi","MQ1f8HCU4EA","|"), db);
    	 addLink(new WebLinks("Aadavallu Meeku Joharulu 1981 DVD Chiranjeevi","tUULiZT3AbI","|"), db);
    	 addLink(new WebLinks("Aakhari Poratam 1988 DVD HQ Nagarjuna","du8Qqc2CFRY","|"), db);
    	 addLink(new WebLinks("Aakrandana 1986 DVD HQ Jayasudha","FFasyut4Xuc","|"), db);
    	 addLink(new WebLinks("Aalaya Shikaram 1983 DVD HQ Chiranjeevi","usd3zy4Sjvw","|"), db);
    	 addLink(new WebLinks("Aaradhana 1987 DVD HQ Chiranjeevi","LQ7nSHUZAN0","|"), db);
    	 addLink(new WebLinks("Aatagadu 1980 DVD HQ NTR","O1vZtm_vxcE","|"), db);
    	 addLink(new WebLinks("Aathma Katha 1988 DVD HQ Mohan","nH4NiCOSWCs","|"), db);
    	 addLink(new WebLinks("Abhimanyudu 1984 DVD HQ Sobhan Babu","QRsmmYUG4i8","|"), db);
    	 addLink(new WebLinks("Abinandhana 1988 DVD HQ Karthik","LJrfBiUagcw","|"), db);
    	 addLink(new WebLinks("Adavi Donga 1985 DVD HQ Chiranjeevi","J9NFfistBx8","|"), db);
    	 addLink(new WebLinks("Adavilo Abhimanyudu 1989 DVD Jagapathi Babu","wuICHcplCvY","|"), db);
    	 addLink(new WebLinks("Addala Meda 1981 DVD HQ Murali Mohan","KWt_DzNAfow","|"), db);
    	 addLink(new WebLinks("Aggirava 1981 DVD HQ Sridevi.","CsU1ijNn010","|"), db);
    	 addLink(new WebLinks("Agni Gundam 1984 Chiranjeevi","OEmrbAaPLM4","|"), db);
    	 addLink(new WebLinks("Agni Keratalu 1988 DVD Krishna","Luei36qgIo8","|"), db);
    	 addLink(new WebLinks("Agni Poolu 1981 DVD Krishnam Raju","SCcSWpdSZDY","|"), db);
    	 addLink(new WebLinks("Aha Naa Pellanta 1987 DVD Rajendra Prasad","XI6fIbmeezY","|"), db);
    	 addLink(new WebLinks("Ajeyudu 1987 DVD HQ Venkatesh","vISpMJ3P5lA","|"), db);
    	 addLink(new WebLinks("Akali Rajyam 1981 DVD HQ Kamal Hassan","sewsGJEc2wE","|"), db);
    	 addLink(new WebLinks("Allari Krishnayya 1987 DVD HQ Balakrishna","k-K49Rj_XqA","|"), db);
    	 addLink(new WebLinks("Alludugaru Zindabad 1981 DVD HQ Gummadi","Q7t9YbD1r-8","|"), db);
    	 addLink(new WebLinks("Allulu Vasthunnaru 1984 DVD HQ Chiranjeevi","OlYvLyQdYPg","|"), db);
    	 addLink(new WebLinks("Amarajeevi 1983 DVD ANR","o2_kQvvo_J8","|"), db);
    	 addLink(new WebLinks("Amavasya Chandrudu 1981 DVD HQ L V Prasad","YglkNov2f2A","|"), db);
    	 addLink(new WebLinks("Amayaka Chakravarthy 1983 DVD HQ Chandra Mohan","A14ISbE66Yk","|"), db);
    	 addLink(new WebLinks("America Abbayi 1987 Rajasekhar","8nIHdNEerTI","|"), db);
    	 addLink(new WebLinks("Ammayi Manasu 1989 DVD HQ Jayasudha","V7IPJ-BA-nw","|"), db);
    	 addLink(new WebLinks("Ananda Bhairavi 1984 DVD HQ Girish Karnad","ra1pAsjaHBA","|"), db);
    	 addLink(new WebLinks("Anasuyamma Gari Alludu 1986 DVD HQ Balakrishna","FeJ2g1HNYD0","|"), db);
    	 addLink(new WebLinks("Antham Kadidi Aarambam 1981 DVD HQ Krishna","hgxjNoulgf8","|"), db);
    	 addLink(new WebLinks("Anuraga Devatha 1982 DVD HQ NTR","cjbr14wPsNw","|"), db);
    	 addLink(new WebLinks("Anveshana 1985 DVD HQ Bhanupriya","zTq0pwiRI-k","|"), db);
    	 addLink(new WebLinks("Apathbandhavudu 1983 DVD Sharada.","mjwNUtYcLfQ","|"), db);
    	 addLink(new WebLinks("Ashoka Chakravarthy 1989 DVD HQ Anjali Devi.","8kzp8WuvaZA","|"), db);
    	 addLink(new WebLinks("Ashwathama 1988 DVD HQ Sharada.","ofN3VP3sm1U","|"), db);
    	 addLink(new WebLinks("Attagari Pettanam 1981 DVD HQ Murali Mohan","1NZpPFCG2GY","|"), db);
    	 addLink(new WebLinks("Attagaru Swagatham 1988 DVD HQ Bhanumathi Ramakrishna.","dhE331WTe48","|"), db);
    	 addLink(new WebLinks("Attagaru Zindabad 1987 DVD Bhanumathi","Qgwt3fT0qi0","|"), db);
    	 addLink(new WebLinks("Attaku Yamudu Ammayiki Mogudu 1989 DVD HQ Chiranjeevi","gO8jiRjtyeg","|"), db);
    	 addLink(new WebLinks("Bahudoorapu Batasari 1983 DVD HQ ANR","7De4WFjEqzo","|"), db);
    	 addLink(new WebLinks("Balidaanam 1983 DVD Sobhan Babu","60e_h2EV0NU","|"), db);
    	 addLink(new WebLinks("Bandhuvulostunnaru Jagratha 1989 DVD HQ Rajendra Prasad","-3WAFeG6BJs","|"), db);
    	 addLink(new WebLinks("Bangaru Bhoomi 1982 DVD Sridevi.","eBNGNZw0iUQ","|"), db);
    	 addLink(new WebLinks("Bangaru Chilaka 1985 Arjun","E6OIoE93eX0","|"), db);
    	 addLink(new WebLinks("Bapuji Bharatham 1980 DVD HQ Chandra Mohan","SnNeg7PnoLE","|"), db);
    	 addLink(new WebLinks("Bava Bava Panniru 1989 DVD HQ Naresh","L3ukss3_uVY","|"), db);
    	 addLink(new WebLinks("Bava Maradallu 1984 DVD HQ Sobhan Babu","m2F0yM0zda4","|"), db);
    	 addLink(new WebLinks("Bazaru Rowdy 1988 DVD Ramesh Babu","UbCe4XnYdeI","|"), db);
    	 addLink(new WebLinks("Bezwada Bebbuli 1983 DVD HQ Krishna","bYf-BjJSrpU","|"), db);
    	 addLink(new WebLinks("Bhakta Dhruva Markandeya 1982 DVD Master Vamsi","EOR55HQnJos","|"), db);
    	 addLink(new WebLinks("Bhale Dampathulu 1989 DVD HQ ANR","YtAkINSg90A","|"), db);
    	 addLink(new WebLinks("Bhale Donga 1989 DVD HQ Balakrishna","HgqcC6dyWqo","|"), db);
    	 addLink(new WebLinks("Bhale Krishnudu 1980 DVD Anjali Devi.","rMP3pL0RUVA","|"), db);
    	 addLink(new WebLinks("Bhale Mogudu 1987 DVD HQ Rajendra Prasad","vMdqlsC7XUc","|"), db);
    	 addLink(new WebLinks("Bhale Tammudu 1985 DVD NTR","XehL_cYstv0","|"), db);
    	 addLink(new WebLinks("Bhama Kalapam 1988 DVD HQ Rajendra Prasad","o1HgpE-GeZY","|"), db);
    	 addLink(new WebLinks("Bhanumati Gari Mogudu 1987 DVD HQ Balakrishna","TGegoP5PghU","|"), db);
    	 addLink(new WebLinks("Bharata Nari 1989 DVD Vijayashanthi","P1jK4Q36e6k","|"), db);
    	 addLink(new WebLinks("Bharathamlo Arjunudu 1987 DVD Venkatesh","elEP5TVGASk","|"), db);
    	 addLink(new WebLinks("Bharyalu Jagratha 1989 DVD HQ Raghu","ESceJOLsaEA","|"), db);
    	 addLink(new WebLinks("Bobbili Puli 1982 DVD HQ NTR","ZzqjC6SrQY0","|"), db);
    	 addLink(new WebLinks("Buchi Babu 1980 DVD HQ Gummadi.","gA0qYpRVl9c","|"), db);
    	 addLink(new WebLinks("Captian Nagarjun 1986 DVD HQ Nagarjuna","gDnpnttubW0","|"), db);
    	 addLink(new WebLinks("Chadastapu Mogudu 1986 DVD HQ Suman","ALmyAj2LhS4","|"), db);
    	 addLink(new WebLinks("Chakravarthy 1987 DVD HQ Chiranjeevi","zQwNnsozAO8","|"), db);
    	 addLink(new WebLinks("Chanakya Shapadham 1986 DVD HQ Chiranjeevi","MY6W7oRRYrw","|"), db);
    	 addLink(new WebLinks("Chanda Sasanudu 1983 DVD HQ NTR","UPrKl7HILgc","|"), db);
    	 addLink(new WebLinks("Chandipriya 1980 DVD HQ Chiranjeevi","MXPKfP_aVr0","|"), db);
    	 addLink(new WebLinks("Chantabbai 1986 DVD HQ Chiranjeevi","5YZk90nYf1o","|"), db);
    	 addLink(new WebLinks("Chattamtho Porattam 1985 DVD HQ Chiranjeevi","dKyQBVv5YjE","|"), db);
    	 addLink(new WebLinks("Chattaniki Kallu Levu 1981 DVD HQ Chiranjeevi","9A7xzzNyDek","|"), db);
    	 addLink(new WebLinks("Chettu Kinda Pleader 1989 DVD HQ Rajendra Prasad","TxlGlWNF53I","|"), db);
    	 addLink(new WebLinks("Chikkadu Dorakadu 1988 DVD HQ Rajendra Prasad","g0shKNm-Tjk","|"), db);
    	 addLink(new WebLinks("Chinna Babu 1988 DVD HQ Nagarjuna","9RAGNEhyUO0","|"), db);
    	 addLink(new WebLinks("Chinnari Sneham 1989 DVD HQ Anjali Devi.","b08l67zqPP0","|"), db);
    	 addLink(new WebLinks("Manchi Donga 1988 DVD HQ Chiranjeevi","Y4SjbHDjYgY","|"), db);
    	  addLink(new WebLinks("Manchupallaki 1982 DVD HQ Chiranjeevi","TZRUGjgWRlE","|"), db);
    	 addLink(new WebLinks("Mandaladeesudu 1987 DVD HQ Bhanumathi Ramakrishna","vIXdCN5jqug","|"), db);
    	 addLink(new WebLinks("Mangala Gauri 1980 Murali Mohan","NQfzo6bBFRg","|"), db);
    	 addLink(new WebLinks("Mangalyabalam 1985 DVD Sobhan Babu","mV37pDiNtMI","|"), db);
    	 addLink(new WebLinks("Mangamma Gari Manavadu 1984 DVD HQ Balakrishna","WGsSE9P2B18","|"), db);
    	 addLink(new WebLinks("Mannemlo Monagadu 1986 DVD HQ Arjun","BDZFO-ryZwc","|"), db);
    	 addLink(new WebLinks("Manthra Dandam 1985 Siva Krishna","rjItaJFaPvs","|"), db);
    	 addLink(new WebLinks("Manthrigari Viyyankudu 1983 DVD HQ Chiranjeevi","zMqHwHIpSjQ","|"), db);
    	 addLink(new WebLinks("Marana Mrudangam 1988 DVD Chiranjeevi","0JM_3euT7xg","|"), db);
    	 addLink(new WebLinks("Marana Shasanam 1987 DVD HQ Krishnam Raju","-VoBVWT46fM","|"), db);
    	 addLink(new WebLinks("Maro Maya Bazaar 1983 DVD HQ Chandra Mohan","NF_Nw4qj9ak","|"), db);
    	 addLink(new WebLinks("Mouna Ragam 1986 DVD Karthik","5qy-5xGhJxk","|"), db);
    	 addLink(new WebLinks("Mudda Mandaram 1981 Pradeep","R2bdBFuKPIo","|"), db);
    	 addLink(new WebLinks("Muddula Manavaraalu 1986 DVD Chandra Mohan","IZ150I-l0Ag","|"), db);
    	 addLink(new WebLinks("Mugguru Mithrulu 1985 DVD HQ Sobhan Babu","jMISkxo74II","|"), db);
    	 addLink(new WebLinks("Mundadugu 1983 DVD Krishna","Ej33-y6nnnE","|"), db);
    	 addLink(new WebLinks("Musugu Donga 1985 DVD HQ Suman","9GFH5cXx1X4","|"), db);
    	 addLink(new WebLinks("Naa Pilupe Prabhanjanam 1986 DVD HQ Krishna","jWOEf52ZZYM","|"), db);
    	 addLink(new WebLinks("Naagu 1984 Chiranjeevi","JwjkQqwORPw","|"), db);
    	 addLink(new WebLinks("Naku Pellam Kavali 1987 DVD HQ Rajendra Prasad","wo8gYxpEbPw","|"), db);
    	 addLink(new WebLinks("Nalla Trachu 1987 DVD HQ Benarjee","loUu35NJxeI","|"), db);
    	 addLink(new WebLinks("Nalugu Sthambalata 1982 DVD HQ Naresh","Tpgvf8zVTXY","|"), db);
    	 addLink(new WebLinks("Navodayam 1983 Suman","1X3_5OJs2Uc","|"), db);
    	 addLink(new WebLinks("Nayakudu 1987 DVD HQ Kamal Hassan","vI-U3ZSj-vk","|"), db);
    	 addLink(new WebLinks("Nayakudu Vinayakudu 1980 DVD HQ ANR","IvcAcpo9pqU","|"), db);
    	 addLink(new WebLinks("Neerajanam 1989 DVD Vishwas","HypSIbT5wko","|"), db);
    	 addLink(new WebLinks("Neti Yugadharamam 1986 DVD HQ Krishnam Raju","PJqZ7WQJCeo","|"), db);
    	 addLink(new WebLinks("Nilavanka 1982 DVD Gummadi","nfWMsMRYXKE","|"), db);
    	 addLink(new WebLinks("Nippulanti Manishi 1986 DVD HQ Balakrishna","v0E8TLVSULE","|"), db);
    	 addLink(new WebLinks("Nireekshana 1982 DVD HQ Bhanuchander","ke5DgytKlsI","|"), db);
    	 addLink(new WebLinks("Noorava Roju 1984 DVD HQ Mohan","_y7Sk0sKZrY","|"), db);
    	 addLink(new WebLinks("Nyayam Kavali 1981 DVD HQ Chiranjeevi","G90jfjPHlwo","|"), db);
    	 addLink(new WebLinks("Nyayam Kosam 1988 DVD HQ Rajasekhar","J6IbH2FyTU4","|"), db);
    	 addLink(new WebLinks("Nyayam Meere Cheppali 1989 Suman","UyfE0UScYf4","|"), db);
    	 addLink(new WebLinks("O Amma Katha 1981 DVD HQ Nutan Prasad","brZRKudjg8o","|"), db);
    	 addLink(new WebLinks("Oka Naati Raathri 1980 DVD HQ Bhanumathi Ramakrishna","p9u7eTuXTzI","|"), db);
    	 addLink(new WebLinks("Ontari Poraatam 1989 Venkatesh","k12HGZGnkcw","|"), db);
    	 addLink(new WebLinks("Ooriki Ichina Maata 1981 DVD Chiranjeevi","Yx3GL7nu2js","|"), db);
    	 addLink(new WebLinks("Ooriki Monagadu 1981 DVD HQ Krishna","wUSpB6Cxu4k","|"), db);
    	 addLink(new WebLinks("Paalu Neelu 1980 DVD HQ Mohan Babu","HZXadUsiDMc","|"), db);
    	 addLink(new WebLinks("Pachhani Kapuram 1985 DVD HQ Krishna","H04375WvBnM","|"), db);
    	 addLink(new WebLinks("Padamati Sandhya Ragam 1986 DVD HQ Vijayashanthi","16QCdOkt9QA","|"), db);
    	 addLink(new WebLinks("Palnati Simham 1985 DVD HQ Krishna","xeWXy1unH-o","|"), db);
    	 addLink(new WebLinks("Pasivadi Pranam 1987 DVD HQ Chiranjeevi","i48ismjnVhI","|"), db);
    	 addLink(new WebLinks("Pedala Brathukulu 1981 DVD HQ Sudhakar","u5UccLyhed8","|"), db);
    	 addLink(new WebLinks("Pelleedu Pillalu 1982 DVD HQ Suresh","2EXX6F0vSQA","|"), db);
    	 addLink(new WebLinks("Pilla Piduga 1982 DVD HQ RamaKrishna","nFZx8t732Ks","|"), db);
    	 addLink(new WebLinks("Prachanda Bhaaratam 1988 DVD HQ Krishnam Raju","ZVXGHmNPHzI","|"), db);
    	 addLink(new WebLinks("Prema Gharshana 1986 Sarath","WD29H70aMDc","|"), db);
    	 addLink(new WebLinks("Prema Murtulu 1982 DVD HQ Sobhan Babu","B32-s0FFg4Y","|"), db);
    	 addLink(new WebLinks("Prema Nakshatram 1982 DVD Krishna","o7fxeIpv_uU","|"), db);
    	 addLink(new WebLinks("Prema Pichollu 1983 DVD HQ Chiranjeevi","6cr-PI_s9Pc","|"), db);
    	 addLink(new WebLinks("Prema Sagaram 1983 DVD HQ Ramesh","zWU9Phk_HQw","|"), db);
    	 addLink(new WebLinks("Prema Simhasanam 1981 DVD HQ NTR","ljOK5ChPUT8","|"), db);
    	 addLink(new WebLinks("Prema Tarangalu 1980 DVD HQ Chiranjeevi","TEzoy850Po8","|"), db);
    	 addLink(new WebLinks("Premaabhishekam 1981 DVD HQ ANR","1yX2kRuMoac","|"), db);
    	 addLink(new WebLinks("Premaradhana 1985 Satya Raj","4riihcBbLxs","|"), db);
    	 addLink(new WebLinks("Puli Bebbuli 1983 DVD HQ Chiranjeevi","8DNQ_-Nj5Fo","|"), db);
    	 addLink(new WebLinks("Punnami Naagu 1980 DVD HQ Chiranjeevi","N_Bb-eP2toU","|"), db);
    	 addLink(new WebLinks("Raaga Deepam 1982 DVD HQ ANR","RCu7vRQEx6k","|"), db);
    	 addLink(new WebLinks("Radha Kalyanam 1981 DVD HQ Chandra Mohan","fTBONj5RUvM","|"), db);
    	 addLink(new WebLinks("Ragile Hrudayalu 1980 DVD HQ Krishna","LLT9SaI9co0","|"), db);
    	 addLink(new WebLinks("Rajadhi Raju 1980 DVD HQ Vijayachandar","Ez6yuRWCZDI","|"), db);
    	 addLink(new WebLinks("Raktabhishekam 1988 DVD HQ Balakrishna","gQhLUlFt-9E","|"), db);
    	 addLink(new WebLinks("Ram Robert Rahim 1980 DVD HQ Krishna","xlVaPhkhnAA","|"), db);
    	 addLink(new WebLinks("Rama Rajyamlo Bheemaraju 1983 Krishna","Kfjm1GAdJmk","|"), db);
    	 addLink(new WebLinks("Ramu 1987 DVD HQ Balakrishna","8XKJ8Y_VTUI","|"), db);
    	 addLink(new WebLinks("Ramudu Bheemudu 1988 DVD HQ Balakrishna","ff2Y5Y4o_G0","|"), db);
    	 addLink(new WebLinks("Rani Kasularangamma 1981 Chiranjeevi","eyGvEg-7ljU","|"), db);
    	 addLink(new WebLinks("Rao Gari Illu 1988 DVD HQ ANR","E-3jA9EtHWI","|"), db);
    	 addLink(new WebLinks("Raraju 1984 DVD HQ Krishnam Raju","S_i_rFN4PJk","|"), db);
    	 addLink(new WebLinks("Rendu Jella Seetha 1983 DVD HQ Naresh","cJV88xD0aXE","|"), db);
    	 addLink(new WebLinks("Repati Pourulu 1986 DVD HQ Vijayashanthi","sw-EViuB-1w","|"), db);
    	 addLink(new WebLinks("Rowdy 1984 DVD Krishnam Raju","O2raJZ2fzIU","|"), db);
    	 addLink(new WebLinks("Rudra Veena 1988 DVD HQ Chiranjeevi","gt9zL2QI7JU","|"), db);
    	 addLink(new WebLinks("Rustum 1984 Chiranjeevi","fmyw3Ys8qGA","|"), db);
    	 addLink(new WebLinks("Sagara Sangamam 1983 DVD Kamal Hassan","M0BHf_jQTWY","|"), db);
    	 addLink(new WebLinks("Sakkanodu 1986 DVD HQ Sobhan Babu","c0VfwKwsaHs","|"), db);
    	 addLink(new WebLinks("Samsaram Santhanam 1981 DVD HQ Sobhan Babu","zzHmnpphog8","|"), db);
    	 addLink(new WebLinks("Sanchalanam 1985 DVD HQ Mohan Babu","ZiIwScHDW4A","|"), db);
    	 addLink(new WebLinks("Sangharshana 1983 DVD Chiranjeevi","cmN0bocwdnU","|"), db);
    	 addLink(new WebLinks("Sankeerthana 1987 DVD HQ Nagarjuna","yij6zatDC4E","|"), db);
    	 addLink(new WebLinks("Santhi Nivasam 1986 DVD HQ Krishna","5V9Xv8RJldQ","|"), db);
    	 addLink(new WebLinks("Saptapadhi 1980 DVD HQ Somayajulu","mJLmmHC5e-U","|"), db);
    	 addLink(new WebLinks("Sarada Ramudu 1980 DVD HQ NTR","dfIaYc_d_7E","|"), db);
    	 addLink(new WebLinks("Sardar Papa Rayudu 1980 DVD HQ NTR","Tgy__kA6_co","|"), db);
    	 addLink(new WebLinks("Seetha Ramulu 1980 DVD HQ Krishnam Raju","j2dZVCBeXOw","|"), db);
    	 addLink(new WebLinks("Seethakoka Chiluka 1981 DVD HQ Karthik","NoNZrYMYuqk","|"), db);
    	 addLink(new WebLinks("Seethamma Pelli 1984 DVD Mohan Babu","bSzEJqG43Y4","|"), db);
    	 addLink(new WebLinks("Shankaravam 1987 DVD HQ Krishna","YvbwHoMPiQc","|"), db);
    	 addLink(new WebLinks("Shiva 1989 DVD HQ Nagarjuna","VRVHoabpSoI","|"), db);
    	 addLink(new WebLinks("Shri Mantralaya Raghavendra Swamy Mahatyam 1985 DVD Rajnikanth","z5Eq7XG-dfk","|"), db);
    	 addLink(new WebLinks("Shri Ranganeethulu 1983 DVD HQ ANR","LA16S9lT3Yg","|"), db);
    	 addLink(new WebLinks("Shri Vinayaka Vijayam 1980 DVD HQ Krishnam Raju","AO01sqCjbdc","|"), db);
    	 addLink(new WebLinks("Shuba Muhurtham 1983 DVD Chandra Mohan","vQj8T9LGQA0","|"), db);
    	 addLink(new WebLinks("Simha Swapnam 1981 DVD HQ Narasimha Raju","3AnA5sCQ-sc","|"), db);
    	 addLink(new WebLinks("Sirivennela 1986 DVD HQ Benarjee","1pJJXPIxliM","|"), db);
    	 addLink(new WebLinks("Sisindri 1985 DVD HQ Nagarjuna","YubHfXNKo9c","|"), db);
    	 addLink(new WebLinks("Sitaara 1983 DVD HQ Suman","wLCmIjIaDyY","|"), db);
    	 addLink(new WebLinks("Sivamettina Sathyam 1980 Krishnam Raju","IkzayEiJkCA","|"), db);
    	 addLink(new WebLinks("Sravana Sandhya 1986 DVD HQ Sobhan Babu","hZPWq1PfoAU","|"), db);
    	 addLink(new WebLinks("Sreevari Muchatlu 1980 DVD HQ ANR","HBb529eZxKI","|"), db);
    	 addLink(new WebLinks("Sri Kanaka Mahalakshmi Recording Dance Troupe 1987 DVD HQ Naresh","meNjBHj_PS4","|"), db);
    	 addLink(new WebLinks("Sri Madvirata Virabrahmendra Swamy Charitra 1984 DVD HQ NTR","UKiU57hSltU","|"), db);
    	 addLink(new WebLinks("Sri Shirdi Saibaba Mahathyam 1986 DVD HQ Vijayachandar","w_Ku_vuoiIg","|"), db);
    	 addLink(new WebLinks("Srimati Oka Bahumathi 1987 DVD HQ Chandra Mohan","brqa_Y3cYd8","|"), db);
    	 addLink(new WebLinks("Srinivasa Kalyanam 1987 DVD Venkatesh","OWF1QqOocjg","|"), db);
    	 addLink(new WebLinks("Srirasthu Subhamasthu 1981 DVD Chiranjeevi","eOQ1QTsRzeQ","|"), db);
    	 addLink(new WebLinks("Srivaari Sobhanam 1985 DVD HQ Naresh","Y42ivUJQyDg","|"), db);
    	 addLink(new WebLinks("Srivaru 1985 DVD HQ Sobhan Babu","qxqURZxc9zE","|"), db);
    	 addLink(new WebLinks("Sruthi Layalu 1987 DVD HQ Rajasekhar.","CPr7zv7HXAs","|"), db);
    	 addLink(new WebLinks("Station Master 1988 Rajendra Prasad","XRBJESfuADg","|"), db);
    	 addLink(new WebLinks("Street Rowdy 1989 Devaraj","F_nJn1udsHY","|"), db);
    	 addLink(new WebLinks("Subhalekha 1982 DVD HQ Chiranjeevi","sFcjK0Ol6_k","|"), db);
    	 addLink(new WebLinks("Subhodayam 1980 DVD HQ Chandra Mohan","hVtb1ykvu2s","|"), db);
    	 addLink(new WebLinks("Swarakalpana 1989 DVD HQ Edida Sreeram","hSgHh9dI7gM","|"), db);
    	 addLink(new WebLinks("Swarnakamalam 1988 DVD HQ Venkatesh","BsGhE116zLQ","|"), db);
    	 addLink(new WebLinks("Swathi Muthyam 1985 DVD HQ Kamal Hassan","u7wGjWnhR8I","|"), db);
    	 addLink(new WebLinks("Swayam Krushi 1987 DVD HQ Chiranjeevi","pd7OrghXFcU","|"), db);
    	 addLink(new WebLinks("Swayamvaram 1982 DVD Sobhan Babu","huzn3ztNb1o","|"), db);
    	 addLink(new WebLinks("Taatayya Kankanam 1980 Narasimha Raju","1ZlL4DxCLIM","|"), db);
    	 addLink(new WebLinks("Tandava Krishnudu 1984 DVD HQ ANR","SH6BhcTVaUo","|"), db);
    	 addLink(new WebLinks("Tarangini 1982 DVD HQ Suman","Pm5_QUMcrco","|"), db);
    	 addLink(new WebLinks("Thriveni Sangamam 1983 DVD HQ Suman","JfaKGGl64Wk","|"), db);
    	 addLink(new WebLinks("Tingu Rangadu 1982 DVD HQ Chiranjeevi","P2KT4mOts-Q","|"), db);
    	 addLink(new WebLinks("Tirugu Leni Manishi 1981 DVD HQ Chiranjeevi","ZBP32VrzTLI","|"), db);
    	 addLink(new WebLinks("Todu Dongalu 1981 DVD Chiranjeevi","2HMo7DRjtRc","|"), db);
    	 addLink(new WebLinks("Trinetrudu 1988 DVD HQ Chiranjeevi","AQAK8lcmDyc","|"), db);
    	 addLink(new WebLinks("Trisulam 1983 DVD HQ Krishnam Raju","DMe9urPfgQs","|"), db);
    	 addLink(new WebLinks("Vaarala Abbayee 1981 DVD HQ Murali Mohan","OQKMdget_bg","|"), db);
    	 addLink(new WebLinks("Vamsha Gouravam 1982 Sobhan Babu","PkxGs07WVdk","|"), db);
    	 addLink(new WebLinks("Varasudochhadu 1988 DVD HQ Venkatesh","T2KIO_Ho074","|"), db);
    	 addLink(new WebLinks("Vasantha Geetham 1984 DVD HQ ANR","FYXgxKOJOJQ","|"), db);
    	 addLink(new WebLinks("Vasantha Kokila 1982 DVD HQ Kamal Hassan","Fd-vyT6I1Yc","|"), db);
    	 addLink(new WebLinks("Veera Prathap 1987 DVD HQ Mohan Babu","FCuewARxrU8","|"), db);
    	 addLink(new WebLinks("Veguchukka Pagatichukka 1988 DVD HQ Bhanuchander","wpcwE6EAUAs","|"), db);
    	 addLink(new WebLinks("Veta 1986 DVD Chiranjeevi","X6zyKJCknYo","|"), db);
    	 addLink(new WebLinks("Vicky Dada 1989 DVD HQ Nagarjuna","U2Q9z_2Rknk","|"), db);
    	 addLink(new WebLinks("Vijetha 1985 DVD HQ Chiranjeevi","C7hUI7zwTnY","|"), db);
    	 addLink(new WebLinks("Vijrumbhana 1986 Sobhan Babu","iVA-xaggk2c","|"), db);
    	 addLink(new WebLinks("Vikram 1986 DVD HQ Nagarjuna","JEvXEhgvhe4","|"), db);
    	 addLink(new WebLinks("Vimukthi Kosam 1983 DVD HQ Sai Chand","fPQcZkQx9pw","|"), db);
    	 addLink(new WebLinks("Vivaaha Bhojanambu 1988 DVD Rajendra Prasad","R-z8Yg6Wvk4","|"), db);
    	 addLink(new WebLinks("YamaKinkarudu 1982 DVD HQ Chiranjeevi","ofwuwyk49To","|"), db);
    	 addLink(new WebLinks("Yamudiki Mogudu 1988 DVD HQ Chiranjeevi","y7OeQEREUJM","|"), db);
    	 addLink(new WebLinks("Yugakarthalu 1987 DVD HQ Rajasekhar","Cfy792bGnhY","|"), db);
    	 addLink(new WebLinks("Yuvaraju 1982 DVD HQ ANR","_VyqwxWU4xY","|"), db);
    	 addLink(new WebLinks("Yuvatharam Kadilindi 1980 DVD HQ Ramakrishna","lWev9fCxHXI","|"), db);




    	
    	
    	
    	
    	
    }
}
