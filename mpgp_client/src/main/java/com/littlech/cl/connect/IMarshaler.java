/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.connect;

import javax.xml.bind.JAXBException;

import com.littlech.gen.d.D4;
import com.littlech.gen.e.E19;

/**
 * 
 * Commands marshaler
 * 
 * @author Kristjan Veskim�e
 *
 */
public interface IMarshaler {
	
	/**
	 * Unmarshals parameter client to server command from string representation into command object
	 * @param xml String representing command from client to server
	 * @return Client to server command object
	 * @throws JAXBException 
	 */
	D4 unmarshalCS(String xml) throws JAXBException;
	
	/**
	 * Marshals parameter client to server command into string representation
	 * @param cs Command from client to server
	 * @return String representation of parameter command
	 * @throws JAXBException
	 */
	String marshalCS(D4 cs) throws JAXBException;

	/**
	 * Unmarshals parameter server to client command from string representation into command object
	 * @param xml String representing command from server to client
	 * @return Server to client command object
	 */
	E19 unmarshalSC(String xml) throws JAXBException;

	/**
	 * Marshals parameter server to client command into string representation
	 * @param sc Command from server to client
	 * @return String representation of parameter command
	 * @throws JAXBException 
	 * @throws JAXBException
	 */
	String marshalSC(E19 sc) throws JAXBException;

}
