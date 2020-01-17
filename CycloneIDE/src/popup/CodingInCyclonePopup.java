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

public class CodingInCyclonePopup extends JFrame implements DisplayPopups, ActionListener {
	
	//Dimensions
	private static final int WIDTH = (int) (State.SCREEN_WIDTH / 3 * 2);
	private static final int HEIGHT = (int) (State.SCREEN_HEIGHT / 3 * 2);
	
	//Forward and back buttons
	private JButton continueButton = new JButton("Continue");
	private JButton backButton = new JButton("Back");
	
	//Background labels
	private JLabel helpLabel1 = new JLabel(); 
	private JLabel helpLabel2 = new JLabel(); 
	private JLabel helpLabel3 = new JLabel(); 
	
	private int screenNum = 0; //Tracks which label should be displayed
	
	private IDEInterface ide; //IDEInterface is passed in to the constructor
	
	public CodingInCyclonePopup(IDEInterface ide) {
		
		this.ide = ide;
		
		addJComponents();
		frameSetup();
		setScreen(0);
		
		addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent arg0) {
				ide.setEnabled(true); //Enable the ide before closing
			}
			
		});
		
	}
	
	@Override
	public void addJComponents() {
		
		//Add the buttons
		backButton.setBounds(10, 10, 100, 40);
		backButton.addActionListener(this);
		add(backButton);
		
		continueButton.setBounds(WIDTH - 110, 10, 100, 40);
		continueButton.addActionListener(this);
		add(continueButton);
		
		//Set up the background label
		try {
			helpLabel1.setIcon(new ImageIcon(new ImageIcon(ImageIO.read(Images.class.getResource("/writingAProgram.png"))).getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH)));
			helpLabel2.setIcon(new ImageIcon(new ImageIcon(ImageIO.read(Images.class.getResource("/controlStructures.png"))).getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH)));
			helpLabel3.setIcon(new ImageIcon(new ImageIcon(ImageIO.read(Images.class.getResource("/cycloneConventions.png"))).getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		helpLabel1.setBounds(0, 0, WIDTH, HEIGHT);
		helpLabel2.setVisible(true);
		add(helpLabel1);
		helpLabel2.setBounds(0, 0, WIDTH, HEIGHT);
		helpLabel2.setVisible(false);
		add(helpLabel2);
		helpLabel3.setBounds(0, 0, WIDTH, HEIGHT);
		helpLabel3.setVisible(false);
		add(helpLabel3);
		
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
	
	private void setScreen(int screen) {
		
		screenNum = screen;
		
		if(screen == 0) {
			helpLabel1.setVisible(true);
			helpLabel2.setVisible(false);
			helpLabel3.setVisible(false);
			backButton.setVisible(false);
		} else if(screen == 1) {
			helpLabel1.setVisible(false);
			helpLabel2.setVisible(true);
			helpLabel3.setVisible(false);
			continueButton.setVisible(true);
			backButton.setVisible(true);
		} else if(screen == 2) {
			helpLabel1.setVisible(false);
			helpLabel2.setVisible(false);
			helpLabel3.setVisible(true);
			continueButton.setVisible(false);
		} 
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == continueButton) { //Move forward one page
			setScreen(screenNum + 1);
		} else if(e.getSource() == backButton) { //Move back one page
			setScreen(screenNum - 1);
		}
		
	}

}
