
package service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for punkt1 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="punkt1"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="czytelnik" type="{http://Service/}czytelnik" minOccurs="0"/&gt;
 *         &lt;element name="ilość" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "punkt1", propOrder = {
    "czytelnik",
    "ilo\u015b\u0107"
})
public class Punkt1 {

    protected Czytelnik czytelnik;
    protected int ilość;

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
     * Gets the value of the ilość property.
     * 
     */
    public int getIlość() {
        return ilość;
    }

    /**
     * Sets the value of the ilość property.
     * 
     */
    public void setIlość(int value) {
        this.ilość = value;
    }

}
