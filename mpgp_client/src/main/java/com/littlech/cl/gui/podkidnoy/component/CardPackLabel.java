/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.podkidnoy.component;


import java.awt.Component;
import javax.swing.JLabel;
import com.littlech.cl.IImageLoader;
import com.littlech.cl.gui.podkidnoy.constants.*;

/**
 * 
 * Card pack label
 * 
 * @author Kristjan Veskim�e
 *
 */
public class CardPackLabel { // implements Observer {

	/**
	 * Label
	 */
	private JLabel pack = new JLabel();
	
	/**
	 * Image loader
	 */
	private IImageLoader mImageLoader;

	/**
	 * Constructor
	 * @param imageLoader Image loader
	 */
	public CardPackLabel(final IImageLoader imageLoader) {
		this.mImageLoader = imageLoader;
		pack.setFont(PodkidnoyFonts.PACK);
		pack.setForeground(new java.awt.Color(255, 255, 255));
		pack.setText("NI");
		pack.setHorizontalAlignment((int) Component.CENTER_ALIGNMENT);
		pack.setOpaque(false);
	}
	
	public JLabel getPack() {
		return pack;
	}
	
	public void setVisible(boolean b) {
		pack.setVisible(b);
	}

	public void setValue(int value) {
		pack.setText("" + value);
	}
	
}
