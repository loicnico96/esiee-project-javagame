
package pkg_commands;

import pkg_engine.Item;
import pkg_engine.Player;

/**
 * Command USE
 * This command allows the player to use an Item in the room or in their inventory. 
 * @author Loic
 */
public class CommandUse extends Command {
	/**
	 * Constructor
	 */
	public CommandUse (final String pParameter) {
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
			
			if (item != null) { // Everything's alright! 
				pPlayer.useItem(item); 
				
			} else { // Not an item name
				try {
					int slot = Integer.parseInt(parameter) - 1; 
					if (slot < 0) {	// Negative index
						pPlayer.message("Are you kidding me?");
					} else {
						pPlayer.useItemSlot(slot); 
					}
				} catch (final NumberFormatException pException) { // Not a number
					pPlayer.message(String.format("I don't even know what a '%s' is. ", parameter)); 
				}
			}
			
		} else { // No parameter
			pPlayer.message("Use what?! "); 
		}
	}
}
