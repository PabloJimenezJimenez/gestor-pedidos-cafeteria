package cafeteria.factories.products;

import cafeteria.products.Product;
import cafeteria.products.beverage.tea.BlackTea;
import cafeteria.products.beverage.tea.GreenTea;
import cafeteria.products.beverage.tea.Matcha;
import cafeteria.products.beverage.tea.RedTea;

public class TeaFactory implements ProductFactory {

  @Override
  public Product create(String type) {
    return switch (type.toLowerCase()) {
      case "rojo" -> new RedTea();
      case "negro" -> new BlackTea();
      case "verde" -> new GreenTea();
      case "matcha" -> new Matcha();
      default -> throw new IllegalArgumentException("Té desconocido: " + type);
    };
  }
}