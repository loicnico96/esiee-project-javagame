
package pkg_engine;

import java.util.HashMap;

/**
 * Class containing information about an item. 
 * @author Loic
 */
public class Item extends NamedObject {
	private static HashMap<String, Item> sItems; 
	static {
		sItems = new HashMap<String, Item> (); 
	}
	
	/**
	 * Returns the Item corresponding to a string (ignoring case). 
	 * Returns null if no Item is found. 
	 */
	public static Item fromString (final String pName) {
		return sItems.get(pName.toLowerCase()); 
	}
	
	private String aDescription; 
	
	/**
	 * Constructor
	 * @param pName			Item name
	 * @param pDescription	Item description (for the Inspect command)
	 */
	public Item (final String pName, final String pDescription) {
		super (pName); 
		aDescription = pDescription; 
		sItems.put(pName.toLowerCase(), this); 
	}
	
	/**
	 * Returns the item's description. 
	 */
	public String getDescription () {
		return aDescription; 
	}
	
	/**
	 * Changes the item's description. 
	 */
	public void setDescription (final String pDescription) {
		aDescription = pDescription; 
	}
	
	/**
	 * Returns the image name for this item. 
	 */
	public String getImageName () {
		return String.format("item_%s.png", getName().replace(" ", "_")); 
	}
	
	/**
	 * Checks whether the item can be picked up or dropped. 
	 */
	public boolean isMovable () {
		return false; 
	}
	
	/**
	 * Action performed by a Charge command. 
	 */
	public void onActionCharge (final Player pPlayer) {
		pPlayer.message("I can't charge that. "); 
	}
	
	/**
	 * Action performed by an Inspect command. 
	 */
	public void onActionInspect (final Player pPlayer) {
		pPlayer.message(getDescription()); 
	}
	
	/**
	 * Action performed by a Talk command. 
	 */
	public void onActionTalk (final Player pPlayer) {
		pPlayer.message("I can't talk to that. "); 
	}
	
	/**
	 * Action performed by a Use command. 
	 */
	public void onActionUse (final Player pPlayer) {
		pPlayer.message("I can't use that. "); 
	}
	
	/**
	 * Should the item be ignored from item lists? 
	 */
	public boolean ignoredFromList () {
		return false; 
	}
	
	/**
	 * Action performed by a click on the ground. 
	 */
	public void onDoubleClickGround (final Player pPlayer, final int pSlot) {
		onActionUse(pPlayer); 
	}
	
	/**
	 * Action performed by a click in the inventory. 
	 */
	public void onDoubleClickInventory (final Player pPlayer, final int pSlot) {
		onActionUse(pPlayer); 
	}
}
