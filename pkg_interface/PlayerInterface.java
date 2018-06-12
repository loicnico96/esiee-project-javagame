
package pkg_interface;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import pkg_engine.Direction;
import pkg_engine.Door;
import pkg_engine.Player;
import pkg_engine.Updatable;

public class PlayerInterface implements Updatable {
	private Player				aPlayer; 
	private Cache				aCache; 
	private Stack<String>		aCommandLines; 
	private boolean				aInputEnabled; 
	private JFrame				aFrame; 
	private JPanel				aPanel; 
	private JTextArea			aTextArea; 
	private JTextField			aTextField; 
	private LinkedList<Element>	aElements; 
	private KeyListener			aKeyboardControls; 
	
	/**
	 * Constructor
	 * @param pPlayer Player attached to the interface
	 */
	public PlayerInterface (final Player pPlayer) {
		aPlayer			= pPlayer; 
		aCache			= new Cache ("images/"); 
		aCommandLines	= new Stack<String> (); 
		this.setup(); 
		this.update(); 
	}
	
	/**
	 * Sets up the GUI. 
	 */
	private void setup () {
		// Frame
		aFrame			= new JFrame ("Projection"); 
		aFrame.setResizable(false); 
		aFrame.addWindowListener (new WindowAdapter () {
			public void windowClosing (final WindowEvent pEvent) {
				onExit(); 
			}
		}); 
		
		// Keyboard controls
		aKeyboardControls = new KeyAdapter () {
			public void keyPressed (final KeyEvent pEvent) {
				if (isInputEnabled()) {
					switch (pEvent.getKeyCode()) {
					case KeyEvent.VK_ENTER:
						aTextField.requestFocus(); 
						break; 
					case KeyEvent.VK_A:
						aPlayer.onActionKeyboard(); 
						break; 
					case KeyEvent.VK_H:
						aPlayer.showCommands(); 
						break; 
					case KeyEvent.VK_I:
						aPlayer.inspectCurrentRoom(); 
						break; 
					case KeyEvent.VK_B:
						aPlayer.goBack(); 
						break; 
					case KeyEvent.VK_X:
						aPlayer.quit(2); 
						break; 
					case KeyEvent.VK_1:
					case KeyEvent.VK_NUMPAD1:
						aPlayer.useItemSlot(0); 
						break; 
					case KeyEvent.VK_2:
					case KeyEvent.VK_NUMPAD2:
						aPlayer.useItemSlot(1); 
						break; 
					case KeyEvent.VK_3:
					case KeyEvent.VK_NUMPAD3:
						aPlayer.useItemSlot(2); 
						break; 
					case KeyEvent.VK_4:
					case KeyEvent.VK_NUMPAD4:
						aPlayer.useItemSlot(3); 
						break; 
					case KeyEvent.VK_5:
					case KeyEvent.VK_NUMPAD5:
						aPlayer.useItemSlot(4); 
						break; 
					case KeyEvent.VK_6:
					case KeyEvent.VK_NUMPAD6:
						aPlayer.useItemSlot(5); 
						break; 
					case KeyEvent.VK_7:
					case KeyEvent.VK_NUMPAD7:
						aPlayer.useItemSlot(6); 
						break; 
					case KeyEvent.VK_8:
					case KeyEvent.VK_NUMPAD8:
						aPlayer.useItemSlot(7); 
						break; 
					case KeyEvent.VK_9:
					case KeyEvent.VK_NUMPAD9:
						aPlayer.useItemSlot(8); 
						break; 
					case KeyEvent.VK_UP:
					case KeyEvent.VK_KP_UP:
					case KeyEvent.VK_Z:
						aPlayer.move(Direction.NORTH); 
						break; 
					case KeyEvent.VK_RIGHT:
					case KeyEvent.VK_KP_RIGHT:
					case KeyEvent.VK_D:
						aPlayer.move(Direction.EAST); 
						break; 
					case KeyEvent.VK_DOWN:
					case KeyEvent.VK_KP_DOWN:
					case KeyEvent.VK_S:
						aPlayer.move(Direction.SOUTH); 
						break; 
					case KeyEvent.VK_LEFT:
					case KeyEvent.VK_KP_LEFT:
					case KeyEvent.VK_Q:
						aPlayer.move(Direction.WEST); 
						break; 
					}
				}
			}
		}; 
		
		// Panel and layout
		aPanel				= new JPanel (); 
		aPanel.setLayout(new GridBagLayout ()); 
		aPanel.addKeyListener(aKeyboardControls); 
		GridBagConstraints constraints = new GridBagConstraints (); 
		constraints.fill = GridBagConstraints.BOTH; 
		
		// Elements
		aElements				= new LinkedList<Element> (); 
		
		// Buttons
		constraints.gridx 		= 1; 
		constraints.gridy		= 0; 
		constraints.gridwidth	= 7; 
		constraints.gridheight	= 1; 
		aElements.push(new Button(this, "go north", "Go North", constraints, new PlayerPredicate () {
			public boolean test (final Player pPlayer) {
				Door door = pPlayer.getCurrentRoom().getExit(Direction.NORTH); 
				return (door != null) && door.isOpened(pPlayer); 
			}
		}));
		constraints.gridx 		= 8; 
		constraints.gridy		= 1; 
		constraints.gridwidth	= 1; 
		constraints.gridheight	= 7; 
		aElements.push(new Button(this, "go east", "Go East", constraints, new PlayerPredicate () {
			public boolean test (final Player pPlayer) {
				Door door = pPlayer.getCurrentRoom().getExit(Direction.EAST); 
				return (door != null) && door.isOpened(pPlayer); 
			}
		}));
		constraints.gridx 		= 1; 
		constraints.gridy		= 8; 
		constraints.gridwidth	= 7; 
		constraints.gridheight	= 1; 
		aElements.push(new Button(this, "go south", "Go South", constraints, new PlayerPredicate () {
			public boolean test (final Player pPlayer) {
				Door door = pPlayer.getCurrentRoom().getExit(Direction.SOUTH); 
				return (door != null) && door.isOpened(pPlayer); 
			}
		}));
		constraints.gridx 		= 0; 
		constraints.gridy		= 1; 
		constraints.gridwidth	= 1; 
		constraints.gridheight	= 7; 
		aElements.push(new Button(this, "go west", "Go West", constraints, new PlayerPredicate () {
			public boolean test (final Player pPlayer) {
				Door door = pPlayer.getCurrentRoom().getExit(Direction.WEST); 
				return (door != null) && door.isOpened(pPlayer); 
			}
		}));
		constraints.gridx 		= 0; 
		constraints.gridy		= 0; 
		constraints.gridwidth	= 1; 
		constraints.gridheight	= 1; 
		aElements.push(new Button(this, "show commands", "Help [H]", constraints, null));
		constraints.gridx 		= 8; 
		constraints.gridy		= 0; 
		constraints.gridwidth	= 1; 
		constraints.gridheight	= 1; 
		aElements.push(new Button(this, "inspect room", "Info [I]", constraints, null));
		constraints.gridx 		= 0; 
		constraints.gridy		= 8; 
		constraints.gridwidth	= 1; 
		constraints.gridheight	= 1; 
		aElements.push(new Button(this, "go back", "Back [B]", constraints, new PlayerPredicate () {
			public boolean test (final Player pPlayer) {
				return pPlayer.canGoBack(); 
			}
		}));
		constraints.gridx 		= 8; 
		constraints.gridy		= 8; 
		constraints.gridwidth	= 1; 
		constraints.gridheight	= 1; 
		aElements.push(new Button(this, "quit", "Exit [X]", constraints, null));
		
		// Background
		for (int i = 0 ; i < 49 ; i++) { // 7 * 7 tiles
			constraints.gridx 		= 1 + (i % 7); 
			constraints.gridy		= 1 + (i / 7); 
			constraints.gridwidth	= 1; 
			constraints.gridheight	= 1; 
			aElements.push(new RoomTile (this, i, constraints)); 
		}
		
		// Inventory
		for (int i = 0 ; i < 9 ; i++) {
			constraints.gridx 		= 9; 
			constraints.gridy		= i; 
			constraints.gridwidth	= 1; 
			constraints.gridheight	= 1; 
			aElements.push(new InventoryTile (this, i, constraints)); 
		}
		
		// Text field
		constraints.gridx 		= 0; 
		constraints.gridy		= 9; 
		constraints.gridwidth	= 10; 
		constraints.gridheight	= 1; 
		aTextField				= new JTextField (); 
		aTextField.requestFocus(); 
		aTextField.addActionListener (new ActionListener () {
			public void actionPerformed (final ActionEvent pEvent) {
				if (isInputEnabled()) {
					String commandLine = aTextField.getText(); 
					if (!commandLine.isEmpty()) {
						aCommandLines.push(commandLine); 
						aPlayer.processCommand(commandLine); 
						aTextField.setText(""); 
						aPanel.requestFocus(); 
					}
				}
			}
		}); 
		aTextField.addKeyListener (new KeyAdapter () {
			public void keyPressed (final KeyEvent pEvent) {
				if (isInputEnabled() && !aCommandLines.empty()) {
					switch (pEvent.getKeyCode()) {
						case KeyEvent.VK_UP:
						case KeyEvent.VK_KP_UP:
							aTextField.setText(aCommandLines.pop()); 
							break; 
					}
				}
			}
		}); 
		aPanel.add(aTextField, constraints); 
		
		// Text area
		constraints.gridx 		= 0; 
		constraints.gridy		= 10; 
		constraints.gridwidth	= 10; 
		constraints.gridheight	= 1;  
		aTextArea				= new JTextArea (); 
        JScrollPane scroller	= new JScrollPane(aTextArea);
		scroller.setPreferredSize(new Dimension(0, 120));
		aPanel.add(scroller, constraints); 
		aTextArea.setLineWrap(true); 
		aTextArea.setWrapStyleWord(true); 
		aTextArea.setEditable(false); 
		
		// Display
		aFrame.getContentPane().add(aPanel, BorderLayout.CENTER);
		aFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		aFrame.setVisible(true);
		enableInput(true); 
		aFrame.pack(); 
	
		// Focus on panel
		aPanel.requestFocus(); 
	}

	/**
	 * Updates all GUI elements. 
	 */
	@Override
	public void update () {
		for (Element element : aElements) {
			element.update(); 
		}
	}

	/**
	 * Returns the player attached to this interface. 
	 */
	public Player getPlayer () {
		return aPlayer; 
	}
	
	/**
	 * Returns the image cache for this interface. 
	 */
	public Cache getCache () {
		return aCache; 
	}
	
	/**
	 * Returns the panel for this interface. 
	 */
	public JPanel getPanel () {
		return aPanel; 
	}
	
	/**
	 * Returns the keyboard controls for this interface. 
	 */
	public KeyListener getKeyboardControls () {
		return aKeyboardControls; 
	}
	
	/**
	 * Action performed upon closing the window. 
	 */
	public void onExit () {
		System.exit(0); 
	}
	
	/**
	 * Checks whether inputs are enabled. 
	 */
	public boolean isInputEnabled () {
		return aInputEnabled; 
	}
	
	/**
	 * Enables or disables inputs. 
	 */
	public void enableInput (final boolean pEnabled) {
		aInputEnabled = pEnabled; 
		aTextField.setEnabled(pEnabled); 
		if (!pEnabled) {
			aTextField.getCaret().setBlinkRate(0); 
		}
	}
	
	/**
	 * Prints a message. 
	 */
	public void printMessage (final String pMessage) {
		aTextArea.append(pMessage + "\n"); 
		aTextArea.setCaretPosition(aTextArea.getDocument().getLength()); 
	}
}
