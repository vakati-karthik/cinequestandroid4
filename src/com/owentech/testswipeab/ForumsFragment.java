package com.owentech.testswipeab;

import java.net.URL;
import java.util.ArrayList;

import org.xml.sax.InputSource;

import android.support.v4.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ForumsFragment extends Fragment {
	private final String url = "http://mobile.cinequest.org/mobileCQ.php?type=xml&name=events";
	private ForumsList forumsList;
	private ArrayList<String> forumsNameList;
	private ListView listView;
	private ForumListAdapter adapter;
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);
	    forumsList = new ForumsList();
		listView = (ListView) getView().findViewById(R.id.ListViewId3);
		new DownloadTextTask().execute(url);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.forums, container, false);
		return view;
	}

	private class DownloadTextTask extends AsyncTask<String, Void,Void>{

		protected Void doInBackground(String... strings) {
			 try{
				  URL url = new URL(strings[0]);
				  InputSource is = new InputSource(url.openStream());
				  is.setEncoding("ISO-8859-1");
				  forumsList.readFromXml(is);
				  forumsNameList = forumsList.getNameList();
		        }catch(Exception e){
		            Log.e(getClass().toString(), e.getMessage());
		        }
			 return null;
		} 
	
	     protected void onPostExecute(Void nothing) {
	    	adapter = new ForumListAdapter(getActivity(), forumsNameList);
	 		listView.setAdapter(adapter);
	     }
	     
	}

}
