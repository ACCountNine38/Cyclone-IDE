package popup;

import display.State;

//This interface is implemented by popup frames such as cutomization and help screens
public interface DisplayPopups {
	
	//Popup dimensions
	public static final int POPUP_WIDTH = (int)(State.SCREEN_WIDTH/3*2);
	public static final int POPUP_HEIGHT = (int)(State.SCREEN_HEIGHT/3*2);
	
	//Abstract methods
	public abstract void addJComponents();
	public abstract void frameSetup();

}
