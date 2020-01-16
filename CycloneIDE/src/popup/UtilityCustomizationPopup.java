package popup;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.NumberFormatter;

import display.Console;
import display.IDEInterface;
import objects.Class;
import objects.Project;

public class UtilityCustomizationPopup extends JFrame implements DisplayPopups, ActionListener {
	
	private static final int WIDTH = DisplayPopups.POPUP_WIDTH - 100;
	private static final int HEIGHT = DisplayPopups.POPUP_HEIGHT - 200;
	
	private static final int PANEL_HEIGHT = HEIGHT / 2 - 10;
	
	//Title and buttons
	private JLabel titleLabel = new JLabel("Utility Customization");
	private JButton defaultSettings = new JButton("SET TO DEFAULT");
	private JButton save = new JButton("SAVE CHANGES");
	private JButton exit = new JButton("EXIT");
	
	//Editor Settings
	private JPanel editorPanel = new JPanel(); 
	private JLabel editorTitleLabel = new JLabel("Editor Settings");
	private JLabel editorFontLabel = new JLabel("Editor Font");
	private JComboBox<String> editorFontComboBox = new JComboBox<String>();
	private JLabel editorFontSizeLabel = new JLabel("Editor Font Size");
	private JFormattedTextField editorFontSizeField;
	private JLabel editorIndentSpaceLabel = new JLabel("Editor Indent Space Size");
	private JFormattedTextField editorIndentSpaceField;
	
	//Console settings
	private JPanel consolePanel = new JPanel();
	private JLabel consoleTitleLabel = new JLabel("Console Settings");
	private JLabel consoleFontLabel = new JLabel("Console Font");
	private JComboBox<String> consoleFontComboBox = new JComboBox<String>();
	private JLabel consoleFontSizeLabel = new JLabel("Console Font Size");
	private JFormattedTextField consoleFontSizeField;
	private JLabel consoleIndentSpaceLabel = new JLabel("Console Indent Space Size");
	private JFormattedTextField consoleIndentSpaceField;
	
	//Light/dark theme
	private JLabel darkThemeLabel = new JLabel("<html><center>Dark<br>Theme:</center></html>");
	private JRadioButton onButton = new JRadioButton("On");
	private JRadioButton offButton = new JRadioButton("Off");
	private ButtonGroup radioButtonGroup = new ButtonGroup();
	
	private IDEInterface ide;
	
	//Constructor Method
	public UtilityCustomizationPopup(IDEInterface ide) {
		
		this.ide = ide;
		
		addJComponents();
		frameSetup();
		addFontOptions();
		readCurrentFont();
		
		addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent arg0) {
				ide.setEnabled(true); //Enable the ide before closing
			}
			
		});
		
	}
	
	//This method adds the required JComponents to the frame
	@Override
	public void addJComponents() {
		
		//Set up the editor and console panels
		setupEditorPanel();
		setupConsolePanel();
		
		//Add a title label to the frame
		titleLabel.setFont(new Font("Dialog", Font.BOLD, 36));
		titleLabel.setBounds(50, 0, WIDTH, 75);
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(titleLabel);
		
		//Set up the dark theme label and radio buttons
		darkThemeLabel.setFont(new Font("Dialog", Font.BOLD, 24));
		darkThemeLabel.setBounds(50, 75 + HEIGHT + 10, 85, 75);
		darkThemeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(darkThemeLabel);
		
		radioButtonGroup.add(onButton);
		radioButtonGroup.add(offButton);
		
		onButton.setBounds(150, 75 + HEIGHT + 10, 60, 75);
		onButton.setFont(new Font("Dialog", Font.BOLD, 24));
		onButton.addActionListener(this);
		add(onButton);
		
		offButton.setBounds(210, 75 + HEIGHT + 10, 60, 75);
		offButton.setFont(new Font("Dialog", Font.BOLD, 24));
		offButton.addActionListener(this);
		add(offButton);
		
		//Setup the buttons
		exit.setBounds(POPUP_WIDTH - 200, POPUP_HEIGHT - 100, 150, 50);
		exit.addActionListener(this);
		add(exit);
		
		save.setBounds(POPUP_WIDTH - 375, POPUP_HEIGHT - 100, 150, 50);
		save.addActionListener(this);
		add(save);
		
		defaultSettings.setBounds(POPUP_WIDTH - 550, POPUP_HEIGHT - 100, 150, 50);
		defaultSettings.addActionListener(this);
		add(defaultSettings);
		
		//Add the panels to the frame
		add(editorPanel);
		add(consolePanel);
		
	}
	
	//This method sets put he editor panel
	private void setupEditorPanel() {
		
		//Number formatter for formatted text fields
	    NumberFormat format = NumberFormat.getInstance();
	    format.setGroupingUsed(false);
	    NumberFormatter formatter = new NumberFormatter(format);
	    formatter.setValueClass(Integer.class);
	    formatter.setMinimum(1);
	    formatter.setMaximum(Integer.MAX_VALUE);
		
		//Set up the editor panel
		editorPanel.setLayout(null);
		editorPanel.setBounds(50, 75, WIDTH, PANEL_HEIGHT); 
		editorPanel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		
		//Set up the panel title label
		editorTitleLabel.setFont(new Font("Dialog", Font.BOLD, 28));
		editorTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		editorTitleLabel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		editorTitleLabel.setBounds(0, 0, WIDTH, PANEL_HEIGHT / 4);
		editorPanel.add(editorTitleLabel);
		
		//Editor font
		editorFontLabel.setFont(new Font("Dialog", Font.BOLD, 24));
		editorFontLabel.setHorizontalAlignment(SwingConstants.CENTER);
		editorFontLabel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		editorFontLabel.setBounds(0, PANEL_HEIGHT / 4, KeywordOption.WIDTH, PANEL_HEIGHT / 4);
		editorPanel.add(editorFontLabel);
		
		editorFontComboBox.setFont(new Font("Dialog", Font.BOLD, 24));
		editorFontComboBox.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		editorFontComboBox.setBounds(KeywordOption.WIDTH, PANEL_HEIGHT / 4, KeywordOption.WIDTH, PANEL_HEIGHT / 4);
		editorPanel.add(editorFontComboBox);
		
		//Editor font size
		editorFontSizeLabel.setFont(new Font("Dialog", Font.BOLD, 24));
		editorFontSizeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		editorFontSizeLabel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		editorFontSizeLabel.setBounds(0, PANEL_HEIGHT / 4 * 2, KeywordOption.WIDTH, PANEL_HEIGHT / 4);
		editorPanel.add(editorFontSizeLabel);
		
		editorFontSizeField = new JFormattedTextField(formatter);
		editorFontSizeField.setFont(new Font("Dialog", Font.BOLD, 24));
		editorFontSizeField.setHorizontalAlignment(SwingConstants.CENTER);
		editorFontSizeField.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		editorFontSizeField.setBounds(KeywordOption.WIDTH, PANEL_HEIGHT / 4 * 2, KeywordOption.WIDTH, PANEL_HEIGHT / 4);
		editorPanel.add(editorFontSizeField);
		
		//Editor indent spacing
		editorIndentSpaceLabel.setFont(new Font("Dialog", Font.BOLD, 24));
		editorIndentSpaceLabel.setHorizontalAlignment(SwingConstants.CENTER);
		editorIndentSpaceLabel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		editorIndentSpaceLabel.setBounds(0, PANEL_HEIGHT / 4 * 3, KeywordOption.WIDTH, PANEL_HEIGHT / 4);
		editorPanel.add(editorIndentSpaceLabel);
		
		editorIndentSpaceField = new JFormattedTextField(formatter);
		editorIndentSpaceField.setFont(new Font("Dialog", Font.BOLD, 24));
		editorIndentSpaceField.setHorizontalAlignment(SwingConstants.CENTER);
		editorIndentSpaceField.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		editorIndentSpaceField.setBounds(KeywordOption.WIDTH, PANEL_HEIGHT / 4 * 3, KeywordOption.WIDTH, PANEL_HEIGHT / 4);
		editorPanel.add(editorIndentSpaceField);
		
	}
	
	//This method sets up the console panel
	private void setupConsolePanel() {
		
		//Number formatter for formatted text fields
	    NumberFormat format = NumberFormat.getInstance();
	    format.setGroupingUsed(false);
	    NumberFormatter formatter = new NumberFormatter(format);
	    formatter.setValueClass(Integer.class);
	    formatter.setMinimum(1);
	    formatter.setMaximum(Integer.MAX_VALUE);
		
		//set up the console panel
		consolePanel.setLayout(null);
		consolePanel.setBounds(50, 75 + HEIGHT / 2 + 10, WIDTH, PANEL_HEIGHT); 
		consolePanel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		
		//set up the panel title label
		consoleTitleLabel.setFont(new Font("Dialog", Font.BOLD, 28));
		consoleTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		consoleTitleLabel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		consoleTitleLabel.setBounds(0, 0, WIDTH, PANEL_HEIGHT / 4);
		consolePanel.add(consoleTitleLabel);
		
		//Console font
		consoleFontLabel.setFont(new Font("Dialog", Font.BOLD, 24));
		consoleFontLabel.setHorizontalAlignment(SwingConstants.CENTER);
		consoleFontLabel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		consoleFontLabel.setBounds(0, PANEL_HEIGHT / 4, KeywordOption.WIDTH, PANEL_HEIGHT / 4);
		consolePanel.add(consoleFontLabel);
		
		consoleFontComboBox.setFont(new Font("Dialog", Font.BOLD, 24));
		consoleFontComboBox.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		consoleFontComboBox.setBounds(KeywordOption.WIDTH, PANEL_HEIGHT / 4, KeywordOption.WIDTH, PANEL_HEIGHT / 4);
		consolePanel.add(consoleFontComboBox);
		
		//Console font size
		consoleFontSizeLabel.setFont(new Font("Dialog", Font.BOLD, 24));
		consoleFontSizeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		consoleFontSizeLabel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		consoleFontSizeLabel.setBounds(0, PANEL_HEIGHT / 4 * 2, KeywordOption.WIDTH, PANEL_HEIGHT / 4);
		consolePanel.add(consoleFontSizeLabel);
		
		consoleFontSizeField = new JFormattedTextField(formatter);
		consoleFontSizeField.setFont(new Font("Dialog", Font.BOLD, 24));
		consoleFontSizeField.setHorizontalAlignment(SwingConstants.CENTER);
		consoleFontSizeField.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		consoleFontSizeField.setBounds(KeywordOption.WIDTH, PANEL_HEIGHT / 4 * 2, KeywordOption.WIDTH, PANEL_HEIGHT / 4);
		consolePanel.add(consoleFontSizeField);
		
		//Console indent spacing
		consoleIndentSpaceLabel.setFont(new Font("Dialog", Font.BOLD, 24));
		consoleIndentSpaceLabel.setHorizontalAlignment(SwingConstants.CENTER);
		consoleIndentSpaceLabel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		consoleIndentSpaceLabel.setBounds(0, PANEL_HEIGHT / 4 * 3, KeywordOption.WIDTH, PANEL_HEIGHT / 4);
		consolePanel.add(consoleIndentSpaceLabel);
		
		consoleIndentSpaceField = new JFormattedTextField(formatter);
		consoleIndentSpaceField.setFont(new Font("Dialog", Font.BOLD, 24));
		consoleIndentSpaceField.setHorizontalAlignment(SwingConstants.CENTER);
		consoleIndentSpaceField.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		consoleIndentSpaceField.setBounds(KeywordOption.WIDTH, PANEL_HEIGHT / 4 * 3, KeywordOption.WIDTH, PANEL_HEIGHT / 4);
		consolePanel.add(consoleIndentSpaceField);
		
	}
	
	//This method sets up the frame
	@Override
	public void frameSetup() {
		// set the name and size of the frame, and now allowing user to resize
		setTitle("Utility Customization");
		setSize(POPUP_WIDTH, POPUP_HEIGHT);
		setResizable(false);

		// disables auto layout, center program
		setLayout(null);
		setFocusable(true);
		setLocationRelativeTo(null);
		setBackground(new Color(225, 225, 225));

		// set frame to appear on screen
		setVisible(true);
		
	}
	
	//This method adds the fonts to the combo boxes
	private void addFontOptions() {
		
		//Add fonts to editor options
		editorFontComboBox.addItem("Consolas");
		editorFontComboBox.addItem("Monospaced");
		editorFontComboBox.addItem("Serif");
		editorFontComboBox.addItem("SimSun");
		editorFontComboBox.addItem("Lucida Console");
		editorFontComboBox.addItem("Letter Gothic Std");
		
		//Add fonts to console options
		consoleFontComboBox.addItem("Consolas");
		consoleFontComboBox.addItem("Monospaced");
		consoleFontComboBox.addItem("Serif");
		consoleFontComboBox.addItem("SimSun");
		consoleFontComboBox.addItem("Lucida Console");
		consoleFontComboBox.addItem("Letter Gothic Std");
		
	}
	
	//This method reads the current settings of the IDE and displays them in the combo boxes and text fields
	private void readCurrentFont() {
		
		try {
			
			Scanner input = new Scanner(new File("settings/fonts"));
			
			//Display settings to respective components on screen
			editorFontComboBox.setSelectedItem(input.next());
			editorFontSizeField.setText(input.next());
			editorIndentSpaceField.setText(input.next());
			consoleFontComboBox.setSelectedItem(input.next());
			consoleFontSizeField.setText(input.next());
			consoleIndentSpaceField.setText(input.next());
			
			String theme = input.next();
			
			if(theme.equals("light")) {
				offButton.setSelected(true);
			} else if(theme.equals("dark")) {
				onButton.setSelected(true);
			}
			
			input.close();
			
		} catch(FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == defaultSettings) { //Reset the settings to default
			
			//Change editor and console settings to default settings
			editorFontComboBox.setSelectedItem("Consolas");
			editorFontSizeField.setText("22");
			editorIndentSpaceField.setText("2");
			
			consoleFontComboBox.setSelectedItem("Consolas");
			consoleFontSizeField.setText("22");
			consoleIndentSpaceField.setText("2");
			
			offButton.setSelected(true);
			
		} else if(e.getSource() == save) {
			
			if(editorFontSizeField.getText().equals("")) {
				editorFontSizeField.setText("1");
			} else if(editorIndentSpaceField.getText().equals("")) {
				editorIndentSpaceField.setText("1");
			} else if(consoleFontSizeField.getText().equals("")) {
				consoleFontSizeField.setText("1");
			} else if(consoleIndentSpaceField.getText().equals("")) {
				consoleIndentSpaceField.setText("1");
			} 
			
			//Save current settings to the file
			try {
				
				PrintWriter pr = new PrintWriter(new File("settings/fonts"));

				pr.println(editorFontComboBox.getSelectedItem() + " " + Integer.parseInt(editorFontSizeField.getText()) + " " + Integer.parseInt(editorIndentSpaceField.getText()));
				pr.println(consoleFontComboBox.getSelectedItem() + " " + Integer.parseInt(consoleFontSizeField.getText()) + " " + Integer.parseInt(consoleIndentSpaceField.getText()));

				if(onButton.isSelected()) {
					pr.println("dark");
				} else {
					pr.println("light");
				}

				pr.close();

			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			String theme = "";
			
			//Apply the saved settings
			ide.loadUtilitySettings();
			ide.resetColor();
			
			//Apply changes to all editor text areas
			for(Project currentProject: ide.getProjectExplorer().getProjects()) {
				for(Class currentClass: currentProject.getFileButtons()) {
					
					currentClass.getEditorTextArea().setFont(Class.editorFont); //Change editor font
					currentClass.getEditorTextArea().setTabSize(Class.editorTabSize); //Change editor font size
					currentClass.getLineNumberComponent().adjustWidth(); //Adjust line number size
					
				}
			}
			
			Console.consoleTextArea.setFont(Console.consoleFont); //Change the console font
			Console.consoleTextArea.setTabSize(Console.consoleTabSize); //Change the console tab size
			
			//Apply dark theme changes
			if(theme.equals("light")) {
				
			} else if(theme.equals("dark")) {
				
			}
			
			//Close the popup
			ide.setEnabled(true); //Enable the ide before closing
			this.dispose();
			
		} else if(e.getSource() == exit) { //Close the popup
			ide.setEnabled(true); //Enable the ide before closing
			this.dispose();
		}
		
	}

}
