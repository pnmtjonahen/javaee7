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

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Philippe Tjon - A - Hen, philippe@tjonahen.nl
 */
@WebFilter(filterName = "GenerateCSRFCookieFilter", urlPatterns = {"/*"})
public class GenerateCSRFCookieFilter implements Filter {



    private String getCookieValue(final HttpServletRequest hsr) {
        String id = "ID:" + System.currentTimeMillis();
        Object o = hsr.getSession().getAttribute(CSRFConstants.CSRF_SESSION_ATTRIBUTE);
        if (o == null) {
            hsr.getSession().setAttribute(CSRFConstants.CSRF_SESSION_ATTRIBUTE, id);
        } else {
            id = (String) o;
        }
        
        return id;
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException 
    {
        chain.doFilter(request, response);
        
        final HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        final HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        final Cookie cookie = new Cookie(CSRFConstants.CSRF_COOKIE, getCookieValue(httpServletRequest));
        cookie.setHttpOnly(false);
        cookie.setMaxAge(-1);
        
        httpServletResponse.addCookie(cookie);
        
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }


    @Override
    public void destroy() {
    }
    
    
    
}
