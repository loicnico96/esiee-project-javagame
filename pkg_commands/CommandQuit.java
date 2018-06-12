
package pkg_commands;

import pkg_engine.Player;

/**
 * Command QUIT
 * This command allows the player to quit the game. 
 * @author Loic
 */
public class CommandQuit extends Command {
	/**
	 * Constructor
	 */
	public CommandQuit (final String pParameter) {
		super (pParameter);
	}

	/**
	 * Executes the command. 
	 */
	@Override
	public void execute(Player pPlayer) {
		if (hasParameter()) {
			pPlayer.message("Quit what?! "); 
		} else {
			pPlayer.quit(2); 
		}
	}
}
