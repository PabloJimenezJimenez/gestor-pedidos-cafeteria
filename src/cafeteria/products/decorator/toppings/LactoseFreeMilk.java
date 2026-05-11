package cafeteria.products.decorator.toppings;
import cafeteria.products.Product;

public class LactoseFreeMilk extends Topping {
  private static final String EXTRA_DESCRIPTION = "con leche sin lactosa";

  public LactoseFreeMilk(Product wrapped) {
    super(wrapped);
  }

  @Override
  protected Double extraPrice() {
    return 0.30;
  }

  @Override
  protected String extraDescription() {
    return LactoseFreeMilk.EXTRA_DESCRIPTION;
  }
}