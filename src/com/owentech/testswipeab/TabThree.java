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

public class TabThree extends Fragment{
	private DvdList dvdList;
	private ArrayList<String> dvdNameList;
	private ListView listView;
	private DvdListAdapter adapter;
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);
	    dvdList = new DvdList();
		listView = (ListView) getView().findViewById(R.id.ListViewId2);
		new DownloadTextTask().execute("http://mobile.cinequest.org/mobileCQ.php?type=dvds");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.tabtwo, container, false);
		return view;
	}

	private class DownloadTextTask extends AsyncTask<String, Void,Void>{

		protected Void doInBackground(String... strings) {
			 try{
				  URL url = new URL(strings[0]);
				  InputSource is = new InputSource(url.openStream());
				  is.setEncoding("ISO-8859-1");
				  dvdList.readFromXml(is);
				  dvdNameList = dvdList.getNameList();
		        }catch(Exception e){
		            Log.e(getClass().toString(), e.getMessage());
		        }
			 return null;
		} 
	
	     protected void onPostExecute(Void nothing) {
	    	adapter = new DvdListAdapter(getActivity(), dvdNameList);
	 		listView.setAdapter(adapter);
	     }
	     
	}
}
