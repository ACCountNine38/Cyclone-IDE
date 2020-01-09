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
	
	private void setupLabel() {
		titleLabel.setText(title);
		titleLabel.setOpaque(true);
		add(titleLabel);
	}
	
	private void setupButton() {
		//default JButton font is Dialog.bold 12
//		closeButton.setBorder(null);
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
