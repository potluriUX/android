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
    private static final int DATABASE_VERSION = 11;
 
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
        String selectQuery = "SELECT * FROM " + TABLE_CONTACTS + " ORDER BY RANDOM() LIMIT 15";//"SELECT  * FROM " + TABLE_CONTACTS;
     
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
		
		
    	
    	addLink(new WebLinks("Kaanchi 2014  HD" ,"_L8sOLoWMjk" ,"|"), db);
    			addLink(new WebLinks("Holiday Akshay Kumar 2014  HD" ,"_013JOm8bIo" ,"|"), db);
    			addLink(new WebLinks("Rush - HD | Emraan Hashmi 2014" ,"IEoRL9wDQsw" ,"|"), db);
    			addLink(new WebLinks("BA Pass - 2014" ,"r51mlHAcEIY" ,"|"), db);
    			addLink(new WebLinks("Baat Bann Gayi 2014" ,"ihFkSVI4Sgc" ,"|"), db);
    			addLink(new WebLinks("Vidhwanshak The Destroyer (Ayan) - Suriya" ,"Hz-D7v8lQBk" ,"|"), db);
    			addLink(new WebLinks("Singham Returns 2014" ,"Ti-4DxSs4W4" ,"|"), db);
    			addLink(new WebLinks("Singham Returns (2014) Part 1" ,"AjQd8qXjBc0" ,"|"), db);
    			addLink(new WebLinks("LOOT 2014" ,"jqE3GLz3ocA" ,"|"), db);
    			addLink(new WebLinks("Singham Returns 2014" ,"GppcN9-rmZE" ,"|"), db);
    			addLink(new WebLinks("Ishkq in Paris movies 2014" ,"J8qEGwpgvNA" ,"|"), db);
    			addLink(new WebLinks("Sixteen 2014" ,"H4CTKgtsgzU" ,"|"), db);
    			addLink(new WebLinks("Hasee Toh Phasee 2014" ,"ANo0Su92YIw" ,"|"), db);
    			addLink(new WebLinks("Jai Ho 2014 Salman Khan" ,"VBYtoAcbzGc" ,"|"), db);
    			addLink(new WebLinks("Youngistaan 2014 DVDRip 480P ESubs" ,"79aT7FQzhTs" ,"|"), db);
    			addLink(new WebLinks("Lucky The Racer 2014 Dubbed - PlayMovie.Co" ,"pYkM4TN02gQ" ,"|"), db);
    			addLink(new WebLinks("Kick Full Movie 2014 (HD)" ,"ynMomoW8JEo" ,"|"), db);
    			addLink(new WebLinks("Salman Khan kick 2014" ,"BRSFvFEfrNc" ,"|"), db);
    			addLink(new WebLinks("EK: VILLAIN 2014" ,"s88NMqL760E" ,"|"), db);
    			addLink(new WebLinks("Bin Phere Free Me Tere 2013 HD" ,"48uOlfuFmeE" ,"|"), db);

    			addLink(new WebLinks("Main Tera Hero - 2014" ,"kACNemYofFw" ,"|"), db);
    			addLink(new WebLinks("Stalin - Chiranjeevi Dubbed Hindi Movies 2014" ,"dyGLejLwUXw" ,"|"), db);



    			addLink(new WebLinks("Hawaa Hawaai 2014 DVD HQ Saqib Saleem","UUYkass6wbo","|"), db);
    			addLink(new WebLinks("Main Tera Hero 2014 DVD HQ Varun Dhawan","hUQ_A10swFQ","|"), db);
    			addLink(new WebLinks("Ragini MMS 2 2014 DVD HQ Sunny Leone","qJX9IbJTLxQ","|"), db);
    			addLink(new WebLinks("Karar 2014 DVD Mahek Chhal","zoQLbNHe0yU","|"), db);
    			addLink(new WebLinks("Paranthe Wali Gali 2014 DVD HQ Anuuj Saxena","5eX6Shul0jA","|"), db);
    			addLink(new WebLinks("Gangotri 2014 DVD HQ Allu Arjun","pW89Woe_bzc","|"), db);
    			addLink(new WebLinks("Betting Raja 2014 DVD HQ Ram Charan","twQ7AJf-6Ks","|"), db);
    			addLink(new WebLinks("Kismet Love Paisa Dilli  Vivek Oberoi 2012" ,"NAQFHm24dSg" ,"|"), db);
    			addLink(new WebLinks("Alex Pandian 2014 DVD HQ Karthi","QmpDrlHjb2c","|"), db);
    			addLink(new WebLinks("Captain Nagarjuna 2014 DVD HQ Nagarjuna","95LLpT6CB9M","|"), db);
    			addLink(new WebLinks("Double Attack 2014 DVD HQ Ram Charan","F3F1UIT4DkY","|"), db);
    			addLink(new WebLinks("Shaktimaan 2014 DVD HQ Dileep","AsLyiLPPYmI","|"), db);
    			addLink(new WebLinks("Jackpot 2013 DVD HQ Sunny Leone","FJOFwgUTBTw","|"), db);
    			addLink(new WebLinks("Ishk Actually 2013 DVD HQ Rajeev Khandelwal","r2incN-izoo","|"), db);
    			addLink(new WebLinks("16 December 2002 DVD Milind Soman","lW76wRuJeVo","|"), db);
    			addLink(new WebLinks("1971 2007 DVD Manoj Bajpai","upx7w2M99e4","|"), db);
    			addLink(new WebLinks("3 Idiots 2009 DVD HQ Aamir Khan","vgUWkR11Roc","|"), db);
    			addLink(new WebLinks("3 Nights 4 Days 2009 Anuj Sawhney","VG_CrbERvrg","|"), db);
    			addLink(new WebLinks("50 Lakh 2007 DVD Pawan Malhotra","e3RHpAbbR9E","|"), db);
    			addLink(new WebLinks("8x10 Tasveer 2009 Akshay Kumar","3pS2WcsVHE0","|"), db);
    			addLink(new WebLinks("99.9 FM 2005 Shahwar Ali","Cm0-NnhvKSA","|"), db);
    			addLink(new WebLinks("Aabra Ka Daabra 2004 DVD Athit Naik","l2Fv5XpcpxE","|"), db);
    			addLink(new WebLinks("Aaghaaz 2000 DVD Sunil Shetty","0tx-PP2UXCk","|"), db);
    			addLink(new WebLinks("Aaj Ka Boss 2008 Mithun Chakraborty","ry8GimM1tGk","|"), db);
    			addLink(new WebLinks("Aaj Ka Maseeha 2009 DVD Thriller Manju","sCm3Biu-Dyk","|"), db);
    			addLink(new WebLinks("Aaj Ka Nanha Farishta 2000 Raj Babbar","TpYVG5cnI6Q","|"), db);
    			addLink(new WebLinks("Aaj Ka Naya Khiladi 2010 DVD HQ Nitin","XT1tgwJd3Wc","|"), db);
    			addLink(new WebLinks("Aaj Ki Dadagiri 2009 Krishna Gowda","eIlStN0MGLU","|"), db);
    			addLink(new WebLinks("Aakrosh 2010 DVD HQ Ajay Devgan","ubQFV9TLw7E","|"), db);
    			addLink(new WebLinks("Aamdani Athanni Kharcha Rupaiya 2001 DVD HQ Govinda","FevDWhZUMWk","|"), db);
    			addLink(new WebLinks("Aan 2004 DVD HQ Shatrughan Sinha","ILj7QOXaK7k","|"), db);
    			addLink(new WebLinks("Aanch 2003 DVD HQ Nana Patekar","QR7jH2M_7Iw","|"), db);
    			addLink(new WebLinks("Aap Ki Khatir 2006 DVD Akshay Khanna","1esknXWX3m4","|"), db);
    			addLink(new WebLinks("Aap Mujhe Achche Lagne Lage 2002 DVD Hrithik Roshan","CBAGJfvyRBc","|"), db);
    			addLink(new WebLinks("Aapko Pehle Bhi Kahin Dekha Hai 2002 DVD HQ Priyanshu","J-QLBwRRKKo","|"), db);
    			addLink(new WebLinks("Aarya 2 2009 DVD HQ Allu Arjun","r8NLT2Hxbb4","|"), db);
    			addLink(new WebLinks("Aaryan Mera Naam 2010 DVD HQ Mohanlal","ehakCegcle8","|"), db);
    			addLink(new WebLinks("Ab Bas 2004 DVD HQ Shahwar Ali","8rdYHWN-zAo","|"), db);
    			addLink(new WebLinks("Accident on Hill Road 2009 DVD HQ Farooq Sheikh","LBkjeTBol4k","|"), db);
    			addLink(new WebLinks("Action Replayy 2010 DVD HQ Akshay Kumar","t2Qwb-mTP_w","|"), db);
    			addLink(new WebLinks("Ada 2010 DVD HQ Ayaan Ahmad","euACk54N624","|"), db);
    			addLink(new WebLinks("Admissions Open 2010 DVD Anupam Kher","n_Hs086aLkE","|"), db);
    			addLink(new WebLinks("Agni Putra 2000 DVD HQ Mithun Chakraborty","lQUNojQqTPE","|"), db);
    			addLink(new WebLinks("Agni Varsha 2002 DVD Amitabh Bachchan","fZYHM_3V0aQ","|"), db);
    			addLink(new WebLinks("Akhiyon Se Goli Maare 2002 DVD HQ Govinda","Exv9EJoEuG4","|"), db);
    			addLink(new WebLinks("Aksar 2006 DVD HQ Emraan Hashmi","naBbS4x3dk4","|"), db);
    			addLink(new WebLinks("Allah Ke Banday 2010 DVD HQ Sharman Joshi","_1xgZLSSYOs","|"), db);
    			addLink(new WebLinks("Amu 2005 DVD HQ Konkona Sen Sharma","-Nz-A38JQJM","|"), db);
    			addLink(new WebLinks("Anamika 2008 DVD HQ Dino Morea","2-eww7BI8Q0","|"), db);
    			addLink(new WebLinks("Andolan 2010 DVD Srihari","LASLApiIfyw","|"), db);
    			addLink(new WebLinks("Anjaane 2005 DVD Sanjay Kapoor","N06IPNkO7gw","|"), db);
    			addLink(new WebLinks("Annarth 2002 DVD Sanjay Dutt","utfTvFXXRwc","|"), db);
    			addLink(new WebLinks("Anthony Kaun Hai 2006 Sanjay Dutt","X4CRITAnguk","|"), db);
    			addLink(new WebLinks("Anwar 2007 Manisha Koirala","U4btOIUYaqE","|"), db);
    			addLink(new WebLinks("Apna Asmaan 2007 DVD HQ Irfan Khan","S0NIRoUHsXA","|"), db);
    			addLink(new WebLinks("Apne 2007 DVD HQ Dharmendra","zJ7pLSoEGaQ","|"), db);
    			addLink(new WebLinks("Apsara 2000 Avinash","q7Buapp9nX0","|"), db);
    			addLink(new WebLinks("Arjun Devaa 2001 DVD HQ Mithun Chakraborty","IZ040k_B6eQ","|"), db);
    			addLink(new WebLinks("Armaan 2003 DVD HQ Amitabh Bachchan","1Q-65PZNFYo","|"), db);
    			addLink(new WebLinks("Arya 2009 Allu Arjun","b7S4iTa6Pz0","|"), db);
    			addLink(new WebLinks("Ashok Chakra 2010 DVD Rajan Verma","hLKLx1sm_Xo","|"), db);
    			addLink(new WebLinks("Aur Ek Ilzaam 2007 Sumanth","_rVTy6Inzrk","|"), db);
    			addLink(new WebLinks("Aur Ek Takkar 2009 DVD HQ Rajeev Kanakala","YFHPbTN7xII","|"), db);
    			addLink(new WebLinks("Awara Paagal Deewana 2002 DVD HQ Akshay Kumar","x_oJPi3deKc","|"), db);
    			addLink(new WebLinks("Baabul 2006 DVD HQ Amitabh Bachchan","2rUJ_2iPIdA","|"), db);
    			addLink(new WebLinks("Baadal 2004 DVD HQ Prabhas","TEWFaOjDfJ8","|"), db);
    			addLink(new WebLinks("Bachke Rehna Re Baba 2005 Rekha","Zs30kKnBPL0","|"), db);
    			addLink(new WebLinks("Bada Beta 2008 Vishnuvardhan","ytiJ1RJvUzw","|"), db);
    			addLink(new WebLinks("Badla 2007 DVD HQ Ravi Teja","PlWhggHfXvU","|"), db);
    			addLink(new WebLinks("Badmash No 1 2010 DVD HQ Kalyan Ram","gfZHg1rsv20","|"), db);
    			addLink(new WebLinks("Badmasho ka Badmash 2009 DVD Vijay Raghavendra","bmlKSYR5f1E","|"), db);
    			addLink(new WebLinks("Baghban 2003 DVD HQ Amitabh Bachchan","ZupARvlOyxg","|"), db);
    			addLink(new WebLinks("Balram 2006 DVD Balakrishna","vQGouj6F5CM","|"), db);
    			addLink(new WebLinks("Barah Aana 2009 Naseeruddin Shah","zA_t2wZsQUY","|"), db);
    			addLink(new WebLinks("Bardaasht 2004 DVD HQ Bobby Deol","z0PJxMYOhMw","|"), db);
    			addLink(new WebLinks("Barish 2004 Prabhas","8gcEQp6OGFM","|"), db);
    			addLink(new WebLinks("Bedardi 2008 DVD Jatin Ramesh","TJOWA0QH_mg","|"), db);
    			addLink(new WebLinks("Berozgaar 2010 DVD HQ Mast Ali","0jrIjRLl2Js","|"), db);
    			addLink(new WebLinks("Bewafaa 2005 DVD Anil Kapoor","83VIWiFfeC4","|"), db);
    			addLink(new WebLinks("Bhagam Bhag 2006 DVD HQ Akshay Kumar","qVWxN_4usLc","|"), db);
    			addLink(new WebLinks("Bhairav 2001 Mithun Chakraborty","Ox9jaPW9QIs","|"), db);
    			addLink(new WebLinks("Bhavnao Ko Samjho 2010 DVD HQ Raju Shrivastava","Ny_hgLyXrmY","|"), db);
    			addLink(new WebLinks("Bheeshma Pratigyaa 2010 DVD Ranjeet","hXPt9o0EgL0","|"), db);
    			addLink(new WebLinks("Bhoot Uncle 2006 DVD HQ Jackie Shroff","LlFLYCwjLpY","|"), db);
    			addLink(new WebLinks("Bindaas 2010 DVD Puneeth Raj Kumar","-qBC8oKF9UQ","|"), db);
    			addLink(new WebLinks("Black & White 2008 DVD HQ Anil Kapoor","OflKFobKz6c","|"), db);
    			addLink(new WebLinks("Blue Oranges 2009 DVD HQ Rajit Kapoor","Ivy_y2pAfaw","|"), db);
    			addLink(new WebLinks("Bolo Raam 2010 DVD Rishi Bhutani","eK0u3lGdBD8","|"), db);
    			addLink(new WebLinks("Bombay to Bangkok 2008 DVD HQ Shreyas Talpade","y5io5UdxCwE","|"), db);
    			addLink(new WebLinks("Bombay To Goa 2007 DVD HQ Sunil Pal","sEhxQvjU3g8","|"), db);
    			addLink(new WebLinks("Border Hindustan Ka 2003 DVD HQ Akshay Khanna","zskJkaj0-a4","|"), db);
    			addLink(new WebLinks("Bumm Bumm Bole 2010 DVD HQ Darsheel Safary","BV25J4a7iOQ","|"), db);
    			addLink(new WebLinks("C Kkompany 2008 DVD HQ Tushar Kapoor","dPOMYTSS29w","|"), db);
    			addLink(new WebLinks("C U at 9 2005 DVD Isaiah","CtGMuuSICpM","|"), db);
    			addLink(new WebLinks("Chaalbaaz 2003 DVD HQ Mithun Chakraborty","RvikU1yhibc","|"), db);
    			addLink(new WebLinks("Chal Chala Chal 2009 DVD HQ Govinda","D98co6lXBSE","|"), db);
    			addLink(new WebLinks("Chal Mere Bhai 2000 DVD HQ Sanjay Dutt","85tKlaUhHxE","|"), db);
    			addLink(new WebLinks("Chalo Ishq Ladaaye 2002 DVD HQ Govinda","XbXyJ0K6Kws","|"), db);
    			addLink(new WebLinks("Chameli 2003 DVD HQ Kareena Kapoor","fF03gxZamE0","|"), db);
    			addLink(new WebLinks("Chamku 2008 DVD HQ Bobby Deol","6tMhAsJBSfU","|"), db);
    			addLink(new WebLinks("Champion 2000 DVD HQ Sunny Deol","9NtljkEYB5c","|"), db);
    			addLink(new WebLinks("Chanchal 2008 DVD HQ Armaan Shahabi","DJjnT0dbMUs","|"), db);
    			addLink(new WebLinks("Chandni Bar 2001 DVD HQ Tabu","_LgPIJJhirU","|"), db);
    			addLink(new WebLinks("Chandni Ki Ek Kahani 2007 Shivaji","ys263vIMC6k","|"), db);
    			addLink(new WebLinks("Chase 2010 DVD HQ Anuj Saxena","gYRLfWUe2x0","|"), db);
    			addLink(new WebLinks("Chehraa 2005 DVD Bipasha Basu","PD1PDZ3TVZY","|"), db);
    			addLink(new WebLinks("Chhupa Rustam 2001 DVD HQ Sanjay Kapoor","S0JkRn0ya_E","|"), db);
    			addLink(new WebLinks("Chocolate 2005 DVD Anil Kapoor","BF6G1g7_0Q8","|"), db);
    			addLink(new WebLinks("Chor Machaye Shor 2002 DVD Bobby Deol","EPC-ALpeji8","|"), db);
    			addLink(new WebLinks("Chor Mandli 2006 DVD HQ Mukul Dev","rNpdzbruO6A","|"), db);
    			addLink(new WebLinks("City Of Gold 2010 DVD HQ Vineet Kumar","P03s_aJGmck","|"), db);
    			addLink(new WebLinks("Click 2010 DVD HQ Shreyas Talpade","YP-EnqFnOQM","|"), db);
    			addLink(new WebLinks("Company 2002 DVD HQ Ajay Devgan","Og7Afj5LY9c","|"), db);
    			addLink(new WebLinks("Crook 2010 DVD HQ Emraan Hashmi","URxt9Wbwf5E","|"), db);
    			addLink(new WebLinks("Dada No 1 2005 DVD VikRam","SHIvtwfhkxM","|"), db);
    			addLink(new WebLinks("Daddy Cool 2009 DVD Sunil Shetty","05Ne2Mbnxq4","|"), db);
    			addLink(new WebLinks("Dal 2001 Raaja","Z35RR8Ar2H8","|"), db);
    			addLink(new WebLinks("Darling 2007 DVD HQ Fardeen Khan","aScVVhUx56M","|"), db);
    			addLink(new WebLinks("Dayanayak 2007 DVD HQ Mahesh Manjrekar","ZTzJUEQ3Ong","|"), db);
    			addLink(new WebLinks("De Taali 2008 DVD HQ Aftab Shivdasani","hqZYkE52nmc","|"), db);
    			addLink(new WebLinks("Deadline 2006 DVD HQ Konkona Sen Sharma","P0eQhJT1GPI","|"), db);
    			addLink(new WebLinks("Deewaanapan 2001 DVD HQ Arjun Rampal","pyrwzAkv8Go","|"), db);
    			addLink(new WebLinks("Deewane 2000 DVD HQ Ajay Devgan","lSfH6_vnrII","|"), db);
    			addLink(new WebLinks("Deewangee 2002 DVD HQ Ajay Devgan","gwBozJpmQdg","|"), db);
    			addLink(new WebLinks("Don 2006 DVD HQ Shahrukh Khan","NTnAAtTVU90","|"), db);
    			addLink(new WebLinks("Dulha Mil Gaya 2010 DVD Fardeen Khan","cXH2y9tM5_I","|"), db);
    			addLink(new WebLinks("Dulhan Hum Le Jayenge 2000 DVD HQ Salman Khan","5PPsLpuWxwg","|"), db);
    			addLink(new WebLinks("Dus 2005 DVD HQ Sanjay Dutt","Ay1Ie389m2A","|"), db);
    			addLink(new WebLinks("Dushmani 2007 DVD Pavan Kalyan","wGg3VevoXFA","|"), db);
    			addLink(new WebLinks("Dushmano Ka Dushman 2010 DVD Nitin","fEZMVFhXb8I","|"), db);
    			addLink(new WebLinks("Dushmano Ki Dushmani 2008 Veerindra","SvKvcYQ_xjE","|"), db);
    			addLink(new WebLinks("Ek Alag Mausam 2003 DVD HQ Nandita Das","UPEDyW97n1I","|"), db);
    			addLink(new WebLinks("Ek Aur Ek Gyarah 2003 DVD Sanjay Dutt","Plhy4ETazP4","|"), db);
    			addLink(new WebLinks("Ek Aur Jaanbaaz Khiladi 2009 Vijay","JmS9EvkUZv0","|"), db);
    			addLink(new WebLinks("Ek Aur Jigerbaaz 2008 Kalyan Ram","A4lymHYihiA","|"), db);
    			addLink(new WebLinks("Ek Elan E Jang 2007 DVD Jagapathi Babu","Cuid0nxowFs","|"), db);
    			addLink(new WebLinks("Ek Hasina Thi 2004 DVD HQ Saif Ali Khan","hE2c0gG1AZk","|"), db);
    			addLink(new WebLinks("Ek Hi Raasta 2010 DVD HQ Prabhas","vmQzwoG_nDM","|"), db);
    			addLink(new WebLinks("Ek Jwalamukhi 2007 DVD Allu Arjun","f3eTemO-Kpk","|"), db);
    			addLink(new WebLinks("Ek Khiladi Ek Haseena 2005 DVD HQ Fardeen Khan","N3AvhWvNovE","|"), db);
    			addLink(new WebLinks("Ek Krantiveer Vasudev Balwant Phadke 2007 Ajinkya Deo","_ntbL-ZgfvA","|"), db);
    			addLink(new WebLinks("Ek Pal Pyar Ka 2005 DVD Sharad Ponkshe","w6SNxeztXEk","|"), db);
    			addLink(new WebLinks("Ek Se Badhkar Ek 2004 DVD HQ Sunil Shetty","U9zMsGxcQQs","|"), db);
    			addLink(new WebLinks("Ek Vivaah Aisa Bhi 2008 DVD Sonu Sood","H0dLpqNbSPk","|"), db);
    			addLink(new WebLinks("Elaan 2005 DVD Arjun Rampal","zYEtBq6fvyA","|"), db);
    			addLink(new WebLinks("Farz 2001 DVD HQ Sunny Deol","hvmr208hQ-c","|"), db);
    			addLink(new WebLinks("Fast Forward 2009 Rehan Khan","l9y8eOzcMm4","|"), db);
    			addLink(new WebLinks("Fight Club 2006 Sunil Shetty","wET93nTiQvE","|"), db);
    			addLink(new WebLinks("Firaaq 2009 DVD HQ Naseeruddin Shah","pc4ANivCCgs","|"), db);
    			addLink(new WebLinks("Fool n Final 2007 DVD HQ Sunny Deol","iVlz-rQ0Y-U","|"), db);
    			addLink(new WebLinks("Fun2shh 2003 DVD Amitabh Bachchan","EU_dBCWKWIk","|"), db);
    			addLink(new WebLinks("Game 2007 DVD Sameer Dharmadhikari","nSiuTWCJY4g","|"), db);
    			addLink(new WebLinks("Gautam Govinda 2002 DVD HQ Mithun Chakraborty","RM7-0_NOiJs","|"), db);
    			addLink(new WebLinks("Ghutan 2007 DVD HQ Aryan Vaid","DUTrX_AFhDc","|"), db);
    			addLink(new WebLinks("Golden Boys 2008 DVD Asrani","C2kokZLrJxs","|"), db);
    			addLink(new WebLinks("Good Boy Bad Boy 2007 DVD HQ Emraan Hashmi","_h3agW3YM9o","|"), db);
    			addLink(new WebLinks("Grahan 2001 DVD HQ Jackie Shroff","eHg6XDUJTjc","|"), db);
    			addLink(new WebLinks("Great Chatrapathy 2004 DVD Sarath Kumar","N5liVL2cNEo","|"), db);
    			addLink(new WebLinks("Great Dharmatma 2007 DVD Arjun","TlzBNtRnga8","|"), db);
    			addLink(new WebLinks("Gunehgar 2007 DVD HQ Kamal Hassan","wYJoYezUVuY","|"), db);
    			addLink(new WebLinks("Hadh 2001 DVD Jackie Shroff","2UpXnpq7XHo","|"), db);
    			addLink(new WebLinks("Haisiyat Ki Jung 2008 Shashi Kant","u0-fWJrrJWc","|"), db);
    			addLink(new WebLinks("Hamara Dil Aapke Paas Hai 2000 DVD HQ Anil Kapoor","j_vSBbyRiKQ","|"), db);
    			addLink(new WebLinks("Har Dil Jo Pyaar Karega 2000 DVD HQ Salman Khan","N4XM6Y7-KB4","|"), db);
    			addLink(new WebLinks("Haseena 2005 Raj Babbar","ax22mVgPA_I","|"), db);
    			addLink(new WebLinks("Hathyar 2002 Sanjay Dutt","F1HVTPv214g","|"), db);
    			addLink(new WebLinks("Hawas Ki Raat 2001 DVD Jeet Upendra","1nO-RGBt4XQ","|"), db);
    			addLink(new WebLinks("Hazaron Khwaishein Aisi 2005 DVD HQ Kay Kay Menon","QqoNdAx16Kk","|"), db);
    			addLink(new WebLinks("Hello 2008 DVD HQ Salman Khan","ja3XCa8GcRk","|"), db);
    			addLink(new WebLinks("Hera Pheri 2000 DVD HQ Paresh Rawal","MmIW6qu8hHk","|"), db);
    			addLink(new WebLinks("Hide & Seek 2010 DVD Purab Kohli","6cp8JGYecM8","|"), db);
    			addLink(new WebLinks("Hogi Pyar Ki Jeet 2006 Nitin","K5s5Nk2VKqM","|"), db);
    			addLink(new WebLinks("Holiday 2005 Dino Morea","FxLwIJsoPc4","|"), db);
    			addLink(new WebLinks("Hot Money 2006 Tarun Arora","SuKEe6Zi3s0","|"), db);
    			addLink(new WebLinks("Hukumat Ki Jung 2008 DVD HQ Prabhas","A6lSxiceO5A","|"), db);
    			addLink(new WebLinks("Hulchul 2004 DVD HQ Akshay Khanna","31M8mqXD21o","|"), db);
    			addLink(new WebLinks("Hum Ho Gaye Aapke 2001 DVD HQ Fardeen Khan","6HRfEIvymxo","|"), db);
    			addLink(new WebLinks("Hum Pyar Tumhi Se Kar Baithe 2002 DVD HQ Jugal Hansraj","v5JqSc2Up70","|"), db);
    			addLink(new WebLinks("Hum Tum aur Mom 2005 DVD HQ Raj Vasudeva","LwxzKXqFWGw","|"), db);
    			addLink(new WebLinks("Humko Deewana Kar Gaye 2006 DVD HQ Akshay Kumar","r9XvIDA_Meg","|"), db);
    			addLink(new WebLinks("Humraaz 2002 DVD HQ Bobby Deol","8OaVUN4TS2M","|"), db);
    			addLink(new WebLinks("Hungama 2003 DVD HQ Akshay Khanna","oqeUuaK1bPU","|"), db);
    			addLink(new WebLinks("Hyderabad Blues 2 2004 Nagesh Kukunoor","km3J_agKgzg","|"), db);
    			addLink(new WebLinks("Hyderabad Nawabs 2006 Mast Ali","uKW_FPsFiB8","|"), db);
    			addLink(new WebLinks("I - Proud to be an Indian 2004 DVD HQ Sohail Khan","fF03gxZamE0","|"), db);
    			addLink(new WebLinks("I Am Kalam 2010 DVD HQ Gulshan Grover","y-YWcdAu30Y","|"), db);
    			addLink(new WebLinks("Idiot Box 2010 DVD Sushant Singh","Y-nkFD_1cPg","|"), db);
    			addLink(new WebLinks("Inspector Kiran 2004 Milind Gunaji","cwq14jy7d3I","|"), db);
    			addLink(new WebLinks("Intequam 2009 DVD HQ Kuldeep Pawar","TgpI03It0jw","|"), db);
    			addLink(new WebLinks("International Don 2005 DVD Kalyan Ram","U6iLtvNZ4ec","|"), db);
    			addLink(new WebLinks("IPS Jhansi 2008 DVD HQ Vijayashanthi","xpNgW6dJqJI","|"), db);
    			addLink(new WebLinks("Iqbal 2005 DVD Shreyas Talpade","LBZec7FEn4c","|"), db);
    			addLink(new WebLinks("Ishq Hai Tumse 2004 DVD Dino Morea","mVDXUHVDmQY","|"), db);
    			addLink(new WebLinks("Isi Life Mein 2010 DVD Akshay Oberoi","2rMtpHnCBBc","|"), db);
    			addLink(new WebLinks("Jaani Dushman 2002 DVD HQ Sunny Deol","Aqw8NNYMZmI","|"), db);
    			addLink(new WebLinks("Jai Maha Sakthi 2009 Raaja","Y9xssk5TO64","|"), db);
    			addLink(new WebLinks("Jai Santoshi Maa 2006 DVD Nushrat Bharucha","CyUkmHBW26g","|"), db);
    			addLink(new WebLinks("Jail 2009 DVD HQ Neil Nitin Mukesh","_fsuAf3YPoA","|"), db);
    			addLink(new WebLinks("Jajantaram Mamantaram 2003 DVD HQ Javed Jaffrey","JDzlpVIErTc","|"), db);
    			addLink(new WebLinks("Janani 2006 DVD HQ Bhagyashree","8_gZo2fAcsU","|"), db);
    			addLink(new WebLinks("Jawani Diwani 2006 Emraan Hashmi","htktQBBOOKk","|"), db);
    			addLink(new WebLinks("Jeena Sirf Merre Liye 2002 DVD Tushar Kapoor","ARo9pMZoINo","|"), db);
    			addLink(new WebLinks("Jhankaar Beats 2003 DVD HQ Sanjay Suri","44dJq3mDMN0","|"), db);
    			addLink(new WebLinks("Jhootha Hi Sahi 2010 DVD HQ John Abraham","TACYnYD5B_g","|"), db);
    			addLink(new WebLinks("Jigarwala 2009 Ajit","y0DkGHLQ6m8","|"), db);
    			addLink(new WebLinks("Jimmy 2008 DVD HQ Mimoh Chakraborty","7oCrrX2A7cQ","|"), db);
    			addLink(new WebLinks("Jis Desh Mein Ganga Rehta Hai 2000 DVD HQ Govinda","b2DlEQ4h1gc","|"), db);
    			addLink(new WebLinks("Jism 2003 DVD John Abraham","tVAFQ4a4DJY","|"), db);
    			addLink(new WebLinks("Jo Bole So Nihaal 2005 DVD HQ Sunny Deol","xX6Hb5JmFxc","|"), db);
    			addLink(new WebLinks("Joru Ka Ghulam 2000 DVD HQ Govinda","pAnDDPCCxtk","|"), db);
    			addLink(new WebLinks("Josh 2006 DVD Arjun","dpf9Fe70HwQ","|"), db);
    			addLink(new WebLinks("Julie 2005 Neha Dhupia.","GOppw4YaaFY","|"), db);
    			addLink(new WebLinks("Jung Ka Elaan 2006 DVD Darshan","YJOf5gEjQ1E","|"), db);
    			addLink(new WebLinks("Jung Watan Ke Liye 2007 Devaraj","sWsaZxwN11o","|"), db);
    			addLink(new WebLinks("Just Married 2007 DVD HQ Fardeen Khan","e8Bs3uHGhIo","|"), db);
    			addLink(new WebLinks("Kaaboo 2002 DVD Faisal Khan","DFJUAp8lX44","|"), db);
    			addLink(new WebLinks("Kaali Pahadi 2000 DVD Poonam Das Gupta","Ow34mvYSd5I","|"), db);
    			addLink(new WebLinks("Kaalo 2010 DVD HQ Swini Khara","oYmXKIISoyE","|"), db);
    			addLink(new WebLinks("Kabhi Socha Bhi Na Tha 2005 DVD HQ Madhoo","BcM737ej8AY","|"), db);
    			addLink(new WebLinks("Kabzaa 2010 DVD HQ Nitin","M4EroNPtr-M","|"), db);
    			addLink(new WebLinks("Kahin Pyaar Na Ho Jaaye 2000 DVD HQ Salman Khan","Cc21usGvBeY","|"), db);
    			addLink(new WebLinks("Khakee 2004 DVD HQ Amitabh Bachchan","qtmtHgw3J4U","|"), db);
    			addLink(new WebLinks("Khakee 2009 Vishaal","WU8rbdfYOfI","|"), db);
    			addLink(new WebLinks("Khanjar 2003 DVD Sunil Shetty","s3_6EJkUa3k","|"), db);
    			addLink(new WebLinks("Khauff 2000 DVD HQ Sanjay Dutt","o4Y8GGwDyjk","|"), db);
    			addLink(new WebLinks("Khichdi 2010 DVD HQ Supriya Pathak","1yxZcC8qqx0","|"), db);
    			addLink(new WebLinks("Khoon Ka Karz 2008 DVD Kamal Hassan","SadszCz9pW0","|"), db);
    			addLink(new WebLinks("Khuda Gawah 2004 DVD HQ Vijaykant","NRkn4Rk5xfM","|"), db);
    			addLink(new WebLinks("Khushi 2003 DVD HQ Amitabh Bachchan","M1CrdJzhjWw","|"), db);
    			addLink(new WebLinks("Knock Out 2010 DVD HQ Sanjay Dutt","DeNTh1hqJ7Y","|"), db);
    			addLink(new WebLinks("Koi Mere Dil Mein Hai 2005 DVD HQ Priyanshu Chatterjee","RHrLmj12C0Q","|"), db);
    			addLink(new WebLinks("Kool Nahi Hot Hai Hum 2008 DVD HQ Vinay Anand","r26UMVe6KUw","|"), db);
    			addLink(new WebLinks("Kranti 2002 DVD HQ Bobby Deol","_vf6YAZ-A5I","|"), db);
    			addLink(new WebLinks("Kris Aur Krishna 2009 DVD HQ Sandeep Bhansali","DCf3X_pSzcY","|"), db);
    			addLink(new WebLinks("Krishna Cottage 2004 DVD HQ Sohail Khan","lC1FMT5fRtw","|"), db);
    			addLink(new WebLinks("Krishna Tere Desh Mein 2001 DVD Shiv Kumar","Y9tYDnohxH4","|"), db);
    			addLink(new WebLinks("Kuch Tum Kahon Kuch Hum Kahein 2002 DVD HQ Fardeen Khan","gYGgffwnsoE","|"), db);
    			addLink(new WebLinks("Kushti 2010 DVD HQ Rajpal Yadav","nO-Pq5Z13ts","|"), db);
    			addLink(new WebLinks("Kyaa Dil Ne Kahaa 2002 DVD HQ Rajesh Khanna","avS4MZ_w-3o","|"), db);
    			addLink(new WebLinks("Kyaa Kool Hai Hum 2005 DVD HQ Tushar Kapoor","3FzDA6vjEm8","|"), db);
    			addLink(new WebLinks("Kyo Kii Main Jhuth Nahin Bolta 2001 DVD HQ Govinda","i0m3oppt83w","|"), db);
    			addLink(new WebLinks("Kyun Ho Gaya Na 2004 DVD HQ Vivek Oberoi","b-XeNV9v-58","|"), db);
    			addLink(new WebLinks("Lahore 2010 DVD Aanaahad","04w-RzSnps4","|"), db);
    			addLink(new WebLinks("Lakeer 2004 DVD HQ Sunny Deol","p7bcLU0ROe0","|"), db);
    			addLink(new WebLinks("Lakhan 2007 DVD HQ Upendra","XgMNbwqVe_Q","|"), db);
    			addLink(new WebLinks("Life Express 2010 DVD Kiran Janjani","DcsOTpVCdhw","|"), db);
    			addLink(new WebLinks("Life Mein Kabhie Kabhiee 2007 DVD Aftab Shivdasani","icy5odoleSA","|"), db);
    			addLink(new WebLinks("Loafer 2007 DVD HQ Ravi Teja","8oECSuOatcQ","|"), db);
    			addLink(new WebLinks("Love Ka Tadka 2009 DVD HQ Nauheed Cyrusi","D4Eh5OpV2cY","|"), db);
    			addLink(new WebLinks("Love Sex Aur Dhokha 2010 DVD Anshuman Jha","Kh0-aEk_D08","|"), db);
    			addLink(new WebLinks("Lucky 2005 DVD HQ Salman Khan","7nOXmI_gz6o","|"), db);
    			addLink(new WebLinks("Maa Ka Chamatkar 2004 Soundarya","sh1clmBOczE","|"), db);
    			addLink(new WebLinks("Maa O Maa 2008 DVD Sujit Kumar","QcbMHEIM6C8","|"), db);
    			addLink(new WebLinks("Maa Tujhhe Salaam 2002 DVD Sunny Deol","MisgmyC31-g","|"), db);
    			addLink(new WebLinks("Maan Gaye Mughall E Azam 2008 DVD Rahul Bose","k93F2lP3GTk","|"), db);
    			addLink(new WebLinks("Madhubaala 2006 DVD Manoj Chhadva","eHogpfKb554","|"), db);
    			addLink(new WebLinks("Magic Robot 2010 DVD HQ Baby Geethika","69tg--hnF6g","|"), db);
    			addLink(new WebLinks("Maharathi 2008 DVD HQ Naseruddin Shah","XrawidL5bMI","|"), db);
    			addLink(new WebLinks("Main Hoon Rakhwala 2000 DVD HQ Chiranjeevi","S2cil16BJtk","|"), db);
    			addLink(new WebLinks("Main Khunkhar Yodha 2000 DVD HQ Vishnu Vardhan","xFZiXm7RAm4","|"), db);
    			addLink(new WebLinks("Main Meri Patni Aur Woh 2005 DVD Rajpal Yadav","tE8oX9mBNDk","|"), db);
    			addLink(new WebLinks("Main Prem Ki Diwani Hoon 2003 DVD Hrithik Roshan","qgih1kOthL8","|"), db);
    			addLink(new WebLinks("Mallika 2010 DVD HQ Sameer Dattani","WpuYP4d5xXQ","|"), db);
    			addLink(new WebLinks("Man Mandir Mein Maa 2009 DVD HQ Hemant Chaddha","1i5TmkTYHoU","|"), db);
    			addLink(new WebLinks("Manthan Ek Kashmakash 2008 DVD Sanjay Kumar","jXw63y1JoY8","|"), db);
    			addLink(new WebLinks("Mardon Wali Baat 2009 DVD Rishi","S1iHW8EkjMI","|"), db);
    			addLink(new WebLinks("Mardonwali Baat 2007 Vijaykant","Ba9xk16zTks","|"), db);
    			addLink(new WebLinks("Marte Dum Tak 2002 DVD Sai Kumar","YxfNx_ogmSM","|"), db);
    			addLink(new WebLinks("Maruti Mera Dost 2009 DVD HQ Chandrachur Singh","5xJlqm5eW3k","|"), db);
    			addLink(new WebLinks("Mastaani 2005 Akshay Bedi","6hm2-2H_OU8","|"), db);
    			addLink(new WebLinks("Masti 2004 DVD HQ Ajay Devgan","P_0fx5uNPnk","|"), db);
    			addLink(new WebLinks("Match Fixing 2002 Raju Sundaram","Vy-LLEGyOQU","|"), db);
    			addLink(new WebLinks("Matrubhoomi 2005 DVD HQ Tulip Joshi","CWl85Akuxn0","|"), db);
    			addLink(new WebLinks("Meera 2007 Vikram","kmzjI9W1m4Y","|"), db);
    			addLink(new WebLinks("Mera Kartavya 2009 DVD HQ Suresh Gopi","AT8hdVi0RYA","|"), db);
    			addLink(new WebLinks("Mere Baap Pehle Aap 2008 DVD HQ Akshay Khanna","UMAa148hojo","|"), db);
    			addLink(new WebLinks("Meri Aan 2001 DVD HQ Vikram","5b2R96RzADs","|"), db);
    			addLink(new WebLinks("Meri Aashiqui 2005 DVD Rahul Roy","UlCIR3RaP3I","|"), db);
    			addLink(new WebLinks("Meri Hukumat 2003 Srihari","e8ie51UDGEc","|"), db);
    			addLink(new WebLinks("Meri Ishq Ki Kahani 2001 DVD Ashwini Bhave","cZaeEQXKWrA","|"), db);
    			addLink(new WebLinks("Meri Jung 2006 DVD HQ Nagarjuna.","8vFm09hvr24","|"), db);
    			addLink(new WebLinks("Meri Pyaari Bahania Banegi Dulhaniya 2001 DVD Mithun Chakraborty","jmKz-_x72XM","|"), db);
    			addLink(new WebLinks("Meri Sapath 2008 DVD Gopichand","UnTIvK3q_Xk","|"), db);
    			addLink(new WebLinks("Meri Taaqat 2005 DVD Venkatesh","NktglsIzObw","|"), db);
    			addLink(new WebLinks("Meri Zanjeer 2000 DVD HQ Kamal Hassan","LOuXeyxh9yM","|"), db);
    			addLink(new WebLinks("Mohandas 2009 DVD HQ Sonali Kulkarni","y_u8euCSW9g","|"), db);
    			addLink(new WebLinks("Mohe Bhool Gaye Saawariya 2007 DVD Ajith","rjaEBvKxj1c","|"), db);
    			addLink(new WebLinks("Mr Hindustani 2003 Vijaykant","fe6-yn9nIN4","|"), db);
    			addLink(new WebLinks("Mr Singh & Mrs Mehta 2008 DVD Prroshant Narayanna","JmTMyp7mpfk","|"), db);
    			addLink(new WebLinks("Mr Ya Miss 2005 DVD Antra Mali","LM8fE0kL5e8","|"), db);
    			addLink(new WebLinks("Mudda 2003 DVD HQ Arya Babbar","l32YADbNjNM","|"), db);
    			addLink(new WebLinks("Mujhe Jeene Do 2005 Sai Kumar","2sHriRLHPTU","|"), db);
    			addLink(new WebLinks("Mujhe Kucch Kehna Hai 2001 DVD Tushar Kapoor","sO055F9yMCA","|"), db);
    			addLink(new WebLinks("Mukhbiir 2008 DVD HQ Sameer Dattani","onEAja3izJc","|"), db);
    			addLink(new WebLinks("Mumbai Matinee 2003 DVD HQ Rahul Bose","JKV5pAFVS0g","|"), db);
    			addLink(new WebLinks("Mumbai Se Aaya Mera Dost 2003 DVD HQ Abhishek Bachchan","gUXWmYqE7CM","|"), db);
    			addLink(new WebLinks("Musaa 2010 DVD Jackie Shroff","QvNKPZqS3nI","|"), db);
    			addLink(new WebLinks("Muskaan 2004 DVD HQ Aftab Shivdasani","BuwPZZR8sQY","|"), db);
    			addLink(new WebLinks("Muskurake Dekh Zara 2010 DVD HQ Gashmeer Mahajani","HPvTA6Ldwhw","|"), db);
    			addLink(new WebLinks("My Friend Ganesha 2 2008 DVD HQ .","aiXjIsAoVXU","|"), db);
    			addLink(new WebLinks("My Name is Anthony Gonsalves 2008 DVD Nikhil Dwivedi","wcUy509q9MM","|"), db);
    			addLink(new WebLinks("Nagin Ka Inteqaam 2009 Mumait Khan","sg6QmWDXtkA","|"), db);
    			addLink(new WebLinks("Naina 2005 DVD HQ Urmila Matondkar","G46uMHBv9hE","|"), db);
    			addLink(new WebLinks("Nakshatra 2010 DVD HQ Shubh","CiH7NsJbP4w","|"), db);
    			addLink(new WebLinks("Naqaab 2007 DVD Bobby Deol","ldy3L9ReCaI","|"), db);
    			addLink(new WebLinks("Narsimha 2004 DVD Sarath Kumar","L7xyFP-ipFA","|"), db);
    			addLink(new WebLinks("Naxalite 2000 DVD Ashwath","xGb6tuVTOr8","|"), db);
    			addLink(new WebLinks("Naya Barood 2003 DVD HQ Upendra","A32AgdsMIII","|"), db);
    			addLink(new WebLinks("Naya Jigar 2007 DVD Nagarjuna","2w2gIDYYdqE","|"), db);
    			addLink(new WebLinks("Naya Natwarlal 2007 DVD HQ Prakash Raj","1o38RgQmF3I","|"), db);
    			addLink(new WebLinks("Neel Kamal 2007 DVD HQ Kamal Hassan","ElIGMLUhUYE","|"), db);
    			addLink(new WebLinks("New Entery 2007 DVD HQ Prasanth","p_KRxeQt1SI","|"), db);
    			addLink(new WebLinks("Om Bahadur 2007 DVD HQ Upendra","CGJXDcJsnvY","|"), db);
    			addLink(new WebLinks("Om Jai Jagadish 2002 DVD HQ Anil Kapoor","BAmONQdMYS0","|"), db);
    			addLink(new WebLinks("Paap 2003 DVD HQ John Abraham","AXWrMz0hW74","|"), db);
    			addLink(new WebLinks("Paisa Vasool 2004 DVD Manisha Koirala","zIwmjOCCS4M","|"), db);
    			addLink(new WebLinks("Payback 2010 DVD HQ Munish Khan","7Q84Fn0OyGk","|"), db);
    			addLink(new WebLinks("Paying Guests 2009 DVD HQ Shreyas Talpade","3UlSRYx0HSI","|"), db);
    			addLink(new WebLinks("Phas Gaye Re Obama 2010 DVD HQ Rajat Kapoor","A5j9nAfDeGY","|"), db);
    			addLink(new WebLinks("Phir Ek Most Wanted 2010 DVD HQ Gopichand","F7MC9-6rLm4","|"), db);
    			addLink(new WebLinks("Phir Ek Tehelka 2009 DVD HQ Darshan","etmsEBKs5mQ","|"), db);
    			addLink(new WebLinks("Phir Ek Toofan 2006 Murali","ItzxkS_M4e0","|"), db);
    			addLink(new WebLinks("Phir Hera Pheri 2006 DVD HQ Akshay Kumar","1FT6VOrFMLo","|"), db);
    			addLink(new WebLinks("Phir Tauba Tauba 2007 DVD Rahul Roy","FeRF-NnIOe8","|"), db);
    			addLink(new WebLinks("Phool Aur Kante 2008 DVD Ram","ldrjI8x8k9I","|"), db);
    			addLink(new WebLinks("Police Force 2004 DVD HQ Akshay Kumar","u4XjpyRRaKo","|"), db);
    			addLink(new WebLinks("Poochho Mere Dil Se 2004 DVD HQ Diya Chakraborty","wDv1B_HCG5M","|"), db);
    			addLink(new WebLinks("Pranali 2008 Nargis","yb0r0H35FBk","|"), db);
    			addLink(new WebLinks("Prarambh 2004 DVD Vijay Raaz","4aMKReQt2n4","|"), db);
    			addLink(new WebLinks("Pratighat 2006 DVD HQ Ravi Teja","wwU5q0ZHEYA","|"), db);
    			addLink(new WebLinks("Premi 2005 DVD Bhargavaram","E1Ds0WNjHHk","|"), db);
    			addLink(new WebLinks("Pukar 2000 DVD HQ Anil Kapoor","wJvMeo1kwTc","|"), db);
    			addLink(new WebLinks("Pyaar Ke Side Effects 2006 DVD HQ Rahul Bose","svByqTtmSb4","|"), db);
    			addLink(new WebLinks("Pyaar Mein Kabhi Kabhi 2000 DVD Siddhant","mn4CPdV1sis","|"), db);
    			addLink(new WebLinks("Pyaar Tune Kya Kiya 2001 Fardeen Khan","uENQh5xGrCY","|"), db);
    			addLink(new WebLinks("Pyaasa 2002 DVD HQ AAftab Shivdasani","bn5_lcDy9uw","|"), db);
    			addLink(new WebLinks("Qayamat 2003 DVD Ajay Devgan","piTKnq0I7Ew","|"), db);
    			addLink(new WebLinks("Raat Gayi Baat Gayi 2009 DVD HQ Rajat Kapoor","Lo4tKWXNW14","|"), db);
    			addLink(new WebLinks("Raja Bhai Lagey Raho 2005 Ravi Kishan","R_C22y95VJw","|"), db);
    			addLink(new WebLinks("Raja Ko Rani Se Pyar Ho Gaya 2000 DVD HQ Arvind Swamy","GJlCAOeMkp4","|"), db);
    			addLink(new WebLinks("Rakshak 2004 Chiranjeevi","mo-frEZaAJo","|"), db);
    			addLink(new WebLinks("Rama Rama Kya Hai Dramaaa 2007 DVD HQ Neha Dhupia","V2m0tVd7BYk","|"), db);
    			addLink(new WebLinks("Ramaa The Saviour 2010 DVD HQ Sahil Khan","JLDirqJlQeg","|"), db);
    			addLink(new WebLinks("Ramayana 2010 DVD HQ Manoj Bajpai","yQd5GdVHuqY","|"), db);
    			addLink(new WebLinks("Ramgadh Ki Ramkali 2001 DVD HQ Durgesh Nandini","YQ90sxfxAcU","|"), db);
    			addLink(new WebLinks("Rampuri Damaad 2007 Vijay","7E6rhpKX_XM","|"), db);
    			addLink(new WebLinks("Ramraaj 2008 DVD HQ Nagarjuna","Glfez4j_z5g","|"), db);
    			addLink(new WebLinks("Rann 2010 DVD HQ Amitabh Bachchan","KXIDCfoj8Qw","|"), db);
    			addLink(new WebLinks("Red 2007 DVD Celina Jaitley","tUzvc-7qRqU","|"), db);
    			addLink(new WebLinks("Rehnaa Hai Terre Dil Mein 2001 DVD Madhavan","RXc6C-VFUuY","|"), db);
    			addLink(new WebLinks("Reshma aur Sultaan 2002 Dharmendra","3aquLD1ongw","|"), db);
    			addLink(new WebLinks("Revati 2005 DVD Kashmira Shah","DtuZ-SaczLY","|"), db);
    			addLink(new WebLinks("Rishtey 2002 DVD HQ Anil Kapoor","2hTYL-wGHcI","|"), db);
    			addLink(new WebLinks("Riwayat 2010 DVD HQ Samapika Debnath","uD-EW116Axg","|"), db);
    			addLink(new WebLinks("Robbery 2005 DVD Nagarjuna","FY3KsuaWeMI","|"), db);
    			addLink(new WebLinks("Rocky 2006 DVD HQ Zayed Khan","itIRa5ITQ9g","|"), db);
    			addLink(new WebLinks("Rog 2005 DVD Irfan Khan","i1oQ9kTfxFs","|"), db);
    			addLink(new WebLinks("Rudraksh 2004 DVD HQ Sanjay Dutt","tOYoeTttzWU","|"), db);
    			addLink(new WebLinks("Run 2004 DVD HQ Abhishek Bachchan","EhgxvJF54QY","|"), db);
    			addLink(new WebLinks("Runway 2009 DVD HQ Amarjeet","6SMlGmqgEMo","|"), db);
    			addLink(new WebLinks("Saathi 2005 Divvij Kak","NjVY_XW9zqs","|"), db);
    			addLink(new WebLinks("Saawan 2006 DVD HQ Salman Khan","AQZhfUaMBWc","|"), db);
    			addLink(new WebLinks("Sabse Badhkar Hum 2010 DVD HQ Prabhas","MzSq1oPhJgI","|"), db);
    			addLink(new WebLinks("Sachhai Ki Taaqat 2005 DVD HQ Sudeep","zLrqrjA92Wg","|"), db);
    			addLink(new WebLinks("Sadiyaan 2010 DVD Luv Sinha","T8dmWr-CWy8","|"), db);
    			addLink(new WebLinks("Samay 2003 DVD HQ Jackie Shroff","x41QnX-zGUU","|"), db);
    			addLink(new WebLinks("Sanak 2007 DVD HQ Abhay Bakshi","_6lbWqu-o7c","|"), db);
    			addLink(new WebLinks("Sanam Teri Kasam 2009 DVD HQ Saif Ali Khan","nNwBnlatnb4","|"), db);
    			addLink(new WebLinks("Sankat City 2009 Kay Kay Menon","UyY_2mQYShM","|"), db);
    			addLink(new WebLinks("Sarhad Paar 2007 DVD HQ Sanjay Dutt","FcyrHJ0aSRg","|"), db);
    			addLink(new WebLinks("Sati Ki Shakti 2010 DVD Manhar Desai","lfNwAPNnS1g","|"), db);
    			addLink(new WebLinks("Sati Sukanya 2009 DVD Narendra Prasad","TaDGrRjwW_E","|"), db);
    			addLink(new WebLinks("Shaadi Ka Laddoo 2004 DVD HQ Sanjay Suri","meg2fNQdXcw","|"), db);
    			addLink(new WebLinks("Shaadi No. 1 2005 DVD HQ Sanjay Dutt","e6je56YzE50","|"), db);
    			addLink(new WebLinks("Shaapit 2010 DVD HQ Aditya Narayan","U4Uh4S8Jb1w","|"), db);
    			addLink(new WebLinks("Shabd 2005 DVD HQ Sanjay Dutt","f6H4lMpi8WY","|"), db);
    			addLink(new WebLinks("Shadow 2009 DVD HQ Naseer Khan","mfj0J4NxWVM","|"), db);
    			addLink(new WebLinks("Shah Ji Ki Advice 2006 Jaspal Bhatti","GX6YtMWAbeA","|"), db);
    			addLink(new WebLinks("Shaktishali Shiva 2008 Bharat","01MfVZufCN4","|"), db);
    			addLink(new WebLinks("Shapath 2009 DVD HQ Gopichand","9bm7abXMLW8","|"), db);
    			addLink(new WebLinks("Shirdi Sai Baba 2001 DVD HQ Dharmendra","-3woM_0XwVk","|"), db);
    			addLink(new WebLinks("Shiv Bhakt 2009 DVD HQ Jayanthi","4fOll2Gnr7w","|"), db);
    			addLink(new WebLinks("Shiv Senapati 2009 Suryakanth","7k7cTOLYn5Y","|"), db);
    			addLink(new WebLinks("Showbiz 2007 DVD Tushar Jalota","NYDyHOekJLE","|"), db);
    			addLink(new WebLinks("Sindoor Mange Khoon 2001 DVD Hemant Birje","-1iFr3Y3X1I","|"), db);
    			addLink(new WebLinks("Sri Raghavendra 2007 DVD HQ Rajnikanth","Ov-rUpqbZNU","|"), db);
    			addLink(new WebLinks("Straight 2009 DVD HQ Vinay Pathak","yHs9-7kQ2J8","|"), db);
    			addLink(new WebLinks("Strangers 2007 DVD HQ Kay Kay Menon","bS60VFmLq0Q","|"), db);
    			addLink(new WebLinks("Stumped 2003 DVD HQ Ali Khan","flAlhmsdFd0","|"), db);
    			addLink(new WebLinks("Sur 2002 DVD HQ Lucky Ali","h5vHACpaRUg","|"), db);
    			addLink(new WebLinks("Swaha 2010 DVD Rikkee","APVjVS4-42w","|"), db);
    			addLink(new WebLinks("Swami 2007 DVD HQ Manoj Bajpai","I93iMZhhZaU","|"), db);
    			addLink(new WebLinks("Taarzan 2004 DVD HQ Vatsal Sheth","zFNRIpgvg7c","|"), db);
    			addLink(new WebLinks("Tadap 2009 DVD Naina Rawal","cxQqCrYi6oA","|"), db);
    			addLink(new WebLinks("Tadipaar 2010 DVD HQ Sumanth","1S5X_ttbbDE","|"), db);
    			addLink(new WebLinks("Tahaan 2008 DVD HQ Master Purav Bhandare","VOkK4urA3Pg","|"), db);
    			addLink(new WebLinks("Tantra Mantra 2009 DVD HQ Prithviraj","rYFuPcZaiDU","|"), db);
    			addLink(new WebLinks("Tarkieb 2000 DVD HQ Nana Patekar","d7QgQPRq850","|"), db);
    			addLink(new WebLinks("Teen Patti 2010 DVD HQ Amitabh Bachchan","OjjhPxtWlZ0","|"), db);
    			addLink(new WebLinks("Traffic Signal 2007 DVD HQ Konkona Sen Sharma","7xBoQa8YY4s","|"), db);
    			addLink(new WebLinks("Trump Card 2010 DVD Vikram Kumar","ZgML4ONgEEg","|"), db);
    			addLink(new WebLinks("Tum 2004 DVD HQ Manisha Koirala","pntkYAIBHsA","|"), db);
    			addLink(new WebLinks("Tum Bin 2001 DVD HQ Priyanshu Chatterjee","bDMRYWCvsgE","|"), db);
    			addLink(new WebLinks("Tum Milo Toh Sahi 2010 Nana Patekar","9lyxzjmDUhE","|"), db);
    			addLink(new WebLinks("Tumko Na Bhool Paayenge 2002 Salman Khan","iuITOvGvsRg","|"), db);
    			addLink(new WebLinks("Uljhan 2001 Puru Rajkumar","dp4-CdBuYAE","|"), db);
    			addLink(new WebLinks("Utthaan 2006 DVD Priyanshu Chatterjee","kuvOtTEVW_4","|"), db);
    			addLink(new WebLinks("Uuf Kya Jaadoo Mohabbat Hai 2004 DVD Sameer Dattani","YR30lFliAjM","|"), db);
    			addLink(new WebLinks("Vaada 2005 DVD Arjun Rampal","0XtrCa8OIws","|"), db);
    			addLink(new WebLinks("Vaah Life Ho Toh Aisi 2005 DVD Shahid Kapoor","AOYkNaPpVnk","|"), db);
    			addLink(new WebLinks("Vajra 2004 Jagat Singh","9ZsY6q-VksQ","|"), db);
    			addLink(new WebLinks("Vajra 2006 DVD HQ Darshan","cQQz1Q_LEts","|"), db);
    			addLink(new WebLinks("Vardi Tujhe Salaam 2009 DVD Sudeep","emIS17C8bQ4","|"), db);
    			addLink(new WebLinks("Veer Madakari 2009 DVD HQ Sudeep","cwvtULs6Mb0","|"), db);
    			addLink(new WebLinks("Videsh - Heaven on Earth 2009 DVD HQ Preity Zinta","oNe9-BlLg_A","|"), db);
    			addLink(new WebLinks("Vikram 2000 DVD HQ Nagarjuna","kILE-Hq8WOs","|"), db);
    			addLink(new WebLinks("Vivah 2006 DVD HQ Shahid Kapoor","R9upjxi7_lk","|"), db);
    			addLink(new WebLinks("Vroom 2010 DVD HQ Gaurav Bajaj","wHZwiskmtr8","|"), db);
    			addLink(new WebLinks("Welcome 2007 DVD HQ Feroz Khan","bOqFGkFO3Lo","|"), db);
    			addLink(new WebLinks("Winner 2007 DVD HQ Prasanth","zDqQgUXhcmw","|"), db);
    			addLink(new WebLinks("Xcuse Me 2003 DVD Sharman Joshi","Q7wc4hqL0BM","|"), db);
    			addLink(new WebLinks("Yakeen 2005 DVD Arjun Rampal","ZpmsKEsqUjU","|"), db);
    			addLink(new WebLinks("Yatra 2007 DVD HQ Rekha","eJxvN87P8XY","|"), db);
    			addLink(new WebLinks("Yeh Dil 2003 DVD Tushar Kapoor","RirwT7HjgQs","|"), db);
    			addLink(new WebLinks("Yeh Diljale 2008 Sai Kumar","ZtA-D_m4-ZM","|"), db);
    			addLink(new WebLinks("Yeh Hai Jalwa 2002 DVD HQ Salman Khan","A7suQ4FZ3UE","|"), db);
    			addLink(new WebLinks("Yeh Kaisa Karz 2008 DVD HQ Nagarjuna","ueSTOqmJSJA","|"), db);
    			addLink(new WebLinks("Yudhveer 2008 DVD Sudeep.","WTb39N8eiBQ","|"), db);
    			addLink(new WebLinks("Yun Hota Toh Kya Hota 2006 Jimmy Shergill","9-90NmSXkb4","|"), db);
    			addLink(new WebLinks("Yuva 2004 DVD HQ Ajay Devgan","xaETSjOn3Nc","|"), db);
    			addLink(new WebLinks("Zakhmi Aurat 2008 Prema","GyucYZKcqrI","|"), db);
    			addLink(new WebLinks("Zameen 2003 DVD HQ Ajay Devgan","HT4Fq65YI3k","|"), db);
    			addLink(new WebLinks("Zameer 2005 DVD HQ Ajay Devgan","R_IDDagQgXw","|"), db);
    			addLink(new WebLinks("Zindagi 2001 DVD HQ Zarina Wahab","rqBo7HwLtlM","|"), db);
    			addLink(new WebLinks("100 Days 1991 DVD HQ Jackie Shroff","zFgh9XR-7zk","|"), db);
    			addLink(new WebLinks("Aadmi Khilona Hai 1993 DVD HQ Jeetendra","aO3jSPxw-Ng","|"), db);
    			addLink(new WebLinks("Aag 1994 DVD HQ Govinda","yGYYuzPuwL4","|"), db);
    			addLink(new WebLinks("Aaj Ka Goonda Raaj 1992 DVD HQ Chiranjeevi","kpkukYksoVw","|"), db);
    			addLink(new WebLinks("Aaj Ke Shahanshah 1990 DVD Jeetendra","ZExZVF-Dcxk","|"), db);
    			addLink(new WebLinks("Aaj Kie Aurat 1993 Jeetendra","-h9liycElZY","|"), db);
    			addLink(new WebLinks("Aaja Sanam 1992 DVD Avinash Wadhavan","YDJ5LJl8GbA","|"), db);
    			addLink(new WebLinks("Aandhiyan 1990 Shatrughan Sinha","1VjATglnFSA","|"), db);
    			addLink(new WebLinks("Aankhen 1993 DVD HQ Govinda","o2tLrv47MPs","|"), db);
    			addLink(new WebLinks("Aashiq Awara 1993 DVD HQ Saif Ali Khan","s8gnMMr4x-o","|"), db);
    			addLink(new WebLinks("Aashiqui 1990 DVD HQ Rahul Roy","Am2DzL81ovM","|"), db);
    			addLink(new WebLinks("Aasoo Bane Angaarey 1993 DVD HQ Jeetendra","5-HOSzPyOzQ","|"), db);
    			addLink(new WebLinks("Aatank 1996 DVD HQ Dharmendra","1ffZNSOASE0","|"), db);
    			addLink(new WebLinks("Aatank Hi Aatank 1995 DVD HQ Aamir Khan","YCVU0HB7Low","|"), db);
    			addLink(new WebLinks("Aatish 1994 DVD HQ Sanjay Dutt","trRdEsb_i2w","|"), db);
    			addLink(new WebLinks("Aaya Toofan 1999 DVD HQ Mithun Chakraborty","vCa7Wy16p44","|"), db);
    			addLink(new WebLinks("Aazmayish 1995 DVD HQ Dharmendra","0Sp6C8tOAVE","|"), db);
    			addLink(new WebLinks("Ab Insaf Hoga 1995 DVD HQ Mithun Chakraborty","HmvR9-LmaGY","|"), db);
    			addLink(new WebLinks("Agneekaal 1990 DVD Raj Babbar","PcOoFsBx_jA","|"), db);
    			addLink(new WebLinks("Agni Sakshi 1996 DVD HQ Jackie Shroff","xUKuKDGvILg","|"), db);
    			addLink(new WebLinks("Ahankaar 1995 DVD HQ Mithun Chakraborty","X-iJ1BGMWQw","|"), db);
    			addLink(new WebLinks("Akele Hum Akele Tum 1995 DVD HQ Aamir Khan","R_6fADfZnZA","|"), db);
    			addLink(new WebLinks("Allah Meherban to Gadha Pahelwan 1997 DVD HQ Kader Khan","aVelabAv8R4","|"), db);
    			addLink(new WebLinks("Amba 1990 DVD HQ Anil Kapoor","_smO8eoVtkI","|"), db);
    			addLink(new WebLinks("Amiri Garibi 1990 DVD HQ Jeetendra","L9fz9IC9OKE","|"), db);
    			addLink(new WebLinks("Anaam 1992 DVD Arman Kohli","EwCjwFif04k","|"), db);
    			addLink(new WebLinks("Anari 1994 DVD HQ Karisma Kapoor.","DY3bK66N-Gw","|"), db);
    			addLink(new WebLinks("Andaz 1994 DVD HQ Anil Kapoor","ONDd0UNy_oc","|"), db);
    			addLink(new WebLinks("Andaz Apna Apna 1994 DVD HQ Aamir Khan","7M_jHUawccI","|"), db);
    			addLink(new WebLinks("Andhera 1994 Rajveer","6Epa8Zc1VoA","|"), db);
    			addLink(new WebLinks("Andolan 1995 DVD HQ Govinda","4NlSfSnjPHI","|"), db);
    			addLink(new WebLinks("Angaara 1996 DVD HQ Mithun Chakraborty","AUq0Hbc0yhA","|"), db);
    			addLink(new WebLinks("Angaaray 1998 DVD HQ Akshay Kumar","gShj_tNEew4","|"), db);
    			addLink(new WebLinks("Angrakshak 1995 DVD HQ Sunny Deol","xB_XzLPmRpI","|"), db);
    			addLink(new WebLinks("Anjaam 1994 DVD HQ Shahrukh Khan","YbNDE9FWKlA","|"), db);
    			addLink(new WebLinks("Ankhon Mein Tum Ho 1997 DVD Rohit Roy","l7Oj4jPjuFQ","|"), db);
    			addLink(new WebLinks("Anokha Andaaz 1995 DVD HQ Manisha Koirala","ZDAirvRn2Nk","|"), db);
    			addLink(new WebLinks("Apne Dam Par 1996 Vikas Anand","oyFhiVnPKkA","|"), db);
    			addLink(new WebLinks("Apradhi 1992 DVD HQ Anil Kapoor","jS2gXk82k_k","|"), db);
    			addLink(new WebLinks("Arjun Pandith 1999 DVD HQ Sunny Deol","nPaEMt5P4To","|"), db);
    			addLink(new WebLinks("Atishbaz 1990 Shatrughan Sinha","-5z__h-FJQQ","|"), db);
    			addLink(new WebLinks("Aulad Ke Dushman 1993 DVD HQ Vikas Anand","5gpNODDu70A","|"), db);
    			addLink(new WebLinks("Awaargi 1990 Anil Kapoor","mnTXZb0-GXQ","|"), db);
    			addLink(new WebLinks("Baali Umar Ko Salaam 1994 DVD Kamal Sadanah","JA213ym2vlQ","|"), db);
    			addLink(new WebLinks("Baap Numbri Beta Dus Numbri 1990 DVD HQ Jackie Shroff","WgznAepDI44","|"), db);
    			addLink(new WebLinks("Baazigar 1993 DVD HQ Shahrukh Khan","jlliV_Nit7I","|"), db);
    			addLink(new WebLinks("Bade Miyan Chote Miyan 1998 DVD Amitabh Bachchan","eBMuorXUB_U","|"), db);
    			addLink(new WebLinks("Balwaan 1992 DVD HQ Sunil Shetty","Z7I-LEaelKc","|"), db);
    			addLink(new WebLinks("Banarasi Babu 1997 DVD HQ Govinda","Cm4LnWwvCxI","|"), db);
    			addLink(new WebLinks("Bandh Darwaza 1990 DVD Hashmat Khan","fefCsG67WNg","|"), db);
    			addLink(new WebLinks("Bandhan 1998 DVD HQ Jackie Shroff","Hd0Pu7qdNLM","|"), db);
    			addLink(new WebLinks("Bandhu 1992 DVD Abhishek","wrMxdN_eVpc","|"), db);
    			addLink(new WebLinks("Bandook Dahej Ke Seeney Par 1996 DVD HQ Shashi Kapoor","CYw3gseakoI","|"), db);
    			addLink(new WebLinks("Banjaran 1991 DVD HQ Rishi Kapoor","y4nYf9j04Nk","|"), db);
    			addLink(new WebLinks("Barood 1998 DVD HQ Akshay Kumar","d8xT79aGWNI","|"), db);
    			addLink(new WebLinks("Bedardi 1993 DVD HQ Ajay Devgan","Y9HE1y1nwhM","|"), db);
    			addLink(new WebLinks("Begunaah 1994 DVD Ashok Kumar","TQ-3hAx_Lwk","|"), db);
    			addLink(new WebLinks("Bekhudi 1992 DVD HQ Kamal Sadanah","mLVsWWvX6B8","|"), db);
    			addLink(new WebLinks("Benaam 1999 DVD HQ Mithun Chakraborty","CtVCCFChHJI","|"), db);
    			addLink(new WebLinks("Benaam Badsha 1991 DVD HQ Anil Kapoor","7I1uBRee5-w","|"), db);
    			addLink(new WebLinks("Beta 1992 DVD HQ Anil Kapoor","EfEp6gKPU_8","|"), db);
    			addLink(new WebLinks("Betaaj Badshah 1994 DVD HQ Raaj Kumar","lnJqP1I_oTU","|"), db);
    			addLink(new WebLinks("Bhabhi 1991 DVD HQ Govinda","nl20rbZxkk8","|"), db);
    			addLink(new WebLinks("Bhagyawan 1994 DVD Govinda","qZLOx4i4ExE","|"), db);
    			addLink(new WebLinks("Bhai Bhai 1997 DVD HQ Manek Bedi","ROp3-lLAt_A","|"), db);
    			addLink(new WebLinks("Bhediyon Ka Samooh 1991 Sunil Chauhan","LF0vPxuuNZ0","|"), db);
    			addLink(new WebLinks("Biwi No. 1 1999 DVD Anil Kapoor","jwwvmiqQSYA","|"), db);
    			addLink(new WebLinks("Bombay War 1990 Dyandeo Aroskar","Bet4DdclTJw","|"), db);
    			addLink(new WebLinks("Boy Friend 1993 Sheeba","pEr2fFOrB5k","|"), db);
    			addLink(new WebLinks("Chalbazz 1997 DVD Mohan Babu","YJmawkFA8SA","|"), db);
    			addLink(new WebLinks("Chamatkar 1992 DVD HQ Shahrukh Khan","c3rDmCOdbUQ","|"), db);
    			addLink(new WebLinks("Chauraha 1994 DVD HQ Jeetendra","CXFGH9dna8k","|"), db);
    			addLink(new WebLinks("Chhota Sa Ghar 1996 DVD HQ Vivek Mushran","lqp2olZWdaM","|"), db);
    			addLink(new WebLinks("Chhoti Bahoo 1994 DVD HQ Shilpa Shirodkar","7JUpOxFqjbk","|"), db);
    			addLink(new WebLinks("Chor Ke Ghar Chorni 1992 DVD HQ Rajnikanth","hgb_zdO3A7I","|"), db);
    			addLink(new WebLinks("Chupp 1997 DVD HQ Jeetendra","n9I2Hy34i5g","|"), db);
    			addLink(new WebLinks("College Girl 1990 DVD HQ Amita","Sy5LI6WYhLE","|"), db);
    			addLink(new WebLinks("Coolie No. 1 1995 DVD Govinda","KKZ0A8-lEBE","|"), db);
    			addLink(new WebLinks("Daag 1999 DVD Sanjay Dutt","XtUZROpRoVU","|"), db);
    			addLink(new WebLinks("Dada 1999 DVD HQ Mithun Chakraborty","cP_buCE4odc","|"), db);
    			addLink(new WebLinks("Dahleez 1996 DVD HQ Jackie Shroff","GjJw9nycTs4","|"), db);
    			addLink(new WebLinks("Dancer 1991 DVD HQ Akshay Kumar","RNLzBarj7og","|"), db);
    			addLink(new WebLinks("Darmiyaan 1997 DVD Kiron Kher","-zWR5XUPt0I","|"), db);
    			addLink(new WebLinks("Daulat Ki Jung 1992 DVD HQ Aamir Khan","4E7OG97PdHw","|"), db);
    			addLink(new WebLinks("Deedar 1992 DVD HQ Akshay Kumar","jUczCepeixQ","|"), db);
    			addLink(new WebLinks("Deewana Mujh Sa Nahin 1990 DVD HQ Aamir Khan","kLrivX5dWF8","|"), db);
    			addLink(new WebLinks("Dharam Ka Insaaf 1993 DVD HQ Sumeet Saigal","1yidXv_pEz0","|"), db);
    			addLink(new WebLinks("Dhartiputra 1993 DVD HQ Rishi Kapoor","6OcX1Na91z8","|"), db);
    			addLink(new WebLinks("Dil Aashna Hai 1992 DVD HQ Shahrukh Khan","KJX2R8WOG44","|"), db);
    			addLink(new WebLinks("Dil Apna Aur Preet Paraee 1993 DVD Saahil","Tg40NKMC75s","|"), db);
    			addLink(new WebLinks("Dil Hai Ki Manta Nahin 1991 DVD HQ Aamir Khan","QRC9t29jo9E","|"), db);
    			addLink(new WebLinks("Dil Hi To Hai 1992 DVD HQ Jackie Shroff","bFS6kS15jgY","|"), db);
    			addLink(new WebLinks("Dil Ka Sauda 1999 DVD Dilip Thadeshwar","So-dm_cLUo8","|"), db);
    			addLink(new WebLinks("Dil Ki Baazi 1993 DVD HQ Akshay Kumar","k_otDYXn8z4","|"), db);
    			addLink(new WebLinks("Dil Se 1998 DVD Shahrukh Khan","Y-XvPs8e_Oc","|"), db);
    			addLink(new WebLinks("Dil Tera Aashiq 1993 Salman Khan","n3lu7SGzs8E","|"), db);
    			addLink(new WebLinks("Dil Tera Diwana 1996 DVD HQ Saif Ali Khan","xaDMnVfH2og","|"), db);
    			addLink(new WebLinks("Dilbar 1995 DVD HQ Mamta Kulkarni","vAc-Caa-0HM","|"), db);
    			addLink(new WebLinks("Diljale 1996 DVD Ajay Devgan","lagD6wHrGy4","|"), db);
    			addLink(new WebLinks("Dilwale 1994 DVD Ajay Devgan","lUrDNqWLjUo","|"), db);
    			addLink(new WebLinks("Dilwale Kabhi Na Hare 1992 DVD Rahul Roy","jA5M9aDIBJk","|"), db);
    			addLink(new WebLinks("Divya Shakti 1993 DVD HQ Ajay Devgan","rGRNFTX3yzY","|"), db);
    			addLink(new WebLinks("Do Fantoosh 1993 DVD Amjad Khan","_hvH8Ix-dvw","|"), db);
    			addLink(new WebLinks("Do Matwale 1991 DVD Sanjay Dutt","HdBNZ9Hh68U","|"), db);
    			addLink(new WebLinks("Do Numbri 1998 DVD HQ Mithun Chakraborty","hEeGf2Ww6LE","|"), db);
    			addLink(new WebLinks("Drohi 1992 DVD Nagarjuna","3H-DBOTAza4","|"), db);
    			addLink(new WebLinks("Dulhe Raja 1998 DVD HQ Govinda","qiCtVW_sPxY","|"), db);
    			addLink(new WebLinks("Duniya Na Mane 1997 DVD Pradeep Kumar","HoCWPpx_SSk","|"), db);
    			addLink(new WebLinks("Dushman Devta 1991 DVD Dharmendra","xoKt1bWuJYs","|"), db);
    			addLink(new WebLinks("Ek Anari Do Khiladi 1996 Balakrishna","hif8dNikg9g","|"), db);
    			addLink(new WebLinks("Ek Ladka Ek Ladki 1992 DVD Salman Khan","AlTc1akFlCA","|"), db);
    			addLink(new WebLinks("Ek Phool Teen Kante 1997 DVD HQ Vikas Bhalla","z20Wh_qFlD0","|"), db);
    			addLink(new WebLinks("Ek Tha Raja 1996 DVD Saif Ali Khan","mrrHkRpzQvQ","|"), db);
    			addLink(new WebLinks("Farishtay 1991 DVD HQ Dharmendra","Z9--L1Mul3o","|"), db);
    			addLink(new WebLinks("Fauj 1994 DVD HQ Kamal Sadanah","xPTE-666jWY","|"), db);
    			addLink(new WebLinks("First Love Letter 1991 DVD HQ Vivek Mushran","Auy3ntekjaU","|"), db);
    			addLink(new WebLinks("Gajab Tamaasa 1992 DVD HQ Rahul Roy","RZb1PEVqDXY","|"), db);
    			addLink(new WebLinks("Ganga Bani Shola 1995 Sripradha","3gDJP4jfsAA","|"), db);
    			addLink(new WebLinks("Gangster 1994 DVD HQ Dev Anand","WaFeweHJhlU","|"), db);
    			addLink(new WebLinks("Gardish 1993 DVD HQ Jackie Shroff","DEbjIlIcasE","|"), db);
    			addLink(new WebLinks("Geetanjali 1993 DVD Jeetendra","5aXjsMmPtlc","|"), db);
    			addLink(new WebLinks("Ghar Ho To Aisa 1990 DVD HQ Anil Kapoor","D3cOTWwpsFs","|"), db);
    			addLink(new WebLinks("Ghar Parivaar 1991 DVD HQ Rajesh Khanna","qpHLx-wHW1k","|"), db);
    			addLink(new WebLinks("Ghulam E Musthafa 1997 DVD HQ Nana Patekar","sQZEnS4Ccqs","|"), db);
    			addLink(new WebLinks("Gunda 1998 DVD Mithun Chakraborty","vYd2XcIxGBk","|"), db);
    			addLink(new WebLinks("Gunehgar Kaun 1991 DVD HQ Raj Babbar","M9uRTfyzzPM","|"), db);
    			addLink(new WebLinks("Haque 1991 DVD Dimple Kapadia","y4v4Kx7jwOY","|"), db);
    			addLink(new WebLinks("Haseena Maan Jaayegi 1999 Sanjay Dutt","3p_rnl2PFEY","|"), db);
    			addLink(new WebLinks("Hasina Aur Nagina 1997 DVD HQ Ekta","ItU6ivHNUSc","|"), db);
    			addLink(new WebLinks("Hasti 1993 DVD HQ Jackie Shroff","BeimyCM36BE","|"), db);
    			addLink(new WebLinks("Hathkadi 1995 DVD HQ Govinda","fK3Roar5owU","|"), db);
    			addLink(new WebLinks("Hawai Dhamaka 1991 DVD HQ Charan Raj","O1U5yQd3sCA","|"), db);
    			addLink(new WebLinks("Heer Ranjha 1992 DVD HQ Anil Kapoor","-B2vVRoMsJM","|"), db);
    			addLink(new WebLinks("Hero Hindustani 1998 Arshad Warsi","Y-tp5sEUdzQ","|"), db);
    			addLink(new WebLinks("Hero No. 1 1997 DVD Govinda","dF49eyaTxkM","|"), db);
    			addLink(new WebLinks("Himmat 1996 DVD HQ Sunny Deol","NfS0ATRU77E","|"), db);
    			addLink(new WebLinks("Himmatwala 1998 DVD Mithun Chakraborty","6vvT-qoEQTw","|"), db);
    			addLink(new WebLinks("Hindustani 1996 DVD Kamal Hassan","M3a3iC6ZyXA","|"), db);
    			addLink(new WebLinks("Hulchul 1995 DVD HQ Ajay Devgan","fvjRkd2_lxU","|"), db);
    			addLink(new WebLinks("Hum 1991 DVD HQ Amitabh Bachchan","msqkase5jsM","|"), db);
    			addLink(new WebLinks("Hum Aapke Dil Mein Rehte Hain 1999 DVD HQ Anil Kapoor","0Ghy4Qv19DM","|"), db);
    			addLink(new WebLinks("Hum Aapke Hai Koun 1994 Salman Khan","FtW4C1Y4kRc","|"), db);
    			addLink(new WebLinks("Hum Dono 1995 DVD HQ Rishi Kapoor","PGHpc77VK1U","|"), db);
    			addLink(new WebLinks("Hum Hain Bemisaal 1994 DVD HQ Akshay Kumar","WLAdeqXfUvs","|"), db);
    			addLink(new WebLinks("Hum Hain Kamaal Ke 1993 DVD HQ Sheeba","B7GwSIdQ4y8","|"), db);
    			addLink(new WebLinks("Hum Saath Saath Hain 1999 Salman Khan","R7udCAEsRm4","|"), db);
    			addLink(new WebLinks("Humlaa 1992 DVD HQ Dharmendra","NQDgOHeAxxM","|"), db);
    			addLink(new WebLinks("Humse Na Takrana 1990 DVD HQ Dharmendra","FtEisC1S524","|"), db);
    			addLink(new WebLinks("Hyderabad Blues 1998 Nagesh Kukunoor","b5VbX4wzb4M","|"), db);
    			addLink(new WebLinks("Imtihaan 1994 Saif Ali Khan","2m3XJynGcgs","|"), db);
    			addLink(new WebLinks("Ishq 1997 DVD HQ Aamir Khan","aLb4v_jgmjE","|"), db);
    			addLink(new WebLinks("Itihaas 1997 Ajay Devgan","4B2Eq5GlrY4","|"), db);
    			addLink(new WebLinks("Jaan 1996 DVD HQ Ajay Devgan","H8nHm4zCfpA","|"), db);
    			addLink(new WebLinks("Jaan Ki Kasam 1991 Krisna","ihixGPkmUww","|"), db);
    			addLink(new WebLinks("Janam Se Pehle 1990 DVD HQ Raj Babbar","DK0Eo9At9ws","|"), db);
    			addLink(new WebLinks("Jawani Zindabad 1990 DVD HQ Aamir Khan","1a5rJ1hflps","|"), db);
    			addLink(new WebLinks("Jayate 1998 DVD Sachin Khedekar","GxenWZpCYI0","|"), db);
    			addLink(new WebLinks("Jeene Do 1990 DVD HQ Sanjay Dutt","qK_NyUpkE_4","|"), db);
    			addLink(new WebLinks("Jeevan Daata 1991 DVD Chunky Pandey","zLBfZHDfYTc","|"), db);
    			addLink(new WebLinks("Jeevan Ki Shatranj 1993 DVD HQ Mithun Chakraborty","Hiz7v_j3zxs","|"), db);
    			addLink(new WebLinks("Jeevan Yudh 1997 DVD HQ Mithun Chakraborty","a2_r_61qtUg","|"), db);
    			addLink(new WebLinks("Jhoothi Shaan 1991 DVD Mithun Chakraborty","iop4TfYuRO0","|"), db);
    			addLink(new WebLinks("Jigarwala 1991 DVD Anil Kapoor","QYPGTvpj95c","|"), db);
    			addLink(new WebLinks("Jo Jeeta Wohi Sikandar 1992 DVD HQ Aamir Khan","XziDIU_w2yI","|"), db);
    			addLink(new WebLinks("Judaai 1997 DVD Anil Kapoor","5iTci9dBRjA","|"), db);
    			addLink(new WebLinks("Judge Mujrim 1997 DVD HQ Jeetendra","lyo34I67-Rk","|"), db);
    			addLink(new WebLinks("Jung 1996 DVD Ajay Devgan","eE6ro0z12JE","|"), db);
    			addLink(new WebLinks("Jungle Love 1990 DVD HQ Goga Kapoor","0N1NhPjHZfA","|"), db);
    			addLink(new WebLinks("Kaalia 1997 DVD HQ Mithun Chakraborty","jWvu9MJt5Lk","|"), db);
    			addLink(new WebLinks("Kaaranama 1990 DVD HQ Vinod Khanna","e4bajqmsv4o","|"), db);
    			addLink(new WebLinks("Kabhi Na Kabhi 1998 DVD HQ Anil Kapoor","CinSrumZL7M","|"), db);
    			addLink(new WebLinks("Kal Ki Awaz 1992 DVD HQ Dharmendra","WrdS0VN1KeY","|"), db);
    			addLink(new WebLinks("Kartavya 1995 DVD Sanjay Kapoor","xSR2EfYVd5s","|"), db);
    			addLink(new WebLinks("Kasam Dhande Ki 1990 DVD HQ Sumeet Saigal","vtQXfAGKQuE","|"), db);
    			addLink(new WebLinks("Maahir 1996 DVD HQ Govinda","6HBp4phyzFc","|"), db);
    			addLink(new WebLinks("Madam X 1991 DVD HQ Rekha","HL3VRs-szlo","|"), db);
    			addLink(new WebLinks("Madhosh 1994 DVD Faisal Khan","0pa1EPgkqtA","|"), db);
    			addLink(new WebLinks("Mafia 1996 DVD Dharmendra","IcjzClD_1zs","|"), db);
    			addLink(new WebLinks("Mahakaal 1993 DVD Karan Shah","M7UA-2_DMzM","|"), db);
    			addLink(new WebLinks("Main Hoon Sherni 1992 Archana Puran Singh","486lYB4sUAI","|"), db);
    			addLink(new WebLinks("Main Khiladi Tu Anari 1994 DVD HQ Akshay Kumar","HQGZHXlaBTU","|"), db);
    			addLink(new WebLinks("Manchala 1999 DVD Vivek Mushran","L_vO82E1ZE0","|"), db);
    			addLink(new WebLinks("Mann 1999 DVD HQ Aamir Khan","NOHMFjMlJwI","|"), db);
    			addLink(new WebLinks("Mard 1998 DVD HQ Mithun Chakraborty","jPM_p_k86-4","|"), db);
    			addLink(new WebLinks("Maut Ki Sazaa 1991 DVD HQ Ashok Kumar","wpsrLW51WXM","|"), db);
    			addLink(new WebLinks("Mawali Raj 1993 DVD Balakrishna","B_NwLfmnGfc","|"), db);
    			addLink(new WebLinks("Meena Bazaar 1991 Om Puri","sxmLgyCSp-A","|"), db);
    			addLink(new WebLinks("Meera Ke GIrdhar 1993 DVD HQ Upasna Khosla","JTH7o772XDw","|"), db);
    			addLink(new WebLinks("Mehandi Ban Gai Khoon 1991 DVD Juhi Chawla","vgEoIJy9-VI","|"), db);
    			addLink(new WebLinks("Meherbaan 1993 DVD HQ Mithun Chakraborty","KSOETiaLksU","|"), db);
    			addLink(new WebLinks("Mera Damad 1995 DVD HQ Ashok Kumar","vzW9fPNsiTw","|"), db);
    			addLink(new WebLinks("Meri Janeman 1991 Kader Khan","36vCe99SPsE","|"), db);
    			addLink(new WebLinks("Meri Partigya 1996 Mithun Chakraborty","5XBhUkmZ57M","|"), db);
    			addLink(new WebLinks("Mohabbat 1997 DVD HQ Sanjay Kapoor","LcfmS4fChZg","|"), db);
    			addLink(new WebLinks("Mohabbat Aur Jung 1998 DVD HQ Kamal Sadanah","5C5bmwR98sc","|"), db);
    			addLink(new WebLinks("Moun 1996 DVD HQ Arvind Swamy","WsyJdJ0fj-8","|"), db);
    			addLink(new WebLinks("Mr Bechara 1996 DVD HQ Anil Kapoor","SqdaVVZxV1Y","|"), db);
    			addLink(new WebLinks("Mr Bond 1992 DVD HQ Akshay Kumar","MNUdaeRcuwM","|"), db);
    			addLink(new WebLinks("Muqabla 1993 DVD Govinda","eSBkFfO-EI0","|"), db);
    			addLink(new WebLinks("Muskurahat 1992 DVD HQ Jay Mehta","Nzqvziuy_WA","|"), db);
    			addLink(new WebLinks("Naach Govinda Naach 1992 Govinda","lDTfIDowVd0","|"), db);
    			addLink(new WebLinks("Naamcheen 1991 DVD HQ Aditya Pancholi","2iVETYUnsHg","|"), db);
    			addLink(new WebLinks("Nache Nagin Gali Gali 1990 Meenakshi Seshadri","ucd18WVyq7Q","|"), db);
    			addLink(new WebLinks("Nachnewale Gaanewale 1991 Sheeba","P_0YXyelqFE","|"), db);
    			addLink(new WebLinks("Nai Raat Nai Baat 1991 Devan","W_YYo_G43q0","|"), db);
    			addLink(new WebLinks("Nakabandi 1990 Dharmendra","hAkQ0XVyDRo","|"), db);
    			addLink(new WebLinks("Narasimha 1991 DVD HQ Sunny Deol","MpuXC7oSGL0","|"), db);
    			addLink(new WebLinks("Nidaan 1997 DVD Shivaji Satam","TLWxuxvqQcU","|"), db);
    			addLink(new WebLinks("Oh Darling Yeh Hai India 1995 DVD HQ Shahrukh Khan","aJZUHyhJ8so","|"), db);
    			addLink(new WebLinks("Paap Ki Kamaee 1990 DVD Mithun Chakraborty","Ev2pB_jKi84","|"), db);
    			addLink(new WebLinks("Paappi Devataa 1995 Dharmendra","OwTVv7TBjgI","|"), db);
    			addLink(new WebLinks("Paayal 1992 DVD Bhagyashree","Kfl5rzS_dPg","|"), db);
    			addLink(new WebLinks("Paisa Paisa Paisa 1993 Tushar Dalvi","TU1bfwD30UQ","|"), db);
    			addLink(new WebLinks("Paise Ke Peechey 1992 Tariq","YxBDeJX9gms","|"), db);
    			addLink(new WebLinks("Panaah 1992 DVD HQ Naseeruddin Shah","V-0SNFBA6rk","|"), db);
    			addLink(new WebLinks("Papa Kahte Hain 1996 DVD HQ Anupam Kher","ebkIZljhADk","|"), db);
    			addLink(new WebLinks("Papi Gudia 1996 DVD HQ Avinash Wadhavan","artR55XEuvY","|"), db);
    			addLink(new WebLinks("Paramaatma 1994 DVD HQ Mithun Chakraborty","MQkRDwKMUUk","|"), db);
    			addLink(new WebLinks("Pardes 1997 DVD Shahrukh Khan","T-LY4gH55ck","|"), db);
    			addLink(new WebLinks("Pardesi 1993 DVD HQ Mithun Chakraborty","Qzwn1ub5InI","|"), db);
    			addLink(new WebLinks("Pathreela Raasta 1994 DVD HQ Dimple Kapadia","S9DBSi-0JtQ","|"), db);
    			addLink(new WebLinks("Pati Patni Aur Tawaif 1990 DVD HQ Mithun Chakraborty","7q8t9xgN02w","|"), db);
    			addLink(new WebLinks("Phool Aur Kaante 1991 DVD HQ Ajay Devgan","NdFb8wOzom8","|"), db);
    			addLink(new WebLinks("Phool Bane Patthar 1996 DVD Avinash Wadhavan","Ym7L3c43Jj0","|"), db);
    			addLink(new WebLinks("Police Ki Jung 1995 DVD HQ Mammootty","uDNoXZhsqW0","|"), db);
    			addLink(new WebLinks("Police Public 1990 DVD HQ Raaj Kumar","jIRcv1buJ0k","|"), db);
    			addLink(new WebLinks("Policewala Gunda 1995 DVD HQ Dharmendra","IjA85yBhWck","|"), db);
    			addLink(new WebLinks("Pratigyabadh 1991 DVD HQ Mithun Chakraborty","tbt_7IA8Za0","|"), db);
    			addLink(new WebLinks("Pratikar 1991 DVD HQ Anil Kapoor","73A0TMYBI9s","|"), db);
    			addLink(new WebLinks("Prem 1995 DVD HQ Sanjay Kapoor","IE2zV1v2lTI","|"), db);
    			addLink(new WebLinks("Prem Aggan 1998 DVD HQ Fardeen Khan","HL6PfmFHR3c","|"), db);
    			addLink(new WebLinks("Prem Path 1997 DVD Shashi Kumar","19DWvQeLlDs","|"), db);
    			addLink(new WebLinks("Prem Qaidi 1991 DVD HQ Harish","OHzqh8D5NrY","|"), db);
    			addLink(new WebLinks("Prem Shakti 1994 DVD Govinda","TAUV-74iOHg","|"), db);
    			addLink(new WebLinks("Prithvi 1997 DVD Sunil Shetty","ugUDB2zflnc","|"), db);
    			addLink(new WebLinks("Professor Ki Padosan 1993 DVD HQ Sanjeev Kumar","t50RBIpKGE8","|"), db);
    			addLink(new WebLinks("Pyaar Ke Naam Qurban 1990 Mithun Chakraborty","VtRaZ2_Za80","|"), db);
    			addLink(new WebLinks("Pyaar Koi Khel Nahin 1999 Sunny Deol","0XWEsg2PlV8","|"), db);
    			addLink(new WebLinks("Pyaar To Hona Hi Tha 1998 Ajay Devgan","16XWdoiTmB4","|"), db);
    			addLink(new WebLinks("Pyar Hua Chori Chori 1991 DVD Mithun Chakraborty","5CqEc67iIT8","|"), db);
    			addLink(new WebLinks("Pyar Ka Devta 1990 DVD HQ Mithun Chakraborty","Fj13-ir-oEE","|"), db);
    			addLink(new WebLinks("Pyar Ka Taraana 1993 Aneeta Ayoob","UZMUq39xlYE","|"), db);
    			addLink(new WebLinks("Qahar 1997 DVD HQ Sunny Deol","KlYVXz9Ktfc","|"), db);
    			addLink(new WebLinks("Qila 1998 DVD HQ Dilip Kumar","0YCHNWDEMJA","|"), db);
    			addLink(new WebLinks("Raat 1992 DVD Revathi","MYUSqtqnYzg","|"), db);
    			addLink(new WebLinks("Raja 1995 DVD HQ Sanjay Kapoor","9170Hhy2ndg","|"), db);
    			addLink(new WebLinks("Raja Babu 1994 DVD HQ Govinda","DQwweMA52Eg","|"), db);
    			addLink(new WebLinks("Ram Jaane 1995 DVD Shahrukh Khan","k6OFS0olUGs","|"), db);
    			addLink(new WebLinks("Ram Shastra 1995 DVD HQ Jackie Shroff","kR9Qi8aUmSY","|"), db);
    			addLink(new WebLinks("Ramgarh Ke Sholay 1991 DVD HQ Vijay Saxena","mAEAj2fPLn8","|"), db);
    			addLink(new WebLinks("Rampur Ka Raja 1993 DVD HQ Venkatesh","TOKqeVbc3Sg","|"), db);
    			addLink(new WebLinks("Rang 1993 DVD Jeetendra","w8Hmj5mrTqM","|"), db);
    			addLink(new WebLinks("Roja 1992 DVD HQ Arvind Swamy","dWPdoROlJtU","|"), db);
    			addLink(new WebLinks("Roop Ki Rani Choron Ka Raja 1993 DVD HQ Anil Kapoor","JxjTdw8s0W8","|"), db);
    			addLink(new WebLinks("Roti Ki Keemat 1990 DVD HQ Mithun Chakraborty","c8Wk6Tf30RQ","|"), db);
    			addLink(new WebLinks("Saajan Chale Sasural 1996 DVD Govinda","nyg6Dp55H94","|"), db);
    			addLink(new WebLinks("Saajan Ki Baahon Mein 1995 DVD HQ Rishi Kapoor","5N1mug_Qqmg","|"), db);
    			addLink(new WebLinks("Sahebzaade 1992 DVD Sanjay Dutt","AXabVz83myk","|"), db);
    			addLink(new WebLinks("Sainik 1993 DVD Akshay Kumar","15p8TZk74Lk","|"), db);
    			addLink(new WebLinks("Salakhen 1998 DVD HQ Sunny Deol","GziRkr7_I5o","|"), db);
    			addLink(new WebLinks("Sanam Harjai 1995 DVD Himanshu","xOjtMxUW0s0","|"), db);
    			addLink(new WebLinks("Sandhya Chhaya 1995 DVD Shriram Lagoo","H0fwZP-Sbmg","|"), db);
    			addLink(new WebLinks("Sangram 1993 DVD HQ Ajay Devgan","iTgO6cT75I8","|"), db);
    			addLink(new WebLinks("Sapnay 1997 DVD HQ Arvind Swamy","-mHQ2vTwaAw","|"), db);
    			addLink(new WebLinks("Sapoot 1996 DVD HQ Akshay Kumar","br-x9S2fg00","|"), db);
    			addLink(new WebLinks("Sarfarosh 1999 DVD HQ Aamir Khan","_tBFoXDkcO4","|"), db);
    			addLink(new WebLinks("Sarphira 1992 DVD HQ Sanjay Dutt","ZM8Y7yK_Cxs","|"), db);
    			addLink(new WebLinks("Shaitan Mantrik 1990 Hemant","QMJGr6ZAuEs","|"), db);
    			addLink(new WebLinks("Shandaar 1990 DVD Mithun Chakraborty","V1YNWtQrKMU","|"), db);
    			addLink(new WebLinks("Shathru 1993 DVD Vijaykant","lX4IEdu9Ng4","|"), db);
    			addLink(new WebLinks("Shatranj 1993 DVD HQ Mithun Chakraborty","6atUDq3TCNI","|"), db);
    			addLink(new WebLinks("Sher Dil 1990 DVD HQ Dharmendra","31fzX3m2j0g","|"), db);
    			addLink(new WebLinks("Teri Talash Mein 1990 DVD HQ Pradeepta","NC5jpFSuDgY","|"), db);
    			addLink(new WebLinks("Thanedaar 1990 DVD Jeetendra","IyEdgvkRfcg","|"), db);
    			addLink(new WebLinks("Trimurti 1995 DVD Shahrukh Khan","PkYYwz5Z0Uw","|"), db);
    			addLink(new WebLinks("Tu Chor Main Sipahi 1996 DVD HQ Akshay Kumar","oO94ZCNRdyw","|"), db);
    			addLink(new WebLinks("Tum Karo Vaada 1993 Raj","H9iAMmTnOMY","|"), db);
    			addLink(new WebLinks("Umar 55 Ki Dil Bachpan Ka 1992 DVD HQ Akshay Anand","QahPARUkak0","|"), db);
    			addLink(new WebLinks("Vaastav 1999 DVD HQ Sanjay Dutt","j1ydiReyw9M","|"), db);
    			addLink(new WebLinks("Veertaa 1993 DVD HQ Sunny Deol","YtL8jAo_vi8","|"), db);
    			addLink(new WebLinks("Veeru Dada 1990 DVD Dharmendra","k1ndtyCB4LA","|"), db);
    			addLink(new WebLinks("Virasat 1997 DVD HQ Anil Kapoor","DTwDREzWqk8","|"), db);
    			addLink(new WebLinks("Virodhi 1992 DVD HQ Dharmendra","mCBdB3LHZ4E","|"), db);
    			addLink(new WebLinks("Vishnu Deva 1991 DVD HQ Sunny Deol","Z7sRONZDAp4","|"), db);
    			addLink(new WebLinks("Vishwasghaat 1996 DVD Sunil Shetty","KwlTE-4o43Y","|"), db);
    			addLink(new WebLinks("Waqt Ki Zanjeer 1999 DVD Prakash Raj","4N34Z1MLuPE","|"), db);
    			addLink(new WebLinks("Yaad Rakhegi Duniya 1992 DVD HQ Aditya Pancholi","-535LMRyxNU","|"), db);
    			addLink(new WebLinks("Yaar Gaddar 1994 DVD HQ Saif Ali Khan","O-9Fm1K89lc","|"), db);
    			addLink(new WebLinks("Yalgaar 1992 DVD HQ Feroz Khan","bxz-aegLzwE","|"), db);
    			addLink(new WebLinks("Yash 1996 DVD HQ Vijay Anand","OLNuT0kds1M","|"), db);
    			addLink(new WebLinks("Yudhpath 1992 DVD HQ Siddharth Ray","acPJVmRKTRk","|"), db);
    			addLink(new WebLinks("Yugandhar 1993 DVD HQ Mithun Chakraborty","EItzI9lCVDY","|"), db);
    			addLink(new WebLinks("Yugpurush 1998 DVD HQ Jackie Shroff","UJUyydx3Sy0","|"), db);
    			addLink(new WebLinks("Zakhmi Dil 1994 DVD HQ Akshay Kumar","cCzx_lU4vWg","|"), db);
    			addLink(new WebLinks("Zakhmo Ka Hisaab 1993 DVD HQ Govinda","30P0SN-Buw0","|"), db);
    			addLink(new WebLinks("Zor 1997 DVD HQ Sunny Deol","dbm2fLXVzpc","|"), db);
    			addLink(new WebLinks("Zulm Ka Baadshah 1998 DVD Rajnikanth","gOcoYbZby2Y","|"), db);





    	
    	
    	
    	
    	
    }
}
