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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for f17 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="f17">
 *   &lt;complexContent>
 *     &lt;extension base="{http://littlech.com/gen/f}f14">
 *       &lt;all>
 *         &lt;element name="f18" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/all>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "f17", propOrder = {
    "f18"
})
public class F17
    extends F14
{

    protected int f18;

    /**
     * Gets the value of the f18 property.
     * 
     */
    public int getF18() {
        return f18;
    }

    /**
     * Sets the value of the f18 property.
     * 
     */
    public void setF18(int value) {
        this.f18 = value;
    }

}