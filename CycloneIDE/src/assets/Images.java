package assets;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Images {
	
	public static ImageIcon folderImage;
	public static ImageIcon classImage;
	
	public Images() {
		
		try {
			
			folderImage = new ImageIcon(new ImageIcon(ImageIO.read(Images.class.getResource("/folder.png"))).getImage().getScaledInstance(40, 40, 0));
			classImage = new ImageIcon(new ImageIcon(ImageIO.read(Images.class.getResource("/class.png"))).getImage().getScaledInstance(40, 40, 0));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
