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
package nl.tjonahen.jaxws.javaee7.handler;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.net.MalformedURLException;
import javax.servlet.ServletContext;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.ws.LogicalMessage;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.handler.LogicalHandler;
import javax.xml.ws.handler.LogicalMessageContext;
import javax.xml.ws.handler.MessageContext;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
public class XSDValidationHandler implements LogicalHandler<LogicalMessageContext> {



    @Override
    public boolean handleMessage(LogicalMessageContext logicalContext) {
        Boolean isOutBound = (Boolean) logicalContext
                .get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

        if (isOutBound) {
            return true;
        }

        LogicalMessage lm = logicalContext.getMessage();
        Source payload = lm.getPayload();
        StreamResult res = new StreamResult(new StringWriter());
        String message = "";

        try {
            Transformer trans;
            trans = TransformerFactory.newInstance().newTransformer();
            trans.transform(payload, res);
            message = res.getWriter().toString();
            // Validate  
            validate(message, logicalContext);
        } catch (TransformerConfigurationException e) {
            // When Source payload Transformer object could not be obtained.  
            throw new WebServiceException(e);
        } catch (TransformerFactoryConfigurationError e) {
            // When Source payload Transformer object could not be obtained.  
            throw new WebServiceException(e);
        } catch (TransformerException e) {
            // When Source payload could not be transformed to String.  
            throw new WebServiceException(e);
        } catch (MalformedURLException e) {
            // When URL to schema is invalid.  
            throw new WebServiceException(e);
        } catch (ParserConfigurationException e) {
            // When parser needed for validation could not be obtained.  
            throw new WebServiceException(e);
        } catch (IOException e) {
            // When something is wrong with IO.  
            throw new WebServiceException(e);
        } catch (SAXException e) {
            // When XSD-schema validation fails.  
            throw new WebServiceException(e);
        }

        return true;
    }

    private void validate(String xml, LogicalMessageContext logicalContext)
            throws ParserConfigurationException, IOException,
            MalformedURLException, SAXException {

        DocumentBuilder parser = null;
        Document document = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        parser = dbf.newDocumentBuilder();

        byte bytes[] = xml.getBytes();
        document = parser.parse(new ByteArrayInputStream(bytes));
        SchemaFactory factory = SchemaFactory
                .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

        ServletContext servletContext =
            (ServletContext) logicalContext.get(MessageContext.SERVLET_CONTEXT);

        Schema schema = factory.newSchema(new StreamSource(servletContext.getResourceAsStream("/WEB-INF/wsdl/service.xsd")));
        javax.xml.validation.Validator validator = schema.newValidator();
        validator.validate(new DOMSource(document));
    }

    @Override
    public boolean handleFault(LogicalMessageContext context) {
        return true;
    }

    @Override
    public void close(MessageContext context) {
        ;
    }
}
