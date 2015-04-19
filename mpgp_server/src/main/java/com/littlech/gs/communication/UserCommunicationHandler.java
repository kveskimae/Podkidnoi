/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.gs.communication;

import static com.littlech.cl.connect.ConnectionConstants.DELIMITER;
import static com.littlech.gs.gate.Gateway.*;

import java.nio.channels.ClosedChannelException;


import org.springframework.security.core.context.SecurityContextHolder;
import org.xsocket.connection.IConnectHandler;
import org.xsocket.connection.IDataHandler;
import org.xsocket.connection.IDisconnectHandler;
import org.xsocket.connection.INonBlockingConnection;

import com.littlech.gen.c.C1;
import com.littlech.gen.d.D4;
import com.littlech.gs.GameServer;
import com.littlech.gs.auth.WatchDog;
import com.littlech.gs.user.ServerUser;

public class UserCommunicationHandler implements IDataHandler, IConnectHandler,
		IDisconnectHandler {
	
	/** The dog. */
	private WatchDog dog;
	
	private MessageHandler messageHandler;
	
	public MessageHandler getMessageHandler() {
		return messageHandler;
	}
	
	public void setMessageHandler(MessageHandler messageHandler) {
		this.messageHandler = messageHandler;
	}
	
	public void setDog(WatchDog dog) {
		this.dog = dog;
	}
	
	/**
	 * Gets the dog.
	 *
	 * @return the dog
	 */
	public WatchDog getDog() {
		return dog;
	}

	public boolean onData(INonBlockingConnection _connection) {
		ServerMarshalerImpl marshaler = null;
		try {
			marshaler = (ServerMarshalerImpl) GameServer.getInstance()
					.getMarshalersPool().borrowObject();
			String data = _connection.readStringByDelimiter(DELIMITER);
			D4 received = marshaler.unmarshalCS(data);
			C1 code = received.getC13();
			switch (code) {
			case C_2:

				// User is not yet online, login command is allowed
				getDog().handleLoginCommand(received, _connection);
				break;
			case C_3:
				
				// Clean log out
				ServerUser su2 = getServerUser(_connection);
				// getDog().handleLogoutCommand(su2, received);
				getDog().logUserOut(su2);
				break;
			default:
				ServerUser su = getServerUser(_connection);

				if (logCommunication.isDebugEnabled()) {
					logCommunication.debug("Received " + received + " from " + su);
				}
				
				// System.out.println("Received " + received + " from " + su);

				SecurityContextHolder.getContext().setAuthentication(su.getAuthentication());
				getMessageHandler().handleNonLoginCommand(received, su);
				break;
			}

			return true;
		} catch (ClosedChannelException ignored) {
		} catch (Exception e) {
			logCommunication.error("Error reading user message", e);
		} finally {
			if (marshaler != null) {
				try {
					GameServer.getInstance().getMarshalersPool()
							.returnObject(marshaler);
				} catch (Exception e) {
					logGeneral.error("Error while returning marshaler to pool", e);
				}
			}
			SecurityContextHolder.getContext().setAuthentication(null);
		}
		return false;
	}

	private ServerUser getServerUser(INonBlockingConnection _connection) {
		ServerUser ret = null;
		Object attachment = _connection.getAttachment();
		if (attachment == null || !(attachment instanceof ServerUser)) {
			throw new IllegalStateException("No server user: "
					+ attachment);
		}
		ret = (ServerUser) attachment;
		return ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.xsocket.connection.IConnectHandler#onConnect(org.xsocket.connection
	 * .INonBlockingConnection)
	 */
	public boolean onConnect(INonBlockingConnection con) {
		if (logCommunication.isDebugEnabled()) {
    	logCommunication.debug("Connecting to server from " + con.getRemoteAddress() + ":" + con.getRemotePort());
		}
		con.setAutoflush(true);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.xsocket.connection.IDisconnectHandler#onDisconnect(org.xsocket.connection
	 * .INonBlockingConnection)
	 */
	public boolean onDisconnect(INonBlockingConnection con) {
		if (logCommunication.isDebugEnabled()) {
    	logCommunication.debug("Disconnecting from server " + con.getRemoteAddress() + ":" + con.getRemotePort());
		}
		Object attachment = con.getAttachment();
		if (attachment != null) {
			ServerUser su = (ServerUser) attachment;
			if (su.isOnline()) {
				// Dirty log out
				getDog().logUserOut(su);
				// su.disconnected();
			}
		}
		return true;
	}
}
