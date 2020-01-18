package objects;

import javax.swing.JPopupMenu;

//This class is used to create popup menus when a class in the project explorer is is right clicked
public class ProjectPopup extends JPopupMenu {
	
	//Project that has been right clicked
	private Project project;
	
	//Constructor method
	public ProjectPopup() {
		
	}
	
	//Getter and setter
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

}
