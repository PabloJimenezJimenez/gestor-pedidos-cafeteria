package cafeteria.products.beverage.coffe;

public class Latte extends Coffee{
  private final static String NAME = "Latte";
  public Latte() {
    super(Latte.NAME, 1.80);
  }

  @Override
  protected void prepareIngredients() {
    System.out.println("Moliendo café para Latte");
  }

  @Override
  protected void cook() {
    System.out.println("Extrayendo Latte");
  }
}
