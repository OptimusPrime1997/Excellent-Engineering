package Explain.DetailAnalysis;

import javax.xml.soap.Node;

import org.w3c.dom.NodeList;

import Explain.Action;
import Explain.XmlAnalyse;
import Node.ActionNode;

public class ComponentAnalysis {

	public void solveSingleComponent(NodeList childNodes, String action) {
		// NodeList detailOpe =
		// operation.getChildNodes();
		String type2 = null;
		String Text = null;
		String message = null;
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
						}else if (ComponentList.item(k).getNodeName().equalsIgnoreCase("index")) {
							Text = ComponentList.item(k).getTextContent();
							// System.out.println(Text);
						}else if (ComponentList.item(k).getNodeName().equalsIgnoreCase("message")) {
							message = ComponentList.item(k).getTextContent();
							if (type2 .equalsIgnoreCase("ImageButton")) {
								ResolveMessage resolveMessage = new ResolveMessage();
								Text = resolveMessage.ResolveMsg(message, "index");
							}
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

		XmlAnalyse.setNode(anActionNode);
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
					String message = null;
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
								}else if (ComponentList.item(m).getNodeName().equalsIgnoreCase("index")) {
									Text = ComponentList.item(m).getTextContent();
									// System.out.println(Text);
								}else if (ComponentList.item(k).getNodeName().equalsIgnoreCase("message")) {
									message = ComponentList.item(k).getTextContent();
									if (type2 .equalsIgnoreCase("ImageButton")) {
										ResolveMessage resolveMessage = new ResolveMessage();
										Text = resolveMessage.ResolveMsg(message, "index");
									}
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

						XmlAnalyse.setNode(anActionNode);
					}
				}
			}
		}
	}
}
