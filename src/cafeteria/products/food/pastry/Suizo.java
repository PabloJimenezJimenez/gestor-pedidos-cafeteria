package cafeteria.products.food.pastry;

public class Suizo extends Pastry {
  private static final String NAME = "Suizo";
  public Suizo() {
    super(Suizo.NAME, 1.40);
  }

  @Override
  protected void prepareIngredients() {
    System.out.println("Cogiendo suizo de la vitrina");
  }

  @Override
  protected void cook() {
    System.out.println("Espolvoreando azúcar al suizo");
  }
}
