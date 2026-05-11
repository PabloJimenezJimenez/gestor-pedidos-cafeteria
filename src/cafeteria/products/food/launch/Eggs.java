package cafeteria.products.food.launch;

public class Eggs  extends LaunchProduct {
  private static final String NAME = "Huevos";
  public Eggs() {
    super(Eggs.NAME, 1.80);
  }

  @Override
  protected void prepareIngredients() {
    System.out.println("Cascando huevos");
  }

  @Override
  protected void cook() {
    System.out.println("Friendo huevos");
  }
}