package popup;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
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

public class KeywordCustomizationPopup extends JFrame implements DisplayPopups, ActionListener {
	
	private static final int PANEL_WIDTH = DisplayPopups.POPUP_WIDTH - 100;
	private static final int PANEL_HEIGHT = DisplayPopups.POPUP_HEIGHT - 200;
	
	private JLabel titleLabel = new JLabel("Key Customization");
	private JButton defaultSettings = new JButton("SET TO DEFAULT");
	private JButton save = new JButton("SAVE CHANGES");
	private JButton exit = new JButton("EXIT");
	
	private JPanel customizePanel = new JPanel(); //Main panel
	private JScrollPane scroll;
	
	private ArrayList<KeywordOption> options = new ArrayList<KeywordOption>();
	
	private IDEInterface ide;
	
	public KeywordCustomizationPopup(IDEInterface ide) {
		
		this.ide = ide;
		
		addJComponents();
		frameSetup();
		addKeywordOptions();
		
		addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent arg0) {
				ide.setEnabled(true); //Enable the ide before closing
			}
			
		});
		
	}
	
	@Override
	public void addJComponents() {
		
		//set up the main panel
		customizePanel.setLayout(new BoxLayout(customizePanel, BoxLayout.Y_AXIS));
		scroll = new JScrollPane(customizePanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setBounds(50, 75, PANEL_WIDTH, PANEL_HEIGHT);
		scroll.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		add(scroll);
		
		//add the title labels for each column
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(null);
		titlePanel.setSize(KeywordOption.WIDTH * 2, KeywordOption.HEIGHT);
		titlePanel.setPreferredSize(titlePanel.getSize());
		titlePanel.setMaximumSize(titlePanel.getSize());
		titlePanel.setMinimumSize(titlePanel.getSize());
		
		JLabel functionTitleLabel = new JLabel("<html><center><font color='black'>Existing Keywords/Functions</font></center></html>");
		functionTitleLabel.setFont(new Font("Dialog", Font.BOLD, 24));
		functionTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		functionTitleLabel.setBounds(0, 0, KeywordOption.WIDTH, KeywordOption.HEIGHT);
		functionTitleLabel.setPreferredSize(functionTitleLabel.getSize());
		functionTitleLabel.setMaximumSize(functionTitleLabel.getSize());
		functionTitleLabel.setMinimumSize(functionTitleLabel.getSize());
		functionTitleLabel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		titlePanel.add(functionTitleLabel);
		
		JLabel keywordTitleLabel = new JLabel("<html><center><font color='black'>User Defined Keywords/Functions</font></center></html>");
		keywordTitleLabel.setFont(new Font("Dialog", Font.BOLD, 24));
		keywordTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		keywordTitleLabel.setBounds(KeywordOption.WIDTH, 0, KeywordOption.WIDTH, KeywordOption.HEIGHT);
		keywordTitleLabel.setPreferredSize(keywordTitleLabel.getSize());
		keywordTitleLabel.setMaximumSize(keywordTitleLabel.getSize());
		keywordTitleLabel.setMinimumSize(keywordTitleLabel.getSize());
		keywordTitleLabel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		titlePanel.add(keywordTitleLabel);
		
		customizePanel.add(titlePanel);
		
		//Add a title label to the frame
		titleLabel.setFont(new Font("Dialog", Font.BOLD, 36));
		titleLabel.setBounds(50, 0, PANEL_WIDTH, 75);
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(titleLabel);
		
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
	
	private void addKeywordOptions() {
		
		try {
			Scanner input = new Scanner(new File("commands/userCommands"));
			
			while(input.hasNextLine()) {
				
				KeywordOption keywordOption = new KeywordOption(input.next(), input.next() + ":");
				options.add(keywordOption);
				customizePanel.add(keywordOption.getCustomizePanel());
				
			}
			
			input.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//KeywordOption option = new KeywordOption();
		
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
