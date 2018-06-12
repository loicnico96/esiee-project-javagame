
package pkg_engine;

/**
 * Character moving randomly in his room. 
 * @author Loic
 */
public class CharacterRoaming extends Character {
	private int aInterval; 
	private int aCount; 
	
	/**
	 * Constructor
	 * @param pRoom			Room
	 * @param pRoomLocation	Location in the room
	 * @param pName			Character name
	 * @param pDescription	Character talk
	 * @param pInterval		Interval between moves (per 0.1s)
	 */
	public CharacterRoaming (final Room pRoom, final int pRoomLocation, final String pName, final String pDescription, final int pInterval) {
		super(pRoom, pRoomLocation, pName, pDescription);
		aInterval	= pInterval; 
		aCount		= 0; 
	}

	/**
	 * Updates position. 
	 */
	@Override
	public void update () {
		aCount++; 
		if (aCount % aInterval == 0)
			moveRandom(); 
	}
}
