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

function SalesOrderListCtrl($scope, $http) {
    $http.get('rest/orders').success(function(data) {
        $scope.SalesOrders = data;
    });

    $scope.delete = function(id) {
        $http.delete('rest/orders/' + id).success(function(data) {
            $http.get('rest/orders').success(function(data) {
                $scope.SalesOrders = data;
            });
        });
    };

}

function SalesOrderDetailCtrl($scope, $routeParams, $http) {
    $http.get('rest/orders/' + $routeParams.orderId).success(function(data) {
        $scope.salesOrder = data;
    });

}

function NewSalesOrderDetailCtrl($scope, $http, $location) {
    
    var salesOrder = $scope.salesOrder = {
        id: '',
        salesOrderLineCollection: {
            salesOrderLine: [{id: '', price: '',
                    product: {id: '', name: '', description: ''}
                }]
        }
    };

    $scope.addSalesOrderLine = function() {
        salesOrder.salesOrderLineCollection.salesOrderLine.push({id: '', price: '',
            product: {id: '', name: '', description: ''}
        });
    };

    $scope.removeSalesOrderLine = function(salesOrderLine) {
        for (var i = 0, ii = salesOrder.salesOrderLineCollection.salesOrderLine.length; i < ii; i++) {
            if (salesOrderLine === salesOrder.salesOrderLineCollection.salesOrderLine[i]) {
                $scope.salesOrder.salesOrderLineCollection.salesOrderLine.splice(i, 1);
            }
        }
    };
    $scope.saveMessage = "";
    $scope.saveSalesOrder = function() {
        $http.put('rest/orders', salesOrder)
                .success(function(data) {
                    $location.path("/orders");
                })
                .error(function(data, status, headers, config) {
                    $scope.saveMessage = "an error occurred......."
                });
    };
}
