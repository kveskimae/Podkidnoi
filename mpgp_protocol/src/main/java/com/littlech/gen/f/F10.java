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
 * <p>Java class for f10 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="f10">
 *   &lt;complexContent>
 *     &lt;extension base="{http://littlech.com/gen/f}f9">
 *       &lt;all>
 *         &lt;element name="f11" type="{http://littlech.com/gen/f}f6"/>
 *       &lt;/all>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "f10", propOrder = {
    "f11"
})
public class F10
    extends F9
{

    @XmlElement(required = true)
    protected F6 f11;

    /**
     * Gets the value of the f11 property.
     * 
     * @return
     *     possible object is
     *     {@link F6 }
     *     
     */
    public F6 getF11() {
        return f11;
    }

    /**
     * Sets the value of the f11 property.
     * 
     * @param value
     *     allowed object is
     *     {@link F6 }
     *     
     */
    public void setF11(F6 value) {
        this.f11 = value;
    }

}