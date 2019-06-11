/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dami
 */
@Entity
@Table(name = "CZYTELNIK")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Czytelnik.findAll", query = "SELECT c FROM Czytelnik c"),
    @NamedQuery(name = "Czytelnik.findByIdCzytelnik", query = "SELECT c FROM Czytelnik c WHERE c.idCzytelnik = :idCzytelnik"),
    @NamedQuery(name = "Czytelnik.findByImie", query = "SELECT c FROM Czytelnik c WHERE c.imie = :imie"),
    @NamedQuery(name = "Czytelnik.findByNazwisko", query = "SELECT c FROM Czytelnik c WHERE c.nazwisko = :nazwisko")})
public class Czytelnik implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_CZYTELNIK")
    private Integer idCzytelnik;
    @Size(max = 40)
    @Column(name = "IMIE")
    private String imie;
    @Size(max = 40)
    @Column(name = "NAZWISKO")
    private String nazwisko;

    public Czytelnik() {
    }

    public Czytelnik(Integer idCzytelnik) {
        this.idCzytelnik = idCzytelnik;
    }

    public Integer getIdCzytelnik() {
        return idCzytelnik;
    }

    public void setIdCzytelnik(Integer idCzytelnik) {
        this.idCzytelnik = idCzytelnik;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCzytelnik != null ? idCzytelnik.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Czytelnik)) {
            return false;
        }
        Czytelnik other = (Czytelnik) object;
        if ((this.idCzytelnik == null && other.idCzytelnik != null) || (this.idCzytelnik != null && !this.idCzytelnik.equals(other.idCzytelnik))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Service.Czytelnik[ idCzytelnik=" + idCzytelnik + " ]";
    }
    
}
