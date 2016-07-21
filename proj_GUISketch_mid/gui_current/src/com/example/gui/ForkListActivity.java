package com.example.gui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ForkListActivity extends Activity {
	
	private List<String> sList = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_list);
		Intent intent=getIntent();
		sList = intent.getStringArrayListExtra("sList");
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(ForkListActivity.this,
				android.R.layout.simple_list_item_1, sList);
		ListView listView = (ListView) findViewById(R.id.list_view);
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub;
				
				System.out.println("点击了。。。。。。。"+arg2);
				if (sList.size()>0) {
					
					Intent backIntent = new Intent();
					backIntent.putExtra("choosePath", sList.get(arg2));
					System.out.println(sList.get(arg2));
					setResult(RESULT_OK, backIntent);
					finish();
				}			
			}
		});
	}
	
	
}
