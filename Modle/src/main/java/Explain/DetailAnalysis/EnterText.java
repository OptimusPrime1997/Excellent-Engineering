package Explain.DetailAnalysis;

import javax.xml.soap.Node;

import org.w3c.dom.NodeList;

import Explain.Action;
import Explain.XmlAnalyse;
import Node.ActionNode;

public class EnterText {

	public void solveTextEnter(NodeList childNodes, String action){
		String type = null;
		String ComName = null;
		String EnterText = null;
		String message = null;
		for(int i=0;i<childNodes.getLength();i++){
			if (childNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
//				System.out.println("当前结点为"+childNodes.item(i).getNodeName());
				NodeList textList = childNodes.item(i).getChildNodes();
				for(int j=0;j<textList.getLength();j++){
					if (textList.item(j).getNodeType() == Node.ELEMENT_NODE) {
						String NowName = textList.item(j).getNodeName();
//						System.out.println("当前结点为"+NowName);
						if (NowName.equalsIgnoreCase("index")) {
//							message = textList.item(j).getTextContent();
//							ResolveMessage resolveMessage = new ResolveMessage();
//							ComName = resolveMessage.ResolveMsg(message, "index");
//							ComName = textList.item(j).getTextContent();
						}else if (NowName.equalsIgnoreCase("componentType")) {
							type = textList.item(j).getTextContent();
						}else if (NowName.equalsIgnoreCase("input")) {
							EnterText = textList.item(j).getTextContent();
						}else if (NowName.equalsIgnoreCase("message")) {
							message = textList.item(j).getTextContent();
							ResolveMessage resolveMessage = new ResolveMessage();
							ComName = resolveMessage.ResolveMsg(message, "index");
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

		XmlAnalyse.setNode(anActionNode);
	}
}
