package display;

import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class IDEInterface extends State {
	
	// perspectives
	private Console console;
	private Editor editor;
	private ProjectExplorer projectExplorer;
	
	private JLabel background = new JLabel();
	
	public IDEInterface() {
		
		addPerspectives();
		
		frameSetup(this, "Cyclone IDE", (int) Perspective.screenWidth, (int) Perspective.screenHeight);

	}
	
	private void addPerspectives(){
		
		console = new Console();
		add(console);
		
		editor = new Editor();
		add(editor);
		
		projectExplorer = new ProjectExplorer();
		add(projectExplorer);
		
	}

	// method that sets up a frame
	public static void frameSetup(JFrame frame, String name, int width, int height) {

		// set the name and size of the frame, and now allowing user to resize
		frame.setTitle(name);
		frame.setSize((int)State.screenWidth, (int)State.screenHeight);
		frame.setResizable(false);

		// disables auto layout, center program, exit frame when program closes
		frame.setLayout(null);
		frame.setFocusable(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBackground(new Color(225, 225, 225));

		// set frame to appear on screen
		frame.setVisible(true);

	}
		
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}

