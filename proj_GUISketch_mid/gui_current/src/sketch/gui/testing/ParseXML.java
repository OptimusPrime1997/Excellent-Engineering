package sketch.gui.testing;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.graphics.PointF;
import android.util.Log;

/**
 * Parsing a XML file to an Android-NodeList.
 * 
 * @author zhchuch
 */

public class ParseXML {
	public static String CLASS_PRE = "android.widget.";
	public List<AndroidNode> node_list;

	public ParseXML(InputStream input) {
		node_list = new ArrayList<AndroidNode>();
		parse(input);
		print(node_list);
	}

	public void parse(InputStream xmlFile) {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(xmlFile);
			String id, tt, wn, pn, bd;

			doc.getDocumentElement().normalize();
			Log.w("TAG-n1", doc.getDocumentElement().getNodeName());

			NodeList nodes = doc.getElementsByTagName("node");
			Log.w("TAG-n2", nodes.getLength() + "");

			for (int i = 0; i < nodes.getLength(); i++) {
				Element node = (Element) nodes.item(i);
				id = node.getAttribute("index");
				tt = node.getAttribute("text");
				wn = node.getAttribute("class");
				pn = node.getAttribute("package");
				bd = node.getAttribute("bounds");

				if (wn.startsWith(CLASS_PRE) && !wn.contains("Layout")) {
					AndroidNode mid = new AndroidNode(id, tt, wn, pn, bd);
					node_list.add(mid);
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("A Exception occurred! error = " + e.toString());
			e.printStackTrace();
		}
	}

	public AndroidNode findWidgetByLocation(double x, double y) {
		AndroidNode mid_and;
		for (int i = 0; i < node_list.size(); i++) {
			mid_and = node_list.get(i);
			// mid_and.print();
			if (mid_and.isLocated(x, y)) {
				Log.w("TAG-n3", "find the widget:" + mid_and.getPrintString() + "");
				return mid_and;
			}
		}

		return null;
	}

	public List<AndroidNode> findWidgetByRect(PointF[] rect) {
		List<AndroidNode> wid_list = new ArrayList<AndroidNode>();

		// 设定判断 控件是否在Rect区域内的 比例 (2016.04.27)
		double ratio = 0.5;
		float x_cup, x1_cup, y_cup, y1_cup;

		for (AndroidNode mid : node_list) {
			/*
			 * if (mid.x1 >= rect[0].x && mid.x2 <= rect[3].x && mid.y1 >=
			 * rect[0].y && mid.y2 <= rect[3].y) wid_list.add(mid);
			 */

			x_cup = Math.max(mid.x1, rect[0].x);
			x1_cup = Math.min(mid.x2, rect[3].x);
			y_cup = Math.max(mid.y1, rect[0].y);
			y1_cup = Math.min(mid.y2, rect[3].y);
			// 计算两者重合部分的面积，面积大于比例则算选中了控件
			if ((calRectArea(x_cup, y_cup, x1_cup, y1_cup) / calRectArea(mid.x1, mid.y1, mid.x2, mid.y2)) >= ratio) {
				wid_list.add(mid);
			}
		}
		print(wid_list);

		return wid_list;
	}

	public float calRectArea(float x1, float y1, float x2, float y2) {
		return (x2 - x1) * (y2 - y1);
	}

	public void print(List<AndroidNode> nodes) {
		if (nodes != null) {
			Log.w("TAG-P1", "++++++++ Node List (size = " + nodes.size() + ") +++++++++");
			for (int i = 0; i < nodes.size(); i++) {
				Log.w("TAG-P2", nodes.get(i).getPrintString());
			}
		}
	}

	/*
	 * public static void main(String args[]) {
	 * parse("screen_data/simple_1.uix"); }
	 */
}
