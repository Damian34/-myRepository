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
@Table(name = "KSI\u0104\u017bKA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ksi\u0105\u017cka.findAll", query = "SELECT k FROM Ksi\u0105\u017cka k"),
    @NamedQuery(name = "Ksi\u0105\u017cka.findByIdKsi\u0105\u017cka", query = "SELECT k FROM Ksi\u0105\u017cka k WHERE k.idKsi\u0105\u017cka = :idKsi\u0105\u017cka"),
    @NamedQuery(name = "Ksi\u0105\u017cka.findByTytu\u0142", query = "SELECT k FROM Ksi\u0105\u017cka k WHERE k.tytu\u0142 = :tytu\u0142"),
    @NamedQuery(name = "Ksi\u0105\u017cka.findByAutor", query = "SELECT k FROM Ksi\u0105\u017cka k WHERE k.autor = :autor")})
public class Książka implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_KSI\u0104\u017bKA")
    private Integer idKsiążka;
    @Size(max = 40)
    @Column(name = "TYTU\u0141")
    private String tytuł;
    @Size(max = 40)
    @Column(name = "AUTOR")
    private String autor;

    public Książka() {
    }

    public Książka(Integer idKsiążka) {
        this.idKsiążka = idKsiążka;
    }

    public Integer getIdKsiążka() {
        return idKsiążka;
    }

    public void setIdKsiążka(Integer idKsiążka) {
        this.idKsiążka = idKsiążka;
    }

    public String getTytuł() {
        return tytuł;
    }

    public void setTytuł(String tytuł) {
        this.tytuł = tytuł;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idKsiążka != null ? idKsiążka.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Książka)) {
            return false;
        }
        Książka other = (Książka) object;
        if ((this.idKsiążka == null && other.idKsiążka != null) || (this.idKsiążka != null && !this.idKsiążka.equals(other.idKsiążka))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Service.Ksi\u0105\u017cka[ idKsi\u0105\u017cka=" + idKsiążka + " ]";
    }
    
}
