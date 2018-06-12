
package pkg_items;

import pkg_engine.Player;
import pkg_engine.Item;

/**
 * Subclass for items that can be taken or dropped. 
 * @author Loic
 */
public class ItemMovable extends Item {

	/**
	 * Constructor
	 * @param pName			Item name
	 * @param pDescription	Item description (for the Inspect command)
	 */
	public ItemMovable(final String pName, final String pDescription) {
		super(pName, pDescription);
	}

	/**
	 * Checks whether the item can be picked up or dropped. 
	 */
	@Override
	public boolean isMovable () {
		return true; 
	}
	
	/**
	 * Action performed by a click on the ground. 
	 */
	@Override
	public void onDoubleClickGround (final Player pPlayer, final int pSlot) {
		pPlayer.takeItemSlot(pSlot); 
	}
}
