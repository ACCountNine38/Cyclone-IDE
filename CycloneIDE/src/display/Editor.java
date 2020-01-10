package display;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.beans.EventHandler;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.TabSet;
import javax.swing.text.TabStop;

import utils.LineNumberComponent;
import utils.LineNumberModelImpl;

public class Editor extends Perspective {
	
	private static int orginX = (int) (Perspective.screenWidth/4 + 25);
	private static int orginY = 25;
	private static int width = (int) (Perspective.screenWidth/4*3 - 50);
	private static int height = (int) (Perspective.screenHeight/3*2 - 25);
	
	public static JTextArea editorTextArea = new JTextArea();
	private LineNumberModelImpl lineNumberModel = new LineNumberModelImpl(editorTextArea);
	private LineNumberComponent lineNumberComponent = new LineNumberComponent(lineNumberModel);
	
	public Editor() {
		
		panelSetup(orginX, orginY, (int) Perspective.screenWidth, (int) Perspective.screenHeight, new Color(243, 243, 243));
		
		addJComponents();
		
	}
	
	public void addJComponents() {
		
		editorTextArea.setFont(new Font("serif", Font.PLAIN, 22));
		editorTextArea.setBounds(0, 0, width, height);
		editorTextArea.setLineWrap(true);
		editorTextArea.setWrapStyleWord(true);
		editorTextArea.setTabSize(2);
		
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
		
		JScrollPane editorTextAreaScroll = new JScrollPane(editorTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		editorTextAreaScroll.setBounds(0 , 0, width, height);
		editorTextAreaScroll.setRowHeaderView(lineNumberComponent);
		add(editorTextAreaScroll);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
