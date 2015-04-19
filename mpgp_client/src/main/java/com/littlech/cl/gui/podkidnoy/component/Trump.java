/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.podkidnoy.component;

import java.util.*;

import com.littlech.cl.IImageLoader;
import com.littlech.cl.constants.Names;
import com.littlech.cl.gui.podkidnoy.button.CardButton;
import com.littlech.cl.gui.podkidnoy.util.ObservableValue;
import com.littlech.gen.b.B20;

/**
 * 
 * Container for trump card
 * 
 * @author Kristjan Veskim�e
 *
 */
public class Trump implements Observer {
	
	/**
	 * Image loader
	 */
	private IImageLoader mImageLoader;

	/**
	 * Trump card component
	 */
	private CardButton trumpCard;

	/**
	 * Returns the trump card component
	 * @return Component
	 */
	public CardButton getCardButton() {
		return trumpCard;
	}

	/**
	 * Constructor
	 * @param imageLoader Image loader
	 */
	public Trump(IImageLoader imageLoader) {
		mImageLoader = imageLoader;
		trumpCard = new CardButton(mImageLoader);
		trumpCard.setName(Names.PODKIDNOY_TRUMP);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg0 instanceof ObservableValue<?>) {
			ObservableValue ov = (ObservableValue) arg0;
			Object o = ov.getValue();
			if (o instanceof B20) {
				B20 card = (B20) o;
				trumpCard.setCard(card);
				trumpCard.repaint();
			} else if (o == null) {
				System.out.println("setting null");
				trumpCard.setCard(null);
				trumpCard.repaint();
			} else {
				throw new IllegalArgumentException(
						// "Trump was listening to observable value " 
						"To "
						+ o.getClass().getCanonicalName());
			}
		} else {
			throw new IllegalStateException(this.getClass().getCanonicalName() + " to " + arg0.getClass().getCanonicalName());
		}
	}

}
