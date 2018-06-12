
package pkg_engine;

import pkg_items.ItemSwitch;

/**
 * Class containing information about a door. 
 * Doors should be created using the createDoor() static method. 
 * @author Loic
 */
public class Door {	
	/**
	 * Creates 2 doors between 2 rooms (in both directions). 
	 * Doors are automatically added to the rooms (no need to call setExit). 
	 * @param pRoomA		First room
	 * @param pRoomB		Second room
	 * @param pDirection	Exit direction (from first to second room)
	 * @param pColor		Color of the door (null to indicate that there is no color)
	 * @param pOpened		Default status of the door (true if opened)
	 */
	public static void createDoor (final Room pRoomA, final Room pRoomB, final Direction pDirection, final Color pColor, final Boolean pOpened) {
		pRoomA.setExit(pDirection, new Door (pRoomB, pColor, pOpened)); 
		pRoomB.setExit(pDirection.opposite(), new Door (pRoomA, pColor, pOpened)); 
	}
	
	private Room	aExitRoom; 
	private Color	aColor; 
	private boolean	aOpened; 
	
	/**
	 * Constructor
	 * @param pExitRoom	Exit room
	 * @param pColor	Door color (NONE if no color)
	 * @param pOpened	Default stasus (true if opened, false otherwise)
	 */
	public Door (final Room pExitRoom, final Color pColor, final boolean pOpened) {
		this.aExitRoom	= pExitRoom; 
		this.aColor		= pColor; 
		this.aOpened	= pOpened; 
	}
	
	/**
	 * Returns the Room behind this Door. 
	 */
	public Room getExitRoom () {
		return this.aExitRoom; 
	}
	
	/**
	 * Returns the Color of this Door. 
	 */
	public Color getColor () {
		return this.aColor; 
	}
	
	/**
	 * Returns whether or not this Door is opened. 
	 * Checks Switch status for this Door's Color. While the corresponding Switch is activated, the Door's status is inversed. 
	 */
	public boolean isOpened () {
		return (ItemSwitch.isColorActivated(this.aColor))? !this.aOpened : this.aOpened; 
	}
	
	/**
	 * Also checks player status. 
	 */
	public boolean isOpened (final Player pPlayer) {
		return isOpened(); 
	}
	
	/**
	 * Returns whether or not this Door is opened. 
	 * Checks Switch status for this Door's Color. While the corresponding Switch is activated, the Door's status is inversed. 
	 */
	public void setOpened (final boolean pOpened) {
		this.aOpened = pOpened; 
	}
	
	/**
	 * Action upon opening the door. 
	 */
	public void onActionOpen (final Player pPlayer) {}
	
	/**
	 * Returns image name for this door. 
	 */
	public String getImageName (final Direction pDirection) {
		if (aColor == Color.NONE) {
			if (isOpened()) {
				return String.format("door_%s.png", pDirection.getName()); 
			} else { // Closed
				return String.format("door_%s_closed.png", pDirection.getName()); 
			}
			
		} else { // Colored door
			if (isOpened()) {
				return String.format("door_%s_%s.png", aColor.getName(), pDirection.getName()); 
			} else { // Closed
				return String.format("door_%s_%s_closed.png", aColor.getName(), pDirection.getName()); 
			}
		}
	}
}
