package objects;

import javax.swing.JPopupMenu;

//This class is used to create popup menus when a class is is right clicked
public class ClassPopup extends JPopupMenu {
	
	private Class selectedClass; //Selected class
	
	//Constructor method
	public ClassPopup() {
	}
	
	//Getter and Setter
	public Class getSelectedClass() {
		return selectedClass;
	}

	public void setSelectedClass(Class selectedClass) {
		this.selectedClass = selectedClass;
	}

}
