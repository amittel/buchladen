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
@Table(name = "account")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Account.findAll", query = "SELECT a FROM Account a"),
    @NamedQuery(name = "Account.findByAcid", query = "SELECT a FROM Account a WHERE a.acid = :acid"),
    @NamedQuery(name = "Account.findByACCName", query = "SELECT a FROM Account a WHERE a.aCCName = :aCCName"),
    @NamedQuery(name = "Account.findByAccpwd", query = "SELECT a FROM Account a WHERE a.accpwd = :accpwd"),
    @NamedQuery(name = "Account.findByACCAdmin", query = "SELECT a FROM Account a WHERE a.aCCAdmin = :aCCAdmin")})
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ACID")
    private Integer acid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "ACCName")
    private String aCCName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "ACCPWD")
    private String accpwd;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "ACCAdmin")
    private String aCCAdmin;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkAcc")
    private Collection<Kunde> kundeCollection;

    public Account() {
    }

    public Account(Integer acid) {
        this.acid = acid;
    }

    public Account(Integer acid, String aCCName, String accpwd, String aCCAdmin) {
        this.acid = acid;
        this.aCCName = aCCName;
        this.accpwd = accpwd;
        this.aCCAdmin = aCCAdmin;
    }

    public Integer getAcid() {
        return acid;
    }

    public void setAcid(Integer acid) {
        this.acid = acid;
    }

    public String getACCName() {
        return aCCName;
    }

    public void setACCName(String aCCName) {
        this.aCCName = aCCName;
    }

    public String getAccpwd() {
        return accpwd;
    }

    public void setAccpwd(String accpwd) {
        this.accpwd = accpwd;
    }

    public String getACCAdmin() {
        return aCCAdmin;
    }

    public void setACCAdmin(String aCCAdmin) {
        this.aCCAdmin = aCCAdmin;
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
        hash += (acid != null ? acid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Account)) {
            return false;
        }
        Account other = (Account) object;
        if ((this.acid == null && other.acid != null) || (this.acid != null && !this.acid.equals(other.acid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Account[ acid=" + acid + " ]";
    }
    
}
