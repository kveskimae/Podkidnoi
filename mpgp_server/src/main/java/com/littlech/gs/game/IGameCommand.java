/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.gs.game;

import com.littlech.gen.d.D4;
import com.littlech.gen.f.F1;
import com.littlech.gs.user.ServerUser;

public interface IGameCommand {
	
	D4 getCommand();
	
	boolean isFromFramework();
	
	boolean isBotMove();
	
	F1 getBotSeatID();
	
	boolean isFromUser();
	
	ServerUser getUser();

	boolean isBotTask();

	IBotTask getBotTask();

}
