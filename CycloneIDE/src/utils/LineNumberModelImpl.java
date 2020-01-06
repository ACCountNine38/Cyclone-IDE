package utils;

import java.awt.Rectangle;

import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;

public class LineNumberModelImpl implements LineNumberModel {

	private JTextArea textArea;

	public LineNumberModelImpl(JTextArea textArea) {

		this.textArea = textArea;

	}

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
