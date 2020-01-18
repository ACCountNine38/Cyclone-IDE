package objects;

import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

//This class is used for creating tab components to be used in the tab pane of the editor
public class TabComponent extends JPanel {
	
	//Panel components
	private JLabel titleLabel = new JLabel();
	private JButton closeButton = new JButton("x");
	
	private String title; //Class name
	
	//Constructor method
	public TabComponent(String title) {
		
		this.title = title;
		
		//Set the panel layout
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		//Set up the label and button
		setupLabel();
		setupButton();
		
	}
	
	//Set up the title label
	private void setupLabel() {
		titleLabel.setText(title);
		titleLabel.setOpaque(true);
		add(titleLabel);
	}
	
	//Set up the button used to close the tab
	private void setupButton() {
		closeButton.setMargin(new Insets(0,0,0,0));
		closeButton.setSize(new Dimension(17, 17));
		closeButton.setPreferredSize(closeButton.getSize());
		closeButton.setMaximumSize(closeButton.getSize());
		closeButton.setMinimumSize(closeButton.getSize());
		add(closeButton);
	}
	
	//This method adds an asterisk to the label to show that the class has been edited
	public void showEdited() {
		titleLabel.setText("*" + title);
	}
	
	//This method removes an asterisk from the label to show that the class has been saved
	public void showSaved() {
		titleLabel.setText(title);
	}
	
	//Getters and setters
	public JLabel getTitleLabel() {
		return titleLabel;
	}

	public void setTitleLabel(JLabel titleLabel) {
		this.titleLabel = titleLabel;
	}

	public JButton getCloseButton() {
		return closeButton;
	}

	public void setCloseButton(JButton closeButton) {
		this.closeButton = closeButton;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
}
