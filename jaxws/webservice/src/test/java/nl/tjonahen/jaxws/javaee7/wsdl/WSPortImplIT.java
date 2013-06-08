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
package nl.tjonahen.jaxws.javaee7.wsdl;

import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.WebServiceException;
import nl.tjonahen.jaxws.javaee7.ObjectFactory;
import nl.tjonahen.jaxws.javaee7.WsRequest;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Perform Integration Tests
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
public class WSPortImplIT {

    private static String address;
    private static URL wsdlURL;
    private static QName serviceName;

    @BeforeClass
    public static void setUp() throws Exception {
        serviceName = new QName("http://wsdl.javaee7.jaxws.tjonahen.nl", "WSService");
        address = "http://localhost:8088/javaee7/WSService";
        wsdlURL = new URL(address + "?wsdl");
    }

    @Test(expected = WebServiceException.class)
    public void triggerFilter_with_wrong_data() throws WsFault {

        final WSService service = new WSService(wsdlURL, serviceName);
        final ObjectFactory of = new ObjectFactory();
        
        final WsRequest parameters = of.createWsRequest();
        
        parameters.setParam1("junk");
        parameters.setParam2("junk");
        service.getWSPort().triggerFilter(parameters);
    }
    
    @Test(expected = WebServiceException.class)
    public void triggerFilter_with_ok_data() throws WsFault {

        final WSService service = new WSService(wsdlURL, serviceName);
        final ObjectFactory of = new ObjectFactory();
        
        final WsRequest parameters = of.createWsRequest();
        
        parameters.setParam1("junk@test.org");
        parameters.setParam2("Mister Junk");
        service.getWSPort().triggerFilter(parameters);
    }
}
