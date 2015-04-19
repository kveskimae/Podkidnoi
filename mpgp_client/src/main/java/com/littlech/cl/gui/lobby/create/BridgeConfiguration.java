/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.lobby.create;

import java.awt.Color;
import javax.swing.JPanel;

import org.netbeans.lib.awtextra.AbsoluteLayout;

import com.littlech.gen.g.G13;
import com.littlech.gen.g.G17;
import com.littlech.gen.g.G26;

public class BridgeConfiguration implements IConfigurationPanel {

	/**
	 * Base panel
	 */
	private final JPanel panel;

	/**
	 * Constraint for base panel
	 */
	// private AbsoluteConstraints constraint = new AbsoluteConstraints(Locations.LOBBY_CREATE_CONF, Sizes.LOBBY_CREATE_CONF);

	public BridgeConfiguration() {
		panel = new JPanel();
		panel.setLayout(new AbsoluteLayout());
		panel.setBackground(Color.orange);
		panel.setOpaque(true);
	}
/*
	public AbsoluteConstraints getConstraint() {
		return constraint;
	}
*/
	public JPanel getPanel() {
		return panel;
	}
	
	public G13 getGameType() {
		return G13.G_15;
	}

	@Override
	public void reset() {
	}

	@Override
	public void setEnabled(boolean b) {
	}
	
	@Override
	public G17 getGameConf() {
		G17 ret = new G17();
		G26 bridgeConf = new G26();
		ret.setG19(bridgeConf);
		ret.setG18(G13.G_15);
		return ret;
	}

}
