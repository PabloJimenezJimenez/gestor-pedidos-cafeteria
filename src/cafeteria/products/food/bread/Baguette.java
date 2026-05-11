package cafeteria.products.food.bread;

public class Baguette extends Bread {
  private static final String NAME = "Baguette";
  public Baguette() {
    super(Baguette.NAME, 2.00);
  }

  @Override
  protected void prepareIngredients() {
    System.out.println("Cogiendo baguette de la panera");
  }

  @Override
  protected void cook() {
    System.out.println("Cortando baguette");
  }
}
