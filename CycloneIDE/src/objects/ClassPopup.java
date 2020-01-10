package objects;

import javax.swing.JPopupMenu;

public class ClassPopup extends JPopupMenu {
	
	private Class selectedClass;
	
	public Class getSelectedClass() {
		return selectedClass;
	}

	public void setSelectedClass(Class selectedClass) {
		this.selectedClass = selectedClass;
	}

	public ClassPopup() {
		
	}

}
