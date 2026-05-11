package cafeteria.products.food.pastry;

import cafeteria.products.ProductBase;

public abstract class Pastry extends ProductBase {
  protected Pastry(String name, Double basePrice) {
    super(name, basePrice);
  }
}