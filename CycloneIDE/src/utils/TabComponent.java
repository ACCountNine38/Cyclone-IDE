package utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TabComponent extends JPanel {
	
	private JLabel titleLabel = new JLabel();
	private JButton closeButton = new JButton("x");
	
	private String title; //Class name
	
	public TabComponent(String title) {
		
		this.title = title;
		
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		setupLabel();
		setupButton();
		
	}
	
	//Set up the title label
	private void setupLabel() {
		titleLabel.setText(title);
		titleLabel.setOpaque(true);
		add(titleLabel);
	}
	
	//Set up the title button
	private void setupButton() {
		closeButton.setMargin(new Insets(0,0,0,0));
		closeButton.setSize(new Dimension(17, 17));
		closeButton.setPreferredSize(closeButton.getSize());
		closeButton.setMaximumSize(closeButton.getSize());
		closeButton.setMinimumSize(closeButton.getSize());
		add(closeButton);
	}

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
