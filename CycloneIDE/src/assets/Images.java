package assets;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/*
 * class that stores all the images used for the program
 * all images can be accessed statically
 */
public class Images {
	
	// images used in the project
	public static ImageIcon folderImage;
	public static ImageIcon classImage;
	
	// constructor that initializes all the images
	public Images() {
		
		// read all the images and see if it exist
		try {
			
			folderImage = new ImageIcon(new ImageIcon(ImageIO.read(Images.class.getResource("/folder.png"))).getImage().getScaledInstance(40, 40, 0));
			classImage = new ImageIcon(new ImageIcon(ImageIO.read(Images.class.getResource("/class.png"))).getImage().getScaledInstance(40, 40, 0));
			
		} catch (IOException error) {
			
			error.printStackTrace();
			
		}
		
	}

}
