package utils;

import javax.swing.JPopupMenu;

public class ProjectPopup extends JPopupMenu {
	
	private Project project;
	
	public ProjectPopup() {
		
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

}
