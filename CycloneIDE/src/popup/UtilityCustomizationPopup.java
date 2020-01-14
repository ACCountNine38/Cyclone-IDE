package popup;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import display.IDEInterface;

public class UtilityCustomizationPopup extends JFrame implements DisplayPopups, ActionListener {
	
	private static final int WIDTH = DisplayPopups.POPUP_WIDTH - 100;
	private static final int HEIGHT = DisplayPopups.POPUP_HEIGHT - 200;
	
	private static final int PANEL_HEIGHT = HEIGHT / 2 - 10;
	
	private JLabel titleLabel = new JLabel("Utility Customization");
	private JButton defaultSettings = new JButton("SET TO DEFAULT");
	private JButton save = new JButton("SAVE CHANGES");
	private JButton exit = new JButton("EXIT");
	
	private JPanel editorPanel = new JPanel(); //Main panel
	private JLabel editorTitleLabel = new JLabel("Editor Settings");
	private JLabel editorFontLabel = new JLabel("Editor Font");
	private JComboBox<String> editorFontComboBox = new JComboBox<String>();
	private JLabel editorFontSizeLabel = new JLabel("Editor Font Size");
	private JTextField editorFontSizeField = new JTextField();
	private JLabel editorIndentSpaceLabel = new JLabel("Editor Indent Space Size");
	private JTextField editorIndentSpaceField = new JTextField();
	
	private JPanel consolePanel = new JPanel();
	private JLabel consoleTitleLabel = new JLabel("Console Settings");
	private JLabel consoleFontLabel = new JLabel("Console Font");
	private JComboBox<String> consoleFontComboBox = new JComboBox<String>();
	private JLabel consoleFontSizeLabel = new JLabel("Console Font Size");
	private JTextField consoleFontSizeField = new JTextField();
	private JLabel consoleIndentSpaceLabel = new JLabel("Console Indent Space Size");
	private JTextField consoleIndentSpaceField = new JTextField();
	
	private JLabel darkThemeLabel = new JLabel("Dark Theme:");
	private JRadioButton onButton = new JRadioButton("On");
	private JRadioButton offButton = new JRadioButton("Off");
	private ButtonGroup radioButtonGroup = new ButtonGroup();
	
	private IDEInterface ide;
	
	public UtilityCustomizationPopup(IDEInterface ide) {
		
		this.ide = ide;
		
		addJComponents();
		frameSetup();
		addFontOptions();
		
		addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent arg0) {
				ide.setEnabled(true); //Enable the ide before closing
			}
			
		});
		
	}
	
	@Override
	public void addJComponents() {
		
		setupEditorPanel();
		setupConsolePanel();
		
		//Add a title label to the frame
		titleLabel.setFont(new Font("Dialog", Font.BOLD, 36));
		titleLabel.setBounds(50, 0, WIDTH, 75);
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(titleLabel);
		
		//set up the dark theme label and radio buttons
		darkThemeLabel.setFont(new Font("Dialog", Font.BOLD, 24));
		darkThemeLabel.setBounds(50, 75 + HEIGHT + 10, 150, 75);
		darkThemeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		//darkThemeLabel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		add(darkThemeLabel);
		
		radioButtonGroup.add(onButton);
		radioButtonGroup.add(offButton);
		
		onButton.setBounds(220, 75 + HEIGHT + 10, 80, 75);
		onButton.setFont(new Font("Dialog", Font.BOLD, 24));
		onButton.addActionListener(this);
		//onButton.setSelected(true);
		add(onButton);
		
		offButton.setBounds(300, 75 + HEIGHT + 10, 80, 75);
		offButton.setFont(new Font("Dialog", Font.BOLD, 24));
		offButton.addActionListener(this);
		//offButton.setSelected(true);
		add(offButton);
		
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
	
	//This method sets put he editor panel
	private void setupEditorPanel() {
		
		//set up the editor panel
		editorPanel.setLayout(null);
		editorPanel.setBounds(50, 75, WIDTH, PANEL_HEIGHT); 
		editorPanel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		
		//set up the panel title label
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
		
		editorIndentSpaceField.setFont(new Font("Dialog", Font.BOLD, 24));
		editorIndentSpaceField.setHorizontalAlignment(SwingConstants.CENTER);
		editorIndentSpaceField.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		editorIndentSpaceField.setBounds(KeywordOption.WIDTH, PANEL_HEIGHT / 4 * 3, KeywordOption.WIDTH, PANEL_HEIGHT / 4);
		editorPanel.add(editorIndentSpaceField);
		
	}
	
	//This method sets up the console panel
	private void setupConsolePanel() {
		
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
		
		consoleIndentSpaceField.setFont(new Font("Dialog", Font.BOLD, 24));
		consoleIndentSpaceField.setHorizontalAlignment(SwingConstants.CENTER);
		consoleIndentSpaceField.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		consoleIndentSpaceField.setBounds(KeywordOption.WIDTH, PANEL_HEIGHT / 4 * 3, KeywordOption.WIDTH, PANEL_HEIGHT / 4);
		consolePanel.add(consoleIndentSpaceField);
		
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
		
		editorFontComboBox.addItem("Consolas");
		editorFontComboBox.addItem("Monospaced");
		editorFontComboBox.addItem("Serif");
		editorFontComboBox.addItem("SimSun");
		editorFontComboBox.addItem("Lucida Console");
		editorFontComboBox.addItem("Letter Gothic Std");
		
		consoleFontComboBox.addItem("Consolas");
		consoleFontComboBox.addItem("Monospaced");
		consoleFontComboBox.addItem("Serif");
		consoleFontComboBox.addItem("SimSun");
		consoleFontComboBox.addItem("Lucida Console");
		consoleFontComboBox.addItem("Letter Gothic Std");
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getSource() == exit) {
			ide.setEnabled(true); //Enable the ide before closing
			this.dispose();
		}
		
	}

}
