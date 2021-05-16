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
@Table(name = "autor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Autor.findAll", query = "SELECT a FROM Autor a"),
    @NamedQuery(name = "Autor.findByAid", query = "SELECT a FROM Autor a WHERE a.aid = :aid"),
    @NamedQuery(name = "Autor.findByAName", query = "SELECT a FROM Autor a WHERE a.aName = :aName"),
    @NamedQuery(name = "Autor.findByAVorname", query = "SELECT a FROM Autor a WHERE a.aVorname = :aVorname"),
    @NamedQuery(name = "Autor.findByATitel", query = "SELECT a FROM Autor a WHERE a.aTitel = :aTitel"),
    @NamedQuery(name = "Autor.findByAEmail", query = "SELECT a FROM Autor a WHERE a.aEmail = :aEmail"),
    @NamedQuery(name = "Autor.findByATel", query = "SELECT a FROM Autor a WHERE a.aTel = :aTel"),
    @NamedQuery(name = "Autor.findByAwww", query = "SELECT a FROM Autor a WHERE a.awww = :awww")})
public class Autor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "AID")
    private Integer aid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "AName")
    private String aName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "AVorname")
    private String aVorname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "ATitel")
    private String aTitel;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "AEmail")
    private String aEmail;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "ATel")
    private String aTel;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "AWWW")
    private String awww;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkAid")
    private Collection<Buchautor> buchautorCollection;

    public Autor() {
    }

    public Autor(Integer aid) {
        this.aid = aid;
    }

    public Autor(Integer aid, String aName, String aVorname, String aTitel, String aEmail, String aTel, String awww) {
        this.aid = aid;
        this.aName = aName;
        this.aVorname = aVorname;
        this.aTitel = aTitel;
        this.aEmail = aEmail;
        this.aTel = aTel;
        this.awww = awww;
    }

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public String getAName() {
        return aName;
    }

    public void setAName(String aName) {
        this.aName = aName;
    }

    public String getAVorname() {
        return aVorname;
    }

    public void setAVorname(String aVorname) {
        this.aVorname = aVorname;
    }

    public String getATitel() {
        return aTitel;
    }

    public void setATitel(String aTitel) {
        this.aTitel = aTitel;
    }

    public String getAEmail() {
        return aEmail;
    }

    public void setAEmail(String aEmail) {
        this.aEmail = aEmail;
    }

    public String getATel() {
        return aTel;
    }

    public void setATel(String aTel) {
        this.aTel = aTel;
    }

    public String getAwww() {
        return awww;
    }

    public void setAwww(String awww) {
        this.awww = awww;
    }

    @XmlTransient
    public Collection<Buchautor> getBuchautorCollection() {
        return buchautorCollection;
    }

    public void setBuchautorCollection(Collection<Buchautor> buchautorCollection) {
        this.buchautorCollection = buchautorCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (aid != null ? aid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Autor)) {
            return false;
        }
        Autor other = (Autor) object;
        if ((this.aid == null && other.aid != null) || (this.aid != null && !this.aid.equals(other.aid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Autor[ aid=" + aid + " ]";
    }
    
}
