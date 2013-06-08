/*
 * Copyright (C) 2013 Philippe Tjon-A-Hen philippe@tjonahen.nl
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
package nl.tjonahen.javaee7;

import freemarker.cache.WebappTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import javax.servlet.ServletContext;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

@Produces(MediaType.APPLICATION_XHTML_XML)
@Provider
public class FreemarkerProvider implements
        MessageBodyWriter<FreemarkerModel> {

    private static Configuration freemarkerConfig;
    private ServletContext servletContext;
    private String rootPath;

    @Context
    public void setServletContext(final ServletContext context) {
        this.servletContext = context;

        freemarkerConfig = new Configuration();

        rootPath = context.getInitParameter("freemarker.template.path");
        if (rootPath == null || rootPath.trim().length() == 0) {
            rootPath = "/WEB-INF/templates";
        }
        rootPath = rootPath.replaceAll("/$", "");

        freemarkerConfig.setTemplateLoader(new WebappTemplateLoader(context, rootPath));

        final InputStream fmProps = context.getResourceAsStream("freemarker.properties");
        if (fmProps != null) {
            try {
                freemarkerConfig.setSettings(fmProps);
                return;
            } catch (Throwable t) {
            }
        }
        assignFreemarkerConfig(freemarkerConfig, context);
    }

    private void assignFreemarkerConfig(final Configuration config,
            final ServletContext context) {
        // don't always put a ',' in numbers (e.g., id=2000 vs id=2,000)
        config.setNumberFormat("0");

        // don't look for list.en.ftl when list.ftl requested
        config.setLocalizedLookup(false);

        // don't cache
        config.setTemplateUpdateDelay(0);

    }

    private String resolve(final String path) {
        // accept both '/path/to/template' and '/path/to/template.ftl'
        final String defaultExtension = getDefaultExtension();
        final String filePath = path.endsWith(defaultExtension) ? path : path + defaultExtension;
        try {
            final String fullPath = rootPath + filePath;
            final boolean templateFound = servletContext.getResource(fullPath) != null;
            return templateFound ? filePath : null;
        } catch (MalformedURLException e) {
            return null;
        }
    }

    private String getDefaultExtension() {
        return ".ftl";
    }

    @Override
    public void writeTo(FreemarkerModel viewModel, 
                        Class<?> type,
                        Type genericType, Annotation[] annotations,
                        MediaType mediaType,
                        MultivaluedMap<String, Object> headers, OutputStream os)
                        throws IOException, WebApplicationException {

        final Template t =
                freemarkerConfig.getTemplate(resolve(viewModel.getView()));

        try {
            t.process(viewModel.getModel(),
                    new OutputStreamWriter(os));
        } catch (TemplateException ex) {
            System.err.println(ex.getMessage());
        }

    }

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return FreemarkerModel.class.isAssignableFrom(type);
    }

    @Override
    public long getSize(FreemarkerModel t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return -1;
    }
}
