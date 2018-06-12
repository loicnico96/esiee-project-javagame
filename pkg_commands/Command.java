
package pkg_commands;

import pkg_engine.Player;

/**
 * Superclass for all commands. 
 * @author Loic
 */
public abstract class Command {
	private String aParameter; 
	
	/**
	 * Constructor
	 * @param pParameter	Parameter of the command
	 */
	public Command (final String pParameter) {
		aParameter = pParameter; 
	}
	
	/**
	 * Checks whether the command has a parameter. 
	 */
	public boolean hasParameter () {
		return !aParameter.isEmpty(); 
	}
	
	/**
	 * Returns the command's parameter. 
	 */
	public String getParameter () {
		return aParameter; 
	}
	
	/**
	 * Executes the command. 
	 */
	public abstract void execute (final Player pPlayer); 
}
