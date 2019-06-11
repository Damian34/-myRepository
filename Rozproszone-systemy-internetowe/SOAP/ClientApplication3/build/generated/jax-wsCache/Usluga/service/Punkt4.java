
package service;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for punkt4 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="punkt4"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="text" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="lk" type="{http://Service/}książka" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="lc" type="{http://Service/}czytelnik" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="lw" type="{http://Service/}wypożyczenie" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "punkt4", propOrder = {
    "text",
    "lk",
    "lc",
    "lw"
})
public class Punkt4 {

    protected String text;
    @XmlElement(nillable = true)
    protected List<Książka> lk;
    @XmlElement(nillable = true)
    protected List<Czytelnik> lc;
    @XmlElement(nillable = true)
    protected List<Wypożyczenie> lw;

    /**
     * Gets the value of the text property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the value of the text property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setText(String value) {
        this.text = value;
    }

    /**
     * Gets the value of the lk property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the lk property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLk().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Książka }
     * 
     * 
     */
    public List<Książka> getLk() {
        if (lk == null) {
            lk = new ArrayList<Książka>();
        }
        return this.lk;
    }

    /**
     * Gets the value of the lc property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the lc property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLc().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Czytelnik }
     * 
     * 
     */
    public List<Czytelnik> getLc() {
        if (lc == null) {
            lc = new ArrayList<Czytelnik>();
        }
        return this.lc;
    }

    /**
     * Gets the value of the lw property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the lw property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLw().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Wypożyczenie }
     * 
     * 
     */
    public List<Wypożyczenie> getLw() {
        if (lw == null) {
            lw = new ArrayList<Wypożyczenie>();
        }
        return this.lw;
    }

}
