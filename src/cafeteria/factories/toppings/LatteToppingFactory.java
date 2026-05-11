package cafeteria.factories.toppings;


import cafeteria.products.Product;
import cafeteria.products.decorator.toppings.Honey;
import cafeteria.products.decorator.toppings.LactoseFreeMilk;
import cafeteria.products.decorator.toppings.OatMilk;
import cafeteria.products.decorator.toppings.Sugar;
import cafeteria.products.decorator.toppings.Topping;

public class LatteToppingFactory implements ToppingFactory {

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
    return new LactoseFreeMilk(wrapped);
  }

  @Override
  public Topping createOatMilk(Product wrapped) {
    return new OatMilk(wrapped);
  }
}
