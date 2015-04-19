/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.podkidnoy.util;

import java.util.Observable;

/**
 * 
 * Can be used to listen to a value
 * 
 * @author Kristjan Veskim�e
 *
 * @param <T> Generic type of value
 */
public class ObservableValue<T> extends Observable{
	
	/**
	 * Value that is listened to
	 */
	private T value;

	/**
	 * Notifies listeners of changed value
	 */
	public void notifyListeners() {
		setChanged();
		notifyObservers();
	}

	/**
	 * Retrieves the new value
	 * @return Value
	 */
	public T getValue() {
		return value;
	}
	
	/**
	 * Applies a new value and notifies all listeners of the value change
	 * @param value New value
	 */
	public void setValue(T value) {
		this.value = value;
		notifyListeners();
	}

}
