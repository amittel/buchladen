/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author vm-dba
 */
@Entity
@Table(name = "kategorie")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Kategorie.findAll", query = "SELECT k FROM Kategorie k"),
    @NamedQuery(name = "Kategorie.findByKatid", query = "SELECT k FROM Kategorie k WHERE k.katid = :katid"),
    @NamedQuery(name = "Kategorie.findByKKategorie", query = "SELECT k FROM Kategorie k WHERE k.kKategorie = :kKategorie")})
public class Kategorie implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "KATID")
    private Integer katid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "KKategorie")
    private String kKategorie;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkKatid")
    private Collection<Buch> buchCollection;

    public Kategorie() {
    }

    public Kategorie(Integer katid) {
        this.katid = katid;
    }

    public Kategorie(Integer katid, String kKategorie) {
        this.katid = katid;
        this.kKategorie = kKategorie;
    }

    public Integer getKatid() {
        return katid;
    }

    public void setKatid(Integer katid) {
        this.katid = katid;
    }

    public String getKKategorie() {
        return kKategorie;
    }

    public void setKKategorie(String kKategorie) {
        this.kKategorie = kKategorie;
    }

    @XmlTransient
    public Collection<Buch> getBuchCollection() {
        return buchCollection;
    }

    public void setBuchCollection(Collection<Buch> buchCollection) {
        this.buchCollection = buchCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (katid != null ? katid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Kategorie)) {
            return false;
        }
        Kategorie other = (Kategorie) object;
        if ((this.katid == null && other.katid != null) || (this.katid != null && !this.katid.equals(other.katid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Kategorie[ katid=" + katid + " ]";
    }
    
}
