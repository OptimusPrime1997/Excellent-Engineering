package Explain;

/**
 * Created by Administrator on 2016/7/8.
 */
public enum Action {
    DRAG,CLICK,DCLICK,LCLICK,ORACLE,AND,OR,NOT,TEXT;
    
    public static Action valueOf(int cur_action) {
		// TODO Auto-generated method stub
		switch (cur_action)
		{
			case 0: return Action.DRAG;
			case 1: return Action.CLICK;
			case 2: return Action.DCLICK;
			case 3: return Action.LCLICK;
			case 4: return Action.ORACLE;
//			default: return UNINDENTIFIED;
		}
		return null;
	}
    
    public static Action ToString(String act){
    	switch (act) {
		case "drag":
			return Action.DRAG;
		case "click":
			return Action.CLICK;
		case "dclick":
			return Action.DCLICK;
		case "lclick":
			return Action.LCLICK;
		case "oracle":
			return Action.ORACLE;
		case "and":
			return Action.AND;
		case "or":
			return Action.OR;
		case "not":
			return Action.NOT;
		case "input":
			return Action.TEXT;
		default:
			return null;
		}
    }
}
