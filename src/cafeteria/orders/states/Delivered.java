package cafeteria.orders.states;

import cafeteria.orders.Order;
import cafeteria.payments.PaymentMethod;

public class Delivered implements OrderState {

  private static final String NAME = "Entregado";
  private static final String FINAL = "El pedido ya ha sido entregado";

  @Override
  public void pay(Order order, PaymentMethod method) {
    throw new IllegalStateException(Delivered.FINAL);
  }

  @Override
  public void prepare(Order order) {
    throw new IllegalStateException(Delivered.FINAL);
  }

  @Override
  public void markReady(Order order) {
    throw new IllegalStateException(Delivered.FINAL);
  }

  @Override
  public void deliver(Order order) {
    throw new IllegalStateException(Delivered.FINAL);
  }

  @Override
  public String getName() {
    return Delivered.NAME;
  }
}