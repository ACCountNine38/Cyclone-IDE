package popup;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

public class UtilityCustomizationPopup extends JFrame implements DisplayPopups, ActionListener {
	
	private static final int PANEL_WIDTH = DisplayPopups.POPUP_WIDTH - 100;
	private static final int PANEL_HEIGHT = DisplayPopups.POPUP_HEIGHT - 175;
	
	private JButton defaultSettings = new JButton("SET TO DEFAULT");
	private JButton save = new JButton("SAVE CHANGES");
	private JButton exit = new JButton("EXIT");
	
	private JPanel editorPanel = new JPanel(); //Main panel
	private JLabel editorFontLabel = new JLabel("Editor Font");
	private JComboBox<String> editorFontComboBox = new JComboBox<String>();
	
	
	private JPanel consolePanel = new JPanel();
	
	private ArrayList<KeywordOption> options = new ArrayList<KeywordOption>();
	
	public UtilityCustomizationPopup() {
		
		addJComponents();
		frameSetup();
		addFontOptions();
		
	}
	
	@Override
	public void addJComponents() {
		
		//set up the main panel
		editorPanel.setLayout(null);
		editorPanel.setBounds(50, 50, PANEL_WIDTH, PANEL_HEIGHT / 2 - 10); 
		
		consolePanel.setLayout(null);
		consolePanel.setBounds(50,  PANEL_HEIGHT / 2 + 10, PANEL_WIDTH, PANEL_HEIGHT / 2 - 10); 
		
		editorFontLabel.setFont(new Font("Dialog", Font.BOLD, 24));
		editorFontLabel.setHorizontalAlignment(SwingConstants.CENTER);
		editorFontLabel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		editorFontLabel.setBounds(0, 0, KeywordOption.WIDTH, KeywordOption.HEIGHT);
		editorPanel.add(editorFontLabel);
		
		editorFontComboBox.setFont(new Font("Dialog", Font.BOLD, 24));
		//editorFontComboBox.setHorizontalAlignment(SwingConstants.CENTER);
		editorFontComboBox.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		editorFontComboBox.setBounds(0, 0, KeywordOption.WIDTH, KeywordOption.HEIGHT);
		editorPanel.add(editorFontComboBox);
		
		
		
		
		//Setup the buttons
		exit.setBounds(POPUP_WIDTH - 200, POPUP_HEIGHT - 100, 150, 50);
		exit.addActionListener(this);
		add(exit);
		
		save.setBounds(POPUP_WIDTH - 400, POPUP_HEIGHT - 100, 150, 50);
		save.addActionListener(this);
		add(save);
		
		defaultSettings.setBounds(POPUP_WIDTH - 600, POPUP_HEIGHT - 100, 150, 50);
		defaultSettings.addActionListener(this);
		add(defaultSettings);
		
		//add the panels to the frame
		add(editorPanel);
		add(consolePanel);
		
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
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(new Color(225, 225, 225));

		// set frame to appear on screen
		setVisible(true);
		
	}
	
	//This method adds the fonts to the combo boxes
	private void addFontOptions() {
		
		//KeywordOption option = new KeywordOption();
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
