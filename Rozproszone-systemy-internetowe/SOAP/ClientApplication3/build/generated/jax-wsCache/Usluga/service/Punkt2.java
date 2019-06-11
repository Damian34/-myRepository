
package service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for punkt2 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="punkt2"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="czytelnik" type="{http://Service/}czytelnik" minOccurs="0"/&gt;
 *         &lt;element name="książka" type="{http://Service/}książka" minOccurs="0"/&gt;
 *         &lt;element name="wyp" type="{http://Service/}wypożyczenie" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "punkt2", propOrder = {
    "czytelnik",
    "ksi\u0105\u017cka",
    "wyp"
})
public class Punkt2 {

    protected Czytelnik czytelnik;
    protected Książka książka;
    protected Wypożyczenie wyp;

    /**
     * Gets the value of the czytelnik property.
     * 
     * @return
     *     possible object is
     *     {@link Czytelnik }
     *     
     */
    public Czytelnik getCzytelnik() {
        return czytelnik;
    }

    /**
     * Sets the value of the czytelnik property.
     * 
     * @param value
     *     allowed object is
     *     {@link Czytelnik }
     *     
     */
    public void setCzytelnik(Czytelnik value) {
        this.czytelnik = value;
    }

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
     * Gets the value of the wyp property.
     * 
     * @return
     *     possible object is
     *     {@link Wypożyczenie }
     *     
     */
    public Wypożyczenie getWyp() {
        return wyp;
    }

    /**
     * Sets the value of the wyp property.
     * 
     * @param value
     *     allowed object is
     *     {@link Wypożyczenie }
     *     
     */
    public void setWyp(Wypożyczenie value) {
        this.wyp = value;
    }

}
