/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.gs.game;


public interface ICommandHandler {
	
	void queueGameCommand(IGameCommand _command);
	
	Runnable getTask();

}
