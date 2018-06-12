
package pkg_commands;

import pkg_engine.Item;
import pkg_engine.Player;

/**
 * Command CHARGE
 * This command allows the player to charge an item from their inventory. 
 * @author Loic
 */
public class CommandCharge extends Command {
	/**
	 * Constructor
	 */
	public CommandCharge (final String pParameter) {
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
				pPlayer.message("Are you crazy? I can't charge myself! "); 
				
			} else if (item != null) { // Everything's alright! 
				pPlayer.chargeItem(item); 
				
			} else { // Invalid parameter
				try {
					int slot = Integer.parseInt(parameter) - 1; 
					if (slot < 0) {	// Negative index
						pPlayer.message("Are you kidding me?");
					} else {
						pPlayer.chargeItemSlot(slot); 
					}
				} catch (final NumberFormatException pException) { // Not a number
					pPlayer.message(String.format("I don't even know what a '%s' is. ", parameter)); 
				}
			}
			
		} else { // No parameter
			pPlayer.message("Charge what?! "); 
		}
	}
}
