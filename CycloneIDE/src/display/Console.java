package display;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

//This class is used to create a console JPanel to be displayed on the main frame of the Cyclone IDE
public class Console extends Perspective {
	
	//Dimensions
	private static int orginX = (int) (Perspective.screenWidth/4 + 25);
	private static int orginY = (int) (Perspective.screenHeight*2/3 + 25);
	private static int width = (int) (Perspective.screenWidth/4*3 - 50);
	private static int height = (int) (Perspective.screenHeight/3 - 65) - 25;
	
	//Console text area
	public static JTextArea consoleTextArea = new JTextArea();
	
	//Font settings
	public static Font consoleFont = new Font("Consolas", Font.PLAIN, 22);
	public static int consoleTabSize = 2;
	
	//Constructor method
	public Console() {
		
		//Set up the panel
		panelSetup(orginX, orginY, (int) Perspective.screenWidth, (int) Perspective.screenHeight, State.utilityColor);
		
		//Add the JComponents
		addJComponents();
		
	}
	
	//This method adds JComponents to the panel
	public void addJComponents() {
		
		//Set up the console text area
		consoleTextArea.setFont(consoleFont);
		consoleTextArea.setForeground(State.textColor);
		consoleTextArea.setBackground(State.utilityColor);
		consoleTextArea.setOpaque(true);
		consoleTextArea.setBounds(0, 0, width, height);
		consoleTextArea.setEditable(false);
		consoleTextArea.setTabSize(consoleTabSize);
		
		//Set up a scroll pane for the text area
		JScrollPane consoleTextAreaScroll = new JScrollPane(consoleTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		consoleTextAreaScroll.setBounds(0 , 0, width, height);
		add(consoleTextAreaScroll); 
		
	}
	
	//This is a required method for action listener
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
