
package pkg_commands;

import pkg_engine.Item;
import pkg_engine.Player;

/**
 * Command DROP
 * This command allows the player to drop an Item from their inventory. 
 * 
 * How to use: 
 * - The command requires an item name as parameter
 * @author Loic
 */
public class CommandDrop extends Command {
	/**
	 * Constructor
	 */
	public CommandDrop (final String pParameter) {
		super (pParameter);
	}

	/**
	 * Executes the command. 
	 */
	@Override
	public void execute (Player pPlayer) {
		if (hasParameter()) {
			String parameter = getParameter(); 
			Item item = Item.fromString(parameter); 
			
			if (parameter.equals("player")) {
				pPlayer.message("Are you crazy? I can't drop myself! "); 
				
			} else if (item != null) { // Everything's alright! 
				pPlayer.dropItem(item); 
				
			} else { // Not an item name
				try {
					int slot = Integer.parseInt(parameter) - 1; 
					if (slot < 0) {	// Negative index
						pPlayer.message("Are you crazy? I can't drops myself! "); 
					} else {
						pPlayer.dropItemSlot(slot); 
					}
				} catch (final NumberFormatException pException) { // Not a number
					pPlayer.message(String.format("I don't even know what a '%s' is. ", parameter)); 
				}
			}
			
		} else { // No parameter
			pPlayer.message("Drop what?! "); 
		}
	}
}
