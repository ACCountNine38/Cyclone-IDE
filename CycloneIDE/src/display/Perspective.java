package display;

import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
/*
 * The parent class of all states, top of the class hierarchy
 * contains default information to be used for other states and abstract methods
 */
public abstract class Perspective extends JFrame implements ActionListener {

	// final variables
	public static final double unscaledScreenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	public static final double unscaledScreenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	private static final double widthScale = unscaledScreenWidth / 1280;
	private static final double heightScale = unscaledScreenHeight / 800;
	
	// scale variable to determine the size of components based on the screen size
	public static double scale;

	// the scaled dimensions of the game screen
	public static int ScreenWidth;
	public static int ScreenHeight;

	// the origin of components placed on screen based on the screen size
	public static int scaledOrginX;
	public static int scaledOrginY;
	
	// modified tile icon size based on the scale
	public static int tileIconSize;
			
	// a constructor that will be shared to all states
	public Perspective() {

		// to avoid any stretching, the scale that is the minimum will be the final scale
		if (widthScale < heightScale) {

			scale = 1;

		} else {

			scale = 1;

		}
		scale = 1;
		// calculating the modified screen dimensions
		ScreenWidth = (int) (1280 * scale);
		ScreenHeight = (int) (800 * scale);
		
		// calculating the tile icon size
		tileIconSize = (int)(ScreenHeight/9.5);
		
		//scaledOrginX = (int)(State.unscaledScreenWidth - State.ScreenWidth)/2;
		//scaledOrginY = (int)(State.unscaledScreenHeight - State.ScreenHeight)/2;
		scaledOrginX = 0;
		scaledOrginY = 0;

		customCursor();

	}
	
	// method that sets up a frame
	private void frameSetup(String name, int width, int height) {

		addJComponents();
		
		// set the name and size of the frame, and now allowing user to resize
		setTitle(name);
		setSize(width, height);
		setResizable(false);

		// disables auto layout, center program, exit frame when program closes
		setLayout(null);
		setFocusable(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// set frame to appear on screen
		setVisible(true);

	}
	
	// method that changes the cursor icon
	private void customCursor() {

		// using the java TookKit to change cursors
		Toolkit toolkit = Toolkit.getDefaultToolkit();

		// load an image using ToolKit
		Image mouse = toolkit.getImage("images/cursor.png").getScaledInstance(25, 40, 0);

		// set the cursor icon giving a new image, point, and name
		setCursor(toolkit.createCustomCursor(mouse, new Point(0, 0), "Custom Cursor"));

	}
	
	// abstract method that adds all the JComponents to a state
	public abstract void addJComponents();

}