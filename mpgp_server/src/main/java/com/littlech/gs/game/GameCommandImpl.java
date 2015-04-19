/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.gs.game;

import org.apache.commons.lang.NullArgumentException;

import com.littlech.gen.d.D4;
import com.littlech.gen.f.F1;
import com.littlech.gs.user.ServerUser;

public class GameCommandImpl implements IGameCommand {

	
	private D4 command;
	private F1 botSeatID;
	private ServerUser from;
	
	/**
	 * A bot move is not considered as being from framework.
	 */
	private boolean fromFrameWork = false;
	private IBotTask botTask;
	
	public GameCommandImpl(IBotTask _botTask) {
		if (_botTask == null) {
			throw new NullArgumentException("_botTask");
		}
		botTask = _botTask;
		botSeatID = _botTask.getSeatID();
	}

	public GameCommandImpl(final D4 _command, final F1 _botSeatID) {
		if (_command == null) { 
			throw new NullArgumentException("_command");
		}
		if (_botSeatID == null) {
			throw new NullArgumentException("_botSeatID");
		}
		command = _command;
		botSeatID = _botSeatID;
	}
	
	public GameCommandImpl(final D4 _command, final ServerUser _from) {
		if (_command == null) { 
			throw new NullArgumentException("_command");
		}
		if (_from == null) {
			throw new NullArgumentException("_from");
		}
		command = _command;
		from = _from;
	}
	
	public GameCommandImpl(final D4 _frameWorkCommand) {
		if (_frameWorkCommand == null) { 
			throw new NullArgumentException("_frameWorkCommand");
		}
		command = _frameWorkCommand;
		fromFrameWork  = true;
	}

	@Override
	public D4 getCommand() {
		if (isBotTask() && !getBotTask().isCalculated()) {
			getBotTask().calculate();
			command = getBotTask().getCommand();
		}
		return command;
	}
	
	@Override
	public boolean isBotTask() {
		return botTask != null;
	}
	
	@Override
	public IBotTask getBotTask() {
		return botTask;
	}

	@Override
	public boolean isBotMove() {
		return isBotTask() || (botSeatID != null);
	}
	@Override
	public F1 getBotSeatID() {
		if (!isBotMove()) {
			throw new IllegalStateException("This is not a bot move");
		}
		if (botSeatID == null) {
			throw new IllegalStateException("Bot seat ID is null");
		}
		return botSeatID;
	}

	@Override
	public boolean isFromUser() {
		return from != null;
	}

	@Override
	public ServerUser getUser() {
		if (!isFromUser()) {
			throw new IllegalStateException("This is not from user");
		}
		if (from == null) {
			throw new IllegalStateException("User is null");
		}
		return from;
	}

	@Override
	public boolean isFromFramework() {
		return fromFrameWork;
	}

}
