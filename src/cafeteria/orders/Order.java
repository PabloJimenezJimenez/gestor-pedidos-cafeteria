package cafeteria.orders;

import cafeteria.orders.observers.OrderObserver;
import cafeteria.orders.states.OrderState;
import cafeteria.orders.states.Pending;
import cafeteria.payments.PaymentMethod;
import cafeteria.products.Product;
import java.util.ArrayList;
import java.util.List;

public class Order {

  private static Integer nextId = 1;

  private final Integer id;
  private final List<Product> items;
  private final List<OrderObserver> observers;
  private OrderState state;

  public Order() {
    this.id = Order.nextId++;
    this.items = new ArrayList<>();
    this.observers = new ArrayList<>();
    this.state = new Pending();
  }

  public void addItem(Product product) {
    this.items.add(product);
  }

  public Double getTotal() {
    Double total = 0.0;
    for (Product p : this.items) {
      total += p.getPrice();
    }
    return total;
  }

  public Integer getId() {
    return this.id;
  }

  public String getStateName() {
    return this.state.getName();
  }

  public void setState(OrderState newState) {
    this.state = newState;
    this.notifyObservers();
  }

  public void registerObserver(OrderObserver observer) {
    this.observers.add(observer);
  }

  public void removeObserver(OrderObserver observer) {
    this.observers.remove(observer);
  }

  private void notifyObservers() {
    for (OrderObserver o : this.observers) {
      o.onStateChanged(this, this.state);
    }
  }

  public void pay(PaymentMethod method) {
    this.state.pay(this, method);
  }

  public void prepare() {
    this.state.prepare(this);
  }

  public void markReady() {
    this.state.markReady(this);
  }

  public void deliver() {
    this.state.deliver(this);
  }

  public void prepareAllProducts() {
    for (Product p : this.items) {
      p.prepare();
    }
  }
}