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
@Table(name = "verlag_adresse")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VerlagAdresse.findAll", query = "SELECT v FROM VerlagAdresse v"),
    @NamedQuery(name = "VerlagAdresse.findByVadrid", query = "SELECT v FROM VerlagAdresse v WHERE v.vadrid = :vadrid"),
    @NamedQuery(name = "VerlagAdresse.findByVAStrasse", query = "SELECT v FROM VerlagAdresse v WHERE v.vAStrasse = :vAStrasse"),
    @NamedQuery(name = "VerlagAdresse.findByVAOrt", query = "SELECT v FROM VerlagAdresse v WHERE v.vAOrt = :vAOrt"),
    @NamedQuery(name = "VerlagAdresse.findByVaplz", query = "SELECT v FROM VerlagAdresse v WHERE v.vaplz = :vaplz"),
    @NamedQuery(name = "VerlagAdresse.findByVABundesland", query = "SELECT v FROM VerlagAdresse v WHERE v.vABundesland = :vABundesland"),
    @NamedQuery(name = "VerlagAdresse.findByVALand", query = "SELECT v FROM VerlagAdresse v WHERE v.vALand = :vALand"),
    @NamedQuery(name = "VerlagAdresse.findByVADatum", query = "SELECT v FROM VerlagAdresse v WHERE v.vADatum = :vADatum")})
public class VerlagAdresse implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "VADRID")
    private Integer vadrid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "VAStrasse")
    private String vAStrasse;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "VAOrt")
    private String vAOrt;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "VAPLZ")
    private String vaplz;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 17)
    @Column(name = "VABundesland")
    private String vABundesland;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "VALand")
    private String vALand;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VADatum")
    @Temporal(TemporalType.TIMESTAMP)
    private Date vADatum;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkAdrid")
    private Collection<Verlag> verlagCollection;

    public VerlagAdresse() {
    }

    public VerlagAdresse(Integer vadrid) {
        this.vadrid = vadrid;
    }

    public VerlagAdresse(Integer vadrid, String vAStrasse, String vAOrt, String vaplz, String vABundesland, String vALand, Date vADatum) {
        this.vadrid = vadrid;
        this.vAStrasse = vAStrasse;
        this.vAOrt = vAOrt;
        this.vaplz = vaplz;
        this.vABundesland = vABundesland;
        this.vALand = vALand;
        this.vADatum = vADatum;
    }

    public Integer getVadrid() {
        return vadrid;
    }

    public void setVadrid(Integer vadrid) {
        this.vadrid = vadrid;
    }

    public String getVAStrasse() {
        return vAStrasse;
    }

    public void setVAStrasse(String vAStrasse) {
        this.vAStrasse = vAStrasse;
    }

    public String getVAOrt() {
        return vAOrt;
    }

    public void setVAOrt(String vAOrt) {
        this.vAOrt = vAOrt;
    }

    public String getVaplz() {
        return vaplz;
    }

    public void setVaplz(String vaplz) {
        this.vaplz = vaplz;
    }

    public String getVABundesland() {
        return vABundesland;
    }

    public void setVABundesland(String vABundesland) {
        this.vABundesland = vABundesland;
    }

    public String getVALand() {
        return vALand;
    }

    public void setVALand(String vALand) {
        this.vALand = vALand;
    }

    public Date getVADatum() {
        return vADatum;
    }

    public void setVADatum(Date vADatum) {
        this.vADatum = vADatum;
    }

    @XmlTransient
    public Collection<Verlag> getVerlagCollection() {
        return verlagCollection;
    }

    public void setVerlagCollection(Collection<Verlag> verlagCollection) {
        this.verlagCollection = verlagCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (vadrid != null ? vadrid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VerlagAdresse)) {
            return false;
        }
        VerlagAdresse other = (VerlagAdresse) object;
        if ((this.vadrid == null && other.vadrid != null) || (this.vadrid != null && !this.vadrid.equals(other.vadrid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.VerlagAdresse[ vadrid=" + vadrid + " ]";
    }
    
}
