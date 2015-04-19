/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.gs.user;
import java.util.HashMap;
import java.util.Map;


// TODO: Auto-generated Javadoc
/**
 * The Class UsersStore.
 */
public class UserStore {

	/** The server users. */
	private Map<String, ServerUser> serverUsers = new HashMap<String, ServerUser>();

	/**
	 * Load user.
	 *
	 * @param id the id
	 * @return the f6
	 */
	/*
	private F6 loadUser(String id) {
		F6 ret = new F6();
		ret.setF8("actor.png"); // avatar
		ret.setF7(id + id + id); // username
		return ret;
	}
	*/

	/**
	 * Gets the server user.
	 *
	 * @param id the id
	 * @return the server user
	 */

	public void addServerUser(ServerUser ret) {
		synchronized (getServerUsers()) {
			if (ret == null) {
				throw new NullPointerException();
			}
			String key = ret.getUsername();
			while (getServerUsers().containsKey(key)) {
				getServerUsers().remove(key);
			}
			getServerUsers().put(ret.getUsername(), ret);
		}
	}
	

	public void removeServerUser(ServerUser _serverUser) {
		synchronized (getServerUsers()) {
			if (_serverUser == null) {
				return;
			}
			String key = _serverUser.getUsername();
			while (getServerUsers().containsKey(key)) {
				getServerUsers().remove(key);
			}
		}
	}
	/*
	public ServerUser getServerUser(String id) {
		synchronized (getServerUsers()) {
			ServerUser ret = getServerUsers().get(id);
			if (ret == null) {
				F6 u = loadUser(id);
				ret = new ServerUser(id, u);
				getServerUsers().put(id, ret);
			}
			return ret;
		}
	}
*/
	/**
	 * Gets the server users.
	 *
	 * @return the server users
	 */
	public Map<String, ServerUser> getServerUsers() {
		return serverUsers;
	}

}
