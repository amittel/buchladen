/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author vm-dba
 */
@Entity
@Table(name = "buch")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Buch.findAll", query = "SELECT b FROM Buch b"),
    @NamedQuery(name = "Buch.findByBid", query = "SELECT b FROM Buch b WHERE b.bid = :bid"),
    @NamedQuery(name = "Buch.findByBName", query = "SELECT b FROM Buch b WHERE b.bName = :bName"),
    @NamedQuery(name = "Buch.findByBPublikationsjahr", query = "SELECT b FROM Buch b WHERE b.bPublikationsjahr = :bPublikationsjahr"),
    @NamedQuery(name = "Buch.findByBisbn", query = "SELECT b FROM Buch b WHERE b.bisbn = :bisbn"),
    @NamedQuery(name = "Buch.findByBAuflage", query = "SELECT b FROM Buch b WHERE b.bAuflage = :bAuflage"),
    @NamedQuery(name = "Buch.findByBPreis", query = "SELECT b FROM Buch b WHERE b.bPreis = :bPreis")})
public class Buch implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "BID")
    private Integer bid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "BName")
    private String bName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "BPublikationsjahr")
    private int bPublikationsjahr;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 13)
    @Column(name = "BISBN")
    private String bisbn;
    @Basic(optional = false)
    @NotNull
    @Column(name = "BAuflage")
    private int bAuflage;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "BPreis")
    private BigDecimal bPreis;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkBid")
    private Collection<Buchautor> buchautorCollection;
    @JoinColumn(name = "FK_VID", referencedColumnName = "VID")
    @ManyToOne(optional = false)
    private Verlag fkVid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkBid")
    private Collection<Bestelldetail> bestelldetailCollection;

    public Buch() {
    }

    public Buch(Integer bid) {
        this.bid = bid;
    }

    public Buch(Integer bid, String bName, int bPublikationsjahr, String bisbn, int bAuflage, BigDecimal bPreis) {
        this.bid = bid;
        this.bName = bName;
        this.bPublikationsjahr = bPublikationsjahr;
        this.bisbn = bisbn;
        this.bAuflage = bAuflage;
        this.bPreis = bPreis;
    }

    public Integer getBid() {
        return bid;
    }

    public void setBid(Integer bid) {
        this.bid = bid;
    }

    public String getBName() {
        return bName;
    }

    public void setBName(String bName) {
        this.bName = bName;
    }

    public int getBPublikationsjahr() {
        return bPublikationsjahr;
    }

    public void setBPublikationsjahr(int bPublikationsjahr) {
        this.bPublikationsjahr = bPublikationsjahr;
    }

    public String getBisbn() {
        return bisbn;
    }

    public void setBisbn(String bisbn) {
        this.bisbn = bisbn;
    }

    public int getBAuflage() {
        return bAuflage;
    }

    public void setBAuflage(int bAuflage) {
        this.bAuflage = bAuflage;
    }

    public BigDecimal getBPreis() {
        return bPreis;
    }

    public void setBPreis(BigDecimal bPreis) {
        this.bPreis = bPreis;
    }

    @XmlTransient
    public Collection<Buchautor> getBuchautorCollection() {
        return buchautorCollection;
    }

    public void setBuchautorCollection(Collection<Buchautor> buchautorCollection) {
        this.buchautorCollection = buchautorCollection;
    }

    public Verlag getFkVid() {
        return fkVid;
    }

    public void setFkVid(Verlag fkVid) {
        this.fkVid = fkVid;
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
        hash += (bid != null ? bid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Buch)) {
            return false;
        }
        Buch other = (Buch) object;
        if ((this.bid == null && other.bid != null) || (this.bid != null && !this.bid.equals(other.bid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Buch[ bid=" + bid + " ]";
    }
    
}
