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

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.lang.ref.WeakReference;
import org.springframework.context.ApplicationContext;

/**
 * ApplicationContextManager, this is used to find an ApplicationContextLocator. An ApplicationContextLocator locates an
 * application context.
 *
 *
 * @author Philippe Tjon - A - Hen, philippe@tjonahen.nl
 *
 */
public class ApplicationContextManager {

    /**
     * Using a WeakHash map instead of a singleton to be classloader friendly in a Java EE application. Essentially, I
     * don't want to hold on to a class otherwise the container will not be able to reload the webapplication.
     */
    private static final Map<ClassLoader, WeakReference<ApplicationContextLocator>> MAP = Collections
            .synchronizedMap(new WeakHashMap<ClassLoader, WeakReference<ApplicationContextLocator>>());

    /**
     *
     * @return The Spring application context
     */
    public static ApplicationContext getInstance() {

        final ApplicationContextLocator applicationContextLocator;

        synchronized (MAP) {

            final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
            final WeakReference<ApplicationContextLocator> weakReference = MAP
                    .get(contextClassLoader);

            if (weakReference == null || weakReference.get() == null) {
                applicationContextLocator = new ApplicationContextLocatorImpl();
                MAP.put(contextClassLoader,
                        new WeakReference<ApplicationContextLocator>(
                                applicationContextLocator));
            } else {
                applicationContextLocator = weakReference.get();
            }
        }
        return applicationContextLocator.locateApplicationContext();
    }
}
