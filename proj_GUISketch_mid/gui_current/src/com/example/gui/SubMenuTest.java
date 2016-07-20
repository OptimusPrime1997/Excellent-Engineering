package com.example.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class SubMenuTest extends Activity {
	private AlertDialog alertDialog;
	private GridView gridView;
	private View view;
	private int[] icons = { R.drawable.menu_exit, R.drawable.menu_choose, R.drawable.menu_area, R.drawable.menu_target,
			R.drawable.menu_more };
	private String[] titles = { "退出", "文件", "设置", "新建", "更多" };
	private SimpleAdapter simpleAdapter;
	private List<Map<String, Object>> datas;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		view = LayoutInflater.from(this).inflate(R.layout.view, null);// 获得弹出框布局文件对象
		initDatas();// 初始化数据集
		// 初始化SimpleAdapter
		simpleAdapter = new SimpleAdapter(this, datas, R.layout.item, new String[] { "icon", "title" },
				new int[] { R.id.iv_icon, R.id.tv_title });
		alertDialog = new AlertDialog.Builder(this).create();// 创建弹出框
		alertDialog.setView(view);// 设置弹出框布局
		gridView = (GridView) view.findViewById(R.id.gridview);
		gridView.setAdapter(simpleAdapter);// 设置适配器
		gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {// GridView子项单击事件监听
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Toast.makeText(SubMenuTest.this, "You clicked " + titles[position] + "button", Toast.LENGTH_SHORT).show();
				alertDialog.dismiss();
			}
		});
	}

	private void initDatas() {
		datas = new ArrayList<>();
		for (int i = 0; i < titles.length; i++) {
			Map map = new HashMap();
			map.put("icon", icons[i]);
			map.put("title", titles[i]);
			datas.add(map);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {// 覆写了onKeyDown方法
		if (keyCode == KeyEvent.KEYCODE_MENU) {// 当按下菜单键时
			if (alertDialog == null) {
				alertDialog = new AlertDialog.Builder(this).setView(view).show();
			} else
				alertDialog.show();
		}
		return super.onKeyDown(keyCode, event);
	}
}
