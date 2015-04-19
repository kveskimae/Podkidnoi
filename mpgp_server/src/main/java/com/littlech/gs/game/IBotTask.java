/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.gs.game;

import com.littlech.gen.d.D4;
import com.littlech.gen.f.F1;

public interface IBotTask {
	
	F1 getSeatID();
	
	D4 getCommand();
	
	boolean isCalculated();
	
	void calculate();

}
