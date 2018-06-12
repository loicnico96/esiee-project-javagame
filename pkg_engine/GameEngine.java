
package pkg_engine;

import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import pkg_items.ItemBeamer;
import pkg_items.ItemHole;
import pkg_items.ItemMovable;
import pkg_items.ItemPortal;
import pkg_items.ItemSwitch;

/**
 * Class managing a single game instance. 
 * @author Loic
 */
public class GameEngine {
    private LinkedList<Updatable>   aUpdateObjects; 
    private Player                  aPlayer; 
    private Timer                   aTimer; 
    
    /**
     * Constructor
     */
    public GameEngine () {
        aUpdateObjects  = new LinkedList<Updatable> (); 
        aTimer          = new Timer (); 
        aTimer.schedule(new TimerTask () {
            public void run () {
                for (Updatable object : aUpdateObjects) {
                    object.update(); 
                }
            }
        }, 100, 100); 
        setup(); 
    }
    
    /**
     * Adds an object to the update list. 
     */
    public void requiresUpdate (final Updatable pUpdateObject) {
        aUpdateObjects.push(pUpdateObject); 
    }
    
    /**
     * Creates all game elements. 
     */
    private void setup () {
        // Rooms
        // Floor 0
        Room room002 = new Room ("Room 002"); 
        Room room003 = new Room ("Room 003"); 
        Room room004 = new Room ("Room 004"); 
        Room room005 = new Room ("Room 005") {
            private boolean aActionEnter = true; 
            public void onActionEnter (final Player pPlayer) {
                if (aActionEnter) {
                    pPlayer.messageFrom("Voice", "Alert! Alert! Code BLUE activated! All blue doors are inverted!"); 
                    ItemSwitch.inverseColor(Color.BLUE);
                    aActionEnter = false; 
                    pPlayer.update(); 
                }
            }
        }; 
        Room room006 = new Room ("Room 006"); 
        Room room007 = new Room ("Room 007"); 
        Room room008 = new Room ("Room 008"); 
        Room room009 = new Room ("Room 009"); 
        Room room010 = new Room ("Room 010"); 
        final Room room011 = new Room ("Room 011"); 
        Room room012 = new Room ("Room 012"); 
        Room room013 = new Room ("Room 013")  {
            private boolean aActionEnter = true; 
            public void onActionEnter (final Player pPlayer) {
                if (aActionEnter) {
                    aActionEnter = false; 
                    aPlayer.messageFrom("Voice", "Alert! Alert! Code GREEN activated. This sector will be put in quarantine."); 
                    aTimer.schedule(new TimerTask () {
                        int time = 1805; 
                        public void run () {
                            time -= 5; 
                            if (aPlayer.getItemList().hasItem(Item.fromString("great can"))) {
                                aPlayer.messageFrom("Voice", "Congratulations! You won the game!"); 
                                aPlayer.quit(5); 
                            } else if (time == 0) {
                                aPlayer.messageFrom("Voice", "Alert! Alert! Code BLACK activated. AUTO-DEESTRUCTION NOW!"); 
                                aPlayer.quit(2); 
                            } else if (time < 60) {
                                aPlayer.messageFrom("Voice", "Auto-destruction in " + time + " seconds."); 
                            } else if (time % 60 == 0) {
                                aPlayer.messageFrom("Voice", "Auto-destruction in " + (time/60) + " minutes."); 
                            }
                        }
                    }, 1000, 5000); 
                }
            }
        }; 
        Room room015 = new Room ("Room 015"); 
        Room room018 = new Room ("Room 018"); 
        Room room023 = new Room ("Room 023"); 
        // Floor 1
        Room room100 = new Room ("Room 100"); 
        Room room101 = new Room ("Room 101"); 
        Room room102 = new Room ("Room 102"); 
        Room room103 = new Room ("Room 103"); 
        Room room104 = new Room ("Room 104"); 
        Room room106 = new Room ("Room 106"); 
        Room room107 = new Room ("Room 107"); 
        Room room108 = new Room ("Room 108"); 
        Room room109 = new Room ("Room 109"); 
        Room room111 = new Room ("Room 111"); 
        Room room114 = new Room ("Room 114"); 
        Room room117 = new Room ("Room 117"); 
        // Floor 3
        Room room300 = new Room ("Room 300"); 
        Room room301 = new Room ("Room 301"); 
        Room room302 = new Room ("Room 302"); 
        Room room303 = new Room ("Room 303"); 
        Room room304 = new Room ("Room 304"); 
        Room room305 = new Room ("Room 305"); 
        Room room306 = new Room ("Room 306"); 
        Room room307 = new Room ("Room 307"); 
        Room room308 = new Room ("Room 308"); 
        Room room309 = new Room ("Room 309"); 
        Room room310 = new Room ("Room 310"); 
        Room room311 = new Room ("Room 311"); 
        Room room312 = new Room ("Room 312"); 
        Room room313 = new Room ("Room 313"); 
        Room room314 = new Room ("Room 314"); 
        Room room315 = new Room ("Room 315"); 
        Room room316 = new Room ("Room 316"); 
        final Room room317 = new Room ("Room 317"); 
        // Floor 7
        Room room702 = new Room ("Room 702") {
            private boolean aActionEnter = true; 
            public void onActionEnter (final Player pPlayer) {
                if (aActionEnter) {
                    aActionEnter = false; 
                    pPlayer.messageFrom("Voice", "Code WHITE restored. Auto-destruction cancelled."); 
                }
            }
        }; 
        Room room705 = new Room ("Room 705"); 
        Room room708 = new Room ("Room 708"); 
        Room room711 = new Room ("Room 711"); 
        Room room713 = new Room ("Room 713"); 
        Room room714 = new Room ("Room 714"); 
        Room room715 = new Room ("Room 715"); 
        final Room room717 = new Room ("Room 717"); 

        // Player
        aPlayer = new Player (room018, 17); 
        requiresUpdate(aPlayer); 
        
        // Switches
        Item switchBlue     = new ItemSwitch (Color.BLUE); 
        Item switchGreen    = new ItemSwitch (Color.GREEN); 
        Item switchRed      = new ItemSwitch (Color.RED); 
        Item switchYellow   = new ItemSwitch (Color.YELLOW); 
        room015.getItemList().addItemSlot(switchBlue,   12); 
        room106.getItemList().addItemSlot(switchBlue,   12); 
        room305.getItemList().addItemSlot(switchBlue,   12); 
        room002.getItemList().addItemSlot(switchGreen,  12); 
        room006.getItemList().addItemSlot(switchGreen,  12); 
        room104.getItemList().addItemSlot(switchGreen,  12); 
        room304.getItemList().addItemSlot(switchGreen,  12); 
        room008.getItemList().addItemSlot(switchRed,    12); 
        room108.getItemList().addItemSlot(switchRed,    12); 
        room312.getItemList().addItemSlot(switchRed,    12); 
        room713.getItemList().addItemSlot(switchRed,    12); 
        room102.getItemList().addItemSlot(switchYellow, 12); 
        room314.getItemList().addItemSlot(switchYellow, 12); 
        room715.getItemList().addItemSlot(switchYellow, 12); 
        
        // Beamers
        Item beamer         = new ItemBeamer (30); 
        room005.getItemList().addItemSlot(beamer,       2); 
        
        // Toolboxes (unlock 3 inventory slots upon use)
        Item toolBox        = new Item ("toolbox", "Using a toolbox would allow you to hold more items in your inventory.") {
            public void onActionUse (final Player pPlayer) {
                pPlayer.message("You have unlocked 3 additional inventory slots! "); 
                pPlayer.getItemList().setMaximumSize(pPlayer.getItemList().getMaximumSize() + 3); 
                pPlayer.getCurrentRoom().getItemList().removeItem(this);
                pPlayer.update(); 
            }
        }; 
        room100.getItemList().addItemSlot(toolBox,      2); 
        room117.getItemList().addItemSlot(toolBox,      22); 
        
        // Cans
        Item can            = new ItemMovable ("can", "It's not really fresh anymore.") {
            public void onActionUse (final Player pPlayer) {
                Item greenGuy = Item.fromString("green guy"); 
                if (pPlayer.getCurrentRoom().getItemList().hasItem(greenGuy)) {
                    pPlayer.getItemList().removeItem(this); 
                    pPlayer.getItemList().addItem(Item.fromString("pass card")); 
                    pPlayer.messageFrom("Green guy", "Thank you! Take this pass card. I'm not sure which door it opens."); 
                    pPlayer.message("You received a pass card! "); 
                    greenGuy.setDescription("I lost 2 other pass cards in this floor. You should probably look for them."); 
                } else {
                    pPlayer.message("I can't use that now. "); 
                }
            }
        }; 
        room023.getItemList().addItemSlot(can,          19); 
        for (int i = 0 ; i < 25 ; i++) {
            if ((i % 5) != 2)
                room100.getItemList().addItemSlot(can,  i); 
        }
        
        // Pass cards (open locked doors)
        Item passCard       = new ItemMovable ("pass card", "It's a pass card. It will probably open a door somewhere. ") {
            public void onActionUse (final Player pPlayer) {
                for (Door door : pPlayer.getCurrentRoom().getExits()) {
                    if (door instanceof DoorLocked)
                        door.onActionOpen(pPlayer); 
                }
            }
        }; 
        room004.getItemList().addItemSlot(passCard,     6); 
        room006.getItemList().addItemSlot(passCard,     16); 

        // Main portal (random teleport)
        Item portal         = new ItemPortal (new Room [] { room114, room300 }); 
        room011.getItemList().addItemSlot(portal,       11); 
        
        // Return portals (teleport back to Main portal)
        Item returnPortal   = new Item ("return portal", "This portal will bring you back to the main portal.") {
            public void onActionUse (final Player pPlayer) {
                pPlayer.changeRoom(room011, 11); 
            }
        }; 
        room114.getItemList().addItemSlot(returnPortal, 12); 
        room300.getItemList().addItemSlot(returnPortal, 12); 
        
        // Portal ring
        final Item portalRing     = new ItemMovable ("portal ring", "This ring can bring you back to the main portal at any time.") {
            public void onActionUse (final Player pPlayer) {
                pPlayer.changeRoom(room011, 12); 
            }
        }; 
        
        // Lifts
        Item liftUp         = new Item ("lift up", "This lift leads to Floor 7.") {
            public void onActionUse (final Player pPlayer) {
                pPlayer.changeRoom(room717, 7); 
            }
        }; 
        room317.getItemList().addItemSlot(liftUp,       12); 
        Item liftDown       = new Item ("lift down", "This lift leads to Floor 3.") {
            public void onActionUse (final Player pPlayer) {
                pPlayer.changeRoom(room317, 17); 
            }
        }; 
        room717.getItemList().addItemSlot(liftDown,     12); 
        
        // Trophee
        final Item trophee        = new ItemMovable ("great can", "So fresh!"); 
        room702.getItemList().addItemSlot(trophee,      2); 
        
        // Holes
        Item hole           = new ItemHole (); 
        room011.getItemList().addItemSlot(hole,         0); 
        room011.getItemList().addItemSlot(hole,         1); 
        room011.getItemList().addItemSlot(hole,         2); 
        room011.getItemList().addItemSlot(hole,         3); 
        room011.getItemList().addItemSlot(hole,         4); 
        room011.getItemList().addItemSlot(hole,         5); 
        room011.getItemList().addItemSlot(hole,         9); 
        room011.getItemList().addItemSlot(hole,         10); 
        room011.getItemList().addItemSlot(hole,         15); 
        room011.getItemList().addItemSlot(hole,         19); 
        room011.getItemList().addItemSlot(hole,         20); 
        room011.getItemList().addItemSlot(hole,         21); 
        room011.getItemList().addItemSlot(hole,         22); 
        room011.getItemList().addItemSlot(hole,         23); 
        room011.getItemList().addItemSlot(hole,         24); 
        
        // Characters
        Character greenGuy  = new CharacterRoaming  (room007,   8,      "Green guy",    "I'm thirsty... I'm sooooooo thirsty...",       20); 
        Character orangeGuy = new CharacterRoaming  (room013,   8,      "Orange guy",   "We're all gonna die! We're all gonna die!",    3) {
            public void update () {
                super.update(); 
                if ((aPlayer.getCurrentRoom() == getCurrentRoom()) && (Randomizer.getRandomInt(15) == 0))
                    aPlayer.messageFrom(getName(), "We're all gonna die!"); 
            }
        }; 
        Character merchant  = new CharacterMerchant (room311,   12); 
        Character pinkGuy   = new Character         (room015,   22,     "Pink guy",     "I've been trapped here for so long. If only I had a beamer..."); 
        Character redGuy    = new Character         (room011,   7,      "Red guy",      "Using this portal is very dangerous. I can give you something useful.") {
            boolean aFirstTime = true; 
            public void onActionTalk (final Player pPlayer) {
                if (aFirstTime) {
                    pPlayer.messageFrom(getName(), "You should keep this in your inventory. It will teleport you here if you get trapped somewhere for some reason."); 
                    if (pPlayer.getItemList().hasFreeSlot()) {
                        pPlayer.getItemList().addItem(portalRing); 
                    } else {
                        pPlayer.getCurrentRoom().getItemList().addItemSlot(portalRing, getRoomLocation()); 
                    }
                    setDescription("Only you can find the Great Can! Good luck!"); 
                    aFirstTime = false; 
                }
            }
        }; 
        requiresUpdate(greenGuy); 
        requiresUpdate(orangeGuy); 
        requiresUpdate(pinkGuy); 
        requiresUpdate(redGuy); 
        requiresUpdate(merchant); 

        // Doors
        // Floor 0
        Door.createDoor(room002,    room003,    Direction.EAST,     Color.RED,      false);
        Door.createDoor(room002,    room007,    Direction.SOUTH,    Color.GREEN,    true);
        Door.createDoor(room003,    room008,    Direction.SOUTH,    Color.NONE,     true);
        Door.createDoor(room004,    room005,    Direction.EAST,     Color.BLUE,     true);
        Door.createDoor(room005,    room010,    Direction.SOUTH,    Color.NONE,     true);
        Door.createDoor(room006,    room007,    Direction.EAST,     Color.GREEN,    false);
        Door.createDoor(room007,    room012,    Direction.SOUTH,    Color.YELLOW,   false);
        Door.createDoor(room008,    room009,    Direction.EAST,     Color.GREEN,    true);
        Door.createDoor(room008,    room013,    Direction.SOUTH,    Color.RED,      true);
        Door.createDoor(room009,    room010,    Direction.EAST,     Color.BLUE,     true);
        Door.createDoor(room010,    room015,    Direction.SOUTH,    Color.BLUE,     false);
        Door.createDoor(room011,    room012,    Direction.EAST,     Color.NONE,     true);
        Door.createDoor(room012,    room013,    Direction.EAST,     Color.RED,      false);
        Door.createDoor(room013,    room018,    Direction.SOUTH,    Color.NONE,     true);
        Door.createDoor(room018,    room023,    Direction.SOUTH,    Color.GREEN,    false);
        // Floor 1
        Door.createDoor(room101,    room102,    Direction.EAST,     Color.RED,      true);
        Door.createDoor(room101,    room104,    Direction.SOUTH,    Color.BLUE,     false);
        Door.createDoor(room102,    room103,    Direction.EAST,     Color.RED,      true);
        Door.createDoor(room102,    room100,    Direction.NORTH,    Color.RED,      false);
        Door.createDoor(room103,    room106,    Direction.SOUTH,    Color.GREEN,    true);
        Door.createDoor(room104,    room107,    Direction.SOUTH,    Color.BLUE,     true);
        Door.createDoor(room106,    room109,    Direction.SOUTH,    Color.GREEN,    false);
        Door.createDoor(room107,    room108,    Direction.EAST,     Color.RED,      false);
        Door.createDoor(room108,    room109,    Direction.EAST,     Color.RED,      true);
        Door.createDoor(room108,    room111,    Direction.SOUTH,    Color.BLUE,     true);
        Door.createDoor(room111,    room114,    Direction.SOUTH,    Color.YELLOW,   true);
        Door.createDoor(room114,    room117,    Direction.SOUTH,    Color.NONE,     true);
        // Floor 3
        Door.createDoor(room300,    room302,    Direction.NORTH,    Color.GREEN,    false);
        Door.createDoor(room301,    room302,    Direction.EAST,     Color.GREEN,    true);
        Door.createDoor(room301,    room305,    Direction.NORTH,    Color.RED,      false);
        Door.createDoor(room302,    room303,    Direction.EAST,     Color.BLUE,     false);
        Door.createDoor(room302,    room306,    Direction.NORTH,    Color.RED,      true);
        Door.createDoor(room303,    room304,    Direction.EAST,     Color.GREEN,    false);
        Door.createDoor(room303,    room307,    Direction.NORTH,    Color.BLUE,     true);
        Door.createDoor(room304,    room308,    Direction.NORTH,    Color.RED,      false);
        Door.createDoor(room305,    room306,    Direction.EAST,     Color.GREEN,    true);
        Door.createDoor(room305,    room309,    Direction.NORTH,    Color.YELLOW,   false);
        Door.createDoor(room306,    room307,    Direction.EAST,     Color.RED,      false);
        Door.createDoor(room306,    room310,    Direction.NORTH,    Color.YELLOW,   true);
        Door.createDoor(room307,    room308,    Direction.EAST,     Color.BLUE,     false);
        Door.createDoor(room307,    room311,    Direction.NORTH,    Color.RED,      true);
        Door.createDoor(room308,    room312,    Direction.NORTH,    Color.RED,      true);
        Door.createDoor(room309,    room310,    Direction.EAST,     Color.GREEN,    false);
        Door.createDoor(room309,    room313,    Direction.NORTH,    Color.GREEN,    true);
        Door.createDoor(room310,    room311,    Direction.EAST,     Color.BLUE,     true);
        Door.createDoor(room310,    room314,    Direction.NORTH,    Color.YELLOW,   false);
        Door.createDoor(room311,    room312,    Direction.EAST,     Color.GREEN,    false);
        Door.createDoor(room311,    room315,    Direction.NORTH,    Color.GREEN,    false);
        Door.createDoor(room312,    room316,    Direction.NORTH,    Color.YELLOW,   false);
        Door.createDoor(room313,    room314,    Direction.EAST,     Color.BLUE,     false);
        Door.createDoor(room314,    room315,    Direction.EAST,     Color.BLUE,     true);
        Door.createDoor(room315,    room316,    Direction.EAST,     Color.YELLOW,   true);
        Door.createDoor(room315,    room317,    Direction.NORTH,    Color.GREEN,    true);
        // Floor 7
        Door.createDoor(room702,    room705,    Direction.SOUTH,    Color.RED,      false); 
        Door.createDoor(room705,    room708,    Direction.SOUTH,    Color.RED,      true);
        Door.createDoor(room708,    room711,    Direction.SOUTH,    Color.YELLOW,   false);
        Door.createDoor(room711,    room714,    Direction.SOUTH,    Color.YELLOW,   true);
        Door.createDoor(room714,    room713,    Direction.WEST,     Color.YELLOW,   false);
        Door.createDoor(room714,    room715,    Direction.EAST,     Color.YELLOW,   false);
        Door.createDoor(room714,    room717,    Direction.SOUTH,    Color.BLUE,     false);
        
        // Locked Doors
        Door lockedDoor005  = new DoorLocked (room005, passCard); 
        room010.setExit(Direction.NORTH, lockedDoor005); 
        Door lockedDoor011  = new DoorLocked (room011, passCard); 
        room012.setExit(Direction.WEST, lockedDoor011); 
        Door lockedDoor117  = new DoorLocked (room117, passCard) {
            @Override
        	public void onActionOpen (final Player pPlayer) {
        	    Item.fromString("Green guy").setDescription("I heard there is a merchant roaming in the 3rd floor, who exchanges 7 cans for a beamer."); 
        	    super.onActionOpen(pPlayer); 
        	}
        }; 
        room114.setExit(Direction.SOUTH, lockedDoor117); 
        
        // Intro message
        aPlayer.message("Welcome to Projection!"); 
        aPlayer.showCommands(); 
    }
}
