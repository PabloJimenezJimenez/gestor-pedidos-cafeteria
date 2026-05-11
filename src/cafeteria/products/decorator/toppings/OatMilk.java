package cafeteria.products.decorator.toppings;

import cafeteria.products.Product;

public class OatMilk extends Topping {
  private static final String EXTRA_DESCRIPTION = "con leche de avena";

  public OatMilk(Product wrapped) {
    super(wrapped);
  }

  @Override
  protected Double extraPrice() {
    return 0.40;
  }

  @Override
  protected String extraDescription() {
    return OatMilk.EXTRA_DESCRIPTION;
  }
}
