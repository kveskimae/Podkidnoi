/*
 * Created by: Kristjan Veskimäe
 */
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.05.12 at 01:01:14 AM EEST 
//


package com.littlech.gen.f;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for f6 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="f6">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="f7" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="f8" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "f6", propOrder = {

})
// user
public class F6 {

    @XmlElement(required = true)
    protected String f7; // name 
    @XmlElement(required = true)
    protected String f8; // avatar

    /**
     * Gets the value of the f7 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getF7() {
        return f7;
    }

    /**
     * Sets the value of the f7 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setF7(String value) {
        this.f7 = value;
    }

    /**
     * Gets the value of the f8 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getF8() {
        return f8;
    }

    /**
     * Sets the value of the f8 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setF8(String value) {
        this.f8 = value;
    }

}
