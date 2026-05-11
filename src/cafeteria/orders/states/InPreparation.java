package cafeteria.orders.states;

import cafeteria.orders.Order;
import cafeteria.payments.PaymentMethod;

public class InPreparation implements OrderState {

  private static final String NAME = "En preparación";
  private static final String ALREADY_PAID = "El pedido ya está pagado";
  private static final String NOT_READY = "El pedido aún se está preparando";

  @Override
  public void pay(Order order, PaymentMethod method) {
    throw new IllegalStateException(InPreparation.ALREADY_PAID);
  }

  @Override
  public void prepare(Order order) {
    System.out.println("[Pedido] Preparando productos en cocina");
    order.prepareAllProducts();
    order.setState(new Ready());
  }

  @Override
  public void markReady(Order order) {
    throw new IllegalStateException(InPreparation.NOT_READY);
  }

  @Override
  public void deliver(Order order) {
    throw new IllegalStateException(InPreparation.NOT_READY);
  }

  @Override
  public String getName() {
    return InPreparation.NAME;
  }
}