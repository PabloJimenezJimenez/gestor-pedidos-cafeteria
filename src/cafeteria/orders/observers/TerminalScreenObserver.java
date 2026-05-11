package cafeteria.orders.observers;


import cafeteria.orders.Order;
import cafeteria.orders.states.OrderState;

public class TerminalScreenObserver implements OrderObserver {
  @Override
  public void onStateChanged(Order order, OrderState state) {
    if (state != null) {
      System.out.println("[Terminal Screen] " + System.currentTimeMillis() + " - Pedido #" + order.getId() + " → "  + state.getName());
    } else {
      System.out.println("[Terminal Screen] " + System.currentTimeMillis() + " - Pedido #" + order.getId() + " → " + order.getStateName());
    }
  }
}
