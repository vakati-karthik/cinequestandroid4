package com.owentech.testswipeab;


import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TabOne extends Fragment
{
	private static ArrayList<Tweet> tweetList = new ArrayList<Tweet>();
	private static ListView listView;

	 @Override
	    public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);

		listView = (ListView) getView().findViewById(R.id.ListViewId1);
		new DownloadTextTask().execute("iphone");
		listView.setAdapter(new TweetItemAdapter(getActivity(), R.layout.twitteritem, tweetList));
	    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.tabone, container, false);
        
		
		return view;
	}
	
	private static final class DownloadTextTask extends AsyncTask<String, Void,Void>{

    	protected Void doInBackground(String... strings) {
    		
    		String index = strings[0];
    		 try{
    			  tweetList = getTweets(index, 1);
    	        }catch(Exception e){
    	            Log.e(getClass().toString(), e.getMessage());
    	        }
    		 return null;

    	} 
         

         protected void onPostExecute(Void nothing) {
         }
         
         public Bitmap getBitmap(String bitmapUrl) {
   		  try {
   		    URL url = new URL(bitmapUrl);
   		    return BitmapFactory.decodeStream(url.openConnection().getInputStream()); 
   		  }
   		  catch(Exception ex) {return null;}
   		}
         
         public ArrayList<Tweet> getTweets(String searchTerm, int page) {
        	  String searchUrl = 
        	        "http://search.twitter.com/search.json?q=@" 
        	        + searchTerm + "&rpp=10&page=" + page;

        	  ArrayList<Tweet> tweets = 
        	        new ArrayList<Tweet>();
        	  JSONParser jParser = new JSONParser();
        	  JSONObject json = jParser.getJSONFromUrl(searchUrl);
        	  
        	  try {
        		    // Getting Array of Contacts
        		    JSONArray result = json.getJSONArray("results");
        		 
        		    // looping through All Contacts
        		    for(int i = 0; i < result.length(); i++){
        		        JSONObject c = result.getJSONObject(i);
        		        String userID = c.get("from_user").toString();
        		        String text = c.get("text").toString();
        		        String imgUrl = c.get("profile_image_url").toString();
        		        Bitmap image = getBitmap(imgUrl);
        		        Tweet tweet = new Tweet(userID, text, imgUrl, image);
        		        tweets.add(tweet);

        		    }
        		} catch (JSONException e) {
        		    e.printStackTrace();
        		}
        	  return tweets;
        	}
    }
	
}
