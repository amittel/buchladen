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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "bestellung")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Bestellung.findAll", query = "SELECT b FROM Bestellung b"),
    @NamedQuery(name = "Bestellung.findByBeid", query = "SELECT b FROM Bestellung b WHERE b.beid = :beid"),
    @NamedQuery(name = "Bestellung.findByBLieferDatum", query = "SELECT b FROM Bestellung b WHERE b.bLieferDatum = :bLieferDatum"),
    @NamedQuery(name = "Bestellung.findByBStatus", query = "SELECT b FROM Bestellung b WHERE b.bStatus = :bStatus"),
    @NamedQuery(name = "Bestellung.findByBKommentar", query = "SELECT b FROM Bestellung b WHERE b.bKommentar = :bKommentar"),
    @NamedQuery(name = "Bestellung.findByBAenderungsDatum", query = "SELECT b FROM Bestellung b WHERE b.bAenderungsDatum = :bAenderungsDatum")})
public class Bestellung implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "BEID")
    private Integer beid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "BLieferDatum")
    @Temporal(TemporalType.DATE)
    private Date bLieferDatum;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 14)
    @Column(name = "BStatus")
    private String bStatus;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "BKommentar")
    private String bKommentar;
    @Basic(optional = false)
    // @NotNull -- AUSKOMMENTIEREN WEIL ES EBEN SO IST!
    @Column(name = "BAenderungsDatum")
    @Temporal(TemporalType.TIMESTAMP)
    private Date bAenderungsDatum;
    @JoinColumn(name = "FK_KID", referencedColumnName = "KID")
    @ManyToOne(optional = false)
    private Kunde fkKid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkBeid")
    private Collection<Bestelldetail> bestelldetailCollection;

    public Bestellung() {
    }

    public Bestellung(Integer beid) {
        this.beid = beid;
    }

    public Bestellung(Integer beid, Date bLieferDatum, String bStatus, String bKommentar, Date bAenderungsDatum) {
        this.beid = beid;
        this.bLieferDatum = bLieferDatum;
        this.bStatus = bStatus;
        this.bKommentar = bKommentar;
        this.bAenderungsDatum = bAenderungsDatum;
    }

    public Integer getBeid() {
        return beid;
    }

    public void setBeid(Integer beid) {
        this.beid = beid;
    }

    public Date getBLieferDatum() {
        return bLieferDatum;
    }

    public void setBLieferDatum(Date bLieferDatum) {
        this.bLieferDatum = bLieferDatum;
    }

    public String getBStatus() {
        return bStatus;
    }

    public void setBStatus(String bStatus) {
        this.bStatus = bStatus;
    }

    public String getBKommentar() {
        return bKommentar;
    }

    public void setBKommentar(String bKommentar) {
        this.bKommentar = bKommentar;
    }

    public Date getBAenderungsDatum() {
        return bAenderungsDatum;
    }

    public void setBAenderungsDatum(Date bAenderungsDatum) {
        this.bAenderungsDatum = bAenderungsDatum;
    }

    public Kunde getFkKid() {
        return fkKid;
    }

    public void setFkKid(Kunde fkKid) {
        this.fkKid = fkKid;
    }

    @XmlTransient
    public Collection<Bestelldetail> getBestelldetailCollection() {
        return bestelldetailCollection;
    }

    public void setBestelldetailCollection(Collection<Bestelldetail> bestelldetailCollection) {
        this.bestelldetailCollection = bestelldetailCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (beid != null ? beid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bestellung)) {
            return false;
        }
        Bestellung other = (Bestellung) object;
        if ((this.beid == null && other.beid != null) || (this.beid != null && !this.beid.equals(other.beid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Bestellung[ beid=" + beid + " ]";
    }

}
