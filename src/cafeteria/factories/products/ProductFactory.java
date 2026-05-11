package cafeteria.factories.products;

import cafeteria.products.Product;

public interface ProductFactory {
  Product create(String type);
}
