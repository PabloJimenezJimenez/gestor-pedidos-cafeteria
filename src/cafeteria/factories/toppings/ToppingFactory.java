package cafeteria.factories.toppings;

import cafeteria.products.Product;
import cafeteria.products.decorator.toppings.Topping;

public interface ToppingFactory {
  Topping createSugar(Product wrapped);
  Topping createHoney(Product wrapped);
  Topping createLactoseFreeMilk(Product wrapped);
  Topping createOatMilk(Product wrapped);
}
