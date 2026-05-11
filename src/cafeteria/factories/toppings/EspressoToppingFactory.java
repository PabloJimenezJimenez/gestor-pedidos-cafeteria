package cafeteria.factories.toppings;

import cafeteria.products.Product;
import cafeteria.products.decorator.toppings.Honey;
import cafeteria.products.decorator.toppings.Sugar;
import cafeteria.products.decorator.toppings.Topping;

public class EspressoToppingFactory implements ToppingFactory {

  private static final String NOT_ALLOWED = "Complemento no válido para espresso";

  @Override
  public Topping createSugar(Product wrapped) {
    return new Sugar(wrapped);
  }

  @Override
  public Topping createHoney(Product wrapped) {
    return new Honey(wrapped);
  }

  @Override
  public Topping createLactoseFreeMilk(Product wrapped) {
    throw new UnsupportedOperationException(EspressoToppingFactory.NOT_ALLOWED);
  }

  @Override
  public Topping createOatMilk(Product wrapped) {
    throw new UnsupportedOperationException(EspressoToppingFactory.NOT_ALLOWED);
  }
}