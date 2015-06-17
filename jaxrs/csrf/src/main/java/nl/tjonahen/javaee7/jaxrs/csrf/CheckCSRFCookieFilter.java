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

import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Philippe Tjon - A - Hen, philippe@tjonahen.nl
 */
@Provider
public class CheckCSRFCookieFilter implements ContainerRequestFilter {

    @Context
    private HttpServletRequest hsr;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        
        System.out.println("Check SessionID    :" + hsr.getSession().getId());
        if ("GET".equals(requestContext.getRequest().getMethod())) {
            return;
        }
        if ("HEAD".equals(requestContext.getRequest().getMethod())) {
            return;
        }
        if ("OPTIONS".equals(requestContext.getRequest().getMethod())) {
            return;
        }

        for (Entry<String, Cookie> e : requestContext.getCookies().entrySet()) {
            if ("tjonahen-csrfp".equals(e.getKey())) {
                System.out.println("Found cookie " + e.getValue());
                Object o = hsr.getSession().getAttribute("tjonahen-csrfp");
                if (o != null) {
                    String id = (String) o;
                    if (id.equals(e.getValue().getValue())) {
                        return;
                    } else {
                        System.out.println("Session cookie value mismatch.");
                    }
                } else {
                    System.out.println("No session cookie value.");
                }
            }
        }
        requestContext.abortWith(Response.status(Response.Status.NOT_ACCEPTABLE).build());
    }

}
