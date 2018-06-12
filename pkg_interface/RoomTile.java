
package pkg_interface;

import java.awt.GridBagConstraints;
import java.awt.event.MouseEvent;

import pkg_engine.Direction;
import pkg_engine.Door;
import pkg_engine.Item;

/**
 * Tiles for the room. 
 * @author Loic
 */
public class RoomTile extends Tile {
	private int aIndex; 

	/**
	 * Constructor
	 * @param pInterface	Owning interface
	 * @param pIndex		Tile index
	 * @param pConstraints	Position in the panel
	 */
	public RoomTile(final PlayerInterface pInterface, final int pIndex, final GridBagConstraints pConstraints) {
		super (pInterface, pConstraints);
		aIndex = pIndex; 
	}
	
	/**
	 * Returns background name for ground. 
	 */
	public String getGroundName () {
		return "room_ground.png"; 
	}
	
	/**
	 * Returns background name for walls. 
	 */
	public String getWallName (final Direction pDirection) {
		return String.format("room_wall_%s.png", pDirection.getName().toLowerCase()); 
	}

	/**
	 * Returns background name for corners. 
	 */
	public String getCornerName (final Direction pDirection1, final Direction pDirection2) {
		return String.format("room_wall_%s_%s.png", pDirection1.getName().toLowerCase(), pDirection2.getName().toLowerCase()); 
	}
	
	/**
	 * Returns background name for doors. 
	 */
	public String getDoorName (final Direction pDirection) {
		Door door = getInterface().getPlayer().getCurrentRoom().getExit(pDirection); 
		if (door != null) {
			return door.getImageName(pDirection); 
		} else { // No door
			return getWallName(pDirection); 
		}
	}
	
	/**
	 * Returns background name. 
	 */
	@Override
	public String getBackgroundName() {
		int raw	= aIndex / 7; 
		int col	= aIndex % 7; 
		switch (raw) {
			case 0:
				switch (col) {
					case 0:
						return getCornerName(Direction.NORTH, Direction.WEST); 
					case 3:
						return getDoorName(Direction.NORTH); 
					case 6:
						return getCornerName(Direction.NORTH, Direction.EAST); 
					default:
						return getWallName(Direction.NORTH); 
				}
			case 3:
				switch (col) {
					case 0:
						return getDoorName(Direction.WEST); 
					case 6:
						return getDoorName(Direction.EAST); 
					default:
						return getGroundName(); 
				}
			case 6:
				switch (col) {
					case 0:
						return getCornerName(Direction.SOUTH, Direction.WEST); 
					case 3:
						return getDoorName(Direction.SOUTH); 
					case 6:
						return getCornerName(Direction.SOUTH, Direction.EAST); 
					default:
						return getWallName(Direction.SOUTH); 
				}
			default:
				switch (col) {
					case 0:
						return getWallName(Direction.WEST); 
					case 6:
						return getWallName(Direction.EAST); 
					default:
						return getGroundName(); 
				}
		}
	}

	/**
	 * Returns image name for the contained item (if any). 
	 */
	@Override
	public String getContentName() {
		int raw	= aIndex / 7; 
		int col	= aIndex % 7; 
		if ((raw > 0) && (raw < 6) && (col > 0) && (col < 6)) {
			int itemIndex = (raw - 1) * 5 + (col - 1); 
			Item item = getInterface().getPlayer().getCurrentRoom().getItemList().getItem(itemIndex); 
			if (item != null) {
				return item.getImageName(); 
			} else { // Empty
				return ""; 
			}
		} else { // Out of range
			return ""; 
		}
	}

	/**
	 * Action on click. 
	 */
	@Override
	public void onClick (final MouseEvent pEvent) {
		int raw	= aIndex / 7; 
		int col	= aIndex % 7; 
		if (raw == 0) {
			if (col == 3) {
				onClickDoor(Direction.NORTH, pEvent); 
			} else {
				onClickWall(pEvent); 
			}
		} else if (raw == 6) {
			if (col == 3) {
				onClickDoor(Direction.SOUTH, pEvent); 
			} else {
				onClickWall(pEvent); 
			}
		} else if (raw == 3) {
			if (col == 0) {
				onClickDoor(Direction.WEST, pEvent); 
			} else if (col == 6) {
				onClickDoor(Direction.EAST, pEvent); 
			} else {
				onClickGround(pEvent); 
			}
		} else {
			if ((col == 0) || (col == 6)) {
				onClickWall(pEvent); 
			} else {
				onClickGround(pEvent); 
			}
		}
	}
		
	/** 
	 * Action on click on the ground. 
	 */
	public void onClickGround (final MouseEvent pEvent) {
		int raw	= aIndex / 7; 
		int col	= aIndex % 7; 
		int itemIndex = (raw - 1) * 5 + (col - 1); 
		Item item = getInterface().getPlayer().getCurrentRoom().getItemList().getItem(itemIndex); 
		if (item != null) {
			if (pEvent.getClickCount() % 2 == 0) {
				item.onDoubleClickGround(getInterface().getPlayer(), itemIndex);
				getInterface().update(); 
			} else {
				item.onActionInspect(getInterface().getPlayer());
			}
		}
	}
	
	/**
	 * Action on click on walls. 
	 */
	public void onClickWall (final MouseEvent pEvent) {
		getInterface().getPlayer().message("This is just a wall... "); 
	}
	
	/**
	 * Action on click on doors. 
	 */
	public void onClickDoor (final Direction pDirection, final MouseEvent pEvent) {
		Door door = getInterface().getPlayer().getCurrentRoom().getExit(pDirection); 
		if (door != null) {
			if (pEvent.getClickCount() % 2 == 0) {
				getInterface().getPlayer().goRoom(pDirection); 
			} else if (door.isOpened(getInterface().getPlayer())) {
				getInterface().getPlayer().message("This door is opened. "); 
			} else {
				getInterface().getPlayer().message("This door is closed. "); 
			}
		} else {
			onClickWall(pEvent); 
		}
	}
}
