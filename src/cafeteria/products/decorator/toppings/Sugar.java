package cafeteria.products.decorator.toppings;

import cafeteria.products.Product;

public class Sugar extends Topping {
  private static final String EXTRA_DESCRIPTION = "con azúcar";

  public Sugar(Product wrapped) {
    super(wrapped);
  }

  @Override
  protected Double extraPrice() {
    return 0.10;
  }

  @Override
  protected String extraDescription() {
    return Sugar.EXTRA_DESCRIPTION;
  }
}