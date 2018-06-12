
package pkg_items;

import java.util.Timer;
import java.util.TimerTask;

import pkg_engine.Item;
import pkg_engine.Player;
import pkg_engine.Room;

/**
 * Class for beamer item. 
 * Beamer portals have a limited duration and are destroyed upon use. 
 * @author Loic
 */
public class ItemBeamer extends ItemMovable {
	private Room 	aPortalRoom; 
	private int		aPortalLocation; 
	private int		aPortalDuration; 
	private Timer	aTimer; 

	/**
	 * Constructor
	 * @param pDuration Duration of the portal
	 */
	public ItemBeamer (final int pDuration) {
		super ("beamer", "This powerful invention can control portals. Charge it to create a single temporary portal at your location. You will be able to return to it later.");
		aPortalRoom		= null; 
		aPortalLocation	= 0; 
		aPortalDuration	= pDuration;  
		aTimer			= null; 
	}
	
	/**
	 * Returns portal's duration. 
	 */
	public int getPortalDuration () {
		return aPortalDuration; 
	}
	
	/**
	 * Changes the duration of portals. 
	 * Does not affect portals that have already been placed. 
	 */
	public void setPortalDuration (final int pDuration) {
		aPortalDuration = pDuration; 
	}
	
	/**
	 * Checks whether the beamer is charged. 
	 */
	public boolean isCharged () {
		return (aPortalRoom != null); 
	}
	
	/**
	 * Returns the image name for this item. 
	 */
	@Override
	public String getImageName () {
		return isCharged()? "item_beamer_charged.png" : "item_beamer.png"; 
	}
	
	/**
	 * Removes the portal. 
	 */
	public void removePortal () {
		if (isCharged()) {
			aPortalRoom.getItemList().removeItemSlot(aPortalLocation); 
			aTimer.cancel(); 
		}
		aPortalRoom		= null; 
		aPortalLocation	= 0; 
		aTimer			= null; 
	}
	
	/**
	 * Action performed by a Charge command. 
	 */
	@Override
	public void onActionCharge (final Player pPlayer) {
		Item portal = new Item ("beamer portal", "This portal was created by a beamer. You can use the beamer to return to it at any time. ") {
			@Override
			public boolean ignoredFromList () {
				return true; 
			}
		}; 
		removePortal(); 
		pPlayer.message("You created a portal for " + aPortalDuration + " seconds. You can now use the beamer to return here instantly. ");
		aPortalRoom		= pPlayer.getCurrentRoom(); 
		aPortalLocation	= pPlayer.getCurrentRoom().getItemList().addItemSlot(portal, pPlayer.getRoomLocation()); 
		aTimer			= new Timer (); 
		aTimer.schedule (new TimerTask () {
			@Override
			public void run() {
				pPlayer.message("Your portal will disappear in 5 seconds. ");
			}
		}, (aPortalDuration - 5) * 1000); 
		aTimer.schedule (new TimerTask () {
			@Override
			public void run() {
				removePortal(); 
				pPlayer.message("Your portal has disappeared. ");
				pPlayer.update(); 
			}
		}, aPortalDuration * 1000); 
	}
	
	/**
	 * Action performed by a Use command. 
	 */
	@Override
	public void onActionUse (final Player pPlayer) {
		if (isCharged()) {
			Room portalRoom		= aPortalRoom; 
			int portalLocation	= aPortalLocation; 
			removePortal(); 
			pPlayer.changeRoom(portalRoom, portalLocation); 
			pPlayer.clearBackHistory(); 
		} else { 
			onActionCharge(pPlayer); 
		}
	}
	
	/**
	 * Action performed by a click in the inventory. 
	 */
	@Override
	public void onDoubleClickInventory (final Player pPlayer, final int pSlot) {
		onActionUse(pPlayer); 
	}
}
