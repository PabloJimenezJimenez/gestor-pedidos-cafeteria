package cafeteria.factories.products;

import cafeteria.products.Product;
import cafeteria.products.food.pastry.Croissant;
import cafeteria.products.food.pastry.Donut;
import cafeteria.products.food.pastry.Palmera;
import cafeteria.products.food.pastry.Suizo;

public class PastryFactory implements ProductFactory {

  @Override
  public Product create(String type) {
    return switch (type.toLowerCase()) {
      case "croissant" -> new Croissant();
      case "donut" -> new Donut();
      case "palmera" -> new Palmera();
      case "suizo" -> new Suizo();
      default -> throw new IllegalArgumentException("Bollería desconocida: " + type);
    };
  }
}