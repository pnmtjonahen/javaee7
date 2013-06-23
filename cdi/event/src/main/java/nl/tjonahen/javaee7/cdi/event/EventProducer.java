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
package nl.tjonahen.javaee7.cdi.event;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 *
 * Produces the CDI message event.
 * 
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
@Path("/producer")
@ApplicationScoped
public class EventProducer {
    
    @Inject
    @MessageEvent
    private Event<Message> eventMessage;
    
   
    /**
     * 
     * @param message -
     * @return -
     */
    @GET
    @Path("/{message}")
    public String get(final @PathParam("message") String message) {
        final Message msg = new Message();
        msg.setSomeData(message);

        eventMessage.fire(msg);
        
        return "sending " + message;
    }
}
