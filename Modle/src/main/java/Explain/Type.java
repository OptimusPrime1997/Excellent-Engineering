package Explain;

public enum Type {
	Button, ImageButton, Menu;

	public static String valueOf(Type type) {

		switch (type) {
		case Button:
			return "clickOnButton";
		case ImageButton:
			return "clickOnImageButton";
		case Menu:
			return "clickOnMenuItem";
		default:
			return null;
		}

	}
	
	public static Type ToType(String type){
		switch (type) {
		case "Button":
			return Type.Button;
		case "ImageButton":
			return Type.ImageButton;
		case "Menu":
			return Type.Menu;
		default:
			break;
		}
		return null;
	}
}
