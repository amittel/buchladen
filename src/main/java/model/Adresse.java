/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author vm-dba
 */
@Entity
@Table(name = "adresse")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Adresse.findAll", query = "SELECT a FROM Adresse a"),
    @NamedQuery(name = "Adresse.findByAdrid", query = "SELECT a FROM Adresse a WHERE a.adrid = :adrid"),
    @NamedQuery(name = "Adresse.findByAStrasse", query = "SELECT a FROM Adresse a WHERE a.aStrasse = :aStrasse"),
    @NamedQuery(name = "Adresse.findByAOrt", query = "SELECT a FROM Adresse a WHERE a.aOrt = :aOrt"),
    @NamedQuery(name = "Adresse.findByAplz", query = "SELECT a FROM Adresse a WHERE a.aplz = :aplz"),
    @NamedQuery(name = "Adresse.findByABundesland", query = "SELECT a FROM Adresse a WHERE a.aBundesland = :aBundesland"),
    @NamedQuery(name = "Adresse.findByALand", query = "SELECT a FROM Adresse a WHERE a.aLand = :aLand"),
    @NamedQuery(name = "Adresse.findByADatum", query = "SELECT a FROM Adresse a WHERE a.aDatum = :aDatum")})
public class Adresse implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ADRID")
    private Integer adrid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "AStrasse")
    private String aStrasse;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "AOrt")
    private String aOrt;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "APLZ")
    private String aplz;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 17)
    @Column(name = "ABundesland")
    private String aBundesland;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "ALand")
    private String aLand;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ADatum")
    @Temporal(TemporalType.TIMESTAMP)
    private Date aDatum;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkAid")
    private Collection<Kunde> kundeCollection;

    public Adresse() {
    }

    public Adresse(Integer adrid) {
        this.adrid = adrid;
    }

    public Adresse(Integer adrid, String aStrasse, String aOrt, String aplz, String aBundesland, String aLand, Date aDatum) {
        this.adrid = adrid;
        this.aStrasse = aStrasse;
        this.aOrt = aOrt;
        this.aplz = aplz;
        this.aBundesland = aBundesland;
        this.aLand = aLand;
        this.aDatum = aDatum;
    }

    public Integer getAdrid() {
        return adrid;
    }

    public void setAdrid(Integer adrid) {
        this.adrid = adrid;
    }

    public String getAStrasse() {
        return aStrasse;
    }

    public void setAStrasse(String aStrasse) {
        this.aStrasse = aStrasse;
    }

    public String getAOrt() {
        return aOrt;
    }

    public void setAOrt(String aOrt) {
        this.aOrt = aOrt;
    }

    public String getAplz() {
        return aplz;
    }

    public void setAplz(String aplz) {
        this.aplz = aplz;
    }

    public String getABundesland() {
        return aBundesland;
    }

    public void setABundesland(String aBundesland) {
        this.aBundesland = aBundesland;
    }

    public String getALand() {
        return aLand;
    }

    public void setALand(String aLand) {
        this.aLand = aLand;
    }

    public Date getADatum() {
        return aDatum;
    }

    public void setADatum(Date aDatum) {
        this.aDatum = aDatum;
    }

    @XmlTransient
    public Collection<Kunde> getKundeCollection() {
        return kundeCollection;
    }

    public void setKundeCollection(Collection<Kunde> kundeCollection) {
        this.kundeCollection = kundeCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (adrid != null ? adrid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Adresse)) {
            return false;
        }
        Adresse other = (Adresse) object;
        if ((this.adrid == null && other.adrid != null) || (this.adrid != null && !this.adrid.equals(other.adrid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Adresse[ adrid=" + adrid + " ]";
    }
    
}
