
package pkg_engine;

/**
 * Superclass for all characters. 
 * @author Loic
 */
public class Character extends Item implements Updatable {
	private Room		aCurrentRoom; 
	private int			aRoomLocation; 
	private Direction	aFacingDirection; 
	
	/**
	 * Constructor
	 * @param pRoom Room Starting room for the character
	 * @param pRoomLocation Position of the character in the room
	 * @param pName Internal name of the character
	 * @param pDescription Description of the character (for Inspect command)
	 */
	public Character(final Room pRoom, final int pRoomLocation, final String pName, final String pDescription) {
		super (pName, pDescription);
		aCurrentRoom		= pRoom; 
		aFacingDirection	= Direction.NORTH; 
		aRoomLocation		= pRoom.getItemList().addItemSlot(this, pRoomLocation); 
	}
	
    /**
     * Returns the character's current room. 
     */
    public Room getCurrentRoom () {
        return aCurrentRoom; 
    }
	
    /**
     * Returns the character's location in the room. 
     */
    public int getRoomLocation () {
        return aRoomLocation; 
    }
	
	/**
	 * Returns the image name for this item. 
	 */
    @Override
	public String getImageName () {
		return String.format("character_%s.png", getName().toLowerCase().replace(" ", "_")); 
	}
	
	/**
	 * Action performed by a Inspect command. 
	 */
    @Override
	public void onActionInspect (final Player pPlayer) {
		pPlayer.messageFrom(getName(), getDescription()); 
	}
	
	/**
	 * Action performed by a Talk command. 
	 */
    @Override
	public void onActionTalk (final Player pPlayer) {
		onActionInspect(pPlayer); 
	}
	
	/**
	 * Action performed by a click on the ground. 
	 */
    @Override
	public void onDoubleClickGround (final Player pPlayer, final int pSlot) {
		onActionTalk(pPlayer); 
	}
    
    /**
     * Teleports the character to another room. 
     */
    public void changeRoom (final Room pRoom, final Direction pDirection) {
    	aCurrentRoom.getItemList().removeItem(this);
        aCurrentRoom 		= pRoom; 
        aFacingDirection	= pDirection; 
        switch (pDirection) {
	    	case NORTH: 
	    		aRoomLocation = 22; 
	    		break; 
	    	case EAST: 
	    		aRoomLocation = 10; 
	    		break; 
	    	case SOUTH: 
	    		aRoomLocation = 2; 
	    		break; 
	    	case WEST: 
	    		aRoomLocation = 14; 
	    		break; 
        }
        aRoomLocation = aCurrentRoom.getItemList().addItemSlot(this, aRoomLocation);
    }
    
    /**
     * Teleports the character to another room at a specified location. 
     */
    public void changeRoom (final Room pRoom, final int pLocation) {
    	aCurrentRoom.getItemList().removeItem(this);
        aCurrentRoom 		= pRoom; 
        aRoomLocation		= aCurrentRoom.getItemList().addItemSlot(this, pLocation); 
    }
    
    /**
     * Return the character's facing direction. 
     */
    public Direction getFacingDirection () {
    	return aFacingDirection; 
    }
    
    /**
     * Checks whether a character can move in a direction. 
     */
    public boolean canMove (final Direction pDirection) {
    	int raw = aRoomLocation / 5; 
    	int col = aRoomLocation % 5; 
        switch (pDirection) {
	    	case NORTH: 
	    		return (raw > 0) && aCurrentRoom.getItemList().isSlotEmpty(aRoomLocation - 5); 
	    	case EAST:  
	    		return (col < 4) && aCurrentRoom.getItemList().isSlotEmpty(aRoomLocation + 1); 
	    	case SOUTH: 
	    		return (raw < 4) && aCurrentRoom.getItemList().isSlotEmpty(aRoomLocation + 5); 
	    	case WEST: 
	    		return (col > 0) && aCurrentRoom.getItemList().isSlotEmpty(aRoomLocation - 1); 
	    	default:
	    		return false; 
        }
    }
    
    /**
     * Moves the character one case in a direction. 
     * @return true if the character moved, false otherwise
     */
    public boolean move (final Direction pDirection) {
		aFacingDirection = pDirection; 
    	if (canMove(pDirection)) {
    		aCurrentRoom.getItemList().removeItemSlot(aRoomLocation); 
            switch (pDirection) {
    	    	case NORTH: 
    	    		aRoomLocation -= 5; 
    	    		break; 
    	    	case EAST:  
    	    		aRoomLocation += 1; 
    	    		break; 
    	    	case SOUTH:  
    	    		aRoomLocation += 5; 
    	    		break; 
    	    	case WEST: 
    	    		aRoomLocation -= 1; 
            }
            aRoomLocation = aCurrentRoom.getItemList().addItemSlot(this, aRoomLocation); 
    		return true; 
    	} else {
    		return false; 
    	}
    }
    
    /**
     * Moves (or not) the character randomly. 
     * @return true if the character moved, false otherwise. 
     */
    public boolean moveRandom () {
    	int random = Randomizer.getRandomInt(Direction.values().length + 1); 
    	if (random == Direction.values().length)
    		return false; 
    	if (move(Direction.values()[random]))
    		return true; 
    	return moveRandom(); 
    }
    
    /**
     * Does nothing. 
     */
    @Override
    public void update () {}
}
