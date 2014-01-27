package com.ravi.worldnews;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class OurArrayAdapter extends ArrayAdapter{//need arrayadapter for filterning and making listview dynamically

    Context context;
    int layoutResourceId;   
    WebLinks data[] = null;
   
    public OurArrayAdapter(Context context, int layoutResourceId, WebLinks[] data) {//layoutResourceIdis layout filename
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        WeatherHolder holder = null;
       
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
           
            holder = new WeatherHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);//in list_item.xml layout
           
            holder.txtTitle = (TextView)row.findViewById(R.id.txtTitle);//R.id.txtTitle in list_item.xml layout
           
            row.setTag(holder);//set data so we can retrieve it later on click of listitem click
        }//this whole class seems a generic array adapter for a list view.
        else
        {
            holder = (WeatherHolder)row.getTag();
        }
    
        WebLinks weather = (WebLinks)getItem(position);
        if(weather._countryFilteringFlag){
        holder.txtTitle.setText(Character.toUpperCase(weather.get_country().charAt(0)) + weather.get_country().substring(1) + " - "+weather.get_name());
        }
        else{
        	//holder.txtTitle.setText(weather.get_name());
        	holder.txtTitle.setText(weather.get_name()  + " - "+ Character.toUpperCase(weather.get_country().charAt(0))+ weather.get_country().substring(1));
        }
        //+" " +weather._country set text is different its displayname. 
        //filtering and other aspects work based on adapter. we pass the tostring method return value to webviewactivity
        holder.imgIcon.setImageResource(R.drawable.arrow_icon);
      
        return row;
    }
   
    static class WeatherHolder
    {
        ImageView imgIcon;//this is imageview type
       
        TextView txtTitle;
    }
}

