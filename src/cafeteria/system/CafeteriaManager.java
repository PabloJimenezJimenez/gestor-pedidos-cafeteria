package cafeteria.system;

import cafeteria.factories.products.ProductFactory;
import cafeteria.factories.toppings.ToppingFactory;
import cafeteria.kitchen.Kitchen;
import cafeteria.orders.Order;
import cafeteria.orders.observers.OrderObserver;
import cafeteria.payments.PaymentMethod;
import cafeteria.products.Product;
import java.util.ArrayList;
import java.util.List;

public class CafeteriaManager {

  private static CafeteriaManager instance;

  private final Catalog catalog;
  private final Kitchen kitchen;
  private final List<Order> activeOrders;

  private CafeteriaManager() {
    this.catalog = Catalog.getInstance();
    this.kitchen = Kitchen.getInstance();
    this.activeOrders = new ArrayList<>();
  }

  public static CafeteriaManager getInstance() {
    if (CafeteriaManager.instance == null) {
      CafeteriaManager.instance = new CafeteriaManager();
    }
    return CafeteriaManager.instance;
  }

  public Order createNewOrder() {
    Order order = new Order();
    this.activeOrders.add(order);
    return order;
  }

  public Product createProduct(String category, String type) {
    ProductFactory factory = this.catalog.getProductFactory(category);
    return factory.create(type);
  }

  public ToppingFactory getToppingFactoryFor(String beverageName) {
    return this.catalog.getToppingFactory(beverageName);
  }

  public void addProductToOrder(Order order, Product product) {
    order.addItem(product);
  }

  public Boolean checkout(Order order, PaymentMethod method) {
    try {
      order.pay(method);
      this.kitchen.enqueue(order);
      return true;
    } catch (IllegalStateException e) {
      System.out.println("[Manager] Error al cobrar: " + e.getMessage());
      return false;
    }
  }

  public void processKitchenQueue() {
    while (this.kitchen.hasPendingOrders()) {
      this.kitchen.processNext();
    }
  }

  public void registerObserverOnOrder(Order order, OrderObserver observer) {
    order.registerObserver(observer);
  }
}