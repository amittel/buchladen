/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vm-dba
 */
@Entity
@Table(name = "bestelldetail")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Bestelldetail.findAll", query = "SELECT b FROM Bestelldetail b"),
    @NamedQuery(name = "Bestelldetail.findByBdid", query = "SELECT b FROM Bestelldetail b WHERE b.bdid = :bdid"),
    @NamedQuery(name = "Bestelldetail.findByBDMenge", query = "SELECT b FROM Bestelldetail b WHERE b.bDMenge = :bDMenge"),
    @NamedQuery(name = "Bestelldetail.findByBDDatum", query = "SELECT b FROM Bestelldetail b WHERE b.bDDatum = :bDDatum")})
public class Bestelldetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "BDID")
    private Integer bdid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "BDMenge")
    private short bDMenge;
    @Basic(optional = false)
    // @NotNull s-- AUSKOMMENTIEREN WEIL ES EBEN SO IST!
    @Column(name = "BDDatum")
    @Temporal(TemporalType.TIMESTAMP)
    private Date bDDatum;
    @JoinColumn(name = "FK_BEID", referencedColumnName = "BEID")
    @ManyToOne(optional = false)
    private Bestellung fkBeid;
    @JoinColumn(name = "FK_BID", referencedColumnName = "BID")
    @ManyToOne(optional = false)
    private Buch fkBid;

    public Bestelldetail() {
    }

    public Bestelldetail(Integer bdid) {
        this.bdid = bdid;
    }

    public Bestelldetail(Integer bdid, short bDMenge, Date bDDatum) {
        this.bdid = bdid;
        this.bDMenge = bDMenge;
        this.bDDatum = bDDatum;
    }

    public Integer getBdid() {
        return bdid;
    }

    public void setBdid(Integer bdid) {
        this.bdid = bdid;
    }

    public short getBDMenge() {
        return bDMenge;
    }

    public void setBDMenge(short bDMenge) {
        this.bDMenge = bDMenge;
    }

    public Date getBDDatum() {
        return bDDatum;
    }

    public void setBDDatum(Date bDDatum) {
        this.bDDatum = bDDatum;
    }

    public Bestellung getFkBeid() {
        return fkBeid;
    }

    public void setFkBeid(Bestellung fkBeid) {
        this.fkBeid = fkBeid;
    }

    public Buch getFkBid() {
        return fkBid;
    }

    public void setFkBid(Buch fkBid) {
        this.fkBid = fkBid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bdid != null ? bdid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bestelldetail)) {
            return false;
        }
        Bestelldetail other = (Bestelldetail) object;
        if ((this.bdid == null && other.bdid != null) || (this.bdid != null && !this.bdid.equals(other.bdid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Bestelldetail[ bdid=" + bdid + " ]";
    }
    
}
