package Explain;

import java.awt.Component;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.Position;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.Detail;
import javax.xml.soap.Node;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import Explain.DetailAnalysis.ComponentAnalysis;
import Explain.DetailAnalysis.DragAnalysis;
import Explain.DetailAnalysis.EnterText;
import Explain.DetailAnalysis.Oracle;
import Explain.DetailAnalysis.PointAnalysis;
import Node.ActionNode;

public class XmlAnalyse {
	/*
	 * 操作列表
	 */
	private static List<ActionNode> AllPath = new ArrayList<ActionNode>();

	private static ActionNode NowNode = null;

	private static boolean isFirst = true;

	private static boolean OpeFstAndOr = true;

	public List<ActionNode> GetAction(String Xmlway) {
		AllPath.clear();
		// 创建一个documentBuildFactory的对象
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		// DragNode dragNode = new DragNode(Type.pointToArea, Action.DRAG, 0, 0,
		// 0, 0, 0, 0, null);
		// AllPath.add(dragNode);

		try {
			// 创建documentBuilder对象
			DocumentBuilder db = dbf.newDocumentBuilder();
			// 通过document的parse方法加载bookstore.xml文件到当前项目下
			Document document = db.parse(Xmlway);
//			Document document = db
//					.parse("/Users/sam/TestMake-Project/Test-Make/AllXml/paths(7).xml");
			// 获取所有path节点的集合
			NodeList pathlist = document.getElementsByTagName("path");
			// 通过NodeList的getLength方法可以获取bookList的长度
			// System.out.println("一共有" + pathlist.getLength() + "条路");

			for (int i = 0; i < pathlist.getLength(); i++) {
				// System.out.println("=====================下面开始遍历第" + (i + 1) +
				// "条路的内容=====================");
				// 通过Item(index)方法获取一个operation节点，nodelist的索引值从0开始
				// 将book节点进行强制类型转换，转换为Element类型
				Element path = (Element) pathlist.item(i); // 第i条路

				// 解析path节点(即当前路)的子节点
				NodeList OperationList = path.getChildNodes();
				// 通过NodeList的getLength方法可以获取bookList的长度
				// System.out.println("当前一共有" + OperationList.getLength() +
				// "个操作");
				int ope = 0;
				for (int j = 0; j < OperationList.getLength(); j++) {
					// 区分出text类型的node以及element类型的node 节点
					if (OperationList.item(j).getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
						// System.out.println("================下面开始遍历第" + (ope +
						// 1) + "个操作的内容================");
						ope++;
						// 解析operation节点
						Element operation = (Element) OperationList.item(j);
						// 当前标签名字
						String Opename = operation.getNodeName();
						// 通过type，Action取得属性
						String type = operation.getAttribute("type");
						String action = operation.getAttribute("action");
						// System.out.println(Opename);
						// System.out.println("第" + (ope) + "个操作的类型为：" + type);
						// System.out.println("第" + ope + "个操作的动作为：" + action);

						NodeList childNodes = operation.getChildNodes();
						// System.out.println("一共有" + childNodes.getLength() +
						// "个子节点");

						if (Opename.equalsIgnoreCase("oracle")) {
							Oracle oracle = new Oracle();
							oracle.solveOracle(type, action, childNodes);
//							this.solveOracle(type, action, childNodes);

						} else {// 路径中的Operation
							/*
							 * 针对不同的type属性进行不同的解析过程
							 */
							if (type.equalsIgnoreCase("Component")) {
								ComponentAnalysis componentAnalysis = new ComponentAnalysis();
								componentAnalysis.solveSingleComponent(childNodes, action);
//								this.solveSingleComponent(childNodes, action);

							} else if (type.contains("MultiComponent")) {
								ComponentAnalysis componentAnalysis = new ComponentAnalysis();
								componentAnalysis.solveMultiComponent(childNodes, action);
//								this.solveMultiComponent(childNodes, action);

							} else if (type.equalsIgnoreCase("SinglePoint")) {
								PointAnalysis pointAnalysis = new PointAnalysis();
								pointAnalysis.solveSinglePoint(childNodes, action);
//								this.solveSinglePoint(childNodes, action);

							} else if (type.equalsIgnoreCase("DoublePoint")) {
								PointAnalysis pointAnalysis = new PointAnalysis();
								pointAnalysis.solveDoublePoint(childNodes, action);;
//								this.solveDoublePoint(childNodes, action);

							} else if (type.equalsIgnoreCase("DragRange")) {
								DragAnalysis dragAnalysis = new DragAnalysis();
								dragAnalysis.solveDragRange(childNodes, action);
//								this.solveDragRange(childNodes, action);
							}else if (type.equalsIgnoreCase("TextComponent")) {
								EnterText enterText = new EnterText();
								enterText.solveTextEnter(childNodes, action);
//								this.solveTextEnter(childNodes,action);
							}

						} // element型子节点（operation）
					}
				} // 不同操作
				NowNode = null;

				isFirst = true;

			} // 不同路径
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return AllPath;
	}

	// public String ComponentClick(NodeList DetailList){
	// for(int i=0;i<DetailList.getLength();i++){
	// if (DetailList.item(i).getNodeType() == Node.ELEMENT_NODE) {
	// String NodeValue = DetailList.item(i).
	// }
	//
	// }
	//
	// return null;
	// }
	public void solveTextEnter(NodeList childNodes, String action){
		String type = null;
		String ComName = null;
		String EnterText = null;
		for(int i=0;i<childNodes.getLength();i++){
			if (childNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
//				System.out.println("当前结点为"+childNodes.item(i).getNodeName());
				NodeList textList = childNodes.item(i).getChildNodes();
				for(int j=0;j<textList.getLength();j++){
					if (textList.item(j).getNodeType() == Node.ELEMENT_NODE) {
						String NowName = textList.item(j).getNodeName();
//						System.out.println("当前结点为"+NowName);
						if (NowName.equalsIgnoreCase("index")) {
							ComName = textList.item(j).getTextContent();
						}else if (NowName.equalsIgnoreCase("componentType")) {
							type = textList.item(j).getTextContent();
						}else if (NowName.equalsIgnoreCase("input")) {
							EnterText = textList.item(j).getTextContent();
						}
					}
				}
			}
		}
		if (type.equalsIgnoreCase("EditText")) {
			type = "Text";
		}
		System.out.println("当前操作为："+"类型"+type+"组件名字"+ComName+"输入文本"+EnterText);
		ActionNode anActionNode = null;
		anActionNode = new ActionNode(null, Action.TEXT, type, ComName, EnterText);

		this.setNode(anActionNode);
	}
	
	
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
		this.setNode(anActionNode);
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
		this.setNode(anActionNode);
	}

	public void solveSingleComponent(NodeList childNodes, String action) {
		// NodeList detailOpe =
		// operation.getChildNodes();
		String type2 = null;
		String Text = null;
		for (int h = 0; h < childNodes.getLength(); h++) {
			if (childNodes.item(h).getNodeType() == Node.ELEMENT_NODE) {
				NodeList ComponentList = childNodes.item(h).getChildNodes();

				for (int k = 0; k < ComponentList.getLength(); k++) {
					if (ComponentList.item(k).getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
						// System.out.println("第"+(buttonNum+1)+"个操作的组件类型："+ComponentList.item(k).getNodeName());
						// System.out.println("第"+(buttonNum+1)+"个操作的组件的text内容："+ComponentList.item(k).getTextContent());
						if (ComponentList.item(k).getNodeName().equalsIgnoreCase("componentType")) {
							type2 = ComponentList.item(k).getTextContent();
							// System.out.println(type2);
						}
						if (ComponentList.item(k).getNodeName().equalsIgnoreCase("index")) {
							Text = ComponentList.item(k).getTextContent();
							// System.out.println(Text);
						}
					}
				}
			}
		}
		if (type2.equalsIgnoreCase("TextView")) {
			type2 = "Text";
		}
		ActionNode anActionNode = null;
		if (action.equalsIgnoreCase("click")) {
			anActionNode = new ActionNode(null, Action.CLICK, type2, Text, null);
		} else if (action.equalsIgnoreCase("LClick")) {
			anActionNode = new ActionNode(null, Action.LCLICK, type2, Text, null);
		}

		this.setNode(anActionNode);
	}

	// int buttonNum = 0;
	public void solveMultiComponent(NodeList childNodes, String action) {
		for (int h = 0; h < childNodes.getLength(); h++) {
			System.out.println("11" + childNodes.getLength());
			if (childNodes.item(h).getNodeType() == Node.ELEMENT_NODE) {
				NodeList MultiComponentList = childNodes.item(h).getChildNodes();
				System.out.println("22" + MultiComponentList.getLength());
				for (int k = 0; k < MultiComponentList.getLength(); k++) {
					String type2 = null;
					String Text = null;
					if (MultiComponentList.item(k).getNodeType() == Node.ELEMENT_NODE) {
						// buttonNum++;
						NodeList ComponentList = MultiComponentList.item(k).getChildNodes();

						for (int m = 0; m < ComponentList.getLength(); m++) {
							if (ComponentList.item(m).getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
								// System.out.println("第"+(buttonNum)+"个操作的组件类型："+ComponentList.item(m).getNodeName());
								// System.out.println("第"+(buttonNum)+"个操作的组件的text内容："+ComponentList.item(m).getTextContent());
								if (ComponentList.item(m).getNodeName().equalsIgnoreCase("componentType")) {
									type2 = ComponentList.item(m).getTextContent();
									// System.out.println(type2);
								}
								if (ComponentList.item(m).getNodeName().equalsIgnoreCase("index")) {
									Text = ComponentList.item(m).getTextContent();
									// System.out.println(Text);
								}
							}
						}
						if(type2.equalsIgnoreCase("TextView")){
							type2 = "Text";
						}
						ActionNode anActionNode = null;
						if (action.equalsIgnoreCase("click")) {
							anActionNode = new ActionNode(null, Action.CLICK, type2, Text, null);
						} else if (action.equalsIgnoreCase("longClick")) {
							anActionNode = new ActionNode(null, Action.LCLICK, type2, Text, null);
						}

						this.setNode(anActionNode);
					}
				}
			}
		}
	}

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
				this.setNode(anActionNode);
			}
		}

	}

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
			this.setNode(anActionNode);
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
			ActionNode anActionNode = new ActionNode(null, Action.ORACLE, "IMAGE",
					theRGB, position);
			this.setNode(anActionNode);
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
							this.setNode(anActionNode);
							OpeFstAndOr = false;
						}else {
//							System.out.println("与或非操作"+";当前操作为："+Action.ToString(OpeName));
//							System.out.println("与或非操作之"+thePos);
							ActionNode anActionNode = new ActionNode(null, Action.ToString(OpeName), theType,
									theComponent, thePos);
							this.setNode(anActionNode);
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
							ActionNode anActionNode = new ActionNode(null, Action.ORACLE, "IMAGE",
									theRGB, position);
							this.setNode(anActionNode);
							OpeFstAndOr = false;
						}else {
//							System.out.println("与或非操作"+";当前操作为："+Action.ToString(OpeName));
//							System.out.println("与或非操作之"+thePos);
							ActionNode anActionNode = new ActionNode(null, Action.ToString(OpeName), "IMAGE",
									theRGB, position);
							this.setNode(anActionNode);
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

	public static void setNode(ActionNode actionNode) {
		// 路径链表 的第一个
		if (isFirst) {
			isFirst = false;
			AllPath.add(actionNode);
			NowNode = actionNode;
		} else {// 不是第一个，把上一个node连接到当前的node
			if (actionNode == null) {
				System.out.println("操死你王栋");
			}
			NowNode.setNext(actionNode);
			NowNode = actionNode;
		}
	}

//	public static void main(String[] args) {
//		XmlAnalyse analyse = new XmlAnalyse();
//		analyse.GetAction(null);
//	}
}
