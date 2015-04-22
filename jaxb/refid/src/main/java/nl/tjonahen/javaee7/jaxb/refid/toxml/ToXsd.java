/*
 * Copyright (C) 2015 Philippe Tjon - A - Hen, philippe@tjonahen.nl
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
package nl.tjonahen.javaee7.jaxb.refid.toxml;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import org.xml.sax.SAXException;

/**
 *
 * @author Philippe Tjon - A - Hen, philippe@tjonahen.nl
 */
public class ToXsd {

    public void generate() throws JAXBException, IOException, SAXException, XMLStreamException {
        final JAXBContext jc = JAXBContext.newInstance(Order.class);
        final List<ByteArrayOutputStream> outs = new ArrayList<>();
        class MySchemaOutputResolver extends SchemaOutputResolver {

            @Override
            public Result createOutput(String namespaceUri, String suggestedFileName) throws IOException {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                outs.add(out);
                StreamResult streamResult = new StreamResult(out);
                streamResult.setSystemId("");
                return streamResult;
            }
        }
        jc.generateSchema(new MySchemaOutputResolver());
        final StreamSource[] sources = new StreamSource[outs.size()];
        for (int i = 0; i < outs.size(); i++) {
            final ByteArrayOutputStream out = outs.get(i);
            System.out.append(new String(out.toByteArray()));
            sources[i] = new StreamSource(new ByteArrayInputStream(out.toByteArray()), "");
        }
        final SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

        final Marshaller m = jc.createMarshaller();

//        m.setSchema(sf.newSchema(sources));
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);


        Order o = new Order();
        Product c = new Product("1", "chips");
        Product b = new Product("2", "bier");
        
        o.addProduct(c);
        o.addProduct(b);
        
        OrderLine ol1 = new OrderLine();
        ol1.setNumber(5);
        ol1.setProduct(c);
        
        OrderLine ol2 = new OrderLine();
        ol2.setNumber(3);
        ol2.setProduct(b);
        
        o.addOrderLine(ol1);
        o.addOrderLine(ol2);
        
        
        m.marshal(o, System.out );
    }
}
