
package service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for punkt3 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="punkt3"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="książka" type="{http://Service/}książka" minOccurs="0"/&gt;
 *         &lt;element name="wypożyczenie" type="{http://Service/}wypożyczenie" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "punkt3", propOrder = {
    "ksi\u0105\u017cka",
    "wypo\u017cyczenie"
})
public class Punkt3 {

    protected Książka książka;
    protected Wypożyczenie wypożyczenie;

    /**
     * Gets the value of the książka property.
     * 
     * @return
     *     possible object is
     *     {@link Książka }
     *     
     */
    public Książka getKsiążka() {
        return książka;
    }

    /**
     * Sets the value of the książka property.
     * 
     * @param value
     *     allowed object is
     *     {@link Książka }
     *     
     */
    public void setKsiążka(Książka value) {
        this.książka = value;
    }

    /**
     * Gets the value of the wypożyczenie property.
     * 
     * @return
     *     possible object is
     *     {@link Wypożyczenie }
     *     
     */
    public Wypożyczenie getWypożyczenie() {
        return wypożyczenie;
    }

    /**
     * Sets the value of the wypożyczenie property.
     * 
     * @param value
     *     allowed object is
     *     {@link Wypożyczenie }
     *     
     */
    public void setWypożyczenie(Wypożyczenie value) {
        this.wypożyczenie = value;
    }

}
