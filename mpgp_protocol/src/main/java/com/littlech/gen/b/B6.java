/*
 * Created by: Kristjan Veskimäe
 */
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.05.12 at 01:01:14 AM EEST 
//


package com.littlech.gen.b;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for b6.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="b6">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="b7"/>
 *     &lt;enumeration value="b8"/>
 *     &lt;enumeration value="b9"/>
 *     &lt;enumeration value="b10"/>
 *     &lt;enumeration value="b11"/>
 *     &lt;enumeration value="b12"/>
 *     &lt;enumeration value="b13"/>
 *     &lt;enumeration value="b14"/>
 *     &lt;enumeration value="b15"/>
 *     &lt;enumeration value="b16"/>
 *     &lt;enumeration value="b17"/>
 *     &lt;enumeration value="b18"/>
 *     &lt;enumeration value="b19"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "b6")
@XmlEnum
public enum B6 {

    @XmlEnumValue("b7")
    B_7("b7"),
    @XmlEnumValue("b8")
    B_8("b8"),
    @XmlEnumValue("b9")
    B_9("b9"),
    @XmlEnumValue("b10")
    B_10("b10"),
    @XmlEnumValue("b11")
    B_11("b11"),
    @XmlEnumValue("b12")
    B_12("b12"),
    @XmlEnumValue("b13")
    B_13("b13"),
    @XmlEnumValue("b14")
    B_14("b14"),
    @XmlEnumValue("b15")
    B_15("b15"),
    @XmlEnumValue("b16")
    B_16("b16"),
    @XmlEnumValue("b17")
    B_17("b17"),
    @XmlEnumValue("b18")
    B_18("b18"),
    @XmlEnumValue("b19")
    B_19("b19");
    private final String value;

    B6(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static B6 fromValue(String v) {
        for (B6 c: B6 .values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
