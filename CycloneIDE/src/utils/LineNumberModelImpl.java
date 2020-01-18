package utils;

import java.awt.Rectangle;

import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;

// SOURCE: https://www.algosome.com/articles/line-numbers-java-jtextarea-jtable.html
public class LineNumberModelImpl implements LineNumberModel {

	private JTextArea textArea; //Text area

	//Constructor Method
	public LineNumberModelImpl(JTextArea textArea) {

		this.textArea = textArea;

	}
	
	//This method returns the line count
	@Override
	public int getNumberLines() {

		return textArea.getLineCount();

	}

	@Override
	public Rectangle getLineRect(int line) {

		try {

			return textArea.modelToView(textArea.getLineStartOffset(line));

		} catch (BadLocationException e) {

			e.printStackTrace();

			return new Rectangle();

		}

	}

}
