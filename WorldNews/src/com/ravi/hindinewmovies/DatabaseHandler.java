package com.ravi.hindinewmovies;

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
    private static final int DATABASE_VERSION = 1;
 
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
    	addLink(new WebLinks("Heropanti 2014 DVD","9prx9br3VRg","|"), db);
    	addLink(new WebLinks("Hawaa Hawaai 2014 DVD HQ","UUYkass6wbo","|"), db);
    	addLink(new WebLinks("Purani Jeans 2014 DVD HQ","9prx9br3VRg","|"), db);
    	addLink(new WebLinks("Kaanchi 2014 DVD","9prx9br3VRg","|"), db);
    	addLink(new WebLinks("Revolver Rani 2014","9prx9br3VRg","|"), db);
    	addLink(new WebLinks("2 States 2014 DVD HQ","9prx9br3VRg","|"), db);
    	addLink(new WebLinks("Dekh Tamasha Dekh 2014 DVD HQ","9prx9br3VRg","|"), db);
    	addLink(new WebLinks("Bhoothnath Returns 2014 DVD HQ","9prx9br3VRg","|"), db);
    	addLink(new WebLinks("Main Tera Hero 2014 DVD HQ","hUQ_A10swFQ","|"), db);
    	addLink(new WebLinks("Jal 2014 DVD","9prx9br3VRg","|"), db);
    	addLink(new WebLinks("Youngistaan 2014 DVD","9prx9br3VRg","|"), db);
    	addLink(new WebLinks("Dishkiyaoon 2014 DVD HQ","9prx9br3VRg","|"), db);
    	addLink(new WebLinks("O Teri 2014 DVD","9prx9br3VRg","|"), db);
    	addLink(new WebLinks("Ragini MMS 2 2014 DVD HQ","qJX9IbJTLxQ","|"), db);
    	addLink(new WebLinks("Gang of Ghosts 2014 DVD","9prx9br3VRg","|"), db);
    	addLink(new WebLinks("Gulaab Gang 2014 DVD","9prx9br3VRg","|"), db);
    	addLink(new WebLinks("Total Siyapaa 2014 DVD","9prx9br3VRg","|"), db);
    	addLink(new WebLinks("Queen 2014 DVD HQ","9prx9br3VRg","|"), db);
    	addLink(new WebLinks("Shaadi Ke Side Effects 2014 DVD","9prx9br3VRg","|"), db);
    	addLink(new WebLinks("Highway 2014 DVD","9prx9br3VRg","|"), db);
    	addLink(new WebLinks("Karar 2014 DVD","zoQLbNHe0yU","|"), db);
    	addLink(new WebLinks("Gunday 2014 DVD","9prx9br3VRg","|"), db);
    	addLink(new WebLinks("Hasee Toh Phasee 2014 DVD","9prx9br3VRg","|"), db);
    	addLink(new WebLinks("Heartless 2014","9prx9br3VRg","|"), db);
    	addLink(new WebLinks("One By Two 2014 DVD","9prx9br3VRg","|"), db);
    	addLink(new WebLinks("Jai Ho 2014 DVD HQ","XeY6A8dQM7I","|"), db);
    	addLink(new WebLinks("Karle Pyaar Karle 2014","9prx9br3VRg","|"), db);
    	addLink(new WebLinks("Paranthe Wali Gali 2014 DVD HQ","5eX6Shul0jA","|"), db);
    	addLink(new WebLinks("Yaariyan 2014 DVD HQ","9prx9br3VRg","|"), db);
    	addLink(new WebLinks("Dedh Ishqiya 2014 DVD HQ","9prx9br3VRg","|"), db);
    	addLink(new WebLinks("Mr Joe B. Carvalho 2014 DVD","7SzUp71A6LM","|"), db);
    	addLink(new WebLinks("Gangotri 2014 DVD HQ","pW89Woe_bzc","|"), db);
    	addLink(new WebLinks("Betting Raja 2014 DVD HQ","twQ7AJf-6Ks","|"), db);
    	addLink(new WebLinks("Alex Pandian 2014 DVD HQ","QmpDrlHjb2c","|"), db);
    	addLink(new WebLinks("Captain Nagarjuna 2014 DVD HQ","95LLpT6CB9M","|"), db);
    	addLink(new WebLinks("Double Attack 2014 DVD HQ","F3F1UIT4DkY","|"), db);
    	addLink(new WebLinks("Shaktimaan 2014 DVD HQ","AsLyiLPPYmI","|"), db);
    	addLink(new WebLinks("Jackpot 2013 DVD HQ","FJOFwgUTBTw","|"), db);
    	addLink(new WebLinks("R...Rajkumar 2013 DVD HQ","9prx9br3VRg","|"), db);
    	addLink(new WebLinks("Gori Tere Pyaar Mein 2013 DVD HQ","9prx9br3VRg","|"), db);
    	addLink(new WebLinks("Ramleela 2013","9prx9br3VRg","|"), db);
    	addLink(new WebLinks("Rajjo 2013","9prx9br3VRg","|"), db);
    	addLink(new WebLinks("Satya 2 2013 DVD","9prx9br3VRg","|"), db);
    	addLink(new WebLinks("Krrish 3 2013 DVD HQ","TjVCLA6B7K0","|"), db);
    	addLink(new WebLinks("Sooper Se Ooper 2013 DVD HQ","MxRKqrBODsM","|"), db);
    	addLink(new WebLinks("Mickey Virus 2013 DVD HQ","D_9Q-_oMgC0","|"), db);
    	addLink(new WebLinks("Ishk Actually 2013 DVD HQ","r2incN-izoo","|"), db);
    	addLink(new WebLinks("Shahid 2013","9prx9br3VRg","|"), db);
    	addLink(new WebLinks("Boss 2013 DVD HQ","CJh4utCKUh0","|"), db);
    	addLink(new WebLinks("Besharam 2013 DVD HQ","pco6BoC-PVM","|"), db);
    	addLink(new WebLinks("13B 2009 DVD HQ","oTq6qYFhAAc","|"), db);
    	addLink(new WebLinks("15 Park Avenue 2006 DVD HQ","XlttpupRV24","|"), db);
    	addLink(new WebLinks("16 December 2002 DVD","lW76wRuJeVo","|"), db);
    	addLink(new WebLinks("1920 2008 DVD","ZRy9AJ-CdY8","|"), db);
    	addLink(new WebLinks("1971 2007 DVD","upx7w2M99e4","|"), db);
    	addLink(new WebLinks("3 Idiots 2009 DVD HQ","vgUWkR11Roc","|"), db);
    	addLink(new WebLinks("3 Nights 4 Days 2009","VG_CrbERvrg","|"), db);
    	addLink(new WebLinks("3 Nights 4 Days 2009","VG_CrbERvrg","|"), db);
    	addLink(new WebLinks("36 China Town 2006 DVD HQ","1SQDVRD-pS0","|"), db);
    	addLink(new WebLinks("50 Lakh 2007 DVD","e3RHpAbbR9E","|"), db);
    	addLink(new WebLinks("8x10 Tasveer 2009","3pS2WcsVHE0","|"), db);
    	addLink(new WebLinks("99 2009","kAxc5mdVEcs","|"), db);
    	addLink(new WebLinks("99.9 FM 2005","Cm0-NnhvKSA","|"), db);
    	addLink(new WebLinks("Aabra Ka Daabra 2004 DVD","l2Fv5XpcpxE","|"), db);
    	addLink(new WebLinks("Aaghaaz 2000 DVD","0tx-PP2UXCk","|"), db);
    	addLink(new WebLinks("Aaj Ka Boss 2008","ry8GimM1tGk","|"), db);
    	addLink(new WebLinks("Aaj Ka Krantiveer 2003","d9L1_xCNrkc","|"), db);
    	addLink(new WebLinks("Aaj Ka Maseeha 2009 DVD","sCm3Biu-Dyk","|"), db);
    	addLink(new WebLinks("Aaj Ka Nanha Farishta 2000","TpYVG5cnI6Q","|"), db);
    	addLink(new WebLinks("Aaj Ka Naya Kamina 2006","AEBGblJMulk","|"), db);
    	addLink(new WebLinks("Aaj Ka Naya Khiladi 2010 DVD HQ","XT1tgwJd3Wc","|"), db);
    	addLink(new WebLinks("Aaj Ke Lutyray 2007","IaYp3nq3jQY","|"), db);
    	addLink(new WebLinks("Aaj Ki Dadagiri 2007","zkQkTZNfTD0","|"), db);
    	addLink(new WebLinks("Aaj Ki Dadagiri 2009","eIlStN0MGLU","|"), db);
    	addLink(new WebLinks("Aakrosh 2010 DVD HQ","ubQFV9TLw7E","|"), db);
    	addLink(new WebLinks("Aamdani Athanni Kharcha Rupaiya 2001 DVD HQ","FevDWhZUMWk","|"), db);
    	addLink(new WebLinks("Aan 2004 DVD HQ","ILj7QOXaK7k","|"), db);
    	addLink(new WebLinks("Aanch 2003 DVD HQ","QR7jH2M_7Iw","|"), db);
    	addLink(new WebLinks("Aap Ki Khatir 2006 DVD","1esknXWX3m4","|"), db);
    	addLink(new WebLinks("Aap Mujhe Achche Lagne Lage 2002 DVD","CBAGJfvyRBc","|"), db);
    	addLink(new WebLinks("Aapko Pehle Bhi Kahin Dekha Hai 2002 DVD HQ","J-QLBwRRKKo","|"), db);
    	addLink(new WebLinks("Aarya 2 2009 DVD HQ","r8NLT2Hxbb4","|"), db);
    	addLink(new WebLinks("Aaryan Mera Naam 2010 DVD HQ","ehakCegcle8","|"), db);
    	addLink(new WebLinks("Ab Bas 2004 DVD HQ","8rdYHWN-zAo","|"), db);
    	addLink(new WebLinks("Ab Ke Baras 2002 DVD","2--EJFOXhRM","|"), db);
    	addLink(new WebLinks("Abhimanyu IAS 2006","2_birzRWSZc","|"), db);
    	addLink(new WebLinks("Accident on Hill Road 2009 DVD HQ","LBkjeTBol4k","|"), db);
    	addLink(new WebLinks("Action Replayy 2010 DVD HQ","t2Qwb-mTP_w","|"), db);
    	addLink(new WebLinks("Ada 2010 DVD HQ","euACk54N624","|"), db);
    	addLink(new WebLinks("Admissions Open 2010 DVD","n_Hs086aLkE","|"), db);
    	addLink(new WebLinks("Agni Putra 2000 DVD HQ","lQUNojQqTPE","|"), db);
    	addLink(new WebLinks("Agni Varsha 2002 DVD","fZYHM_3V0aQ","|"), db);
    	addLink(new WebLinks("Aitraaz 2004 DVD","RCRLRml6bNk","|"), db);
    	addLink(new WebLinks("Akhiyon Se Goli Maare 2002 DVD HQ","Exv9EJoEuG4","|"), db);
    	addLink(new WebLinks("Aksar 2006 DVD HQ","naBbS4x3dk4","|"), db);
    	addLink(new WebLinks("Allah Ke Banday 2010 DVD HQ","_1xgZLSSYOs","|"), db);
    	addLink(new WebLinks("Amu 2005 DVD HQ","-Nz-A38JQJM","|"), db);
    	addLink(new WebLinks("Anamika 2008 DVD HQ","2-eww7BI8Q0","|"), db);
    	addLink(new WebLinks("Andolan 2010 DVD","LASLApiIfyw","|"), db);
    	addLink(new WebLinks("Angaar 2003","NDW-MIHc3f4","|"), db);
    	addLink(new WebLinks("Anjaane 2005 DVD","N06IPNkO7gw","|"), db);
    	addLink(new WebLinks("Ankush II 2008","5bjB49Tagjk","|"), db);
    	addLink(new WebLinks("Annarth 2002 DVD","utfTvFXXRwc","|"), db);
    	addLink(new WebLinks("Anthony Kaun Hai 2006","X4CRITAnguk","|"), db);
    	addLink(new WebLinks("Anwar 2007","U4btOIUYaqE","|"), db);
    	addLink(new WebLinks("Apna Asmaan 2007 DVD HQ","S0NIRoUHsXA","|"), db);
    	addLink(new WebLinks("Apna Sapna Money Money 2006 DVD HQ","8F3oXzsMqfo","|"), db);
    	addLink(new WebLinks("Apne 2007 DVD HQ","zJ7pLSoEGaQ","|"), db);
    	addLink(new WebLinks("Apsara 2000","q7Buapp9nX0","|"), db);
    	addLink(new WebLinks("Arjun Devaa 2001 DVD HQ","IZ040k_B6eQ","|"), db);
    	addLink(new WebLinks("Armaan 2003 DVD HQ","1Q-65PZNFYo","|"), db);
    	addLink(new WebLinks("Arya 2009","b7S4iTa6Pz0","|"), db);
    	addLink(new WebLinks("Aseema 2009 DVD","5pN5s_Z8XEs","|"), db);
    	addLink(new WebLinks("Ashok Chakra 2010 DVD","hLKLx1sm_Xo","|"), db);
    	addLink(new WebLinks("Aur Ek Ilzaam 2007","_rVTy6Inzrk","|"), db);
    	addLink(new WebLinks("Aur Ek Jalaad 2007","npeysR49uYo","|"), db);
    	addLink(new WebLinks("Aur Ek Takkar 2009 DVD HQ","YFHPbTN7xII","|"), db);
    	addLink(new WebLinks("Awara Paagal Deewana 2002 DVD HQ","x_oJPi3deKc","|"), db);
    	addLink(new WebLinks("Baabul 2006 DVD HQ","2rUJ_2iPIdA","|"), db);
    	addLink(new WebLinks("Baadal 2004 DVD HQ","TEWFaOjDfJ8","|"), db);
    	addLink(new WebLinks("Bachke Rehna Re Baba 2005","Zs30kKnBPL0","|"), db);
    	addLink(new WebLinks("Bada Beta 2008","ytiJ1RJvUzw","|"), db);
    	addLink(new WebLinks("Badhaai Ho Badhaai 2002 DVD HQ","3Hzzs9Y702o","|"), db);
    	addLink(new WebLinks("Badla 2007 DVD HQ","PlWhggHfXvU","|"), db);
    	addLink(new WebLinks("Badmash No 1 2010 DVD HQ","gfZHg1rsv20","|"), db);
    	addLink(new WebLinks("Badmasho ka Badmash 2009 DVD","bmlKSYR5f1E","|"), db);
    	addLink(new WebLinks("Baghban 2003 DVD HQ","ZupARvlOyxg","|"), db);
    	addLink(new WebLinks("Balram 2001 DVD HQ","FrbCuxvKB5A","|"), db);
    	addLink(new WebLinks("Balram 2006 DVD","vQGouj6F5CM","|"), db);
    	addLink(new WebLinks("Barah Aana 2009","zA_t2wZsQUY","|"), db);
    	addLink(new WebLinks("Bardaasht 2004 DVD HQ","z0PJxMYOhMw","|"), db);
    	addLink(new WebLinks("Barish 2004","8gcEQp6OGFM","|"), db);
    	addLink(new WebLinks("Basti 2003","ymZnICajMSI","|"), db);
    	addLink(new WebLinks("Bedardi 2008 DVD","TJOWA0QH_mg","|"), db);
    	addLink(new WebLinks("Bengal Tiger 2001 DVD","On-to4aYtIQ","|"), db);
    	addLink(new WebLinks("Berozgaar 2010 DVD HQ","0jrIjRLl2Js","|"), db);
    	addLink(new WebLinks("Bewafaa 2005 DVD","83VIWiFfeC4","|"), db);
    	addLink(new WebLinks("Bhagam Bhag 2006 DVD HQ","qVWxN_4usLc","|"), db);
    	addLink(new WebLinks("Bhai 2007","29rb1p0iu84","|"), db);
    	addLink(new WebLinks("Bhairav 2001","Ox9jaPW9QIs","|"), db);
    	addLink(new WebLinks("Bhavnao Ko Samjho 2010 DVD HQ","Ny_hgLyXrmY","|"), db);
    	addLink(new WebLinks("Bheeshma Pratigyaa 2010 DVD","hXPt9o0EgL0","|"), db);
    	addLink(new WebLinks("Bhoot 2003 DVD HQ","owN5ktyv_AQ","|"), db);
    	addLink(new WebLinks("Bhoot Uncle 2006 DVD HQ","LlFLYCwjLpY","|"), db);
    	addLink(new WebLinks("Bhootnath 2008 DVD HQ","pSstpJkvkzs","|"), db);
    	addLink(new WebLinks("Bindaas 2010 DVD","-qBC8oKF9UQ","|"), db);
    	addLink(new WebLinks("Black & White 2008 DVD HQ","OflKFobKz6c","|"), db);
    	addLink(new WebLinks("Black and White 2008","mxBQgysvF6o","|"), db);
    	addLink(new WebLinks("Black Friday 2007 DVD HQ","6Zyd2qj3kOY","|"), db);
    	addLink(new WebLinks("Blue Oranges 2009 DVD HQ","Ivy_y2pAfaw","|"), db);
    	addLink(new WebLinks("Bolo Raam 2010 DVD","eK0u3lGdBD8","|"), db);
    	addLink(new WebLinks("Bombay Crime 2002","eOrgjaXxnUM","|"), db);
    	addLink(new WebLinks("Bombay to Bangkok 2008 DVD HQ","y5io5UdxCwE","|"), db);
    	addLink(new WebLinks("Bombay To Goa 2007 DVD HQ","sEhxQvjU3g8","|"), db);
    	addLink(new WebLinks("Border Hindustan Ka 2003 DVD HQ","zskJkaj0-a4","|"), db);
    	addLink(new WebLinks("Bulandi 2000 DVD HQ","QMHmq4THj34","|"), db);
    	addLink(new WebLinks("Bumm Bumm Bole 2010 DVD HQ","BV25J4a7iOQ","|"), db);
    	addLink(new WebLinks("C Kkompany 2008 DVD HQ","dPOMYTSS29w","|"), db);
    	addLink(new WebLinks("C U at 9 2005 DVD","CtGMuuSICpM","|"), db);
    	addLink(new WebLinks("Chaalbaaz 2003 DVD HQ","RvikU1yhibc","|"), db);
    	addLink(new WebLinks("Chain Kulii Ki Main Kulii 2007 DVD HQ","h4iMwFysQHM","|"), db);
    	addLink(new WebLinks("Chal Chala Chal 2009 DVD HQ","D98co6lXBSE","|"), db);
    	addLink(new WebLinks("Chal Mere Bhai 2000 DVD HQ","85tKlaUhHxE","|"), db);
    	addLink(new WebLinks("Chalo Ishq Ladaaye 2002 DVD HQ","XbXyJ0K6Kws","|"), db);
    	addLink(new WebLinks("Chameli 2003 DVD HQ","fF03gxZamE0","|"), db);
    	addLink(new WebLinks("Chamku 2008 DVD HQ","6tMhAsJBSfU","|"), db);
    	addLink(new WebLinks("Champion 2000 DVD HQ","9NtljkEYB5c","|"), db);
    	addLink(new WebLinks("Chanchal 2008 DVD HQ","DJjnT0dbMUs","|"), db);
    	addLink(new WebLinks("Chandni Bar 2001 DVD HQ","_LgPIJJhirU","|"), db);
    	addLink(new WebLinks("Chandni Ki Ek Kahani 2007","ys263vIMC6k","|"), db);
    	addLink(new WebLinks("Chase 2010 DVD HQ","gYRLfWUe2x0","|"), db);
    	addLink(new WebLinks("Chatrapathy 2008","aurAOewki74","|"), db);
    	addLink(new WebLinks("Chehraa 2005 DVD","PD1PDZ3TVZY","|"), db);
    	addLink(new WebLinks("Chhupa Rustam 2001 DVD HQ","S0JkRn0ya_E","|"), db);
    	addLink(new WebLinks("Chocolate 2005 DVD","BF6G1g7_0Q8","|"), db);
    	addLink(new WebLinks("Chor Machaye Shor 2002 DVD","EPC-ALpeji8","|"), db);
    	addLink(new WebLinks("Chor Mandli 2006 DVD HQ","rNpdzbruO6A","|"), db);
    	addLink(new WebLinks("City Of Gold 2010 DVD HQ","P03s_aJGmck","|"), db);
    	addLink(new WebLinks("Click 2010 DVD HQ","YP-EnqFnOQM","|"), db);
    	addLink(new WebLinks("Company 2002 DVD HQ","Og7Afj5LY9c","|"), db);
    	addLink(new WebLinks("Coolie The Real Baazigar 2008","hnXG90Olo6o","|"), db);
    	addLink(new WebLinks("Crook 2010 DVD HQ","URxt9Wbwf5E","|"), db);
    	addLink(new WebLinks("Dabdaba 2003 DVD HQ","XvJ0ETkgJn0","|"), db);
    	addLink(new WebLinks("Dada No 1 2005 DVD","SHIvtwfhkxM","|"), db);
    	addLink(new WebLinks("Dadagiri Nahi Chalegi 2009","Ytq5wcxqmhg","|"), db);
    	addLink(new WebLinks("Daddy Cool 2009 DVD","05Ne2Mbnxq4","|"), db);
    	addLink(new WebLinks("Dal 2001","Z35RR8Ar2H8","|"), db);
    	addLink(new WebLinks("Danger 2002 DVD HQ","6DGDXD9Q8f4","|"), db);
    	addLink(new WebLinks("Darling 2007 DVD HQ","aScVVhUx56M","|"), db);
    	addLink(new WebLinks("Dayanayak 2007 DVD HQ","ZTzJUEQ3Ong","|"), db);
    	addLink(new WebLinks("De Dana Dan 2009 DVD HQ","NnkMqH2hsRM","|"), db);
    	addLink(new WebLinks("De Taali 2008 DVD HQ","hqZYkE52nmc","|"), db);
    	addLink(new WebLinks("Deadline 2006 DVD HQ","P0eQhJT1GPI","|"), db);
    	addLink(new WebLinks("Deewaanapan 2001 DVD HQ","pyrwzAkv8Go","|"), db);
    	addLink(new WebLinks("Deewane 2000 DVD HQ","lSfH6_vnrII","|"), db);
    	addLink(new WebLinks("Deewangee 2002 DVD HQ","gwBozJpmQdg","|"), db);
    	addLink(new WebLinks("Desh Ka Rakhwala 2007 DVD HQ","hQO5I3RArAw","|"), db);
    	addLink(new WebLinks("Detective Naani 2009 DVD HQ","t8sX7Zj3JCk","|"), db);
    	addLink(new WebLinks("Dhaai Akshar Prem Ke 2000 DVD HQ","87LhcaE2Dz0","|"), db);
    	addLink(new WebLinks("Dhamaal 2007 DVD","6xdRuJoEMoU","|"), db);
    	addLink(new WebLinks("Dhara 2008 DVD","lRlByKeRYDc","|"), db);
    	addLink(new WebLinks("Dharam Veer 2010","0fpUVDZfHSs","|"), db);
    	addLink(new WebLinks("Dharmatma 2008","Q1TNULrOiQI","|"), db);
    	addLink(new WebLinks("Dharmveer 2010 DVD HQ","nHfksfpEu8Y","|"), db);
    	addLink(new WebLinks("Dhoom 2004 DVD","9prx9br3VRg","|"), db);
    	addLink(new WebLinks("Dhoom 2 2006 DVD","9prx9br3VRg","|"), db);
    	addLink(new WebLinks("Dil Diya Hai 2006 DVD HQ","x8FeUJ38XOQ","|"), db);
    	addLink(new WebLinks("Dil Hi Dil Mein 2000 DVD HQ","zIecU5wI9Ew","|"), db);
    	addLink(new WebLinks("Dil Maange More 2004","k3DQkrL_xxQ","|"), db);
    	addLink(new WebLinks("Dil Ne Phir Yaad Kiya 2001 DVD","HEJYaRKzWh0","|"), db);
    	addLink(new WebLinks("Dil Pardesi Ho Gayaa 2003 DVD","mH2aTAGX7oM","|"), db);
    	addLink(new WebLinks("Dil Pe Mat Le Yaar 2000 DVD HQ","2wgLK8nUymI","|"), db);
    	addLink(new WebLinks("Dil Se Teri Yaad Na Jaye 2003","7WUVwPOmpDQ","|"), db);
    	addLink(new WebLinks("Do Dilon Ke Khel Mein 2010","DeiG2j9rCb0","|"), db);
    	addLink(new WebLinks("Don 2006 DVD HQ","NTnAAtTVU90","|"), db);
    	addLink(new WebLinks("Don Muthuswami 2008 DVD HQ","Dj0nznvtpjw","|"), db);
    	addLink(new WebLinks("Dulha Mil Gaya 2010 DVD","cXH2y9tM5_I","|"), db);
    	addLink(new WebLinks("Dulhan Hum Le Jayenge 2000 DVD HQ","5PPsLpuWxwg","|"), db);
    	addLink(new WebLinks("Dunno Y Na Jaane Kyun 2010 DVD HQ","6_iy3XrpT2Q","|"), db);
    	addLink(new WebLinks("Dus 2005 DVD HQ","Ay1Ie389m2A","|"), db);
    	addLink(new WebLinks("Dushmani 2007 DVD","wGg3VevoXFA","|"), db);
    	addLink(new WebLinks("Dushmano Ka Dushman 2010 DVD","fEZMVFhXb8I","|"), db);
    	addLink(new WebLinks("Dushmano Ki Dushmani 2008","SvKvcYQ_xjE","|"), db);
    	addLink(new WebLinks("Ek Alag Mausam 2003 DVD HQ","UPEDyW97n1I","|"), db);
    	addLink(new WebLinks("Ek Aur Aatank 2005","wH1ocu1myhU","|"), db);
    	addLink(new WebLinks("Ek Aur Ek Gyarah 2003 DVD","Plhy4ETazP4","|"), db);
    	addLink(new WebLinks("Ek Aur Jaanbaaz Khiladi 2009","JmS9EvkUZv0","|"), db);
    	addLink(new WebLinks("Ek Aur Jigerbaaz 2008","A4lymHYihiA","|"), db);
    	addLink(new WebLinks("Ek Aur Karmyodha 2005","YZF6FaRT8Gk","|"), db);
    	addLink(new WebLinks("Ek Elan E Jang 2007 DVD","Cuid0nxowFs","|"), db);
    	addLink(new WebLinks("Ek Hasina Thi 2004 DVD HQ","hE2c0gG1AZk","|"), db);
    	addLink(new WebLinks("Ek Hi Raasta 2010 DVD HQ","vmQzwoG_nDM","|"), db);
    	addLink(new WebLinks("Ek Jwalamukhi 2007 DVD","f3eTemO-Kpk","|"), db);
    	addLink(new WebLinks("Ek Khiladi Ek Haseena 2005 DVD HQ","N3AvhWvNovE","|"), db);
    	addLink(new WebLinks("Ek Krantiveer Vasudev Balwant Phadke 2007","_ntbL-ZgfvA","|"), db);
    	addLink(new WebLinks("Ek Pal Pyar Ka 2005 DVD","w6SNxeztXEk","|"), db);
    	addLink(new WebLinks("Ek Se Badhkar Ek 2004 DVD HQ","U9zMsGxcQQs","|"), db);
    	addLink(new WebLinks("Ek Vivaah Aisa Bhi 2008 DVD","H0dLpqNbSPk","|"), db);
    	addLink(new WebLinks("Elaan 2005 DVD","zYEtBq6fvyA","|"), db);
    	addLink(new WebLinks("Farz 2001 DVD HQ","hvmr208hQ-c","|"), db);
    	addLink(new WebLinks("Fast Forward 2009","l9y8eOzcMm4","|"), db);
    	addLink(new WebLinks("Fight Club 2006","wET93nTiQvE","|"), db);
    	addLink(new WebLinks("Firaaq 2009 DVD HQ","pc4ANivCCgs","|"), db);
    	addLink(new WebLinks("Fool n Final 2007 DVD HQ","iVlz-rQ0Y-U","|"), db);
    	addLink(new WebLinks("Fun2shh 2003 DVD","EU_dBCWKWIk","|"), db);
    	addLink(new WebLinks("Gafla 2006","mMDf845X4rI","|"), db);
    	addLink(new WebLinks("Game 2007 DVD","nSiuTWCJY4g","|"), db);
    	addLink(new WebLinks("Garam Masala 2005 DVD HQ","1CRftlyQQ44","|"), db);
    	addLink(new WebLinks("Garv 2004 DVD HQ","S0aj1nSojlE","|"), db);
    	addLink(new WebLinks("Gautam Govinda 2002 DVD HQ","RM7-0_NOiJs","|"), db);
    	addLink(new WebLinks("Ghajini 2009","L1xSAQy47K4","|"), db);
    	addLink(new WebLinks("Ghutan 2007 DVD HQ","DUTrX_AFhDc","|"), db);
    	addLink(new WebLinks("Giraftaar 2008","Jy2_usQfzT0","|"), db);
    	addLink(new WebLinks("Golden Boys 2008 DVD","C2kokZLrJxs","|"), db);
    	addLink(new WebLinks("Golmaal 3 2010","9prx9br3VRg","|"), db);
    	addLink(new WebLinks("Good Boy Bad Boy 2007 DVD HQ","_h3agW3YM9o","|"), db);
    	addLink(new WebLinks("Grahan 2001 DVD HQ","eHg6XDUJTjc","|"), db);
    	addLink(new WebLinks("Great Chatrapathy 2004 DVD","N5liVL2cNEo","|"), db);
    	addLink(new WebLinks("Great Dharmatma 2007 DVD","TlzBNtRnga8","|"), db);
    	addLink(new WebLinks("Great Target 2002","VqPJqBWZgvc","|"), db);
    	addLink(new WebLinks("Gunehgar 2007 DVD HQ","wYJoYezUVuY","|"), db);
    	addLink(new WebLinks("Hadh 2001 DVD","2UpXnpq7XHo","|"), db);
    	addLink(new WebLinks("Haisiyat Ki Jung 2008","u0-fWJrrJWc","|"), db);
    	addLink(new WebLinks("Hamara Dil Aapke Paas Hai 2000 DVD HQ","j_vSBbyRiKQ","|"), db);
    	addLink(new WebLinks("Har Dil Jo Pyaar Karega 2000 DVD HQ","N4XM6Y7-KB4","|"), db);
    	addLink(new WebLinks("Haseena 2005","ax22mVgPA_I","|"), db);
    	addLink(new WebLinks("Hathyar 2002","F1HVTPv214g","|"), db);
    	addLink(new WebLinks("Hawas Ki Raat 2001 DVD","1nO-RGBt4XQ","|"), db);
    	addLink(new WebLinks("Hazaron Khwaishein Aisi 2005 DVD HQ","QqoNdAx16Kk","|"), db);
    	addLink(new WebLinks("Hello 2008 DVD HQ","ja3XCa8GcRk","|"), db);
    	addLink(new WebLinks("Hello Darling 2010 DVD","Lq6S5Sid4-g","|"), db);
    	addLink(new WebLinks("Hello Hum Lallann Bol Rahe Hain 2010","UmBYCvWdZ4M","|"), db);
    	addLink(new WebLinks("Hera Pheri 2000 DVD HQ","MmIW6qu8hHk","|"), db);
    	addLink(new WebLinks("Hide & Seek 2010 DVD","6cp8JGYecM8","|"), db);
    	addLink(new WebLinks("Hogi Pyar Ki Jeet 2006","K5s5Nk2VKqM","|"), db);
    	addLink(new WebLinks("Holiday 2005","FxLwIJsoPc4","|"), db);
    	addLink(new WebLinks("Hot Money 2006","SuKEe6Zi3s0","|"), db);
    	addLink(new WebLinks("Housefull 2010","9prx9br3VRg","|"), db);
    	addLink(new WebLinks("Hukumat Ki Jung 2008 DVD HQ","A6lSxiceO5A","|"), db);
    	addLink(new WebLinks("Hulchul 2004 DVD HQ","31M8mqXD21o","|"), db);
    	addLink(new WebLinks("Hulla 2008 DVD","HZdahne3evQ","|"), db);
    	addLink(new WebLinks("Hum Ho Gaye Aapke 2001 DVD HQ","6HRfEIvymxo","|"), db);
    	addLink(new WebLinks("Hum Kaun Hai 2004 DVD HQ","IhJ6ELwAqmg","|"), db);
    	addLink(new WebLinks("Hum Pyar Tumhi Se Kar Baithe 2002 DVD HQ","v5JqSc2Up70","|"), db);
    	addLink(new WebLinks("Hum Tum aur Mom 2005 DVD HQ","LwxzKXqFWGw","|"), db);
    	addLink(new WebLinks("Humdum 2005 DVD","sXtsR7zOWpM","|"), db);
    	addLink(new WebLinks("Humko Deewana Kar Gaye 2006 DVD HQ","r9XvIDA_Meg","|"), db);
    	addLink(new WebLinks("Humraaz 2002 DVD HQ","8OaVUN4TS2M","|"), db);
    	addLink(new WebLinks("Hungama 2003 DVD HQ","oqeUuaK1bPU","|"), db);
    	addLink(new WebLinks("Hyderabad Blues 2 2004","km3J_agKgzg","|"), db);
    	addLink(new WebLinks("Hyderabad Nawabs 2006","uKW_FPsFiB8","|"), db);
    	addLink(new WebLinks("I - Proud to be an Indian 2004 DVD HQ","fF03gxZamE0","|"), db);
    	addLink(new WebLinks("I Am Kalam 2010 DVD HQ","y-YWcdAu30Y","|"), db);
    	addLink(new WebLinks("Idiot Box 2010 DVD","Y-nkFD_1cPg","|"), db);
    	addLink(new WebLinks("Indian Kanoon 2001","MXyP7i7lspE","|"), db);
    	addLink(new WebLinks("Inspector Kiran 2004","cwq14jy7d3I","|"), db);
    	addLink(new WebLinks("Intequam 2009 DVD HQ","TgpI03It0jw","|"), db);
    	addLink(new WebLinks("International Don 2005 DVD","U6iLtvNZ4ec","|"), db);
    	addLink(new WebLinks("IPS Jhansi 2008 DVD HQ","xpNgW6dJqJI","|"), db);
    	addLink(new WebLinks("Iqbal 2005 DVD","LBZec7FEn4c","|"), db);
    	addLink(new WebLinks("Ishq Hai Tumse 2004 DVD","mVDXUHVDmQY","|"), db);
    	addLink(new WebLinks("Isi Life Mein 2010 DVD","2rMtpHnCBBc","|"), db);
    	addLink(new WebLinks("Jaago 2004 DVD","LXSXxKVxquQ","|"), db);
    	addLink(new WebLinks("Jaal 2008","7LRRWULfL_A","|"), db);
    	addLink(new WebLinks("Jaane Tu Ya Jaane Na 2008 DVD","9prx9br3VRg","|"), db);
    	addLink(new WebLinks("Jaani Dushman 2002 DVD HQ","Aqw8NNYMZmI","|"), db);
    	addLink(new WebLinks("Jaanwar 2006","QbDf8hYJPdY","|"), db);
    	addLink(new WebLinks("Jai Maha Sakthi 2009","Y9xssk5TO64","|"), db);
    	addLink(new WebLinks("Jai Santoshi Maa 2006 DVD","CyUkmHBW26g","|"), db);
    	addLink(new WebLinks("Jail 2009 DVD HQ","_fsuAf3YPoA","|"), db);
    	addLink(new WebLinks("Jajantaram Mamantaram 2003 DVD HQ","JDzlpVIErTc","|"), db);
    	addLink(new WebLinks("Janani 2006 DVD HQ","8_gZo2fAcsU","|"), db);
    	addLink(new WebLinks("Jawani Diwani 2006","htktQBBOOKk","|"), db);
    	addLink(new WebLinks("Jeena Sirf Merre Liye 2002 DVD","ARo9pMZoINo","|"), db);
    	addLink(new WebLinks("Jhankaar Beats 2003 DVD HQ","44dJq3mDMN0","|"), db);
    	addLink(new WebLinks("Jhootha Hi Sahi 2010 DVD HQ","TACYnYD5B_g","|"), db);
    	addLink(new WebLinks("Jigarwala 2009","y0DkGHLQ6m8","|"), db);
    	addLink(new WebLinks("Jimmy 2008 DVD HQ","7oCrrX2A7cQ","|"), db);
    	addLink(new WebLinks("Jis Desh Mein Ganga Rehta Hai 2000 DVD HQ","b2DlEQ4h1gc","|"), db);
    	addLink(new WebLinks("Jism 2003 DVD","tVAFQ4a4DJY","|"), db);
    	addLink(new WebLinks("Jo Bole So Nihaal 2005 DVD HQ","xX6Hb5JmFxc","|"), db);
    	addLink(new WebLinks("Joggers Park 2003 DVD","ood9T-u0LOo","|"), db);
    	addLink(new WebLinks("Joru Ka Ghulam 2000 DVD HQ","pAnDDPCCxtk","|"), db);
    	addLink(new WebLinks("Josh 2006 DVD","dpf9Fe70HwQ","|"), db);
    	addLink(new WebLinks("Judgement 2004","vywQfEW2_98","|"), db);
    	addLink(new WebLinks("Julie 2005","GOppw4YaaFY","|"), db);
    	addLink(new WebLinks("Jung Ka Elaan 2006 DVD","YJOf5gEjQ1E","|"), db);
    	addLink(new WebLinks("Jung Watan Ke Liye 2007","sWsaZxwN11o","|"), db);
    	addLink(new WebLinks("Just Married 2007 DVD HQ","e8Bs3uHGhIo","|"), db);
    	addLink(new WebLinks("Jyoti Bane Jwala 2006 DVD","fihuEcJRldY","|"), db);
    	addLink(new WebLinks("Kaaboo 2002 DVD","DFJUAp8lX44","|"), db);
    	addLink(new WebLinks("Kaali Pahadi 2000 DVD","Ow34mvYSd5I","|"), db);
    	addLink(new WebLinks("Kaalo 2010 DVD HQ","oYmXKIISoyE","|"), db);
    	addLink(new WebLinks("Kabhi Socha Bhi Na Tha 2005 DVD HQ","BcM737ej8AY","|"), db);
    	addLink(new WebLinks("Kabzaa 2010 DVD HQ","M4EroNPtr-M","|"), db);
    	addLink(new WebLinks("Kahin Pyaar Na Ho Jaaye 2000 DVD HQ","Cc21usGvBeY","|"), db);
    	addLink(new WebLinks("Kaisay Kahein 2007 DVD HQ","5PWVtBu7-cs","|"), db);
    	addLink(new WebLinks("Kajraare 2010 DVD HQ","WqKpl4b9jQk","|"), db);
    	addLink(new WebLinks("Kala Samrajya 2005","K80plDO5RrY","|"), db);
    	addLink(new WebLinks("Kalyug 2005 DVD","W6KpJdEN0HE","|"), db);
    	addLink(new WebLinks("Kanchivaram 2008 DVD HQ","eMVvhMzkEG4","|"), db);
    	addLink(new WebLinks("Kanoon Ki Lalkaar 2000","L6cVbNOcUvg","|"), db);
    	addLink(new WebLinks("Kanss 2004","ghlSDFDecbA","|"), db);
    	addLink(new WebLinks("Karan Poojari 2008 DVD","Howa71gldMc","|"), db);
    	addLink(new WebLinks("Kartoos 2010 DVD","BqTuk4viDmE","|"), db);
    	addLink(new WebLinks("Kasam 2001 DVD HQ","swd0U8G80X0","|"), db);
    	addLink(new WebLinks("Kasam Vardee Ki 2004","ebdBZIPE3hA","|"), db);
    	addLink(new WebLinks("Kasoor 2001 DVD HQ","1W5DndgbMOA","|"), db);
    	addLink(new WebLinks("Khakee 2004 DVD HQ","qtmtHgw3J4U","|"), db);
    	addLink(new WebLinks("Khakee 2009","WU8rbdfYOfI","|"), db);
    	addLink(new WebLinks("Khanjar 2003 DVD","s3_6EJkUa3k","|"), db);
    	addLink(new WebLinks("Khanna & Iyer 2007 DVD","7wkeqFoVdAU","|"), db);
    	addLink(new WebLinks("Khauff 2000 DVD HQ","o4Y8GGwDyjk","|"), db);
    	addLink(new WebLinks("Khel 2003 DVD","Ff-bZiiNxp8","|"), db);
    	addLink(new WebLinks("Khel Khiladi Ka 2008 DVD","AAjlqFoeGCE","|"), db);
    	addLink(new WebLinks("Khichdi 2010 DVD HQ","1yxZcC8qqx0","|"), db);
    	addLink(new WebLinks("Khoon Ka Karz 2008 DVD","SadszCz9pW0","|"), db);
    	addLink(new WebLinks("Khooni Kaala Jadu 2000","K31YSN4ghwU","|"), db);
    	addLink(new WebLinks("Khuda Gawah 2004 DVD HQ","NRkn4Rk5xfM","|"), db);
    	addLink(new WebLinks("Khushi 2003 DVD HQ","M1CrdJzhjWw","|"), db);
    	addLink(new WebLinks("Kisna 2005 DVD","mo6Y0pdJ8zs","|"), db);
    	addLink(new WebLinks("Kitne Door Kitne Paas 2002 DVD HQ","JMttjZSnEVs","|"), db);
    	addLink(new WebLinks("Knock Out 2010 DVD HQ","DeNTh1hqJ7Y","|"), db);
    	addLink(new WebLinks("Koi Hai 2003 DVD HQ","Z0JlpopnGlk","|"), db);
    	addLink(new WebLinks("Koi Mere Dil Mein Hai 2005 DVD HQ","RHrLmj12C0Q","|"), db);
    	addLink(new WebLinks("Kool Nahi Hot Hai Hum 2008 DVD HQ","r26UMVe6KUw","|"), db);
    	addLink(new WebLinks("Kranti 2008","zlof4Iip7ro","|"), db);
    	addLink(new WebLinks("Kranti 2002 DVD HQ","_vf6YAZ-A5I","|"), db);
    	addLink(new WebLinks("Kris Aur Krishna 2009 DVD HQ","DCf3X_pSzcY","|"), db);
    	addLink(new WebLinks("Krishna Cottage 2004 DVD HQ","lC1FMT5fRtw","|"), db);
    	addLink(new WebLinks("Krishna Tere Desh Mein 2001 DVD","Y9tYDnohxH4","|"), db);
    	addLink(new WebLinks("Kuch Tum Kahon Kuch Hum Kahein 2002 DVD HQ","gYGgffwnsoE","|"), db);
    	addLink(new WebLinks("Kuchh Meetha Ho Jaye 2005 DVD","PBTEucfIhvo","|"), db);
    	addLink(new WebLinks("Kushti 2010 DVD HQ","nO-Pq5Z13ts","|"), db);
    	addLink(new WebLinks("Kyaa Dil Ne Kahaa 2002 DVD HQ","avS4MZ_w-3o","|"), db);
    	addLink(new WebLinks("Kyaa Kool Hai Hum 2005 DVD HQ","3FzDA6vjEm8","|"), db);
    	addLink(new WebLinks("Kyo Kii Main Jhuth Nahin Bolta 2001 DVD HQ","i0m3oppt83w","|"), db);
    	addLink(new WebLinks("Kyun Ho Gaya Na 2004 DVD HQ","b-XeNV9v-58","|"), db);
    	addLink(new WebLinks("Lage Raho Munnabhai 2006 DVD HQ","PMxSUKMBy-s","|"), db);
    	addLink(new WebLinks("Lahore 2010 DVD","04w-RzSnps4","|"), db);
    	addLink(new WebLinks("Lakeer 2004 DVD HQ","p7bcLU0ROe0","|"), db);
    	addLink(new WebLinks("Lakhan 2007 DVD HQ","XgMNbwqVe_Q","|"), db);
    	addLink(new WebLinks("Life Express 2010 DVD","lAQyU-CczjU","|"), db);
    	addLink(new WebLinks("Life Mein Kabhie Kabhiee 2007 DVD","icy5odoleSA","|"), db);
    	addLink(new WebLinks("Loafer 2007 DVD HQ","8oECSuOatcQ","|"), db);
    	addLink(new WebLinks("Love Ka Tadka 2009 DVD HQ","D4Eh5OpV2cY","|"), db);
    	addLink(new WebLinks("Love Sex Aur Dhokha 2010 DVD","Kh0-aEk_D08","|"), db);
    	addLink(new WebLinks("Lucky 2005 DVD HQ","7nOXmI_gz6o","|"), db);
    	addLink(new WebLinks("Maa Ka Chamatkar 2004","sh1clmBOczE","|"), db);
    	addLink(new WebLinks("Maa O Maa 2008 DVD","QcbMHEIM6C8","|"), db);
    	addLink(new WebLinks("Maa Tujhhe Salaam 2002 DVD","MisgmyC31-g","|"), db);
    	addLink(new WebLinks("Maan Gaye Mughall E Azam 2008 DVD","k93F2lP3GTk","|"), db);
    	addLink(new WebLinks("Madhubaala 2006 DVD","eHogpfKb554","|"), db);
    	addLink(new WebLinks("Magic Robot 2010 DVD HQ","69tg--hnF6g","|"), db);
    	addLink(new WebLinks("Maha Nayak 2010","Y0Zw5BtTEtc","|"), db);
    	addLink(new WebLinks("Mahabali 2003 DVD","4SSdroYWcA0","|"), db);
    	addLink(new WebLinks("Maharathi 2008 DVD HQ","XrawidL5bMI","|"), db);
    	addLink(new WebLinks("Main Hoon Rakhwala 2000 DVD HQ","S2cil16BJtk","|"), db);
    	addLink(new WebLinks("Main Khunkhar Yodha 2000 DVD HQ","xFZiXm7RAm4","|"), db);
    	addLink(new WebLinks("Main Meri Patni Aur Woh 2005 DVD","tE8oX9mBNDk","|"), db);
    	addLink(new WebLinks("Main Prem Ki Diwani Hoon 2003 DVD","qgih1kOthL8","|"), db);
    	addLink(new WebLinks("Makdee 2002 DVD HQ","vdld5iACghM","|"), db);
    	addLink(new WebLinks("Mallika 2010 DVD HQ","WpuYP4d5xXQ","|"), db);
    	addLink(new WebLinks("Man Mandir Mein Maa 2009 DVD HQ","1i5TmkTYHoU","|"), db);
    	addLink(new WebLinks("Manthan Ek Kashmakash 2008 DVD","jXw63y1JoY8","|"), db);
    	addLink(new WebLinks("Mardon Wali Baat 2009 DVD","S1iHW8EkjMI","|"), db);
    	addLink(new WebLinks("Mardonwali Baat 2007","Ba9xk16zTks","|"), db);
    	addLink(new WebLinks("Marshal 2002","T7y5psjyQW0","|"), db);
    	addLink(new WebLinks("Marte Dum Tak 2002 DVD","YxfNx_ogmSM","|"), db);
    	addLink(new WebLinks("Maruti Mera Dost 2009 DVD HQ","5xJlqm5eW3k","|"), db);
    	addLink(new WebLinks("Mastaani 2005","6hm2-2H_OU8","|"), db);
    	addLink(new WebLinks("Masti 2004 DVD HQ","P_0fx5uNPnk","|"), db);
    	addLink(new WebLinks("Match Fixing 2002","Vy-LLEGyOQU","|"), db);
    	addLink(new WebLinks("Matrubhoomi 2005 DVD HQ","CWl85Akuxn0","|"), db);
    	addLink(new WebLinks("Mawali 2007 DVD HQ","efR85KSw7OE","|"), db);
    	addLink(new WebLinks("Meera 2007","kmzjI9W1m4Y","|"), db);
    	addLink(new WebLinks("Mera Dost Ghatothkach 2008","P1gnz_syCGA","|"), db);
    	addLink(new WebLinks("Mera Farz 2005","sZQX9qHGxsY","|"), db);
    	addLink(new WebLinks("Mera Kartavya 2009 DVD HQ","AT8hdVi0RYA","|"), db);
    	addLink(new WebLinks("Mere Baap Pehle Aap 2008 DVD HQ","UMAa148hojo","|"), db);
    	addLink(new WebLinks("Meri Aan 2001 DVD HQ","5b2R96RzADs","|"), db);
    	addLink(new WebLinks("Meri Aashiqui 2005 DVD","UlCIR3RaP3I","|"), db);
    	addLink(new WebLinks("Meri Badle Ki Aag 2006","zmKwHDHb1So","|"), db);
    	addLink(new WebLinks("Meri Hukumat 2003","e8ie51UDGEc","|"), db);
    	addLink(new WebLinks("Meri Ishq Ki Kahani 2001 DVD","cZaeEQXKWrA","|"), db);
    	addLink(new WebLinks("Meri Izzat 2001","etcMz1Hxlno","|"), db);
    	addLink(new WebLinks("Meri Jung 2006 DVD HQ","8vFm09hvr24","|"), db);
    	addLink(new WebLinks("Meri Parchaien 2000 DVD","EzuBGJ2SOio","|"), db);
    	addLink(new WebLinks("Meri Pyaari Bahania Banegi Dulhaniya 2001 DVD","jmKz-_x72XM","|"), db);
    	addLink(new WebLinks("Meri Sapath 2008 DVD","UnTIvK3q_Xk","|"), db);
    	addLink(new WebLinks("Meri Taaqat 2005 DVD","NktglsIzObw","|"), db);
    	addLink(new WebLinks("Meri Warrant 2010","oehXiAB8DsM","|"), db);
    	addLink(new WebLinks("Meri Zanjeer 2000 DVD HQ","LOuXeyxh9yM","|"), db);
    	addLink(new WebLinks("Miss India 2003 DVD","iF6WKrch524","|"), db);
    	addLink(new WebLinks("Mission 369 2008","ixwMVyWl2sI","|"), db);
    	addLink(new WebLinks("Mitti 2001 DVD HQ","hTptKh8f9Ok","|"), db);
    	addLink(new WebLinks("Mohandas 2009 DVD HQ","y_u8euCSW9g","|"), db);
    	addLink(new WebLinks("Mohe Bhool Gaye Saawariya 2007 DVD","rjaEBvKxj1c","|"), db);
    	addLink(new WebLinks("Mohre 2008 DVD","GeTK2sqGzBo","|"), db);
    	addLink(new WebLinks("Mr 100 Percent 2006","Eamrrkla2RI","|"), db);
    	addLink(new WebLinks("Mr Hindustani 2003","fe6-yn9nIN4","|"), db);
    	addLink(new WebLinks("Mr Singh & Mrs Mehta 2008 DVD","JmTMyp7mpfk","|"), db);
    	addLink(new WebLinks("Mr Ya Miss 2005 DVD","LM8fE0kL5e8","|"), db);
    	addLink(new WebLinks("Mudda 2003 DVD HQ","l32YADbNjNM","|"), db);
    	addLink(new WebLinks("Mujhe Jeene Do 2005","2sHriRLHPTU","|"), db);
    	addLink(new WebLinks("Mujhe Kucch Kehna Hai 2001 DVD","sO055F9yMCA","|"), db);
    	addLink(new WebLinks("Mukhbiir 2008 DVD HQ","onEAja3izJc","|"), db);
    	addLink(new WebLinks("Mulaqaat 2002","HB1dG-D6QGs","|"), db);
    	addLink(new WebLinks("Mumbai Matinee 2003 DVD HQ","JKV5pAFVS0g","|"), db);
    	addLink(new WebLinks("Mumbai Se Aaya Mera Dost 2003 DVD HQ","gUXWmYqE7CM","|"), db);
    	addLink(new WebLinks("Munnabhai MBBS 2003 DVD HQ","R1nL7IWlOg4","|"), db);
    	addLink(new WebLinks("Musaa 2010 DVD","QvNKPZqS3nI","|"), db);
    	addLink(new WebLinks("Muskaan 2004 DVD HQ","BuwPZZR8sQY","|"), db);
    	addLink(new WebLinks("Muskurake Dekh Zara 2010 DVD HQ","HPvTA6Ldwhw","|"), db);
    	addLink(new WebLinks("My Friend Ganesha 2 2008 DVD HQ","aiXjIsAoVXU","|"), db);
    	addLink(new WebLinks("My Name is Anthony Gonsalves 2008 DVD","wcUy509q9MM","|"), db);
    	addLink(new WebLinks("My Name is Khan 2010 DVD HQ","F--rHyEJrHI","|"), db);
    	addLink(new WebLinks("Nagin Ka Inteqaam 2009","sg6QmWDXtkA","|"), db);
    	addLink(new WebLinks("Naina 2005 DVD HQ","G46uMHBv9hE","|"), db);
    	addLink(new WebLinks("Nakshatra 2010 DVD HQ","CiH7NsJbP4w","|"), db);
    	addLink(new WebLinks("Naqaab 2007 DVD","ldy3L9ReCaI","|"), db);
    	addLink(new WebLinks("Narsimha 2004 DVD","L7xyFP-ipFA","|"), db);
    	addLink(new WebLinks("Naxalite 2000 DVD","xGb6tuVTOr8","|"), db);
    	addLink(new WebLinks("Naya Barood 2003 DVD HQ","A32AgdsMIII","|"), db);
    	addLink(new WebLinks("Naya Jigar 2007 DVD","2w2gIDYYdqE","|"), db);
    	addLink(new WebLinks("Naya Natwarlal 2007 DVD HQ","1o38RgQmF3I","|"), db);
    	addLink(new WebLinks("Naya Zalzala 2007","EOBS3em84EI","|"), db);
    	addLink(new WebLinks("Neel Kamal 2007 DVD HQ","ElIGMLUhUYE","|"), db);
    	addLink(new WebLinks("New Entery 2007 DVD HQ","p_KRxeQt1SI","|"), db);
    	addLink(new WebLinks("Om Bahadur 2007 DVD HQ","CGJXDcJsnvY","|"), db);
    	addLink(new WebLinks("Om Jai Jagadish 2002 DVD HQ","BAmONQdMYS0","|"), db);
    	addLink(new WebLinks("Om Shanti Om 2007 DVD HQ","Dk403n7TYlg","|"), db);
    	addLink(new WebLinks("One 2 Ka 4 2001 DVD","b-ck1rUGO_k","|"), db);
    	addLink(new WebLinks("Paap 2003 DVD HQ","AXWrMz0hW74","|"), db);
    	addLink(new WebLinks("Paisa Vasool 2004 DVD","zIwmjOCCS4M","|"), db);
    	addLink(new WebLinks("Parineeta 2005 DVD HQ","NRuT8gisTZU","|"), db);
    	addLink(new WebLinks("Pathar Aur Payal 2000 DVD HQ","j7chj4iTKls","|"), db);
    	addLink(new WebLinks("Payback 2010 DVD HQ","7Q84Fn0OyGk","|"), db);
    	addLink(new WebLinks("Paying Guests 2009 DVD HQ","3UlSRYx0HSI","|"), db);
    	addLink(new WebLinks("Pehli Nazar Ka Pehla Pyaar 2003 DVD HQ","v1SnG2YMD78","|"), db);
    	addLink(new WebLinks("People's Dada 2006","Giz3YoNLDQs","|"), db);
    	addLink(new WebLinks("Phas Gaye Re Obama 2010 DVD HQ","A5j9nAfDeGY","|"), db);
    	addLink(new WebLinks("Phir Ek Most Wanted 2010 DVD HQ","F7MC9-6rLm4","|"), db);
    	addLink(new WebLinks("Phir Ek Tehelka 2009 DVD HQ","etmsEBKs5mQ","|"), db);
    	addLink(new WebLinks("Phir Ek Toofan 2006","ItzxkS_M4e0","|"), db);
    	addLink(new WebLinks("Phir Hera Pheri 2006 DVD HQ","1FT6VOrFMLo","|"), db);
    	addLink(new WebLinks("Phir Tauba Tauba 2007 DVD","FeRF-NnIOe8","|"), db);
    	addLink(new WebLinks("Phool Aur Kante 2008 DVD","ldrjI8x8k9I","|"), db);
    	addLink(new WebLinks("Police Force 2004 DVD HQ","u4XjpyRRaKo","|"), db);
    	addLink(new WebLinks("Poochho Mere Dil Se 2004 DVD HQ","wDv1B_HCG5M","|"), db);
    	addLink(new WebLinks("Pranali 2008","yb0r0H35FBk","|"), db);
    	addLink(new WebLinks("Prarambh 2004 DVD","4aMKReQt2n4","|"), db);
    	addLink(new WebLinks("Pratighat 2006 DVD HQ","wwU5q0ZHEYA","|"), db);
    	addLink(new WebLinks("Premi 2005 DVD","E1Ds0WNjHHk","|"), db);
    	addLink(new WebLinks("Pukar 2000 DVD HQ","wJvMeo1kwTc","|"), db);
    	addLink(new WebLinks("Pyaar Ke Side Effects 2006 DVD HQ","svByqTtmSb4","|"), db);
    	addLink(new WebLinks("Pyaar Mein Kabhi Kabhi 2000 DVD","mn4CPdV1sis","|"), db);
    	addLink(new WebLinks("Pyaar Tune Kya Kiya 2001","uENQh5xGrCY","|"), db);
    	addLink(new WebLinks("Pyaasa 2002 DVD HQ","bn5_lcDy9uw","|"), db);
    	addLink(new WebLinks("Pyar Karenge Pal Pal 2008","cn3ffgM1J7A","|"), db);
    	addLink(new WebLinks("Qaidi 2002 DVD","___8WMyK9yw","|"), db);
    	addLink(new WebLinks("Qayamat 2003 DVD","piTKnq0I7Ew","|"), db);
    	addLink(new WebLinks("Raat Gayi Baat Gayi 2009 DVD HQ","Lo4tKWXNW14","|"), db);
    	addLink(new WebLinks("Raavan 2010 DVD HQ","HKKfQL48who","|"), db);
    	addLink(new WebLinks("Raaz The Mystery Continues 2009 DVD","9prx9br3VRg","|"), db);
    	addLink(new WebLinks("Rafta Rafta 2006 DVD","bZSiWTlW5b4","|"), db);
    	addLink(new WebLinks("Rahul 2001 DVD","m-lvQadfX3k","|"), db);
    	addLink(new WebLinks("Raja Bhai Lagey Raho 2005","R_C22y95VJw","|"), db);
    	addLink(new WebLinks("Raja Ko Rani Se Pyar Ho Gaya 2000 DVD HQ","GJlCAOeMkp4","|"), db);
    	addLink(new WebLinks("Rakshak 2004","mo-frEZaAJo","|"), db);
    	addLink(new WebLinks("Ram Lakhan 2008","lZ0-O1sOE80","|"), db);
    	addLink(new WebLinks("Ram Rahim Robbert 2009","poFkJomsrVU","|"), db);
    	addLink(new WebLinks("Rama Rama Kya Hai Dramaaa 2007 DVD HQ","V2m0tVd7BYk","|"), db);
    	addLink(new WebLinks("Ramaa The Saviour 2010 DVD HQ","JLDirqJlQeg","|"), db);
    	addLink(new WebLinks("Ramayana 2010 DVD HQ","yQd5GdVHuqY","|"), db);
    	addLink(new WebLinks("Ramgadh Ki Ramkali 2001 DVD HQ","YQ90sxfxAcU","|"), db);
    	addLink(new WebLinks("Rampuri Damaad 2007","7E6rhpKX_XM","|"), db);
    	addLink(new WebLinks("Ramraaj 2008 DVD HQ","Glfez4j_z5g","|"), db);
    	addLink(new WebLinks("Rann 2010 DVD HQ","KXIDCfoj8Qw","|"), db);
    	addLink(new WebLinks("Red 2007 DVD","tUzvc-7qRqU","|"), db);
    	addLink(new WebLinks("Red Alert 2010 DVD","Nvw3V5_8s5Y","|"), db);
    	addLink(new WebLinks("Refugee 2000 DVD","S7SQVVjgG_8","|"), db);
    	addLink(new WebLinks("Rehnaa Hai Terre Dil Mein 2001 DVD","RXc6C-VFUuY","|"), db);
    	addLink(new WebLinks("Reshma aur Sultaan 2002","3aquLD1ongw","|"), db);
    	addLink(new WebLinks("Return Of Zid 2006","jm8IPFoxxSU","|"), db);
    	addLink(new WebLinks("Revati 2005 DVD","DtuZ-SaczLY","|"), db);
    	addLink(new WebLinks("Right Yaaa Wrong 2010 DVD","SL9Qghn7cvQ","|"), db);
    	addLink(new WebLinks("Rishtey 2002 DVD HQ","2hTYL-wGHcI","|"), db);
    	addLink(new WebLinks("Riwayat 2010 DVD HQ","uD-EW116Axg","|"), db);
    	addLink(new WebLinks("Road to Sangam 2010 DVD","A2MLlYzcXdU","|"), db);
    	addLink(new WebLinks("Robbery 2005 DVD","FY3KsuaWeMI","|"), db);
    	addLink(new WebLinks("Rocky 2006 DVD HQ","itIRa5ITQ9g","|"), db);
    	addLink(new WebLinks("Rog 2005 DVD","i1oQ9kTfxFs","|"), db);
    	addLink(new WebLinks("Rudraksh 2004 DVD HQ","tOYoeTttzWU","|"), db);
    	addLink(new WebLinks("Run 2004 DVD HQ","EhgxvJF54QY","|"), db);
    	addLink(new WebLinks("Runway 2009 DVD HQ","6SMlGmqgEMo","|"), db);
    	addLink(new WebLinks("Saathi 2005","NjVY_XW9zqs","|"), db);
    	addLink(new WebLinks("Saawan 2006 DVD HQ","AQZhfUaMBWc","|"), db);
    	addLink(new WebLinks("Sabse Bada Khiladi 2009","Mn0Z5qt1HyM","|"), db);
    	addLink(new WebLinks("Sabse bada Mawali 2009","z_fdfXLU5JY","|"), db);
    	addLink(new WebLinks("Sabse Badhkar Hum 2007 DVD","2PEIzt6Mxa0","|"), db);
    	addLink(new WebLinks("Sabse Badhkar Hum 2010 DVD HQ","MzSq1oPhJgI","|"), db);
    	addLink(new WebLinks("Sachhai Ki Taaqat 2005 DVD HQ","zLrqrjA92Wg","|"), db);
    	addLink(new WebLinks("Sadiyaan 2010 DVD","T8dmWr-CWy8","|"), db);
    	addLink(new WebLinks("Samay 2003 DVD HQ","x41QnX-zGUU","|"), db);
    	addLink(new WebLinks("Sanak 2007 DVD HQ","_6lbWqu-o7c","|"), db);
    	addLink(new WebLinks("Sanam Teri Kasam 2009 DVD HQ","nNwBnlatnb4","|"), db);
    	addLink(new WebLinks("Sankat City 2009","UyY_2mQYShM","|"), db);
    	addLink(new WebLinks("Sarhad Paar 2007 DVD HQ","FcyrHJ0aSRg","|"), db);
    	addLink(new WebLinks("Sati Ki Shakti 2010 DVD","lfNwAPNnS1g","|"), db);
    	addLink(new WebLinks("Sati Sukanya 2009 DVD","TaDGrRjwW_E","|"), db);
    	addLink(new WebLinks("Saugandh Gita Ki 2001","Kq7WbuLKESE","|"), db);
    	addLink(new WebLinks("Shaadi Ka Laddoo 2004 DVD HQ","meg2fNQdXcw","|"), db);
    	addLink(new WebLinks("Shaadi No. 1 2005 DVD HQ","e6je56YzE50","|"), db);
    	addLink(new WebLinks("Shaadi Se Pehle 2006 DVD HQ","wmptnsEmQLU","|"), db);
    	addLink(new WebLinks("Shaapit 2010 DVD HQ","U4Uh4S8Jb1w","|"), db);
    	addLink(new WebLinks("Shabd 2005 DVD HQ","f6H4lMpi8WY","|"), db);
    	addLink(new WebLinks("Shadow 2009 DVD HQ","mfj0J4NxWVM","|"), db);
    	addLink(new WebLinks("Shah Ji Ki Advice 2006","GX6YtMWAbeA","|"), db);
    	addLink(new WebLinks("Shaktishali Shiva 2008","01MfVZufCN4","|"), db);
    	addLink(new WebLinks("Shankar Ustad 2007","1k2wNX6G-yg","|"), db);
    	addLink(new WebLinks("Shapath 2009 DVD HQ","9bm7abXMLW8","|"), db);
    	addLink(new WebLinks("Sher Dil 2010","CZH16BAxcqs","|"), db);
    	addLink(new WebLinks("Sherni Ka Shikaar 2001 DVD","3iR9Va3eb30","|"), db);
    	addLink(new WebLinks("Shirdi Sai Baba 2001 DVD HQ","-3woM_0XwVk","|"), db);
    	addLink(new WebLinks("Shiv Bhakt 2009 DVD HQ","4fOll2Gnr7w","|"), db);
    	addLink(new WebLinks("Shiv Senapati 2009","7k7cTOLYn5Y","|"), db);
    	addLink(new WebLinks("Showbiz 2007 DVD","NYDyHOekJLE","|"), db);
    	addLink(new WebLinks("Sikandar 2009 DVD HQ","E8EolK153LE","|"), db);
    	addLink(new WebLinks("Sindoor Ki Saugandh 2002","WirIXtGjZCI","|"), db);
    	addLink(new WebLinks("Sindoor Mange Khoon 2001 DVD","-1iFr3Y3X1I","|"), db);
    	addLink(new WebLinks("Sipahi 2009","Qq_lAqVPLlg","|"), db);
    	addLink(new WebLinks("Sri Raghavendra 2007 DVD HQ","Ov-rUpqbZNU","|"), db);
    	addLink(new WebLinks("Straight 2009 DVD HQ","yHs9-7kQ2J8","|"), db);
    	addLink(new WebLinks("Strangers 2007 DVD HQ","bS60VFmLq0Q","|"), db);
    	addLink(new WebLinks("Stumped 2003 DVD HQ","flAlhmsdFd0","|"), db);
    	addLink(new WebLinks("Sukra 2007","W-47uZDPl10","|"), db);
    	addLink(new WebLinks("Sur 2002 DVD HQ","h5vHACpaRUg","|"), db);
    	addLink(new WebLinks("Swaha 2010 DVD","APVjVS4-42w","|"), db);
    	addLink(new WebLinks("Swami 2007 DVD HQ","I93iMZhhZaU","|"), db);
    	addLink(new WebLinks("Taarzan 2004 DVD HQ","zFNRIpgvg7c","|"), db);
    	addLink(new WebLinks("Tadap 2009 DVD","cxQqCrYi6oA","|"), db);
    	addLink(new WebLinks("Tadipaar 2010 DVD HQ","1S5X_ttbbDE","|"), db);
    	addLink(new WebLinks("Tahaan 2008 DVD HQ","VOkK4urA3Pg","|"), db);
    	addLink(new WebLinks("Takkar 2009","iF7yIFD6BC8","|"), db);
    	addLink(new WebLinks("Tantra Mantra 2009 DVD HQ","rYFuPcZaiDU","|"), db);
    	addLink(new WebLinks("Tarkieb 2000 DVD HQ","d7QgQPRq850","|"), db);
    	addLink(new WebLinks("Teen Patti 2010 DVD HQ","OjjhPxtWlZ0","|"), db);
    	addLink(new WebLinks("Teesri Aankh 2008 DVD HQ","OwtumoVhNno","|"), db);
    	addLink(new WebLinks("Tera Jadoo Chal Gayaa 2000 DVD HQ","1o34pG8WPY8","|"), db);
    	addLink(new WebLinks("Tera Mera Saath Rahen 2001 DVD HQ","p0MQ0qslcPw","|"), db);
    	addLink(new WebLinks("Tezaab 2003","Ro-tB9Hijrc","|"), db);
    	addLink(new WebLinks("Thakur Bhavani Singh 2007","m0iaqNu1N9Y","|"), db);
    	addLink(new WebLinks("Thanks Maa 2010 DVD HQ","VIuQW_MgwNA","|"), db);
    	addLink(new WebLinks("The Boss 2010 DVD HQ","zzMY2lGX_Us","|"), db);
    	addLink(new WebLinks("The Film Emotional Atyachar 2010 DVD HQ","KEZtwGWwWIc","|"), db);
    	addLink(new WebLinks("The Great Kalicharan 2007 DVD HQ","6IJMEHRI9_w","|"), db);
    	addLink(new WebLinks("The Hungama 2010 DVD HQ","GkQ0fz0O8gI","|"), db);
    	addLink(new WebLinks("The Last Lear 2008 DVD HQ","FYou_gtxUjU","|"), db);
    	addLink(new WebLinks("The Real Dostana 2008","DOMoB1tGd24","|"), db);
    	addLink(new WebLinks("The Return of Tezaab 2009 DVD","bkNM945tX6w","|"), db);
    	addLink(new WebLinks("The Returns of Zid 2009 DVD HQ","xDfH25vD2Uw","|"), db);
    	addLink(new WebLinks("Thodi Life Thoda Magic 2008 DVD HQ","JSedlaTLUy4","|"), db);
    	addLink(new WebLinks("Tirupati Shree Balaji 2006 DVD HQ","M7cLtZZSuA0","|"), db);
    	addLink(new WebLinks("Traffic Signal 2007 DVD HQ","7xBoQa8YY4s","|"), db);
    	addLink(new WebLinks("Trump Card 2010 DVD","ZgML4ONgEEg","|"), db);
    	addLink(new WebLinks("Tum 2004 DVD HQ","pntkYAIBHsA","|"), db);
    	addLink(new WebLinks("Tum Bin 2001 DVD HQ","bDMRYWCvsgE","|"), db);
    	addLink(new WebLinks("Tum Milo Toh Sahi 2010","9lyxzjmDUhE","|"), db);
    	addLink(new WebLinks("Tumko Na Bhool Paayenge 2002","iuITOvGvsRg","|"), db);
    	addLink(new WebLinks("Uljhan 2001","dp4-CdBuYAE","|"), db);
    	addLink(new WebLinks("Utthaan 2006 DVD","kuvOtTEVW_4","|"), db);
    	addLink(new WebLinks("Uuf Kya Jaadoo Mohabbat Hai 2004 DVD","YR30lFliAjM","|"), db);
    	addLink(new WebLinks("Vaada 2005 DVD","0XtrCa8OIws","|"), db);
    	addLink(new WebLinks("Vaah Life Ho Toh Aisi 2005 DVD","AOYkNaPpVnk","|"), db);
    	addLink(new WebLinks("Vaishnavi 2010","eEkW_SAjqhA","|"), db);
    	addLink(new WebLinks("Vajra 2004","9ZsY6q-VksQ","|"), db);
    	addLink(new WebLinks("Vajra 2006 DVD HQ","cQQz1Q_LEts","|"), db);
    	addLink(new WebLinks("Vardi Tujhe Salaam 2009 DVD","emIS17C8bQ4","|"), db);
    	addLink(new WebLinks("Veer Madakari 2009 DVD HQ","cwvtULs6Mb0","|"), db);
    	addLink(new WebLinks("Victoria No. 203 2007 DVD","2h6xMrIxORE","|"), db);
    	addLink(new WebLinks("Videsh - Heaven on Earth 2009 DVD HQ","oNe9-BlLg_A","|"), db);
    	addLink(new WebLinks("Vijaypath 2009","aEDNQj1bnGY","|"), db);
    	addLink(new WebLinks("Vikram 2000 DVD HQ","kILE-Hq8WOs","|"), db);
    	addLink(new WebLinks("Vivah 2006 DVD HQ","R9upjxi7_lk","|"), db);
    	addLink(new WebLinks("Vroom 2010 DVD HQ","wHZwiskmtr8","|"), db);
    	addLink(new WebLinks("Welcome 2007 DVD HQ","bOqFGkFO3Lo","|"), db);
    	addLink(new WebLinks("Winner 2007 DVD HQ","zDqQgUXhcmw","|"), db);
    	addLink(new WebLinks("Xcuse Me 2003 DVD","Q7wc4hqL0BM","|"), db);
    	addLink(new WebLinks("Yaadein 2001 DVD HQ","rw0ePOHm73U","|"), db);
    	addLink(new WebLinks("Yakeen 2005 DVD","ZpmsKEsqUjU","|"), db);
    	addLink(new WebLinks("Yatra 2007 DVD HQ","eJxvN87P8XY","|"), db);
    	addLink(new WebLinks("Yeh Dil 2003 DVD","RirwT7HjgQs","|"), db);
    	addLink(new WebLinks("Yeh Diljale 2008","ZtA-D_m4-ZM","|"), db);
    	addLink(new WebLinks("Yeh Hai Jalwa 2002 DVD HQ","A7suQ4FZ3UE","|"), db);
    	addLink(new WebLinks("Yeh Kaisa Karz 2008 DVD HQ","ueSTOqmJSJA","|"), db);
    	addLink(new WebLinks("Yeh Kaisi Hukumat 2006 DVD","9F0WCtyUMFw","|"), db);
    	addLink(new WebLinks("Yeh Raaste Hain Pyaar Ke 2001 DVD","URt1uWBUfl8","|"), db);
    	addLink(new WebLinks("Yuddh 2008","MeqJZxE36EI","|"), db);
    	addLink(new WebLinks("Yudhveer 2008 DVD","WTb39N8eiBQ","|"), db);
    	addLink(new WebLinks("Yun Hota Toh Kya Hota 2006","9-90NmSXkb4","|"), db);
    	addLink(new WebLinks("Yuva 2004 DVD HQ","xaETSjOn3Nc","|"), db);
    	addLink(new WebLinks("Zakhmi Aurat 2008","GyucYZKcqrI","|"), db);
    	addLink(new WebLinks("Zameen 2003 DVD HQ","HT4Fq65YI3k","|"), db);
    	addLink(new WebLinks("Zameer 2005 DVD HQ","R_IDDagQgXw","|"), db);
    	addLink(new WebLinks("Zehreeli Nagin 2008 DVD","jwfMKBwWYQA","|"), db);
    	addLink(new WebLinks("Zindagi 2001 DVD HQ","rqBo7HwLtlM","|"), db);


    	
    			
    			
    	
    }
}
