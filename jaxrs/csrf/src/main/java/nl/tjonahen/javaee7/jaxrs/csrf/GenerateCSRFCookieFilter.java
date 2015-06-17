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
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Philippe Tjon - A - Hen, philippe@tjonahen.nl
 */
@Provider
public class GenerateCSRFCookieFilter implements ContainerResponseFilter {

    @Context
    private HttpServletRequest hsr;
    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
        System.out.println("Generate SessionID :" + hsr.getSession().getId());

        final NewCookie cookie = new NewCookie("tjonahen-csrfp", getCookieValue());
        responseContext.getHeaders().add("Set-Cookie", cookie);
    }

    private String getCookieValue() {
        String id = "ID:" + System.currentTimeMillis();
        Object o = hsr.getSession().getAttribute("tjonahen-csrfp");
        if (o == null) {
            hsr.getSession().setAttribute("tjonahen-csrfp", id);
            System.out.println("New cookie value " + id);
        } else {
            id = (String) o;
            System.out.println("Existing cookie value " + id);
        }
        
        return id;
    }
    
    
    
}
