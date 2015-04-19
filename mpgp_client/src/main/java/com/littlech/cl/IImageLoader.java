/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl;

import java.awt.Image;

import javax.swing.ImageIcon;

import com.littlech.gen.b.B20;

/**
 * 
 * Interface for loading images from server
 * 
 * @author Kristjan Veskim�e
 * 
 */
public interface IImageLoader {

	/**
	 * 
	 * Retrieves image from server or local cache
	 * 
	 * @param _name
	 *          Image name without folder path
	 * @return Image
	 */
	ImageIcon fetchImage(String _name);

	/**
	 * 
	 * Retrieves card image. Parameter card cannot be null and must have suit and
	 * rank set.
	 * 
	 * @param _c
	 *          Card
	 * @return Image for card
	 */
	Image getCardImg(B20 _c);

}
