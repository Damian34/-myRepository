
package service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for wypożyczenie complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="wypożyczenie"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="dataWypożyczenia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="idCzytelnika" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="idKsiążka" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="idWypożyczenie" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="terminZwrotu" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "wypo\u017cyczenie", propOrder = {
    "dataWypo\u017cyczenia",
    "idCzytelnika",
    "idKsi\u0105\u017cka",
    "idWypo\u017cyczenie",
    "status",
    "terminZwrotu"
})
public class Wypożyczenie {

    protected String dataWypożyczenia;
    protected Integer idCzytelnika;
    protected Integer idKsiążka;
    protected Integer idWypożyczenie;
    protected String status;
    protected String terminZwrotu;

    /**
     * Gets the value of the dataWypożyczenia property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataWypożyczenia() {
        return dataWypożyczenia;
    }

    /**
     * Sets the value of the dataWypożyczenia property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataWypożyczenia(String value) {
        this.dataWypożyczenia = value;
    }

    /**
     * Gets the value of the idCzytelnika property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIdCzytelnika() {
        return idCzytelnika;
    }

    /**
     * Sets the value of the idCzytelnika property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIdCzytelnika(Integer value) {
        this.idCzytelnika = value;
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
     * Gets the value of the idWypożyczenie property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIdWypożyczenie() {
        return idWypożyczenie;
    }

    /**
     * Sets the value of the idWypożyczenie property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIdWypożyczenie(Integer value) {
        this.idWypożyczenie = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Gets the value of the terminZwrotu property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTerminZwrotu() {
        return terminZwrotu;
    }

    /**
     * Sets the value of the terminZwrotu property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTerminZwrotu(String value) {
        this.terminZwrotu = value;
    }

}
