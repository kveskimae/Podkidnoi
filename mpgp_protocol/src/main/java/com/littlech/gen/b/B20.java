/*
 * Created by: Kristjan Veskimäe
 */
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.05.12 at 01:01:14 AM EEST 
//


package com.littlech.gen.b;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for b20 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="b20">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="b21" type="{http://littlech.com/gen/b}b1"/>
 *         &lt;element name="b22" type="{http://littlech.com/gen/b}b6"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "b20", propOrder = {

})
public class B20 {

    @XmlElement(required = true)
    protected B1 b21;
    @XmlElement(required = true)
    protected B6 b22;
    
    @Override
    public boolean equals(Object arg0) {
    	if (arg0 == this) {
    		return true;
    	}
    	if (arg0 != null && arg0 instanceof B20) {
    		B20 comp = (B20)arg0;
    		if (comp.getB21().equals(this.getB21()) && comp.getB22().equals(this.getB22())) {
    			return true;
    		}
    	}
    	return false;
    }

    /**
     * Gets the value of the b21 property.
     * 
     * @return
     *     possible object is
     *     {@link B1 }
     *     
     */
    public B1 getB21() {
        return b21;
    }

    /**
     * Sets the value of the b21 property.
     * 
     * @param value
     *     allowed object is
     *     {@link B1 }
     *     
     */
    public void setB21(B1 value) {
        this.b21 = value;
    }

    /**
     * Gets the value of the b22 property.
     * 
     * @return
     *     possible object is
     *     {@link B6 }
     *     
     */
    public B6 getB22() {
        return b22;
    }

    /**
     * Sets the value of the b22 property.
     * 
     * @param value
     *     allowed object is
     *     {@link B6 }
     *     
     */
    public void setB22(B6 value) {
        this.b22 = value;
    }

}
