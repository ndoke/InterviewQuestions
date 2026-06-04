package com.algorithm.citi;

public class Order {
	  int orderId;
	  int restaurantId;
	  int customerId;
	  double orderValue;
	  double distanceKm;
	  OrderStatus status;

	  Order(int orderId, int restaurantId, int customerId, double orderValue, double distanceKm, OrderStatus status) {
	    this.orderId = orderId;
	    this.restaurantId = restaurantId;
	    this.customerId = customerId;
	    this.orderValue = orderValue;
	    this.distanceKm = distanceKm;
	    this.status = status;
	  }
}
