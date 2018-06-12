
package pkg_commands;

import pkg_engine.Item;
import pkg_engine.Player;

/**
 * Command TAKE
 * This command allows the player to place an Item in their inventory. 
 * @author Loic
 */
public class CommandTake extends Command {
	/**
	 * Constructor
	 */
	public CommandTake (final String pParameter) {
		super (pParameter);
	}

	/**
	 * Executes the command. 
	 */
	@Override
	public void execute(Player pPlayer) {
		if (hasParameter()) {
			String parameter = getParameter(); 
			Item item = Item.fromString(parameter); 

			if (parameter.equals("player")) {
				pPlayer.message("Are you crazy? I can't take myself! "); 
				
			} else if (item != null) { // Everything's alright! 
				pPlayer.takeItem(item); 
				
			} else { // Invalid parameter
				pPlayer.message(String.format("I don't even know what a '%s' is. ", parameter)); 
			}
			
		} else { // No parameter
			pPlayer.message("Take what?! "); 
		}
	}
}
