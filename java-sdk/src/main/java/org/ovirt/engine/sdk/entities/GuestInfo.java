//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-b10 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.11.11 at 03:29:58 PM IST 
//


package org.ovirt.engine.sdk.entities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GuestInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GuestInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}ips" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GuestInfo", propOrder = {
    "ips"
})
public class GuestInfo {

    protected IPs ips;

    /**
     * Gets the value of the ips property.
     * 
     * @return
     *     possible object is
     *     {@link IPs }
     *     
     */
    public IPs getIps() {
        return ips;
    }

    /**
     * Sets the value of the ips property.
     * 
     * @param value
     *     allowed object is
     *     {@link IPs }
     *     
     */
    public void setIps(IPs value) {
        this.ips = value;
    }

    public boolean isSetIps() {
        return (this.ips!= null);
    }

}