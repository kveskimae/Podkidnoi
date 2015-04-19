/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.gs.game.pod.task;

import java.util.List;


import com.littlech.gen.a.A5;
import com.littlech.gen.b.B20;
import com.littlech.gen.b.B26;
import com.littlech.gen.b.B28;
import com.littlech.gen.d.D4;
import com.littlech.gen.f.F1;
import com.littlech.gs.game.IBotTask;

public abstract class AbstractPodkidnoyBotTask implements IBotTask {

	/** The m player cards. */
	protected B26 mPlayerCards;

	/** The m trump. */
	protected B20 mTrump;

	/** The m buttons. */
	protected List<A5> mButtons;

	/** The m table. */
	protected B28 mTable;

	/** The m max add. */
	protected int mMaxAdd;

	/** The m game. */
	// private Podkidnoy mGame;

	/** The m bot id. */
	private F1 mBotID;

	private D4 mAnswer = null;

	public AbstractPodkidnoyBotTask(F1 botID) {
		this.mBotID = botID;
	}

	public AbstractPodkidnoyBotTask(F1 botID, final B26 sof, final B20 trump,
			final List<A5> buttons, final B28 table, final int max) {
		this.mBotID = botID;
this.mPlayerCards = sof;
		this.mTrump = trump;
		this.mButtons = buttons;
		this.mTable = table;
		this.mMaxAdd = max;
	}

	@Override
	public D4 getCommand() {
		if (!isCalculated()) {
			throw new IllegalStateException("Task is not calculated yet");
		}
		return mAnswer;
	}

	@Override
	public F1 getSeatID() {
		return mBotID;
	}

	@Override
	public boolean isCalculated() {
		return mAnswer != null;
	}

	protected void resolved(D4 answer) {
		mAnswer = answer;
	}

}
