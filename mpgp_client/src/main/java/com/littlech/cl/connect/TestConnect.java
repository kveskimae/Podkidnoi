/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.connect;

import java.io.FileInputStream;
import java.util.Properties;

public class TestConnect {

	/**
	 * Server IP address and port
	 */
	private String ip;

	private int port;

	/**
	 * User ID and secret token
	 */
	private String id, token;

	/**
	 * Location of image catalog
	 */
	private String img;

	public static void main(String[] args) throws Exception {
		TestConnect tc = new TestConnect();
		tc.tryConnect();
		Thread.currentThread().sleep(5000);
	}

	public TestConnect() {
		try {
			Properties properties = new Properties();
			FileInputStream in = new FileInputStream("params.properties");
			properties.load(in);
			in.close();
			loadInitsFromFile(properties);
			checkInits();
		} catch (Exception e) {
			System.out.println("Error while constructing test connector");
			throw new ExceptionInInitializerError(e);
		}
	}

	protected void tryConnect() throws Exception {
		MarshalerImpl.getInstance();
		Connector connector = new Connector(null);
		try {
			connector.connect(ip, port);
			connector.login(id, token);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Connecting to game server failed");
		}
	}

	private void checkInits() {
		if (ip == null) {
			throw new NullPointerException(
					"IP address (\"ip\") is not provided");
		}
		if (id == null) {
			throw new NullPointerException("User ID (\"id\") is not provided");
		}
		if (token == null) {
			throw new NullPointerException(
					"Secret token (\"token\") is not provided");
		}
		if (img == null) {
			throw new NullPointerException(
					"Image catalog (\"img\") is not provided");
		}
	}

	private int parsePort(final String portString) {
		int ret;
		if (portString == null) {
			throw new NullPointerException("Port (\"port\") is not provided");
		}
		try {
			ret = Integer.valueOf(portString).intValue();
		} catch (Exception e) {
			throw new IllegalArgumentException(
					"Port (\"port\") must be a positive number");
		}
		if (ret < 1) {
			throw new IllegalArgumentException(
					"Port (\"port\") must be a positive number");
		}
		return ret;
	}

	/*
	 * private int parseMode(final String modeString) { int ret; try { ret =
	 * Integer.valueOf(modeString).intValue(); if (ret < 0 || ret > 2) { throw
	 * new IllegalArgumentException("Mode must be 0, 1 or 2"); } } catch
	 * (Exception e) { ret = 0; } return ret; }
	 */
	private void loadInitsFromFile(final Properties properties) {
		ip = (String) properties.get("ip");
		port = parsePort((String) properties.get("port"));
		id = (String) properties.get("id");
		token = (String) properties.get("token");
		img = (String) properties.get("img");
		// mode = parseMode((String) properties.get("mode"));
	}

}
