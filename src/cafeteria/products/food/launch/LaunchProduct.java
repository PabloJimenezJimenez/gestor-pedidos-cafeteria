package cafeteria.products.food.launch;

import cafeteria.products.ProductBase;

public abstract class LaunchProduct extends ProductBase implements ItemLaunch {
  protected LaunchProduct(String name, Double basePrice) {
    super(name, basePrice);
  }
}
