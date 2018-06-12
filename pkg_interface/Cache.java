
package pkg_interface; 

import java.net.URL; 
import java.util.HashMap; 
import javax.swing.ImageIcon;

/**
 * Class for loading and storing images. 
 * @author Loic
 */
public class Cache {
    private HashMap<String, ImageIcon>	aIcons; 
    private String 						aDirectory; 
    
    /**
     * Constructor
     * @param pDirectory Default directory for file access
     */
    public Cache (final String pDirectory) {
    	aIcons = new HashMap<String, ImageIcon> (); 
    	aDirectory = pDirectory; 
    }
    
	/**
	 * Loads and stores an image. 
	 * If the image is already loaded, it is directly returned. 
	 * If the image does not exist, returns an empty image and displays an error message. 
	 */
	public ImageIcon getImage (final String pFilename) {
		ImageIcon icon = aIcons.get(pFilename); 
		if (icon == null) {
			URL url = getClass().getClassLoader().getResource(aDirectory + pFilename); 
			if (url != null) {
				icon = new ImageIcon (url); 
			} else { // File not found
				System.out.println("File not found: " + aDirectory + pFilename); 
				icon = new ImageIcon (); 
			}
			aIcons.put(pFilename, icon); 
		}
		return icon; 
	}
}

