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
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * SalesOrderLine collection
 * 
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
@Embeddable
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SalesOrderLineCollection", propOrder = {
    "salesOrderLine"
})
public class SalesOrderLineCollection implements Serializable {

    @OneToMany(mappedBy = "salesOrder", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @XmlElement(name = "salesOrderLine", required = true)
    private Collection<SalesOrderLine> salesOrderLines;

    public SalesOrderLineCollection() {
    }
    
    boolean contains(final String productName) {
        if (salesOrderLines == null) {
            return false;
        }
        for (SalesOrderLine salesOrderLine: salesOrderLines) {
            if (salesOrderLine.containsProduct(productName)) {
                return true;
            }
        }
        return false;
    }    

    void add(final Product product, final BigDecimal price) {
        if (salesOrderLines == null) {
            salesOrderLines = new ArrayList<>();
        }
        salesOrderLines.add(new SalesOrderLine(product, price));
    }

    void fixRelations(final SalesOrder salesOrder) {
        for (SalesOrderLine salesOrderLine: salesOrderLines) {
            salesOrderLine.setSalesOrder(salesOrder);
        }
    }

}