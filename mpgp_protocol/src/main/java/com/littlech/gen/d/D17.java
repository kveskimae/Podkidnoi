/*
 * Created by: Kristjan Veskimäe
 */
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.05.12 at 01:01:14 AM EEST 
//


package com.littlech.gen.d;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.littlech.gen.f.F1;


/**
 * <p>Java class for d17 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="d17">
 *   &lt;complexContent>
 *     &lt;extension base="{http://littlech.com/gen/d}d7">
 *       &lt;all>
 *         &lt;element name="d18" type="{http://littlech.com/gen/f}f1"/>
 *       &lt;/all>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "d17", propOrder = {
    "d18"
})
public class D17
    extends D7
{

    @XmlElement(required = true)
    protected F1 d18;

    /**
     * Gets the value of the d18 property.
     * 
     * @return
     *     possible object is
     *     {@link F1 }
     *     
     */
    public F1 getD18() {
        return d18;
    }

    /**
     * Sets the value of the d18 property.
     * 
     * @param value
     *     allowed object is
     *     {@link F1 }
     *     
     */
    public void setD18(F1 value) {
        this.d18 = value;
    }

}
