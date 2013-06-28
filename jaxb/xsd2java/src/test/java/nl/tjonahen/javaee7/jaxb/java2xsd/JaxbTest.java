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
package nl.tjonahen.javaee7.jaxb.java2xsd;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import nl.tjonahen.javaee7.jaxb.xsd.ObjectFactory;
import nl.tjonahen.javaee7.jaxb.xsd.Product;
import nl.tjonahen.javaee7.jaxb.xsd.SalesOrder;
import nl.tjonahen.javaee7.jaxb.xsd.SalesOrderLine;
import nl.tjonahen.javaee7.jaxb.xsd.SalesOrderLineCollection;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
public class JaxbTest {

    @Test
    public void marshaller() throws XMLStreamException, JAXBException {
        final JAXBContext jc = JAXBContext.newInstance("nl.tjonahen.javaee7.jaxb.xsd");


        final Marshaller m = jc.createMarshaller();

        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        final ObjectFactory of = new ObjectFactory();

        final SalesOrder so = of.createSalesOrder();
        final SalesOrderLineCollection solc = of.createSalesOrderLineCollection();
        final SalesOrderLine sol = of.createSalesOrderLine();
        final Product p = of.createProduct();
        p.setId(new BigInteger("1"));
        p.setName("Useless Box");
        p.setDescription("A shining black box");

        sol.setId(new BigInteger("1"));
        sol.setPrice(BigDecimal.ZERO);
        sol.setProduct(p);

        solc.getSalesOrderLine().add(sol);

        so.setId(new BigInteger("1"));
        so.setSalesOrderLineCollection(solc);

        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        final XMLStreamWriter writer = XMLOutputFactory.newFactory().createXMLStreamWriter(bos);
        m.marshal(so, writer);

        Assert.assertEquals("<?xml version=\"1.0\" ?><SalesOrder xmlns=\"http://xsd.jaxb.javaee7.tjonahen.nl\"><id>1</id><SalesOrderLineCollection><SalesOrderLine><id>1</id><price>0</price><product><id>1</id><name>Useless Box</name><description>A shining black box</description></product></SalesOrderLine></SalesOrderLineCollection></SalesOrder>", bos.toString());
    }

    @Test
    public void unmarshaller() throws JAXBException {
        final JAXBContext jc = JAXBContext.newInstance("nl.tjonahen.javaee7.jaxb.xsd");
        final Unmarshaller u = jc.createUnmarshaller();
        final InputStream resourceAsStream = ClassLoader.class.getResourceAsStream("/salesorder.xml");
        Assert.assertNotNull(resourceAsStream);
        final SalesOrder so = (SalesOrder) u.unmarshal(resourceAsStream);

        Assert.assertEquals(new BigInteger("1"), so.getId());
    }

    @Test
    public void generateXsd() throws JAXBException, IOException {
        final File baseDir = new File("./target");
        
        JAXBContext context = JAXBContext.newInstance("nl.tjonahen.javaee7.jaxb.xsd");
        context.generateSchema(new SchemaOutputResolver() {
            @Override
            public Result createOutput(String namespaceUri, String suggestedFileName) throws IOException {
                return new StreamResult(new File(baseDir, suggestedFileName));
            }
        });
    }
}
