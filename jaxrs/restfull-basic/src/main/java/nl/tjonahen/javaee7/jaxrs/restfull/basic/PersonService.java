/*
 * Copyright (C) 2014 Philippe Tjon - A - Hen, philippe@tjonahen.nl
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
package nl.tjonahen.javaee7.jaxrs.restfull.basic;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 *
 * @author Philippe Tjon - A - Hen, philippe@tjonahen.nl
 */
@Path("/person")
public class PersonService {

    private final Map<Integer, Person> dataInMemory;

    public PersonService() {
        dataInMemory = new HashMap<>();
    }

    @POST
    @Consumes("application/xml")
    public Response post(final Person person) {
        int id = dataInMemory.size() + 1;
        person.setId(id);
        dataInMemory.put(id, person);

        return Response.created(URI.create("/person/" + id)).build();
    }

    @GET
    @Path("{id}")
    @Produces("application/xml")
    public Person get(@PathParam("id") int id) {
        Person person = dataInMemory.get(id);
        if (person == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return person;
    }
}
