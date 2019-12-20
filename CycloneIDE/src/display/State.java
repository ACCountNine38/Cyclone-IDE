package display;

import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class State extends JFrame implements ActionListener{
	
	// final variables
	public static final double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	public static final double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		
	public State() {
		
		customCursor();
		
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

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
