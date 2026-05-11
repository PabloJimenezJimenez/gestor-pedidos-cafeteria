package cafeteria.orders.observers;

import cafeteria.orders.Order;
import cafeteria.orders.states.OrderState;

public class AuditLogObserver implements OrderObserver {

  @Override
  public void onStateChanged(Order order,  OrderState state) {
    if (state != null) {
      System.out.println("[Log auditoría] " + System.currentTimeMillis() + " - Pedido #" + order.getId() + " → "  + state.getName());
    } else {
      System.out.println("[Log auditoría] " + System.currentTimeMillis() + " - Pedido #" + order.getId() + " → " + order.getStateName());
    }
  }
}
