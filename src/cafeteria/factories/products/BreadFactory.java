package cafeteria.factories.products;

import cafeteria.products.Product;
import cafeteria.products.food.bread.Baguette;
import cafeteria.products.food.bread.Chapata;
import cafeteria.products.food.bread.PanPayes;

public class BreadFactory implements ProductFactory {

  @Override
  public Product create(String type) {
    return switch (type.toLowerCase()) {
      case "baguette" -> new Baguette();
      case "chapata" -> new Chapata();
      case "panpayes" -> new PanPayes();
      default -> throw new IllegalArgumentException("Pan desconocido: " + type);
    };
  }
}