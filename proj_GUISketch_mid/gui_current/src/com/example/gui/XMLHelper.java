package com.example.gui;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.xmlpull.v1.XmlSerializer;

import android.util.Xml;

public class XMLHelper {
	public static OutputStream getOutputStream(String path){
		File f=new File(path);
		if(!f.exists()){
			try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		OutputStream output=null;
		try {
			output=new FileOutputStream(new File(path));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return output;
	}
	public static XmlSerializer getSerializer(OutputStream output){
		
		XmlSerializer serializer=Xml.newSerializer();
		try {
			serializer.setOutput(output, "UTF-8");
			serializer.startDocument("UTF-8", true);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return serializer;
	}
	
	public static void writeTag(XmlSerializer ser,String tag,String text){
		try {
			ser.startTag(null, tag);
			ser.text(text);
			ser.endTag(null, tag);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void endWrite(XmlSerializer ser,OutputStream output){
		try {
			ser.endDocument();
			output.flush();
			output.close();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
} 
