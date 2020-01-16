package display;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

public class Console extends Perspective {
	
	private static int orginX = (int) (Perspective.screenWidth/4 + 25);
	private static int orginY = (int) (Perspective.screenHeight*2/3 + 25);
	private static int width = (int) (Perspective.screenWidth/4*3 - 50);
	private static int height = (int) (Perspective.screenHeight/3 - 65) - 25;
	
	public static JTextArea consoleTextArea = new JTextArea();
	
	public static Font consoleFont = new Font("Consolas", Font.PLAIN, 22);
	public static int consoleTabSize = 2;
	
	public Console() {
		
		panelSetup(orginX, orginY, (int) Perspective.screenWidth, (int) Perspective.screenHeight, State.utilityColor);
		
		addJComponents();
		
	}
	
	public void addJComponents() {
		
		consoleTextArea.setFont(consoleFont);
		consoleTextArea.setForeground(State.textColor);
		consoleTextArea.setBackground(State.utilityColor);
		consoleTextArea.setOpaque(true);
		consoleTextArea.setBounds(0, 0, width, height);
		consoleTextArea.setEditable(false);
		consoleTextArea.setTabSize(consoleTabSize);

		JScrollPane consoleTextAreaScroll = new JScrollPane(consoleTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		consoleTextAreaScroll.setBounds(0 , 0, width, height);
		add(consoleTextAreaScroll); 
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
