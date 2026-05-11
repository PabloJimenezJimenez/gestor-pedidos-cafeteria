package cafeteria.orders.states;

import cafeteria.orders.Order;
import cafeteria.payments.PaymentMethod;

public interface OrderState {
  void pay(Order order, PaymentMethod method);
  void prepare(Order order);
  void markReady(Order order);
  void deliver(Order order);
  String getName();
}
