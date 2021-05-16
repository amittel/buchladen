/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vm-dba
 */
@Entity
@Table(name = "buchautor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Buchautor.findAll", query = "SELECT b FROM Buchautor b"),
    @NamedQuery(name = "Buchautor.findByBaid", query = "SELECT b FROM Buchautor b WHERE b.baid = :baid")})
public class Buchautor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "BAID")
    private Integer baid;
    @JoinColumn(name = "FK_AID", referencedColumnName = "AID")
    @ManyToOne(optional = false)
    private Autor fkAid;
    @JoinColumn(name = "FK_BID", referencedColumnName = "BID")
    @ManyToOne(optional = false)
    private Buch fkBid;

    public Buchautor() {
    }

    public Buchautor(Integer baid) {
        this.baid = baid;
    }

    public Integer getBaid() {
        return baid;
    }

    public void setBaid(Integer baid) {
        this.baid = baid;
    }

    public Autor getFkAid() {
        return fkAid;
    }

    public void setFkAid(Autor fkAid) {
        this.fkAid = fkAid;
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
        hash += (baid != null ? baid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Buchautor)) {
            return false;
        }
        Buchautor other = (Buchautor) object;
        if ((this.baid == null && other.baid != null) || (this.baid != null && !this.baid.equals(other.baid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Buchautor[ baid=" + baid + " ]";
    }
    
}
