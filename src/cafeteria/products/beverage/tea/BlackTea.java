package cafeteria.products.beverage.tea;

public class BlackTea extends Tea{
  private final static String NAME = "Black Tea";
  public BlackTea() {
    super(BlackTea.NAME, 1.30);
  }

  @Override
  protected void prepareIngredients() {
    System.out.println("Calentando agua para Black Tea");
  }

  @Override
  protected void cook() {
    System.out.println("Infusionando Black Tea");
  }
}
