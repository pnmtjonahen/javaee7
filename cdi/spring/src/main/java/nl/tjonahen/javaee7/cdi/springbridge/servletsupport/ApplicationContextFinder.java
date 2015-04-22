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
 *
 * Based on the ideas from https://code.google.com/p/jee6-cdi/wiki/CDIAndSpringLivingInHarmony
 */
package nl.tjonahen.javaee7.cdi.springbridge.servletsupport;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import nl.tjonahen.javaee7.cdi.springbridge.springsupport.ApplicationContextLocatorImpl;
import nl.tjonahen.javaee7.cdi.springbridge.springsupport.ApplicationContextManager;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * ServletContextListner to pickup the web application Context from spring.
 * Requires a ContextLoaderListner to be configured.
 * 
 * @author Philippe Tjon - A - Hen, philippe@tjonahen.nl
 */
public class ApplicationContextFinder implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        WebApplicationContext requiredWebApplicationContext = 
                WebApplicationContextUtils.getRequiredWebApplicationContext(sce.getServletContext());
        ApplicationContextLocatorImpl.putContext(requiredWebApplicationContext);
        ApplicationContextManager.getInstance(); // trigger Application context locator
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }

}
