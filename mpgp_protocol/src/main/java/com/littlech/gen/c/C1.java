/*
 * Created by: Kristjan Veskimäe
 */
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.05.12 at 01:01:14 AM EEST 
//


package com.littlech.gen.c;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for c1.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="c1">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="c2"/>
 *     &lt;enumeration value="c3"/>
 *     &lt;enumeration value="c4"/>
 *     &lt;enumeration value="c5"/>
 *     &lt;enumeration value="c6"/>
 *     &lt;enumeration value="c7"/>
 *     &lt;enumeration value="c8"/>
 *     &lt;enumeration value="c9"/>
 *     &lt;enumeration value="c10"/>
 *     &lt;enumeration value="c11"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "c1")
@XmlEnum
public enum C1 {

    @XmlEnumValue("c2")
    C_2("c2"),
    @XmlEnumValue("c3")
    C_3("c3"),
    @XmlEnumValue("c4")
    C_4("c4"),
    @XmlEnumValue("c5")
    C_5("c5"),
    @XmlEnumValue("c6")
    C_6("c6"),
    @XmlEnumValue("c7")
    C_7("c7"),
    @XmlEnumValue("c8")
    C_8("c8"),
    @XmlEnumValue("c9")
    C_9("c9"),
    @XmlEnumValue("c10")
    C_10("c10"),
    @XmlEnumValue("c11")
    C_11("c11");
    private final String value;

    C1(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static C1 fromValue(String v) {
        for (C1 c: C1 .values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
