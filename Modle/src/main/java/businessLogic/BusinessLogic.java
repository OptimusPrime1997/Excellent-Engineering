package businessLogic;

import java.io.File;
import java.util.List;

import Explain.XmlAnalyse;
import Node.ActionNode;

public class BusinessLogic {
	
	public void GetAllAction(String PackageWay,String MainActivity,String XmlWay){
		String name = "Test";
		XmlAnalyse analyseAction = new XmlAnalyse();
		List<ActionNode> actionList = analyseAction.GetAction(XmlWay+"paths.xml");
		System.out.println("asasfa"+actionList.get(0).getAction());
		for(int i=0;i<actionList.size();i++){
			ActionNode anActionNode = actionList.get(i);
//			while(anActionNode.getNext()!=null){
//				System.out.println("当前action为："+anActionNode.getAction());
//				System.out.println(anActionNode.getType());
//				System.out.println(anActionNode.getComponentid());
//				System.out.println(anActionNode.getPosition());
//				anActionNode = anActionNode.getNext();
//			}
//			System.out.println("当前路径的Oracle为："+anActionNode.getAction());
//			System.out.println(anActionNode.getType());
//			System.out.println(anActionNode.getComponentid());
//			System.out.println(anActionNode.getPosition());
			
			
			String Name = name+(i+"");
			String result = new TestGenerator().generatorCompleteTest(PackageWay,Name,MainActivity);
//			System.out.println(Name);
			new TestAssist().generator(anActionNode, result,XmlWay+"Script"+File.separator,Name);
			System.out.println("王栋去死吧");

//			System.out.println("heheda");
		}
		
		
//		return analyseAction.GetAction();
	}
	
	public static void main(String[] args) {
		BusinessLogic businessLogic = new BusinessLogic();
		businessLogic.GetAllAction(null,null,null);
	}
}
