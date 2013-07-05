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
angular.module('salesOrderServices', ['ngResource']).factory('Orders', function($resource) {
            return $resource('rest/orders/:id', {id:'@id'}, {
                query:  {method: 'GET', params: {}, isArray: true},
                read:   {method: 'GET', params: {}, isArray: false},
                delete: {method: 'DELETE', params: {}, isArray: false},
                update: {method: 'POST', params: {}, isArray: false},
                save:   {method: 'PUT', params: {}, isArray: false}
            });
});



