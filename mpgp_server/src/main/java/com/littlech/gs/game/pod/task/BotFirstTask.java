/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.gs.game.pod.task;

import java.util.List;


import com.littlech.gen.a.A18;
import com.littlech.gen.a.A5;
import com.littlech.gen.b.*;
import com.littlech.gen.c.C1;
import com.littlech.gen.d.D1;
import com.littlech.gen.d.D19;
import com.littlech.gen.d.D24;
import com.littlech.gen.d.D4;
import com.littlech.gen.f.F1;

// TODO: Auto-generated Javadoc
/**
 * The Class BotFirstTask.
 */
public class BotFirstTask extends AbstractPodkidnoyBotTask {

	/**
	 * Instantiates a new bot first task.
	 *
	 * @param game the game
	 * @param botID the bot id
	 * @param playerCards the player cards
	 * @param trump the trump
	 * @param buttons the buttons
	 * @param table the table
	 * @param max the max
	 */
	public BotFirstTask(
			// final Podkidnoy game, 
			final F1 botID,
			final B26 sof, 
			final B20 trump,
			final List<A5> buttons, final B28 table,
			final int max) {
		super(// game, 
				botID,
				
				sof,
				trump,
				buttons,
				 table,
				 max
		
		
		);
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void calculate() {
			D4 ret = new D4();
			ret.setC13(C1.C_11);

			D19 gameContent = new D19();
			gameContent.setD20(D1.D_3);
			ret.setD5(gameContent);

			D24 addContent = new D24();
			gameContent.setD21(addContent);

			A18 action = new A18();
			action.setA19(A5.A_6);
			addContent.setD25(action);

			B26 firstCards = PodkidnoyUtils.calcFirstCards(mPlayerCards, mMaxAdd);

			action.setA21(firstCards);

			resolved(ret);
	}

}
