
package pkg_engine;

import java.util.HashMap;

/**
 * Class containing information about a room. 
 * @author Loic
 */
public class Room extends NamedObject {
	private static HashMap<String, Room> sRooms; 
	static {
		sRooms = new HashMap<String, Room> (); 
	}
	
	/**
	 * Returns the Room corresponding to a string (ignoring case). 
	 * Returns null if no Room is found. 
	 */
	public static Room fromString (final String pName) {
		return sRooms.get(pName.toLowerCase()); 
	}
	
	/** 
	 * Returns an array containing all rooms. 
	 */
	public static Room[] allRooms () {
		return sRooms.values().toArray(new Room [0]); 
	}
	
	private HashMap<Direction, Door>	aExits; 
	private ItemList 					aItemList; 
	
	/**
	 * Constructor
	 * @param pName			Room name
	 */
	public Room (final String pName) {
		super (pName); 
		aItemList 	= new ItemList (25); 
		aExits		= new HashMap<Direction, Door> (); 
		sRooms.put(pName.toLowerCase(), this); 
	}
	
	/**
	 * Returns the items contained in the room. 
	 */
	public ItemList getItemList () {
		return aItemList; 
	}
	
	/**
	 * Returns an array of all Doors, without null values. 
	 */
	public Door[] getExits () {
		return aExits.values().toArray(new Door [0]); 
	}
	
	/**
	 * Returns the Door in specified Direction. 
	 * Returns null if there is no door. 
	 */
	public Door getExit (final Direction pDirection) {
		return aExits.get(pDirection); 
	}
	
	/**
	 * Changes the Door in specified Direction. 
	 */
	public void setExit (final Direction pDirection, final Door pDoor) {
		aExits.put(pDirection, pDoor); 
	}
	
	/**
	 * Action performed upon entering the room. 
	 */
	public void onActionEnter (final Player pPlayer) {
		onActionInspect(pPlayer);
	}
	
	/**
	 * Action performed by an Inspect command. 
	 */
	public void onActionInspect (final Player pPlayer) {
		pPlayer.message(String.format("You are in %s. ", getName())); 
	}
}
