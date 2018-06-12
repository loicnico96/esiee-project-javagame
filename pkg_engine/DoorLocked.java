
package pkg_engine;

/**
 * Door locked by a pass card. 
 * @author Loic
 */
public class DoorLocked extends Door {
	private Item aKey; 
	
	/**
	 * 
	 * @param pExitRoom		Exit room
	 * @param pKey			Item that opens the door
	 */
	public DoorLocked (final Room pExitRoom, final Item pKey) {
		super (pExitRoom, Color.NONE, false); 
		aKey = pKey; 
	}
	
	/**
	 * Returns whether or not this Door is opened. 
	 * Checks Switch status for this Door's Color. While the corresponding Switch is activated, the Door's status is inversed. 
	 */
	@Override
	public boolean isOpened (final Player pPlayer) {
		return (aKey == null) || pPlayer.getItemList().hasItem(aKey); 
	}
	
	/**
	 * Action upon opening the door. 
	 */
	@Override
	public void onActionOpen (final Player pPlayer) {
		if (aKey != null) {
			pPlayer.message(String.format("You used a %s to open the door", aKey.getName()));
			pPlayer.getItemList().removeItem(aKey); 
			setOpened(true); 
			aKey = null; 
		}
	}
}
