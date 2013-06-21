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
package nl.tjonahen.javaee7.jpacollection.service;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import nl.tjonahen.javaee7.jpacollection.entity.SalesOrder;
import nl.tjonahen.javaee7.jpacollection.entity.Product;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
public class SalesOrderTest {

    /**
     * The factory that produces entity manager.
     */
    private static EntityManagerFactory entityManagerFactory;
    /**
     * The entity manager that persists and queries the DB.
     */
    private static EntityManager entityManager;

    
    private EntityTransaction entityTransaction;
    
    @BeforeClass
    public static void initTestFixture() throws Exception {
        // Get the entity manager for the tests.
        entityManagerFactory = Persistence.createEntityManagerFactory("JPAPU");
        entityManager = entityManagerFactory.createEntityManager();
    }

    /**
     * Cleans up the session.
     */
    @AfterClass
    public static void closeTestFixture() {
        entityManager.close();
        entityManagerFactory.close();
    }
    
    @Before
    public void initTransaction() {
        entityTransaction = entityManager.getTransaction();
    }

    @Test
    public void verwerkBestelling() {
        entityTransaction.begin();

        final Product product = new Product();
        product.setName("Useless Box Kit");
        product.setDescription("Ok, so it's a black box, sort of shiny, with a switch on top.");

        final SalesOrder bestelOrder = new SalesOrder();
        bestelOrder.add(product, new BigDecimal("39.99"));
        
        entityManager.persist(bestelOrder);

        entityTransaction.commit();
        
        final List<SalesOrder> orders = entityManager.createNamedQuery("SalesOrder.findAll").getResultList();
        
        Assert.assertEquals(1, orders.size());
        
        Assert.assertTrue(bestelOrder.contains("Useless Box Kit"));
        
    }

}
