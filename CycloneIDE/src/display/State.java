package display;

import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import utils.FileExecutionTool;

public class State extends JFrame implements ActionListener {

	// final variables
	public static final double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	public static final double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	
	public static File currentFile = null;
	
	public State() {

		new FileExecutionTool();
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

	public void addMenuBar() {
		// create a new JMenuBar item that stores different menus
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		// create a new menu called control and add it to the menu bar
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);

		// creating the exit option under the control menu
		JMenuItem exitOption = new JMenuItem("Exit IDE");

		// add an action listener for button actions when clicked
		exitOption.addActionListener(new ActionListener() {

			// method handles the current button's actions
			@Override
			public void actionPerformed(ActionEvent e) {

				System.exit(1);

			}

		});

		fileMenu.add(exitOption);

		// create a new menu for settings
		JMenu editMenu = new JMenu("Edit");
		menuBar.add(editMenu);

		// create a new menu to make window changes
		JMenu windowMenu = new JMenu("Window");
		menuBar.add(windowMenu);

		// create a new menu to run the project
		JMenu runMenu = new JMenu("Run");
		menuBar.add(runMenu);

		// creating the exit option under the control menu
		JMenuItem runOption = new JMenuItem("Run Project");

		// add an action listener for button actions when clicked
		exitOption.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				// runs the current project
				FileExecutionTool.executeFile(currentFile);

			}

		});

		runMenu.add(runOption);

		// create a new menu to show help commands
		JMenu helpMenu = new JMenu("Help");
		menuBar.add(helpMenu);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
