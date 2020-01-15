package popup;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import assets.Images;
import display.IDEInterface;
import display.State;

public class GettingStartedPopup extends JFrame implements DisplayPopups, ActionListener {
	
	private static final int WIDTH = (int) (State.SCREEN_WIDTH / 3 * 2);
	private static final int HEIGHT = (int) (State.SCREEN_HEIGHT / 3 * 2);
	
	private JButton exit = new JButton("EXIT");
	private JLabel helpLabel = new JLabel(); //Main panel
	
	private IDEInterface ide;
	
	public GettingStartedPopup(IDEInterface ide) {
		
		this.ide = ide;
		
		addJComponents();
		frameSetup();
		
		addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent arg0) {
				ide.setEnabled(true); //Enable the ide before closing
			}
			
		});
		
	}
	
	@Override
	public void addJComponents() {
		
		//Set up the background label
		try {
			helpLabel.setIcon(new ImageIcon(new ImageIcon(ImageIO.read(Images.class.getResource("/gettingStarted.png"))).getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		helpLabel.setBounds(0, 0, WIDTH, HEIGHT);
		add(helpLabel);
		
		//Setup the buttons
//		exit.setBounds(WIDTH - 200, HEIGHT - 100, 150, 50);
//		exit.addActionListener(this);
//		add(exit);

	}

	//This method sets up the frame
	@Override
	public void frameSetup() {
		// set the name and size of the frame, and now allowing user to resize
		setTitle("Getting Started");
		setSize(POPUP_WIDTH, POPUP_HEIGHT);
		setResizable(false);

		// disables auto layout, center program, exit frame when program closes
		setLayout(null);
		setFocusable(true);
		setLocationRelativeTo(null);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(new Color(225, 225, 225));

		// set frame to appear on screen
		setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == exit) {
			ide.setEnabled(true); //Enable the ide before closing
			this.dispose();
		}
		
	}

}
