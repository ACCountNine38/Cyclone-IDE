package popup;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class KeywordCustomizationPopup extends JFrame implements DisplayPopups, ActionListener {

	private JButton save = new JButton("SAVE CHANGES");
	private JButton exit = new JButton("EXIT");
	
	public KeywordCustomizationPopup() {
		
		addJComponents();
		frameSetup();
		
	}
	
	@Override
	public void addJComponents() {
		
		exit.setBounds(POPUP_WIDTH - 200, POPUP_HEIGHT - 100, 150, 50);
		exit.addActionListener(this);
		add(exit);
		
		save.setBounds(POPUP_WIDTH - 400, POPUP_HEIGHT - 100, 150, 50);
		save.addActionListener(this);
		add(save);
	
	}

	// method that sets up the popup
	@Override
	public void frameSetup() {
		// set the name and size of the frame, and now allowing user to resize
		setTitle("Keyword Customization");
		setSize(POPUP_WIDTH, POPUP_HEIGHT);
		setResizable(false);

		// disables auto layout, center program, exit frame when program closes
		setLayout(null);
		setFocusable(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(new Color(225, 225, 225));

		// set frame to appear on screen
		setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
