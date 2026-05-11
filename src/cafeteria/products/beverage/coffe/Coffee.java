package cafeteria.products.beverage.coffe;

import cafeteria.products.ProductBase;

public abstract class Coffee extends ProductBase {
  protected Coffee(String name, Double basePrice) {
    super(name, basePrice);
  }
}
