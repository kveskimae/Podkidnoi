/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.gs.auth;

import java.util.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;

import static com.littlech.gs.gate.Gateway.*;
/**
 * 
 * Should be cleared from expired tokens; scheduled task?
 * 
 * @author Kristjan Veskim�e
 *
 */
public class LoginTokensCache {
	
	private volatile long idSequence = 0;
	
	private Map<String, Set<LoginToken>> tokens = new HashMap<String, Set<LoginToken>>();
	
	public LoginTokensCache() {
		
	}
	
	/*
	
	private static LoginTokensCache instance = new LoginTokensCache();
	
	private LoginTokensCache() {
		init();
	}
	
	public static LoginTokensCache getInstance() {
		return instance;
	}
	
	*/
	
	public void init() {
		List<String> names = new ArrayList<String>();
		names.add("jimi");
		names.add("joe");
		for (String cur : names) {
			Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			GrantedAuthority ga = new GrantedAuthorityImpl("ROLE_PLAYER");
			authorities.add(ga);
			LoginToken lt = new LoginToken(cur, cur, authorities, new Date(), "actor.png");
			addLoginToken(lt);	
		}
	}
	
	public synchronized void removeLoginTokens(String _username) {
		getTokens().remove(_username);
	}

	public synchronized void addPlayerLoginToken(String _username, String _token) {
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
	GrantedAuthority ga = new GrantedAuthorityImpl("ROLE_PLAYER");
	authorities.add(ga);
	LoginToken lt = new LoginToken(_username, _token, authorities, new Date(), "actor.png");
	addLoginToken(lt);	
	}
	public synchronized void addLoginToken(LoginToken _token) {
		// System.out.println("Adding " + _token);
		_token.setId(idSequence++);
		Set<LoginToken> tokensSet;
		String userName = _token.getUsername();
		if (getTokens().containsKey(userName)) {
			tokensSet = getTokens().get(userName);
		} else {
			tokensSet = new HashSet<LoginToken>();
			getTokens().put(userName, tokensSet);
		}
		tokensSet.add(_token);
	}
	
	public synchronized List<LoginToken> getTokensForUsername(String _userName) {
		// System.out.println("Searching tokens for user: " + _userName);
		Set<LoginToken> foundTokens = getTokens().get(_userName);
		if (logSecurity.isDebugEnabled()) {
		logSecurity.debug("Tokens for user '" + _userName + "': " + foundTokens); // Possible security vulnerability
		}
		List<LoginToken> ret = new ArrayList<LoginToken>();
		if (foundTokens != null && foundTokens.size() > 0) {
			ret.addAll(foundTokens);
		}
		return ret;
	}
	
	public Map<String, Set<LoginToken>> getTokens() {
		return tokens;
	}

}
