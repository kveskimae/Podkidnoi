/*
 * Created by: Kristjan Veskimäe
 */
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.05.12 at 01:01:14 AM EEST 
//


package com.littlech.gen.f;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for f1.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="f1">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="f2"/>
 *     &lt;enumeration value="f3"/>
 *     &lt;enumeration value="f4"/>
 *     &lt;enumeration value="f5"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "f1")
@XmlEnum
public enum F1 {

    @XmlEnumValue("f2")
    F_2("f2"),
    @XmlEnumValue("f3")
    F_3("f3"),
    @XmlEnumValue("f4")
    F_4("f4"),
    @XmlEnumValue("f5")
    F_5("f5");
    private final String value;

    F1(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static F1 fromValue(String v) {
        for (F1 c: F1 .values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}