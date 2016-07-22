//package com.example.gui;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.app.Dialog;
//import android.content.Context;
//import android.os.Bundle;
//import android.view.Gravity;
//import android.view.KeyEvent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.Window;
//import android.view.WindowManager;
//import android.widget.AdapterView;
//import android.widget.Button;
//import android.widget.GridView;
//import android.widget.SimpleAdapter;
//import android.widget.Toast;
//
//public class SubMenuTest extends Activity {
//	private AlertDialog alertDialog;
//	private GridView gridView;
//	private View view;
//	private int[] icons = { R.drawable.menu_exit, R.drawable.menu_choose, R.drawable.menu_area, R.drawable.menu_target,
//			R.drawable.menu_more };
//	private String[] titles = { "退出", "文件", "设置", "新建", "更多" };
//	private SimpleAdapter simpleAdapter;
//	private List<Map<String, Object>> datas;
//	
//	private Context mContext;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
////		super.onCreate(savedInstanceState);
////		setContentView(R.layout.activity_main);
////		view = LayoutInflater.from(this).inflate(R.layout.view, null);// 获得弹出框布局文件对象
////		initDatas();// 初始化数据集
////		// 初始化SimpleAdapter
////		simpleAdapter = new SimpleAdapter(this, datas, R.layout.item, new String[] { "icon", "title" },
////				new int[] { R.id.iv_icon, R.id.tv_title });
////		alertDialog = new AlertDialog.Builder(this).create();// 创建弹出框
////		alertDialog.setView(view);// 设置弹出框布局
////		gridView = (GridView) view.findViewById(R.id.gridview);
////		gridView.setAdapter(simpleAdapter);// 设置适配器
////		gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {// GridView子项单击事件监听
////			@Override
////			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////				Toast.makeText(SubMenuTest.this, "You clicked " + titles[position] + "button", Toast.LENGTH_SHORT).show();
////				alertDialog.dismiss();
////			}
//	        super.onCreate(savedInstanceState);
//	        setContentView(R.layout.activity_main);
//	        mContext = SubMenuTest.this;
//	        btShare = (Button) findViewById(R.id.share);
//	        btShare.setOnClickListener(new View.OnClickListener() {
//	            @Override
//	            public void onClick(View v) {
//	                final Dialog dialog = new Dialog(mContext, R.style.Theme_Light_Dialog);
//	                View dialogView = LayoutInflater.from(mContext).inflate(R.layout.view,null);
//	                //获得dialog的window窗口
//	                Window window = dialog.getWindow();
//	                //设置dialog在屏幕底部
//	                window.setGravity(Gravity.BOTTOM);
//	                //设置dialog弹出时的动画效果，从屏幕底部向上弹出
//	                window.setWindowAnimations(R.style.dialogStyle);
//	                window.getDecorView().setPadding(0, 0, 0, 0);
//	                //获得window窗口的属性
//	                android.view.WindowManager.LayoutParams lp = window.getAttributes();
//	                //设置窗口宽度为充满全屏
//	                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//	                //设置窗口高度为包裹内容
//	                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//	                //将设置好的属性set回去
//	                window.setAttributes(lp);
//	                //将自定义布局加载到dialog上
//	                dialog.setContentView(dialogView);
//	                btWeixin = (Button) dialogView.findViewById(R.id.bt_weixin);
//	                btWeibo = (Button) dialogView.findViewById(R.id.bt_weibo);
//	                btPengyouquan = (Button) dialogView.findViewById(R.id.bt_pengyouquan);
//	                dialog.show();
//	                btWeixin.setOnClickListener(new View.OnClickListener() {
//	                    @Override
//	                    public void onClick(View v) {
//	                        Toast.makeText(mContext,"分享到微信",Toast.LENGTH_SHORT).show();
//	                    }
//	                });
//	                btWeibo.setOnClickListener(new View.OnClickListener() {
//	                    @Override
//	                    public void onClick(View v) {
//	                        Toast.makeText(mContext,"分享到微博",Toast.LENGTH_SHORT).show();
//	                    }
//	                });
//	                btPengyouquan.setOnClickListener(new View.OnClickListener() {
//	                    @Override
//	                    public void onClick(View v) {
//	                        Toast.makeText(mContext,"分享到朋友圈",Toast.LENGTH_SHORT).show();
//	                    }
//	                });
//	            }
//	        });
//
//	}
//
//	private void initDatas() {
//		datas = new ArrayList<>();
//		for (int i = 0; i < titles.length; i++) {
//			Map map = new HashMap();
//			map.put("icon", icons[i]);
//			map.put("title", titles[i]);
//			datas.add(map);
//		}
//	}
//
//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {// 覆写了onKeyDown方法
//		if (keyCode == KeyEvent.KEYCODE_MENU) {// 当按下菜单键时
//			if (alertDialog == null) {
//				alertDialog = new AlertDialog.Builder(this).setView(view).show();
//			} else
//				alertDialog.show();
//		}
//		return super.onKeyDown(keyCode, event);
//	}
//}
