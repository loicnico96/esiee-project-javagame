
package pkg_items;

import java.util.HashMap;

import pkg_engine.Color;
import pkg_engine.Item;
import pkg_engine.Player;

/**
 * Class for Switches, immobile colored items that inverse the status of the doors of same color when activated. 
 * @author Loic
 */
public class ItemSwitch extends Item {
	private static HashMap<Color, Boolean> sColors; 
	static {
		sColors = new HashMap<Color, Boolean> (); 
		for (Color color : Color.values()) {
			sColors.put(color, false); 
		}
	}
	
	/**
	 * Checks whether switches of the specified Colors are activated. 
	 */
	public static boolean isColorActivated (final Color pColor) {
		return sColors.get(pColor); 
	}
	
	/**
	 * Inverses switches and doors of the specified Color. 
	 */
	public static void inverseColor (final Color pColor) {
		sColors.put(pColor, !sColors.get(pColor)); 
	}
	
	private Color aColor; 

	/**
	 * Constructor 
	 * @param pColor Color of the switch
	 */
	public ItemSwitch (final Color pColor) {
		super (pColor.getName() + " switch", ""); 
		aColor = pColor; 
	}
	
	/**
	 * Checks whether the switch is activated. 
	 */
	public boolean isActivated () {
		return isColorActivated(aColor); 
	}

	/**
	 * Returns the item's description. 
	 */
	@Override
	public String getDescription () {
		String format = "This artefact emits a %s %s light. Touching it will inverse all the doors of that color. "; 
		return String.format(format, (isActivated()? "radiant" : "dim"), aColor.getName()); 
	}
	
	/**
	 * Returns the image name for this item. 
	 */
	@Override
	public String getImageName () {
		String format = isActivated()? "item_%s_active.png" : "item_%s.png"; 
		return String.format(format, getName().replace(" ", "_")); 
	}
	
	/**
	 * Action performed by a Use command. 
	 */
	@Override
	public void onActionUse (final Player pPlayer) {
		inverseColor(aColor); 
		pPlayer.message(String.format("The artefact is now %s. ", isActivated()? "activated" : "deactivated")); 
		pPlayer.update(); 
	}
}
