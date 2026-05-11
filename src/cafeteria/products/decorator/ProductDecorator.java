package cafeteria.products.decorator;
import cafeteria.products.Product;

public abstract class ProductDecorator implements Product {

  protected final Product wrapped;

  protected ProductDecorator(Product wrapped) {
    this.wrapped = wrapped;
  }

  @Override
  public Double getPrice() {
    return this.wrapped.getPrice() + this.extraPrice();
  }

  @Override
  public String getDescription() {
    return this.wrapped.getDescription() + " " + this.extraDescription();
  }

  @Override
  public void prepare() {
    this.wrapped.prepare();
  }

  protected abstract Double extraPrice();
  protected abstract String extraDescription();
}