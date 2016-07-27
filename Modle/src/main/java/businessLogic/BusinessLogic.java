package businessLogic;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import Explain.XmlAnalyse;
import Node.ActionNode;
import view.ConsolePane;

public class BusinessLogic {
	private ConsolePane consolePane;
	
	public void GetAllAction(String PackageWay,String MainActivity,String XmlWay){
		String name = "Test";
		XmlAnalyse analyseAction = new XmlAnalyse();
		List<ActionNode> actionList = new ArrayList<>();
		try {
			actionList = analyseAction.GetAction(XmlWay+"paths.xml");
		} catch (Exception e) {
			// TODO: handle exception
			consolePane.creatField();
		}
		System.out.println("asasfa"+actionList.get(0).getAction());
//		ConsolePane anConsolePane = this.getConsole();
		consolePane.resetProcessBar();
		consolePane.setMax(actionList.size());
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
			consolePane.addProcessBar();
//			System.out.println("heheda");
		}
		
		
//		return analyseAction.GetAction();
	}
	
	public void setConsole(ConsolePane consolePane){
		this.consolePane = consolePane;
	}
	
//	public ConsolePane getConsole(){
//		return this.consolePane;
//	}
	
	public static void main(String[] args) {
		BusinessLogic businessLogic = new BusinessLogic();
		businessLogic.GetAllAction(null,null,null);
	}
}
