/*
 * Created by: Kristjan Veskimäe
 */
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.05.12 at 01:01:14 AM EEST 
//


package com.littlech.gen.a;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for a13 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="a13">
 *   &lt;complexContent>
 *     &lt;extension base="{http://littlech.com/gen/a}a12">
 *       &lt;sequence>
 *         &lt;element name="a14" type="{http://littlech.com/gen/a}a5" maxOccurs="2" minOccurs="0"/>
 *         &lt;element name="a15" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "a13", propOrder = {
    "a14",
    "a15"
})
public class A13
    extends A12
{

    protected List<A5> a14;
    protected Integer a15;

    /**
     * Gets the value of the a14 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the a14 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getA14().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link A5 }
     * 
     * 
     */
    public List<A5> getA14() {
        if (a14 == null) {
            a14 = new ArrayList<A5>();
        }
        return this.a14;
    }

    /**
     * Gets the value of the a15 property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getA15() {
        return a15;
    }

    /**
     * Sets the value of the a15 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setA15(Integer value) {
        this.a15 = value;
    }

}
