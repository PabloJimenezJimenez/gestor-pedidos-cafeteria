package cafeteria.kitchen;

import cafeteria.orders.Order;
import java.util.LinkedList;
import java.util.Queue;

public class Kitchen {

  private static Kitchen instance;

  private final Queue<Order> queue;

  private Kitchen() {
    this.queue = new LinkedList<>();
  }

  public static Kitchen getInstance() {
    if (Kitchen.instance == null) {
      Kitchen.instance = new Kitchen();
    }
    return Kitchen.instance;
  }

  public void enqueue(Order order) {
    System.out.println("[Cocina] Pedido #" + order.getId() + " encolado");
    this.queue.add(order);
  }

  public void processNext() {
    Order next = this.queue.poll();
    if (next == null) {
      System.out.println("[Cocina] No hay pedidos pendientes");
      return;
    }
    System.out.println("[Cocina] Procesando pedido #" + next.getId());
    next.prepare();
    next.deliver();
  }

  public Boolean hasPendingOrders() {
    return !this.queue.isEmpty();
  }
}