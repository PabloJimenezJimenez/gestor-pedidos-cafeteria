package cafeteria.products.beverage.coffe;

public class Cappuccino extends Coffee{
  private final static String NAME = "Cappuccino";
  public Cappuccino() {
    super(Cappuccino.NAME, 1.50);
  }

  @Override
  protected void prepareIngredients() {
    System.out.println("Moliendo café para cappuccino");
  }

  @Override
  protected void cook() {
    System.out.println("Extrayendo cappuccino");
  }
}
