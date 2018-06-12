
package pkg_items;

import pkg_engine.Item;
import pkg_engine.Player;

/**
 * Class for holes. 
 * @author Loic
 */
public class ItemHole extends Item {
	/**
	 * Constructor
	 */
	public ItemHole () {
		super ("hole", "I shouldn't stand near that. ");
	}
	
	/**
	 * Action performed by a Use command. 
	 */
	@Override
	public void onActionUse (final Player pPlayer) {
		onActionInspect(pPlayer); 
	}
	
	/**
	 * Should the item be ignored from item lists? 
	 */
	@Override
	public boolean ignoredFromList () {
		return true; 
	}
}
