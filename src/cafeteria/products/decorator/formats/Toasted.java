package cafeteria.products.decorator.formats;

import cafeteria.products.Product;

public class Toasted extends Format {
  private static final String EXTRA_DESCRIPTION = "tostado";

  public Toasted(Product wrapped) {
    super(wrapped);
  }

  @Override
  protected Double extraPrice() {
    return 0.50;
  }

  @Override
  protected String extraDescription() {
    return Toasted.EXTRA_DESCRIPTION;
  }
}
