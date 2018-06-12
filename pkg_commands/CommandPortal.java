
package pkg_commands;

import pkg_items.ItemPortal;
import pkg_engine.Player;
import pkg_engine.Room;

/**
 * Command PORTAL
 * This command allows the player to pre-define the next random destination of the Portal. 
 * @author Loic
 */
public class CommandPortal extends Command {
	/**
	 * Constructor
	 */
	public CommandPortal (final String pParameter) {
		super (pParameter);
	}

	/**
	 * Executes the command. 
	 */
	@Override
	public void execute(Player pPlayer) {
		//if (pPlayer.getInterpreter().isTestMode())
			ItemPortal.setNextDestination(Room.fromString(getParameter())); 
	}
}
