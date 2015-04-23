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
package nl.tjonahen.javaee7.cdi.springbeans;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import nl.tjonahen.javaee7.cdi.springbridge.springsupport.ApplicationContextLocatorImpl;
import nl.tjonahen.javaee7.cdi.springbridge.springsupport.ApplicationContextManager;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Spring web application init.
 * 
 * We configure the dispatcher servlet with a applicationContext. 
 * The same application context is given to the CDI-Spring bridge to locate spring beans 
 * when injecting them in a CDI way.
 *
 * @author Philippe Tjon - A - Hen, philippe@tjonahen.nl
 */
public class SpringApplication implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext container) {
        XmlWebApplicationContext appContext = new XmlWebApplicationContext();
        appContext.setConfigLocation("classpath:root-context.xml");  
        ApplicationContextLocatorImpl.putContext(appContext);
        appContext.refresh();
        ApplicationContextManager.getInstance(); // trigger Application context locator
        
        
        ServletRegistration.Dynamic registration = container.addServlet("dispatcher", new DispatcherServlet(appContext));
        registration.setInitParameter("contextConfigLocation", "");
        registration.setLoadOnStartup(1);
        registration.addMapping("/spring/*");
        
    }

}