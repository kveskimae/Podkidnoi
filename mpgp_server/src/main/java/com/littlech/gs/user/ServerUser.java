/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.gs.user;
import static com.littlech.cl.connect.ConnectionConstants.DELIMITER;
import static com.littlech.gs.gate.Gateway.*;

import java.io.IOException;
import java.nio.BufferOverflowException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.xml.bind.annotation.XmlElement;

import org.springframework.security.core.Authentication;
import org.xsocket.connection.INonBlockingConnection;

import com.littlech.gen.e.E19;
import com.littlech.gen.f.F6;
import com.littlech.gs.GameServer;
import com.littlech.gs.auth.LoginToken;
import com.littlech.gs.communication.ServerMarshalerImpl;
import com.littlech.gs.game.IGame;


// TODO: Auto-generated Javadoc
/**
 * The Class ServerUser.
 */
public class ServerUser 
// extends F10 // Human seat user
// implements UserDetails, CredentialsContainer
extends LoginToken
{
	

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The connection. */
    private INonBlockingConnection connection;

    // private User user;

    /** The id. */
    // private final String id;

    // private volatile boolean online =false;

    /** The game. */
    private IGame game;
    
    /** The write lock. */
    private Lock lock = new ReentrantLock(), writeLock = new ReentrantLock();
    

	/** The send lobby updates. */
	private boolean sendLobbyUpdates;
    
	/**
	 * User
	 */
    @XmlElement(required = true)
    protected F6 f11;

	private Authentication authentication = null;

    /**
     * Instantiates a new server user.
     *
     * @param _id the _id
     * @param _user the _user
     */
    public ServerUser (LoginToken token) {
    	super(token.getUsername(), token.getPassword(), token.getAuthorities(), token.getCreateDate(), token.getAvatar());
    	setId(token.getId());
    	F6 user = new F6();
    	user.setF7(token.getUsername()); // getUsername()); Object is not yet initialized, cannot use 
    	user.setF8(token.getAvatar()); // getAvatar()); -II-
    	setF11(user);
/*
    	if (token.getUsername() == null || token.getAvatar() == null) {
    		throw new NullPointerException();
    	}
    	if (getUsername() == null) {
    		throw new NullPointerException();
    	}
    	if (getAvatar() == null) {
    		throw new NullPointerException();
    	}
    	if (getF11() == null) {
    		throw new NullPointerException();
    	}
    	if (getF11().getF7() == null) {
    		throw new NullPointerException();
    	}
    	if (getF11().getF8() == null) {
    		throw new NullPointerException();
    	}
    	*/
    	// id = _id;
    	// setF11(_user);
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    	StringBuilder sb = new StringBuilder();
    	sb.append("ServerUser{");
    	// sb.append("id=" + getId());
    	sb.append(", user=" + parseUser());
    	sb.append(", online=" + isOnline());
    	sb.append(", in game=" + isInGame());
    	sb.append("}");
    	return sb.toString();
    }
    
    private String parseUser() {
    	F6 communicationUser = getF11();
    	StringBuilder sb = new StringBuilder();
    	sb.append("User[");
    	sb.append("name=").append(communicationUser.getF7());
    	sb.append(", ");
    	sb.append("avatar=").append(communicationUser.getF8());
    	sb.append("]");
    	return sb.toString();
    }

    /**
     * Gets the value of the f11 property.
     * 
     * @return
     *     possible object is
     *     {@link F6 }
     *     
     */
    public F6 getF11() {
        return f11;
    }

    /**
     * Sets the value of the f11 property.
     * 
     * @param value
     *     allowed object is
     *     {@link F6 }
     *     
     */
    public void setF11(F6 value) {
        this.f11 = value;
    }

    /**
     * Gets the connection.
     *
     * @return the connection
     */
    public INonBlockingConnection getConnection () {
        return connection;
    }

    /**
     * Sets the game.
     *
     * @param val the new game
     */
    public void setGame (IGame val) {
        this.game = val;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    /*
    public String getId () {
        return id;
    }
    */
/*
    public User getUser () {
        return user;
    }
*/
    /**
 * Checks if is online.
 *
 * @return true, if is online
 */
public boolean isOnline () {
        return getConnection() != null; // online;
    }

    /**
     * Gets the game.
     *
     * @return the game
     */
    public IGame getGame () {
        return game;
    }

    /*
    public void setOnline (boolean val) {
        this.online = val;
    }*/

    /**
     * Connected.
     *
     * @param _connection the _connection
     */
    public void connected (INonBlockingConnection _connection, Authentication result) {
    	getLock().lock();
    	if (isOnline()) {
    		throw new IllegalStateException("Server user is already online " + this);
    	}
    	if (result == null) {
    		throw new NullPointerException("Authentication is null");
    	}
    	if (!result.isAuthenticated()) {
    		throw new IllegalStateException("Authentication failed");
    	}
    	setConnection(_connection);
    	// setOnline(true);
    	setAuthentication(result);
    	getLock().unlock();
    }
    
    public void resetConnection() {
    	logCommunication.info("Resetting connection for " + this);
    	this.connection = null;
	}

    public void setConnection(INonBlockingConnection connection) {
    	logCommunication.info("Setting connection for " + this);
		this.connection = connection;
	}

	/**
     * Disconnected.
     */
    public void disconnected () {
    	getLock().lock();
    	if (!isOnline()) {
    		throw new IllegalStateException("Server user is not online " + this);
    	}
	    removeFromGame();
    	resetConnection();
    	// setOnline(false);
    	getLock().unlock();
    }

    /**
     * Removes the from game.
     */
    public void removeFromGame () {
		// IGame game = getGame();
		if (game != null) {
		game.getPlayerStore().remove(this);
		game = null;
		}
    }

    /**
     * Write.
     *
     * @param _command the _command
     * @throws Exception the exception
     */
    public void write (E19 _command) throws Exception {
    	ServerMarshalerImpl marshaler = null;
		try {
			getWriteLock().lock();
	    	if (!isOnline()) {
	    		throw new IllegalStateException("User is offline");
	    	}
			marshaler = (ServerMarshalerImpl) GameServer.getInstance().getMarshalersPool().borrowObject();

	    	String marshalled = marshaler.marshalSC(_command);
	    	marshalled.replaceAll("\n", "");
			String s = marshalled + DELIMITER;
			// System.out.println("Writing to " + this + " command " + marshalled);
			getConnection().write(s);
		} catch (Exception e) {
			// e.printStackTrace();
			throw e;
		} finally {
			if (marshaler != null) {
			try {
				GameServer.getInstance().getMarshalersPool().returnObject(marshaler);
			} catch (Exception e) {
				logGeneral.error("Error while returning marshaler to pool", e);
			}
			}
			getWriteLock().unlock();
		}
    }

    /**
     * Gets the lock.
     *
     * @return the lock
     */
    public Lock getLock () {
        return lock;
    }
    
    /**
     * Gets the write lock.
     *
     * @return the write lock
     */
    public Lock getWriteLock() {
		return writeLock;
	}

	/**
	 * Write string.
	 *
	 * @param all the all
	 * @throws BufferOverflowException the buffer overflow exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void writeString(String all) throws BufferOverflowException, IOException {
		getWriteLock().lock();
		// System.out.println("Writing all");
		getConnection().write(all + DELIMITER);
		getWriteLock().unlock();
	}

	/**
	 * Sets the send lobby updates.
	 *
	 * @param b the new send lobby updates
	 */
	public void setSendLobbyUpdates(boolean b) {
		sendLobbyUpdates = b;
	}
	
	/**
	 * Checks if is send lobby updates.
	 *
	 * @return true, if is send lobby updates
	 */
	public boolean isSendLobbyUpdates() {
		return sendLobbyUpdates;
	}

	/**
	 * Checks if is in game.
	 *
	 * @return true, if is in game
	 */
	public boolean isInGame() {
		boolean ret = getGame() != null;
		return ret;
	}
	
	/*

	@Override
	public void eraseCredentials() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}
	*/

	
	private void setAuthentication(Authentication result) {
		this.authentication  = result;
	}
	
	
	public Authentication getAuthentication() {
		return authentication;
	}

}

