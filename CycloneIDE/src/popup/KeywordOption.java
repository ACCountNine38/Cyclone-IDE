package popup;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class KeywordOption {
	
	public static final int WIDTH = DisplayPopups.POPUP_WIDTH / 2 - 50;
	public static final int HEIGHT = 50;
	
	private JPanel customizePanel = new JPanel();
	private JLabel functionLabel = new JLabel();
	private JTextField keywordField = new JTextField();
	
	private String function;
	private String keyword;
	
	public KeywordOption(String function, String keyword) {
		
		this.function = function;
		this.keyword = keyword;
		
		setupJComponents();
		
	}
	
	private void setupJComponents() {
		
		customizePanel.setLayout(null);
		customizePanel.setSize(WIDTH * 2, HEIGHT);
		customizePanel.setPreferredSize(customizePanel.getSize());
		customizePanel.setMaximumSize(customizePanel.getSize());
		customizePanel.setMinimumSize(customizePanel.getSize());
		
		functionLabel.setText(function);
		functionLabel.setFont(new Font("Dialog", Font.BOLD, 24));
		functionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		functionLabel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		functionLabel.setBounds(0, 0, WIDTH, HEIGHT);
		functionLabel.setPreferredSize(functionLabel.getSize());
		functionLabel.setMaximumSize(functionLabel.getSize());
		functionLabel.setMinimumSize(functionLabel.getSize());
		
		keywordField.setText(keyword);
		keywordField.setFont(new Font("Dialog", Font.BOLD, 24));
		keywordField.setHorizontalAlignment(SwingConstants.CENTER);
		keywordField.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		keywordField.setBounds(WIDTH, 0, WIDTH, HEIGHT);
		keywordField.setPreferredSize(keywordField.getSize());
		keywordField.setMaximumSize(keywordField.getSize());
		keywordField.setMinimumSize(keywordField.getSize());
		
		customizePanel.add(functionLabel);
		customizePanel.add(keywordField);
		
	}
	
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
