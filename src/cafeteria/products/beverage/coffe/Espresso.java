package cafeteria.products.beverage.coffe;

public class Espresso extends Coffee {
  private static final String name = "Espresso";
  public Espresso() {
    super(Espresso.name, 1.20);
  }
  @Override
  protected void prepareIngredients() {
    System.out.println("Moliendo café para espresso");
  }

  @Override
  protected void cook() {
    System.out.println("Extrayendo espresso");
  }

}
