
package pkg_items;

import pkg_engine.Item;
import pkg_engine.Player;
import pkg_engine.Randomizer;
import pkg_engine.Room;

/**
 * Class for the random portal. 
 * @author Loic
 */
public class ItemPortal extends Item {
    private static Room sNextDestination = null; 
    
    /**
     * Pre-determines the next destination of a portal. 
     */
    public static void setNextDestination (final Room pRoom) {
        sNextDestination = pRoom; 
    }
    
    private Room[] aRooms; 
    
    /**
     * Constructor
     */
    public ItemPortal (final Room[] pRooms) {
        super ("portal", "The energy from this portal is so high and unstable that it may teleport you to a random location."); 
        aRooms = pRooms; 
    }

    /**
     * Action performed by a Use command. 
     */
    @Override
    public void onActionUse (final Player pPlayer) {
        if (pPlayer.getItemList().hasItem(Item.fromString("portal ring"))) {
            Room destination = sNextDestination; 
            if (destination == null)
                destination = Randomizer.getRandomElement(aRooms); 
            pPlayer.changeRoom(destination, 12);  
            pPlayer.clearBackHistory(); 
            sNextDestination = null; 
        } else {
            pPlayer.messageFrom("Red guy", "You shouldn't use this portal recklessly."); 
        }
    }
}

