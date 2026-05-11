package cafeteria.products.food.bread;

import cafeteria.products.ProductBase;

public abstract class Bread extends ProductBase {
  protected Bread(String name, Double basePrice) {
    super(name, basePrice);
  }
}