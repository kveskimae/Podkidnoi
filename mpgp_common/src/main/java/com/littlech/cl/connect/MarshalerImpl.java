/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.connect;

import javax.xml.bind.JAXBException;


import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import org.xml.sax.SAXException;

import com.littlech.gen.d.D4;
import com.littlech.gen.e.E19;

/**
 * 
 * Commands marshaler implementation
 * 
 * @author Kristjan Veskim�e
 * 
 */
public class MarshalerImpl implements IMarshaler {

	/**
	 * Singleton instance
	 */
	private static MarshalerImpl instance;

	/**
	 * Catalog for schemas
	 */
	private static final String
	// Schemas catalog
			SCHEMAS = "/resource/schemas/",
			// SC schema
			SCHEMA_SC = "sc.xsd",
			// CS schema
			SCHEMA_CS = "cs.xsd";

	public static MarshalerImpl getInstance() throws JAXBException, SAXException, MarshalerInitializationException {
		if (instance == null) {
			instance = new MarshalerImpl();
		}
		return instance;
	}

	/**
	 * JAXB command factories and marshalers
	 */
	private com.littlech.gen.e.ObjectFactory scFactory;
	private com.littlech.gen.d.ObjectFactory csFactory;
	private JAXBContext scJaxbContext, csJaxbContext;
	private Schema scSchema, csSchema;
	private Marshaller scMarshaller, csMarshaller;
	private Unmarshaller scUnmarshaller, csUnmarshaller;

	/**
	 * 
	 * Initializes JAXB marshalers
	 * 
	 * @throws JAXBException
	 * @throws SAXException
	 * @throws MarshalerInitializationException
	 */
	private MarshalerImpl() throws JAXBException, SAXException, MarshalerInitializationException {
		// URI scURI = getSchemaURI(SCHEMA_SC);
		// URI csURI = getSchemaURI(SCHEMA_CS);
		
		// InputStream in = ...
		// BufferedReader x = new BufferedReader( new InputStreamReader(in) );

		/* SC */
		scFactory = new com.littlech.gen.e.ObjectFactory();
		scJaxbContext = JAXBContext.newInstance("com.littlech.gen.e");
		scMarshaller = scJaxbContext.createMarshaller();
		scMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(true));

		scUnmarshaller = scJaxbContext.createUnmarshaller();

		/* CS */
		csFactory = new com.littlech.gen.d.ObjectFactory();
		csJaxbContext = JAXBContext.newInstance("com.littlech.gen.d");		
		csMarshaller = csJaxbContext.createMarshaller();
		csMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(true));
		

		csUnmarshaller = csJaxbContext.createUnmarshaller();
		
		/* Verification */
		/*
		URL foo1 = getClass().getResource(SCHEMAS + SCHEMA_SC);
		URL foo2 = getClass().getResource(SCHEMAS + SCHEMA_CS);		
		scSchema = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(
				//		new File(scURI)
						
					foo1	
				);
		scUnmarshaller.setSchema(scSchema);
		scMarshaller.setSchema(scSchema);
		
		csSchema = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(
		//		new File(csURI)
			foo2	
		);
		
		csMarshaller.setSchema(csSchema);
		csUnmarshaller.setSchema(csSchema);

*/
	}

	/* does not work properly with jars
	public URI getSchemaURI(final String schema) throws MarshalerInitializationException {
		try {
			URL foo = getClass().getResource(SCHEMAS + schema);
			URI fooURI = foo.toURI();
			return fooURI;
		} catch (Exception e) {
			throw new MarshalerInitializationException("Cannot find schema: " + schema);
		}
	}
	*/

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
		// throw new UnsupportedOperationException("Not yet implemented!");
		

		JAXBElement<E19> element = scFactory.createE23(cs);
		StringWriter sw = new StringWriter();
		scMarshaller.marshal(element, sw);
		String xml = sw.toString();
		return xml;
	}

	@Override
	public D4 unmarshalCS(String _xml) throws JAXBException {
		StringReader sr = new StringReader(_xml);
		JAXBElement<D4> element = (JAXBElement<D4>) csUnmarshaller.unmarshal(sr);
		D4 sc = element.getValue();
		return sc;
	}

	@Override
	public E19 unmarshalSC(String _xml) throws JAXBException {
		StringReader sr = new StringReader(_xml);
		JAXBElement<E19> element = (JAXBElement<E19>) scUnmarshaller.unmarshal(sr);
		E19 sc = element.getValue();
		return sc;
	}

}
