package com.ravi.hindinewmovies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;



public class WebLinks {
	  int _id;
	    String _name;
	    String _link;
	    String _country;
	    Boolean _countryFilteringFlag = false;
	    public String id;
	    public Context context;
	    private int currentImage = 0;
	    
	   /* OnClickListener listener1 = new OnClickListener(){ // the book's action
	        @Override
	        public void onClick(View v) {
	                            
	        	currentImage++;
	            currentImage = currentImage % 2;
	String selection = id;
	     		
	     	
	DatabaseHandler db = new DatabaseHandler(context);	
	     		WebLinks w = db.getLink(selection);
	        	//Button b1 = (Button)v.findViewById(R.id.fav);
	        	 
	            //Set the image depending on the counter.
	            switch (currentImage) {
	           
	            case 0: b1.setBackgroundResource(R.drawable.watch1); 
	            String value2 = id;
	    		
	    		//prevvalue = null;
	    			db.deleteFav(w.get_id());   
	    		
	                    break;
	            case 1: b1.setBackgroundResource(R.drawable.watch3);
			            String value = id;
			    		//DatabaseHandler db = new DatabaseHandler(context);	
			    		
			    			db.addFav(w);   
			    		 
	            		break;
	            default:b1.setBackgroundResource(R.drawable.watch1);     
	            }
	            
	        }
	    };*/
	    OnClickListener listener2 = new OnClickListener(){ // the book's action
	        @Override
	        public void onClick(View v) {
	        	final DatabaseHandler db = new DatabaseHandler(context);
	        
	        	
	        	Intent intent = new Intent(context, WebViewActivity.class);
	     		Bundle b = new Bundle();
	     		
	     		
	     		String selection = id;
	     		
	     	
	     		
	     		WebLinks w = db.getLink(selection);

	     		b.putString("key", w.get_link());
	     		b.putString("key2", w.get_name());
	     		intent.putExtras(b);

	     		context.startActivity(intent);
	            }
	            
	        }
	    ;
	   
	    public WebLinks(){
	    	
	    }
	    public WebLinks(int id, String name, String link, String country){
	        this._id = id;
	        this._name = name;
	        this._link = link;
	        this._country = country;
	        
	    }
	    public WebLinks( String name, String link, String country){
	        
	        this._name = name;
	        this._link = link;
	        this._country = country;
	        
	    }
		public int get_id() {
			return _id;
		}
		public void set_id(int _id) {
			this._id = _id;
		}
		public String get_name() {
			return _name;
		}
		public void set_name(String _name) {
			this._name = _name;
		}
		public String get_link() {
			return _link;
		}
		public void set_link(String _link) {
			this._link = _link;
		}
		public String get_country() {
			return _country;
		}
		public void set_country(String _country) {
			this._country = _country;
		}
		
		public String toString(){
			if(this._countryFilteringFlag)
				return this.get_country();
			else
			return this.get_name();
		}
}
