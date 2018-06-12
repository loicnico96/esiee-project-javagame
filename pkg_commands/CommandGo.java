
package pkg_commands;

import pkg_engine.Direction;
import pkg_engine.Player;

/**
 * Command GO
 * This command allows the player to go in a direction. 
 * @author Loic
 */
public class CommandGo extends Command {
	/**
	 * Constructor
	 */
	public CommandGo (final String pParameter) {
		super (pParameter);
	}

	/**
	 * Executes the command. 
	 */
	@Override
	public void execute(final Player pPlayer) {
		if (hasParameter()) {
			String parameter = getParameter(); 
			
			if (parameter.equalsIgnoreCase("back")) { // Go back
				pPlayer.goBack(); 
				
			} else {
				Direction direction = Direction.fromString(parameter); 
				if (direction != null) { // Everything's alright! 
					pPlayer.goRoom(direction); 
					
				} else { // Invalid direction
					pPlayer.message(String.format("What does '%s' even mean? ", parameter)); 
				}
			}
			
		} else { // No parameter
			pPlayer.message("Go where?! "); 
		}
	}
}
