/*
 * Created by: Kristjan Veskimäe
 */
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.05.12 at 01:01:14 AM EEST 
//


package com.littlech.gen.a;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for a1.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="a1">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="a2"/>
 *     &lt;enumeration value="a3"/>
 *     &lt;enumeration value="a4"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "a1")
@XmlEnum
public enum A1 {

    @XmlEnumValue("a2")
    A_2("a2"),
    @XmlEnumValue("a3")
    A_3("a3"),
    @XmlEnumValue("a4")
    A_4("a4");
    private final String value;

    A1(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static A1 fromValue(String v) {
        for (A1 c: A1 .values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
