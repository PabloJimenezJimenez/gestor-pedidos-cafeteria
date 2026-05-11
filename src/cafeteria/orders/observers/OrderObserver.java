package cafeteria.orders.observers;

import cafeteria.orders.Order;
import cafeteria.orders.states.OrderState;

public interface OrderObserver {
  void onStateChanged(Order order, OrderState newState);
}
