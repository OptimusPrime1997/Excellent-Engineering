package com.example.gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.xmlpull.v1.XmlSerializer;

import android.os.Environment;
import android.util.Xml;

public class XMLHelper {
	public static OutputStream getOutputStream(){
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		String fileName = simpleDateFormat.format(date);
		File skRoot = Environment.getExternalStorageDirectory();
		File totalDir = new File(skRoot.getPath() + "/transdata", fileName + ".xml");
		if (!totalDir.exists()) {
			totalDir.getParentFile().mkdirs();
			try {
				totalDir.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		OutputStream output = null;
		try {
			output = new FileOutputStream(totalDir);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return output;
	}

	public static XmlSerializer getSerializer(OutputStream output) {

		XmlSerializer serializer = Xml.newSerializer();
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

	public static void writeTagText(XmlSerializer ser, String tag, String text) {
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

	public static void writeTagAttr(XmlSerializer ser, String tag, String text, Map<String, String> map) {
		try {
			ser.startTag(null, tag);
			Iterator iter = map.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				String key = (String) entry.getKey();
				String val = (String) entry.getValue();
				ser.attribute(null, key, val);
			}
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

	public static void endWrite(XmlSerializer ser, OutputStream output) {
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
