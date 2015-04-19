/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.gs.communication;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.apache.commons.pool.PoolableObjectFactory;
import org.xml.sax.SAXException;

import com.littlech.cl.connect.MarshalerInitializationException;

public class MarshalersFactory implements PoolableObjectFactory {

	private final JAXBContext scJaxbContext, csJaxbContext;

	/**
	 * 
	 * Initializes JAXB marshalers
	 * 
	 * @throws MarshalerInitializationException
	 */
	public MarshalersFactory() throws JAXBException, SAXException, MarshalerInitializationException {
		/* SC */
		scJaxbContext = JAXBContext.newInstance("com.littlech.gen.e");

		/* CS */
		csJaxbContext = JAXBContext.newInstance("com.littlech.gen.d");
	}
	
	
	@Override
	public void activateObject(Object obj) throws Exception {
	}

	@Override
	public void destroyObject(Object obj) throws Exception {
	}

	@Override
	public Object makeObject() throws Exception {
		Marshaller scMarshaller = scJaxbContext.createMarshaller();
		scMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(true));

		Unmarshaller scUnmarshaller = scJaxbContext.createUnmarshaller();
		
		Marshaller csMarshaller = csJaxbContext.createMarshaller();
		csMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(true));
		
		Unmarshaller csUnmarshaller = csJaxbContext.createUnmarshaller();

		ServerMarshalerImpl ret = new ServerMarshalerImpl(scMarshaller, csMarshaller, scUnmarshaller, csUnmarshaller);
		return ret;
	}

	@Override
	public void passivateObject(Object obj) throws Exception {
	}

	@Override
	public boolean validateObject(Object obj) {
		return true;
	}

}
