
package pkg_engine; 

/**
 * Color enumerations for switches and doors. 
 * @author Loic
 */
public enum Color {
	RED		("red"), 
	BLUE	("blue"), 
	GREEN	("green"), 
	YELLOW	("yellow"), 
	NONE	("none"); 
	
	private String aName; 
	private Color (final String pName) {
		this.aName = pName; 
	}
	
	/**
	 * Returns the String corresponding to a Color. 
	 */
	public String getName () {
		return this.aName; 
	}
	
	/**
	 * Returns the Color corresponding to a string (ignoring case). 
	 * Returns null if no Color is found. 
	 */
	public static Color fromString (final String pName) {
		for (Color color : Color.values()) {
			if (color.getName().equalsIgnoreCase(pName))
				return color; 
		}
		return null; 
	}
}

