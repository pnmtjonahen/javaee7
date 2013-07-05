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

function SalesOrderListCtrl($scope, Orders) {
    $scope.saveMessage = "";
    $scope.SalesOrders = Orders.query();

    $scope.delete = function(id) {
        Orders.delete({id: id}, function() {
            $scope.SalesOrders = Orders.query();
        }, function() {
            $scope.saveMessage = "an error occurred.......";
        });
    };

}

function SalesOrderDetailCtrl($scope, $routeParams, $location, Orders) {
    $scope.saveMessage = "";
    $scope.salesOrder = Orders.read({id: $routeParams.orderId});

    $scope.addSalesOrderLine = function() {
        $scope.salesOrder.salesOrderLineCollection.salesOrderLine.push({id: '', price: '',
            product: {id: '', name: '', description: ''}
        });
    };

    $scope.removeSalesOrderLine = function(salesOrderLine) {
        for (var i = 0, ii = $scope.salesOrder.salesOrderLineCollection.salesOrderLine.length; i < ii; i++) {
            if (salesOrderLine === $scope.salesOrder.salesOrderLineCollection.salesOrderLine[i]) {
                $scope.salesOrder.salesOrderLineCollection.salesOrderLine.splice(i, 1);
            }
        }
    };


    $scope.saveSalesOrder = function() {
        Orders.update({id: $scope.salesOrder.id}, $scope.salesOrder, function() {
            $location.path("/orders");
        }, function() {
            $scope.saveMessage = "an error occurred.......";
        });
    };
}

function SalesOrderNewCtrl($scope, $location, Orders) {
    $scope.saveMessage = "";
    $scope.salesOrder = {
        id: '',
        salesOrderLineCollection: {
            salesOrderLine: [{id: '', price: '',
                    product: {id: '', name: '', description: ''}
                }]
        }
    };

    $scope.addSalesOrderLine = function() {
        $scope.salesOrder.salesOrderLineCollection.salesOrderLine.push({id: '', price: '',
            product: {id: '', name: '', description: ''}
        });
    };

    $scope.removeSalesOrderLine = function(salesOrderLine) {
        for (var i = 0, ii = $scope.salesOrder.salesOrderLineCollection.salesOrderLine.length; i < ii; i++) {
            if ($scope.salesOrderLine === $scope.salesOrder.salesOrderLineCollection.salesOrderLine[i]) {
                $scope.salesOrder.salesOrderLineCollection.salesOrderLine.splice(i, 1);
            }
        }
    };

    $scope.saveSalesOrder = function() {
        Orders.save({}, $scope.salesOrder, function() {
            $location.path("/orders");
        }, function() {
            $scope.saveMessage = "an error occurred.......";
        });
    };
}
