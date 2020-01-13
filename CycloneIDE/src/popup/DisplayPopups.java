package popup;

import display.State;

public interface DisplayPopups {
	
	public static final int POPUP_WIDTH = (int)(State.SCREEN_WIDTH/3*2);
	public static final int POPUP_HEIGHT = (int)(State.SCREEN_HEIGHT/3*2);
	
//	public static final int POPUP_WIDTH = 500;
//	public static final int POPUP_HEIGHT = 300;
	
	public abstract void addJComponents();
	public abstract void frameSetup();

}
