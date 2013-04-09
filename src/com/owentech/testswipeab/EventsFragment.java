package com.owentech.testswipeab;

import java.net.URL;
import java.util.ArrayList;

import org.xml.sax.InputSource;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class EventsFragment extends Fragment {
	private final String url = "http://mobile.cinequest.org/mobileCQ.php?type=xml&name=events";
	private EventsList eventsList;
	private ArrayList<String> eventsNameList;
	private ListView listView;
	private ForumListAdapter adapter;
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);
	    eventsList = new EventsList();
		listView = (ListView) getView().findViewById(R.id.ListViewId4);
		new DownloadTextTask().execute(url);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.events, container, false);
		return view;
	}

	private class DownloadTextTask extends AsyncTask<String, Void,Void>{

		protected Void doInBackground(String... strings) {
			 try{
				  URL url = new URL(strings[0]);
				  InputSource is = new InputSource(url.openStream());
				  is.setEncoding("ISO-8859-1");
				  eventsList.readFromXml(is);
				  eventsNameList = eventsList.getNameList();
		        }catch(Exception e){
		            Log.e(getClass().toString(), e.getMessage());
		        }
			 return null;
		} 
	
	     protected void onPostExecute(Void nothing) {
	    	adapter = new ForumListAdapter(getActivity(), eventsNameList);
	 		listView.setAdapter(adapter);
	     }
	     
	}

}
