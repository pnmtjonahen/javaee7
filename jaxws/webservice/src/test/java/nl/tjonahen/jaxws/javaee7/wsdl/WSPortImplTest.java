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

import nl.tjonahen.jaxws.javaee7.wsdl.WSPortImpl;
import nl.tjonahen.jaxws.javaee7.ObjectFactory;
import nl.tjonahen.jaxws.javaee7.WsRequest;
import org.junit.Assert;
import org.junit.Test;

/**
 * Directly call the ws implementing class, no infrastructure or javaEE features are used.
 * 
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
public class WSPortImplTest {
    

    /**
     * Test of triggerFilter method, of class WSPortImpl.
     */
    @Test
    public void testTriggerFilter() throws Exception {
        final ObjectFactory of = new ObjectFactory();
        final WsRequest parameters = of.createWsRequest();
        parameters.setParam1("test1");
        parameters.setParam2("test2");
        final WSPortImpl instance = new WSPortImpl();
        final String result = instance.triggerFilter(parameters);
        Assert.assertEquals("test1 test2", result);
    }
}