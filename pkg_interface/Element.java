
package pkg_interface;

import pkg_engine.Updatable;

/**
 * Superclass for custom interface elements. 
 * @author Loic
 */
public abstract class Element implements Updatable {
	private PlayerInterface	aInterface; 

	/**
	 * Constructor
	 * @param pInterface	Owning interface
	 */
	public Element (final PlayerInterface pInterface) {
		aInterface = pInterface; 
	}
	
	/**
	 * Returns owning interface. 
	 */
	public PlayerInterface getInterface () {
		return aInterface; 
	}
}
