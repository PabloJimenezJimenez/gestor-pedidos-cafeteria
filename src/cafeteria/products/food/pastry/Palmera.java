package cafeteria.products.food.pastry;

public class Palmera extends Pastry {
  private static final String NAME = "Palmera";
  public Palmera() {
    super(Palmera.NAME, 1.30);
  }

  @Override
  protected void prepareIngredients() {
    System.out.println("Cogiendo palmera de la vitrina");
  }

  @Override
  protected void cook() {
    System.out.println("Calentando palmera");
  }
}