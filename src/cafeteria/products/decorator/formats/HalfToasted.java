package cafeteria.products.decorator.formats;

import cafeteria.products.Product;

public class HalfToasted extends Format {
  private static final String EXTRA_DESCRIPTION = "medio tostado";

  public HalfToasted(Product wrapped) {
    super(wrapped);
  }

  @Override
  protected Double extraPrice() {
    return 0.30;
  }

  @Override
  protected String extraDescription() {
    return HalfToasted.EXTRA_DESCRIPTION;
  }
}