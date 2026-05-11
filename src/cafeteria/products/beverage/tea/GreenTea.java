package cafeteria.products.beverage.tea;

public class GreenTea  extends Tea{
  private final static String NAME = "Green Tea";
  public GreenTea() {
    super(GreenTea.NAME, 1.30);
  }

  @Override
  protected void prepareIngredients() {
    System.out.println("Calentando agua para Green Tea");
  }

  @Override
  protected void cook() {
    System.out.println("Infusionando Green Tea");
  }
}
