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
package nl.tjonahen.javaee7.jpacollection.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;

/**
 * SalesOrderLine collection
 * 
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
@Embeddable
public class SalesOrderLineCollection implements Serializable {

    @OneToMany(mappedBy = "salesOrder", cascade = CascadeType.ALL)
    private Collection<SalesOrderLine> salesOrderLineCollection;

    public SalesOrderLineCollection() {
        salesOrderLineCollection = new ArrayList<>();
    }
    
    boolean contains(final String productName) {
        for (SalesOrderLine bestelOrderLine: salesOrderLineCollection) {
            if (bestelOrderLine.containsProduct(productName)) {
                return true;
            }
        }
        return false;
    }    

    void add(final Product product, final BigDecimal price) {
        salesOrderLineCollection.add(new SalesOrderLine(product, price));
    }
}
