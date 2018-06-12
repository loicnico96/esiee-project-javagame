
package pkg_interface; 

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

/**
 * Small parts of rooms and inventory that can contain a single item image. 
 * @author Loic
 */
public abstract class Tile extends Element {
	private JLabel			aBackground; 
	private JLabel			aContent; 
	
	/**
	 * Constructor
	 * @param pInterface	Owning interface
	 * @param pConstraints	Position in the panel
	 */
	public Tile (final PlayerInterface pInterface, final GridBagConstraints pConstraints) {
		super (pInterface); 
		// Background image
		aBackground		= new JLabel (); 
		aBackground.setPreferredSize(new Dimension(60, 60)); 
		// Content image
		aContent		= new JLabel (); 
		aContent.setPreferredSize(new Dimension(60, 60)); 
		// Adding to interface
		getInterface().getPanel().add(aBackground, pConstraints); 
		getInterface().getPanel().add(aContent, pConstraints); 
		getInterface().getPanel().setComponentZOrder(aBackground, 1); 
		getInterface().getPanel().setComponentZOrder(aContent, 0); 
		// Controls
		aBackground.addKeyListener(getInterface().getKeyboardControls());
		aBackground.addMouseListener (new MouseAdapter () {
			public void mouseClicked (final MouseEvent pEvent) {
				if (getInterface().isInputEnabled()) {
					aBackground.requestFocus(); 
					onClick(pEvent); 
				}
			}
		});
	}
	
	/**
	 * Updates images. 
	 */
	@Override
	public void update () {
		aBackground.setIcon(getInterface().getCache().getImage(getBackgroundName())); 
		aContent.setIcon(getInterface().getCache().getImage(getContentName())); 
	}

	/**
	 * Returns background name. 
	 */
	public abstract String getBackgroundName (); 

	/**
	 * Returns image name for the contained item (if any). 
	 */
	public abstract String getContentName (); 

	/**
	 * Action on click. 
	 */
	public abstract void onClick (final MouseEvent pEvent); 
}

