/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.gs.auth;

import static com.littlech.cl.connect.ConnectionConstants.DELIMITER;
import static com.littlech.gs.gate.Gateway.logGeneral;
import static com.littlech.gs.gate.Gateway.logSecurity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.xsocket.connection.INonBlockingConnection;

import com.littlech.cl.Utils;
import com.littlech.gen.c.C1;
import com.littlech.gen.d.D4;
import com.littlech.gen.d.D9;
import com.littlech.gen.e.E11;
import com.littlech.gen.e.E19;
import com.littlech.gen.e.E46;
import com.littlech.gs.GameServer;
import com.littlech.gs.communication.ServerMarshalerImpl;
import com.littlech.gs.user.ServerUser;
import com.littlech.gs.user.UserStore;

// TODO: Auto-generated Javadoc
/**
 * The Class WatchDog.
 */
public class WatchDog {

	/** The users. */
	private UserStore users; // = new UsersStore();

	private AuthenticationManager authMan;

	/**
	 * Gets the users.
	 * 
	 * @return the users
	 */
	public UserStore getUsers() {
		return users;
	}

	public void setUsers(UserStore users) {
		this.users = users;
	}

	public AuthenticationManager getAuthMan() {
		return authMan;
	}

	public void setAuthMan(AuthenticationManager authMan) {
		this.authMan = authMan;
	}

	/**
	 * Validate.
	 * 
	 * @param id
	 *            the id
	 * @param token
	 *            the token
	 * @return true, if successful
	 */
	// public boolean validate(String id, String token) {
	/*
	 * try { Thread.sleep(3000); } catch (InterruptedException e) {
	 * e.printStackTrace(); }
	 */
	// return true;
	// }

	/**
	 * Handle login command.
	 * 
	 * @param _loginCommand
	 *            the _login command
	 * @param _connection
	 *            the _connection
	 * @throws Exception
	 *             the exception
	 */
	public void handleLoginCommand(D4 _loginCommand,
			INonBlockingConnection _connection) throws Exception {
		Utils.checkCommand(_loginCommand, C1.C_2);
		D9 loginContent = (D9) _loginCommand.getD5();
		String id = loginContent.getD10();
		String token = loginContent.getD11();
		if (id == null) {
			throw new NullPointerException("Id is null");
		}
		if (token == null) {
			throw new NullPointerException("Token is null");
		}

		/*
		 * System.out.println("Hello world!");
		 * SecurityContextHolder.getContext().setAuthentication(result);
		 * DaoAuthenticationProvider dap; messageHandler.handleMessage();
		 */

		Authentication auth = new UsernamePasswordAuthenticationToken(id, token);
		if (authMan == null) {
			throw new NullPointerException(
					"Authentication manager has not been initialized!");
		}
		try {
			Authentication result = authMan.authenticate(auth);

			LoginToken lt = (LoginToken) result.getPrincipal();
			ServerUser su = new ServerUser(lt);
			// su.setAuthentication(result);
			/*
			 * ServerUser su = getUsers().getServerUser(id); if (su.isOnline())
			 * { throw new IllegalStateException("User already is online"); }
			 */
			su.connected(_connection, result);

			E19 answer = getLoginCommandStub();
			E46 scLoginContent = new E46();
			scLoginContent.setE47(su.getF11());
			answer.setE20(scLoginContent);
			_connection.setAttachment(su);
			su.write(answer);

			// Seems like we have a new user
			getUsers().addServerUser(su);
		} catch (Exception e23) {
			logSecurity.error("Error while logging in game server", e23);
			E19 toClient = new E19();
			toClient.setC13(C1.C_2);
			toClient.setE21(E11.E_13);
			toClient.setE22("Login was unsuccessful");

			ServerMarshalerImpl marshaler = null;
			try {
				marshaler = (ServerMarshalerImpl) GameServer.getInstance()
						.getMarshalersPool().borrowObject();
				String s = marshaler.marshalSC(toClient) + DELIMITER;
				_connection.write(s);
			} catch (Exception e) {
				logGeneral.error("Error while retrieving marshaler from pool",
						e);
			} finally {
				if (marshaler != null) {
					try {
						GameServer.getInstance().getMarshalersPool()
								.returnObject(marshaler);
					} catch (Exception e) {
						logGeneral.error(
								"Error while returning marshaler to pool", e);
					}
				}
			}
		}

	}

	/**
	 * Handle logout command.
	 * 
	 * @param _serverUser
	 *            the _server user
	 * @param _logoutCommand
	 *            the _logout command
	 */
	/*
	 * public void handleLogoutCommand(ServerUser _serverUser, D4
	 * _logoutCommand) { // Utils.checkCommand(_logoutCommand, C1.C_3); not
	 * really necessary _serverUser.disconnected(); }
	 */

	/**
	 * Gets the login command stub.
	 * 
	 * @return the login command stub
	 */
	public static E19 getLoginCommandStub() {
		E19 answer = new E19();
		answer.setC13(C1.C_2);
		answer.setE21(E11.E_12);
		return answer;
	}

	public void logUserOut(ServerUser _serverUser) {
		_serverUser.disconnected();
		getUsers().removeServerUser(_serverUser);
	}

}
