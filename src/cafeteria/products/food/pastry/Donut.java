package cafeteria.products.food.pastry;

public class Donut extends Pastry {
  private static final String NAME = "Donut";
  public Donut() {
    super(Donut.NAME, 1.20);
  }

  @Override
  protected void prepareIngredients() {
    System.out.println("Cogiendo donut de la vitrina");
  }

  @Override
  protected void cook() {
    System.out.println("Glaseando donut");
  }
}