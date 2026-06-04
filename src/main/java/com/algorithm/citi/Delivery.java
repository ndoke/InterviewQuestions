package com.algorithm.citi;

public class Delivery {
  int deliveryId;
  int startMinute;
  int endMinute;

  Delivery(int deliveryId, int startMinute, int endMinute) {
    this.deliveryId = deliveryId;
    this.startMinute = startMinute;
    this.endMinute = endMinute;
  }

  int getDurationMinutes() {
    return endMinute - startMinute;
  }
  
  @Override
  public String toString() {
    return "[" + deliveryId + ", " + startMinute + ", " + endMinute + "]";
  }
}
