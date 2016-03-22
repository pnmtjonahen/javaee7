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
package nl.tjonahen.javaee7.cdi.springbridge;

import java.io.File;
import javax.inject.Inject;
import nl.tjonahen.javaee7.cdi.cdibeans.CdiApplication;
import nl.tjonahen.javaee7.cdi.cdibeans.CdiHelloWorld;
import nl.tjonahen.javaee7.cdi.springbeans.SpringApplication;
import nl.tjonahen.javaee7.cdi.springbridge.springsupport.ApplicationContextManager;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author Philippe Tjon - A - Hen, philippe@tjonahen.nl
 */
@RunWith(Arquillian.class)
public class SpringBridgeTest {

    @Deployment
    public static Archive<?> createTestArchive() {
        File[] files = Maven.resolver().loadPomFromFile("pom.xml")
                .importRuntimeDependencies().resolve().withTransitivity().asFile();

        return ShrinkWrap.create(WebArchive.class, "test-springbridge.war")
                .addPackage(CdiApplication.class.getPackage())
                .addPackage(SpringApplication.class.getPackage())
                .addPackage(SpringCdi.class.getPackage())
                .addPackage(ApplicationContextManager.class.getPackage())
                .addAsResource("root-context.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsLibraries(files);

    }

    @Inject
    private CdiHelloWorld helloWorld;

    @Test
    public void test() {
        helloWorld.get();
    }
}
