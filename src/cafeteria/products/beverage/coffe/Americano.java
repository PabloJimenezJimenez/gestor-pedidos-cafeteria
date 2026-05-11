package cafeteria.products.beverage.coffe;

public class Americano extends Coffee{
  private final static String NAME = "Americano";
  public Americano() {
    super(Americano.NAME, 1.40);
  }

  @Override
  protected void prepareIngredients() {
    System.out.println("Moliendo café para Americano");
  }

  @Override
  protected void cook() {
    System.out.println("Extrayendo Americano");
  }
}
