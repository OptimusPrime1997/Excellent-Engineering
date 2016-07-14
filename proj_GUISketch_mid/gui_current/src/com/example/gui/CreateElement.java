package com.example.gui;

import org.dom4j.Element;

public class CreateElement {
	
	
	/**
	 * 默认创建typeCode为1的state
	 * @param rootElement
	 * @param path
	 * @return
	 */
	public static Element createState(Element rootElement,String path){
		
		Element stateElement = BuildDocument.addElement(rootElement, "state");
		String id = path.substring(path.length()-5, path.length()-4);
		BuildDocument.addAttribute(stateElement, "typeCode", "1");
		
		Element idElement = BuildDocument.addElement(stateElement, "stateId");
		BuildDocument.addText(idElement, id);
			
		Element pathElement = BuildDocument.addElement(stateElement, "fileName");
		BuildDocument.addText(pathElement, path);
		
		return stateElement;	
	}
	
	/**
	 * 生成operation的标签
	 * @param rootElement
	 * @param operationId
	 * @return
	 */
	public static Element createOPeration(Element rootElement, int operationId){		
		Element operationElement = BuildDocument.addElement(rootElement, "Operation");
		BuildDocument.addAttribute(operationElement, "id", operationId+"");
		return operationElement;
	}
	
	
	public static void addCombaInfo(Element element, String type, String[] points,String[] startPoints){
		
		BuildDocument.addAttribute(element, "type", type);							
		if (type.equals("area")) {
			Element pointsElement = BuildDocument.addElement(element, "doublePoint");
			
			Element point1Element = BuildDocument.addElement(pointsElement, "singlePoint");
			Element point1XElement = BuildDocument.addElement(point1Element, "pointX");
			BuildDocument.addText(point1XElement, points[0]);
			Element point1YElement = BuildDocument.addElement(point1Element, "pointY");
			BuildDocument.addText(point1YElement, points[1]);
			
			Element point2Element = BuildDocument.addElement(pointsElement, "singlePoit");
			Element point2XElement = BuildDocument.addElement(point2Element, "pointX");
			BuildDocument.addText(point2XElement, points[2]);
			Element point2YElement = BuildDocument.addElement(point2Element, "pointY");
			BuildDocument.addText(point2YElement, points[3]);
		}							
		if (type.equals("point_to_area")) {
			
			Element singlePointElement = BuildDocument.addElement(element, "singlePoint");
			Element pointXElement = BuildDocument.addElement(singlePointElement, "pointX");
			BuildDocument.addText(pointXElement, startPoints[0]);
			Element pointYElement = BuildDocument.addElement(singlePointElement, "pointY");
			BuildDocument.addText(pointYElement, startPoints[1]);
			
			Element pointAreaElement = BuildDocument.addElement(element, "doublePoint");
			
			Element Area1Element = BuildDocument.addElement(pointAreaElement, "singlePoint");
			Element Area1XElement = BuildDocument.addElement(Area1Element, "pointX");
			BuildDocument.addText(Area1XElement, points[0]);
			Element Area1YElement = BuildDocument.addElement(Area1Element, "pointY");
			BuildDocument.addText(Area1YElement, points[1]);
			
			Element Area2Element = BuildDocument.addElement(pointAreaElement, "singlePoit");
			Element Area2XElement = BuildDocument.addElement(Area2Element, "pointX");
			BuildDocument.addText(Area2XElement, points[2]);
			Element Area2YElement = BuildDocument.addElement(Area2Element, "pointY");
			BuildDocument.addText(Area2YElement, points[3]);
		}
				
	}
	
	
	public static void addSubInfo(Element element, String action, String[] points){
		
		if (action.equals("click")||action.equals("lClick")) {
			BuildDocument.addAttribute(element, "action", action);
			BuildDocument.addAttribute(element, "type", "single_point");		
			Element pointElement = BuildDocument.addElement(element, "singlePoint");
			Element pointXElement = BuildDocument.addElement(pointElement, "pointX");
			BuildDocument.addText(pointXElement, points[0]);
			Element pointYElement = BuildDocument.addElement(pointElement, "pointY");
			BuildDocument.addText(pointYElement, points[1]);
		}
		if (action.equals("drag")) {
			BuildDocument.addAttribute(element, "action", action);
			BuildDocument.addAttribute(element, "type", "double_point");
			
			Element pointsElement = BuildDocument.addElement(element, "doublePoint");
			
			Element point1Element = BuildDocument.addElement(pointsElement, "singlePoint");
			Element point1XElement = BuildDocument.addElement(point1Element, "pointX");
			BuildDocument.addText(point1XElement, points[0]);
			Element point1YElement = BuildDocument.addElement(point1Element, "pointY");
			BuildDocument.addText(point1YElement, points[1]);
			
			Element point2Element = BuildDocument.addElement(pointsElement, "singlePoit");
			Element point2XElement = BuildDocument.addElement(point2Element, "pointX");
			BuildDocument.addText(point2XElement, points[2]);
			Element point2YElement = BuildDocument.addElement(point2Element, "pointY");
			BuildDocument.addText(point2YElement, points[3]);
		}
	}
	
	/**
	 * 设置结束边
	 * @param element
	 * @param type
	 * @param text
	 */
	public static void setEndState(Element element,String type,String text){
		
		BuildDocument.addAttribute(element, "typeCode", "2");
		
		if (type.equals("single_component")) {
			BuildDocument.addAttribute(element, "type", type);			
			Element componentElement = BuildDocument.addElement(element, "singleComponent");
			Element indexElement = BuildDocument.addElement(componentElement, "index");
			BuildDocument.addText(indexElement, "3");
			Element resourceElement = BuildDocument.addElement(componentElement, "resourceType");
			BuildDocument.addText(resourceElement, "editView");
			Element expectElement = BuildDocument.addElement(componentElement, "expect");
			BuildDocument.addAttribute(expectElement, "type", "text");
			BuildDocument.addText(expectElement, text);
		}
		
	}
	
	/**
	 * 
	 * @param element
	 * @param index
	 */
	public static void addVirtual(Element element,String resuourceId){
		BuildDocument.addAttribute(element, "type", "component");
		BuildDocument.addAttribute(element, "isVirutal", "true");
		Element resourceIdElement = BuildDocument.addElement(element, "resourceId");
		BuildDocument.addText(resourceIdElement, resuourceId);	
	}

}
