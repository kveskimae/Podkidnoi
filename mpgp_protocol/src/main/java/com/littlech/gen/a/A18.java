/*
 * Created by: Kristjan Veskimäe
 */
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.05.12 at 01:01:14 AM EEST 
//


package com.littlech.gen.a;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.littlech.gen.b.B26;
import com.littlech.gen.b.B28;


/**
 * <p>Java class for a18 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="a18">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="a19" type="{http://littlech.com/gen/a}a5"/>
 *         &lt;element name="a20" type="{http://littlech.com/gen/b}b28"/>
 *         &lt;element name="a21" type="{http://littlech.com/gen/b}b26"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "a18", propOrder = {

})
public class A18 {

    @XmlElement(required = true)
    protected A5 a19;
    @XmlElement(required = true, nillable = true)
    protected B28 a20;
    @XmlElement(required = true, nillable = true)
    protected B26 a21;

    /**
     * Gets the value of the a19 property.
     * 
     * @return
     *     possible object is
     *     {@link A5 }
     *     
     */
    public A5 getA19() {
        return a19;
    }

    /**
     * Sets the value of the a19 property.
     * 
     * @param value
     *     allowed object is
     *     {@link A5 }
     *     
     */
    public void setA19(A5 value) {
        this.a19 = value;
    }

    /**
     * Gets the value of the a20 property.
     * 
     * @return
     *     possible object is
     *     {@link B28 }
     *     
     */
    public B28 getA20() {
        return a20;
    }

    /**
     * Sets the value of the a20 property.
     * 
     * @param value
     *     allowed object is
     *     {@link B28 }
     *     
     */
    public void setA20(B28 value) {
        this.a20 = value;
    }

    /**
     * Gets the value of the a21 property.
     * 
     * @return
     *     possible object is
     *     {@link B26 }
     *     
     */
    public B26 getA21() {
        return a21;
    }

    /**
     * Sets the value of the a21 property.
     * 
     * @param value
     *     allowed object is
     *     {@link B26 }
     *     
     */
    public void setA21(B26 value) {
        this.a21 = value;
    }

}