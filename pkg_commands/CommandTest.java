
package pkg_commands;

import java.io.FileNotFoundException;

import pkg_engine.FileReader;
import pkg_engine.Player;

/**
 * Command TEST
 * This command allows the player to execute a list of commands from a .txt file. 
 * @author Loic
 */
public class CommandTest extends Command {
	/**
	 * Constructor
	 */
	public CommandTest (final String pParameter) {
		super (pParameter);
	}

	/**
	 * Executes the command. 
	 */
	@Override
	public void execute(Player pPlayer) {
		if (hasParameter()) {
			String parameter = getParameter(); 
			if (parameter.equals("on")) {
				pPlayer.getInterpreter().setTestMode(true);
				pPlayer.message("Test mode is now activated. ");
			} else if (parameter.equals("off")) {
				pPlayer.getInterpreter().setTestMode(false);
				pPlayer.message("Test mode is now deactivated. ");
			} else {
				pPlayer.getInterpreter().setTestMode(true);
				try {
					pPlayer.processCommands(FileReader.getLines(parameter));
				} catch (final FileNotFoundException pException) {
					pPlayer.message(String.format("The file '%s' doesn't seem to be there... ", parameter)); 
				}
				pPlayer.getInterpreter().setTestMode(false);
			}
		} else {
			pPlayer.message("Test what?! "); 
		}
	}
}
