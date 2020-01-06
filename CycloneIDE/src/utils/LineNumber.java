package utils;

import javax.swing.JFrame;

import javax.swing.JScrollPane;

import javax.swing.JTextArea;

import javax.swing.event.DocumentEvent;

import javax.swing.event.DocumentListener;

/**

 * Demonstration of the line numbering framework in a JTextArea
 * @author Greg Cope
 *
 */

public class LineNumber {

	private JTextArea textArea = new JTextArea();

	private LineNumberModelImpl lineNumberModel = new LineNumberModelImpl(textArea);
	private LineNumberComponent lineNumberComponent = new LineNumberComponent(lineNumberModel);

	public LineNumber(){

		JFrame frame = new JFrame();

		JScrollPane scroller = new JScrollPane(textArea);

		scroller.setRowHeaderView(lineNumberComponent);

		lineNumberComponent.setAlignment(LineNumberComponent.CENTER_ALIGNMENT);

		textArea.getDocument().addDocumentListener(new DocumentListener(){

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

		textArea.setText("This is a demonstration of...\n...line numbering using a JText area within...\n...a JScrollPane");

		frame.setVisible(true);

	}

}
