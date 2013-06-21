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
package nl.tjonahen.javaee7.jaxws.restfullftl;

import com.sun.jersey.api.view.Viewable;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
@Path("/")
public class HomeResource {

    /**
     * Retrieves representation of an instance of nl.ordina.jtechnologies.restfullftl.HomeResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces({MediaType.TEXT_HTML,MediaType.APPLICATION_XHTML_XML})
    public Viewable get() {
        return new Viewable("/hello", "Me");
    }

}
