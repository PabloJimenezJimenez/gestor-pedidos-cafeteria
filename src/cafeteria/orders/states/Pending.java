package cafeteria.orders.states;

import cafeteria.orders.Order;
import cafeteria.payments.PaymentMethod;

public class Pending implements OrderState {

  private static final String NAME = "Pendiente";
  private static final String INVALID = "El pedido aún no se ha pagado";

  @Override
  public void pay(Order order, PaymentMethod method) {
    Boolean ok = method.charge(order.getTotal());
    if (ok) {
      System.out.println("[Pedido] Pago confirmado, pasando a preparación");
      order.setState(new InPreparation());
    } else {
      System.out.println("[Pedido] Pago rechazado, sigue pendiente");
    }
  }

  @Override
  public void prepare(Order order) {
    throw new IllegalStateException(Pending.INVALID);
  }

  @Override
  public void markReady(Order order) {
    throw new IllegalStateException(Pending.INVALID);
  }

  @Override
  public void deliver(Order order) {
    throw new IllegalStateException(Pending.INVALID);
  }

  @Override
  public String getName() {
    return Pending.NAME;
  }
}