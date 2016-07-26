package Explain.DetailAnalysis;

import javax.xml.soap.Node;

import org.w3c.dom.NodeList;

import Explain.Action;
import Explain.XmlAnalyse;
import Node.ActionNode;

public class PointAnalysis {

	public void solveSinglePoint(NodeList childNodes, String action) {
		String position = null;
		for (int k = 0; k < childNodes.getLength(); k++) {
			if (childNodes.item(k).getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
				// System.out.println(childNodes.item(k).getNodeName());
				NodeList singleNodes = childNodes.item(k).getChildNodes();
				for (int l = 0; l < singleNodes.getLength(); l++) {
					if (singleNodes.item(l).getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
						String pos = singleNodes.item(l).getTextContent();
						// System.out.println("点坐标为：" + pos);
						if (position == null) {
							position = pos;
							position += "|";
						} else {
							position += pos;
						}
					}
				}
				// System.out.println(position);
			}
		}
		ActionNode anActionNode = null;
		if (action.equalsIgnoreCase("click")) {
			anActionNode = new ActionNode(null, Action.CLICK, null, null, position);
		} else if (action.equalsIgnoreCase("LClick")) {
			anActionNode = new ActionNode(null, Action.LCLICK, null, null, position);
		}
		XmlAnalyse.setNode(anActionNode);
	}

	public void solveDoublePoint(NodeList childNodes, String action) {
		// 位置
		String position = null;
		for (int k = 0; k < childNodes.getLength(); k++) {
			if (childNodes.item(k).getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {// doubleNode节点
				// System.out.println(childNodes.item(k).getNodeName());
				NodeList doubleNodes = childNodes.item(k).getChildNodes();
				for (int l = 0; l < doubleNodes.getLength(); l++) {
					// boolean IfFirstNode = true;
					if (doubleNodes.item(l).getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {// singleNode节点
						// System.out.println(doubleNodes.item(l).getNodeName());
						NodeList singleNodes = doubleNodes.item(l).getChildNodes();
						String tempPosition = null;
						for (int m = 0; m < singleNodes.getLength(); m++) {
							if (singleNodes.item(m).getNodeType() == Node.ELEMENT_NODE) {// pointX/Y节点
								String pos = singleNodes.item(m).getTextContent();
								// System.out.println("点坐标为：" + pos);
								if (tempPosition == null) {
									tempPosition = pos;
									tempPosition += "|";
								} else {
									tempPosition += pos;
									// tempPosition +=
									// "#";
								}
							}
						}
						if (position == null) {
							position = tempPosition;
							position += "#";
						} else {
							position += tempPosition;
						}
						tempPosition = null;
					}
				}
			}
		}
		// System.out.println("doublePoint的结果为：" + position);
		ActionNode anActionNode = new ActionNode(null, Action.DRAG, null, null, position);
		XmlAnalyse.setNode(anActionNode);
	}
}
