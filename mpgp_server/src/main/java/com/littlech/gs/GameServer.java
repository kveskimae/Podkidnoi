/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.gs;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.apache.commons.pool.impl.StackObjectPool;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xsocket.connection.Server;

import com.littlech.gs.communication.MarshalersFactory;
import com.littlech.gs.communication.UserCommunicationHandler;
import com.littlech.gs.gate.Gateway;
import com.littlech.gs.lobby.Lobby;
import com.littlech.gs.user.ServerUser;
import com.littlech.gs.user.UserStore;

import static com.littlech.gs.gate.Gateway.*;

// TODO: Auto-generated Javadoc
/**
 * The Class GameServer.
 */
public class GameServer {

	/** The SERVIC e_ threads. */
	public int SERVICE_THREADS;

	/** The instance. */
	private static// ? XXX
	GameServer instance;

	/** The server. */
	private Server server;

	/*
	 * private tests.sec.Server server2;
	 * 
	 * public tests.sec.Server getServer2() { return server2; }
	 * 
	 * public void setServer2(tests.sec.Server server2) { this.server2 =
	 * server2; }
	 */

	/** The service. */
	private ScheduledExecutorService service;

	/** The message handler. */
	// private MessageHandler messageHandler;// = new MessageHandler();

	private StackObjectPool marshalersPool;

	private UserCommunicationHandler communicationHandler;

	private Lobby lobby; // = new Lobby();

	/** The users. */
	private UserStore users;

	// private String host; // "127.0.0.1" not used!!

	private int port; // 4000

	public UserStore getUsers() {
		return users;
	}

	public void setUsers(UserStore users) {
		this.users = users;
	}

	public UserCommunicationHandler getCommunicationHandler() {
		return communicationHandler;
	}

	public void setCommunicationHandler(
			UserCommunicationHandler communicationHandler) {
		this.communicationHandler = communicationHandler;
	}

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

	/**
	 * Gets the message handler.
	 * 
	 * @return the message handler
	 */
	/*
	 * public MessageHandler getMessageHandler() { return messageHandler; }
	 * public void setMessageHandler(MessageHandler messageHandler) {
	 * this.messageHandler = messageHandler; }
	 */
	/*
	 * public String getHost() { return host; }
	 * 
	 * public void setHost(String host) { this.host = host; }
	 */
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * Instantiates a new game server.
	 * @throws Exception 
	 * @throws InitializationException
	 *             the initialization exception
	 */
	public void init() throws Exception {
		/*
		 * if (getHost() == null) { throw new
		 * NullPointerException("Host is not initialized"); } else if
		 * (getHost().length() < 1) { throw new
		 * IllegalArgumentException("Host value is empty"); }
		 */
		if (getPort() < 1) {
			throw new IllegalArgumentException("Port is not specified");
		}

			// DOMConfigurator.configureAndWatch("serverlogconf.xml", 5); //
			// initializing log4j delay 5''

			// FileWatchdog.DEFAULT_DELAY;

			// PropertyConfigurator.configure("log4j.properties");

			int numOfProcessors = Runtime.getRuntime().availableProcessors();
			SERVICE_THREADS = numOfProcessors + 2;

			System.out.println("Server is running " + SERVICE_THREADS
					+ " service threads");

			service = Executors.newScheduledThreadPool(SERVICE_THREADS);

			marshalersPool = new StackObjectPool();
			marshalersPool.setFactory(new MarshalersFactory());
			for (int i = 0; i < SERVICE_THREADS; i++) {
				marshalersPool.addObject();
			}

			server = new org.xsocket.connection.Server(
			// getHost(),

					getPort(), getCommunicationHandler());

			
	}

	public int getNumberOfServiceThreads() {
		return SERVICE_THREADS;
	}

	public StackObjectPool getMarshalersPool() {
		return marshalersPool;
	}

	/**
	 * Gets the online users.
	 * 
	 * @return the online users
	 */
	public List<ServerUser> getOnlineUsers() {
		List<ServerUser> ret = new ArrayList<ServerUser>();
		for (ServerUser su :
		// getMessageHandler().getDog().
		getUsers().getServerUsers().values()) {
			if (su.isOnline()) {
				ret.add(su);
			}
		}
		return ret;
	}

	/**
	 * Gets the single instance of GameServer.
	 * 
	 * @return single instance of GameServer
	 */
	public static GameServer getInstance() {
		return instance;
	}

	public static void setInstance(GameServer instance) {
		GameServer.instance = instance;
	}

	/**
	 * The main method.
	 * 
	 * @param _args
	 *            the arguments
	 */
	public static void main(String[] _args) {
		try {
			ApplicationContext context = new ClassPathXmlApplicationContext(
					// "classpath*:newSpringXMLConfig.xml"
					"classpath*:spring-config.xml"
					);
			instance = context.getBean("gameServer", GameServer.class);
			instance.start();

			// Initializing gateway
			Gateway gateway = context.getBean("gatewayID", Gateway.class);
			Gateway.setInstance(gateway);
		} catch (ExceptionInInitializerError e) {
			System.out.println("Initializing server failed");
			e.printStackTrace();
		}
	}

	/**
	 * Start.
	 * 
	 * @throws InitializationException
	 */
	public void start() {
		try {
			server.start();
			getLobby().start();
		} catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	/**
	 * Stop.
	 */
	public void stop() {
		try {
			try {
				logGeneral.info("Communication server is shutting down");
			} catch (Exception ignored) {
				System.out
						.println("Communication server is shutting down");
			}
			server.close();
			try {
				logGeneral.info("Communication server has been shut down");
			} catch (Exception ignored) {
				System.out.println("Communication server has been shut down");
			}
		} catch (Exception e) {
			try {
				logGeneral.error("Error while shutting communication server down", e);
			} catch (Exception ignored) {
				System.err.println("Error while shutting communication server down");
				e.printStackTrace();
			}
		}

		try {
			try {
				logGeneral.info("Executor service is shutting down");
			} catch (Exception ignored) {
				System.out
						.println("Executor service is shutting down");
			}
			getService().shutdown();
			try {
				logGeneral.info("Executor service has been shut down");
			} catch (Exception ignored) {
				System.out.println("Executor service has been shut down");
			}
		} catch (Exception e) {
			try {
				logGeneral.error("Error while shutting executor service down", e);
			} catch (Exception ignored) {
				System.err.println("Error while shutting executor service down");
				e.printStackTrace();
			}
		}
	}

	/**
	 * Gets the service.
	 * 
	 * @return the service
	 */
	public ScheduledExecutorService getService() {
		return service;
	}

}
