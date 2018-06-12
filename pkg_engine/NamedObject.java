
package pkg_engine;

/**
 * All game objects that have a name: items, rooms, players...
 * @author Loic
 */
public abstract class NamedObject {
	private String aName; 
	
	/**
	 * Constructor
	 * @param pName		Object name
	 */
	public NamedObject (final String pName) {
		this.aName = pName; 
	}
	
	/**
	 * Returns this Object's name. 
	 */
	public String getName () {
		return this.aName; 
	}
}
