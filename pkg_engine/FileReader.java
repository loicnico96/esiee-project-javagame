
package pkg_engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Utility class with static methods for reading files. 
 * @author Loic
 */
public abstract class FileReader {

	/**
	 * Extracts lines from a file as an array of strings. 
	 * Empty lines are automatically discarded. 
	 * @param pFilename Filename
	 * @throws FileNotFoundException
	 */
	public static String[] getLines (final String pFilename) throws FileNotFoundException {
		File file = new File (pFilename); 
		Scanner scanner = new Scanner (file); 
		
		ArrayList<String> lines = new ArrayList<String> (); 
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine(); 
			if (!line.isEmpty())
				lines.add(line); 
		}
		scanner.close(); 
		
		return lines.toArray(new String [0]); 
	}
}
