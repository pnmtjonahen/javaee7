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
package nl.tjonahen.javaee7.cdi.springbridge.springsupport;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Simple implementation of the ApplicationContextLocator that is web application friendly.
 *
 * @author Philippe Tjon - A - Hen, philippe@tjonahen.nl
 *
 */
public class ApplicationContextLocatorImpl implements ApplicationContextLocator {

    private static final Map<ClassLoader, WeakReference<ApplicationContext>> MAP
            = Collections.synchronizedMap(new WeakHashMap<ClassLoader, WeakReference<ApplicationContext>>());

    /**
     *
     * @param context the application context to be associated with the current classloader.
     */
    public static void putContext(ApplicationContext context) {
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        MAP.put(contextClassLoader, new WeakReference<ApplicationContext>(context));
    }

    /**
     * Constructor.
     */
    public ApplicationContextLocatorImpl() {
        final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        final WeakReference<ApplicationContext> reference = MAP.get(contextClassLoader);

        if (reference == null || reference.get() == null) {
            throw new IllegalStateException(
                    "Application context must be associated with the current classloader first");
        }
        /* 
         * To prevent GC from killing this until web app is gone. 
         */
        final ApplicationContext applicationContext = reference.get();
        final String beanName = ApplicationContextLocator.class.getName();
        if (!applicationContext.containsBean(beanName)) {
            ConfigurableListableBeanFactory beanFactory = 
                    ((ConfigurableApplicationContext) applicationContext).getBeanFactory();
            
            beanFactory.registerSingleton(beanName, this);
        }

    }

    @Override
    public ApplicationContext locateApplicationContext() {
        final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        final WeakReference<ApplicationContext> reference = MAP.get(contextClassLoader);
        if (reference == null) {
            return null;
        } 
        return reference.get();
    }

}
