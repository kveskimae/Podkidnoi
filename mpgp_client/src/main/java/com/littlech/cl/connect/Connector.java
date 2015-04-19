/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.connect;

import static com.littlech.cl.connect.ConnectionConstants.DELIMITER;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.JAXBException;
import org.xml.sax.SAXException;

import com.littlech.cl.Controller;
import com.littlech.gen.c.C1;
import com.littlech.gen.d.D4;
import com.littlech.gen.d.D9;

public class Connector implements Runnable, ICSHandler {
	
	/**
	 * Character encoding
	 */
	private Charset charset = Charset.forName("UTF-8");
	private CharsetEncoder encoder = charset.newEncoder();
	private CharsetDecoder decoder = charset.newDecoder();
	private ByteBuffer readBuffer = ByteBuffer.allocate(1024), writeBuffer = ByteBuffer.allocate(1024);
	
	/**
	 * Non-blocking IO channel
	 */
	private SocketChannel channel;
	private InetSocketAddress address;
	public Selector selector;
	private SelectionKey key;
	
	/**
	* Commands waiting at client to be sent to server
	 */
	public List<D4> stringsToSend = new LinkedList<D4>();
	
	/**
	 * Holds partially received command as a character sequence in memory 
	 */
	private String mBegin;

	/**
	 * Client application controller
	 */
	private final Controller mController;
	// private int mMode;

	/**
	 * 
	 * Constructor
	 * 
	 * @throws SAXException 
	 * @throws JAXBException 
	 */
	public Connector(final Controller _controller//, final int _mode
			) throws JAXBException, SAXException {
		this.mController = _controller;
		// this.mMode = _mode;
	}
	
	/**
	 * 
	 * Sets the client application controller for this connector
	 * 
	 * @param _controller Client application controller
	 */
//	public void setController(final Controller _controller) {
//		this.mController = _controller;
//	}
	
	/**
	 * 
	 * Makes a connection with the game server and sends the login command
	 * 
	 * @param ip Server IP address
	 * @param port Server socket port
	 * @param id User ID
	 * @param token User secret token
	 * @throws IOException
	 */
//	public void connect(String ip, int port, String id, String token) throws IOException {
//			connect(ip, port);
//			login(token, id);
//	}

	/**
	 * 
	 * Makes a connection with the game server
	 * 
	 * @param ip Server IP address
	 * @param port Server socket port
	 * @throws IOException
	 */
	public void connect(String ip, int port) throws IOException {
		/*
		if (mMode != Starter.MODE_TEST_CONNECT) {
		}
		*/
		mController.presentInfo("Connecting to game server");
		channel = SocketChannel.open();
		address = new InetSocketAddress(ip, port);
		channel.connect(address);
		channel.configureBlocking(false);
		selector = SelectorProvider.provider().openSelector();
		key = channel.register(selector, SelectionKey.OP_CONNECT);
		
		/* Client IO thread */
		Thread clientThread = new Thread(this);
		clientThread.setName("CONNECTOR");
		clientThread.setDaemon(true);
		clientThread.start();
	}

	/**
	 * 
	 * Sends login command to game server
	 * 
	 * @param id User ID
	 * @param token User secret token
	 */
	public void login(String id, String token) {
		/*
		if (mMode != Starter.MODE_TEST_CONNECT) {
		}
		*/
		if (mController == null) {
			throw new NullPointerException(); // "Controller has not been set");
		}
		mController.presentInfo("Logging into game server");
		D4 loginCommand = new D4();
		loginCommand.setC13(C1.C_2);
		D9 loginContent = new D9();
		loginContent.setD10(id);
		loginContent.setD11(token);
		loginCommand.setD5(loginContent);
		sendCS(loginCommand);
	}

	@Override
	public void sendCS(D4 cmd) {
		synchronized (stringsToSend) {
			stringsToSend.add(cmd);
			selector.wakeup();
		}
	}

	/**
	 * Reads commands from socket and performs writing of commands waiting in IO buffer
	 */
	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName() + " starts");
		try {
			channel.finishConnect();
			key.interestOps(SelectionKey.OP_READ);
			while (true) {
				write();
				selector.select();
				read();
			} // while true
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + " finishes");
	}

	private void write() throws IOException, JAXBException, SAXException, MarshalerInitializationException {
		synchronized (stringsToSend) {
			if (!stringsToSend.isEmpty()) {
				performWrite();
			}
		}

	}

	private void read() throws IOException, JAXBException, SAXException, MarshalerInitializationException {
		Iterator<SelectionKey> it = selector.selectedKeys().iterator();
		while (it.hasNext()) {
			SelectionKey selectedKey = it.next();
			if (!selectedKey.isValid()) {
				throw new IllegalStateException("key is not valid");
			}
			if (!key.equals(selectedKey)) {
				throw new IllegalStateException("keys are not the same");
			}
			if (selectedKey.isReadable()) {
				performRead();
			}
			it.remove();
		}

	}

	private void performWrite() throws IOException, JAXBException, SAXException, MarshalerInitializationException {
		key.interestOps(SelectionKey.OP_WRITE);
		sendWhile: while (!stringsToSend.isEmpty()) {
			D4 nextSend = stringsToSend.get(0);
			String curSend = MarshalerImpl.getInstance().marshalCS(nextSend);
			String replacedCurSend = curSend.replaceAll("\n", "");
			System.out.println("->: " + replacedCurSend);
			String msg = curSend + DELIMITER;
			writeBuffer = encoder.encode(CharBuffer.wrap(msg.toCharArray()));
			channel.write(writeBuffer);
			if (writeBuffer.hasRemaining()) {
				break sendWhile;
			}
			stringsToSend.remove(0);
		}
		if (stringsToSend.isEmpty()) {
			key.interestOps(SelectionKey.OP_READ);
		}
	}

	private void performRead() throws IOException, JAXBException, SAXException, MarshalerInitializationException {
		readBuffer.clear();
		channel.read(readBuffer);
		readBuffer.flip();
		CharBuffer decoded = decoder.decode(readBuffer);
		String decodedString = decoded.toString();
		if (!decodedString.contains(DELIMITER)) {
			if (mBegin == null) {
				mBegin = decodedString;
			} else {
				mBegin = mBegin + decodedString;
			}
		} else {
			if (mBegin != null) {
				decodedString = mBegin + decodedString;
			}
			String[] messages = decodedString.split(DELIMITER);
			for (int i = 0; i < messages.length; i++) {
				String message = messages[i];
				boolean lastSaved = false;
				if (i == messages.length - 1) {
					if (!decodedString.endsWith(DELIMITER)) {
						mBegin = message;
						lastSaved = true;
					} else {
						mBegin = null;
					}
				}
				if (!lastSaved) {
					String replacedMessage = message.replaceAll("\n", "");
					System.out.println("<-: " + replacedMessage);
/*
					if (mMode != Starter.MODE_TEST_CONNECT) {
					}
					*/
					mController.handleSC(MarshalerImpl.getInstance().unmarshalSC(message));
				} // !lastSaved
			} // for i
		} // else
	}

}
