package popup;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class KeywordOption {
	
	//Panel dimensions
	public static final int WIDTH = DisplayPopups.POPUP_WIDTH / 2 - 50;
	public static final int HEIGHT = 50;
	
	//Main panel and components
	private JPanel customizePanel = new JPanel();
	private JLabel functionLabel = new JLabel();
	private JTextField keywordField = new JTextField();
	
	//Keyword and function
	private String function;
	private String keyword;
	
	//Constructor
	public KeywordOption(String function, String keyword) {
		
		this.function = function;
		this.keyword = keyword;
		
		setupJComponents();
		
	}
	
	//This method sets up the 
	private void setupJComponents() {
		
		//Set up the main panel
		customizePanel.setLayout(null);
		customizePanel.setSize(WIDTH * 2, HEIGHT);
		customizePanel.setPreferredSize(customizePanel.getSize());
		customizePanel.setMaximumSize(customizePanel.getSize());
		customizePanel.setMinimumSize(customizePanel.getSize());
		
		//Set up the function label
		functionLabel.setText(function);
		functionLabel.setFont(new Font("Dialog", Font.BOLD, 24));
		functionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		functionLabel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		functionLabel.setBounds(0, 0, WIDTH, HEIGHT);
		functionLabel.setPreferredSize(functionLabel.getSize());
		functionLabel.setMaximumSize(functionLabel.getSize());
		functionLabel.setMinimumSize(functionLabel.getSize());
		
		//Set up the text field
		keywordField.setText(keyword);
		keywordField.setFont(new Font("Dialog", Font.BOLD, 24));
		keywordField.setHorizontalAlignment(SwingConstants.CENTER);
		keywordField.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		keywordField.setBounds(WIDTH, 0, WIDTH, HEIGHT);
		keywordField.setPreferredSize(keywordField.getSize());
		keywordField.setMaximumSize(keywordField.getSize());
		keywordField.setMinimumSize(keywordField.getSize());
		
		//Only allow the user to enter letters in the keyword field
		//SOURCE: https://stackoverflow.com/questions/34377607/how-to-make-jtextfield-only-accept-characters-in-netbeans
		keywordField.addKeyListener(new KeyAdapter() {
	        public void keyTyped(KeyEvent evt) {
	         if(!(Character.isLetter(evt.getKeyChar()))){
	                evt.consume();
	            }
	        }
	    });
		
		//Add the components to the panel
		customizePanel.add(functionLabel);
		customizePanel.add(keywordField);
		
	}
	
	//This method updates the text of the function label and keyword field
	public void updateText() {
		functionLabel.setText(function);
		keywordField.setText(keyword);
	}
	
	//Getters and setters
	public JPanel getCustomizePanel() {
		return customizePanel;
	}

	public void setCustomizePanel(JPanel customizePanel) {
		this.customizePanel = customizePanel;
	}

	public JLabel getFunctionLabel() {
		return functionLabel;
	}

	public void setFunctionLabel(JLabel functionLabel) {
		this.functionLabel = functionLabel;
	}

	public JTextField getKeywordField() {
		return keywordField;
	}

	public void setKeywordField(JTextField keywordField) {
		this.keywordField = keywordField;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
}
