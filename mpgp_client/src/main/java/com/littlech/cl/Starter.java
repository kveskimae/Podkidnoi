/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.xml.bind.JAXBException;
import org.xml.sax.SAXException;

import com.littlech.cl.connect.Connector;
import com.littlech.cl.connect.MarshalerImpl;
import com.littlech.cl.connect.MarshalerInitializationException;
import com.littlech.gen.b.B1;
import com.littlech.gen.b.B20;
import com.littlech.gen.b.B6;
import com.littlech.cl.constants.*;

/**
 * 
 * Applet starting client application
 * 
 * @author Kristjan Veskim�e
 * 
 * 195.250.189.152
 * 
 */
public class Starter extends JApplet implements IImageLoader {

	/**
	 * Indicates if this is just a test of GUI
	 */
	/*
	private boolean testGUI = false,
			testConnect = false;
			*/
	/**
	 * mode: 0 - real, 1 - test gui, 2 - test connect
	 */
	private static final int mode = 1;
	
	public static final int MODE_REAL = 0, MODE_TEST_GUI = 1; // , MODE_TEST_CONNECT = 2;

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

	/**
	 * Cache for loaded images
	 */
	private Map<String, ImageIcon> imageCache = new HashMap<String, ImageIcon>();

	private JPanel core;

	/**
	 * Cache for card images
	 */
	private Map<B1, Map<B6, Image>> cardCache = new HashMap<B1, Map<B6, Image>>();

	/**
	 * Client logic and GUI controller
	 */
	private Controller controller;
	
	@Override
	public void init() {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					try {

						switch (mode) {
						case 0:
							System.out.println("In real mode");
							// Loads inits provided by HTML tags
							loadInitsFromParams();
							break;
						case 1:
							System.out.println("In GUI testing mode");
							// Loads inits from properties file
							loadInitsFromPropertiesFile();
							break;
						case 2:
							System.out.println("In connect testing mode");
							throw new IllegalStateException("Currently is connection testing non-functional");
						default:
							throw new IllegalStateException("Mode must be 0-2");
						}
							checkInits();
							initCards();
							initConnectorAndGUI();
							/*
						if (mode != MODE_TEST_CONNECT) {
						} else {
							throw new IllegalStateException("Connect test shouldn't be used with GUI");
						}
						*/
					} catch (Exception e) {
						System.err.println("Error in initialization");
						e.printStackTrace();
					}
				}
			});
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	private void loadInitsFromPropertiesFile() throws IOException {
	   	Properties prop = new Properties();
		prop.load(new FileInputStream("params.properties"));
		ip = prop.getProperty("ip");
		port = parsePort(prop.getProperty("port"));
		id = prop.getProperty("id");
		token = prop.getProperty("token");
		img = prop.getProperty("img");
	}

	/**
	 * Initializes connection with the game server
	 * 
	 * @throws JAXBException
	 * @throws SAXException
	 * @throws IOException
	 * @throws MarshalerInitializationException
	 */
	protected void initConnectorAndGUI() throws JAXBException, SAXException, IOException, MarshalerInitializationException {
		core = new JPanel();
		setContentPane(core);
		setSize(Sizes.APPLET);

		MarshalerImpl.getInstance(); // Loading

		controller = new Controller(this, getCore()); // , connector);
		Connector connector = new Connector(controller); //, mode);
		controller.setCSHandler(connector);
		// XXX move connect and login to start()
		try {
		connector.connect(ip, port);
		// connector.setController(controller);
		connector.login(id, token);
		} catch (Exception e) {
			e.printStackTrace();
			getController().presentFatalError("Connecting to game server failed");
		}
		/*
		if (mode != MODE_TEST_GUI) {
		} else {
			controller = new Controller(this, getCore(), true);
		}
		*/
	}
	
	public Controller getController() {
		return controller;
	}

	/**
	 * Returns content pane of this applet
	 * 
	 * @return Content pane container
	 */
	public Container getCore() {
		return core;
	}
	
	private void checkInits() {
		if (ip == null) {
			throw new NullPointerException("IP address (\"ip\") is not provided");
		}
		if (id == null) {
			throw new NullPointerException("User ID (\"id\") is not provided");
		}
		if (token == null) {
			throw new NullPointerException("Secret token (\"token\") is not provided");
		}
		if (img == null) {
			throw new NullPointerException("Image catalog (\"img\") is not provided");
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
			throw new IllegalArgumentException("Port (\"port\") must be a positive number");
		}
		if (ret < 1) {
			throw new IllegalArgumentException("Port (\"port\") must be a positive number");
		}
		return ret;
	}
	/*
	private int parseMode(final String modeString) {
		int ret;
		try {
			ret = Integer.valueOf(modeString).intValue();
			if (ret < 0 || ret > 2) {
				throw new IllegalArgumentException("Mode must be 0, 1 or 2");
			}
		} catch (Exception e) {
			ret = 0;
		}
		return ret;
	}
	*/
	
	private void loadInitsFromParams() {
		ip = getParameter("ip");
		port = parsePort(getParameter("port"));
		id = getParameter("id");
		token = getParameter("token");
		img = getParameter("img");
	}

	/*
	private void loadInitsFromFile(final Properties properties) {
		ip = (String) properties.get("ip");
		port = parsePort((String) properties.get("port"));
		id = (String) properties.get("id");
		token = (String) properties.get("token");
		img = (String) properties.get("img");
		mode = parseMode((String) properties.get("mode"));
	}
	
	public void foo() {
		InputStream y = getAppletContext().getStream("x");
		Source c = (Source)y;
	}
	*/

	@Override
	public ImageIcon fetchImage(String _name) {
		if (_name == null) {
			throw new NullPointerException(); // "Image file name is null");
		}
		ImageIcon ret = imageCache.get(_name);
		if (ret == null) {
			String location = img + _name;
			// System.out.println("location='" + location + "'");
			ret = new ImageIcon(getImage(getDocumentBase(), location));
			imageCache.put(_name, ret);
			if (ret.getIconWidth() < 1) {
				throw new IllegalArgumentException("Image not found: " + location);
			}
			
		}
		return ret;
	}

	@Override
	public Image getCardImg(B20 c) {
		if (c == null) {
			throw new NullPointerException(); // "Card is null");
		}
		if (c.getB22() == null) {
			throw new NullPointerException(); // "Rank is null");
		}
		if (c.getB21() == null) {
			throw new NullPointerException(); // "Suit is null");
		}
		Map<B6, Image> suitMap = cardCache.get(c.getB21());
		Image ret = suitMap.get(c.getB22());
		return ret;
	}

	/**
	 * Initializes card images into cache
	 */
	private void initCards() {
		String fileName;
		ImageIcon suitIcon;
		Image suitImg;
		BufferedImage suitBuffered;
		Graphics g;
		Image cardImg;
		Map<B6, Image> ranksInSuitMap;
		for (B1 s : B1.values()) {
			fileName = getImageName(s);
			suitIcon = fetchImage(fileName);
			suitImg = suitIcon.getImage();
			suitBuffered = new BufferedImage(suitImg.getWidth(null), suitImg.getHeight(null), BufferedImage.TYPE_INT_ARGB);
			g = suitBuffered.getGraphics();
			g.drawImage(suitImg, 0, 0, null);
			ranksInSuitMap = new HashMap<B6, Image>();
			for (B6 r : B6.values()) {
				int x = r.ordinal() * 60;
				cardImg = new ImageIcon(suitBuffered.getSubimage(x, 0, 60, 85)).getImage();
				ranksInSuitMap.put(r, cardImg);
			}
			cardCache.put(s, ranksInSuitMap);
		}
	}

	public static String getImageName(final B1 suit) {
		switch (suit) {
		case B_2:
			return com.littlech.cl.gui.podkidnoy.constants.PodkidnoyImages.CLUBS;
		case B_3:
			return com.littlech.cl.gui.podkidnoy.constants.PodkidnoyImages.DIAMONDS;
		case B_4:
			return com.littlech.cl.gui.podkidnoy.constants.PodkidnoyImages.HEARTS;
		case B_5:
			return com.littlech.cl.gui.podkidnoy.constants.PodkidnoyImages.SPADES;
		default:
			throw new IllegalArgumentException("Unsupported suit: " + suit);
		}
	}

}
