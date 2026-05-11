package cafeteria.products.decorator.formats;

import cafeteria.products.Product;
import cafeteria.products.decorator.ProductDecorator;

public abstract class Format extends ProductDecorator {
  protected Format(Product wrapped) {
    super(wrapped);
  }
}
