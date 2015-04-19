/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.gs.communication;
import org.springframework.security.access.annotation.Secured;

import com.littlech.gen.c.C1;
import com.littlech.gen.d.D15;
import com.littlech.gen.d.D4;
import com.littlech.gs.game.GameCommandImpl;
import com.littlech.gs.game.IGame;
import com.littlech.gs.lobby.Lobby;
import com.littlech.gs.user.ServerUser;

// TODO: Auto-generated Javadoc
/**
 * The Class MessageHandler.
 */
public class MessageHandler {

	/** The dog. */
	// private WatchDog dog; // = new WatchDog();
	
	/** The lobby. */
	private Lobby lobby; //  = new Lobby();

	/**
	 * Instantiates a new message handler.
	 */
	public MessageHandler() {
	}
	
	
	
	/*
	public void setDog(WatchDog dog) {
		this.dog = dog;
	}
	*/
	
	/**
	 * Gets the dog.
	 *
	 * @return the dog
	 */
	/*
	public WatchDog getDog() {
		return dog;
	}
	*/
	
	public void setLobby(Lobby lobby) {
		this.lobby = lobby;
	}



	/**
	 * Gets the lobby.
	 *
	 * @return the lobby
	 */
	public Lobby getLobby() {
		return lobby;
	}

	/* (non-Javadoc)
	 * @see org.xsocket.connection.IDataHandler#onData(org.xsocket.connection.INonBlockingConnection)
	 */
	// XXX command validations must be synchronized on game instance!
	/*
	public boolean onData(INonBlockingConnection _connection) {
		ServerMarshalerImpl marshaler = null;
		try {
			marshaler = (ServerMarshalerImpl) GameServer.getInstance().getMarshalersPool().borrowObject();
		String data = _connection.readStringByDelimiter(DELIMITER);
			D4 received =marshaler.unmarshalCS(data);
			C1 code = received.getC13();
			switch (code) {
			case C_2:
				
				// User is not yet online, login command is allowed
				getDog().handleLoginCommand(received, _connection);
				break;

			default:

				ServerUser su = null;
				Object attachment = _connection.getAttachment();
				if (attachment == null || !(attachment instanceof ServerUser)) {
					throw new IllegalStateException("No server user: " + attachment);
				}
				su = (ServerUser)attachment;
				System.out.println("Received " + received + " from " + su);
				handleNonLoginCommand(received,su);
				break;
			}
			
			
			return true;
		}
		catch (ClosedChannelException ignored) {
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (marshaler != null) {
			try {
				GameServer.getInstance().getMarshalersPool().returnObject(marshaler);
			} catch (Exception e) {
				e.printStackTrace();
			}
			}

			SecurityContextHolder.getContext().setAuthentication(null);
		}
		return false;
	}
	*/

	@Secured("ROLE_PLAYER")
	 // @PreAuthorize("hasRole('ROLE_PLAYER')")
	public void handleNonLoginCommand(D4 received, ServerUser su) {


		// XXX
		// GameServer.getInstance().getServer2().start();

		C1 code = received.getC13();
		
		switch (code) {
		case C_11:
			/* TODO checking command eligibility must also be synchronized in game, not only handling command */
			
			IGame g = su.getGame();
			if (g == null) {
				throw new NullPointerException("Server user has no game associated with it");
			}
			g.getCommandHandler().queueGameCommand(new GameCommandImpl(received, su));
			break;
		case C_9:
			IGame g2 = su.getGame();
			if (g2 == null) {
				throw new NullPointerException("Server user has no game associated with it");
			}

			g2.getCommandHandler().queueGameCommand(new GameCommandImpl(received, su));
			
			/*
			RulesCheckResult checkResult2 = g2.getRules().isAllowed(su, received);
			if (checkResult2.isAllowed()) {
				g2.queueGameCommand(new GameCommandImpl(received, su));
			} else {
				handleFail(checkResult2);
				E19 failureCommand = getGameFailureCommand(code, checkResult2.getReason());
				su.write(failureCommand);
			}
			*/
			break;
		case C_7:
			if (su.isInGame()) {
				throw new IllegalStateException("User "+su+" is already in game " + su.getGame().getId());
			}
			D15 joinContent = (D15) received
					.getD5();
			String id = joinContent.getD16();
			IGame g1 = getLobby().getTables().get(id).getGame();
			if (g1 == null) {
				throw new NullPointerException("No game was not found from lobby with id '" + id + "'");
			}

			g1.getCommandHandler().queueGameCommand(new GameCommandImpl(received, su));
			
			/*
			RulesCheckResult checkResult1 = g1.getRules().isAllowed(su, received);
			if (checkResult1.isAllowed()) {
				g1.queueGameCommand(new GameCommandImpl(received, su));
			} else {
				handleFail(checkResult1);
				E19 failureCommand = getGameFailureCommand(code, checkResult1.getReason());
				su.write(failureCommand);
			}
			*/
			break;
		case C_8:
			if (!su.isInGame()) {
				throw new IllegalStateException("User "+su+" is not in game");
			}
			IGame g3 = su.getGame();
			if (g3 == null) {
				throw new NullPointerException("User "+su+" has null object as the game associated with it");
			}

			g3.getCommandHandler().queueGameCommand(new GameCommandImpl(received, su));
			
			/*
			RulesCheckResult checkResult3 = g3.getRules().isAllowed(su, received);
			if (checkResult3.isAllowed()) {
				g3.queueGameCommand(new GameCommandImpl(received, su));
				// su.removeFromGame();
			} else {
				handleFail(checkResult3);
				E19 failureCommand = getGameFailureCommand(code, checkResult3.getReason());
				su.write(failureCommand);
			}
			*/
			break;
		case C_4:
			getLobby().executeRegisterAndSendAll(su);
			break;
		case C_6:
			getLobby().executeNewTable(su, received);
			break;
			/*
		case SITDOWN:
			if (gamesRunner.handleSitDownCommand(received, nbc)) {
				handled = true;
			}
			break;
		case LEAVETABLE:
			if (gamesRunner.handleLeaveGame(received, nbc)) {
				handled = true;
			}
			break;
		case JOINTABLE:
			if (lobby.handleJoin(received, nbc)) {
				handled = true;
			}
			break;
			*/
		default:
			throw new IllegalArgumentException("Unsupported command type for online users: " + code);
		}
	}

}
