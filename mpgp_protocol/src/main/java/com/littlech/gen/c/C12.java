/*
 * Created by: Kristjan Veskimäe
 */
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.05.12 at 01:01:14 AM EEST 
//


package com.littlech.gen.c;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import com.littlech.gen.d.D4;
import com.littlech.gen.e.E19;


/**
 * <p>Java class for c12 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="c12">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="c13" type="{http://littlech.com/gen/c}c1"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "c12", propOrder = {
    "c13"
})
@XmlSeeAlso({
    E19 .class,
    D4 .class
})
public abstract class C12 {

    @XmlElement(required = true)
    protected C1 c13;

    /**
     * Gets the value of the c13 property.
     * 
     * @return
     *     possible object is
     *     {@link C1 }
     *     
     */
    public C1 getC13() {
        return c13;
    }

    /**
     * Sets the value of the c13 property.
     * 
     * @param value
     *     allowed object is
     *     {@link C1 }
     *     
     */
    public void setC13(C1 value) {
        this.c13 = value;
    }

}