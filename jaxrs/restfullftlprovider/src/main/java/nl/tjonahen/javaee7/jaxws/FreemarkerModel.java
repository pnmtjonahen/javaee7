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
package nl.tjonahen.javaee7.jaxws;

import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
public class FreemarkerModel {

    private final Map<String, Object> model;
    private final String view;

    public FreemarkerModel(final String view) {
        this.model = new TreeMap<String, Object>();
        this.view = view;
    }

    
    public Map<String, Object> getModel() {
        return model;
    }

    public String getView() {
        return view;
    }
    
    public Object addObject(final String name, Object value) {
        return model.put(name, value);
    }

    
}
