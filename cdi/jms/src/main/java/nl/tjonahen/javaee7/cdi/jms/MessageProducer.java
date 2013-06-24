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
package nl.tjonahen.javaee7.cdi.jms;

import java.util.UUID;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * JAX-RS message producer. Puts a message on a queue.
 * 
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
@Stateless
@Path("")
public class MessageProducer {
    
    @Resource(lookup = "jms/dataqueue")
    private Queue queue;
    
    @Inject
    private JMSContext jmsContext;
    
    @Path("/send")
    @GET
    public String get() {
        final Payload payload = new Payload();
        payload.setSomeData(UUID.randomUUID().toString());
        jmsContext.createProducer().send(queue, payload);
        return "ok";
    }
    
    
}
