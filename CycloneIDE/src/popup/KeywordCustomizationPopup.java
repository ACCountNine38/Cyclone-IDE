package popup;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import display.IDEInterface;
import utils.FileExecutionTool;

public class KeywordCustomizationPopup extends JFrame implements DisplayPopups, ActionListener {
	
	//Panel dimensions
	private static final int PANEL_WIDTH = DisplayPopups.POPUP_WIDTH - 100;
	private static final int PANEL_HEIGHT = DisplayPopups.POPUP_HEIGHT - 200;
	
	//Title and buttons
	private JLabel titleLabel = new JLabel("Key Customization");
	private JButton defaultSettings = new JButton("SET TO DEFAULT");
	private JButton save = new JButton("SAVE CHANGES");
	private JButton exit = new JButton("EXIT");
	
	//Main panel and scroll pane
	private JPanel customizePanel = new JPanel(); 
	private JScrollPane scroll;
	
	private ArrayList<KeywordOption> options = new ArrayList<KeywordOption>(); //List of keyword options
	
	private IDEInterface ide; //IDEInterface is passed in to the constructor
	
	//Constructor method
	public KeywordCustomizationPopup(IDEInterface ide) {
		
		this.ide = ide;
		
		//Set up the frame and add components
		addJComponents();
		frameSetup();
		addKeywordOptions();
		
		//Enable the ide before closing
		addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent arg0) {
				ide.setEnabled(true); 
			}
			
		});
		
	}
	
	//This method adds the required JComponents to the frame
	@Override
	public void addJComponents() {
		
		//Set up the main panel
		customizePanel.setLayout(new BoxLayout(customizePanel, BoxLayout.Y_AXIS));
		scroll = new JScrollPane(customizePanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setBounds(50, 75, PANEL_WIDTH, PANEL_HEIGHT);
		scroll.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		add(scroll);
		
		//Create a panel for the title labels
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(null);
		titlePanel.setSize(KeywordOption.WIDTH * 2, KeywordOption.HEIGHT);
		titlePanel.setPreferredSize(titlePanel.getSize());
		titlePanel.setMaximumSize(titlePanel.getSize());
		titlePanel.setMinimumSize(titlePanel.getSize());
		
		//Add the title labels for each column
		//Set up and add the label at the top of the function column
		JLabel functionTitleLabel = new JLabel("<html><center>Existing Keywords/Functions</center></html>");
		functionTitleLabel.setFont(new Font("Dialog", Font.BOLD, 24));
		functionTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		functionTitleLabel.setBounds(0, 0, KeywordOption.WIDTH, KeywordOption.HEIGHT);
		functionTitleLabel.setPreferredSize(functionTitleLabel.getSize());
		functionTitleLabel.setMaximumSize(functionTitleLabel.getSize());
		functionTitleLabel.setMinimumSize(functionTitleLabel.getSize());
		functionTitleLabel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		titlePanel.add(functionTitleLabel);
		
		//Set up and add the label at the top of the keyword column
		JLabel keywordTitleLabel = new JLabel("<html><center>User Defined Keywords/Functions</center></html>");
		keywordTitleLabel.setFont(new Font("Dialog", Font.BOLD, 24));
		keywordTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		keywordTitleLabel.setBounds(KeywordOption.WIDTH, 0, KeywordOption.WIDTH, KeywordOption.HEIGHT);
		keywordTitleLabel.setPreferredSize(keywordTitleLabel.getSize());
		keywordTitleLabel.setMaximumSize(keywordTitleLabel.getSize());
		keywordTitleLabel.setMinimumSize(keywordTitleLabel.getSize());
		keywordTitleLabel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		titlePanel.add(keywordTitleLabel);
		
		//Add the title panel to the main panel
		customizePanel.add(titlePanel);
		
		//Add a title label to the frame
		titleLabel.setFont(new Font("Dialog", Font.BOLD, 36));
		titleLabel.setBounds(50, 0, PANEL_WIDTH, 75);
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(titleLabel);
		
		//Setup the buttons
		//Set up the exit button
		exit.setBounds(POPUP_WIDTH - 200, POPUP_HEIGHT - 100, 150, 50);
		exit.addActionListener(this);
		add(exit);
		
		//Set up the save button
		save.setBounds(POPUP_WIDTH - 400, POPUP_HEIGHT - 100, 150, 50);
		save.addActionListener(this);
		add(save);
		
		//Set up the default settings button
		defaultSettings.setBounds(POPUP_WIDTH - 600, POPUP_HEIGHT - 100, 150, 50);
		defaultSettings.addActionListener(this);
		add(defaultSettings);
	}

	//This method sets up the frame
	@Override
	public void frameSetup() {
		
		// set the name and size of the frame, and now allowing user to resize
		setTitle("Keyword Customization");
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
	
	//This method adds all current keyword options to the main panel
	private void addKeywordOptions() {
		
		try {
			
			Scanner input = new Scanner(new File("commands/userCommands"));
			
			//Read the commands and current keywords and display them
			while(input.hasNextLine() && input.hasNext()) {
				
				//Create a keyword option based on the command and keyword
				KeywordOption keywordOption = new KeywordOption(input.next(), input.next());
				System.out.println(keywordOption.getFunction() +  " " + keywordOption.getKeyword());
				options.add(keywordOption); //Add option to the array list
				customizePanel.add(keywordOption.getCustomizePanel()); //Add panel to the main panel
				
			}
			
			input.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//This method displays all of the default keywords for each function in the keyword fields
	private void showDefaultSettings() {
		
		int index = 0;
		
		try {
			
			Scanner input = new Scanner(new File("commands/userDefaultCommands"));
			
			//Read the commands and current keywords and display them
			while(input.hasNextLine() && index < options.size() && input.hasNext()) {
				
				//Create a keyword option based on the command and keyword
				if(options.get(index).getFunction().equals(input.next())) {
					options.get(index).setKeyword(input.next());
					options.get(index).updateText();
				} else {
					input.next();
				}
				index++;
				
			}
			
			input.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//This method saves the current keyword settings that have been entered
	private void saveKeywords() {
		
		//Check that all text fields have been filled
		for(int i = 0; i < options.size(); i++) {
			
			//If a text field hasn't been filled, fill it with the default value
			if(options.get(i).getKeywordField().getText().equals("")) 
				options.get(i).getKeywordField().setText(getSavedKeyword(options.get(i).getFunction()));
			
		}
		
		//User commands file
		File file = new File("commands/userCommands");

		try {
			
			//Print to the file
			PrintWriter pr = new PrintWriter(file);
			
			//Loop through the options array list and print each new keyword to the file
			for(int i = 0; i < options.size(); i++) 
				pr.println(options.get(i).getFunction() + " " + options.get(i).getKeywordField().getText());
			
			pr.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		FileExecutionTool.updateCommands();
		
	}
	
	//This method returns the currently set keyword for a given function
	private String getSavedKeyword(String function) {
		
		//Keyword to be returned
		String keyword = "";
		
		try {
			
			//Read from the commands file
			Scanner input = new Scanner(new File("commands/userCommands"));
			
			//Read the commands and current keywords and until the correct
			while(input.hasNextLine()) {
				
				//Create a keyword option based on the command and keyword
				if(keyword.equals(input.next())) {
					keyword = input.next();
					break;
				} else {
					input.next();
				}
				
			}
			
			input.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Return the keyword
		return keyword;
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == save) { //Save the current settings
			saveKeywords();
		} else if(e.getSource() == defaultSettings) { //Display the default settings
			showDefaultSettings();
		} else if(e.getSource() == exit) {
			ide.setEnabled(true); //Enable the ide before closing
			this.dispose();
		}
		
	}

}
