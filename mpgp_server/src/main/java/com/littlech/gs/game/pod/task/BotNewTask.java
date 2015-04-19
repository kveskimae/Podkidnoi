/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.gs.game.pod.task;


import com.littlech.gen.c.C1;
import com.littlech.gen.d.D1;
import com.littlech.gen.d.D19;
import com.littlech.gen.d.D23;
import com.littlech.gen.d.D4;
import com.littlech.gen.f.F1;
import com.littlech.gs.game.pod.Podkidnoy;

// TODO: Auto-generated Javadoc
/**
 * The Class BotNewTask.
 */
public class BotNewTask extends AbstractPodkidnoyBotTask {

	/** The m game. */
	private Podkidnoy mGame;
	
	/** The m bot id. */
	private F1 mBotID;

	private D4 mAnswer = null;

	private boolean calculated = false;

	/**
	 * Instantiates a new bot new task.
	 *
	 * @param game the game
	 * @param botID the bot id
	 */
	public BotNewTask(
			// final Podkidnoy game, 
			final F1 botID) {
		// this.mGame = game;
		// this.mBotID = botID;
		super(botID);
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void calculate() {
			D4 startCommand = new D4();
			startCommand.setC13(C1.C_11);

			D19 gameContent = new D19();
			gameContent.setD20(D1.D_2);
			startCommand.setD5(gameContent);

			D23 emptyContent = new D23();
			gameContent.setD21(emptyContent);

			resolved(startCommand);
	}

}
