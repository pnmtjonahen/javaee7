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
package nl.tjonahen.javaee7.cdi.cdibeans;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import nl.tjonahen.javaee7.cdi.springbeans.SpringHelloWorldBusiness;
import nl.tjonahen.javaee7.cdi.springbridge.SpringCdi;

/**
 * Set as Stateless to have WELD do the DI instead of jersey.
 * 
 * @author Philippe Tjon - A - Hen, philippe@tjonahen.nl
 */
@Stateless
@Path("/helloWorld")
public class CdiHelloWorld {
    
    @Inject
    @SpringCdi
    private SpringHelloWorldBusiness hwb;
    
    @GET
    public String get() {
        return hwb.welcomeMessage();
    }
}
