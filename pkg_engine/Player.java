
package pkg_engine;

import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

import pkg_commands.Command;
import pkg_commands.CommandInterpreter;
import pkg_interface.PlayerInterface;

/**
 * Class containing information about a player. 
 * @author Loic
 */
public class Player extends Character {
    private ItemList            aItemList; 
    private Stack<Direction>	aBackHistory; 
    private PlayerInterface     aInterface; 
    private CommandInterpreter  aInterpreter; 
    
    /**
     * Constructor
     * @param pName         Player name
     * @param pStartingRoom Starting room
     */
    public Player (final Room pStartingRoom, final int pStartingLocation) {
        super (pStartingRoom, pStartingLocation, "player", "Hello! ");
        aItemList       = new ItemList (3); 
        aBackHistory    = new Stack<Direction> (); 
        aInterface      = new PlayerInterface (this); 
        aInterpreter    = new CommandInterpreter (); 
    }
    
    /**
     * Returns the command interpreter for this player. 
     */
    public CommandInterpreter getInterpreter () {
        return aInterpreter; 
    }

    /**
     * Returns the items contained in the inventory. 
     */
    public ItemList getItemList () {
        return aItemList; 
    }
	
	/**
	 * Returns the image name for this item. 
	 */
    @Override
	public String getImageName () {
		return "player.png"; 
	}
	
	/**
	 * Action performed by an Inspect command. 
	 */
	@Override
	public void onActionInspect (final Player pPlayer) {
		pPlayer.message("Hello! "); 
	}
	
	/**
	 * Action performed by an Use command. 
	 */
	@Override
	public void onActionUse (final Player pPlayer) {
		pPlayer.message("I'm fine. Stop clicking now. "); 
	}
    
    /**
     * Checks whether the player can go back. 
     */
    public boolean canGoBack () {
    	return !aBackHistory.empty(); 
    }
    
    /**
     * Prevents the player from backing. 
     */
    public void clearBackHistory () {
        aBackHistory.clear(); 
    }
    
    /**
     * Teleports the player to another room. 
     */
    @Override
    public void changeRoom (final Room pRoom, final Direction pDirection) {
    	getCurrentRoom().getExit(pDirection).onActionOpen(this);
        super.changeRoom(pRoom, pDirection);
	    getCurrentRoom().onActionEnter(this);
	    update(); 
    }

    /**
     * Teleports the player to another room at a specified location. 
     */
    @Override
    public void changeRoom (final Room pRoom, final int pLocation) {
        super.changeRoom(pRoom, pLocation);
        getCurrentRoom().onActionEnter(this);
        update(); 
    }

    /**
     * Moves the player one case in a direction. 
     * @return true if the character moved, false otherwise
     */
    @Override
    public boolean move (final Direction pDirection) {
        if (super.move(pDirection)) {
            update(); 
        	return true; 
        }
        int raw = getRoomLocation() / 5; 
        int col = getRoomLocation() % 5; 
        switch (pDirection) {
	    	case NORTH: 
	    		if ((getRoomLocation() == 2) && (getCurrentRoom().getExit(Direction.NORTH) != null)) {
	    			goRoom(Direction.NORTH); 
	    			return true; 
	    		} else if (raw > 0) {
	    			getCurrentRoom().getItemList().getItem(getRoomLocation() - 5).onActionInspect(this); 
	    		}
	    		return false; 
	    	case EAST:  
	    		if ((getRoomLocation() == 14) && (getCurrentRoom().getExit(Direction.EAST) != null)) {
	    			goRoom(Direction.EAST); 
	    			return true; 
	    		} else if (col < 4) {
	    			getCurrentRoom().getItemList().getItem(getRoomLocation() + 1).onActionInspect(this); 
	    		}
	    		return false; 
	    	case SOUTH: 
	    		if ((getRoomLocation() == 22) && (getCurrentRoom().getExit(Direction.SOUTH) != null)) {
	    			goRoom(Direction.SOUTH); 
	    			return true; 
	    		} else if (raw < 4) {
	    			getCurrentRoom().getItemList().getItem(getRoomLocation() + 5).onActionInspect(this); 
	    		}
	    		return false; 
	    	case WEST: 
	    		if ((getRoomLocation() == 10) && (getCurrentRoom().getExit(Direction.WEST) != null)) {
	    			goRoom(Direction.WEST); 
	    			return true; 
	    		} else if (col > 0) {
	    			getCurrentRoom().getItemList().getItem(getRoomLocation() - 1).onActionInspect(this); 
	    		}
	    		return false; 
	    	default:
	    		return false; 
        }
    }
    
    /**
     * Charges an item from the player's current room or inventory. 
     */
    public void chargeItem (final Item pItem) {
        if (aItemList.hasItem(pItem) || getCurrentRoom().getItemList().hasItem(pItem)) {
            pItem.onActionCharge(this);
        	update(); 
        } else { // Item not there
            message(String.format("I don't see any %s. ", pItem.getName())); 
        }
    }
    
    /**
     * Charges an item from the player's inventory. 
     */
    public void chargeItemSlot (final int pSlot) {
    	if (pSlot < aItemList.getMaximumSize()) {
    		Item item = aItemList.getItem(pSlot); 
    		if (item != null) {
    			item.onActionCharge(this);
    	    	update(); 
    		} else { // No item
                message("This inventory slot is empty. "); 
    		}
    	} else { // Slot too big
    		message("My inventory is not that large! "); 
    	}
    }
    
    /**
     * Drops an item from the player's inventory. 
     */
    public void dropItem (final Item pItem) {
        if (aItemList.hasItem(pItem)) {
            if (pItem.isMovable()) {
            	getCurrentRoom().getItemList().addItemSlot(pItem, getRoomLocation()); 
                aItemList.removeItem(pItem); 
                message(String.format("You dropped a %s.", pItem.getName())); 
            	update(); 
            } else { // Item not movable
                message("I can't drop that now. "); 
            }
        } else { // Item not there
            message(String.format("I don't have any %s. ", pItem.getName())); 
        }
    }
    
    /**
     * Drops an item from the player's inventory. 
     */
    public void dropItemSlot (final int pSlot) {
    	if (pSlot < aItemList.getMaximumSize()) {
    		Item item = aItemList.getItem(pSlot); 
    		if (item == null) {	// No item
    			message("This slot is already empty. ");  
    		} else if (item.isMovable()) {
            	getCurrentRoom().getItemList().addItemSlot(item, getRoomLocation()); 
                aItemList.removeItemSlot(pSlot); 
                message(String.format("You dropped a %s.", item.getName())); 
            	update(); 
    		} else { // Item not movable
                message("I can't drop that now. "); 
    		}
    	} else { // Slot too big
    		message("My inventory is not that large! "); 
    	}
    }
    
    /**
     * Returns to the previous room. 
     */
    public void goBack () {
        if (canGoBack()) {
        	Direction direction = aBackHistory.peek().opposite(); 
        	Door door = getCurrentRoom().getExit(direction); 
        	if ((door != null) && (door.isOpened(this))) {
                aBackHistory.pop(); 
                changeRoom(door.getExitRoom(), direction); 
        	} else {
        		message("That exit is closed! "); 
        	}
        } else { // No back history
            message("I can't go back now. "); 
        }
    }
    
    /**
     * Takes the door in specified direction. 
     */
    public void goRoom (final Direction pDirection) {
        Door door = getCurrentRoom().getExit(pDirection); 
        if (door != null) {
            if (door.isOpened(this)) {
                if (canGoBack() && aBackHistory.peek().equals(pDirection.opposite())) {
                	aBackHistory.pop(); 
                } else {
                	aBackHistory.push(pDirection); 
                }
                changeRoom(door.getExitRoom(), pDirection); 
            } else { // Door closed
                message("This door is closed. "); 
            }
        } else { // No door
            message("There is no door! "); 
        }
    }
    
    /**
     * Inspects an item in the player's current room or inventory. 
     */
    public void inspectItem (final Item pItem) {
        if (aItemList.hasItem(pItem) || getCurrentRoom().getItemList().hasItem(pItem)) {
            pItem.onActionInspect(this);
        } else { // Item not there
            message(String.format("I don't see any %s. ", pItem.getName())); 
        }
    }
    
    /**
     * Inspects the player's current room. 
     */
    public void inspectCurrentRoom () {
    	getCurrentRoom().onActionInspect(this);
    }
    
    /**
     * Quits the game. 
     */
    public void quit (final int pDuration) {
        message("The window will close in " + pDuration + " seconds. "); 
        message("Thank you for playing. "); 
        message("Good bye! "); 
        aInterface.enableInput(false); 
        Timer shutdownTimer = new Timer (); 
        shutdownTimer.schedule(new TimerTask () {
            public void run () {
                System.exit(0); 
            }
        }, pDuration * 1000); 
    }
    
    /**
     * Shows all commands. 
     */
    public void showCommands () {
        message("Use directional arrows to move your character."); 
        message("Use A to interact with items and characters in front of you."); 
        message("Use numeric pad to use items from your inventory."); 
    }
    
    /**
     * Shows all items. 
     */
    public void showItems () {
        Item[] items = aItemList.getItems(); 
        if (items.length > 0) {
            message("Your inventory contains: "); 
            for (Item item : items) {
                message(String.format(" - %s", item.getName())); 
            }
        } else { // Empty inventory
            message("Your inventory contains nothing. "); 
        }
    }
    
    /**
     * Places an item in the player's inventory. 
     */
    public void takeItem (final Item pItem) {
        if (getCurrentRoom().getItemList().hasItem(pItem)) {
            if (pItem.isMovable()) {
                if (aItemList.hasFreeSlot()) {
                    aItemList.addItem(pItem); 
                    getCurrentRoom().getItemList().removeItem(pItem); 
                    message(String.format("You picked up a %s.", pItem.getName())); 
                	update(); 
                } else { // Inventory already full
                    message("My inventory is full already. "); 
                }
            } else { // Item not movable
                message("I can't pick that up. "); 
            }
        } else { // Item not there
            message(String.format("I don't see any %s. ", pItem.getName())); 
        }
    }
    
    /**
     * Takes an item from the the ground.
     */
    public void takeItemSlot (final int pSlot) {
    	Item item = getCurrentRoom().getItemList().getItem(pSlot); 
    	if (item != null) {
    		if (item.isMovable()) {
                if (aItemList.hasFreeSlot()) {
                    aItemList.addItem(item); 
                    getCurrentRoom().getItemList().removeItemSlot(pSlot); 
                    message(String.format("You picked up a %s.", item.getName())); 
                	update(); 
                } else { // Inventory already full
                    message("My inventory is full already. "); 
                }
    		} else { // Item not movable
                message("I can't pick that up. "); 
            }
    	} else { // Item not there
            message(String.format("I don't see anything here. ")); 
        }
    }
    
    /**
     * Uses an item in the player's current room or inventory. 
     */
    public void useItem (final Item pItem) {
        if (aItemList.hasItem(pItem) || getCurrentRoom().getItemList().hasItem(pItem)) {
            pItem.onActionUse(this);
        	update(); 
        } else { // Item not there
            message(String.format("I don't see any %s. ", pItem.getName())); 
        }
    }
    
    /**
     * Uses an item from the player's inventory. 
     */
    public void useItemSlot (final int pSlot) {
    	if (pSlot < aItemList.getMaximumSize()) {
    		Item item = aItemList.getItem(pSlot); 
    		if (item != null) {
    			item.onActionUse(this);
    	    	update(); 
    		} else { // No item
                message("This inventory slot is empty. "); 
    		}
    	} else { // Slot too big
    		message("My inventory is not that large! "); 
    	}
    }
    
    /**
     * Talks to an item in the player's current room. 
     */
    public void talkItem (final Item pItem) {
        if (getCurrentRoom().getItemList().hasItem(pItem)) {
            pItem.onActionTalk(this);
        	update(); 
        } else { // Item not there
            message(String.format("I don't see any %s. ", pItem.getName())); 
        }
    }
    
    /**
     * Uses the item in front of the player. 
     */
    public void onActionKeyboard () {
        int raw = getRoomLocation() / 5; 
        int col = getRoomLocation() % 5; 
        switch (getFacingDirection()) {
	    	case NORTH: 
	    		if (raw > 0) {
	    			Item item = getCurrentRoom().getItemList().getItem(getRoomLocation() - 5); 
	    			if (item != null)
	    				item.onDoubleClickGround(this, getRoomLocation() - 5); 
	    		} else if ((col == 2) && (getCurrentRoom().getExit(Direction.NORTH) != null)) {
	    			goRoom(Direction.NORTH); 
	    		} else {
	    			message("This is just a wall... "); 
	    		}
	    		break; 
	    	case EAST:  
	    		if (col < 4) {
	    			Item item = getCurrentRoom().getItemList().getItem(getRoomLocation() + 1); 
	    			if (item != null)
	    				item.onDoubleClickGround(this, getRoomLocation() + 1); 
	    		} else if ((raw == 2) && (getCurrentRoom().getExit(Direction.EAST) != null)) {
	    			goRoom(Direction.EAST); 
	    		} else {
	    			message("This is just a wall... "); 
	    		}
	    		break; 
	    	case SOUTH: 
	    		if (raw < 4) {
	    			Item item = getCurrentRoom().getItemList().getItem(getRoomLocation() + 5); 
	    			if (item != null)
	    				item.onDoubleClickGround(this, getRoomLocation() + 5); 
	    		} else if ((col == 2) && (getCurrentRoom().getExit(Direction.SOUTH) != null)) {
	    			goRoom(Direction.SOUTH); 
	    		} else {
	    			message("This is just a wall... "); 
	    		}
	    		break; 
	    	case WEST: 
	    		if (col > 0) {
	    			Item item = getCurrentRoom().getItemList().getItem(getRoomLocation() - 1); 
	    			if (item != null)
	    				item.onDoubleClickGround(this, getRoomLocation() - 1); 
	    		} else if ((raw == 2) && (getCurrentRoom().getExit(Direction.WEST) != null)) {
	    			goRoom(Direction.WEST); 
	    		} else {
	    			message("This is just a wall... "); 
	    		}
	    		break; 
        }
    }
    
    /**
     * Display a message for the player. 
     */
    public void message (final String pMessage) {
        aInterface.printMessage(pMessage); 
    }
    
    /**
     * Display a message for the player from a character. 
     */
    public void messageFrom (final String pName, final String pMessage) {
        message(String.format("[%s] \"%s\"", pName, pMessage)); 
    }
    
    /**
     * Updates the interface of the player. 
     * This method should be called whenever the current room or the inventory changes. 
     */
    @Override
    public void update () {
        aInterface.update(); 
    }
    
    /**
     * Executes a single command. 
     */
    public void processCommand (final String pCommandLine) {
        Command command = aInterpreter.interpretCommand(pCommandLine); 
        if (command != null) {
            command.execute(this); 
        } else { // Invalid command
            message("I don't understand what you mean... "); 
        }
    }
    
    /**
     * Executes a list of commands. 
     */
    public void processCommands (final String[] pCommandLines) {
        for (String line : pCommandLines) {
            processCommand(line); 
        }
    }
	
	/**
	 * Should the item be ignored from item lists? 
	 */
	@Override
	public boolean ignoredFromList () {
		return true; 
	}
}
