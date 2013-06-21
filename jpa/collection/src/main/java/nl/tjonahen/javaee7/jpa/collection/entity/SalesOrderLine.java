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
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 * A single sales order line.
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
@Entity
public class SalesOrderLine implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    
    @ManyToOne
    private SalesOrder salesOrder;

    @OneToOne(cascade = CascadeType.ALL)
    private Product product;
    
    @Basic
    private BigDecimal price;

    public SalesOrderLine() {
        
    }
    
    SalesOrderLine(final Product product, final BigDecimal price) {
        this.product = product;
        this.price = price;
    }
            
    public Long getId() {
        return id;
    }


    public SalesOrder getSalesOrder() {
        return salesOrder;
    }

    public void setSalesOrder(final SalesOrder salesOrder) {
        this.salesOrder = salesOrder;
    }

    public Product getProduct() {
        return product;
    }

    public BigDecimal getPrice() {
        return price;
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
        if (!(object instanceof SalesOrderLine)) {
            return false;
        }
        SalesOrderLine other = (SalesOrderLine) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "nl.tjonahen.javaee7.jpacollection.entity.SalesOrderLine[ id=" + id + " ]";
    }

    boolean containsProduct(final String productName) {
        return product.isProduct(productName);
    }
    
}
