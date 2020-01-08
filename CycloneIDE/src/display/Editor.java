package display;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import utils.LineNumberComponent;
import utils.LineNumberModelImpl;

public class Editor extends Perspective {
	
	private static int orginX = (int) (Perspective.screenWidth/4 + 25);
	private static int orginY = 25;
	private static int width = (int) (Perspective.screenWidth/4*3 - 50);
	private static int height = (int) (Perspective.screenHeight/3*2 - 25);
	
	private JTextArea editorTextArea = new JTextArea();
	private LineNumberModelImpl lineNumberModel = new LineNumberModelImpl(editorTextArea);
	private LineNumberComponent lineNumberComponent = new LineNumberComponent(lineNumberModel);
	
	private JTabbedPane tabbedPane = new JTabbedPane();
	
	public Editor() {
		
		panelSetup(orginX, orginY, (int) Perspective.screenWidth, (int) Perspective.screenHeight, new Color(243, 243, 243));
		
		addJComponents();
		
	}
	
	public void addJComponents() {
		
		//Setup tabbed pane
		tabbedPane.setBounds(0, 0, width, height);
		add(tabbedPane);
		
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
		
		JScrollPane consoleTextAreaScroll = new JScrollPane(editorTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		consoleTextAreaScroll.setBounds(0 , 0, width, height);
		consoleTextAreaScroll.setRowHeaderView(lineNumberComponent);
		//consoleTextAreaScroll.setViewportView(editorTextArea);
		//add(consoleTextAreaScroll);
		
		tabbedPane.add(consoleTextAreaScroll);
		
	}
	
	//This method creates a new tab for a class file
	public void addTab(String projectName, String className) {
		
		
		
	}

	public JTextArea getEditorTextArea() {
		return editorTextArea;
	}

	public void setEditorTextArea(JTextArea editorTextArea) {
		this.editorTextArea = editorTextArea;
	}

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	public void setTabbedPane(JTabbedPane tabbedPane) {
		this.tabbedPane = tabbedPane;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
