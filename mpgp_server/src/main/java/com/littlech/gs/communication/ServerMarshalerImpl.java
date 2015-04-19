/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.gs.communication;

import javax.xml.bind.JAXBException;

import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.apache.commons.lang.NullArgumentException;
import org.xml.sax.SAXException;

import com.littlech.cl.connect.IMarshaler;
import com.littlech.cl.connect.MarshalerInitializationException;
import com.littlech.gen.d.D4;
import com.littlech.gen.e.E19;

/**
 * 
 * Commands marshaler implementation
 * 
 * @author Kristjan Veskim�e
 * 
 */
public class ServerMarshalerImpl implements IMarshaler {

	/**
	 * JAXB command factories and marshalers
	 */
	private final com.littlech.gen.e.ObjectFactory scFactory = new com.littlech.gen.e.ObjectFactory();
	private final com.littlech.gen.d.ObjectFactory csFactory = new com.littlech.gen.d.ObjectFactory();
	private final Marshaller scMarshaller, csMarshaller;
	private final Unmarshaller scUnmarshaller, csUnmarshaller;

	/**
	 * 
	 * Initializes JAXB marshalers
	 * 
	 * @throws JAXBException
	 * @throws SAXException
	 * @throws MarshalerInitializationException
	 */
	public ServerMarshalerImpl(Marshaller scMarshaller, Marshaller csMarshaller,
			Unmarshaller scUnmarshaller, Unmarshaller csUnmarshaller)
			throws JAXBException, SAXException,
			MarshalerInitializationException {
		if (scMarshaller == null) {
			throw new NullArgumentException("s->c marshaler is null");
		}
		if (csMarshaller == null) {
			throw new NullArgumentException("c->s marshaler is null");
		}
		if (scUnmarshaller == null) {
			throw new NullArgumentException("s->c unmarshaler is null");
		}
		if (csUnmarshaller == null) {
			throw new NullArgumentException("c->s unmarshaler is null");
		}
		this.scMarshaller = scMarshaller;
		this.csMarshaller = csMarshaller;
		this.scUnmarshaller = scUnmarshaller;
		this.csUnmarshaller = csUnmarshaller;
	}

	@Override
	public String marshalCS(D4 _cs) throws JAXBException {
		JAXBElement<D4> element = csFactory.createD6(_cs);
		StringWriter sw = new StringWriter();
		csMarshaller.marshal(element, sw);
		String xml = sw.toString();
		return xml;
	}

	@Override
	public String marshalSC(E19 cs) throws JAXBException {
		JAXBElement<E19> element = scFactory.createE23(cs);
		StringWriter sw = new StringWriter();
		scMarshaller.marshal(element, sw);
		String xml = sw.toString();
		return xml;
	}

	@Override
	public D4 unmarshalCS(String _xml) throws JAXBException {
		StringReader sr = new StringReader(_xml);
		JAXBElement<D4> element = (JAXBElement<D4>) csUnmarshaller
				.unmarshal(sr);
		D4 sc = element.getValue();
		return sc;
	}

	@Override
	public E19 unmarshalSC(String _xml) throws JAXBException {
		StringReader sr = new StringReader(_xml);
		JAXBElement<E19> element = (JAXBElement<E19>) scUnmarshaller
				.unmarshal(sr);
		E19 sc = element.getValue();
		return sc;
	}

}
