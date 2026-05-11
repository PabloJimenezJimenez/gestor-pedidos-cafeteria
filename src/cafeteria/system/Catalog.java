package cafeteria.system;

import cafeteria.factories.products.BreadFactory;
import cafeteria.factories.products.CoffeeFactory;
import cafeteria.factories.products.LaunchFactory;
import cafeteria.factories.products.PastryFactory;
import cafeteria.factories.products.ProductFactory;
import cafeteria.factories.products.SoftDrinkFactory;
import cafeteria.factories.products.TeaFactory;
import cafeteria.factories.toppings.CappuccinoToppingFactory;
import cafeteria.factories.toppings.EspressoToppingFactory;
import cafeteria.factories.toppings.LatteToppingFactory;
import cafeteria.factories.toppings.TeaToppingFactory;
import cafeteria.factories.toppings.ToppingFactory;
import java.util.HashMap;
import java.util.Map;

public class Catalog {

  private static Catalog instance;

  private final Map<String, ProductFactory> productFactories;
  private final Map<String, ToppingFactory> toppingFactories;

  private Catalog() {
    this.productFactories = new HashMap<>();
    this.productFactories.put("cafe", new CoffeeFactory());
    this.productFactories.put("te", new TeaFactory());
    this.productFactories.put("refresco", new SoftDrinkFactory());
    this.productFactories.put("bolleria", new PastryFactory());
    this.productFactories.put("panaderia", new BreadFactory());
    this.productFactories.put("launch", new LaunchFactory());

    this.toppingFactories = new HashMap<>();
    this.toppingFactories.put("espresso", new EspressoToppingFactory());
    this.toppingFactories.put("cappuccino", new CappuccinoToppingFactory());
    this.toppingFactories.put("latte", new LatteToppingFactory());
    this.toppingFactories.put("te", new TeaToppingFactory());
  }

  public static Catalog getInstance() {
    if (Catalog.instance == null) {
      Catalog.instance = new Catalog();
    }
    return Catalog.instance;
  }

  public ProductFactory getProductFactory(String category) {
    ProductFactory factory = this.productFactories.get(category.toLowerCase());
    if (factory == null) {
      throw new IllegalArgumentException("Categoría desconocida: " + category);
    }
    return factory;
  }

  public ToppingFactory getToppingFactory(String beverageName) {
    ToppingFactory factory = this.toppingFactories.get(beverageName.toLowerCase());
    if (factory == null) {
      throw new IllegalArgumentException("La bebida no admite complementos: " + beverageName);
    }
    return factory;
  }
}