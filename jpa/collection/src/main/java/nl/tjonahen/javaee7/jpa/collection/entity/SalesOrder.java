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
package nl.tjonahen.javaee7.jpa.collection.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * The Sales Order.
 * 
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "SalesOrder.findAll", query = "SELECT b FROM SalesOrder b")
})
public class SalesOrder implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Embedded
    private SalesOrderLineCollection salesOrderLineCollection;
    
    public SalesOrder() {
        salesOrderLineCollection = new SalesOrderLineCollection();
    }
    
    public Long getId() {
        return id;
    }

    public SalesOrderLineCollection getSalesOrderLineCollection() {
        return salesOrderLineCollection;
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
        return salesOrderLineCollection.contains(productNaam);
    }

    /**
     * 
     * @param product -
     * @param price -
     */
    public void add(final Product product, final BigDecimal price) {
        salesOrderLineCollection.add(product, price);
    }
    
}
