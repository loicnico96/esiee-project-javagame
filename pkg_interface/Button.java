
package pkg_interface;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

import pkg_engine.Player;

/**
 * Class for all interface buttons. 
 * @author Loic
 */
public class Button extends Element {
    private JButton             aButton; 
    private PlayerPredicate     aIsActive; 
    
    /**
     * Constructor
     * @param pInterface        Owning interface
     * @param pActionCommand    Action command
     * @param pTooltip          Tooltip displayed when hovering
     * @param pConstraints      Position in the panel
     * @param pIsActive         Condition for being active (null if always active)
     */
    public Button (final PlayerInterface pInterface, final String pActionCommand, final String pTooltip, final GridBagConstraints pConstraints, final PlayerPredicate pIsActive) {
        super (pInterface); 
        aButton     = new JButton (); 
        aIsActive   = pIsActive; 
        aButton.setActionCommand(pActionCommand); 
        aButton.setToolTipText(pTooltip);
        aButton.setPreferredSize(new Dimension(60, 60));
        // Adding to interface
        getInterface().getPanel().add(aButton, pConstraints); 
        // Controls
        aButton.addKeyListener(getInterface().getKeyboardControls());
        aButton.addActionListener(new ActionListener () {
            public void actionPerformed (final ActionEvent pEvent) {
                if (getInterface().isInputEnabled()) {
                    getInterface().getPlayer().processCommand(aButton.getActionCommand()); 
                }
            }
        }); 
    }
    
    /**
     * Checks whether the button is active. 
     */
    public boolean isActive () {
        return (aIsActive != null)? aIsActive.test(getInterface().getPlayer()) : true; 
    }
    
    /**
     * Updates the button. 
     */
    @Override
    public void update () {
        aButton.setIcon(getInterface().getCache().getImage(getImageName())); 
    }
    
    /**
     * Returns the image name for the button. 
     */
    public String getImageName () {
        String format = isActive()? "button_%s.png" : "button_%s_disabled.png"; 
        return String.format(format, aButton.getActionCommand().replace(" ", "_")); 
    }
}
