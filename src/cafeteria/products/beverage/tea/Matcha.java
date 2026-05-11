package cafeteria.products.beverage.tea;

public class Matcha extends Tea{
  private final static String NAME = "Matcha";
  public Matcha() {
    super(Matcha.NAME, 2.00);
  }

  @Override
  protected void prepareIngredients() {
    System.out.println("Calentando agua para Matcha");
  }

  @Override
  protected void cook() {
    System.out.println("Infusionando Matcha");
  }
}
