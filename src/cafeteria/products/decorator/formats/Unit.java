package cafeteria.products.decorator.formats;

import cafeteria.products.Product;

public class Unit extends Format {
  private static final String EXTRA_DESCRIPTION = "en unidad";

  public Unit(Product wrapped) {
    super(wrapped);
  }

  @Override
  protected Double extraPrice() {
    return 0.0;
  }

  @Override
  protected String extraDescription() {
    return Unit.EXTRA_DESCRIPTION;
  }
}
