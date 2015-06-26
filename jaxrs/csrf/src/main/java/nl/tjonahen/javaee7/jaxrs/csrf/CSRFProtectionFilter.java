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
@WebFilter(filterName = "CSRFProtectionFilter", urlPatterns = {"/*"})
public class CSRFProtectionFilter implements Filter {

    public static final String CSRF_COOKIE = "TJONAHEN-XSRF";
    public static final String CSRF_SESSION_ATTRIBUTE = "TJONAHEN-XSRF-Value";

    private String getCookieValue(final HttpServletRequest hsr) {
        String id = "ID:" + System.currentTimeMillis();
        Object o = hsr.getSession().getAttribute(CSRF_SESSION_ATTRIBUTE);
        if (o == null) {
            hsr.getSession().setAttribute(CSRF_SESSION_ATTRIBUTE, id);
        } else {
            id = (String) o;
        }

        return id;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        final HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        final HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        if (isAllowed(httpServletRequest)) {
            chain.doFilter(request, response);

            final Cookie cookie = new Cookie(CSRF_COOKIE, getCookieValue(httpServletRequest));
            cookie.setHttpOnly(false);
            cookie.setMaxAge(-1);

            httpServletResponse.addCookie(cookie);
        } else {
            httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    private boolean isAllowed(HttpServletRequest request) {
        if ("GET".equals(request.getMethod())) {
            return true;
        }
        if ("HEAD".equals(request.getMethod())) {
            return true;
        }
        if ("OPTIONS".equals(request.getMethod())) {
            return true;
        }

        Object o = request.getSession().getAttribute(CSRF_SESSION_ATTRIBUTE);
        if (o != null) {
            String sessionIDValue = (String) o;
            for (Cookie cookie : request.getCookies()) {
                if (CSRF_COOKIE.equals(cookie.getName())) {
                    if (sessionIDValue.equals(cookie.getValue())) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
