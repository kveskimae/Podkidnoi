/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.podkidnoy.button;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.SwingWorker;

public class TimeCounter implements ActionListener {
	
	/**
	 * Extra time to wait for clock inaccuracies
	 */
	private long DELTA = 20;
	
	private long lastClickTime;
	
	private final long mTime;

	private AbstractButton mButton;
	
	public TimeCounter(final long _time, final AbstractButton _button) {
		mTime = _time;
		mButton = _button;
	}
	
	public boolean isTimeOver() {
		boolean ret = getTimeLeft() <= 0;
		return ret;
	}
	
	private long getTimeLeft() {
		long diff = getTime() - getTimeSpent();
		long ret = diff > 0 ? diff : 0;
		return ret;
	}
	
	private long getTimeSpent() {
		long ret = System.currentTimeMillis() - getLastClickTime();
		return ret;
	}
	
	private long getTime() {
		return mTime;
	}
	
	private long getLastClickTime() {
		return lastClickTime;
	}
	
	private void reset() {
		lastClickTime = System.currentTimeMillis();
	}

public AbstractButton getButton() {
	return mButton;
}

	@Override
	public void actionPerformed(ActionEvent e) {
		reset();
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>(){

			@Override
			protected Void doInBackground() throws Exception {
				getButton().setEnabled(false);
				while (!isTimeOver()) {
					Thread.sleep(getTimeLeft() + DELTA);
				}
				getButton().setEnabled(true);
				return null;
			}};
			worker.execute();
		}

}
