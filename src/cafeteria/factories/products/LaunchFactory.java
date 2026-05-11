package cafeteria.factories.products;

import cafeteria.products.Product;
import cafeteria.products.food.launch.*;

public class LaunchFactory implements ProductFactory {

  @Override
  public Product create(String type) {
    return switch (type.toLowerCase()) {
      case "huevos" -> new Eggs();
      case "bacon" -> new Bacon();
      case "salchichas" -> new Sausages();
      case "yogur" -> new Yogurt();
      case "fruta" -> new Fruit();
      default -> throw new IllegalArgumentException("Launch food desconocido: " + type);
    };
  }
}