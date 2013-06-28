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
 */package nl.tjonahen.javaee7.jaxrs.salesorder;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import nl.tjonahen.javaee7.jaxrs.jpa.entity.SalesOrder;

/**
 * Sales Order rest service
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
@Path("/orders")
@Stateless
public class OrderService {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 
     * @return all sales orders 
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<SalesOrder> getOrders() {
        return entityManager.createNamedQuery("SalesOrder.findAll").getResultList();
    }

    /**
     * 
     * @param salesOrder new sales order 
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putOrder(final SalesOrder salesOrder) {
        entityManager.persist(salesOrder);
    }

    /**
     * 
     * @param id -
     * @return the sales order
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public SalesOrder getSalesOrder(@PathParam("id") final Long id) {
        final SalesOrder salesOrder = entityManager.find(SalesOrder.class, id);
        
        salesOrder.toString();
        
        return salesOrder;
    }
    
    @POST
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void postSalesOrder(@PathParam("id") final Long id, final SalesOrder salesOrder) {
        // perform update
    }
    
    @DELETE
    @Path("/{id}")
    public void deleteSalesOrder(@PathParam("id") final Long id) {
        entityManager.remove(entityManager.find(SalesOrder.class, id));
    }
}
