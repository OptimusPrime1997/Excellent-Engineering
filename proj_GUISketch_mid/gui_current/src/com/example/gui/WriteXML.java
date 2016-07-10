package com.example.gui;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import android.os.Environment;


public class WriteXML {

	public static void writeObject(String content,String fileName){
		File skRoot = Environment.getExternalStorageDirectory();
		FileWriter fw;
		File totalDir = new File(skRoot.getPath()+"/transdata");
		if (!totalDir.exists()) {
			totalDir.mkdir();
		}
		try {
			fw = new  FileWriter( skRoot.toString() +  "/transdata/"+ fileName ,  true );
			fw.write(content);  
		    fw.write("\r\n" );  //写入换行     
		    fw.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }
    
	
	
}
