package cafeteria.orders.states;


import cafeteria.orders.Order;
import cafeteria.payments.PaymentMethod;

public class Ready implements OrderState {

  private static final String NAME = "Listo";
  private static final String ALREADY_PAID = "El pedido ya está pagado";
  private static final String ALREADY_READY = "El pedido ya está listo";

  @Override
  public void pay(Order order, PaymentMethod method) {
    throw new IllegalStateException(Ready.ALREADY_PAID);
  }

  @Override
  public void prepare(Order order) {
    throw new IllegalStateException(Ready.ALREADY_READY);
  }

  @Override
  public void markReady(Order order) {
    throw new IllegalStateException(Ready.ALREADY_READY);
  }

  @Override
  public void deliver(Order order) {
    System.out.println("[Pedido] Entregando pedido al cliente");
    order.setState(new Delivered());
  }

  @Override
  public String getName() {
    return Ready.NAME;
  }
}