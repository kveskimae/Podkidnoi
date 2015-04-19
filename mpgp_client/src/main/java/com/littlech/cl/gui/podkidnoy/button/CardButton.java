/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.podkidnoy.button;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JToggleButton;

import com.littlech.cl.IImageLoader;
import com.littlech.cl.gui.podkidnoy.constants.*;
import com.littlech.gen.b.B20;

/**
 * 
 * Card that can act as a button
 * 
 * @author Kristjan Veskim�e
 *
 */
public class CardButton extends JToggleButton {

	/**
	 * Displayed card
	 */
	private B20 mCard;

	/**
	 * Image loader
	 */
	private IImageLoader mImageLoader;

	/**
	 * 
	 * Constructor
	 * 
	 * @param _imageLoader Image loader
	 */
	public CardButton(final IImageLoader _imageLoader) {
		this.mImageLoader = _imageLoader;
		setBorderPainted(false);
		setContentAreaFilled(false);
		setIcon(mImageLoader.fetchImage(PodkidnoyImages.CARD_DEFAULT));
		setSelectedIcon(mImageLoader.fetchImage(PodkidnoyImages.CARD_SELECTED));
		setRolloverIcon(mImageLoader.fetchImage(PodkidnoyImages.CARD_HOVER));
		setDisabledIcon(mImageLoader.fetchImage(PodkidnoyImages.CARD_DEFAULT));
		setDisabledSelectedIcon(mImageLoader.fetchImage(PodkidnoyImages.CARD_SELECTED));
	}

	/**
	 * 
	 * Sets card for displaying on this card button
	 * 
	 * @param c Card
	 */
	public void setCard(B20 c) {
		if (c == null) {
			throw new NullPointerException(); // "Card is null");
		}
		if (c.getB22() == null) {
			throw new NullPointerException(); // "Rank is not set");
		}
		if (c.getB21() == null) {
			throw new NullPointerException(); // "Suit is not set");
		}
		this.mCard = c;
	}

	/**
	 * 
	 * Returns card displayed on this card button or null if no card has been set yet
	 * 
	 * @return Displayed card or null if card has not been set
	 */
	public B20 getCard() {
		return mCard;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		paintCard(g);
	}

	/**
	 * 
	 * Paints card image on the button
	 * 
	 * @param g Graphics canvas
	 */
	protected void paintCard(Graphics g) {
		if (this.mCard != null) {
			Image cardImg = mImageLoader.getCardImg(mCard);
			g.drawImage(cardImg, 0, 0, null);
		}
	}
	
}
