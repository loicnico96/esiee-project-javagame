
package pkg_commands;

/**
 * Command enumeration. 
 * @author Loic
 */
public enum CommandEnum {
	CHARGE	("Charge"), 
	DROP	("Drop"), 
	GO		("Go"), 
	INSPECT	("Inspect"), 
	PORTAL	("Portal"), 
	QUIT	("Quit"), 
	SHOW	("Show"), 
	TALK	("Talk"), 
	TAKE	("Take"), 
	TEST	("Test"), 
	USE		("Use"); 
	
	/**
	 * Returns the CommandEnum corresponding to a string (ignoring case). 
	 * Returns null if no CommandEnum is found. 
	 */
	public static CommandEnum fromString (final String pName) {
		for (CommandEnum commandEnum : CommandEnum.values()) {
			if (commandEnum.getName().equalsIgnoreCase(pName))
				return commandEnum; 
		}
		return null; 
	}
	
	private String aName; 
	private CommandEnum (final String pName) {
		this.aName = pName; 
	}
	
	/**
	 * Returns the String corresponding to a CommandEnum. 
	 */
	public String getName () {
		return this.aName; 
	}
}
