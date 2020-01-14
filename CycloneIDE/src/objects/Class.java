package objects;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import assets.Images;
import display.Perspective;
import display.ProjectExplorer;
import utils.FileExecutionTool;
import utils.FileInput;
import utils.LineNumberComponent;
import utils.LineNumberModelImpl;

public class Class extends JButton {
	
	private static int width = (int) (Perspective.screenWidth/4*3 - 50);
	private static int height = (int) (Perspective.screenHeight/3*2 - 25);
	
	private JTextArea editorTextArea = new JTextArea();
	private JScrollPane editorTextAreaScroll = new JScrollPane(editorTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	private LineNumberModelImpl lineNumberModel = new LineNumberModelImpl(editorTextArea);
	private LineNumberComponent lineNumberComponent = new LineNumberComponent(lineNumberModel);
	
	private String projectName;
	private String className; //Stores the name of the class
	
	private TabComponent tab; //Used in the editor tabbed pane
	
	private boolean edited;
	
	public Class(String projectName, String className) {
		
		this.projectName = projectName;
		this.className = className;
		edited = false;
		
		setText(className); //Set the button text
		setHorizontalAlignment(SwingConstants.LEFT);
		tab = new TabComponent(className);
		
		addJComponents();
		setupText();
		
		
		addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent e) {
				
				if(e.getButton() == MouseEvent.BUTTON3) {
					//Create the pop up menu
					ProjectExplorer.classPopup(Class.this);
					
				}
				
			}
			
		});
		
	}
	
	public void addJComponents() {
		
		setIcon(Images.classImage);
		setBackground(Color.white);
		setOpaque(false);
		setContentAreaFilled(false);
		setBorderPainted(false);
		
		editorTextArea.setFont(new Font("serif", Font.PLAIN, 22));
		editorTextArea.setBounds(0, 0, width, height);
		editorTextArea.setLineWrap(true);
		editorTextArea.setWrapStyleWord(true);
		editorTextArea.setTabSize(2);
		editorTextArea.addKeyListener(new KeyListener() {            
			
			@Override
	        public void keyPressed(KeyEvent e) {
				
	            if(e.getKeyCode() == KeyEvent.VK_DOWN) {
	            	int currentPosition = editorTextArea.getCaretPosition();
	            	editorTextArea.insert("\n", editorTextArea.getCaretPosition());
	            	
	            	int requiredTabs = FileExecutionTool.insertTabs(editorTextArea.getText().substring(0, editorTextArea.getCaretPosition()));
	            	
	            	for(int i = 0; i < requiredTabs; i++) {
	            		
	            		editorTextArea.insert("\t", editorTextArea.getCaretPosition());
	            		
	            	}
	            	
	            	editorTextArea.setCaretPosition(currentPosition);
	            	
	            }
	            
	        }
	            
			@Override
			public void keyTyped(KeyEvent e) {
	            
	        }

	        @Override
	        public void keyReleased(KeyEvent e) {
	            // TODO Auto-generated method stub      
	        }
	        
	    });
		
		editorTextArea.getDocument().addDocumentListener(new DocumentListener(){

			@Override
			public void changedUpdate(DocumentEvent arg0) {

				lineNumberComponent.adjustWidth();
				setEdited(true);

			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {

				lineNumberComponent.adjustWidth();
				setEdited(true);
				
			}
			
			@Override
			public void removeUpdate(DocumentEvent arg0) {

				lineNumberComponent.adjustWidth();
				setEdited(true);

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
		setEdited(false);
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

	public boolean isEdited() {
		return edited;
	}

	public void setEdited(boolean edited) {
		this.edited = edited;
		if(edited)
			tab.showEdited();
		else
			tab.showSaved();
	}
	
}
