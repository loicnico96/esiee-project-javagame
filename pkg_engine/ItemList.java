
package pkg_engine;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Contains an ordered list of items. 
 * Each item is stored in a slot. The slot location is saved 
 * @author Loic
 */
public class ItemList {
	private Item[] aItems; 
	
	/**
	 * Constructor
	 * @param pSize Number of slots in the list
	 */
	public ItemList (final int pSize) {
		aItems = new Item [pSize]; 
		Arrays.fill(aItems, null); 
	}
	
	/**
	 * Returns the maximum number of slots. 
	 */
	public int getMaximumSize () {
		return aItems.length; 
	}
	
	/**
	 * Changes the maximum number of slots. 
	 */
	public void setMaximumSize (final int pSize) {
		aItems = Arrays.copyOf(aItems, pSize); 
	}
	
	/**
	 * Checks whether the list still has a free slot. 
	 */
	public boolean hasFreeSlot () {
		return hasItem(null); 
	}
	
	/**
	 * Checks whether the specified slot is empty. 
	 * If the slot is too big, returns false. 
	 */
	public boolean isSlotEmpty (final int pSlot) {
		return (pSlot < getMaximumSize()) && (aItems[pSlot] == null); 
	}
	
	/**
	 * Checks whether the list contains a specific Item. 
	 */
	public boolean hasItem (final Item pItem) {
		for (Item item : aItems) {
			if (item == pItem)
				return true; 
		}
		return false; 
	}
	
	/**
	 * Returns the Item stored in a specific slot. 
	 * Returns null if the slot is empty or does not exist. 
	 */
	public Item getItem (final int pSlot) {
		return (pSlot < getMaximumSize())? aItems[pSlot] : null; 
	}
	
	/**
	 * Returns an ordered array of all items in the list, without empty slots. 
	 */
	public Item[] getItems () {
		ArrayList<Item> items = new ArrayList<Item> (getMaximumSize()); 
		for (Item item : this.aItems) {
			if (item != null)
				items.add(item); 
		}
		return items.toArray(new Item [0]); 
	}
	
	/**
	 * Adds an Item in the first free slot of the list. 
	 * If no free slot is available, does nothing. 
	 * @return slot where the item is added, -1 if not added
	 */
	public int addItem (final Item pItem) {
		return addItemSlot (pItem, 0); 
	}
	
	/**
	 * Adds an Item to the list. 
	 * At first, it will attempt to place the Item in the specified slot. 
	 * If that slot is already used, it will check the following slots until a free slot is found. 
	 * If no free slot is available, does nothing. 
	 * @return slot where the item is added, -1 if not added
	 */
	public int addItemSlot (final Item pItem, final int pSlot) {
		for (int i = 0 ; i < getMaximumSize() ; i++) {
			int slot = Math.abs((pSlot + i) % getMaximumSize()); 
			if (isSlotEmpty(slot)) {
				aItems[slot] = pItem; 
				return slot; 
			}
		}
		return -1; 
	}
	
	/**
	 * Adds an Item in a random free slot. 
	 * If no free slot is available, does nothing. 
	 * @return slot where the item is added, -1 if not added
	 */
	public int addItemSlotRandom (final Item pItem) {
		return addItemSlot(pItem, Randomizer.getRandomInt(getMaximumSize())); 
	}
	
	/**
	 * Removes the first instance of an Item from the list. 
	 */
	public void removeItem (final Item pItem) {
		for (int slot = 0 ; slot < getMaximumSize() ; slot++) {
			if (aItems[slot] == pItem) {
				removeItemSlot(slot); 
				break; 
			}
		}
	}
	
	/**
	 * Removes the Item in a specified slot. 
	 */
	public void removeItemSlot (final int pSlot) {
		if (pSlot < getMaximumSize())
			aItems[pSlot] = null; 
	}
}
