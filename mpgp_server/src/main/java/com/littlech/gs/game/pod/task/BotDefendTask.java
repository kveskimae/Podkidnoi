/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.gs.game.pod.task;

import java.util.List;

import com.littlech.cl.Utils;
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
 * The Class BotDefendTask.
 */
public class BotDefendTask extends AbstractPodkidnoyBotTask {
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb =new StringBuilder();
		sb.append("BotDefendTask{");
		sb.append("" + mButtons);
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Instantiates a new bot defend task.
	 *
	 * @param game the game
	 * @param botID the bot id
	 * @param playerCards the player cards
	 * @param trump the trump
	 * @param buttons the buttons
	 * @param table the table
	 * @param max the max
	 */
	public BotDefendTask(
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
		D4 ret;
		if (mButtons.contains(A5.A_7)) {
			
			D4 addCommand = new D4();
			addCommand.setC13(C1.C_11);
			
			D19 gameContent = new D19();
			gameContent.setD20(D1.D_3);
			addCommand.setD5(gameContent);

			D24 sendContent = new D24();
			gameContent.setD21(sendContent );
			
			A18 action = new A18();
			action.setA19(A5.A_7);
			sendContent.setD25(action );
			
			B26 sendCards = PodkidnoyUtils.calcSendForwardCards(mMaxAdd, mTable, mPlayerCards);
			action.setA21(sendCards);
			
			ret = addCommand;
		} else if (mButtons.contains(A5.A_8)) {
			D4 defendCommand = new D4();
			defendCommand.setC13(C1.C_11);
			
			D19 gameContent = new D19();
			gameContent.setD20(D1.D_3);
			defendCommand.setD5(gameContent);

			D24 sendContent = new D24();
			gameContent.setD21(sendContent );
			
			A18 action = new A18();
			action.setA19(A5.A_8);
			sendContent.setD25(action );

			B26 killers = new B26();
			killers.getB27().addAll(mPlayerCards.getB27());
			killers.getB27().addAll(Utils.getDefendingCards(mTable.getB29())); // .getDefendingCards()); XXX
			B28 defendedTable = PodkidnoyUtils.calcDefendedTable(killers, mTable, mTrump);
			action.setA20(defendedTable);
			
			ret = defendCommand;
			
		} else {
			D4 pickCommand = new D4();
			pickCommand.setC13(C1.C_11);
			
			D19 gameContent = new D19();
			gameContent.setD20(D1.D_3);
			pickCommand.setD5(gameContent);
			
			D24 pickContent = new D24();
			gameContent.setD21(pickContent );
			
			A18 action = new A18();
			action.setA19(A5.A_9);
			pickContent.setD25(action );
			
			ret = pickCommand;
		}
		// ret.setSessionID(mBotID);
		// mGame.handleBotMove(mBotID, ret);
		resolved(ret);
	}

}
