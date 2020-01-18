package objects;

import javax.swing.JPopupMenu;

//This class is used to create popup menus when a class in the project explorer is is right clicked
public class ClassPopup extends JPopupMenu {
	
	//Selected class that has been right clicked
	private Class selectedClass; 
	
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
