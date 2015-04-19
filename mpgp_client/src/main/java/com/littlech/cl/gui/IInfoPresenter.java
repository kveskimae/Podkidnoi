/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui;

public interface IInfoPresenter {
	
	/**
	 * Shows parameter string as informative message
	 * 
	 * @param msg
	 *          Message string
	 */
	void info(String msg);

	/**
	 * Shows parameter string as fatal error, should be used when client
	 * application is killed
	 * 
	 * @param msg
	 *          Message string
	 */
	void fatal(String msg);

}
