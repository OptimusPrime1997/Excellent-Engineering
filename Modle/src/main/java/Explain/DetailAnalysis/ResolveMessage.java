package Explain.DetailAnalysis;

public class ResolveMessage {

	public String ResolveMsg(String message,String key){
		String result = null;
		String[] node = message.split(" ");
		String[] detailMsg = node[1].split(";");
		for(int i=0;i<detailMsg.length;i++){
			String[] partName = detailMsg[i].split("=");
			if (partName[0].equalsIgnoreCase(key)) {
				String[] partGet = detailMsg[i].split("\"");
				result = partGet[1];
			}
		}
		
		return result;
	}
}
