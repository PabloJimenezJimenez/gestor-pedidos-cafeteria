package cafeteria.products.beverage.tea;

import cafeteria.products.ProductBase;

public abstract class Tea extends ProductBase {
  protected Tea(String name, Double price){
    super(name, price);
  }
}
