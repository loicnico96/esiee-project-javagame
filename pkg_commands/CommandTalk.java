
package pkg_commands;

import pkg_engine.Item;
import pkg_engine.Player;

/**
 * Command TALK
 * This command allows the player to talk to another character. 
 * @author Loic
 */
public class CommandTalk extends Command {
	/**
	 * Constructor
	 */
	public CommandTalk (final String pParameter) {
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
				pPlayer.talkItem(item); 
				
			} else { // Not an item name
				pPlayer.message(String.format("I don't even know what a '%s' is. ", parameter)); 
			}
		} else { // No parameter
			pPlayer.message("Use what?! "); 
		}
	}
}
