package com.example.gui;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import sketch.gui.testing.ParseXML;
import sketch.gui.testing.TType;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnTouchListener {
	private ArrayList<PointF> graphics = new ArrayList<PointF>();// 所有点的集合
	private ArrayList<PointF> operationPoint = new ArrayList<PointF>();// 点击点的集合
	private ArrayList<PointF> endPoint = new ArrayList<PointF>();// 拖动结束点的集合
	private ArrayList<PointF> jointGraphics = new ArrayList<PointF>();// 两次及以上点的集合
	@SuppressLint("SdCardPath")
	private String sd = "/mnt/sdcard/screenShotPicture";
	private String input;
	private Logic logic = new Logic();

	private Resources r;
	private ImageView imageView;
	
	private Canvas canvas;
	private Paint paint;
	private Bitmap alterBitmap;
	private float downx = 0;
	private float downy = 0;
	private float upx = 0;
	private float upy = 0;
	private int clickCount = 0;
	private int stepCount = 0;// 绘制过程一共有四步，单击/双击/拖拽，绘制区域，forAll/resursiveForAll/forSome,选择target

	private gestureTrain gestureTrain;
	private combinationGestureTrain comGestureTrain;
	private IOOperation ioOperation = new IOOperation();
	private boolean isDrawed = false;// 有没有画过，在MotionEvent.ACTION_UP时置为true
	private boolean isDrawArea = false;// 是否在绘制区域
	private File currentImage;// 当前图片
	private File[] files;// 一个文件夹下所有文件
	private ArrayList<File> imageFiles = new ArrayList<File>();// 一个文件夹下所有的图片文件
/*---- Modify By zhchuch ---*/
	private String imagePath;
	private PointF[] rect;
	private int countDrawArea = 0;
	private boolean isModelCompleted = false;
	private TType test_type;
	private boolean accept_flag = false;
	private boolean target_flag = false;
	private boolean input_flag = false;
	private boolean recogRect_flag = false;
/*-------------------------*/
	
	private String timeInput;
	private boolean timeInput_flag = false;
	private File currentWrittenFile;
	private List<String> onePictureOPerations = new ArrayList<String>();
	
	private boolean fork_flag = false;
	private File forkImage;
	private boolean isCompleted = false;
	
	
	private Intent imageChooseIntent;
	private final int REQUEST_CODE = 1;

	private final int MENU_ITEM_COUNTER = Menu.FIRST;
	public static final String EXTRA_FILE_CHOOSER = "file_chooser";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		try {
			ioOperation.CreateMdr(sd);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		imageView = (ImageView) findViewById(R.id.imageView1);
		currentWrittenFile = WriteXML.createTestFile();
		
		imageChooseIntent = new Intent(this, ImageChooseActivity.class);
		draw();
		gestureTrain = new gestureTrain();
		try {
			gestureTrain.Train(this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void draw() {
		Bitmap bitmap = null;
		if (clickCount == 0) {
			r = this.getResources();
			bitmap = BitmapFactory.decodeResource(r, R.drawable.messi);// 只读,不能直接在bmp上画
		} else {
			try {
				// 读取uri所在的图片
				bitmap = MediaStore.Images.Media.getBitmap(
						this.getContentResolver(), Uri.fromFile(currentImage));
			} catch (Exception e) {
				Log.e("[Android]", e.getMessage());
				Log.e("[Android]", "目录为：" + currentImage.getName());
				e.printStackTrace();
			}

		}

		alterBitmap = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), bitmap.getConfig());
		// System.out.println(bitmap.getWidth() + "");
		canvas = new Canvas(alterBitmap);
		paint = new Paint();
		paint.setColor(Color.GREEN);
		paint.setStrokeWidth(10);
		Matrix matrix = new Matrix();
		canvas.drawBitmap(bitmap, matrix, paint);
		imageView.setImageBitmap(alterBitmap);
		imageView.setOnTouchListener(this);
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		
		int action = event.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			downx = event.getX();
			downy = event.getY();
			graphics.add(new PointF(downx, downy));
			if (stepCount>2) {
				jointGraphics.add(new PointF(upx,upy));
			}
			stepCount++;
			break;
		case MotionEvent.ACTION_MOVE:
			// 路径画板
			upx = event.getX();
			upy = event.getY();
			canvas.drawLine(downx, downy, upx, upy, paint);
			imageView.invalidate();
			downx = upx;
			downy = upy;
			graphics.add(new PointF(upx, upy));// 记录点的坐标
			if (stepCount>2) {
				jointGraphics.add(new PointF(upx,upy));
			}

			break;
		case MotionEvent.ACTION_UP:

			// 直线画板
			upx = event.getX();
			upy = event.getY();
			canvas.drawLine(downx, downy, upx, upy, paint);
			imageView.invalidate();// 刷新
			graphics.add(new PointF(upx, upy));
			isDrawed = true;
			if (stepCount>2) {
				jointGraphics.add(new PointF(upx,upy));
			}
			
			
			
			/*
			 * 无论是否在绘制区域都要先创建文件目录
			 */
			if (clickCount > 0) {
				// 创建文件保存目录
				String dirPath = getDirName(getPath()) + "temp";
				try {
					ioOperation.CreateMdr(dirPath);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (isDrawArea) { // Selection Area
				
				String filePath = getDirName(getPath()) + "temp" + "/"
						+ getImageName(getPath()) + ".txt";
				rect = logic.getExteriorRect(graphics);   //计算出矩形的4个点
				
				ioOperation.recordAreaInfo(filePath, graphics, rect);
/*-------------- Modify by zhchuch -----------------*/
				countDrawArea++;
				
						
				
				if (test_type == TType.ATOMIC) {
					System.out.println("GenerateSingState pos[draw]");
					
					//modelBuilder.generateSingleState("s"+modelBuilder.state_counter, getImageName(imagePath), 1);
					/*使用对话框，让用户输入他自己所要想得到的 期待输出结果(Expected-Output)*/
					final TextView tv = new EditText(this);
					Builder inputDailog = new AlertDialog.Builder(this);
					inputDailog.setTitle("Expected output:");
					inputDailog.setIcon(android.R.drawable.ic_dialog_info);
					inputDailog.setView(tv).setPositiveButton("确定", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							// TODO Auto-generated method stub
							input = tv.getText().toString();
							onePictureOPerations.add(String.valueOf(rect[0])+" ");
							onePictureOPerations.add(String.valueOf(rect[1])+" ");
							onePictureOPerations.add(String.valueOf(rect[2])+" ");
							onePictureOPerations.add(String.valueOf(rect[3])+" ");
							onePictureOPerations.add("Expected output:"+input+"\r\n");
							
						}
					});
					inputDailog.setNegativeButton("取消", null);
					inputDailog.show();
					
					countDrawArea = 0;
					isModelCompleted = true;
				}
				
				if (test_type == TType.JOINT) {
					// 为每一个的 accept 状态，生成 单终止状态
					System.out.println("JOINT [AccpetState Generating...]");
					/*使用对话框，让用户输入他自己所要想得到的 期待输出结果(Expected-Output)*/
					final TextView tv = new EditText(this);
					Builder inputDialog = new AlertDialog.Builder(this);
					inputDialog.setTitle("Expected output:");
					inputDialog.setIcon(android.R.drawable.ic_dialog_info);
					inputDialog.setView(tv);
					inputDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							// TODO Auto-generated method stub
							input = tv.getText().toString();
							onePictureOPerations.add(String.valueOf(rect[0])+" ");
							onePictureOPerations.add(String.valueOf(rect[1])+" ");
							onePictureOPerations.add(String.valueOf(rect[2])+" ");
							onePictureOPerations.add(String.valueOf(rect[3])+" ");
							onePictureOPerations.add("Expected output:"+input+"\r\n");
							System.out.println("Your Input[pos-JOINT]: " +input);
						}
					});
					inputDialog.setNegativeButton("取消", null);
					inputDialog.show();
					accept_flag = true;
				}
				
			} 
			
			
			//没有draw area,只是单独的手势
			else {
				float[] coordinate = logic.calCoordinate(graphics);// 单击时的中心点
				canvas.drawCircle(coordinate[0], coordinate[1], 10, paint);

				PointF center = new PointF(coordinate[0], coordinate[1]);
				operationPoint.add(center);
				int endPointIndex = logic.calEndPoint(graphics);
				endPoint.add(graphics.get(endPointIndex));
				ArrayList<String> recordList = logic.calRecordList(graphics);// 获得用于训练的十五个点的坐标
				if (clickCount > 0) {
					String path = getDirName(getPath()) + "temp" + "/"
							+ getImageName(getPath()) + ".arff";
					ioOperation.recordPoint(path, recordList);
				}
				
				if (isDrawed) {				
					/*--------- After there, imply the identify action function. ---------*/
					// savePicture();
					String operation = "";
					String path = getDirName(getPath()) + "temp" + "/" + getImageName(getPath()) + ".arff";
					double[] result;
					result = gestureTrain.TrainResult(path);
					/*
					 * isDrawed为true并且result等于-1时说明只绘制了区域，并没有单击双击拖动这三种操作
					 */
					if (result[0] != -1) {
						String filePath = getDirName(getPath()) + "temp" + "/" + getImageName(getPath()) + ".txt";
						ArrayList<String> operationList = ioOperation.readOperation(filePath);
						for (int i = 0; i < operationPoint.size(); i++) {

							if (operationPoint.get(i).x != 0 && operationPoint.get(i).y != 0 ) {
								System.out.println("++++++"+result[i]);
								if (result[i] == 0 || result[i] == 1) { // click
									operation = result[i] + "-<" + format(operationPoint.get(i).x) + ","
											+ format(operationPoint.get(i).y) + ">";
									//onePictureOPerations.add(operation);
									
								}
								if (result[i] == 2) { // Drag
									System.out.println("++++++"+result[i]);
									operation = result[i] + "-<" + format(operationPoint.get(i).x) + ","
											+ format(operationPoint.get(i).y) + ">;<" + format(endPoint.get(i).x) + ","
											+ format(endPoint.get(i).y) + ">";
									//onePictureOPerations.add(operation);													
								}

							}

						}
						onePictureOPerations.add(operation);
						
						
					}

					/*--------- Before there, imply the identify action function. ---------*/			
				} else {    
					Toast toast = Toast.makeText(this, "no draw",Toast.LENGTH_SHORT);
					toast.show();
				}
			}

			graphics = new ArrayList<PointF>();

			break;

		default:
			break;
		}

		return true;

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, MENU_ITEM_COUNTER, 0, "choose");
		menu.add(0, MENU_ITEM_COUNTER + 1, 0, "next");
		menu.add(0, MENU_ITEM_COUNTER + 2, 0, "save");
		menu.add(0, MENU_ITEM_COUNTER + 3, 0, "clear");
		menu.add(0, MENU_ITEM_COUNTER + 4, 0, "draw Area");
		menu.add(0, MENU_ITEM_COUNTER + 5, 0, "target");
		//menu.add(0, MENU_ITEM_COUNTER + 6, 0, "enter text");
		menu.add(0, MENU_ITEM_COUNTER+7, 0, "timer");
		menu.add(0,MENU_ITEM_COUNTER + 8, 0, "fork");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_ITEM_COUNTER:	// choose
			clickCount++;
			// 判断sd卡是否存在，并且是否具有读写权限
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				startActivityForResult(imageChooseIntent, REQUEST_CODE);
				
			}
			break;
		case MENU_ITEM_COUNTER + 1:	 // next
			if (clickCount > 0) {
				nextClickOperation();
			}

			break;
		case MENU_ITEM_COUNTER + 2:	 // save
			
			//没有点击target时，只是点击了 draw area, 判断组合情况
			if(stepCount>2){
				
				// 先获取所有的 joint 信息，并放入训练器进行识别 
				ArrayList<String> recordList = logic.calRecordList(jointGraphics);		
				String path = getDirName(getPath()) + "temp"
						+ "/combinationGesture" + getImageName(getPath()) + ".arff";
				ioOperation.recordJointPoint(path, recordList);
				comGestureTrain = new combinationGestureTrain();
				try {
					comGestureTrain.Train(this);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				double[] result = comGestureTrain.TrainResult(path);
				
				String combaOperation = "";
				for (int i = 0; i < result.length; i++) {
					if (result[i] == -1.00) 
						break;
					if (result[i] == 0.00) {
						combaOperation = "FORALL";
						System.out.println("------------"+result[i]);
						onePictureOPerations.add(combaOperation);
					}	
					if (result[i] == 1.00) {
						combaOperation = "REC_FORALL";
						System.out.println("------------"+result[i]);
						onePictureOPerations.add(combaOperation);
					}
					if (result[i] == 2.00) {
						combaOperation = "EXIST";
						System.out.println("------------"+result[i]);
						onePictureOPerations.add(combaOperation);
					}
				}
			}
			
		
			if (isCompleted) {
				currentWrittenFile = WriteXML.createTestFile();
				isCompleted = false;
			}			
			
			WriteXML.writeObject(currentImage.getPath(), currentWrittenFile.getPath());		
			for (int i = 0; i < onePictureOPerations.size(); i++) {
				WriteXML.writeObject(onePictureOPerations.get(i), currentWrittenFile.getPath());
			}
			
			if (target_flag&&!fork_flag) {
				isCompleted = true;
				target_flag = false;
				isDrawArea = false;
				recogRect_flag = false;
			}
			break;
		case MENU_ITEM_COUNTER + 3:	  // clear
			draw();
			break;
		case MENU_ITEM_COUNTER + 4:	  // drawArea
			onePictureOPerations.add("draw area ");
			isDrawArea = true;
			recogRect_flag = true;
			break;
		case MENU_ITEM_COUNTER + 5:	  // target
/*-------------- Modify by zhchuch -----------------*/
			WriteXML.writeObject("targe/r/n", currentWrittenFile.getPath());
			System.out.println("After click Target...");
			target_flag = true;
							
			if (countDrawArea == 0) 
				test_type = TType.ATOMIC;
			else 
				test_type = TType.JOINT;
			
			System.out.println("GenerateSingState pos[target]");
						
			
/*--------------------------------------------------*/
			isDrawArea = false;
			jointGraphics = new ArrayList<PointF>();
			stepCount = 0;
			if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
				startActivityForResult(imageChooseIntent, REQUEST_CODE);
			}
			
			break;
			
		case MENU_ITEM_COUNTER + 6://target
			//getUserInputString();
			/*这里涉及到 多线程，主线程要等待子线程输入。*/
			final TextView tv = new EditText(this);
			Builder expectDialog = new AlertDialog.Builder(this);
			expectDialog.setTitle("Expected output:").setIcon(android.R.drawable.ic_dialog_info);
			expectDialog.setView(tv);
			expectDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					input = tv.getText().toString();
					WriteXML.writeObject("Expected output:"+input+"\r\n", currentWrittenFile.getPath());
					input_flag = true;
					System.out.println("Your Input[pos-menu-selected]: " +input);
				}
			});
			expectDialog.setNegativeButton("取消", null).show();			
			break;
			
		case MENU_ITEM_COUNTER + 7:    //timer
			final TextView timeText = new EditText(this);
			Builder timeDialog = new AlertDialog.Builder(this);
			timeDialog.setTitle("Wait time");
			timeDialog.setIcon(android.R.drawable.ic_dialog_info);
			timeDialog.setView(timeText);
			timeDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					timeInput = timeText.getText().toString();
					WriteXML.writeObject("Delay time:"+timeInput+"\r\n", currentWrittenFile.getPath());
					timeInput_flag = true;
					System.out.println("Your Input[pos-menu-selected]: " +timeInput);
				}
			});
			timeDialog.setNegativeButton("取消", null);
			timeDialog.show();
			break;	
		
		case MENU_ITEM_COUNTER + 8:    //fork
			if (!fork_flag) {
				forkImage = currentImage;
				fork_flag = true;
				Toast.makeText(this, "fork成功", Toast.LENGTH_SHORT).show();
			}else {
				onePictureOPerations.clear();
				fork_flag = false;
				target_flag = false;
				isDrawArea = false;
				int forkIndex = imageFiles.indexOf(forkImage);
				currentImage = imageFiles.get(forkIndex);
				Uri imageUri = Uri.fromFile(currentImage);
	/*------------ modify by zhchuch ----------*/
				imagePath = imageUri.toString();
				//System.out.println("NextImage: Uri="+imageUri.toString()+", ImageName: "+ImageName);
				ParseXML parser = getParserByImagePath(imagePath);
				
	/*----------------------------------------*/
				imageView.setImageURI(imageUri);
				operationPoint = new ArrayList<PointF>();
				draw();
				
			}
			break;		
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * 找到 可处理当前测试界面(ImageName)的 parser
	 */
	private ParseXML getParserByImagePath(String ImagePath)
	{
		//System.out.println("ImagePath = "+ImagePath);
		String[] temp = ImagePath.split("/");
		String app_name = temp[temp.length - 2];
		String ImageName = temp[temp.length - 1];
		String testXMLName = "screen_data/"+app_name+"/" + ImageName.split("\\.")[0] +".uix";
		//System.out.println("testXMLName = "+testXMLName);
		
		ParseXML parser = new ParseXML();
		
		InputStream is_xml = null;
		try {
			is_xml = this.getAssets().open(testXMLName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		parser.parse(is_xml);
		
		return parser;
	}
	
	private String getDirName(String image_Path) {
		// TODO Auto-generated method stub
		String[] temp = image_Path.split("/");
		String path = temp[0];
		for (int i = 1; i < temp.length - 1; i++) {
			path = path + "/" + temp[i];
		}
		return path;
	}

	private String getImageName(String image_Path) {
		// TODO Auto-generated method stub
		String[] temp = image_Path.split("/");
		String name = temp[temp.length - 1];
		// split(".")要转义
		//String[] nameTemp = name.split("\\.");
		return name;//nameTemp[0];
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		onePictureOPerations.clear();
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
			if (data != null) {
				// uri=Uri.parse("content://media/external/images/media/39");
				imagePath = data.getStringExtra(EXTRA_FILE_CHOOSER);				

				currentImage = new File(imagePath);
								
				System.out.println(currentImage.toString());
				Uri imageUri = Uri.fromFile(currentImage);
				imageView.setImageURI(imageUri);
				File dir = new File(getDirName(imagePath));
				files = dir.listFiles();
				for (int i = 0; i < files.length; i++) {
					if (!files[i].isDirectory()) {
						String fileName = files[i].getName();
						String fileType = fileName.substring(fileName
								.indexOf("."));
						if (isImage(fileType)) {
							imageFiles.add(files[i]);
						}
					}

				}
/*--------------- modify by zhchuch ----------*/				
				ParseXML parser = getParserByImagePath(imagePath);
				
				//tempMG = new ModelBuilder(modelBuilder.cur_parser); 
/*-------------------------------------------*/	
				draw();
			} else {
				return;
			}
		}// if

	}// OnActivityResult

	@SuppressLint("DefaultLocale")
	private boolean isImage(String fileType) {
		// TODO Auto-generated method stub
		String temp = fileType.toLowerCase();
		if (temp.equals(".jpg") || temp.equals(".png") || temp.equals("jpeg")) {
			return true;
		}
		return false;
	}

	/*
	 * 根据File获得文件的绝对路径
	 */
	private String getPath() {
		// TODO Auto-generated method stub
		String CanonicalPath = new String();
		try {
			CanonicalPath = currentImage.getCanonicalPath();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return CanonicalPath;
	}

	private void nextClickOperation() {
		
		/*
		 * 点击Next按钮的时候，先对 之前的图片上的操作 生成一个状态
		 */
		if (target_flag&&onePictureOPerations.size()!=0) {
			WriteXML.writeObject(currentImage.getPath(), currentWrittenFile.getPath());		
			for (int i = 0; i < onePictureOPerations.size(); i++) {
				WriteXML.writeObject(onePictureOPerations.get(i), currentWrittenFile.getPath());
			}
			
		}
		
		onePictureOPerations.clear();
		
		int currentIndex = imageFiles.indexOf(currentImage);// 获得所选图片在这个文件夹中的序号
		/*
		 * 点击next时从currentImage之后的一张图片开始，之前的不再出现
		 */
		if (currentIndex == imageFiles.size() - 1) {
			Toast.makeText(this, "已经是最后一张了", Toast.LENGTH_SHORT).show();
			// draw();
		} else {
			currentImage = imageFiles.get(currentIndex + 1);
			Uri imageUri = Uri.fromFile(currentImage);
/*------------ modify by zhchuch ----------*/
			imagePath = imageUri.toString();
			//System.out.println("NextImage: Uri="+imageUri.toString()+", ImageName: "+ImageName);
			ParseXML parser = getParserByImagePath(imagePath);
			
/*----------------------------------------*/
			imageView.setImageURI(imageUri);
			operationPoint = new ArrayList<PointF>();
			draw();
		}

	}

	
	private String format(double input) {
		DecimalFormat decimalFormat = new DecimalFormat(".00");// 将点的坐标统一保留两位小数
		String Coordinate = decimalFormat.format(input);
		return Coordinate;
	}
}
