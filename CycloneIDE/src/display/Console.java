package display;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Console extends Perspective {
	
	private static int orginX = (int) (Perspective.screenWidth/4 + 25);
	private static int orginY = (int) (Perspective.screenHeight*2/3 + 25);
	private static int width = (int) (Perspective.screenWidth/4*3 - 50);
	private static int height = (int) (Perspective.screenHeight/3 - 65);
	
	private JTextArea consoleTextArea = new JTextArea();
	
	public Console() {
		
		panelSetup(orginX, orginY, (int) Perspective.screenWidth, (int) Perspective.screenHeight, new Color(243, 243, 243));
		
		addJComponents();
		
	}
	
	public void addJComponents() {
		consoleTextArea.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 20));
		consoleTextArea.setBounds(0, 0, width, height);
		consoleTextArea.setLineWrap(true);
		consoleTextArea.setWrapStyleWord(true);
		JScrollPane consoleTextAreaScroll = new JScrollPane(consoleTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		consoleTextAreaScroll.setBounds(0 , 0, width, height);
		add(consoleTextAreaScroll); 
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
