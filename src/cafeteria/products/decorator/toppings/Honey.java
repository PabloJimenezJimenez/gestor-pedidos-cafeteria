package cafeteria.products.decorator.toppings;

import cafeteria.products.Product;

public class Honey extends Topping {
  private static final String EXTRA_DESCRIPTION = "con miel";

  public Honey(Product wrapped) {
    super(wrapped);
  }

  @Override
  protected Double extraPrice() {
    return 0.20;
  }

  @Override
  protected String extraDescription() {
    return Honey.EXTRA_DESCRIPTION;
  }
}
