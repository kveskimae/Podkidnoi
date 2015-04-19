/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.gs.game.pod.task;

import java.util.List;

import com.littlech.gen.a.A18;
import com.littlech.gen.a.A5;
import com.littlech.gen.b.B20;
import com.littlech.gen.b.B26;
import com.littlech.gen.b.B28;
import com.littlech.gen.c.C1;
import com.littlech.gen.d.D1;
import com.littlech.gen.d.D19;
import com.littlech.gen.d.D24;
import com.littlech.gen.d.D4;
import com.littlech.gen.f.F1;

// TODO: Auto-generated Javadoc
/**
 * The Class BotAddTask.
 */
public class BotAddTask extends AbstractPodkidnoyBotTask {
	

	/**
	 * Instantiates a new bot add task.
	 *
	 * @param game the game
	 * @param botID the bot id
	 * @param sof the sof
	 * @param trump the trump
	 * @param buttons the buttons
	 * @param table the table
	 * @param max the max
	 */
	public BotAddTask(
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
			D4 answer;
			if (mButtons.contains(A5.A_10)) {
				D4 addCommand = new D4();
				addCommand.setC13(C1.C_11);

				D19 gameContent = new D19();
				gameContent.setD20(D1.D_3);
				addCommand.setD5(gameContent);

				D24 addContent = new D24();
				gameContent.setD21(addContent);

				A18 action = new A18();
				action.setA19(A5.A_10);
				addContent.setD25(action);

				B26 addCards = PodkidnoyUtils.calcAddCards(mTable,
						mMaxAdd, mPlayerCards);
				action.setA21(addCards);

				answer = addCommand;
			} else {
				D4 passCommand = new D4();
				passCommand.setC13(C1.C_11);

				D19 gameContent = new D19();
				gameContent.setD20(D1.D_3);
				passCommand.setD5(gameContent);

				D24 passContent = new D24();
				gameContent.setD21(passContent);

				A18 action = new A18();
				action.setA19(A5.A_11);
				passContent.setD25(action);

				answer = passCommand;
			}
			// answer.setSessionID(mBotID); XXX

			// mGame.handleBotMove(mBotID, answer);
			resolved(answer);
	}

}
