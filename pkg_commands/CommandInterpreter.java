
package pkg_commands;

import java.util.StringTokenizer;

/**
 * Class that interprets input lines as commands. 
 * @author Loic
 */
public class CommandInterpreter {
	private boolean aTestMode; 
	
	/**
	 * Constructor
	 */
	public CommandInterpreter () {
		aTestMode = false; 
	}
	
	/**
	 * Checks whether the interpreter is in test mode. 
	 */
	public boolean isTestMode () {
		return aTestMode; 
	}
	
	/**
	 * Sets the interpreter's mode. 
	 */
	public void setTestMode (final boolean pMode) {
		aTestMode = pMode; 
	}
	
	/**
	 * Interprets a command line as a Command
	 * @param pCommandLine Command line
	 */
	public Command interpretCommand (final String pCommandLine) {
		StringTokenizer tokenizer = new StringTokenizer (pCommandLine); 
		String commandWord	= ""; 
		String parameter	= ""; 
		
		// Extracting command word
		if (tokenizer.hasMoreTokens()) {
			commandWord = tokenizer.nextToken(); 
			
			// Extracting parameter
			if (tokenizer.hasMoreTokens()) {
				parameter = tokenizer.nextToken(); 
				while (tokenizer.hasMoreTokens()) {
					parameter += " " + tokenizer.nextToken(); 
				}
			}
		}
		
		// Creating corresponding Command object
		CommandEnum commandEnum = CommandEnum.fromString(commandWord); 
		if (commandEnum == null)
			return null; 
		
		switch (commandEnum) {
			case CHARGE:
				return new CommandCharge	(parameter); 
			case DROP:
				return new CommandDrop		(parameter); 
			case GO:
				return new CommandGo		(parameter); 
			case INSPECT:
				return new CommandInspect	(parameter); 
			case PORTAL:
				return new CommandPortal	(parameter); 
			case QUIT:
				return new CommandQuit		(parameter); 
			case SHOW:
				return new CommandShow		(parameter); 
			case TALK:
				return new CommandTalk		(parameter); 
			case TAKE:
				return new CommandTake		(parameter); 
			case TEST:
				return new CommandTest		(parameter); 
			case USE:
				return new CommandUse		(parameter); 
			default:
				return null; 
		}
	}
}
