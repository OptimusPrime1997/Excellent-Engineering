package Explain.DetailAnalysis;

import javax.xml.soap.Node;

import org.w3c.dom.NodeList;

import Explain.Action;
import Explain.XmlAnalyse;
import Node.ActionNode;

public class DragAnalysis {

	public void solveDragRange(NodeList childNodes, String action) {
		for (int n = 0; n < childNodes.getLength(); n++) {
			if (childNodes.item(n).getNodeType() == Node.ELEMENT_NODE) {
				NodeList DragRange = childNodes.item(n).getChildNodes();
				// 位置
				String position1 = null;
				String position2 = null;
				for (int k = 0; k < DragRange.getLength(); k++) {
					if (DragRange.item(k).getNodeType() == Node.ELEMENT_NODE) {// singlePoint和doublePoint子节点
						String ChildNodeName = DragRange.item(k).getNodeName();
						// System.out.println("当前子节点名字为（第一层子节点）：" +
						// ChildNodeName);

						if (ChildNodeName.equalsIgnoreCase("singlePoint")) {// singlePoint子节点
							// System.out.println("----当前处理拖动的singlePoint----");
							NodeList singleNode = DragRange.item(k).getChildNodes();
							for (int l = 0; l < singleNode.getLength(); l++) {
								if (singleNode.item(l).getNodeType() == Node.ELEMENT_NODE) {
									String pos = singleNode.item(l).getTextContent();
									// System.out.println("点坐标为：" + pos);
									if (position1 == null) {
										position1 = pos;
										position1 += "|";
									} else {
										position1 += pos;
									}
								}
							}
							// System.out.println("SinglePoint的值为：" +
							// position1);
						} else { // doublePoint子节点
							// System.out.println("----当前处理拖动的area----");
							NodeList singleNodes = DragRange.item(k).getChildNodes();
							for (int l = 0; l < singleNodes.getLength(); l++) { // 遍历doublePoint子节点
								if (singleNodes.item(l).getNodeType() == Node.ELEMENT_NODE) {// doublePoint子节点中的element(singlePoint)
									// System.out.println(
									// "area中的第一层子节点" +
									// singleNodes.item(l).getNodeName());
									NodeList pointNodes = singleNodes.item(l).getChildNodes();
									String tempPosition = null;
									for (int m = 0; m < pointNodes.getLength(); m++) {
										if (pointNodes.item(m).getNodeType() == Node.ELEMENT_NODE) {// pointX/Y节点
											// System.out.println(
											// "area中的第二层子节点" +
											// pointNodes.item(m).getNodeName());
											String pos = pointNodes.item(m).getTextContent();
											// System.out.println("点坐标为：" +
											// pos);
											if (tempPosition == null) {
												tempPosition = pos;
												tempPosition += "|";
											} else {
												tempPosition += pos;
												// tempPosition
												// += "#";
											}
										}
									}
									if (position2 == null) {
										position2 = tempPosition;
										position2 += "#";
									} else {
										position2 += tempPosition;
									}
									tempPosition = null;
								}
							}
						}
					}
				}
				position1 = position1 + "#" + position2;
				System.out.println("拖动操作的最后坐标为：" + position1);
				ActionNode anActionNode = new ActionNode(null, Action.DRAG, null, null, position1);
				XmlAnalyse.setNode(anActionNode);
			}
		}

	}
}
