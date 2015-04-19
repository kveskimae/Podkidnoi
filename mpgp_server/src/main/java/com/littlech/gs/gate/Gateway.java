/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.gs.gate;

import org.apache.log4j.Logger;

import com.littlech.gs.auth.LoginTokensCache;


public class Gateway {

	private static Gateway instance;

	private LoginTokensCache tokensCache;

	public static Logger logSecurity, logGeneral, logCommunication, logGame, logLobby, logDb;

	public static void setInstance(Gateway instance) {
		Gateway.instance = instance;
	}

	public static Gateway getInstance() {
		return instance;
	}

	public void init() {
		logSecurity = Logger.getLogger("SECURITY");
		logCommunication = Logger.getLogger("COMMUNICATION");
		logGame = Logger.getLogger("GAME");
		logGeneral = Logger.getLogger("GENERAL");
		logLobby = Logger.getLogger("LOBBY");
		logDb = Logger.getLogger("DB");
	}

	public LoginTokensCache getTokensCache() {
		return tokensCache;
	}

	public void setTokensCache(LoginTokensCache tokensCache) {
		this.tokensCache = tokensCache;
	}

	public void removeLoginTokens(String _username) {
		getTokensCache().removeLoginTokens(_username);
	}

	public void addPlayerLoginToken(String _username, String _token) {
		getTokensCache().addPlayerLoginToken(_username, _token);
	}

}
