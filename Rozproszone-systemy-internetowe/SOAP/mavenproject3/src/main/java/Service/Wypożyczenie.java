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
@Table(name = "WYPO\u017bYCZENIE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Wypo\u017cyczenie.findAll", query = "SELECT w FROM Wypo\u017cyczenie w"),
    @NamedQuery(name = "Wypo\u017cyczenie.findByIdWypo\u017cyczenie", query = "SELECT w FROM Wypo\u017cyczenie w WHERE w.idWypo\u017cyczenie = :idWypo\u017cyczenie"),
    @NamedQuery(name = "Wypo\u017cyczenie.findByIdCzytelnika", query = "SELECT w FROM Wypo\u017cyczenie w WHERE w.idCzytelnika = :idCzytelnika"),
    @NamedQuery(name = "Wypo\u017cyczenie.findByIdKsi\u0105\u017cka", query = "SELECT w FROM Wypo\u017cyczenie w WHERE w.idKsi\u0105\u017cka = :idKsi\u0105\u017cka"),
    @NamedQuery(name = "Wypo\u017cyczenie.findByStatus", query = "SELECT w FROM Wypo\u017cyczenie w WHERE w.status = :status"),
    @NamedQuery(name = "Wypo\u017cyczenie.findByDataWypo\u017cyczenia", query = "SELECT w FROM Wypo\u017cyczenie w WHERE w.dataWypo\u017cyczenia = :dataWypo\u017cyczenia"),
    @NamedQuery(name = "Wypo\u017cyczenie.findByTerminZwrotu", query = "SELECT w FROM Wypo\u017cyczenie w WHERE w.terminZwrotu = :terminZwrotu")})
public class Wypożyczenie implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_WYPO\u017bYCZENIE")
    private Integer idWypożyczenie;
    @Column(name = "ID_CZYTELNIKA")
    private Integer idCzytelnika;
    @Column(name = "ID_KSI\u0104\u017bKA")
    private Integer idKsiążka;
    @Size(max = 40)
    @Column(name = "STATUS")
    private String status;
    @Size(max = 40)
    @Column(name = "DATA_WYPO\u017bYCZENIA")
    private String dataWypożyczenia;
    @Size(max = 40)
    @Column(name = "TERMIN_ZWROTU")
    private String terminZwrotu;

    public Wypożyczenie() {
    }

    public Wypożyczenie(Integer idWypożyczenie) {
        this.idWypożyczenie = idWypożyczenie;
    }

    public Integer getIdWypożyczenie() {
        return idWypożyczenie;
    }

    public void setIdWypożyczenie(Integer idWypożyczenie) {
        this.idWypożyczenie = idWypożyczenie;
    }

    public Integer getIdCzytelnika() {
        return idCzytelnika;
    }

    public void setIdCzytelnika(Integer idCzytelnika) {
        this.idCzytelnika = idCzytelnika;
    }

    public Integer getIdKsiążka() {
        return idKsiążka;
    }

    public void setIdKsiążka(Integer idKsiążka) {
        this.idKsiążka = idKsiążka;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDataWypożyczenia() {
        return dataWypożyczenia;
    }

    public void setDataWypożyczenia(String dataWypożyczenia) {
        this.dataWypożyczenia = dataWypożyczenia;
    }

    public String getTerminZwrotu() {
        return terminZwrotu;
    }

    public void setTerminZwrotu(String terminZwrotu) {
        this.terminZwrotu = terminZwrotu;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idWypożyczenie != null ? idWypożyczenie.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Wypożyczenie)) {
            return false;
        }
        Wypożyczenie other = (Wypożyczenie) object;
        if ((this.idWypożyczenie == null && other.idWypożyczenie != null) || (this.idWypożyczenie != null && !this.idWypożyczenie.equals(other.idWypożyczenie))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Service.Wypo\u017cyczenie[ idWypo\u017cyczenie=" + idWypożyczenie + " ]";
    }
    
}
