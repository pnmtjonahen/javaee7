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

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import nl.tjonahen.javaee7.cdi.springbeans.SpringHelloWorldBusiness;
import nl.tjonahen.javaee7.cdi.springbridge.SpringCdi;
import nl.tjonahen.javaee7.cdi.springbridge.springsupport.ApplicationContextManager;

/**
 * Explicitly make it a enterprise managed bean. Else WELD is not finding it.
 * 
 * @author Philippe Tjon - A - Hen, philippe@tjonahen.nl
 */
@ApplicationScoped
public class SpringBeanFactory {
    
    @Produces
    @SpringCdi
    public SpringHelloWorldBusiness bridge() {
        return (SpringHelloWorldBusiness) ApplicationContextManager.getInstance().getBean("springHelloWorldBusiness");
    }
    
}
