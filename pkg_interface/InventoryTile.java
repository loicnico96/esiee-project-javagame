
package pkg_interface;

import java.awt.GridBagConstraints;
import java.awt.event.MouseEvent;

import pkg_engine.Item;

/**
 * Tiles for the inventory. 
 * @author Loic
 */
public class InventoryTile extends Tile {
	private int aIndex; 

	/**
	 * Constructor
	 * @param pInterface	Owning interface
	 * @param pIndex		Tile index
	 * @param pConstraints	Position in the panel
	 */
	public InventoryTile(final PlayerInterface pInterface, final int pIndex, final GridBagConstraints pConstraints) {
		super (pInterface, pConstraints);
		aIndex = pIndex; 
	}

	/**
	 * Returns background name. 
	 */
	@Override
	public String getBackgroundName() {
		if (aIndex < getInterface().getPlayer().getItemList().getMaximumSize()) {
			return "inventory_slot.png"; 
		} else { // Locked
			return "inventory_slot_locked.png"; 
		}
	}

	/**
	 * Returns image name for the contained item (if any). 
	 */
	@Override
	public String getContentName() {
		Item item = getInterface().getPlayer().getItemList().getItem(aIndex); 
		if (item != null) {
			return item.getImageName(); 
		} else { // Empty
			return ""; 
		}
	}

	/**
	 * Action on click. 
	 */
	@Override
	public void onClick (final MouseEvent pEvent) {
		if (aIndex < getInterface().getPlayer().getItemList().getMaximumSize()) {
			Item item = getInterface().getPlayer().getItemList().getItem(aIndex); 
			if (item != null) {
				if (pEvent.getClickCount() % 2 == 0) {
					item.onDoubleClickInventory(getInterface().getPlayer(), aIndex);
					getInterface().update(); 
				} else {
					item.onActionInspect(getInterface().getPlayer());
				}
			} else { // Empty
				getInterface().getPlayer().message("This inventory slot is empty. "); 
			}
		} else { // Locked
			getInterface().getPlayer().message("You haven't unlocked this inventory slot yet. "); 
		}
	}
}
