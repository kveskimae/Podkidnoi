/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.login.loadingbar;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import org.netbeans.lib.awtextra.AbsoluteConstraints;

import com.littlech.cl.IImageLoader;
import com.littlech.cl.gui.login.constants.LoginColors;
import com.littlech.cl.gui.login.constants.LoginImages;
import com.littlech.cl.gui.login.constants.LoginLocations;
import com.littlech.cl.gui.login.constants.LoginSizes;

/**
 * 
 * Loading bar that can have an interactive snake moving on it
 * 
 * @author Kristjan Veskim�e
 * 
 */
public class LoadingBar extends JLabel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Number of bubbles drawn on loading bar background; depends on relative
	 * sizes of bar and bubbles
	 */
	private static final int NUM_OF_LOADING_BUBBLES = 10;

	private final IImageLoader mImageLoader;

	/**
	 * Index moving snake's head bubble's index
	 */
	private volatile int bubbleIdx = 0;

	/**
	 * True if snake is moving towards right, false otherwise
	 */
	private volatile boolean bubbleRight = true;

	/**
	 * Constraint
	 */
	private AbsoluteConstraints constraint = new AbsoluteConstraints(
			LoginLocations.LOADING_BAR, LoginSizes.LOADING_BAR);

	/**
	 * Moves snake to the next bubble
	 * <br>
	 * Invariant: task and timer must be nulls or task must be scheduled to
	 * timer and running
	 */
	private AtomicReference<TimerTask> task = new AtomicReference<TimerTask>();
	/**
	 * Timer moving the bubble snake
	 */
	private AtomicReference<Timer> t = new AtomicReference<Timer>();

	public LoadingBar(final IImageLoader _imageLoader) {
		mImageLoader = _imageLoader;
		ImageIcon loadingBarII = mImageLoader
				.fetchImage(LoginImages.LOGIN_LOADING_BAR);
		setIcon(loadingBarII);

	}

	/**
	 * Starts the bubble snake moving
	 */
	public synchronized void start() {
		t.set(new Timer(true));
		task.set(new TimerTask() {

			@Override
			public void run() {
				Runnable task2 = new Runnable() {
					public void run() {
						moveSnake();
					}
				};
				SwingUtilities.invokeLater(task2);
			}
		});
		// task, delay, period (in ms)
		t.get().scheduleAtFixedRate(task.get(), 50, 80);
	}

	/**
	 * Stops the bubble snake moving
	 */
	public synchronized void stop() {
		t.get().cancel();
		t.set(null);
		task.set(null);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawBubbles(g);
	}

	public AbsoluteConstraints getConstraint() {
		return constraint;
	}

	/**
	 * Draws the bubble snake on parameter graphics
	 * 
	 * @param g Graphics
	 */
	private void drawBubbles(Graphics g) {
		for (int i = 0; i < NUM_OF_LOADING_BUBBLES; i++) {
			Color c;
			if (i == bubbleIdx) {
				c = LoginColors.BUBBLE_STRONGEST;
			} else if ((bubbleRight && i < bubbleIdx && i >= bubbleIdx - 1)
					|| (!bubbleRight && i > bubbleIdx && i <= bubbleIdx + 1)) {
				c = LoginColors.BUBBLE_STRONG;
			} else if ((bubbleRight && i < bubbleIdx && i >= bubbleIdx - 2)
					|| (!bubbleRight && i > bubbleIdx && i <= bubbleIdx + 2)) {
				c = LoginColors.BUBBLE_WEAK;
			} else {
				c = LoginColors.BUBBLE_WEAKEST;
			}
			g.setColor(c);
			g.fillOval(LoginLocations.GAP_BUBBLE1_X + i
					* LoginSizes.BUBBLE.width, LoginLocations.GAP_BUBBLE1_Y,
					LoginSizes.BUBBLE.width, LoginSizes.BUBBLE.height);
		}
	}

	/**
	 * Causes the bubble snake to move to the next position
	 */
	public void moveSnake() {
		repaint();
		if (bubbleRight) {
			if (bubbleIdx < NUM_OF_LOADING_BUBBLES - 1) {
				bubbleIdx++;
			} else {
				bubbleRight = !bubbleRight;
				bubbleIdx--;
			}
		} else {
			if (bubbleIdx > 0) {
				bubbleIdx--;
			} else {
				bubbleRight = !bubbleRight;
				bubbleIdx++;
			}
		}

	}

}
