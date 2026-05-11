package cafeteria.products.beverage.tea;

public class RedTea extends Tea {
  private final static String NAME = "Red Tea";
  public RedTea() {
    super(RedTea.NAME, 1.30);
  }

  @Override
  protected void prepareIngredients() {
    System.out.println("Calentando agua para Red Tea");
  }

  @Override
  protected void cook() {
    System.out.println("Infusionando Red Tea");
  }
}
