package utils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicButtonUI;

import display.Perspective;

public class Class extends JButton {
	
	private static int width = (int) (Perspective.screenWidth/4*3 - 50);
	private static int height = (int) (Perspective.screenHeight/3*2 - 25);
	
	private JTextArea editorTextArea = new JTextArea();
	private JScrollPane editorTextAreaScroll = new JScrollPane(editorTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	private LineNumberModelImpl lineNumberModel = new LineNumberModelImpl(editorTextArea);
	private LineNumberComponent lineNumberComponent = new LineNumberComponent(lineNumberModel);
	
	private String projectName;
	private String className; //Stores the name of the class
	
	private TabComponent tab;
	
	public Class(String projectName, String className) {
		
		this.projectName = projectName;
		this.className = className;
		
		setText(className); //Set the button text
		
		addJComponents();
		setupText();
		
		tab = new TabComponent(className);
		
	}
	
	public void addJComponents() {
		
		editorTextArea.setFont(new Font("serif", Font.PLAIN, 22));
		editorTextArea.setBounds(0, 0, width, height);
		editorTextArea.setLineWrap(true);
		editorTextArea.setWrapStyleWord(true);
		editorTextArea.getDocument().addDocumentListener(new DocumentListener(){

			@Override
			public void changedUpdate(DocumentEvent arg0) {

				lineNumberComponent.adjustWidth();

			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {

				lineNumberComponent.adjustWidth();

			}
			
			@Override
			public void removeUpdate(DocumentEvent arg0) {

				lineNumberComponent.adjustWidth();

			}

		});
		
		//JScrollPane editorTextAreaScroll = new JScrollPane(editorTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		editorTextAreaScroll.setBounds(0 , 0, width, height);
		editorTextAreaScroll.setRowHeaderView(lineNumberComponent);
		//consoleTextAreaScroll.setViewportView(editorTextArea);
		//add(consoleTextAreaScroll);
		
	}
	
	public void setTabText(String fileText) {
		editorTextArea.setText(fileText);
	}
	
	public void setupText() {
		editorTextArea.setText(FileInput.loadFileAsString(String.format("projects/%s/%s", projectName, className)));
	}
	
	//Getters and Setters
	public JTextArea getEditorTextArea() {
		return editorTextArea;
	}

	public void setEditorTextArea(JTextArea editorTextArea) {
		this.editorTextArea = editorTextArea;
	}

	public JScrollPane getEditorTextAreaScroll() {
		return editorTextAreaScroll;
	}

	public void setEditorTextAreaScroll(JScrollPane editorTextAreaScroll) {
		this.editorTextAreaScroll = editorTextAreaScroll;
	}

	public LineNumberModelImpl getLineNumberModel() {
		return lineNumberModel;
	}

	public void setLineNumberModel(LineNumberModelImpl lineNumberModel) {
		this.lineNumberModel = lineNumberModel;
	}

	public LineNumberComponent getLineNumberComponent() {
		return lineNumberComponent;
	}

	public void setLineNumberComponent(LineNumberComponent lineNumberComponent) {
		this.lineNumberComponent = lineNumberComponent;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public TabComponent getTab() {
		return tab;
	}

	public void setTab(TabComponent tab) {
		this.tab = tab;
	}
	
}
