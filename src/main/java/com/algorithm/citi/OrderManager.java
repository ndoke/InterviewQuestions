package com.algorithm.citi;

import java.util.*;

public class OrderManager {
  List<Order> orders;
  Map<Integer, List<Delivery>> restIdToDelv;

  OrderManager() {
    this.orders = new ArrayList<>();
    this.restIdToDelv = new HashMap<>();
  }

  void addOrder(Order order) {
    orders.add(order);
  }

  void updateOrderStatus(int orderId, OrderStatus newStatus) {
    for (Order o : orders) {
      if (o.orderId == orderId) {
        o.status = newStatus;
        return;
      }
    }
  }

  OrderStats getOrderStatistics() {
    int total = orders.size();

    int active = 0;
    for (Order o : orders) {
      if (o.status == OrderStatus.PLACED 
          || o.status == OrderStatus.OUT_FOR_DELIVERY
          || o.status == OrderStatus.PREPARING) {
        active++;
      }
    }

    int closed = 0;
    for (Order o : orders) {
      if (o.status == OrderStatus.DELIVERED ||
          o.status == OrderStatus.CANCELED) {
        closed++;
      }
    }

    return new OrderStats(total, active, closed);
  }
  
  void addDelivery(int orderId, Delivery delivery) {
    Order order = null;
    for (Order o : orders) {
      if (o.orderId == orderId) {
        order = o;
      }
    }
    
    if (order != null) {
      List<Delivery> deliveries = restIdToDelv.getOrDefault(order.restaurantId, new ArrayList<>());
      deliveries.add(delivery);
      restIdToDelv.put(order.restaurantId, deliveries);
    }
  }
  
  Map<Integer, Double> getAverageDeliveryTimeByRestaurant() {
    Map<Integer, Double> result = new HashMap<>();
    for (int restId : restIdToDelv.keySet()) {
      List<Delivery> deliveries = restIdToDelv.get(restId);
      int numDel = deliveries.size();
      int totalTime = 0;
      for (Delivery delivery : deliveries) {
        int time = delivery.endMinute - delivery.startMinute;
        totalTime += time;
      }
      result.put(restId, (double)totalTime / numDel);
    }
    return result;
  }
  
  Map<Integer, Double> getDeliveryFees() {
    Map<Integer, Double> result = new HashMap<>();
    for (Order order : orders) {
      if (order.orderValue >= 50) {
        result.put(order.orderId, 0.0d);
      } else {
        order.distanceKm = Math.ceil(order.distanceKm);
        double baseFee = order.distanceKm + 1;
        if (order.orderValue >= 30) {
          baseFee /= 2;
        }
        result.put(order.orderId, baseFee);
      }
    }
    
    return result;
  }
}