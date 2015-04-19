/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.gs.auth;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 
 * @author Kristjan Veskim�e
 *
 */
public class PlayersDetailsService implements UserDetailsService {
	
	private LoginTokensCache tokensCache;
	
	public LoginTokensCache getTokensCache() {
		return tokensCache;
	}

	public void setTokensCache(LoginTokensCache tokensCache) {
		this.tokensCache = tokensCache;
	}




	public UserDetails loadUserByUsername(String userName)
			throws UsernameNotFoundException, DataAccessException {
		/*
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		GrantedAuthority ga = new GrantedAuthorityImpl("ROLE_PLAYER");
		authorities.add(ga);
		if ("jimi".equals(username)) {
		// User ret = new User(username, "jimi", true, true, true, true, authorities);
		LoginToken lt = new LoginToken(username, "jimi", authorities, new Date(), "actor.png");
		return lt;
		}
		
		throw new UsernameNotFoundException("Could not find username: " + username);
		*/
		List<LoginToken> list = 
			// LoginTokensCache.getInstance()
			getTokensCache()
			.getTokensForUsername(userName);
		if (list.isEmpty()) {
			throw new UsernameNotFoundException("Username not found: " + userName);
		}
		LoginToken ret = null;
		forLoginTokens: for (LoginToken cur : list) {
			if (cur.isCredentialsNonExpired()) {
				ret = cur;
				break forLoginTokens;
			} else if (ret == null) {
				ret = cur;
			}
		}
		return ret;
	}

}
