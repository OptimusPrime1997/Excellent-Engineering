package com.example.gui;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.annotation.SuppressLint;
import android.os.Environment;


@SuppressLint("SimpleDateFormat")
public class WriteXML {

	
	public static File createTestFile(){
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String fileName = simpleDateFormat.format(date);
		File skRoot = Environment.getExternalStorageDirectory();
		File totalDir = new File(skRoot.getPath()+"/transdata",fileName+".txt");
		if (!totalDir.exists()) {		
			totalDir.getParentFile().mkdirs();
			try {
				totalDir.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return totalDir;		 
	}
	
	
	public static void writeObject(String content,String fileName){
		FileWriter fw;		
		try {
			fw = new  FileWriter(fileName , true );
			fw.write(content);       
		    fw.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }
    
	
	
}
