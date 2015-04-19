/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.podkidnoy;

import java.awt.Color;

import com.littlech.cl.gui.podkidnoy.constants.*;
import java.awt.TrayIcon.MessageType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

import org.netbeans.lib.awtextra.AbsoluteConstraints;

import com.littlech.cl.IImageLoader;
import com.littlech.cl.constants.Sizes;
import com.littlech.cl.gui.podkidnoy.component.ButtonsPanel;
import com.littlech.cl.gui.podkidnoy.component.CardsPanel;
import com.littlech.cl.gui.podkidnoy.component.ControlPanel;
import com.littlech.cl.gui.podkidnoy.component.PackAndTrumpBox;
import com.littlech.cl.gui.podkidnoy.component.SeatBox;
import com.littlech.cl.gui.podkidnoy.component.SitButton;
import com.littlech.cl.gui.podkidnoy.component.StartButton;
import com.littlech.cl.gui.podkidnoy.component.TablePanel;
import com.littlech.gen.f.F1;

/**
 * 
 * Screen for Podkidnoy GUI
 * 
 * @author Kristjan Veskim�e
 *
 */
public class PodkidnoyScreen {

	/**
	 * Pack and trump component
	 */
	private PackAndTrumpBox packAndTrump;
	
	/**
	 * Player cards component
	 */
	private CardsPanel cards;
	
	/**
	 * Table cards component
	 */
	private TablePanel table;
	
	/**
	 * Action buttons component
	 */
	private ButtonsPanel buttons;
	
	/**
	 * Start and leave buttons component
	 */
	// private StartAndLeave startAndLeave;
	
	/**
	 * Info text area component
	 */
	private JTextPane infoPane = new JTextPane();
	
	/**
	 * Info text area scroll pane
	 */
	private JScrollPane infoScrollPane = new JScrollPane();
	
	/**
	 * Screen background image
	 */
	private JLabel carpet = new JLabel();
	
	/**
	 * Player box components for seats
	 */
	private Map<F1, SeatBox> seats = new HashMap<F1, SeatBox>();

	/**
	 * Image loader
	 */
	protected IImageLoader mImageLoader;
	
	/**
	 * Base panel where components are added
	 */
	protected JPanel panel = new JPanel();
	
	private AbsoluteConstraints infoConstraint = new AbsoluteConstraints(PodkidnoyLocations.PODKIDNOY_INFO, PodkidnoySizes.PODKIDNOY_INFO);
	
	private ControlPanel controlPanel;
	
	private StartButton startButton;
	
	private JLabel copyRightLabel;
	
	private AbsoluteConstraints copyRightConstraint = new AbsoluteConstraints(PodkidnoyLocations.COPYRIGHT, PodkidnoySizes.COPYRIGHT);
	
	/**
	 * 
	 * Constructor
	 * 
	 * @param imageLoader Image loader
	 */
	public PodkidnoyScreen(IImageLoader imageLoader) {
		mImageLoader = imageLoader;
		panel.setBackground(Color.GRAY);
		panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
		panel.setSize(Sizes.APPLET);

		buttons = new ButtonsPanel(mImageLoader);
		table = new TablePanel(mImageLoader);
		cards = new CardsPanel(mImageLoader);
		packAndTrump = new PackAndTrumpBox(mImageLoader);
		// startAndLeave = new StartAndLeave(mImageLoader);
		controlPanel = new ControlPanel(mImageLoader);
		startButton = new StartButton(mImageLoader);
		
		// initPlayerBoxes(); // _self);
		initCarpet();
		initInfo();
		// initControl();
	}
	
	public StartButton getStartButton() {
		return startButton;
	}
	
	public ControlPanel getControlPanel() {
		return controlPanel;
	}

	public void finishLayout() {
		panel.add(infoScrollPane, infoConstraint);
	
		Iterator<F1> itIDs = getSeatIDIterator();
		while (itIDs.hasNext()) {
			F1 id = itIDs.next();
			SeatBox otherBox = getSeats().get(id);
			panel.add(otherBox.getPanel(), otherBox.getConstraint());
		}
		
		panel.add(getStartButton().getPanel(), getStartButton().getConstraint());
		
		panel.add(buttons.getPanel(), buttons.getConstraint());
		panel.add(table.getPanel(), table.getConstraint());
		panel.add(cards.getPanel(), cards.getConstraint());
		panel.add(packAndTrump.getPanel(), packAndTrump.getConstraint());
		
		panel.add(controlPanel.getPanel(), controlPanel.getConstraint());
		controlPanel.setVisible(true);
		
		panel.add(copyRightLabel, copyRightConstraint);
		
		/*
		panel.add(startAndLeave.getPanel(), startAndLeave.getConstraint());
		startAndLeave.setVisible(true);
		*/
		/*
		panel.add(closeButton.getPanel(), new AbsoluteConstraints(PodkidnoyLocations.CONTROL_1, PodkidnoySizes.CONTROL_BUTTON));
		panel.add(optionsButton.getPanel(), new AbsoluteConstraints(PodkidnoyLocations.CONTROL_2, PodkidnoySizes.CONTROL_BUTTON));
		panel.add(controlBG, new AbsoluteConstraints(PodkidnoyLocations.CONTROL_BG, PodkidnoySizes.CONTROL_BG));
		*/
		panel.add(carpet, new AbsoluteConstraints(0, 0, -1, -1));
	}

	/**
	 * Returns base panel where components are placed
	 * @return Base panel
	 */
	public JPanel getPanel() {
		return panel;
	}

	/**
	 * Enables or disables game components
	 * @param b Set true to enable screen or false to disable
	 */
	public void setEnabled(boolean b) {
		table.setEnabled(b);
		buttons.setEnabled(b);
		cards.setEnabled(b);
	}

	/**
	 * Shows or hides screen components 
	 * @param b Set true to make screen components visible or false to hide screen components
	 */
	public void setVisible(boolean b) {
		buttons.setVisible(b);
		table.setVisible(b);
		cards.setVisible(b);
		packAndTrump.setVisible(b);
		// startAndLeave.setVisible(b);
	}

	/**
	 * Disables all game components
	 */
	public void disableAll() {
		table.setEnabled(false);
		buttons.setEnabled(false);
		cards.setEnabled(false);
	}

	/**
	 * Returns player boxes
	 * @return Map of seat ID codes and corresponding player boxes
	 */
	public Map<F1, SeatBox> getSeats() {
		return seats;
	}

	/**
	 * Returns player box for parameter seat
	 * @param id Seat ID code
	 * @return Player box
	 */
	public SeatBox getSeat(final F1 id) {
		SeatBox ret = seats.get(id);
		return ret;
	}

	/**
	 * Initializes screen background image
	 */
	private void initCarpet() {
		ImageIcon copyRightIcon = mImageLoader.fetchImage(PodkidnoyImages.COPYRIGHT);
		copyRightLabel = new JLabel(copyRightIcon);
		carpet.setIcon(mImageLoader.fetchImage(PodkidnoyImages.PODKIDNOY));
	}
	
	/**
	 * Initializes control buttons background image
	 */
	/*
	private void initControl() {
		optionsButton = new ControlButton(mImageLoader, ControlButtonType.OPTIONS);
		closeButton = new ControlButton(mImageLoader, ControlButtonType.CLOSE);
		controlBG.setIcon(mImageLoader.fetchImage(PodkidnoyImages.CONTROL_BG));
	}
	*/

	/**
	 * Initializes player boxes
	 */
	/*
	private void initPlayerBoxes() {
		Iterator<SeatIDCode> itIDs = getSeatIDIterator();
		while (itIDs.hasNext()) {
			SeatIDCode id = itIDs.next();
			PlayerBox otherBox = new PlayerBox(this.mImageLoader, id);
			getSeats().put(id, otherBox);
		}
	}
	*/
	
	private Iterator<F1> getSeatIDIterator() {
		ArrayList<F1> seatIDs = new ArrayList<F1>();
		seatIDs.add(F1.F_2);
		seatIDs.add(F1.F_3);
		seatIDs.add(F1.F_4);
		seatIDs.add(F1.F_5);
		Iterator<F1> itIDs = seatIDs.iterator();
		return itIDs;
	}

	/**
	 * Initializes info messages area
	 */
	private void initInfo() {
		infoPane.setEditable(false);
		infoPane.setContentType("text/html");
		HTMLEditorKit kit = new HTMLEditorKit();
		StyleSheet css = new StyleSheet();
		css.addRule("BODY{ margin : 0;}");
		css.addRule("P{ margin : 0;}");
		css.addRule("A{ color:#0000FF; text-decoration:underline;}");
		kit.setStyleSheet(css);
		infoPane.setEditorKit(kit);
		infoPane.setBackground(PodkidnoyColors.PODKIDNOY_MESSAGES_BG);

		infoPane.setBackground(new java.awt.Color(81, 87, 77));
		infoPane.setBorder(null);
		infoPane.setEditable(false);
		infoPane.setFont(new java.awt.Font("Arial", 0, 12));
		infoPane.setForeground(new java.awt.Color(137, 220, 241));
		infoScrollPane.setViewportView(infoPane);

		// panel.add(infoScrollPane, new AbsoluteConstraints(PodkidnoyLocations.PODKIDNOY_INFO, PodkidnoySizes.PODKIDNOY_INFO));
		
		boolean debug = false; // Determines if dummy messages are added
		if (debug) {
			addText("New game has started", MessageType.NONE);
			addText("Move1 goes like that", MessageType.INFO);
			addText("Move2 goes like that", MessageType.INFO);
			addText("Move3 ends the round with a move that is like that", MessageType.INFO);
			addText("New round has started", MessageType.NONE);
			addText("Move1 goes like that", MessageType.INFO);
			addText("Move2 goes like that", MessageType.INFO);
			addText("Move3 ends the round with a move that is like that", MessageType.INFO);
			addText("New round has started", MessageType.NONE);
			addText("Move1 goes like that", MessageType.INFO);
			addText("Move2 goes like that", MessageType.INFO);
			addText("Move3 ends the round with a move that is like that", MessageType.INFO);
			addText("New round has started", MessageType.NONE);
		}
	}

	/**
	 * Adds parameter message as parameter type
	 * @param text Message
	 * @param type Message type
	 */
	void addText(String text, MessageType type) {
		StringBuffer buff = new StringBuffer();
		HTMLDocument doc = (HTMLDocument) infoPane.getDocument();
		HTMLEditorKit kit = (HTMLEditorKit) infoPane.getEditorKit();
		buff.append("<font color=#" + getMessageColor(type) + "><b>" + text + "</b></font><br>");
		try {
			kit.insertHTML(doc, doc.getLength(), buff.toString(), 0, 0, HTML.Tag.FONT);
			infoPane.setCaretPosition(doc.getLength());
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
	

	public static String getMessageColor(MessageType type) {
		Color color = getMessageAWTColor(type);
		String rgbString = Integer.toHexString(color.getRGB());
		rgbString = rgbString.substring(2);
		return rgbString;
	}

	private static Color getMessageAWTColor(MessageType type) {
		switch (type) {
		case NONE:
			return com.littlech.cl.gui.podkidnoy.constants.PodkidnoyColors.PODKIDNOY_MESSAGES_GENERAL;
		case INFO:
			return com.littlech.cl.gui.podkidnoy.constants.PodkidnoyColors.PODKIDNOY_MESSAGES_INFO;
		default:
			throw new IllegalArgumentException("Unknown: " + type);
		}
	}

	/**
	 * Returns pack and trump component
	 * @return Component
	 */
	public PackAndTrumpBox getPackAndTrump() {
		return packAndTrump;
	}

	/**
	 * Returns start and leave component
	 * @return Component
	 */
	/*
	public StartAndLeave getStartAndLeave() {
		return startAndLeave;
	}
	*/

	/**
	 * Returns player cards component
	 * @return Component
	 */
	public CardsPanel getCards() {
		return cards;
	}

	/**
	 * Returns table cards component
	 * @return Component
	 */
	public TablePanel getTable() {
		return table;
	}

	/**
	 * Returns action buttons component
	 * @return Component
	 */
	public ButtonsPanel getButtons() {
		return buttons;
	}

	public void setEnabledToSitButtons(boolean b) {
		// System.out.println("Setting "+b+" to sit buttons");
		for (SeatBox box : getSeats().values()) {
			SitButton sb = box.getSitButton();
			sb.setEnabled(b);
		}
	}

}
