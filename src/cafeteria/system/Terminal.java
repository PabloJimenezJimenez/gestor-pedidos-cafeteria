package cafeteria.system;
import cafeteria.factories.toppings.ToppingFactory;
import cafeteria.orders.Order;
import cafeteria.orders.observers.AuditLogObserver;
import cafeteria.orders.observers.InfoPanelObserver;
import cafeteria.orders.observers.TerminalScreenObserver;
import cafeteria.payments.BizumPayment;
import cafeteria.payments.CashPayment;
import cafeteria.payments.PaymentMethod;
import cafeteria.payments.tpv.BankTPV;
import cafeteria.payments.tpv.BankTPVAdapter;
import cafeteria.products.Product;
import cafeteria.products.food.launch.CombinedDish;
import cafeteria.products.food.launch.ItemLaunch;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Terminal {

  private static Terminal instance;

  private final CafeteriaManager manager;
  private final Scanner scanner;
  private final List<Order> pendingOrders;

  private Terminal() {
    this.manager = CafeteriaManager.getInstance();
    this.scanner = new Scanner(System.in);
    this.pendingOrders = new ArrayList<>();
  }

  public static Terminal getInstance() {
    if (Terminal.instance == null) {
      Terminal.instance = new Terminal();
    }
    return Terminal.instance;
  }

  public void start() {
    System.out.println("=== Bienvenido a La Terminal ===");
    boolean running = true;
    while (running) {
      System.out.println("\n--- Menú principal ---");
      System.out.println("1) Nueva orden");
      System.out.println("2) Consultar pedidos pendientes");
      System.out.println("3) Salir");
      System.out.print("> ");
      String choice = this.scanner.nextLine().trim();
      switch (choice) {
        case "1": this.newOrder(); break;
        case "2": this.checkPendingOrders(); break;
        case "3": running = false; break;
        default:  System.out.println("Opción no válida");
      }
    }
    System.out.println("Hasta luego");
  }

  private void newOrder() {
    Order order = this.manager.createNewOrder();
    order.registerObserver(new TerminalScreenObserver());
    order.registerObserver(new InfoPanelObserver());
    order.registerObserver(new AuditLogObserver());

    boolean addingItems = true;
    while (addingItems) {
      System.out.println("\n--- Añadir producto ---");
      System.out.println("1) Café (espresso, cappuccino, latte, americano)");
      System.out.println("2) Té (rojo, negro, verde, matcha)");
      System.out.println("3) Refresco (agua, zumo)");
      System.out.println("4) Bollería (croissant, donut, palmera, suizo)");
      System.out.println("5) Panadería (baguette, chapata, panpayes)");
      System.out.println("6) Launch food (plato combinado)");
      System.out.println("7) Terminar y pagar");
      System.out.print("> ");
      String choice = this.scanner.nextLine().trim();

      Product product = null;
      String category = null;
      switch (choice) {
        case "1": category = "cafe"; break;
        case "2": category = "te"; break;
        case "3": category = "refresco"; break;
        case "4": category = "bolleria"; break;
        case "5": category = "panaderia"; break;
        case "6": category = "launch"; break;
        case "7": addingItems = false; break;
        default:  System.out.println("Opción no válida"); continue;
      }

      if (!addingItems) break;

      if (category.equals("launch")) {
        product = this.buildCombinedDish();
        if (product == null) continue;
      } else {
        System.out.print("Tipo: ");
        String type = this.scanner.nextLine().trim();
        try {
          product = this.manager.createProduct(category, type);
        } catch (IllegalArgumentException e) {
          System.out.println("Producto no válido: " + e.getMessage());
          continue;
        }

        if (category.equals("cafe") || category.equals("te")) {
          product = this.applyToppings(product, type, category);
        }
      }

      this.manager.addProductToOrder(order, product);
      System.out.println("Añadido: " + product.getDescription() + " — " + String.format("%.2f", product.getPrice()));
    }

    if (order.getTotal() == 0.0) {
      System.out.println("Carrito vacío, cancelando orden");
      return;
    }

    System.out.println("\nTotal: " + String.format("%.2f",order.getTotal()));
    PaymentMethod method = this.choosePaymentMethod();
    if (method == null) {
      System.out.println("Pago cancelado");
      return;
    }

    Boolean paid = this.manager.checkout(order, method);
    if (paid) {
      this.pendingOrders.add(order);
      System.out.println("Pedido #" + order.getId() + " encolado");
    }
  }

  private Product applyToppings(Product product, String type, String category) {
    String key = category.equals("te") ? "te" : type;
    ToppingFactory factory;
    try {
      factory = this.manager.getToppingFactoryFor(key);
    } catch (IllegalArgumentException e) {
      return product;
    }

    boolean adding = true;
    while (adding) {
      System.out.println("¿Añadir complemento?");
      System.out.println("1) Azúcar  2) Miel  3) Leche sin lactosa  4) Leche de avena  5) Terminar");
      System.out.print("> ");
      String choice = this.scanner.nextLine().trim();
      try {
        switch (choice) {
          case "1": product = factory.createSugar(product); break;
          case "2": product = factory.createHoney(product); break;
          case "3": product = factory.createLactoseFreeMilk(product); break;
          case "4": product = factory.createOatMilk(product); break;
          case "5": adding = false; break;
          default:  System.out.println("Opción no válida");
        }
      } catch (UnsupportedOperationException e) {
        System.out.println("Complemento no permitido: " + e.getMessage());
      }
    }
    return product;
  }

  private PaymentMethod choosePaymentMethod() {
    System.out.println("\n--- Método de pago ---");
    System.out.println("1) Efectivo  2) Bizum  3) Tarjeta  4) Cancelar");
    System.out.print("> ");
    String choice = this.scanner.nextLine().trim();
    switch (choice) {
      case "1": return new CashPayment();
      case "2":
        System.out.print("Número de teléfono: ");
        String phone = this.scanner.nextLine().trim();
        return new BizumPayment(phone);
      case "3": return new BankTPVAdapter(new BankTPV());
      default:  return null;
    }
  }

  private void checkPendingOrders() {
    if (this.pendingOrders.isEmpty()) {
      System.out.println("No hay pedidos pendientes");
      return;
    }
    System.out.println("\n--- Pedidos pendientes ---");
    for (Order o : this.pendingOrders) {
      System.out.println("Pedido #" + o.getId() + " — Estado: " + o.getStateName() + " — Total: " + String.format("%.2f",o.getTotal()));
    }
    System.out.println("\nProcesando cocina...");
    this.manager.processKitchenQueue();
  }
  private Product buildCombinedDish() {
    CombinedDish dish = new CombinedDish();
    boolean adding = true;

    while (adding) {
      System.out.println("\n--- Componer plato ---");
      System.out.println("Componentes actuales: " + (dish.getDescription().isEmpty() ? "ninguno" : dish.getDescription()));
      System.out.println("1) Huevos  2) Bacon  3) Salchichas  4) Yogur  5) Fruta  6) Terminar plato  7) Cancelar");
      System.out.print("> ");
      String choice = this.scanner.nextLine().trim();

      try {
        switch (choice) {
          case "1": dish.add((ItemLaunch) this.manager.createProduct("launch", "huevos")); break;
          case "2": dish.add((ItemLaunch) this.manager.createProduct("launch", "bacon")); break;
          case "3": dish.add((ItemLaunch) this.manager.createProduct("launch", "salchichas")); break;
          case "4": dish.add((ItemLaunch) this.manager.createProduct("launch", "yogur")); break;
          case "5": dish.add((ItemLaunch) this.manager.createProduct("launch", "fruta")); break;
          case "6":
            if (dish.getDescription().isEmpty()) {
              System.out.println("El plato no puede estar vacío");
              continue;
            }
            adding = false;
            break;
          case "7": return null;
          default:  System.out.println("Opción no válida");
        }
      } catch (IllegalArgumentException e) {
        System.out.println("Componente no válido: " + e.getMessage());
      }
    }

    return dish;
  }
}
