/*
 * Copyright (C) 2013 Philippe Tjon-A-Hen philippe@tjonahen.nl
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package nl.tjonahen.javaee7.jaxrs.jpa.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * The Sales Order.
 * 
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "SalesOrder.findAll", query = "SELECT b FROM SalesOrder b")
})
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "id",
    "salesOrderLineCollection"
})
@XmlRootElement(name = "SalesOrder")
public class SalesOrder implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlElement(required = true)
    private Long id;

    @Embedded
    private SalesOrderLineCollection salesOrderLineCollection;
    
    
    public SalesOrder() {
    }
    
    public Long getId() {
        return id;
    }

    public SalesOrderLineCollection getSalesOrderLineCollection() {
        init();
        return salesOrderLineCollection;
    }

    private void init() {
        if (salesOrderLineCollection == null) {
            salesOrderLineCollection = new SalesOrderLineCollection();
        }
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SalesOrder)) {
            return false;
        }
        SalesOrder other = (SalesOrder) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "nl.tjonahen.javaee7.jpacollection.entity.SalesOrder[ id=" + id + " ]";
    }
    
    
    /**
     * 
     * @param productNaam -
     * @return -
     */
    public boolean contains(final String productNaam) {
        init();
        return salesOrderLineCollection.contains(productNaam);
    }

    /**
     * 
     * @param product -
     * @param price -
     */
    public void add(final Product product, final BigDecimal price) {
        init();
        salesOrderLineCollection.add(product, price);
    }
    
    /**
     * Fixes the FK relationship
     */
    public void fixRelations() {
        salesOrderLineCollection.fixRelations(this);
    }
    
}
