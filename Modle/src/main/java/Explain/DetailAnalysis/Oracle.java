package Explain.DetailAnalysis;

import javax.xml.soap.Node;

import org.w3c.dom.NodeList;

import Explain.Action;
import Explain.XmlAnalyse;
import Node.ActionNode;

public class Oracle {

	private boolean OpeFstAndOr = true;
	
	public void solveOracle(String type, String action, NodeList childNodes) {
		// System.out.println("这是一个Oracle哦！！");
		if (type.equalsIgnoreCase("SingleComponentResult")) {
			String theType = null;
			String theComponent = null;
			String thePos = null;
			for (int l = 0; l < childNodes.getLength(); l++) {
				if (childNodes.item(l).getNodeType() == Node.ELEMENT_NODE) {
					// System.out.println(childNodes.item(l).getNodeName());
					NodeList singleElement = childNodes.item(l).getChildNodes();
					for (int m = 0; m < singleElement.getLength(); m++) {
						if (singleElement.item(m).getNodeType() == Node.ELEMENT_NODE) {
							String NowString = singleElement.item(m).getNodeName();
							// System.out.println("现在的子节点名字："
							// + NowString);
							if (NowString.equalsIgnoreCase("index")) {
								theType = singleElement.item(m).getTextContent();
								// System.out.println(theType);
							} else if (NowString.equalsIgnoreCase("resultType")) {
								theType += "|" + singleElement.item(m).getTextContent();
								// System.out.println(theType);
							} else if (NowString.equalsIgnoreCase("componentType")) {
								theComponent = singleElement.item(m).getTextContent();
								// System.out.println(theComponent);
							} else if (NowString.equalsIgnoreCase("expect")) {
								thePos = singleElement.item(m).getTextContent();
								// System.out.println(thePos);
							}
						}
					}
				}
			}
			ActionNode anActionNode = new ActionNode(null, Action.ORACLE, theType, theComponent, thePos);
			XmlAnalyse.setNode(anActionNode);
		}else if (type.equalsIgnoreCase("PixelsResult")) {
			String theRGB = null;
			String position = null;
			for(int l=0;l<childNodes.getLength();l++){
				if (childNodes.item(l).getNodeType() == Node.ELEMENT_NODE) {
					NodeList singleElement = childNodes.item(l).getChildNodes();
					for(int m=0;m<singleElement.getLength();m++){
						if (singleElement.item(m).getNodeType() == Node.ELEMENT_NODE) {
							String NodeString = singleElement.item(m).getNodeName();
							if (NodeString.equalsIgnoreCase("color")) {
								theRGB = singleElement.item(m).getTextContent();
							}else if (NodeString.equalsIgnoreCase("Xray")) {
								position = singleElement.item(m).getTextContent()+"|";
							}else if (NodeString.equalsIgnoreCase("Yray")) {
								position += singleElement.item(m).getTextContent();
							}
						}
					}
				}
			}
			System.out.println("RGB为"+theRGB);
			System.out.println("坐标为"+position);
			ActionNode anActionNode = new ActionNode(null, Action.ORACLE, "0|IMAGE",
					theRGB, position);
			XmlAnalyse.setNode(anActionNode);
		} else if (type.equalsIgnoreCase("OrOperation")) {
//			System.out.println("--------------开始遍历与或非操作--------------");
			for (int i = 0; i < childNodes.getLength(); i++) {
				if (childNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {// or的根节点
					String opeName = childNodes.item(i).getNodeName();
					NodeList childList = childNodes.item(i).getChildNodes(); // or的全部子节点
					this.solveAndOr(opeName, childList);

				}
			}
			OpeFstAndOr = true;
//			System.out.println("--------------结束遍历与或非操作--------------");
		}else if (type.equalsIgnoreCase("AndOperation")) {
//			System.out.println("--------------开始遍历与或非操作--------------");
			for (int i = 0; i < childNodes.getLength(); i++) {
				if (childNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {// and的根节点
					String opeName = childNodes.item(i).getNodeName();
					NodeList childList = childNodes.item(i).getChildNodes(); // and的全部子节点
					this.solveAndOr(opeName, childList);

				}
			}
			OpeFstAndOr = true;
//			System.out.println("--------------结束遍历与或非操作--------------");
		}
	}

	public void solveAndOr(String OpeName, NodeList childList) {
		if (OpeName.equalsIgnoreCase("singleComponent")) {
			
		} else {
			for (int i = 0; i < childList.getLength(); i++) {
				if (childList.item(i).getNodeType() == Node.ELEMENT_NODE) {
					String Operation = childList.item(i).getNodeName();
					if (Operation.equalsIgnoreCase("singleComponent")) {
						String theType = null;
						String theComponent = null;
						String thePos = null;

						NodeList singleElement = childList.item(i).getChildNodes();
						for (int m = 0; m < singleElement.getLength(); m++) {
							if (singleElement.item(m).getNodeType() == Node.ELEMENT_NODE) {
								String NowString = singleElement.item(m).getNodeName();
								// System.out.println("现在的子节点名字："
								// + NowString);
								if (NowString.equalsIgnoreCase("index")) {
									theType = singleElement.item(m).getTextContent();
									// System.out.println(theType);
								} else if (NowString.equalsIgnoreCase("resultType")) {
									theType += "|" + singleElement.item(m).getTextContent();
									// System.out.println(theType);
								} else if (NowString.equalsIgnoreCase("componentType")) {
									theComponent = singleElement.item(m).getTextContent();
									// System.out.println(theComponent);
								} else if (NowString.equalsIgnoreCase("expect")) {
									thePos = singleElement.item(m).getTextContent();
									// System.out.println(thePos);
								}
							}
						}
						// 创建Action
						if (OpeFstAndOr) {
//							System.out.println("与或非操作之"+thePos);
							ActionNode anActionNode = new ActionNode(null, Action.ORACLE, theType,
									theComponent, thePos);
							XmlAnalyse.setNode(anActionNode);
							OpeFstAndOr = false;
						}else {
//							System.out.println("与或非操作"+";当前操作为："+Action.ToString(OpeName));
//							System.out.println("与或非操作之"+thePos);
							ActionNode anActionNode = new ActionNode(null, Action.ToString(OpeName), theType,
									theComponent, thePos);
							XmlAnalyse.setNode(anActionNode);
						}
					}else if (Operation.equalsIgnoreCase("pixelsResult")) {
						String theRGB = null;
						String position = null;
						NodeList pixelsElement = childList.item(i).getChildNodes();
						for(int m=0;m<pixelsElement.getLength();m++){
							if (pixelsElement.item(m).getNodeType() == Node.ELEMENT_NODE) {
								String NodeString = pixelsElement.item(m).getNodeName();
								System.out.println("当前节点名字为："+NodeString);
								if (NodeString.equalsIgnoreCase("r")){
									theRGB = pixelsElement.item(m).getTextContent()+"|";
								}else if (NodeString.equalsIgnoreCase("g")) {
									theRGB += pixelsElement.item(m).getTextContent()+"|";
								}else if (NodeString.equalsIgnoreCase("b")) {
									theRGB +=pixelsElement.item(m).getTextContent();
								}else if (NodeString.equalsIgnoreCase("color")) {
									theRGB = pixelsElement.item(m).getTextContent();
								}else if (NodeString.equalsIgnoreCase("Xray")) {
									position = pixelsElement.item(m).getTextContent()+"|";
								}else if (NodeString.equalsIgnoreCase("Yray")) {
									position += pixelsElement.item(m).getTextContent();
								}
							}
						}
						// 创建Action
						if (OpeFstAndOr) {
							System.out.println("RGB为"+theRGB);
							System.out.println("坐标为"+position);
							ActionNode anActionNode = new ActionNode(null, Action.ORACLE, "0|IMAGE",
									theRGB, position);
							XmlAnalyse.setNode(anActionNode);
							OpeFstAndOr = false;
						}else {
//							System.out.println("与或非操作"+";当前操作为："+Action.ToString(OpeName));
//							System.out.println("与或非操作之"+thePos);
							ActionNode anActionNode = new ActionNode(null, Action.ToString(OpeName), "0|IMAGE",
									theRGB, position);
							XmlAnalyse.setNode(anActionNode);
						}
						
					} 
					else {
						NodeList nextList = childList.item(i).getChildNodes(); // 下一个与或非的全部子节点
						this.solveAndOr(Operation, nextList);
					}
				}
			}
		}
	}
}
