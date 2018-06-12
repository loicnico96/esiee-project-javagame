
package pkg_commands;

import pkg_engine.Player;

/**
 * Command SHOW
 * This command allows the player to show their commands or their items. 
 * @author Loic
 */
public class CommandShow extends Command {
	/**
	 * Constructor
	 */
	public CommandShow (final String pParameter) {
		super (pParameter);
	}

	/**
	 * Executes the command. 
	 */
	@Override
	public void execute(Player pPlayer) {
		String parameter = getParameter(); 
		if (parameter.equalsIgnoreCase("commands")) {
			pPlayer.showCommands(); 
		} else if (parameter.equalsIgnoreCase("items")) {
			pPlayer.showItems(); 
		} else { // No parameter
			pPlayer.message("Show what?! "); 
		}
	}
}
