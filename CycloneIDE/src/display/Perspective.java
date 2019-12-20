package display;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
/*
 * The parent class of all states, top of the class hierarchy
 * contains default information to be used for other states and abstract methods
 */
public abstract class Perspective extends JPanel implements ActionListener {

	// final variables
	public static final double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	public static final double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	
	// method that sets up a frame
	public void panelSetup(int orginX, int orginY, int width, int height, Color color) {

		// set the name and size of the frame, and now allowing user to resize
		setBounds(orginX, orginY, width, height);

		// disables auto layout, center program, exit frame when program closes
		setLayout(null);
		setFocusable(true);
		setBackground(color);

		// set frame to appear on screen
		setVisible(true);

	}
	
	// abstract method that adds all the JComponents to a state
	public abstract void addJComponents();

}