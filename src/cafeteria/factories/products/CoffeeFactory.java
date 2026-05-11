package cafeteria.factories.products;

import cafeteria.products.Product;
import cafeteria.products.beverage.coffe.Americano;
import cafeteria.products.beverage.coffe.Cappuccino;
import cafeteria.products.beverage.coffe.Espresso;
import cafeteria.products.beverage.coffe.Latte;

public class CoffeeFactory implements ProductFactory{
  @Override
  public Product create(String type) {
    return switch (type.toLowerCase()) {
      case "espresso" -> new Espresso();
      case "cappuccino" -> new Cappuccino();
      case "latte" -> new Latte();
      case "americano" -> new Americano();
      default -> throw new IllegalArgumentException("Café desconocido: " + type);
    };
  }
}
