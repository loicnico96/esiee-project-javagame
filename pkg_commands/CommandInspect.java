
package pkg_commands;

import pkg_engine.Item;
import pkg_engine.Player;

/**
 * Command INSPECT
 * This command allows the player to inspect a room or an item for more details. 
 * @author Loic
 */
public class CommandInspect extends Command {
	/**
	 * Constructor
	 */
	public CommandInspect (final String pParameter) {
		super (pParameter);
	}

	/**
	 * Executes the command. 
	 */
	@Override
	public void execute(Player pPlayer) {
		if (hasParameter()) {
			String parameter = getParameter(); 
			
			if (parameter.equalsIgnoreCase("room")) { // Inspect room
				pPlayer.inspectCurrentRoom(); 
				
			} else {
				Item item = Item.fromString(parameter); 
				if (item != null) { // Everything's alright! 
					pPlayer.inspectItem(item); 
					
				} else { // Invalid direction
					pPlayer.message(String.format("I don't even know what a '%s' is. ", parameter));
				}
			}
			
		} else { // No parameter
			pPlayer.message("Inspect what?! "); 
		}
	}
}
