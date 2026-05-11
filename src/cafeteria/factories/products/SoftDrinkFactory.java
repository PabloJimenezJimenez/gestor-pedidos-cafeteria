package cafeteria.factories.products;

import cafeteria.products.Product;
import cafeteria.products.beverage.softDrink.Juice;
import cafeteria.products.beverage.softDrink.Water;

public class SoftDrinkFactory implements ProductFactory {

  @Override
  public Product create(String type) {
    return switch (type.toLowerCase()) {
      case "agua" -> new Water();
      case "zumo" -> new Juice();
      default -> throw new IllegalArgumentException("Refresco desconocido: " + type);
    };
  }
}
