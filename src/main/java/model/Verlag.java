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
@Table(name = "verlag")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Verlag.findAll", query = "SELECT v FROM Verlag v"),
    @NamedQuery(name = "Verlag.findByVid", query = "SELECT v FROM Verlag v WHERE v.vid = :vid"),
    @NamedQuery(name = "Verlag.findByVName", query = "SELECT v FROM Verlag v WHERE v.vName = :vName"),
    @NamedQuery(name = "Verlag.findByVTel", query = "SELECT v FROM Verlag v WHERE v.vTel = :vTel"),
    @NamedQuery(name = "Verlag.findByVEmail", query = "SELECT v FROM Verlag v WHERE v.vEmail = :vEmail"),
    @NamedQuery(name = "Verlag.findByVwww", query = "SELECT v FROM Verlag v WHERE v.vwww = :vwww")})
public class Verlag implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "VID")
    private Integer vid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "VName")
    private String vName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "VTel")
    private String vTel;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "VEmail")
    private String vEmail;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "VWWW")
    private String vwww;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkVid")
    private Collection<Buch> buchCollection;
    @JoinColumn(name = "FK_ADRID", referencedColumnName = "ADRID")
    @ManyToOne(optional = false)
    private Adresse fkAdrid;

    public Verlag() {
    }

    public Verlag(Integer vid) {
        this.vid = vid;
    }

    public Verlag(Integer vid, String vName, String vTel, String vEmail, String vwww) {
        this.vid = vid;
        this.vName = vName;
        this.vTel = vTel;
        this.vEmail = vEmail;
        this.vwww = vwww;
    }

    public Integer getVid() {
        return vid;
    }

    public void setVid(Integer vid) {
        this.vid = vid;
    }

    public String getVName() {
        return vName;
    }

    public void setVName(String vName) {
        this.vName = vName;
    }

    public String getVTel() {
        return vTel;
    }

    public void setVTel(String vTel) {
        this.vTel = vTel;
    }

    public String getVEmail() {
        return vEmail;
    }

    public void setVEmail(String vEmail) {
        this.vEmail = vEmail;
    }

    public String getVwww() {
        return vwww;
    }

    public void setVwww(String vwww) {
        this.vwww = vwww;
    }

    @XmlTransient
    public Collection<Buch> getBuchCollection() {
        return buchCollection;
    }

    public void setBuchCollection(Collection<Buch> buchCollection) {
        this.buchCollection = buchCollection;
    }

    public Adresse getFkAdrid() {
        return fkAdrid;
    }

    public void setFkAdrid(Adresse fkAdrid) {
        this.fkAdrid = fkAdrid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (vid != null ? vid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Verlag)) {
            return false;
        }
        Verlag other = (Verlag) object;
        if ((this.vid == null && other.vid != null) || (this.vid != null && !this.vid.equals(other.vid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Verlag[ vid=" + vid + " ]";
    }
    
}
