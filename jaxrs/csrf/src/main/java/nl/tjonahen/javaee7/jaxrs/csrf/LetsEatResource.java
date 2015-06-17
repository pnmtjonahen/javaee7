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
package nl.tjonahen.javaee7.jaxrs.csrf;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

/**
 *
 * @author Philippe Tjon - A - Hen, philippe@tjonahen.nl
 */
@Path("/letseat")
public class LetsEatResource {
    ;
    @GET
    @Path("/{n}")
    public String dinner(@Context HttpServletRequest request, @PathParam("n") String name) {
        System.out.println("GET sessionID      :" + request.getSession().getId());
        return "Lets eat, " + name;
    }
    
    @DELETE
    public Response deleteDinner(@Context HttpServletRequest request) {
        System.out.println("DELETE sessionID   :" + request.getSession().getId());
        return Response.status(Response.Status.GONE).build();
    }
}
