
package pkg_engine; 

/**
 * Direction enumerations for doors. 
 * @author Loic
 */
public enum Direction {
	NORTH	("north"), 
	EAST	("east"), 
	SOUTH	("south"), 
	WEST	("west"); 
	
	/**
	 * Returns the Direction corresponding to a string (ignoring case). 
	 * Returns null if no Direction is found. 
	 */
	public static Direction fromString (final String pName) {
		for (Direction direction : Direction.values()) {
			if (direction.getName().equalsIgnoreCase(pName))
				return direction; 
		}
		return null; 
	}
	
	private String aName; 
	private Direction (final String pName) {
		this.aName = pName; 
	}
	
	/**
	 * Returns the String corresponding to a Direction. 
	 */
	public String getName () {
		return this.aName; 
	}

	/**
	 * Returns the opposite direction. 
	 */
	public Direction opposite () {
		switch (this) {
			case NORTH:
				return SOUTH; 
			case EAST:
				return WEST; 
			case SOUTH:
				return NORTH; 
			default:
				return EAST; 
		}
	}
}

