package com.example.gui;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ForkListActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_list);
		Intent intent=getIntent();
		List<String> sList=intent.getStringArrayListExtra("sList");
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(ForkListActivity.this,
				android.R.layout.simple_list_item_1, sList);
		ListView listView = (ListView) findViewById(R.id.list_view);
		listView.setAdapter(adapter);
	}
	
	
}
