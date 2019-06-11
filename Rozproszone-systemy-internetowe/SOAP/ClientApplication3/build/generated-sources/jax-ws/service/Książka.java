
package service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for książka complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="książka"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="autor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="idKsiążka" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="tytuł" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ksi\u0105\u017cka", propOrder = {
    "autor",
    "idKsi\u0105\u017cka",
    "tytu\u0142"
})
public class Książka {

    protected String autor;
    protected Integer idKsiążka;
    protected String tytuł;

    /**
     * Gets the value of the autor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAutor() {
        return autor;
    }

    /**
     * Sets the value of the autor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAutor(String value) {
        this.autor = value;
    }

    /**
     * Gets the value of the idKsiążka property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIdKsiążka() {
        return idKsiążka;
    }

    /**
     * Sets the value of the idKsiążka property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIdKsiążka(Integer value) {
        this.idKsiążka = value;
    }

    /**
     * Gets the value of the tytuł property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTytuł() {
        return tytuł;
    }

    /**
     * Sets the value of the tytuł property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTytuł(String value) {
        this.tytuł = value;
    }

}
