package cafeteria.products.decorator.toppings;

import cafeteria.products.Product;
import cafeteria.products.decorator.ProductDecorator;

public abstract class Topping extends ProductDecorator {
  protected Topping(Product wrapped) {
    super(wrapped);
  }
}