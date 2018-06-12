
package pkg_engine;

import java.util.Random; 

/**
 * Utility class with static methods for randomization. 
 * @author Loic
 */
public abstract class Randomizer {
	private static Random sRandom = new Random (); 
	
	/**
	 * Returns a random boolean. 
	 */
	public static boolean getRandomBoolean () {
		return sRandom.nextBoolean(); 
	}
	
	/**
	 * Returns a random integer value between 0 (included) and a maximum (excluded). 
	 */
	public static int getRandomInt (final int pMax) {
		return sRandom.nextInt(pMax); 
	}
	
	/**
	 * Returns a random element from an array. 
	 */
	public static <T> T getRandomElement (final T[] pArray) {
		return (pArray.length > 0)? pArray[getRandomInt(pArray.length)] : null; 
	}
}
