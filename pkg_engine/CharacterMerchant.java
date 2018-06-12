
package pkg_engine;

import java.util.ArrayList;

import pkg_items.ItemBeamer;

/**
 * Roaming merchant in Floor 3. 
 * @author Loic
 */
public class CharacterMerchant extends CharacterRoaming {
	private Direction 	aNextDirection; 
	private boolean		aFirstTime; 
	
	/**
	 * Constructor
	 * @param pRoom				Starting room
	 * @param pRoomLocation		Starting location
	 */
	public CharacterMerchant (final Room pRoom, final int pRoomLocation) {
		super (pRoom, pRoomLocation, "Merchant", "I exchange 7 cans against a beamer.", 8); 
		aNextDirection = null; 
		aFirstTime = true; 
	}
	
	/**
	 * Action on Talk command.
	 */
	@Override
	public void onActionTalk (final Player pPlayer) {
		//if (aFirstTime) {
			int count = 0; 
			Item can = Item.fromString("can"); 
			for (Item item : pPlayer.getItemList().getItems()) {
				if (item == can)
					count++; 
			}
			
			if (count >= 7) {
				for (int i = 0 ; i < 7 ; i++)
					pPlayer.getItemList().removeItem(can);
				pPlayer.getItemList().addItem(new ItemBeamer (30));
				pPlayer.message("You received another beamer!"); 
				aFirstTime = false; 
			} else {
				pPlayer.messageFrom(getName(), "I won't negotiate."); 
				return; 
			}
		//}
		pPlayer.messageFrom(getName(), "Have a nice day!"); 
	}
	
	/**
	 * Finds next random direction. 
	 */
	public void findNextDirection () {
		ArrayList<Direction> directions = new ArrayList<Direction> (4); 
		for (Direction direction : Direction.values()) {
			Door door = getCurrentRoom().getExit(direction); 
			if ((door != null) && door.isOpened())
				directions.add(direction); 
		}
		aNextDirection = Randomizer.getRandomElement(directions.toArray(new Direction [0])); 
	}
    
    /**
     * Moves (or not) the character randomly. 
     * @return true if the character moved, false otherwise. 
     */
	@Override
    public boolean moveRandom () {
    	// No direction, try to find one
    	if (aNextDirection == null)
    		findNextDirection(); 
    	// Still no direction, move randomly in the room
    	if (aNextDirection == null)
    		return super.moveRandom(); 
    	//
    	int destRaw = 2; 
    	int destCol = 2; 
    	switch (aNextDirection) {
	    	case NORTH:
	        	destRaw = 0; 
	        	break; 
	    	case EAST:
	        	destCol = 4; 
	        	break; 
	    	case SOUTH:
	        	destRaw = 4; 
	        	break; 
	    	case WEST:
	        	destCol = 0; 
	        	break; 
    	}
    	int raws = destRaw - getRoomLocation() / 5; 
    	int cols = destCol - getRoomLocation() % 5; 
    	if ((raws == 0) && (cols == 0)) {
    		changeRoom(getCurrentRoom().getExit(aNextDirection).getExitRoom(), aNextDirection); 
    		aNextDirection = null; 
    		return true; 
    	}
    	if (Math.abs(raws) > Math.abs(cols)) {
        	if ((raws < 0) && move(Direction.NORTH))
        		return true; 
        	if ((raws > 0) && move(Direction.SOUTH))
        		return true; 
    	}
    	if ((cols > 0) && move(Direction.EAST))
    		return true; 
    	if ((cols < 0) && move(Direction.WEST))
    		return true; 
    	if ((raws < 0) && move(Direction.NORTH))
    		return true; 
    	if ((raws > 0) && move(Direction.SOUTH))
    		return true; 
		aNextDirection = null; 
    	return false; 
    }
}

